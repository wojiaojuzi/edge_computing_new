package com.ecs.controller;


import com.ecs.model.Response.HttpResponseContent;
import com.ecs.model.Response.JudgeTaskResponse;
import com.ecs.model.Response.ResponseEnum;
import com.ecs.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path = "/taskData")
@EnableAutoConfiguration
@Api(tags = "TaskData", description = "任务数据相关操作")
public class TaskDataController {
    private final TaskService taskService;

    @Autowired
    public TaskDataController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "押解任务导入")
    @RequestMapping(path = "/inputTasks", method = RequestMethod.GET)
    public HttpResponseContent inputTasks() throws SQLException, ClassNotFoundException {
        taskService.inputTasks();
        return null;
    }

    @ApiOperation(value = "判断有无押解数据")
    @RequestMapping(path = "/hasData", method = RequestMethod.GET)
    public HttpResponseContent hasData(){
        System.out.println("hasData");
        HttpResponseContent response = new HttpResponseContent();

        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        Integer isData = 1;
        response.setData(isData);

        return response;
    }
}
