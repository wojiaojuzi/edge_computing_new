package com.ecs.controller;

import com.ecs.model.Prisoner;
import com.ecs.model.PrisonerHeartBeat;
import com.ecs.model.PrisonerRisk;
import com.ecs.model.Response.HttpResponseContent;
import com.ecs.model.Response.PrisonerDataResponse;
import com.ecs.model.Response.ResponseEnum;
import com.ecs.model.Task;
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
    private final UserService userService;
    private final PrisonerDataService prisonerDataService;

    @Autowired
    public PrisonerController(PrisonerService prisonerService, TaskService taskService, AdminService adminService,UserService userService,PrisonerDataService prisonerDataService) {
        this.prisonerService = prisonerService;
        this.taskService = taskService;
        this.adminService = adminService;
        this.userService = userService;
        this.prisonerDataService = prisonerDataService;
    }



    @ApiOperation(value = "获取单个犯人信息")
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public Prisoner getById(@RequestParam("prisonerId") String prisonerId,@RequestHeader(value="token") String token) throws Exception{
        String userId = adminService.getUserIdFromToken(token);
        return prisonerService.getById(prisonerId);
    }

    @ApiOperation(value = "获取所有犯人信息")
    @RequestMapping(path = "/getAllPrisonerData", method = RequestMethod.GET)
    public HttpResponseContent getAllPrisonerData(@RequestHeader(value="token") String token) throws Exception{
        String adminId = adminService.getUserIdFromToken(token);
        List<PrisonerDataResponse> prisonerDataResponses = new ArrayList<>();
        List<Prisoner> prisoners = prisonerService.getAll();
        for(int i=0; i<prisoners.size();i++){
            String prisonerId = prisoners.get(i).getPrisonerId();
            String prisonerName = prisoners.get(i).getPrisonerName();
            String photoUrl = prisoners.get(i).getPrisonerPhotoUrl();
            PrisonerHeartBeat prisonerHeartBeat = prisonerDataService.getLastestHeartbeat(prisonerId);
            PrisonerRisk prisonerRisk = prisonerDataService.getLatestRisk(prisonerId);
            PrisonerDataResponse prisonerDataResponse = new PrisonerDataResponse();
            //prisonerDataResponse.setPrisoner(prisoners.get(i));
            //prisonerDataResponse.setHeartBeat(prisonerHeartBeat);
            //prisonerDataResponse.setPrisonerRisk(prisonerRisk);
            prisonerDataResponse.setPrisonerId(prisonerId);
            prisonerDataResponse.setPrisonerName(prisonerName);
            prisonerDataResponse.setPhoteUrl(photoUrl);
            prisonerDataResponse.setHeartBeat(prisonerHeartBeat.getHeartBeat());
            prisonerDataResponse.setRiskValue(prisonerRisk.getRiskValue());
            prisonerDataResponses.add(prisonerDataResponse);
        }
        HttpResponseContent response = new HttpResponseContent();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        response.setData(prisonerDataResponses);
        return response;
    }


}
