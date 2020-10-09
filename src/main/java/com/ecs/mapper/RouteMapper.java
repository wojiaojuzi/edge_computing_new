package com.ecs.mapper;

import com.ecs.model.Route;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteMapper {
    @Select("SELECT * FROM route")
    List<Route> getAllRoute();
}
