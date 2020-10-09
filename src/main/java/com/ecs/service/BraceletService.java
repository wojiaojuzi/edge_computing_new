package com.ecs.service;

import com.ecs.mapper.BraceletMapper;
import com.ecs.mapper.VervelMapper;
import com.ecs.model.Bracelet;
import com.ecs.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhaoone on 2019/11/4
 **/
@Service
public class BraceletService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";
    private final BraceletMapper braceletMapper;
    private final DeviceService deviceService;
    private final VervelMapper vervelMapper;

    @Autowired
    public BraceletService(BraceletMapper braceletMapper, DeviceService deviceService, VervelMapper vervelMapper) {
        this.braceletMapper = braceletMapper;
        this.deviceService = deviceService;
        this.vervelMapper = vervelMapper;
    }

    /*public void updateBraceletStatus(Boolean deviceStatus, String braceletNo){
        braceletMapper.updateDeviceStatusByBraceletNo(deviceStatus, braceletNo);
        Device device = deviceService.getByDeviceNo(getDeviceNoByBraceletNo(braceletNo));
        if(deviceStatus == true)
            deviceLogsService.insertRecord(device, "bracelet", "设备重连");
        else    deviceLogsService.insertRecord(device, "bracelet", "设备掉线");
    }*/

    public String getDeviceNoByBraceletNo(String braceletNo){
        return braceletMapper.getByBraceletNo(braceletNo).getDeviceNo();
    }

    public Bracelet getByDeviceNo(String deviceNo){
        return braceletMapper.getBraceletByDeviceNo(deviceNo);
    }

    public void updateDeviceNoByBraceletNo(String braceletNo,String deviceNo){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        braceletMapper.updateDeviceNoByBraceletNo(deviceNo,braceletNo,createAt);
    }

    public String getBraceletNoByDeviceNo(String deviceNo){
        return braceletMapper.getBraceletNoByDeviceNo(deviceNo);
    }

    /*public String getPrisonerIdByBraceletNo(String braceletNo){
        return braceletMapper.getPrisonerIdByBraceletNo(braceletNo);
    }*/

    /*public void updatePrisonerIdByBraceletNo(String braceletNo, String prisonerId){
        Bracelet bracelet= new Bracelet();
        bracelet.setBraceletNo(braceletNo);
        bracelet.setPrisonerId(prisonerId);
        braceletMapper.createBracelet(bracelet);
    }*/
}
