package com.eco.wisdompark.controller;

import com.alibaba.fastjson.JSONObject;
import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.exceptions.WisdomParkException;
import com.eco.wisdompark.common.utils.HttpClient;
import com.eco.wisdompark.domain.dto.req.dept.DeptAllDto;
import com.eco.wisdompark.domain.dto.req.user.UserDto;
import com.eco.wisdompark.domain.dto.resp.BatchRechargeDataDto;
import com.eco.wisdompark.domain.model.User;
import com.eco.wisdompark.service.CpuCardService;
import com.eco.wisdompark.service.DeptService;
import com.eco.wisdompark.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 蓝牙开门
 * </p>
 *
 * @author tianyu`
 */

@RestController
@RequestMapping("api/bluetoothOpenDoor")
@Api(value = "蓝牙开门", description = "蓝牙开门相关API")
@Slf4j
public class BluetoothOpenDoorController {


    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;
    @Autowired
    private CpuCardService cpuCardService;


    @RequestMapping(value = "/sendOpenDoor", method = RequestMethod.POST)
    @ApiOperation(value = "批量创建用户", httpMethod = "POST")
    public ResponseData<String> sendOpenDoor(@RequestParam("file") MultipartFile file) {
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
            List<DeptAllDto> list = deptService.getDeptAll();

            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int r = 0; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                String name = row.getCell(1).getStringCellValue();
                log.error(" name:{}, ...", name);

                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                String phone = row.getCell(5).getStringCellValue();
                log.error(" phone:{}, ...", phone);

                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                String userCardNum=row.getCell(7).getStringCellValue();
                log.error(" userCardNum:{}, ...", userCardNum);

                String deptname = row.getCell(9).getStringCellValue();
                Integer deptid = getdeptId(list, deptname);
                log.error(" deptname:{}, ...", deptname);
                log.error(" deptid:{}, ...", deptid);



                row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                String cardnum = row.getCell(14).getStringCellValue();
                log.error(" cardnum:{}, ...", cardnum);
                row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                String cardId = row.getCell(17).getStringCellValue();
                log.error(" cardId:{}, ...", cardId);
                if (StringUtils.isNotBlank(cardId)) {

                    JSONObject param = new JSONObject();
                    param.put("cardId", cardId);
                    param.put("cardSerialNo", cardnum);
                    param.put("deposit", 20);
                    param.put("deptId", deptid);
                    param.put("phoneNum", phone);
                    param.put("userCardNum",userCardNum);
                    param.put("userName", name);
                    log.error("param :{}", param.toJSONString());

                    BufferedReader reader = null;
                    URL url = new URL("http://localhost:60001/wisdompark/api/cpu-card/making");// 创建连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
                    connection.setRequestMethod("POST"); // 设置请求方式
                    // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
                    connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
                    connection.connect();
                    //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

                    out.append(param.toJSONString());
                    out.flush();
                    out.close();
                    // 读取响应
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String line;
                    String res = "";
                    while ((line = reader.readLine()) != null) {
                        res += line;
                    }
                    reader.close();
                }
            }
        } catch (Exception e) {
            log.error("readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }


        return ResponseData.OK("");
    }


//    @RequestMapping(value = "/sendOpenDoor", method = RequestMethod.POST)
//    @ApiOperation(value = "批量创建用户", httpMethod = "POST")
//    public ResponseData<String> sendOpenDoor(@RequestParam("file") MultipartFile file) {
//        String originalFilename = file.getOriginalFilename();
////        Workbook workbook = null;
////        try {
////            if (originalFilename.endsWith(SUFFIX_2003)) {
////                workbook = new HSSFWorkbook(file.getInputStream());
////            } else if (originalFilename.endsWith(SUFFIX_2007)) {
////                workbook = new XSSFWorkbook(file.getInputStream());
////            } else {
////                throw new WisdomParkException(ResponseData.STATUS_CODE_605, "文件格式不正确");
////            }
////            List<DeptAllDto> list = deptService.getDeptAll();
////
////            Sheet sheet = workbook.getSheetAt(0);
////            int rows = sheet.getLastRowNum();
////            for (int r = 2; r <= sheet.getLastRowNum(); r++) {
////                Row row = sheet.getRow(r);
////                if (row == null) {
////                    continue;
////                }
////                String name = row.getCell(1).getStringCellValue();
////                log.error(" name:{}, ...", name);
////
////                String deptname = row.getCell(9).getStringCellValue();
////                Integer deptid = getdeptId(list, deptname);
////                log.error(" deptname:{}, ...", deptname);
////                log.error(" deptid:{}, ...", deptid);
////
////                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
////                String cardnum = row.getCell(7).getStringCellValue();
////                log.error(" cardnum:{}, ...", cardnum);
////
////
////                row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
////                String cardId = row.getCell(17).getStringCellValue();
////                log.error(" cardId:{}, ...", cardId);
////                if (StringUtils.isNotBlank(cardId)) {
////
////                    JSONObject param = new JSONObject();
////                    param.put("cardId", cardId);
////                    param.put("cardSerialNo", cardnum);
////                    param.put("deposit", 20);
////                    param.put("deptId", deptid);
////                    param.put("phoneNum", "17301125534");
////                    param.put("userCardNum", "220887165478533521");
////                    param.put("userName", name);
////                    log.error("param :{}", param.toJSONString());
////
////                    BufferedReader reader = null;
////                    URL url = new URL("http://localhost:60001/wisdompark/api/cpu-card/making");// 创建连接
////                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////                    connection.setDoOutput(true);
////                    connection.setDoInput(true);
////                    connection.setUseCaches(false);
////                    connection.setInstanceFollowRedirects(true);
////                    connection.setRequestMethod("POST"); // 设置请求方式
////                    // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
////                    connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
////                    connection.connect();
////                    //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
////                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
////
////                    out.append(param.toJSONString());
////                    out.flush();
////                    out.close();
////                    // 读取响应
////                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
////                    String line;
////                    String res = "";
////                    while ((line = reader.readLine()) != null) {
////                        res += line;
////                    }
////                    reader.close();
////
////                }
////            }
////        } catch (Exception e) {
////            log.error("readExcel fileName:{}, Exception...", originalFilename);
////            e.printStackTrace();
////            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
////        }
////
//
//        return ResponseData.OK("");
//    }


    private Object getRightTypeCell(Cell cell) {
        Object object = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: {
                object = cell.getStringCellValue();
                break;
            }
            case Cell.CELL_TYPE_NUMERIC: {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object = cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_FORMULA: {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object = cell.getNumericCellValue();
                break;
            }

            case Cell.CELL_TYPE_BLANK: {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                object = cell.getStringCellValue();
                break;
            }
        }
        return object;
    }

    private Integer getdeptId(List<DeptAllDto> list, String deptname) {

        for (DeptAllDto dept : list) {

            for (DeptAllDto d : dept.getChildren()) {

                if (d.getLabel().equals(deptname)) {

                    return d.getValue();

                }

            }


        }


        return 0;

    }

    @RequestMapping(value = "/updateB", method = RequestMethod.POST)
    @ApiOperation(value = "更新余额", httpMethod = "POST")
    public ResponseData<String> updateB(@RequestParam("file") MultipartFile file) {
        List<User> list = userService.getAllUser();
        log.error(" 获取全部用户数据:{}, ...", list);
        List<UserDto> userDtos = this.getUserDtosw(file);
        log.error(" 获取全部excel数据:{}, ...", userDtos);
        for (User u : list) {
            for (UserDto dto : userDtos) {
                if (u.getUserName().equals(dto.getUserName())) {
                    log.error(" 用户名:{}, ...", u.getUserName());
                    cpuCardService.updateCpuCardSBalance(u.getId(),dto.getSubsidyBalance());

                    log.error(" 更新完成:-----------------------------------------");
                }
            }

        }
        return ResponseData.OK("");

    }

    private List<UserDto> getUserDtos(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        List<UserDto> result = new ArrayList<>();
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

            for (int r = 3; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                UserDto dto = new UserDto();
                String name = row.getCell(1).getStringCellValue();
                log.error(" name:{}, ...", name);
                dto.setUserName(name);


                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                String jine = row.getCell(5).getStringCellValue();
                log.error(" jine:{}, ...", jine);
                dto.setRechargeBalance(new BigDecimal(jine));
                result.add(dto);


            }
        } catch (Exception e) {
            log.error("readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }
        return result;

    }

    private List<UserDto> getUserDtosw(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        List<UserDto> result = new ArrayList<>();
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

            for (int r = 3; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                UserDto dto = new UserDto();
                String name = row.getCell(1).getStringCellValue();
                log.error(" name:{}, ...", name);
                dto.setUserName(name);

                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                String jine = row.getCell(7).getStringCellValue();
                log.error(" jine:{}, ...", jine);
                dto.setSubsidyBalance(new BigDecimal(jine));
                result.add(dto);


            }
        } catch (Exception e) {
            log.error("readExcel fileName:{}, Exception...", originalFilename);
            e.printStackTrace();
            throw new WisdomParkException(ResponseData.STATUS_CODE_606, "文件读取异常");
        }
        return result;

    }


}
