package com.eco.wisdompark.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.FileUtils;
import com.eco.wisdompark.common.utils.IdCardUtils;
import com.eco.wisdompark.common.utils.StringTools;
import com.eco.wisdompark.converter.req.CpuCardConverter;
import com.eco.wisdompark.converter.req.UserConverter;
import com.eco.wisdompark.converter.resp.RespActiveCpuCardDtoConverter;
import com.eco.wisdompark.converter.resp.RespLossQueryConfirmDtoConverter;
import com.eco.wisdompark.converter.resp.RespMakingCpuCardDtoConverter;
import com.eco.wisdompark.domain.dto.inner.InnerCpuCardInfoDto;
import com.eco.wisdompark.domain.dto.req.card.*;
import com.eco.wisdompark.domain.dto.req.dept.DeptAllDto;
import com.eco.wisdompark.domain.dto.req.user.GetUserDto;
import com.eco.wisdompark.domain.dto.req.user.SerianNoDto;
import com.eco.wisdompark.domain.dto.req.user.UpdateUserBalanceDto;
import com.eco.wisdompark.domain.dto.resp.*;
import com.eco.wisdompark.domain.model.CpuCard;
import com.eco.wisdompark.domain.model.Dept;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.enums.*;
import com.eco.wisdompark.mapper.CpuCardMapper;
import com.eco.wisdompark.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.eco.wisdompark.common.dto.ResponseData.STATUS_CODE_601;
import static com.eco.wisdompark.common.dto.ResponseData.STATUS_CODE_613;

/**
 * <p>
 * CPU卡 服务实现类
 * </p>
 *
 * @author litao
 * @since 2018-12-28
 */
@Slf4j
@Service
public class CpuCardServiceImpl extends ServiceImpl<CpuCardMapper, CpuCard> implements CpuCardService {

    @Autowired
    private UserService userService;

    @Autowired
    private RechargeRecordService rechargeRecordService;

    @Autowired
    private ChangeAmountService changeAmountService;

    @Autowired
    private CpuCardMapper cpuCardMapper;

    @Autowired
    private DeptService deptService;

    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

    /**
     * 文件上传地址
     */
    @Value("${batch.recharge.fileupload.path}")
    private static String UPLOAD_FILE_PATH;

    @Override
    @Transactional
    public RespMakingCpuCardDto makingCpuCard(MakingCpuCardDto makingCpuCardDto) {
//        // 1.校验用户是否存在
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("phone_num", makingCpuCardDto.getPhoneNum())
//                .or()
//                .eq("user_card_num", makingCpuCardDto.getUserCardNum());
//        List<User> userList = userService.list(queryWrapper);
//        if (!CollectionUtils.isEmpty(userList)) {
//            log.error("makingCpuCard phoneNum:{}, user already exist...", makingCpuCardDto.getPhoneNum());
//            throw new WisdomParkException(ResponseData.STATUS_CODE_600, "用户已存在，不能制卡");
//        }
        // 2.保存用户信息
        User user = saveUser(makingCpuCardDto.getUserName(),
                makingCpuCardDto.getUserCardNum(), makingCpuCardDto.getDeptId(), makingCpuCardDto.getPhoneNum());
        // 3.保存制卡信息
        Integer userId = user.getId();
        CpuCard cpuCard = saveCpuCardInfo(StringTools.cardDecimalToHexString(makingCpuCardDto.getCardId()),
                makingCpuCardDto.getCardSerialNo(), CardSource.MAKE_CARD, makingCpuCardDto.getDeposit(), new BigDecimal(0), userId);
        // 4.封装返回信息
        RespMakingCpuCardDto respMakingCpuCardDto = RespMakingCpuCardDtoConverter.create(cpuCard, user);
        return respMakingCpuCardDto;
    }


