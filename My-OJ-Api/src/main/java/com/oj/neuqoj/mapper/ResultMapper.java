package com.oj.neuqoj.mapper;

import com.oj.neuqoj.pojo.Result;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ResultMapper {

    @Select("select * from `result` where `account` = #{account} and `num` = #{num}")
    Result getResult(@Param("account")String account, @Param("num")int num);

    @Insert("insert into `result`(`account`, `num`, `title`, `key1`, `key2`, `key3`, `key4`, `val1`, `val2`, `val3`, `val4`, `code`) values (#{account}, #{num}, #{title}, #{key1}, #{key2}, #{key3}, #{key4}, #{val1}, #{val2}, #{val3}, #{val4}, #{code})")
    void setResult(Result result);

    @Update("update `result` set `title`=#{title}, `key1`=#{key1}, `key2`=#{key2}, `key3`=#{key3}, `key4`=#{key4}, `val1`=#{val1}, `val2`=#{val2}, `val3`=#{val3}, `val4`=#{val4}, `code`=#{code} where `account`=#{account} and `num`=#{num}")
    void updateResult(Result result);
}
