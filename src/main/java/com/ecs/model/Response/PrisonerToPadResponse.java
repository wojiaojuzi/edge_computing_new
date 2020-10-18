package com.ecs.model.Response;

import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;
import io.swagger.annotations.ApiModelProperty;

public class PrisonerToPadResponse {
    private String prisonerId;

    private String heartBeat;

    private String riskValueOfEnvironment;

    private String riskValueOfVideo;

    private String totalRiskValue;

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getRiskValueOfEnvironment() {
        return riskValueOfEnvironment;
    }

    public void setRiskValueOfEnvironment(String riskValueOfEnvironment) {
        this.riskValueOfEnvironment = riskValueOfEnvironment;
    }

    public String getRiskValueOfVideo() {
        return riskValueOfVideo;
    }

    public void setRiskValueOfVideo(String riskValueOfVideo) {
        this.riskValueOfVideo = riskValueOfVideo;
    }

    public String getTotalRiskValue() {
        return totalRiskValue;
    }

    public void setTotalRiskValue(String totalRiskValue) {
        this.totalRiskValue = totalRiskValue;
    }
}