    @Override
    @Transactional
    public RespActiveCpuCardDto activeCpuCard(ActiveCpuCardDto activeCpuCardDto) {
        // 1.校验用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_num", activeCpuCardDto.getPhoneNum())
                .or()
                .eq("user_card_num", activeCpuCardDto.getUserCardNum());
        List<User> userList = userService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(userList)) {
            log.error("activeCpuCard user already exist... param:{}", JSON.toJSONString(activeCpuCardDto));
            throw new WisdomParkException(ResponseData.STATUS_CODE_600, "用户已存在，无法激活");
        }
        // 2.保存用户信息
        User user = saveUser(activeCpuCardDto.getUserName(),
                activeCpuCardDto.getUserCardNum(), activeCpuCardDto.getDeptId(), activeCpuCardDto.getPhoneNum());
        // 3.保存制卡信息
        CpuCard cpuCard = saveCpuCardInfo(StringTools.cardDecimalToHexString(activeCpuCardDto.getCardId()),
                activeCpuCardDto.getCardSerialNo(), CardSource.ACTIVATION, new BigDecimal(0), new BigDecimal(0), user.getId());
        // 4.封装返回信息
        RespActiveCpuCardDto respActiveCpuCardDto = RespActiveCpuCardDtoConverter.create(cpuCard, user);
        return respActiveCpuCardDto;
    }

    @Override
    public RespQueryCardInfoDto queryCardInfo(QueryCardInfoDto queryCardInfoDto) {
        RespQueryCardInfoDto respQueryCardInfoDto = queryCardInfoByCardId(queryCardInfoDto.getCardId());
        if (Objects.isNull(respQueryCardInfoDto)) {
            throw new WisdomParkException(STATUS_CODE_601, "无效的卡");
        }
        respQueryCardInfoDto.setDeptName(deptService.getDeptName(respQueryCardInfoDto.getDeptId()));
        return respQueryCardInfoDto;
    }

    @Override
    public RespQueryCardInfoListDto queryCardInfoList(LossQueryCardInfoDto lossQueryCardInfoDto) {
        RespQueryCardInfoListDto respQueryCardInfoListDto = new RespQueryCardInfoListDto();
        // 1.根据用户信息查询
        List<RespQueryCardInfoDto> cardInfoList = queryCardInfo(lossQueryCardInfoDto.getUserName(),
                lossQueryCardInfoDto.getPhoneNum(), lossQueryCardInfoDto.getDeptId());
        respQueryCardInfoListDto.setCardInfoList(cardInfoList);
        return respQueryCardInfoListDto;
    }

    @Override
    @Transactional
    public boolean rechargeSingle(RechargeCardDto rechargeCardDto) {
        // 1.校验CPU卡是否存在getUsers
        InnerCpuCardInfoDto innerCpuCardInfoDto = queryCardInfoByCardId(StringTools.cardDecimalToHexString(rechargeCardDto.getCardId()), null);
        if (innerCpuCardInfoDto == null) {
            throw new WisdomParkException(STATUS_CODE_601, "用户或卡信息不存在");
        }
        // 2.进行充值操作（变更卡余额 、保存充值记录、增加金额变更记录）
        return rechargeBalance(innerCpuCardInfoDto, StringTools.cardDecimalToHexString(rechargeCardDto.getCardId()), rechargeCardDto.getRechargeAmt(), rechargeCardDto.getRechargeWay());
    }


    @Override
    public RespRechargeBatchDataDto fileUpload(MultipartFile file) {
        RespRechargeBatchDataDto respRechargeBatchDataDto = new RespRechargeBatchDataDto();
        if (file.isEmpty()) {
            log.error("fileUpload file isEmpty...");
            return null;
        }
        boolean checkResult = checkExcelSize(file);
        if (!checkResult) {
            log.error("fileUpload limit 200... ERROR");
            throw new WisdomParkException(ResponseData.STATUS_CODE_608, "批量充值Excel文件单次限制200条");
        }

        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        log.info("fileUpload originalFileName:{}, size:{}", fileName, size);
        String uploadPath = UPLOAD_FILE_PATH;
        if (StringUtils.isEmpty(uploadPath)) {
            uploadPath = System.getProperty("user.dir") + File.separator + "upload" + File.separator + "file" + File.separator + "recharge" + File.separator + LocalDate.now() + File.separator;
            log.info("fileUpload path not config, use default:{}", uploadPath);
        }
        // 新文件名时间戳
        String newFileName = "recharge_" + System.currentTimeMillis() + SUFFIX_2007;
        File newFile = new File(uploadPath + newFileName);
        // 判断文件父目录是否存在
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(newFile);
            log.info("fileUpload success...");
            FileItem fileItem = FileUtils.createFileItem(newFile.getPath(), newFileName);
            MultipartFile xlsFile = new CommonsMultipartFile(fileItem);
            readExcel(xlsFile, respRechargeBatchDataDto);
            // 组装返回数据
            fileUploadRetData(respRechargeBatchDataDto);
            respRechargeBatchDataDto.setFileCode(newFileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respRechargeBatchDataDto;
    }

    @Override
    public RespQueryAmountDto queryAmount(QueryCardInfoDto queryCardInfoDto) {
        InnerCpuCardInfoDto cardInfoDto = queryCardInfoByCardId(queryCardInfoDto.getCardId(), null);
        BigDecimal amount = cardInfoDto.getRechargeBalance().add(cardInfoDto.getSubsidyBalance());
        RespQueryAmountDto dto = new RespQueryAmountDto();
        dto.setTotalBalance(amount);
        return dto;
    }

    @Override
    public int updateCpuCardBalance(Integer userId, BigDecimal rechargeBalance) {

        CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("user_id", userId));
        cpuCard.setRechargeBalance(rechargeBalance);
        baseMapper.updateById(cpuCard);
        return 0;
    }

    @Override
    public int updateCpuCardSBalance(Integer userId, BigDecimal subsidyBalance) {

        CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("user_id", userId));

        cpuCard.setSubsidyBalance(cpuCard.getSubsidyBalance().add(subsidyBalance));
        baseMapper.updateById(cpuCard);
        return 0;
    }

    /**
     * 校验Excel条数 每次上传不能大于200条
     *
     * @param file
     * @return
     */
    private boolean checkExcelSize(MultipartFile file) {
        //获取文件的名字
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new WisdomParkException(ResponseData.STATUS_CODE_605, "文件格式不正确");
            }
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            if (rows > 200) {
                log.info("checkExcelSize Excel rows:{} > 200", rows);
                return false;
            }
        } catch (Exception e) {
            log.error("readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }
        return true;
    }


    private void fileUploadRetData(RespRechargeBatchDataDto respRechargeBatchDataDto) {

        List<BatchRechargeDataDto> newBatchRechargeDataDtoList = Lists.newArrayList();
        List<BatchRechargeDataDto> successDtoList = respRechargeBatchDataDto.getBatchRechargeDataDtoList();
        List<BatchRechargeDataDto> infoErrorDtoList = respRechargeBatchDataDto.getInfoErrorList();
        int successCount = respRechargeBatchDataDto.getSuccessCount();
        int errorCount = respRechargeBatchDataDto.getErrorCount();

        if (CollectionUtils.isEmpty(successDtoList)) {
            log.error("fileUploadRetData source batchRechargeDataDtoList isEmpty...");
            respRechargeBatchDataDto.setBatchRechargeDataDtoList(newBatchRechargeDataDtoList);
            return;
        }
        for (BatchRechargeDataDto batchRechargeDataDto : successDtoList) {
            String cardSerialNo = batchRechargeDataDto.getCardSerialNo();
            CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("card_serialNo", cardSerialNo));
            if (cpuCard != null) {
                int userId = cpuCard.getUserId();
                User user = userService.getById(userId);
                batchRechargeDataDto.setUserName(user.getUserName());
                batchRechargeDataDto.setPhoneNum(user.getPhoneNum());
                batchRechargeDataDto.setDeptId(user.getDeptId());
                batchRechargeDataDto.setDeptName(deptService.getDeptName(user.getDeptId()));
                batchRechargeDataDto.setUserCardNum(IdCardUtils.idCardHidden(user.getUserCardNum()));
                newBatchRechargeDataDtoList.add(batchRechargeDataDto);
            } else {
                batchRechargeDataDto.setErrorMsg("CPU卡不存在");
                infoErrorDtoList.add(batchRechargeDataDto);
                errorCount++;
                successCount--;
            }
        }
        BigDecimal totalAmt = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(newBatchRechargeDataDtoList)) {
            totalAmt = newBatchRechargeDataDtoList.stream().map(BatchRechargeDataDto::getRechargeAmt).reduce(BigDecimal::add).get();
        }
        respRechargeBatchDataDto.setTotalAmt(totalAmt);
        respRechargeBatchDataDto.setBatchRechargeDataDtoList(newBatchRechargeDataDtoList);
        respRechargeBatchDataDto.setInfoErrorList(infoErrorDtoList);
        respRechargeBatchDataDto.setErrorCount(errorCount);
        respRechargeBatchDataDto.setSuccessCount(successCount);
    }


    /**
     * 读取Excel文件
     *
     * @param file
     */
    private void readExcel(MultipartFile file, RespRechargeBatchDataDto respRechargeBatchDataDto) {
        List<BatchRechargeDataDto> successBatchRechargeDataDtoList = new ArrayList<>();
        List<BatchRechargeDataDto> errorBatchRechargeDataDtoList = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;

        //获取文件的名字
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new WisdomParkException(ResponseData.STATUS_CODE_605, "文件格式不正确");
            }

            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int r = 1; r <= rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                // CPU卡序列号
                String cardSerialNo = null;
                // 充值金额
                Double rechargeAmt = null;
                // 充值方式
                String rechargeWay = null;

                if (!cellIsEmpty(row.getCell(0))) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    cardSerialNo = row.getCell(0).getStringCellValue();
                }
                if (!cellIsEmpty(row.getCell(1))) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
                    rechargeAmt = row.getCell(1).getNumericCellValue();
                }
                if (!cellIsEmpty(row.getCell(2))) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    rechargeWay = row.getCell(2).getStringCellValue();
                }

                BatchRechargeDataDto batchRechargeDataDto = new BatchRechargeDataDto(cardSerialNo, rechargeAmt != null ? new BigDecimal(rechargeAmt) : null, rechargeWay);

                if (StringUtils.isEmpty(cardSerialNo)) {
                    log.error("readExcel 第{}行数据异常，请检查...", r);
                    batchRechargeDataDto.setRowNum(r + 1);
                    batchRechargeDataDto.setErrorMsg("CPU卡序列号为空");
                    errorBatchRechargeDataDtoList.add(batchRechargeDataDto);
                    errorCount++;
                    continue;
                }
                if (rechargeAmt == null) {
                    log.error("readExcel 第{}行数据异常，请检查...", r);
                    batchRechargeDataDto.setRowNum(r + 1);
                    batchRechargeDataDto.setErrorMsg("充值金额为空");
                    errorBatchRechargeDataDtoList.add(batchRechargeDataDto);
                    errorCount++;
                    continue;
                }
                if (rechargeAmt <= 0) {
                    log.error("readExcel 第{}行数据异常，请检查...", r);
                    batchRechargeDataDto.setRowNum(r + 1);
                    batchRechargeDataDto.setErrorMsg("充值金额不合法");
                    errorBatchRechargeDataDtoList.add(batchRechargeDataDto);
                    errorCount++;
                    continue;
                }
                if (StringUtils.isEmpty(rechargeWay)) {
                    log.error("readExcel 第{}行数据异常，请检查...", r);
                    batchRechargeDataDto.setRowNum(r + 1);
                    batchRechargeDataDto.setErrorMsg("充值类型为空");
                    errorBatchRechargeDataDtoList.add(batchRechargeDataDto);
                    errorCount++;
                    continue;
                }
                RechargeWay rechargeWayEnum = RechargeWay.getByDescription(rechargeWay);
                if (rechargeWayEnum == null) {
                    log.error("readExcel 第{}行数据异常，请检查...", r);
                    batchRechargeDataDto.setRowNum(r + 1);
                    batchRechargeDataDto.setErrorMsg("充值类型不合法");
                    errorBatchRechargeDataDtoList.add(batchRechargeDataDto);
                    errorCount++;
                    continue;
                }
                batchRechargeDataDto.setRowNum(r + 1);
                successBatchRechargeDataDtoList.add(batchRechargeDataDto);
                successCount++;
            }
        } catch (Exception e) {
            log.error("readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }
        respRechargeBatchDataDto.setBatchRechargeDataDtoList(successBatchRechargeDataDtoList);
        respRechargeBatchDataDto.setInfoErrorList(errorBatchRechargeDataDtoList);
        respRechargeBatchDataDto.setSuccessCount(successCount);
        respRechargeBatchDataDto.setErrorCount(errorCount);
    }

    @Override
    public boolean rechargeBatch(String fileCode) {
        if (StringUtils.isEmpty(fileCode)) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_607, "批量充值文件不能为空");
        }
        String filePath = UPLOAD_FILE_PATH;
        if (StringUtils.isEmpty(filePath)) {
            filePath = System.getProperty("user.dir") + File.separator + "upload" + File.separator + "file" + File.separator + "recharge" + File.separator + LocalDate.now() + File.separator;
        }
        filePath += fileCode;
        FileItem fileItem = FileUtils.createFileItem(filePath, fileCode);

        MultipartFile xlsFile = new CommonsMultipartFile(fileItem);

        RespRechargeBatchDataDto respRechargeBatchDataDto = new RespRechargeBatchDataDto();
        readExcel(xlsFile, respRechargeBatchDataDto);

        // 进行充值操作
        if (!CollectionUtils.isEmpty(respRechargeBatchDataDto.getBatchRechargeDataDtoList())) {
            respRechargeBatchDataDto.getBatchRechargeDataDtoList().forEach(batchRechargeDataDto -> {
                CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("card_serialNo", batchRechargeDataDto.getCardSerialNo()));
                if (cpuCard != null) {
                    RechargeCardDto rechargeCardDto = new RechargeCardDto();
                    rechargeCardDto.setCardId(cpuCard.getCardId());
                    rechargeCardDto.setRechargeAmt(batchRechargeDataDto.getRechargeAmt());
                    rechargeForBath(rechargeCardDto, RechargeWay.getByDescription(batchRechargeDataDto.getRechargeWay()).getCode());
                }
            });
        }
        return true;
    }


    @Override
    public int updateCpuCard(CpuCard cpuCard) {
        if (cpuCard != null) {
            return baseMapper.updateById(cpuCard);
        }
        return 0;
    }

    /**
     * 查询 卡信息 内部使用
     *
     * @param cardId
     * @param param  没有实际意义，与另外一个方法区分
     * @return
     */
    public InnerCpuCardInfoDto queryCardInfoByCardId(String cardId, String param) {
        if (StringUtils.isEmpty(cardId)) {
            return null;
        }

        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        CpuCard cpuCard = getOne(queryWrapper);
        if (cpuCard == null) {
            log.info("queryCardInfoByCardId  inner cardId:{}, no data...", cardId);
            return null;
        }

        InnerCpuCardInfoDto cardInfoDto = new InnerCpuCardInfoDto();
        cardInfoDto.setCardId(cardId);
        cardInfoDto.setCardSerialNo(cpuCard.getCardSerialNo());
        cardInfoDto.setUserId(cpuCard.getUserId());
        cardInfoDto.setRechargeBalance(cpuCard.getRechargeBalance());
        cardInfoDto.setSubsidyBalance(cpuCard.getSubsidyBalance());
        return cardInfoDto;
    }

    @Override
    public RespQueryCardInfoDto queryCardInfoByCardId(String cardId) {
        if (StringUtils.isEmpty(cardId)) {
            return null;
        }

        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        List<CpuCard> cpuCards = list(queryWrapper);
        if (CollectionUtils.isEmpty(cpuCards)) {
            log.info("queryCardInfoByCardId cardId:{}, no data...", cardId);
            return null;
        }

        CpuCard cpuCard = cpuCards.get(0);
        Integer userId = cpuCard.getUserId();
        User user = userService.getById(userId);
        if (user != null) {
            // 封装返回信息
            RespQueryCardInfoDto respQueryCardInfoDto = userCardDataConvert(user, cpuCard);
            return respQueryCardInfoDto;
        }
        log.error("queryCardInfoByCardId cardId:{}, userId:{}, exists but user not exists ERROR ...", cardId, userId);
        return null;
    }

    @Override
    public List<CpuCard> getCpuCardByUserIds(List<Integer> userIds) {
        if (userIds != null && userIds.size() > 0) {
            QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
            queryWrapper.in("user_id", userIds);
            List<CpuCard> list = baseMapper.selectList(queryWrapper);
            return list;
        }
        return null;
    }

    @Override
    public CpuCard getCpuCarByUserId(Integer userId) {
        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
        queryWrapper.eq("user_id", userId);
        CpuCard cpuCard = baseMapper.selectOne(queryWrapper);
        return cpuCard;
    }

    @Override
    public Integer getUserId(String card_serialNo) {
        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_serialNo", card_serialNo);
        CpuCard cpuCard = baseMapper.selectOne(queryWrapper);
        if (cpuCard != null) {
            return cpuCard.getUserId();
        }
        return null;
    }


    /**
     * 单个人员 充值操作
     * (非共享数据且无并发)，所以无需加锁
     *
     * @param cardInfoDto
     * @param cardId
     * @param amount
     * @return
     */
    private boolean rechargeBalance(InnerCpuCardInfoDto cardInfoDto, String cardId, BigDecimal amount, int rechargeWay) {
        //  1.充值操作
        cpuCardMapper.recharge(cardId, amount);
        // 2.保存充值记录
        rechargeRecordService.saveRechargeRecord(cardInfoDto, amount, RechargeType.MANUAL, null, rechargeWay);
        // 3.保存金额变更记录
        changeAmountService.saveRechargeChanageAmountRecord(cardInfoDto, amount, AmountChangeType.TOP_UP);
        return true;
    }


    @Override
    public List<RespQueryCardInfoDto> queryCardInfo(String userName, String mobile, Integer deptId) {
        List<RespQueryCardInfoDto> cardInfoList = new ArrayList<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userName)) {
            queryWrapper.eq("user_name", userName);
        }
        if (!StringUtils.isEmpty(mobile)) {
            queryWrapper.eq("phone_num", mobile);
        }
        if (!StringUtils.isEmpty(deptId) && deptId > 0) {
            queryWrapper.eq("dept_id", deptId);
        }

        List<User> userList = userService.list(queryWrapper);
        log.info("queryCardInfo no data userName:{}, mobile:{}, deptId:{}, userList:{}",
                userName, mobile, deptId, JSON.toJSONString(userList));
        if (!CollectionUtils.isEmpty(userList)) {
            userList.forEach(user -> {
                Integer userId = user.getId();
                QueryWrapper<CpuCard> cpuCardQueryWrapper = new QueryWrapper<>();
                cpuCardQueryWrapper.eq("user_id", userId);
                cpuCardQueryWrapper.eq("report_loss_ststus", ReportLossStstus.IN_USE.getCode());
                cpuCardQueryWrapper.eq("del", YesNo.NO.getCode());
                CpuCard cpuCard = getOne(cpuCardQueryWrapper);
                if (cpuCard != null) {
                    RespQueryCardInfoDto respQueryCardInfoDto = userCardDataConvert(user, cpuCard);
                    if (respQueryCardInfoDto != null) {
                        cardInfoList.add(respQueryCardInfoDto);
                    }
                }
                log.error("queryCardInfo user no card ERROR... userId:{},reportLossStatus:{}, del:{}", userId, ReportLossStstus.IN_USE, YesNo.NO);
            });
        }
        return cardInfoList;
    }


    @Override
    public RespLossQueryConfirmDto queryCardInfo(LossQueryConfirmDto lossQueryConfirmDto) {
        if (lossQueryConfirmDto.getCardId() == null || lossQueryConfirmDto.getCardId() <= 0) {
            log.error("queryCardInfo RespLossQueryConfirmDto param Error... param:{}", lossQueryConfirmDto);
            throw new WisdomParkException(STATUS_CODE_601, "用户或卡信息不存在");
        }
        int cardId = lossQueryConfirmDto.getCardId();
        CpuCard cpuCard = baseMapper.selectById(cardId);
        if (cpuCard == null) {
            log.error("queryCardInfo RespLossQueryConfirmDto cpuCard not exists cardId:{}", cardId);
            throw new WisdomParkException(STATUS_CODE_601, "用户或卡信息不存在");
        }
        int userId = cpuCard.getUserId();
        User user = userService.queryByUserId(userId);
        if (user == null) {
            log.error("queryCardInfo RespLossQueryConfirmDto user not exists userId:{}", userId);
            throw new WisdomParkException(STATUS_CODE_601, "用户或卡信息不存在");
        }
        RespLossQueryConfirmDto respLossQueryConfirmDto = RespLossQueryConfirmDtoConverter.create(cpuCard, user);
        return respLossQueryConfirmDto;
    }

    /**
     * 组装人员卡信息
     *
     * @param user
     * @param cpuCard
     * @return
     */
    private RespQueryCardInfoDto userCardDataConvert(User user, CpuCard cpuCard) {
        RespQueryCardInfoDto respQueryCardInfoDto = new RespQueryCardInfoDto();
        respQueryCardInfoDto.setId(cpuCard.getId());
        respQueryCardInfoDto.setUserName(user.getUserName());
        respQueryCardInfoDto.setUserCardNum(user.getUserCardNum());
        respQueryCardInfoDto.setDeptId(user.getDeptId());
        respQueryCardInfoDto.setPhoneNum(user.getPhoneNum());
        BigDecimal rechargeBalance = cpuCard.getRechargeBalance() != null ? cpuCard.getRechargeBalance() : new BigDecimal(0);
        BigDecimal subsidyBalance = cpuCard.getSubsidyBalance() != null ? cpuCard.getSubsidyBalance() : new BigDecimal(0);
        respQueryCardInfoDto.setRechargeBalance(rechargeBalance);
        respQueryCardInfoDto.setSubsidyBalance(cpuCard.getSubsidyBalance());
        respQueryCardInfoDto.setTotalBalance(rechargeBalance.add(subsidyBalance));
        respQueryCardInfoDto.setReportLossStstus(cpuCard.getReportLossStstus());
        return respQueryCardInfoDto;
    }


    /**
     * 保存用户信息入库
     *
     * @param userName
     * @param userCardNum
     * @param deptId
     * @param phoneNum
     * @return
     */
    public User saveUser(String userName, String userCardNum, Integer deptId, String phoneNum) {
        User user = UserConverter.create(userName, userCardNum, deptId, phoneNum);
        boolean res = userService.save(user);
        log.info("saveUser reqParam:{}, res:{}", JSON.toJSONString(user), res);
        return user;
    }


    private CpuCard saveCpuCardInfo(String cardId, String cardSerialNo,
                                    CardSource cardSource, BigDecimal deposit, BigDecimal recharge, Integer userId) {
        // 校验CPU卡物理id是否存在
        boolean isExist = queryCardInfoIsExist(cardId, cardSerialNo);
        if (isExist) {
            log.error("saveCpuCardInfo cardId:{} or cardSerialNo:{} exists...",
                    cardId, cardSerialNo);
            throw new WisdomParkException(ResponseData.STATUS_CODE_602, "卡ID或卡序列号已存在");
        }

        CpuCard cpuCard = CpuCardConverter.create(userId, cardId, cardSerialNo,
                CardType.CPU, cardSource, deposit,
                recharge, new BigDecimal(0));

        boolean res = save(cpuCard);
        log.info("saveCpuCardInfo cardSerialNo:{}, res:{}", cardSerialNo, res);
        return cpuCard;
    }


    @Override
    public boolean queryCardInfoIsExist(String cardId, String cardSerialNo) {
        QueryWrapper<CpuCard> queryWrapper = new QueryWrapper();
        queryWrapper.eq("card_id", cardId)
                .or()
                .eq("card_serialNo", cardSerialNo);
        List<CpuCard> cpuCardList = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(cpuCardList)) {
            return true;
        }
        return false;
    }


    /**
     * 批量制卡
     *
     * @param file
     * @return
     */
    @Override
    public BatchMarkingCardRespDto batchMakingCard(MultipartFile file) {
        BatchMarkingCardRespDto batchMarkingCardRespDto = new BatchMarkingCardRespDto();
        if (file.isEmpty()) {
            log.error("batch_making_card fileUpload file isEmpty...");
            throw new WisdomParkException(ResponseData.STATUS_CODE_612, "批量制卡Excel文件为空");
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        log.info("batch_making_card fileUpload originalFileName:{}, size:{}", fileName, size);
        String uploadPath = UPLOAD_FILE_PATH;
        if (StringUtils.isEmpty(uploadPath)) {
            uploadPath = System.getProperty("user.dir") + File.separator + "upload" + File.separator + "file" + File.separator + "making_card" + File.separator + LocalDate.now() + File.separator;
            log.info("batch_making_card fileUpload path not config, use default:{}", uploadPath);
        }
        // 新文件名时间戳
        String newFileName = "making_card" + System.currentTimeMillis() + SUFFIX_2007;
        File newFile = new File(uploadPath + newFileName);
        // 判断文件父目录是否存在
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(newFile);
            log.info("batch_making_card fileUpload success...");
            FileItem fileItem = FileUtils.createFileItem(newFile.getPath(), newFileName);
            MultipartFile xlsFile = new CommonsMultipartFile(fileItem);
            batchMarkingCardRespDto = makingCardReadExcel(xlsFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batchMarkingCardRespDto;
    }

    @Override
    public Integer updateUserBalance(UpdateUserBalanceDto updateUserBalanceDto) {
        CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("user_id", updateUserBalanceDto.getId()));
        if (cpuCard == null) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_600, "未找到用户");
        }
        cpuCard.setSubsidyBalance(updateUserBalanceDto.getSubsidyBalance());
        cpuCard.setRechargeBalance(updateUserBalanceDto.getRechargeBalance());
        Integer result = baseMapper.updateById(cpuCard);
        return result;
    }

    /**
     * 读取制卡Excel文件
     *
     * @param file
     */
    private BatchMarkingCardRespDto makingCardReadExcel(MultipartFile file) {
        BatchMarkingCardRespDto batchMarkingCardRespDto = new BatchMarkingCardRespDto();
        Map<Integer, String> errorMap = new HashMap<>();
        int successCount = 0;
        int errorCount = 0;

        //获取文件的名字
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new WisdomParkException(ResponseData.STATUS_CODE_605, "文件格式不正确");
            }

            // 第四行开始读
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int r = 3; r <= rows; r++) {
                try {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    BatchMakingCpuCardDto batchMakingCpuCardDto = new BatchMakingCpuCardDto();
                    if (cellIsEmpty(row.getCell(1))) {
                        errorMap.put(r + 1, "姓名为空");
                        errorCount++;
                        continue;
                    }
                    if (cellIsEmpty(row.getCell(2))) {
                        errorMap.put(r + 1, "组织架构为空");
                        errorCount++;
                        continue;
                    }
                    if (cellIsEmpty(row.getCell(6))) {
                        errorMap.put(r + 1, "卡编号为空");
                        errorCount++;
                        continue;
                    }
                    if (cellIsEmpty(row.getCell(7))) {
                        errorMap.put(r + 1, "卡id为空");
                        errorCount++;
                        continue;
                    }
                    String deptname = row.getCell(2).getStringCellValue();
                    Integer deptid = getdeptId(deptname);
                    if (deptid == 0) {
                        errorMap.put(r + 1, "组织架构不存在");
                        errorCount++;
                        continue;
                    }
                    batchMakingCpuCardDto.setDeptId(deptid);
                    batchMakingCpuCardDto.setUserName(row.getCell(1).getStringCellValue().replace(" ", ""));
                    batchMakingCpuCardDto.setUserCardNum(row.getCell(3).getStringCellValue());
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    batchMakingCpuCardDto.setPhoneNum(row.getCell(4).getStringCellValue());
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    batchMakingCpuCardDto.setCardSerialNo(row.getCell(6).getStringCellValue());
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    batchMakingCpuCardDto.setCardId(row.getCell(7).getStringCellValue());

                    if (!cellIsEmpty(row.getCell(8))) {
                        batchMakingCpuCardDto.setRechargeBalance(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                    }
                    // 入库
                    batchMakingCpuCard(batchMakingCpuCardDto);

                } catch (WisdomParkException e) {
                    errorMap.put(r + 1, e.getMessage());
                    errorCount++;
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMap.put(r + 1, "解析异常");
                    errorCount++;
                    continue;
                }
                successCount++;
            }
            batchMarkingCardRespDto.setErrorCount(errorCount);
            batchMarkingCardRespDto.setSuccessCount(successCount);
            batchMarkingCardRespDto.setErrorRowInfo(errorMap);

        } catch (Exception e) {
            log.error("batch_making_card readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }
        return batchMarkingCardRespDto;
    }

    private boolean cellIsEmpty(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }
        return false;
    }

    private Integer getdeptId(String deptname) {
        List<DeptAllDto> list = deptService.getDeptAll();
        for (DeptAllDto dept : list) {
            for (DeptAllDto d : dept.getChildren()) {
                if (d.getLabel().equals(deptname)) {
                    return d.getValue();
                }
            }
        }
        return 0;

    }

    private void batchMakingCpuCard(BatchMakingCpuCardDto batchMakingCpuCardDto) {
        // 保存用户信息
        User user = saveUser(batchMakingCpuCardDto.getUserName(),
                batchMakingCpuCardDto.getUserCardNum(), batchMakingCpuCardDto.getDeptId(), batchMakingCpuCardDto.getPhoneNum());
        // 保存制卡信息
        Integer userId = user.getId();
        saveCpuCardInfo(StringTools.cardDecimalToHexString(batchMakingCpuCardDto.getCardId()),
                batchMakingCpuCardDto.getCardSerialNo(), CardSource.MAKE_CARD, BigDecimal.ZERO, batchMakingCpuCardDto.getRechargeBalance(), userId);
    }

    private boolean rechargeForBath(RechargeCardDto rechargeCardDto, int rechargeWay) {
        // 1.校验CPU卡是否存在getUsers
        InnerCpuCardInfoDto innerCpuCardInfoDto = queryCardInfoByCardId(rechargeCardDto.getCardId(), null);
        if (innerCpuCardInfoDto == null) {
            throw new WisdomParkException(STATUS_CODE_601, "用户或卡信息不存在");
        }
        // 2.进行充值操作（变更卡余额 、保存充值记录、增加金额变更记录）
        return rechargeBalance(innerCpuCardInfoDto, rechargeCardDto.getCardId(), rechargeCardDto.getRechargeAmt(), rechargeWay);
    }

    @Override
    @Transactional
    public boolean startStop(int ifUsed, String cardId) {
        int num = cpuCardMapper.startStop(ifUsed, cardId);
        if (num != 1) {
            throw new WisdomParkException(STATUS_CODE_613, "数据库操作异常");
        }
        return true;
    }

    @Override
    public RespCpuCardAndUserInfoDto getCpuCardAndUserInfo(SerianNoDto serianNoDto) {
        RespCpuCardAndUserInfoDto result = new RespCpuCardAndUserInfoDto();
        CpuCard cpuCard = baseMapper.selectOne(new QueryWrapper<CpuCard>().eq("card_serialNo", serianNoDto.getCardSerialNo()));
        if (cpuCard == null) {
            throw new WisdomParkException(ResponseData.STATUS_CODE_600, "未找到用户");
        }
        result.setId(cpuCard.getId());
        result.setCardSerialNo(cpuCard.getCardSerialNo());
        result.setRechargeBalance(cpuCard.getRechargeBalance());
        result.setSubsidyBalance(cpuCard.getSubsidyBalance());
        if (cpuCard.getUserId() != null && cpuCard.getUserId() > 0) {
           User user= userService.queryByUserId(cpuCard.getUserId());
           if(user!=null){
               result.setUserName(user.getUserName());
               result.setDeptName(deptService.getDeptName(user.getDeptId()));
           }
        }

        return result;
    }

    @Override
    public Integer del(GetUserDto getUserDto) {
        CpuCard cpuCard=baseMapper.selectById(getUserDto.getId());
        if(cpuCard!=null){

            userService.delUserById(cpuCard.getUserId());

        }

        return baseMapper.deleteById(cpuCard.getId());
    }

}