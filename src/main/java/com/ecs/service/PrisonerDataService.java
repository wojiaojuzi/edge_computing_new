package com.ecs.service;

import com.ecs.mapper.PrisonerAnomalyMapper;
import com.ecs.mapper.PrisonerHeartBeatMapper;
import com.ecs.mapper.PrisonerRiskMapper;
import com.ecs.model.PrisonerAnomaly;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhaoone on 2019/11/13
 **/
@Service
public class PrisonerDataService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";
    private final PrisonerHeartBeatMapper prisonerHeartBeatMapper;
    private final PrisonerRiskMapper prisonerRiskMapper;
    private final PrisonerAnomalyMapper prisonerAnomalyMapper;
    //private final RiskLevelRecordingMapper riskLevelRecordingMapper;

    @Autowired
    public PrisonerDataService(PrisonerHeartBeatMapper prisonerHeartBeatMapper,
                               PrisonerRiskMapper prisonerRiskMapper, PrisonerAnomalyMapper prisonerAnomalyMapper) {
        this.prisonerHeartBeatMapper = prisonerHeartBeatMapper;
        this.prisonerRiskMapper = prisonerRiskMapper;
        this.prisonerAnomalyMapper = prisonerAnomalyMapper;
    }

    public void uploadHeartbeat(String prisonerId, String heartbeat){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        String now = mysqlSdf.format(new Date());
        prisonerHeartBeatMapper.createHeartbeat(prisonerId, heartbeat, now);
    }

    public PrisonerHeartBeat getLastestHeartbeat(String prisonerId){
        return prisonerHeartBeatMapper.getLastestHeartbeatByPrisonerId(prisonerId);
    }

    public PrisonerRisk getLatestRisk(String prisonerId){
        return prisonerRiskMapper.getByPrisonerId(prisonerId);
    }

    public List<PrisonerAnomaly> getAllPrisonerAnomaly(){
        return prisonerAnomalyMapper.getAll();
    }

    /*public PrisonerOriginalData getLastest(String prisonerId){
        return prisonerHeartBeatMapper.getByLastestTime(prisonerId);
    }

    public GpsData getLastest2(String userId){      //genju userId
        return gpsMapper.getByLastestTime(userId);
    }

    public GpsData getLastest3(String carNo){    //genju carNo
        return gpsMapper.getByLastestTimeAndCarNo(carNo);
    }

    public void uploadGps(String userId, String longitude, String latitude, String carNo){
        gpsMapper.createGps(userId, longitude, latitude, carNo);
    }*/

    public void uploadOutRange(String prisonerId){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        //Timestamp now = new Timestamp(new Date().getTime());
        String now = mysqlSdf.format(new Date());
        String riskId = prisonerRiskMapper.getByPrisonerId(prisonerId).getId();
        prisonerAnomalyMapper.createPrisonerAnomaly(riskId,now,"0",false,false,"犯人位置超距","");
    }
}
