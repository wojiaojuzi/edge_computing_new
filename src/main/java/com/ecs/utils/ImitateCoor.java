package com.ecs.utils;

import com.ecs.mapper.DeviceGpsMapper;
import com.ecs.mapper.RouteMapper;
import com.ecs.mapper.VervelGpsMapper;
import com.ecs.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class ImitateCoor {
    private static final String mysqlSdfPatternString = "yyyy-MM-dd HH:mm:ss";
    private static int prisoner_count=1, count=2,instruct_count=3;
    public static String lon="0",lat="0",height="0",vervel_lon="0",vervel_lat="0";
    private final RouteMapper routeMapper;
    private final DeviceGpsMapper deviceGpsMapper;
    private final VervelGpsMapper vervelGpsMapper;

    @Autowired
    public ImitateCoor(RouteMapper routeMapper, DeviceGpsMapper deviceGpsMapper, VervelGpsMapper vervelGpsMapper) {
        this.routeMapper = routeMapper;
        this.deviceGpsMapper = deviceGpsMapper;
        this.vervelGpsMapper = vervelGpsMapper;
    }

    @Scheduled(fixedDelay = 1000)
    public void imitate(){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        Route prisonerRoute = routeMapper.getByPointId(prisoner_count);
        vervel_lon = prisonerRoute.getLongitude();
        vervel_lat = prisonerRoute.getLatitude();
        vervelGpsMapper.createVervelGps(vervel_lat,vervel_lon,createAt,"bra123456");
        Route route = routeMapper.getByPointId(count);
        deviceGpsMapper.createDeviceGps("34123",route.getLongitude(),route.getLatitude(),"44",createAt);
        Route instruct_route = routeMapper.getByPointId(instruct_count);
        lon=instruct_route.getLongitude();
        lat=instruct_route.getLatitude();
        height="44";
        prisoner_count++;
        count++;
        instruct_count++;
        if(instruct_count>1000) {
            deviceGpsMapper.deleteAll();
            vervelGpsMapper.deleteAll();
            prisoner_count = 1;
            count = 2;
            instruct_count = 3;
        }
    }
}
