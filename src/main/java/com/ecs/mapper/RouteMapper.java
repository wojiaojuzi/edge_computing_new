package com.ecs.mapper;

import com.ecs.model.Response.CoordinateResponse;
import com.ecs.model.Route;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteMapper {
    @Select("SELECT * FROM route")
    List<Route> getAllRoute();

    @Select("SELECT * FROM route WHERE point_id=#{pointId}")
    Route getByPointId(@Param("pointId")int pointId);

    @Select("SELECT longitude,latitude From route")
    List<CoordinateResponse> getCoordinateResponse();
}
