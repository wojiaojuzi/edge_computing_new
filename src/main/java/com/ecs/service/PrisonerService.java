package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;
import com.ecs.model.Response.PrisonerDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jojo
 * @Description:
 * @Date: Created on 2019/5/6 21:23
 */
@Service
public class PrisonerService {

    private final PrisonerMapper prisonerMapper;
    private final DeviceMapper deviceMapper;
    private final UserMapper userMapper;
    private final BraceletMapper braceletMapper;
    private final PrisonerHeartBeatMapper prisonerHeartBeatMapper;
    private final PrisonerRiskMapper prisonerRiskMapper;

    @Autowired
    public PrisonerService(PrisonerMapper prisonerMapper, DeviceMapper deviceMapper, UserMapper userMapper,
                           BraceletMapper braceletMapper, PrisonerHeartBeatMapper prisonerHeartBeatMapper, PrisonerRiskMapper prisonerRiskMapper) {
        this.prisonerMapper = prisonerMapper;
        this.deviceMapper = deviceMapper;
        this.userMapper = userMapper;
        this.braceletMapper = braceletMapper;
        this.prisonerHeartBeatMapper = prisonerHeartBeatMapper;
        this.prisonerRiskMapper = prisonerRiskMapper;
    }

    public List<PrisonerDataResponse> getAllPrisonerData(){
        List<PrisonerDataResponse> prisonerDataResponses = new ArrayList<>();
        List<Prisoner> prisoners = prisonerMapper.getAll();
        for(int i=0; i<prisoners.size();i++){
            String prisonerId = prisoners.get(i).getPrisonerId();
            String prisonerName = prisoners.get(i).getPrisonerName();
            String photoUrl = prisoners.get(i).getPrisonerPhotoUrl();
            PrisonerHeartBeat prisonerHeartBeat = prisonerHeartBeatMapper.getLastestHeartbeatByPrisonerId(prisonerId);
            PrisonerRisk prisonerRisk = prisonerRiskMapper.getByPrisonerId(prisonerId);
            PrisonerDataResponse prisonerDataResponse = new PrisonerDataResponse();

            prisonerDataResponse.setPrisonerId(prisonerId);
            prisonerDataResponse.setPrisonerName(prisonerName);
            prisonerDataResponse.setPhoteUrl(photoUrl);
            prisonerDataResponse.setHeartBeat(prisonerHeartBeat.getHeartBeat());
            prisonerDataResponse.setRiskValue(prisonerRisk.getRiskValue());
            prisonerDataResponses.add(prisonerDataResponse);
        }
        return prisonerDataResponses;
    }

    public Prisoner getById(String prisonerId) {
        return prisonerMapper.getByPrisonerId(prisonerId);
    }

    public List<Prisoner> getAll() {
        return prisonerMapper.getAll();
    }

    public double[] getFeatureByPrisonerId(String prisonerId){
        String feature = prisonerMapper.getFeatureByPrisonerId(prisonerId);
        String[] features = feature.split(",");
        double[] array = new double[14];
        for(int i = 0; i < features.length; i++){
            array[i] = Double.valueOf(features[i]);
        }
        return array;
    }

    public String getPrisonerNameByPrisonerId(String priosnerId){
        return prisonerMapper.getByPrisonerId(priosnerId).getPrisonerName();
    }

    public String getPrisonerFeatureByPrisonerId(String prisonerId){
        return prisonerMapper.getFeatureByPrisonerId(prisonerId);
    }

    public Prisoner getByPrisonerId(String prisonerId){
        return prisonerMapper.getByPrisonerId(prisonerId);
    }

    public Prisoner getByPrisonerName(String prisonerName){
        return prisonerMapper.getByPrisonerName(prisonerName);
    }

    public List<Prisoner> getAllPrisoners(){
        return prisonerMapper.getAll();
    }


}
