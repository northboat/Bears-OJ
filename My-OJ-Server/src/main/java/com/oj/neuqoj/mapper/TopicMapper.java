package com.oj.neuqoj.mapper;

import com.oj.neuqoj.pojo.Topic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopicMapper {

    @Select("select * from `topic`")
    List<Topic> getAllTopic();

    @Select("select * from `topic` where `tag`=#{tag}")
    List<Topic> getTopicByTag(String tag);

    @Select("select * from `topic` where `num`=#{num}")
    Topic getTopic(int num);

    @Insert("insert into `topic`(`title`, `desc`, `content`, `from`, `tag`, `contact`) values(#{title}, #{desc}, #{content}, #{from}, #{tag}, #{contact})")
    void addTopic(Topic topic);

    @Select("select * from `topic` where `from`=#{name}")
    List<Topic> getTopicsByName(String name);

    @Delete("delete from `topic` where `num`=#{num}")
    void deleteTopic(int num);

}
