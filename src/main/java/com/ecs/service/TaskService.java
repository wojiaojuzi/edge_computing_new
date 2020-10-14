package com.ecs.service;

import com.ecs.mapper.*;
import com.ecs.model.*;
import com.ecs.model.Response.EscortDataResponse;
import com.ecs.model.Response.VideoUrlResponse;
import com.ecs.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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


    public List<Route> getAllRoute(){
        return routeMapper.getAllRoute();
    }

    public boolean getDataBaseStatus(){
        System.out.println(taskMapper.getSchemaStatus("edge_computing_service"));
        System.out.println(taskMapper.getTableStatus("task","edge_computing_service"));
        System.out.println(taskMapper.getTaskNumber());
        if(taskMapper.getSchemaStatus("edge_computing_service")==0)
            return false;
        else{
            if(taskMapper.getTableStatus("task","edge_computing_service")==0)
                return false;
            else{
                if(taskMapper.getTaskNumber()==0)
                    return false;
            }
        }


        return true;
    }

    public void inputTasks() throws SQLException, ClassNotFoundException {
        //File file1 = new File("/media/guoxidong/TEST/guoxidong/create_database.sql");
        //File file2 = new File("/media/guoxidong/TEST/guoxidong/register_task.sql");
        //File file3 = new File("/media/guoxidong/TEST/guoxidong/edge_computing_service_part.sql");
        File file1 = new File("F:/guoxidong/create_database.sql");
        File file2 = new File("F:/guoxidong/register_task.sql");
        File file3 = new File("F:/guoxidong/edge_computing_service_part.sql");
        //SqlUtil.mybatisExec2();
        SqlUtil.mybatisExec2(file1);
        SqlUtil.mybatisExec(file2);
        SqlUtil.mybatisExec(file3);
    }
}
