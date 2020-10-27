package com.ecs.server;

import com.alibaba.fastjson.JSONObject;
import com.ecs.mapper.*;
import com.ecs.model.*;
import com.ecs.model.Response.AbnormalResponse;
import com.ecs.model.Response.VervelGpsResponse;
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
    private VervelMapper vervelMapper;
    private DeviceMapper deviceMapper;
    private ConvoyMapper convoyMapper;
    private PrisonerMapper prisonerMapper;
    private static final int timestamp = 2000;

    public FrontSchedule(TestNetworksService testNetworksService, VervelGpsMapper vervelGpsMapper, VervelMapper vervelMapper,
                         DeviceMapper deviceMapper, ConvoyMapper convoyMapper, PrisonerMapper prisonerMapper) {
        this.testNetworksService = testNetworksService;
        this.vervelGpsMapper = vervelGpsMapper;
        this.vervelMapper = vervelMapper;
        this.deviceMapper = deviceMapper;
        this.convoyMapper = convoyMapper;
        this.prisonerMapper = prisonerMapper;
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
            List<VervelGps> vervelGpsList = vervelGpsMapper.getAll();
            List<VervelGpsResponse> vervelGpsResponseList = new ArrayList<>();
            for(int i=0;i<vervelGpsList.size();i++){
                VervelGps vervelGps = vervelGpsList.get(i);
                VervelGpsResponse vervelGpsResponse = new VervelGpsResponse();
                String deviceNo = vervelMapper.getDeviceNoByVervelNo(vervelGps.getVervelNo());
                String userId = deviceMapper.getByDeviceNo(deviceNo).getUserId();
                String prisonerId = convoyMapper.getPrisonerIdByUserId(userId);
                String prisonerName = prisonerMapper.getPrisonerNameByPrisonerId(prisonerId);

                vervelGpsResponse.setLongitude(vervelGps.getLongitude());
                vervelGpsResponse.setLatitude(vervelGps.getLatitude());
                vervelGpsResponse.setCreateAt(vervelGps.getCreateAt());
                vervelGpsResponse.setPrisonerId(prisonerId);
                vervelGpsResponse.setPrisonerName(prisonerName);
                vervelGpsResponseList.add(vervelGpsResponse);
            }
            maps.put("type", "vervelGps");
            maps.put("code",200);
            maps.put("data",vervelGpsResponseList);
            FrontServer.sendInfo(maps);
        }
    }

}
