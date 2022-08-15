package com.oj.neuqoj.mapper;


import com.oj.neuqoj.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /*获取用户列表*/
    @Select("select `name` from `user`")
    List<String> getAllUsersName();

    /*添加用户*/
    @Insert("insert into `user`(`account`, `name`, `password`, `root`, `level`) values(#{account}, #{name}, #{password}, #{root}, #{level})")
    void addUser(User user);

    /*查询用户*/
    //根据昵称查询用户
    @Select("select * from `user` where `name`=#{name}")
    User getUserByName(String name);
    //根据账号查询用户
    @Select("select * from `user` where `account`=#{account}")
    User getUserByAccount(String account);

    //注销账号
    @Delete("delete from `user` where `name` = #{name}")
    void deleteUser(String name);

    /*添加管理员权限*/
    @Update("update `user` set `isRoot`=1 where `account`=#{account}")
    void turnRoot(String account);

    /*移除管理员权限*/
    @Update("update `user` set `isRoot`=0 where `account`=#{account}")
    void deleteRoot(String account);

    /*升级*/
    @Update("update `user` set `level`=`level`+1 where `name`=#{name}")
    void upLevel(String name);

    /*改密码*/
    //{"account", account}, {"password", password}
    @Update("update `user` set `password`=#{password} where `account`=#{account}")
    void changePassword(User user);
}
