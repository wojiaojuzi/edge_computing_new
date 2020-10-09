package com.ecs.model.Response;

import com.ecs.model.Car;
import com.ecs.model.DeviceGps;

import java.util.List;

public class CarGpsResponse {
    private Car car;

    private String taskNo;

    private List<DeviceGps> deviceGpsList;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public List<DeviceGps> getDeviceGpsList() {
        return deviceGpsList;
    }

    public void setDeviceGpsList(List<DeviceGps> deviceGpsList) {
        this.deviceGpsList = deviceGpsList;
    }
}
