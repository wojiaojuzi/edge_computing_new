package com.ecs.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecs.model.*;
import com.ecs.model.Response.*;
import com.ecs.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhaoone on 2019/10/21
 **/
@RestController
@RequestMapping(path = "/tasks")
@EnableAutoConfiguration
@Api(tags = "Task", description = "任务相关操作")

/*
*
*/
public class TaskController {
    private final TaskService taskService;
    private final PrisonerService prisonerService;
    private final AdminService adminService;
    private final UserService userService;
    private final ConvoyService convoyService;
    private final CarService carService;

    @Autowired
    public TaskController(TaskService taskService, PrisonerService prisonerService,
                          AdminService adminService, UserService userService, ConvoyService convoyService,
                          CarService carService) {
        this.taskService = taskService;
        this.prisonerService = prisonerService;
        this.adminService = adminService;
        this.userService = userService;
        this.convoyService = convoyService;
        this.carService = carService;
    }

    @ApiOperation(value = "获取全部任务")
    @RequestMapping(path = "/getAllEscortData", method = RequestMethod.GET)
    public HttpResponseContent getAllTasks(@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        List<EscortDataResponse> escortDataResponses = taskService.getAllTasks();

        HttpResponseContent response = new HttpResponseContent();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(escortDataResponses);
        return response;
    }

/*    @ApiOperation(value = "获得车辆数量")
    @RequestMapping(path = "/getNumbersOfCars", method = RequestMethod.GET)
    public String getNumbersOfCars(@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return String.valueOf(carService.getAllCars().size());
    }

    @ApiOperation(value = "获取单个任务信息")
    @RequestMapping(path = "/getTask", method = RequestMethod.GET)
    public Task getTask(@RequestParam("taskNo") String taskNo, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        Task task = taskService.getTask(taskNo);
        return task;
    }

    @ApiOperation(value = "获取车辆信息")
    @RequestMapping(path = "/getAboutCar", method = RequestMethod.GET)
    public Car getCar(@RequestParam("carNo") String carNo, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        Car car = carService.getCarByCarNo(carNo);
        return car;
    }
    */

    @ApiOperation(value = "获取警察的任务信息（手持机）")
    @RequestMapping(path = "/deviceGetTasks", method = RequestMethod.GET)
    public Task getByUserId(@RequestParam("userName") String userName, @RequestHeader(value="token") String token) {
        String userId = userService.getByUserName(userName).getUserId();
        String taskNo = convoyService.getConvoyByUserId(userId).getTaskNo();
        Task task = taskService.getTask(taskNo);
        return task;
    }


    @ApiOperation(value = "获取警察绑定犯人信息（一体机）")
    @RequestMapping(path = "/getByUser", method = RequestMethod.GET)
    public Prisoner getPrisonerId(@RequestParam("userId") String userId, @RequestHeader(value="token") String token) throws Exception{
        String user_Id = adminService.getUserIdFromToken(token);
        String prisonerId = convoyService.getConvoyByUserId(userId).getPrisonerId();
        Prisoner prisoner = prisonerService.getByPrisonerId(prisonerId);
        return prisoner;
    }

    @ApiOperation(value = "获取视频")
    @RequestMapping(path = "/getVideoUrl", method = RequestMethod.GET)
    public HttpResponseContent getVideoUrl(@RequestHeader(value="token") String token) throws Exception {
        String user_Id = adminService.getUserIdFromToken(token);
        HttpResponseContent response = new HttpResponseContent();
        List<VideoUrlResponse> videoUrlResponses = taskService.getVideoUrl();

        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(videoUrlResponses);
        return response;
    }

    @ApiOperation(value = "获取路线信息")
    @RequestMapping(path = "/getRoute", method = RequestMethod.GET)
    public List<Route> getAllRoute(@RequestHeader(value="token") String token) throws Exception{
        return taskService.getAllRoute();
    }

}
