package com.eco.wisdompark.controller;

import com.eco.wisdompark.common.dto.ResponseData;
import com.eco.wisdompark.common.utils.HttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  蓝牙开门
 * </p>
 *
 * @author tianyu
 */

@RestController
@RequestMapping("api/bluetoothOpenDoor")
@Api(value = "蓝牙开门", description = "蓝牙开门相关API")
public class BluetoothOpenDoorController {



    @RequestMapping(value = "/sendOpenDoor", method = RequestMethod.POST)
    @ApiOperation(value = "蓝牙开门发送指令", httpMethod = "POST")
    public ResponseData<String> sendOpenDoor() {
       String result=  HttpClient.doPost("http://localhost:60001/wisdompark/api/system/now",null);

        return ResponseData.OK(result);
    }



}
