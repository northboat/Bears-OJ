package com.oj.neuqoj.mapper;


import com.oj.neuqoj.pojo.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReplyMapper {

    @Select("select * from `reply` where `topic`=#{topic}")
    List<Reply> getReply(int topic);


    @Insert("insert into `reply`(`topic`, `from`, `to`, `content`) values(#{topic}, #{from}, #{to}, #{content})")
    void reply(Reply reply);

}
