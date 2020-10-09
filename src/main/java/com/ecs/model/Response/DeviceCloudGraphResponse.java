package com.ecs.model.Response;

import com.ecs.model.CarInfo;

import java.util.List;

public class DeviceCloudGraphResponse {
    private String commandCarNo;

    private List<CarInfo> carInfoList;

    public String getCommandCarNo() {
        return commandCarNo;
    }

    public void setCommandCarNo(String commandCarNo) {
        this.commandCarNo = commandCarNo;
    }

    public List<CarInfo> getCarInfoList() {
        return carInfoList;
    }

    public void setCarInfoList(List<CarInfo> carInfoList) {
        this.carInfoList = carInfoList;
    }
}
