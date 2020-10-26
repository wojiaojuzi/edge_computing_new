package com.ecs.mapper;


import com.ecs.model.VervelGps;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface VervelGpsMapper {
    @Select("select * from vervel_gps where create_at=(select max(create_at) from (select * from vervel_gps where vervel_no=#{vervelNo}) as a) and vervel_no=#{vervelNo}")
    VervelGps getByVervelNo(@Param("vervelNo") String VervelNo);

    @Select("select * from vervel_gps where create_at=(select max(create_at) from (select * from vervel_gps) as a) group by vervel_no")
    List<VervelGps> getAll();

    @Insert("INSERT INTO vervel_gps(latitude,longitude,create_at,vervel_no) values(#{latitude},#{longitude},#{createAt},#{vervelNo})")
    void createVervelGps(@Param("latitude")String latitude,@Param("longitude")String longitude,@Param("createAt")String createAt,@Param("vervelNo")String vervelNo);

    @Delete("delete from vervel_gps")
    void deleteAll();
}
