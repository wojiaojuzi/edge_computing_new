package com.ecs.controller;

import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;
import com.ecs.model.Response.*;
import com.ecs.model.Task;
import com.ecs.model.wan.PrisonerInfo;
import com.ecs.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jojo
 * @Description:
 * @Date: Created on 2019/5/6 21:22
 */
@RestController
@RequestMapping(path = "/prisoners")
@EnableAutoConfiguration
@Api(tags = "Prisoner", description = "获取犯人信息")
public class PrisonerController {

    private final PrisonerService prisonerService;
    private final TaskService taskService;
    private final AdminService adminService;
    private final PrisonerDataService prisonerDataService;

    @Autowired
    public PrisonerController(PrisonerService prisonerService, TaskService taskService, AdminService adminService,PrisonerDataService prisonerDataService) {
        this.prisonerService = prisonerService;
        this.taskService = taskService;
        this.adminService = adminService;
        this.prisonerDataService = prisonerDataService;
    }



    @ApiOperation(value = "获取单个犯人信息")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public PrisonerToPadResponse getById(@RequestParam("prisonerId") String prisonerId, @RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return prisonerService.getPrisonerAllInformation(prisonerId);
    }

    @ApiOperation(value = "获取所有犯人信息")
    @RequestMapping(path = "/getAllPrisonerData", method = RequestMethod.GET)
    public HttpResponseContent getAllPrisonerData(@RequestHeader(value="token") String token) throws Exception{
        String adminId = adminService.getUserIdFromToken(token);
        List<PrisonerDataResponse> prisonerDataResponses = prisonerService.getAllPrisonerData();

        HttpResponseContent response = new HttpResponseContent();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(prisonerDataResponses);
        return response;
    }
    @ApiOperation(value = "获取当前犯人风险值")
    @RequestMapping(path = "/getPrisonerRiskValue", method = RequestMethod.GET)
    public HttpResponseContent getPrisonerRiskValue(@RequestParam("prisonerId")String prisonerId,
                                                    @RequestHeader(value="token") String token) throws Exception {
        String adminId = adminService.getUserIdFromToken(token);
        List<PrisonerRiskDataResponse> prisonerDataResponses = prisonerService.getPrisonerRiskValue(prisonerId);
        HttpResponseContent response = new HttpResponseContent();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(prisonerDataResponses);
        return response;
    }

    @ApiOperation(value = "获取万学长数据")
    @RequestMapping(path = "/getWan", method = RequestMethod.GET)
    public HttpResponseContent getWan(){
        List<PrisonerInfo> prisonerInfoList = prisonerService.getWan1();
        HttpResponseContent response = new HttpResponseContent();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(prisonerInfoList);
        return response;
    }

}
