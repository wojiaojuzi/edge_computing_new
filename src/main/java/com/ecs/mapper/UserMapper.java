package com.ecs.mapper;

import com.ecs.model.Car;
import com.ecs.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: zhaoone
 * @Description:添加功能
 * @Date: Created on 2019/10/14
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_name=#{userName};")
    User getByUserName(@Param("userName") String userName);

    @Select("SELECT user_name FROM user WHERE user_id=#{userId};")
    String getUserNameByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM user WHERE user_id=#{userId} AND password=#{password};")
    User getByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    @Select("SELECT token_create_at FROM user WHERE user_id =#{id};")
    String getTokenCreateTime(@Param("id") String id);

    @Insert("INSERT INTO user(user_id,username,password,id_card,create_at,token_create_at) " +
            "VALUES(#{userId},#{userName},#{password},#{idCard},#{createAt},#{tokenCreateAt});")
    void createUser(User user);

    @Update("UPDATE user SET token_create_at=#{tokenCreateTime} WHERE user_id =#{userId};")
    void updateTokenCreateTimeByUserId(@Param("tokenCreateTime") String tokenCreateTime, @Param("userId") String userId);


    @Update("UPDATE user SET phone=#{phone} WHERE id =#{id};")
    void updatePhoneById(@Param("phone") String phone, @Param("id") String id);

    @Delete("DELETE FROM user WHERE id=#{id};")
    void deleteById(@Param("id") String id);


    @Select("SELECT user_id FROM user WHERE user_name=#{userName};")
    String getUserIdByUserName(@Param("userName") String userName);

    @Select("SELECT * FROM user;")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE user_id=#{userId};")
    User getByUserId(@Param("userId") String userId);
}
