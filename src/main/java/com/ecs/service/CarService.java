package com.ecs.service;


import com.ecs.mapper.CarMapper;
import com.ecs.model.Car;
import com.ecs.model.Response.CarAndTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarMapper carMapper;

    @Autowired
    public CarService(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    public List<Car> getAllCars(){
        return carMapper.getAll();
    }

    public Car getCarByCarNo(String carNo){
        return carMapper.getByCarNo(carNo);
    }

    public String getCommandCarNo(){
        return carMapper.getCommandCarNo();
    }

    public List<String> getCarNo(){
        return carMapper.getCarNo();
    }

    //public List<CarAndTaskResponse> getCarAndTask
}
