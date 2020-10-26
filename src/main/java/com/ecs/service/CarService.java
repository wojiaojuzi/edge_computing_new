package com.ecs.service;


import com.ecs.mapper.CarMapper;
import com.ecs.mapper.ConvoyMapper;
import com.ecs.mapper.DeviceGpsMapper;
import com.ecs.mapper.DeviceMapper;
import com.ecs.model.Car;
import com.ecs.model.Convoy;
import com.ecs.model.DeviceGps;
import com.ecs.model.Response.CarAndTaskResponse;
import com.ecs.model.Response.CarGpsResponse;
import com.ecs.utils.ImitateCoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";
    private final CarMapper carMapper;
    private final ConvoyMapper convoyMapper;
    private final DeviceMapper deviceMapper;
    private final DeviceGpsMapper deviceGpsMapper;

    @Autowired
    public CarService(CarMapper carMapper, ConvoyMapper convoyMapper, DeviceMapper deviceMapper, DeviceGpsMapper deviceGpsMapper) {
        this.carMapper = carMapper;
        this.convoyMapper = convoyMapper;
        this.deviceMapper = deviceMapper;
        this.deviceGpsMapper = deviceGpsMapper;
    }

    public List<Car> getAllCars(){
        return carMapper.getAll();
    }

    public Car getCarByCarNo(String carNo){
        return carMapper.getByCarNo(carNo);
    }

    public String getCommandCarNo(){
        return carMapper.getCommandCarNo();
    }

    public List<String> getCarNo(){
        return carMapper.getCarNo();
    }

    //public List<CarAndTaskResponse> getCarAndTask
    public List<CarGpsResponse> getCarGps() throws ParseException {
        SimpleDateFormat mysqlSdf = new java.text.SimpleDateFormat(mysqlSdfPatternString);
        List<CarGpsResponse> carGpsResponses = new ArrayList<>();
        List<Car> cars = carMapper.getAll();
        for(int i = 0; i < cars.size(); i++){
            List<Convoy> convoys = convoyMapper.getByCarNo(cars.get(i).getCarNo());
            CarGpsResponse carGpsResponse = new CarGpsResponse();
            carGpsResponse.setCarNo(cars.get(i).getCarNo());
            carGpsResponse.setCarType(cars.get(i).getType());
            carGpsResponse.setColor(cars.get(i).getColor());
            carGpsResponse.setTaskNo(convoys.get(0).getTaskNo());
            if(cars.get(i).getType().equals("押解车"))
            for(int j=0;j<convoys.size();j++){
                String user_id = convoys.get(j).getUserId();
                DeviceGps deviceGps = null;
                if(deviceMapper.getByUserIdAndDeviceType(user_id,"一体化终端")!=null) {
                    String device_no = deviceMapper.getByUserIdAndDeviceType(user_id,"一体化终端").getDeviceNo();
                    deviceGps = deviceGpsMapper.getByDeviceNo(device_no);
                    if(deviceGps!=null){
                        long timestamp = mysqlSdf.parse(deviceGps.getCreateAt()).getTime();
                        carGpsResponse.setHeight(deviceGps.getHeight());
                        carGpsResponse.setLongitude(deviceGps.getLongitude());
                        carGpsResponse.setLatitude(deviceGps.getLatitude());
                        carGpsResponse.setTimestamp(timestamp);
                        break;
                    }
                }
            }
            else{
                carGpsResponse.setHeight(ImitateCoor.height);
                carGpsResponse.setLongitude(ImitateCoor.lon);
                carGpsResponse.setLatitude(ImitateCoor.lat);
            }


            carGpsResponses.add(carGpsResponse);
        }
        return carGpsResponses;
    }
}
