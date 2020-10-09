package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.*;
import com.ecs.model.Response.EscortDataResponse;
import com.ecs.model.Response.VideoUrlResponse;
import com.ecs.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/21
 **/
@Service
public class TaskService {
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final TaskMapper taskMapper;
    private final DeviceMapper deviceMapper;
    private final PrisonerMapper prisonerMapper;
    private final BraceletMapper braceletMapper;
    private final RouteMapper routeMapper;
    private final ConvoyMapper convoyMapper;

    @Autowired
    public TaskService(UserMapper userMapper, CarMapper carMapper, TaskMapper taskMapper, DeviceMapper deviceMapper,
                       PrisonerMapper prisonerMapper, BraceletMapper braceletMapper, RouteMapper routeMapper, ConvoyMapper convoyMapper) {
        this.userMapper = userMapper;
        this.carMapper = carMapper;
        this.taskMapper = taskMapper;
        this.deviceMapper = deviceMapper;
        this.prisonerMapper = prisonerMapper;
        this.braceletMapper = braceletMapper;
        this.routeMapper = routeMapper;
        this.convoyMapper = convoyMapper;
    }
    //    public List<User> getByTaskNo(String taskNo){
//        return userMapper.getByTaskNo(taskNo);
//    }
//
//    public Car getByCarNo(String carNo){
//        return carMapper.getByCarNo(carNo);
//    }
//
//    public List<Car> getAllCars(){
//        return carMapper.getAll();
//    }
//
//    public List<User> getMembersInCar(String carNo){
//        return userMapper.getMembersByCarNo(carNo);
//    }

    public List<VideoUrlResponse> getVideoUrl(){
        List<Car> cars = carMapper.getAll();
        List<VideoUrlResponse> videoUrlResponses = new ArrayList<>();
        for(int i=0;i<cars.size();i++){
            VideoUrlResponse videoUrlResponse = new VideoUrlResponse();
            Car car = cars.get(i);
            CarInner carInner = new CarInner();
            CarOuter carOuter = new CarOuter();
            carInner.setCarNo(car.getCarNo());
            carInner.setCarInnerVideoUrl(car.getCarInnerVideoUrl());
            carOuter.setCarNo(car.getCarNo());
            carOuter.setCarOuterVideoUrl(car.getCarOuterVideoUrl());

            videoUrlResponse.setCarInner(carInner);
            videoUrlResponse.setCarOuter(carOuter);

            videoUrlResponses.add(videoUrlResponse);
        }
        return videoUrlResponses;
    }

    public List<EscortDataResponse> getAllTasks(){
        List<EscortDataResponse> escortDataResponses = new ArrayList<>();

        List<Car> cars = carMapper.getAll();
        for(int i =0;i<cars.size();i++) {
            EscortDataResponse escortDataResponse = new EscortDataResponse();
            String taskNo = convoyMapper.getTaskNoByCarNo(cars.get(i).getCarNo());
            List<String> userIds = convoyMapper.getUserIdByCarNo(cars.get(i).getCarNo());
            List<String> userNames = new ArrayList<>();
            for (int j = 0; j < userIds.size(); j++)
                userNames.add(userMapper.getByUserId(userIds.get(j)).getUserName());

            List<String> prisonerIds = convoyMapper.getPrisonerIdByCarNo(cars.get(i).getCarNo());
            System.out.println(prisonerIds);
            List<String> prisonerNames = new ArrayList<>();
            for (int j = 0; j < prisonerIds.size(); j++) {
                if(prisonerIds.get(i)!=null)
                    prisonerNames.add(prisonerMapper.getByPrisonerId(prisonerIds.get(j)).getPrisonerName());
            }
            escortDataResponse.setCarNo(cars.get(i).getCarNo());
            escortDataResponse.setCarType(cars.get(i).getType());
            escortDataResponse.setTaskNo(taskNo);
            escortDataResponse.setPoliceNames(userNames);
            escortDataResponse.setPrisonerNames(prisonerNames);

            escortDataResponses.add(escortDataResponse);
        }
        return escortDataResponses;
    }

    public Task getTask(String TaskNo){
        return taskMapper.getByTaskNo(TaskNo);
    }

    public Task getPrisonerCar(String prisonerName){
        return taskMapper.getByPrisonerName(prisonerName);
    }

    public Task getTaskByUserName(String userName){
        return taskMapper.getByUserName(userName);
    }



    public List<Route> getAllRoute(){
        return routeMapper.getAllRoute();
    }

    public void inputTasks() throws SQLException, ClassNotFoundException {
        SqlUtil.mybatisExec2();
        SqlUtil.mybatisExec1();
    }
}
