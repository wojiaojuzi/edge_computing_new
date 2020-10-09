package com.ecs.service;


import com.ecs.mapper.VervelGpsMapper;
import com.ecs.mapper.VervelMapper;
import com.ecs.model.Vervel;
import com.ecs.model.VervelGps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VervelService {
    private final VervelMapper vervelMapper;
    private final VervelGpsMapper vervelGpsMapper;

    @Autowired
    public VervelService(VervelMapper vervelMapper, VervelGpsMapper vervelGpsMapper) {
        this.vervelMapper = vervelMapper;
        this.vervelGpsMapper = vervelGpsMapper;
    }

    public Vervel getVervelByDeviceNo(String deviceNo){
        return vervelMapper.getVervelByDeviceNo(deviceNo);
    }

    public VervelGps getVervelGpsByVervelNo(String vervelNo){
        return vervelGpsMapper.getByVervelNo(vervelNo);
    }

    public Vervel getByVervelNo(String vervelNo){
        return vervelMapper.getVervelByVervelNo(vervelNo);
    }

    public void updateDeviceNoByVervelNo(String vervelNo, String deviceNo,String createAt){
        vervelMapper.updateDeviceNoByVervelNo(deviceNo,vervelNo,createAt);
    }

    public String getVervelNoByDeviceNo(String deviceNo){
        return vervelMapper.getVervelNoByDeviceNo(deviceNo);
    }
}
