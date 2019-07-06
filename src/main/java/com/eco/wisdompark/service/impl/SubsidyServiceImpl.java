package com.eco.wisdompark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.converter.req.ChangeAmountConverter;
import com.eco.wisdompark.converter.req.SubsidyRecordConverter;
import com.eco.wisdompark.domain.dto.req.subsidy.ManualSubsidyDto;
import com.eco.wisdompark.domain.dto.resp.BatchImportSubsidyDto;
import com.eco.wisdompark.domain.dto.resp.RespBatchImportSubsidyDto;
import com.eco.wisdompark.domain.model.*;
import com.eco.wisdompark.enums.*;
import com.eco.wisdompark.mapper.*;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.SubsidyService;
import com.eco.wisdompark.service.UserService;
import com.eco.wisdompark.strategy.subsidy.SubsidyStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Triplet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 补助服务类
 */
@Slf4j
@Service
public class SubsidyServiceImpl implements SubsidyService {

    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";
    private static final BigDecimal RESET_SUBSIDY_AMOUNT = new BigDecimal(550);

    @Autowired
    private CpuCardMapper cpuCardMapper;
    @Autowired
    private CpuCardService cpuCardService;
    @Autowired
    private SubsidyRecordMapper subsidyRecordMapper;
    @Autowired
    private ChangeAmountMapper changeAmountMapper;
    @Autowired
    private SubsidyRuleMapper subsidyRuleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public void manualSubsidy(ManualSubsidyDto manualSubsidyDto) {
        CpuCard changeBefore = cpuCardService.findByCardSerialNo(manualSubsidyDto.getCardSerialNo());
        if (Objects.isNull(changeBefore) || !Objects.equals(ReportLossStstus.IN_USE.getCode(),
                changeBefore.getReportLossStstus()))
            throw new WisdomParkException(ResponseData.STATUS_CODE_601, "该卡不能进行补助操作");
        CpuCard changeAfter = new CpuCard();
        BeanUtils.copyProperties(changeBefore, changeAfter);
        changeAfter.setSubsidyBalance(changeAfter.getSubsidyBalance().add(manualSubsidyDto.getSubsidyAmount()));
        cpuCardMapper.updateById(changeAfter);
        saveSubsidyRecord(manualSubsidyDto.getSubsidyAmount(), changeBefore, changeAfter);
        saveChangeAmount(manualSubsidyDto.getSubsidyAmount(), changeBefore, changeAfter);
    }

