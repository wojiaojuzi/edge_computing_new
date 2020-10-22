package com.ecs.model.wan;

public class PrisonerInfo {
    private String name;

    private String gender;

    private String age;

    private String riskLevel;

    private String imgUrl;

    private String carVideoUrl;

    private String carNo;

    private String exceptionCondition;

    private HeartRate heartRate;

    private MultiFactorialRisk multiFactorialRisk;

    private String crime;

    private String termOfImprisonment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCarVideoUrl() {
        return carVideoUrl;
    }

    public void setCarVideoUrl(String carVideoUrl) {
        this.carVideoUrl = carVideoUrl;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getExceptionCondition() {
        return exceptionCondition;
    }

    public void setExceptionCondition(String exceptionCondition) {
        this.exceptionCondition = exceptionCondition;
    }

    public HeartRate getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(HeartRate heartRate) {
        this.heartRate = heartRate;
    }

    public MultiFactorialRisk getMultiFactorialRisk() {
        return multiFactorialRisk;
    }

    public void setMultiFactorialRisk(MultiFactorialRisk multiFactorialRisk) {
        this.multiFactorialRisk = multiFactorialRisk;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public String getTermOfImprisonment() {
        return termOfImprisonment;
    }

    public void setTermOfImprisonment(String termOfImprisonment) {
        this.termOfImprisonment = termOfImprisonment;
    }

    @Override
    public String toString() {
        return "PrisonerInfo{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", carVideoUrl='" + carVideoUrl + '\'' +
                ", carNo='" + carNo + '\'' +
                ", exceptionCondition='" + exceptionCondition + '\'' +
                ", heartRate=" + heartRate +
                ", multiFactorialRisk=" + multiFactorialRisk +
                ", crime='" + crime + '\'' +
                ", termOfImprisonment='" + termOfImprisonment + '\'' +
                '}';
    }
}
