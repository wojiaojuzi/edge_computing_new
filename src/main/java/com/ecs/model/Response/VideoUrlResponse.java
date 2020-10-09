package com.ecs.model.Response;

import com.ecs.model.CarInner;
import com.ecs.model.CarOuter;

public class VideoUrlResponse {
    private CarInner carInner;

    private CarOuter carOuter;

    public CarInner getCarInner() {
        return carInner;
    }

    public void setCarInner(CarInner carInner) {
        this.carInner = carInner;
    }

    public CarOuter getCarOuter() {
        return carOuter;
    }

    public void setCarOuter(CarOuter carOuter) {
        this.carOuter = carOuter;
    }
}
