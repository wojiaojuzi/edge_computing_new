package com.ecs.model.old;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Zhaoone on 2019/11/12
 **/
/*public interface VideoRiskMapper {

    @Select("SELECT video_risk_level FROM video_risk\n" +
            "WHERE timestamp =\n" +
            "  (SELECT MAX(timestamp) FROM video_risk\n" +
            "   WHERE car_no = #{carNo}) AND car_no=#{carNo};")
    String getLastestVideoRiskLevelByCarNo(@Param("carNo") String carNo);

    @Select("SELECT * FROM video_risk\n" +
            "WHERE timestamp =\n" +
            "  (SELECT MAX(timestamp) FROM video_risk\n" +
            "   WHERE car_no = #{carNo}) AND car_no=#{carNo};")
    VideoDetection getLastestVideoDetectionTypeByCarNo(@Param("carNo") String carNo);

}*/

