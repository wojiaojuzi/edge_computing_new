package com.ecs.mapper;

import com.ecs.model.PrisonerAnomaly;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PrisonerAnomalyMapper {
    @Select("SELECT * FROM prisoner_anomaly")
    List<PrisonerAnomaly> getAll();

    @Select("SELECT * FROM prisoner_anomaly WHERE riskId=#{riskId}")
    List<PrisonerAnomaly> getByRiskId(@Param("riskId")String riskId);

    @Select("select * from prisoner_anomaly where create_at=(select max(create_at) from (select * from prisoner_anomaly where risk_id=#{riskId}) as a) and risk_id=#{riskId} ")
    PrisonerAnomaly getLatestByRiskId(@Param("riskId")String riskId);

    @Insert("INSERT INTO prisoner_anomaly(risk_id,create_at,level,deal_state,misdeclaration,description,comment) " +
            "VALUES(#{riskId},#{createAt},#{level},#{dealState},#{misdeclaration},#{description},#{comment})")
    void createPrisonerAnomaly(@Param("riskId")String riskId, @Param("createAt")String createAt, @Param("level")String level,
                               @Param("dealState")boolean dealState, @Param("misdeclaration")boolean misdeclaration,
                               @Param("description")String description, @Param("comment")String comment);

    @Update("UPDATE prisoner_anomaly SET deal_state=#{dealState} WHERE risk_id=#{riskId}")
    void updateDealStateByRiskId(@Param("dealState")boolean dealState,@Param("riskId")String riskId);

    @Update("UPDATE prisoner_anomaly SET misdeclaration=#{misdeclaration} WHERE risk_id=#{riskId}")
    void updateMisdeclarationByRiskId(@Param("misdeclaration")boolean misdeclaration,@Param("riskId")String riskId);

    @Update("UPDATE prisoner_anomaly SET comment=#{comment} WHERE risk_id=#{riskId}")
    void updateCommentByRiskId(@Param("comment")String comment,@Param("riskId")String riskId);
}
