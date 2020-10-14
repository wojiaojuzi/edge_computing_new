package com.ecs.service;

import com.alibaba.fastjson.JSONObject;
import com.ecs.mapper.ConvoyMapper;
import com.ecs.model.Car;
import com.ecs.model.Convoy;
import com.ecs.model.DeviceGps;
import com.ecs.model.Response.CarGpsResponse;
import com.ecs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

import static com.ecs.constant.Networks.Cloud_IPADDRESS;

/**
 * Created by Zhaoone on 2019/12/19
 **/
@Service
public class CloudService {

    private final PrisonerService prisonerService;
    private final TaskService taskService;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final ConvoyMapper convoyMapper;
    private final CarService carService;
    private final DeviceService deviceService;
    private final ConvoyService convoyService;

    @Autowired
    public CloudService(PrisonerService prisonerService, TaskService taskService, UserService userService,
                        RestTemplate restTemplate, ConvoyMapper convoyMapper, CarService carService,
                        DeviceService deviceService, ConvoyService convoyService) {
        this.prisonerService = prisonerService;
        this.taskService = taskService;
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.convoyMapper = convoyMapper;
        this.carService = carService;
        this.deviceService = deviceService;
        this.convoyService = convoyService;
    }



    @Async
    public void physiologyData(String prisonerId, String heartbeat, String height){
        //        TODO:
//             3、轮训调用脚环gps信息

//             1、调用云端接口（生理数据、gps数据）????
//             2、超距上传，插入数据库，需要字段comment、high_sign
//


        /*
         *   传变量
         */
//        MultiValueMap<String , String> requestParam = new LinkedMultiValueMap<>();
//        requestParam.set("prisonerId",prisonerId);
//        requestParam.set("heartbeat", heartbeat);
//        requestParam.set("height", height);
//        String result = this.restTemplate.
//                postForObject("http://10.128.225.158:8089/upload/spg",    //改成云端接口
//                        requestParam, String.class);
//        System.out.println(result);

        /*
         *   传json
         */
//        "ip:port/api/v1/prisoner"
//        taskId、prisonerId、prisonerName、heartbeat、height、posture
//        json:
//        {
//            "msg":"done"
//        }
//        {
//            "msg":"failed"
//        }

        String prisonerName = prisonerService.getPrisonerNameByPrisonerId(prisonerId);
        String taskNo = convoyMapper.getTaskNoByPrisonerId(prisonerName);
        //String taskNo = taskService.getPrisonerCar(prisonerName).getTaskNo();                 //taskId = taskNo
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("prisonerId", prisonerId);
        map.add("taskId", taskNo);
        map.add("prisonerName", prisonerName);
        map.add("heartbeat", heartbeat);
        map.add("height", height);
        map.add("posture", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String ,String>> request = new HttpEntity<MultiValueMap<String, String>>(map,headers);

        String msg = this.restTemplate.postForEntity("http://"+Cloud_IPADDRESS+"/api/v1/prisoner", request, String.class).getBody();
        System.err.println("向云传输生理数据"+msg);
    }

    @Async
    public void GpsData(String userId,String deviceNo,String longitude,String latitude, String height){

        /*
         *   传变量
         */
//        MultiValueMap<String , String> requestParam = new LinkedMultiValueMap<>();
//        requestParam.set("userId",userId);
//        requestParam.set("longitude", longitude);
//        requestParam.set("latitude", latitude);
//        String result = this.restTemplate.
//                postForObject("http://10.128.225.158:8089/upload/gps",      //改成云端接口
//                        requestParam, String.class);
//        System.out.println(result);

        /*
         *   传json
         */
//        "ip:port/api/v1/deploy"
//        taskId、policeId、long、lat
//        json:
//        {
//            "msg":"done"
//        }
//        {
//            "msg":"failed"
//        }


        /*String userName = userService.getByUserId(userId).getUserName();        //policeId = userId
        String taskNo = convoyMapper.getConvoyByUserId(userId).getTaskNo();

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("taskId", taskNo);
        map.add("policeId", userId);
        map.add("long", longitude);
        map.add("lat", latitude);
        map.add("height",height);*/
        List<CarGpsResponse> carGpsResponses = new ArrayList<>();
        List<Car> cars = carService.getAllCars();
        for(int i = 0; i < cars.size(); i++){
            List<Convoy> convoys = convoyService.getByCarNo(cars.get(i).getCarNo());
            CarGpsResponse carGpsResponse = new CarGpsResponse();
            //List<DeviceGps> deviceGpsList = new ArrayList<>();
            carGpsResponse.setCarNo(cars.get(i).getCarNo());
            carGpsResponse.setCarType(cars.get(i).getType());
            carGpsResponse.setColor(cars.get(i).getColor());
            carGpsResponse.setTaskNo(convoys.get(0).getTaskNo());

            for(int j=0;j<convoys.size();j++){
                String user_id = convoys.get(j).getUserId();
                DeviceGps deviceGps = null;
                if(deviceService.getByUserId(user_id)!=null) {
                    String device_no = deviceService.getByUserId(user_id).getDeviceNo();
                    deviceGps = deviceService.getDeviceGpsBydeviceNo(device_no);
                    if(deviceGps!=null){
                        carGpsResponse.setHeight(deviceGps.getHeight());
                        carGpsResponse.setLongitude(deviceGps.getLongitude());
                        carGpsResponse.setLatitude(deviceGps.getLatitude());
                        break;
                    }
                }
            }


            carGpsResponses.add(carGpsResponse);
        }
        String data = JsonUtil.listToJson(carGpsResponses);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("data",data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String ,String>> request = new HttpEntity<MultiValueMap<String, String>>(map,headers);


        String msg= restTemplate.postForEntity("http://"+Cloud_IPADDRESS+"/api/v1/deploy",request,String.class).getBody();
        System.err.println("向云传输位置数据"+msg);
    }

//    public JSONObject escapeGPS(){
////        JSONObject msg = this.restTemplate.getForEntity("http://"+Cloud_IPADDRESS+"/api/v1/escape", JSONObject.class).getBody();
//        JSONObject msg;
//        return msg;
//    }

}
