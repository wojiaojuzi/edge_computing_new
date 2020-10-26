package com.ecs.server;

import com.alibaba.fastjson.JSONObject;
import com.ecs.mapper.VervelGpsMapper;
import com.ecs.model.*;
import com.ecs.model.Response.AbnormalResponse;
import com.ecs.service.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class FrontSchedule {
    private TestNetworksService testNetworksService;
    private VervelGpsMapper vervelGpsMapper;
    private static final int timestamp = 2000;

    public FrontSchedule(TestNetworksService testNetworksService, VervelGpsMapper vervelGpsMapper) {
        this.testNetworksService = testNetworksService;
        this.vervelGpsMapper = vervelGpsMapper;
    }

    //@Scheduled(fixedRate = timestamp)
    public void networkCondition() throws Exception{
        Map<String, Object> maps = new HashMap<>();
        if (FrontServer.getOnlineCount() > 0){
            //System.out.println(nodeList);
            JSONObject json = new JSONObject();
            //json.put("delay",testNetworksService.getNetworkDelay());
            //json.put("delay",testNetworksService.getNetworkDelay());
            //json.toJSONString();
            maps.put("type", "networkCondition");
            maps.put("code",200);
            maps.put("data",testNetworksService.getNetworkDelay());
            FrontServer.sendInfo(maps);
        }
    }

    @Scheduled(fixedRate = timestamp)
    public void escapePrisonerGps(){
        Map<String, Object> maps = new HashMap<>();
        if (FrontServer.getOnlineCount() > 0){
            JSONObject json = new JSONObject();
            maps.put("type", "vervelGps");
            maps.put("code",200);
            maps.put("data",vervelGpsMapper.getAll());
            FrontServer.sendInfo(maps);
        }
    }

}
