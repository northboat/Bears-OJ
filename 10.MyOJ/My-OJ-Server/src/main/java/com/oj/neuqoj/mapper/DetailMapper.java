package com.oj.neuqoj.mapper;


import com.oj.neuqoj.pojo.Detail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DetailMapper {
    //根据题号查询题目详细信息
    @Select("select * from `detail` where `num`=#{num}")
    Detail getDetail(int num);

    @Insert("")
    void addDetail(Detail detail);

}