    @Override
//    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void automaticSubsidy() {
        List<SubsidyRule> subsidyRules = selectRevSubsidyRuleByNow();
        subsidyRules.forEach(subsidyRule -> {
            List<User> users = selectUsersByDeptId(subsidyRule.getDeptId());
            List<Integer> userIds = users.stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(userIds)) {
                List<CpuCard> cpuCards = selectCpuCardsByUserIds(userIds);
                Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>>
                        triplet = buildSubsidy(subsidyRule.getSubsidyAmount(), cpuCards,
                        Subsidies.ADDUP);
                try {
                    saveAutomaticSubsidy(triplet);
                } catch (Exception ex) {
                    log.error("subsidyRules:" + subsidyRule.getId() + ",error", ex);
                }
            }
        });
    }

    @Scheduled(cron = "0 0 1 26 * ?")
    public void automaticSubsidyByReset() {
        List<Dept> depts = deptService.getDeptByConsumeIdentity(ConsumeIdentity.E.getCode());
        if (!org.springframework.util.CollectionUtils.isEmpty(depts)) {
            List<Integer> deptIds = depts.stream().map(Dept::getId).collect(Collectors.toList());
            List<User> users = userService.searchByDeptIds(deptIds);
            if (!org.springframework.util.CollectionUtils.isEmpty(users)) {
                List<Integer> userIds = users.stream().map(User::getId).collect(Collectors.toList());
                List<CpuCard> cpuCards = selectCpuCardsByUserIds(userIds);
                Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>>
                        triplet = buildSubsidy(RESET_SUBSIDY_AMOUNT, cpuCards,
                        Subsidies.RESET);
                try {
                    saveAutomaticSubsidy(triplet);
                } catch (Exception ex) {
                    log.error("ConsumeIdentity:" + ConsumeIdentity.E.getCode() + ",error", ex);
                }
            }
        }
    }

    @Override
    @Transactional
    public RespBatchImportSubsidyDto batchImportSubsidy(MultipartFile file) {
        RespBatchImportSubsidyDto respBatchImportSubsidyDto = new RespBatchImportSubsidyDto();
        List<BatchImportSubsidyDto> batchRechargeDataDtoList = Lists.newArrayList();
        List<BatchImportSubsidyDto> infoErrorList = Lists.newArrayList();

        List<BatchImportSubsidyDto> batchImportSubsidyDtos = readWorkbook(file);
        batchImportSubsidyDtos.forEach(batchImportSubsidyDto -> {
            if (StringUtils.isBlank(batchImportSubsidyDto.getErrorMsg())) {
                QueryWrapper<CpuCard> wrapper = new QueryWrapper<CpuCard>()
                        .eq("card_serialNo", batchImportSubsidyDto.getCardSerialNo())
                        .eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode())
                        .eq("if_used", 0);
                CpuCard cpuCard = cpuCardMapper.selectOne(wrapper);
                if (Objects.nonNull(cpuCard)) {
                    Triplet<CpuCard, SubsidyRecord, ChangeAmount> triplet =
                            subsidy(batchImportSubsidyDto.getSubsidyAmt(), cpuCard, SubsidyType.BULKIMPORT,
                                    Subsidies.ADDUP.getSubsidyStrategy());
                    cpuCardMapper.updateById(triplet.getValue0());
                    subsidyRecordMapper.insert(triplet.getValue1());
                    changeAmountMapper.insert(triplet.getValue2());
                    batchRechargeDataDtoList.add(batchImportSubsidyDto);
                } else {
                    batchImportSubsidyDto.setErrorMsg("卡编号不存在或不可用");
                    infoErrorList.add(batchImportSubsidyDto);
                }
            } else {
                infoErrorList.add(batchImportSubsidyDto);
            }
        });

        respBatchImportSubsidyDto.setBatchRechargeDataDtoList(batchRechargeDataDtoList);
        respBatchImportSubsidyDto.setSuccessCount(batchRechargeDataDtoList.size());
        respBatchImportSubsidyDto.setInfoErrorList(infoErrorList);
        respBatchImportSubsidyDto.setErrorCount(infoErrorList.size());
        BigDecimal totalAmt = batchRechargeDataDtoList.stream().map(BatchImportSubsidyDto::getSubsidyAmt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        respBatchImportSubsidyDto.setTotalAmt(totalAmt);
        return respBatchImportSubsidyDto;
    }

    /**
     * 读取Excel
     */
    private List<BatchImportSubsidyDto> readWorkbook(MultipartFile file) {
        //获取文件的名字
        String originalFilename = file.getOriginalFilename();
        Workbook workbook;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new WisdomParkException(ResponseData.STATUS_CODE_605, "文件格式不正确");
            }
            return readSheet(workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    /**
     * 读取所有补助数据
     */
    private List<BatchImportSubsidyDto> readSheet(Workbook workbook) {
        List<BatchImportSubsidyDto> batchImportSubsidyDtos = Lists.newArrayList();
        int sheetNumber = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNumber; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int rowNumber = sheet.getLastRowNum();
            for (int j = 2; j <= rowNumber; j++) {
                batchImportSubsidyDtos.add(readRow(sheet, j));
            }
        }
        return batchImportSubsidyDtos;
    }

    /**
     * 读取单行补助
     */
    private BatchImportSubsidyDto readRow(Sheet sheet, int rowNumber) {
        BatchImportSubsidyDto batchImportSubsidyDto = new BatchImportSubsidyDto();
        Row row = sheet.getRow(rowNumber);
        try {
            batchImportSubsidyDto.setRowNum(rowNumber + 1);
            batchImportSubsidyDto.setSheet(sheet.getSheetName());

            String cardSerialNo = null;
            Cell cell = row.getCell(0);
            if (Objects.nonNull(cell)) {
                cardSerialNo = cell.getStringCellValue();
            }
            if (StringUtils.isBlank(cardSerialNo)) {
                batchImportSubsidyDto.setErrorMsg("CPU卡序列号为空");
                return batchImportSubsidyDto;
            } else {
                batchImportSubsidyDto.setCardSerialNo(StringUtils.trim(cardSerialNo));
            }
        } catch (IllegalStateException ex) {
            batchImportSubsidyDto.setErrorMsg("CPU卡序列号不正确");
            return batchImportSubsidyDto;
        }
        try {
            Double subsidyAmt = null;
            Cell cell = row.getCell(3);
            if (Objects.nonNull(cell)) {
                subsidyAmt = cell.getNumericCellValue();
            }
            if (Objects.isNull(subsidyAmt)) {
                batchImportSubsidyDto.setErrorMsg("补助金额为空");
                return batchImportSubsidyDto;
            } else if (subsidyAmt <= 0) {
                batchImportSubsidyDto.setErrorMsg("补助金额不大于0");
                return batchImportSubsidyDto;
            } else {
                batchImportSubsidyDto.setSubsidyAmt(new BigDecimal(subsidyAmt));
            }
        } catch (IllegalStateException ex) {
            batchImportSubsidyDto.setErrorMsg("补助金额不正确");
            return batchImportSubsidyDto;
        }
        return batchImportSubsidyDto;
    }

    /**
     * 保存自动补助信息
     *
     * @param triplet 补助信息
     */
    @Transactional
    public void saveAutomaticSubsidy(Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>> triplet) {
        if (CollectionUtils.isNotEmpty(triplet.getValue0())) {
            cpuCardMapper.updateBatchSubsidy(triplet.getValue0());
            subsidyRecordMapper.insertBatch(triplet.getValue1());
            changeAmountMapper.insertBatch(triplet.getValue2());
        }
    }

    /**
     * 保存补助记录
     *
     * @param amount        补助金额
     * @param cpuCardBefore 变动前CPU卡
     * @param cpuCardAfter  变动后CPU卡
     */
    private void saveSubsidyRecord(BigDecimal amount, CpuCard cpuCardBefore, CpuCard cpuCardAfter) {
        SubsidyRecord subsidyRecord = SubsidyRecordConverter.converter(amount, cpuCardBefore,
                cpuCardAfter, SubsidyType.MANUAL);
        subsidyRecordMapper.insert(subsidyRecord);
    }

    /**
     * 保存金额变动记录
     *
     * @param amount       变动金额
     * @param changeBefore 改变前对象
     * @param changeAfter  改变后对象
     */
    private void saveChangeAmount(BigDecimal amount, CpuCard changeBefore, CpuCard changeAfter) {
        ChangeAmount changeAmount = ChangeAmountConverter.
                changeAmount(amount, AmountChangeType.SUBSIDIES, changeBefore, changeAfter);
        changeAmountMapper.insert(changeAmount);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<SubsidyRule> selectRevSubsidyRuleByNow() {
        LocalDate localDate = LocalDate.now();
        QueryWrapper<SubsidyRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subsidy_time", localDate.getDayOfMonth());
        queryWrapper.eq("subsidy_status", SubsidyStatus.START.getCode());
        return subsidyRuleMapper.selectList(queryWrapper);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<User> selectUsersByDeptId(Integer deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", deptId);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 根据人员ID获取CPU卡
     *
     * @return 补助规则
     */
    private List<CpuCard> selectCpuCardsByUserIds(List<Integer> userIds) {
        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        queryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
        queryWrapper.eq("if_used", 0);
        return cpuCardMapper.selectList(queryWrapper);
    }

    /**
     * 构建补助信息
     *
     * @param subsidyAmount 自动补助规则
     * @param cpuCards      CPU卡
     * @return 补助信息
     */
    private Triplet<List<CpuCard>, List<SubsidyRecord>, List<ChangeAmount>>
    buildSubsidy(BigDecimal subsidyAmount, List<CpuCard> cpuCards, Subsidies subsidies) {
        List<CpuCard> changeAfters = Lists.newArrayList();
        List<SubsidyRecord> subsidyRecords = Lists.newArrayList();
        List<ChangeAmount> changeAmounts = Lists.newArrayList();
        cpuCards.forEach(cpuCard -> {
            Triplet<CpuCard, SubsidyRecord, ChangeAmount> triplet =
                    subsidy(subsidyAmount, cpuCard, SubsidyType.AUTOMATIC, subsidies.getSubsidyStrategy());
            changeAfters.add(triplet.getValue0());
            subsidyRecords.add(triplet.getValue1());
            changeAmounts.add(triplet.getValue2());
        });
        return new Triplet<>(changeAfters, subsidyRecords, changeAmounts);
    }

    /**
     * 对单个卡进行补助
     *
     * @param subsidyAmount   补助金额
     * @param cpuCard         变动前CPU卡
     * @param subsidyType     补助类型
     * @param subsidyStrategy 补助策略
     * @return 补助结果
     */
    private Triplet<CpuCard, SubsidyRecord, ChangeAmount> subsidy(
            BigDecimal subsidyAmount, CpuCard cpuCard, SubsidyType subsidyType, SubsidyStrategy subsidyStrategy) {
        CpuCard changeAfter = new CpuCard();
        BeanUtils.copyProperties(cpuCard, changeAfter);
        changeAfter.setSubsidyBalance(subsidyStrategy.subsidy(cpuCard.getSubsidyBalance(),
                subsidyAmount));
        SubsidyRecord subsidyRecord = SubsidyRecordConverter.converter
                (subsidyAmount, cpuCard, changeAfter, subsidyType);
        ChangeAmount changeAmount = ChangeAmountConverter.
                changeAmount(subsidyAmount,
                        AmountChangeType.SUBSIDIES, cpuCard, changeAfter);
        return new Triplet<>(changeAfter, subsidyRecord, changeAmount);
    }

}