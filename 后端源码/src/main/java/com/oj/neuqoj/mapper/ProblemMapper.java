package com.oj.neuqoj.mapper;

import com.oj.neuqoj.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProblemMapper {

    @Select("select * from `problem`")
    public List<Problem> getList();
}
