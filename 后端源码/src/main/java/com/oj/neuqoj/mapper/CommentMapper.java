package com.oj.neuqoj.mapper;

import com.oj.neuqoj.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Select("select * from `comment` where `question`=#{question}")
    List<Comment> getComments(int question);

    @Insert("insert into `comment`(question, `to`, `from`, content) values(#{question}, #{to}, #{from}, #{content})")
    void comment(Comment comment);
}
