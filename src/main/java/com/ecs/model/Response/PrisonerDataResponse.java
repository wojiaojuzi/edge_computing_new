package com.ecs.model.Response;

import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;

public class PrisonerDataResponse {
    private String prisonerId;

    private String prisonerName;

    private String photeUrl;

    private String heartBeat;

    private String riskValue;


    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getPhoteUrl() {
        return photeUrl;
    }

    public void setPhoteUrl(String photeUrl) {
        this.photeUrl = photeUrl;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(String riskValue) {
        this.riskValue = riskValue;
    }
}
