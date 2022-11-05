package com.oj.neuqoj.mapper;


import com.oj.neuqoj.pojo.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NewsMapper {


    @Select("select * from `news` where `version`=#{version}")
    News getNews(int version);

    @Update("update `news` set c_using = c_using+1 where `version` = #{version}")
    void cUsingUp(int version);

    @Update("update `news` set java_using = java_using+1 where `version` = #{version}")
    void javaUsingUp(int version);

    @Update("update `news` set python_using = python_using+1 where `version` = #{version}")
    void pythonUsingUp(int version);

    @Update("update `news` set visit_num = visit_num+1 where `version` = #{version}")
    void visitUp(int version);

    @Update("update `news` set user_num = user_num+1 where `version` = #{version}")
    void userUp(int version);


}
