package com.ecs.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WanMapper {
    @Select("SELECT prisoner_info FROM risk_warning")
    List<String> getPrisonerInfo();

    @Select("SELECT exception FROM risk_warning")
    List<String> getException();
}
