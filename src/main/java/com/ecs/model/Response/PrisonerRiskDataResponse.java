package com.ecs.model.Response;

public class PrisonerRiskDataResponse {
    private String prisonerId;

    private String riskValueOfEnvironment;

    private String riskValueOfVideo;

    private String totalRiskValue;

    private String createAt;

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
