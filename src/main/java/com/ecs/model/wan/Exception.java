package com.ecs.model.wan;

public class Exception {
    private String createAt;

    private String prisonerName;

    private String exceptionType;

    private String exceptionLevel;

    private String dealState;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionLevel() {
        return exceptionLevel;
    }

    public void setExceptionLevel(String exceptionLevel) {
        this.exceptionLevel = exceptionLevel;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }
}
