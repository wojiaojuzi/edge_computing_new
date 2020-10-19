package com.ecs.utils;

import com.ecs.mapper.DeviceGpsMapper;
import com.ecs.mapper.RouteMapper;
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
    private static int count=1;
    private final RouteMapper routeMapper;
    private final DeviceGpsMapper deviceGpsMapper;

    @Autowired
    public ImitateCoor(RouteMapper routeMapper, DeviceGpsMapper deviceGpsMapper) {
        this.routeMapper = routeMapper;
        this.deviceGpsMapper = deviceGpsMapper;
    }

    @Scheduled(fixedDelay = 1000)
    public void imitate(){
        SimpleDateFormat mysqlSdf = new SimpleDateFormat(mysqlSdfPatternString);
        Date createTime = new Date();
        String createAt = mysqlSdf.format(createTime);
        Route route = routeMapper.getByPointId(count);
        deviceGpsMapper.createDeviceGps("34123",route.getLongitude(),route.getLatitude(),"44",createAt);
        count++;
        if(count>1000) {
            deviceGpsMapper.deleteAll();;
            count = 1;
        }
    }
}
