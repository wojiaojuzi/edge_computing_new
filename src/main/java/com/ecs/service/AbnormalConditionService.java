package com.ecs.service;

import com.ecs.mapper.PrisonerAnomalyMapper;
import com.ecs.mapper.PrisonerMapper;
import com.ecs.mapper.PrisonerRiskMapper;
import com.ecs.mapper.TaskMapper;
import com.ecs.model.*;
import com.ecs.model.Response.AbnormalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Zhaoone on 2019/10/31
 **/
@Service
public class AbnormalConditionService {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";
    private final PrisonerMapper prisonerMapper;
    private final TaskMapper taskMapper;
    private final PrisonerRiskMapper prisonerRiskMapper;
    private final PrisonerAnomalyMapper prisonerAnomalyMapper;

    @Autowired
    public AbnormalConditionService(PrisonerMapper prisonerMapper, TaskMapper taskMapper, PrisonerRiskMapper prisonerRiskMapper,
                                    PrisonerAnomalyMapper prisonerAnomalyMapper) {
        this.prisonerMapper = prisonerMapper;
        this.taskMapper = taskMapper;
        this.prisonerRiskMapper = prisonerRiskMapper;
        this.prisonerAnomalyMapper = prisonerAnomalyMapper;
    }

    public List<AbnormalResponse> getAllExceptions() throws ParseException {
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        List<PrisonerRisk> list = getAllHighRiskLevel("0");
        System.out.println(list.toString());
        List<AbnormalResponse> abnormalResponses = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId();
            String prisonerId = list.get(i).getPrisonerId();
            String prisonerName = prisonerMapper.getPrisonerNameByPrisonerId(prisonerId);


            String createAt = mysqlSdf.format(mysqlSdf.parse(list.get(i).getCreateAt()));;
            System.out.println(createAt);
            String riskValue = list.get(i).getRiskValue();
            //List<PrisonerAnomaly> prisonerAnomalies = abnormalConditionService.getPrisonerAnomalyByRiskId(id);
            PrisonerAnomaly prisonerAnomaly = getLatestByRiskId(id);
            if(prisonerAnomaly!=null) {
                AbnormalResponse abnormalResponse = new AbnormalResponse();
                abnormalResponse.setCreateAt(createAt);
                abnormalResponse.setPrisonerName(prisonerName);
                abnormalResponse.setExceptionType("危险");
                abnormalResponse.setExceptionLevel(prisonerAnomaly.getLevel());
                abnormalResponse.setDealState(prisonerAnomaly.isDealState());

                abnormalResponses.add(abnormalResponse);
            }

        }
        return abnormalResponses;
    }



    public List<PrisonerRisk> getAllHighRiskLevel(String riskValue){
        return prisonerRiskMapper.getAllHighRisk(riskValue);
    }

    public List<PrisonerRisk> getPrisonerRiskByPrisonerId(String prisonerId){
        return prisonerRiskMapper.getPrisonerRiskByPrisonerId(prisonerId);
    }

    public List<PrisonerAnomaly> getPrisonerAnomalyByRiskId(String riskId){
        return prisonerAnomalyMapper.getByRiskId(riskId);
    }
    public PrisonerAnomaly getLatestByRiskId(String riskId){
        return prisonerAnomalyMapper.getLatestByRiskId(riskId);
    }

    /*public List<RiskLevel> getHighRiskLevelAndPrisoner(String prisonerId){
        return riskLevelRecordingMapper.getByHighRiskSignAndPrisonerId(true, prisonerId);
    }*/

    public void updateDealState(String riskId){
        prisonerAnomalyMapper.updateDealStateByRiskId(true, riskId);
    }

    public void updateMisdeclaration(String riskId){
        prisonerAnomalyMapper.updateMisdeclarationByRiskId(true, riskId);
    }

    public void updateComment(String riskId, String comment){
        prisonerAnomalyMapper.updateCommentByRiskId(comment, riskId);
    }

    public Prisoner getPrisonerName(String prisonerId){
        return prisonerMapper.getByPrisonerId(prisonerId);
    }

    public Task getCar(String prisonerName){
        return taskMapper.getByPrisonerName(prisonerName);
    }

    public List<PrisonerAnomaly> getPrisonerAnomalyByPrisonerId(String prisonerId){
        List<PrisonerAnomaly>prisonerAnomalyList = prisonerAnomalyMapper.getByPrisonerId(prisonerId);
        List<PrisonerAnomaly>prisonerAnomalyLatest = new ArrayList<>();
        Set<String> risk_ids = new HashSet<>();
        for(int i = 0; i < prisonerAnomalyList.size(); i++){
            PrisonerAnomaly prisonerAnomaly = prisonerAnomalyList.get(i);
            if(!risk_ids.contains(prisonerAnomaly.getRiskId())) {
                prisonerAnomalyLatest.add(prisonerAnomaly);
                risk_ids.add(prisonerAnomaly.getRiskId());
            }
        }
        return prisonerAnomalyLatest;
    }
}
