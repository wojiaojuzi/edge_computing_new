package com.ecs.server;

import com.alibaba.fastjson.JSONObject;
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

/*@Component
@EnableScheduling
public class FrontSchedule {
    private TestNetworksService testNetworksService;
    private TaskService taskService;
    private PrisonerService prisonerService;
    private AbnormalConditionService abnormalConditionService;
    private DeviceService deviceService;
    private static final int timestamp = 2000;

    public FrontSchedule(TestNetworksService testNetworksService, TaskService taskService, PrisonerService prisonerService,
                         AbnormalConditionService abnormalConditionService, DeviceService deviceService
                         ) {
        this.testNetworksService = testNetworksService;
        this.taskService = taskService;
        this.prisonerService = prisonerService;
        this.abnormalConditionService = abnormalConditionService;
        this.deviceService = deviceService;
    }

    @Scheduled(fixedRate = timestamp)
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
    public void getAllTasks(){
        Map<String, Object> maps = new HashMap<>();
        if (FrontServer.getOnlineCount() > 0) {
            List<Task> data = taskService.getAllTasks();
            maps.put("type", "taskGetAllTasks");
            maps.put("code",200);
            maps.put("data",data);
            FrontServer.sendInfo(maps);
        }
    }

    @Scheduled(fixedRate = timestamp)
    public void getAllPrisoner(){
        Map<String, Object> maps = new HashMap<>();
        if (FrontServer.getOnlineCount() > 0) {
            List<Prisoner> prisoners = prisonerService.getAll();
            List<PrisonerAndTask> prisonerAndCarAndTaskList = new ArrayList<>();
            for(int i = 0; i < prisoners.size(); i++){
                Task task = taskService.getPrisonerCar(prisoners.get(i).getPrisonerName());
                PrisonerAndTask prisonerAndCarAndTask = new PrisonerAndTask();
                prisonerAndCarAndTask.setTask(task);
                prisonerAndCarAndTask.setPrisoner(prisoners.get(i));
                prisonerAndCarAndTaskList.add(prisonerAndCarAndTask);
            }

            maps.put("type", "prisonersGet_all");
            maps.put("code",200);
            maps.put("data",prisonerAndCarAndTaskList);
            FrontServer.sendInfo(maps);
        }
    }
    @Scheduled(fixedRate = timestamp)
    public void getAllExceptions() {
        if (FrontServer.getOnlineCount() > 0) {
            List<PrisonerRisk> list = abnormalConditionService.getAllHighRiskLevel("30");
            List<AbnormalResponse> abnormalResponses = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String id = list.get(i).getId();
                String prisonerId = list.get(i).getPrisonerId();
                String createAt = list.get(i).getCreateAt();
                String riskValue = list.get(i).getRiskValue();
                List<PrisonerAnomaly> prisonerAnomalies = abnormalConditionService.getPrisonerAnomalyByRiskId(id);

                AbnormalResponse abnormalResponse = new AbnormalResponse();
                abnormalResponse.setId(id);
                abnormalResponse.setPrisonerId(prisonerId);
                abnormalResponse.setCreateAt(createAt);
                abnormalResponse.setRiskValue(riskValue);
                abnormalResponse.setPrisonerAnomalies(prisonerAnomalies);

                abnormalResponses.add(abnormalResponse);

            }

            Map<String, Object> maps = new HashMap<>();
            maps.put("type", "exceptionsGetAllExceptions");
            maps.put("code", 200);
            maps.put("data", abnormalResponses);
            FrontServer.sendInfo(maps);
        }
    }

    @Scheduled(fixedRate = timestamp)
    public void getAllDevice() {
        if (FrontServer.getOnlineCount() > 0) {
            List<DeviceAndTask> deviceAndTasks = new ArrayList<>();
            List<Device> devices = deviceService.getAll();
            for(int i = 0; i < devices.size(); i++){
                //根据device.user.username 读 task表，取出来，其中有taskno、carNo

                Device device = devices.get(i);
                //device.setDeviceStatus(deviceService.rectifyDeviceStatus(device.getDeviceNo()));   //修改设备状态
                Task task = taskService.getTaskByUserName(device.getUser().getUserName());
                String prisonerName = taskService.getPrisonerNameByUserName(task.getUserName());
                task.setPrisonerName(prisonerName);
                Bracelet bracelet = deviceService.getBracelet(device.getDeviceNo());
                Vervel vervel = deviceService.getVervel(device.getDeviceNo());
                DeviceAndTask deviceAndTask = new DeviceAndTask();
                deviceAndTask.setDevice(device);
                deviceAndTask.setTask(task);
                deviceAndTask.setBracelet(bracelet);
                deviceAndTask.setVervel(vervel);
                deviceAndTasks.add(deviceAndTask);
            }

            Map<String, Object> maps = new HashMap<>();
            maps.put("type", "devicesGet_all");
            maps.put("code",200);
            maps.put("data",deviceAndTasks);
            FrontServer.sendInfo(maps);
        }
    }

    @Scheduled(fixedRate = timestamp)
    public void getAllRunInfo() {
        if (FrontServer.getOnlineCount() > 0) {
            List<Device> devices = deviceService.getAll();
            List<DeviceRunInfo> deviceRunInfoList = new ArrayList<>();
            for(int i = 0; i < devices.size(); i++){
                deviceRunInfoList.add(deviceRunInfoService.getLastestDeviceRunInfoByDeviceNo(devices.get(i).getDeviceNo()));
            }

            Map<String, Object> maps = new HashMap<>();
            maps.put("type", "deviceRunInfoGetAllRunInfo");
            maps.put("code",200);
            maps.put("data",deviceRunInfoList);
            FrontServer.sendInfo(maps);
        }
    }

}*/
