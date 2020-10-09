package com.ecs.service;

import com.ecs.mapper.PrisonerAnomalyMapper;
import com.ecs.mapper.PrisonerMapper;
import com.ecs.mapper.PrisonerRiskMapper;
import com.ecs.mapper.TaskMapper;
import com.ecs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhaoone on 2019/10/31
 **/
@Service
public class AbnormalConditionService {

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
}