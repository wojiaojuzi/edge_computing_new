package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.*;
import com.ecs.model.Request.DeviceRegisterRequest;
import com.ecs.model.Response.DeviceCloudGraphResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";

    private final DeviceMapper deviceMapper;
    private final UserMapper userMapper;
    private final BraceletMapper braceletMapper;
    private final VervelMapper vervelMapper;
    private final DeviceConnectionMapper deviceConnectionMapper;
    private final DeviceGpsMapper deviceGpsMapper;
    private final DeviceStateMapper deviceStateMapper;
    private final CarMapper carMapper;
    private final ConvoyMapper convoyMapper;
    private final PrisonerMapper prisonerMapper;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper, UserMapper userMapper, BraceletMapper braceletMapper, VervelMapper vervelMapper,
                          DeviceConnectionMapper deviceConnectionMapper, DeviceGpsMapper deviceGpsMapper,
                         DeviceStateMapper deviceStateMapper,CarMapper carMapper,ConvoyMapper convoyMapper,PrisonerMapper prisonerMapper) {
        this.deviceMapper = deviceMapper;
        this.userMapper = userMapper;
        this.braceletMapper = braceletMapper;
        this.vervelMapper = vervelMapper;
        this.deviceConnectionMapper = deviceConnectionMapper;
        this.deviceGpsMapper = deviceGpsMapper;
        this.deviceStateMapper = deviceStateMapper;
        this.carMapper = carMapper;
        this.convoyMapper = convoyMapper;
        this.prisonerMapper = prisonerMapper;
    }

    public DeviceCloudGraphResponse getDeviceCloudGraph(){
        DeviceCloudGraphResponse deviceCloudGraphResponse = new DeviceCloudGraphResponse();
        List<CarInfo> carInfos = new ArrayList<>();
        String commandCarNo = carMapper.getCommandCarNo();
        List<String> carNos= carMapper.getCarNo();
        for(int i=0;i<carNos.size();i++){
            String carNo = carNos.get(i);
            List<DeviceAndRing> deviceAndRingList=new ArrayList<>();
            CarInfo carInfo = new CarInfo();
            carInfo.setEscortCarNo(carNo);
            List<String> userIds = convoyMapper.getUserIdByCarNo(carNo);
            for(int j =0; j<userIds.size();j++){
                DeviceAndRing deviceAndRing = new DeviceAndRing();
                String user_id = userIds.get(j);
                String userName = userMapper.getUserNameByUserId(user_id);
                String deviceNo = deviceMapper.getDeviceNoByUserId(user_id);
                String taskNo = convoyMapper.getConvoyByUserId(user_id).getTaskNo();
                String prisonerId = convoyMapper.getPrisonerIdByUserId(user_id);
                String prisonerName = prisonerMapper.getPrisonerNameByPrisonerId(prisonerId);
                if(deviceNo!=null) {
                    String deviceType = deviceMapper.getByDeviceNo(deviceNo).getDeviceType();
                    String braceletNo = braceletMapper.getBraceletNoByDeviceNo(deviceNo);
                    String vervelNo = vervelMapper.getVervelNoByDeviceNo(deviceNo);
                    Boolean deviceConnectivityStatus = deviceConnectionMapper.getDeviceConnectivityStatusByDeviceNo(deviceNo);
                    Boolean braceletConnectivityStatus = deviceConnectionMapper.getDeviceConnectivityStatusByDeviceNo(braceletNo);
                    Boolean vervelConnectivityStatus = deviceConnectionMapper.getDeviceConnectivityStatusByDeviceNo(vervelNo);
                    deviceAndRing.setUserName(userName);
                    deviceAndRing.setDeviceName(deviceType+deviceNo);
                    deviceAndRing.setTaskNo(taskNo);
                    deviceAndRing.setPrisonerName(prisonerName);
                    deviceAndRing.setDeviceNo(deviceNo);
                    deviceAndRing.setDeviceType(deviceType);
                    deviceAndRing.setDeviceConnectivityStatus(deviceConnectivityStatus);
                    deviceAndRing.setBraceletNo(braceletNo);
                    deviceAndRing.setBraceletConnectivityStatus(braceletConnectivityStatus);
                    deviceAndRing.setVervelNo(vervelNo);
                    deviceAndRing.setVervelConnectivityStatus(vervelConnectivityStatus);

                    deviceAndRingList.add(deviceAndRing);
                }
            }
            carInfo.setDeviceAndRingList(deviceAndRingList);
            carInfos.add(carInfo);
        }

        deviceCloudGraphResponse.setCommandCarNo(commandCarNo);
        deviceCloudGraphResponse.setCarInfoList(carInfos);

        return deviceCloudGraphResponse;
    }

    public Device getByDeviceNo(String deviceNo) {
        return deviceMapper.getByDeviceNo(deviceNo);
    }

    public Device getByUserId(String userId){
        return deviceMapper.getByUserId(userId);
    }

    public List<Device> getAll() {
        return deviceMapper.getAll();
    }

    public Device createDevice(DeviceRegisterRequest deviceRegisterRequest) {
        if(deviceRegisterRequest.getUserId() == null || deviceRegisterRequest.getDeviceType() == null || deviceRegisterRequest.getDeviceNo() == null) {
            return null;
        } else {
            String userId = deviceRegisterRequest.getUserId();
            String deviceType = deviceRegisterRequest.getDeviceType();
            String deviceNo = deviceRegisterRequest.getDeviceNo();
            if(deviceType.equals("一体化终端") || deviceType.equals("手持机")) {
                if(deviceMapper.getByDeviceNo(deviceNo) != null) {
                    deviceMapper.updateDeviceUserByDeviceNo(userId, deviceNo);
                    return deviceMapper.getByDeviceNo(deviceNo);
                } else {
                    Device device = new Device();
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//                String date = df.format(new Date());
//                String deviceNo = deviceRegisterRequest.getDeviceType() + date;
                    device.setDeviceNo(deviceNo);
                    device.setDeviceType(deviceType);
                    device.setUserId(userId);
                    deviceMapper.createDevice(device);
                    return deviceMapper.getByDeviceNo(deviceNo);
                }
            } else {
                return null;
            }
        }
    }



    public void deviceLogin(String deviceNo) {
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        //braceletMapper.updateDeviceStatusByDeviceNo(true, deviceNo);
        //vervelMapper.updateDeviceStatusByDeviceNo(true, deviceNo);
        deviceConnectionMapper.updateDeviceConnectivityStatusByDeviceNo(true,createAt,"设备连接",deviceNo);
    }

    public void deviceLogout(String deviceNo) {
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        //braceletMapper.updateDeviceStatusByDeviceNo(false, deviceNo);
        //vervelMapper.updateDeviceStatusByDeviceNo(false, deviceNo);
        deviceConnectionMapper.updateDeviceConnectivityStatusByDeviceNo(false,createAt,"设备断开",deviceNo);
    }

    public void deleteDevice(String deviceNo) {
        deviceMapper.deleteByDeviceNo(deviceNo);


        braceletMapper.deleteByDeviceNo(deviceNo);
        vervelMapper.deleteByDeviceNo(deviceNo);
    }

    public String getDeviceNoByUserId(String userId){
        return deviceMapper.getDeviceNoByUserId(userId);
    }

    //deviceState
    public DeviceState getDeviceStateBydeviceNo(String deviceNo){
        return deviceStateMapper.getByDeviceNo(deviceNo);
    }
    public void createDeviceState(DeviceState deviceState){
        String createAt = deviceState.getCreateAt();
        String dumpEnergyRate = deviceState.getDumpEnergyRate();
        String cpuUsageRate = deviceState.getCpuUsageRate();
        String memoryUsageRate = deviceState.getMemoryUsageRate();
        String deviceNo = deviceState.getDeviceNo();
        deviceStateMapper.createDeviceState(createAt,dumpEnergyRate,cpuUsageRate,memoryUsageRate,deviceNo);
    }

    //deviceGps
    public DeviceGps getDeviceGpsBydeviceNo(String deviceNo){
        return deviceGpsMapper.getByDeviceNo(deviceNo);
    }
    public void deleteDeviceGpsDeviceGps(String deviceNo) {
        deviceGpsMapper.deleteByDeviceNo(deviceNo);
    }

    //deviceConnection
    public void deleteDeviceConnection(String deviceNo) {
        deviceStateMapper.deleteByDeviceNo(deviceNo);
    }

    public DeviceConnection getDeviceConnectionByDeviceNo(String deviceNo){
        return deviceConnectionMapper.getByDeviceNo(deviceNo);
    }


    //bracelet
    public Bracelet getBracelet(String deviceNo){
        return braceletMapper.getBraceletByDeviceNo(deviceNo);
    }

    //vervel
    public Vervel getVervel(String deviceNo){
        return vervelMapper.getVervelByDeviceNo(deviceNo);
    }


    public Boolean rectifyDeviceStatus(String deviceNo) throws Exception {
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        DeviceState deviceState = deviceStateMapper.getByDeviceNo(deviceNo);
        Device device = getByDeviceNo(deviceNo);
        Timestamp now = new Timestamp(new Date().getTime());

        if(deviceState != null){
            if(now.getTime() - mysqlSdf.parse(deviceState.getCreateAt()).getTime() > 5500){
                updateDeviceConnectivityStatusByDeviceNo(false,"设备离线",deviceNo );

                return false;
            }else{
                DeviceConnection deviceConnection = deviceConnectionMapper.getByDeviceNo(deviceNo);
                if(deviceConnection!=null){
                    if(!deviceConnection.isDeviceConnectivityStatus()){
                        updateDeviceConnectivityStatusByDeviceNo(true,"设备重连",deviceNo);
                        return true;
                    }
                }
                return true;
            }
        }
        return true;/*else{
            if(now.getTime() - mysqlSdf.parse(deviceState.getCreateAt()).getTime() > 10500) {
                return false;
            }else{
                updateDeviceConnectivityStatusByDeviceNo(true,"设备重连",deviceNo);
                return true;
            }
        }*/
    }

    public void updateDeviceConnectivityStatusByDeviceNo(Boolean deviceConnectivityStatus, String record, String deviceNo){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        deviceConnectionMapper.updateDeviceConnectivityStatusByDeviceNo(deviceConnectivityStatus, createAt, record,deviceNo);
    }

    public void uploadDeviceGps(String deviceNo, String longitude, String latitude, String height){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        deviceGpsMapper.createDeviceGps(deviceNo,longitude,latitude,height,createAt);
    }
}

