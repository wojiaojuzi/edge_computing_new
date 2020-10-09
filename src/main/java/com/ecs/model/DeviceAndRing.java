package com.ecs.model;

public class DeviceAndRing {
    private String deviceNo;

    private String deviceType;

    private boolean deviceConnectivityStatus;

    private String braceletNo;

    private String verlvelNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isDeviceConnectivityStatus() {
        return deviceConnectivityStatus;
    }

    public void setDeviceConnectivityStatus(boolean deviceConnectivityStatus) {
        this.deviceConnectivityStatus = deviceConnectivityStatus;
    }

    public String getBraceletNo() {
        return braceletNo;
    }

    public void setBraceletNo(String braceletNo) {
        this.braceletNo = braceletNo;
    }

    public String getVerlvelNo() {
        return verlvelNo;
    }

    public void setVerlvelNo(String verlvelNo) {
        this.verlvelNo = verlvelNo;
    }
}
