package com.oj.neuqoj.mapper;

import com.oj.neuqoj.pojo.Painting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaintingMapper {

    @Select("select * from `painting` order by `num` desc limit 2")
    List<Painting> getNewPaintings();

    @Select("select * from `painting` order by `thumb` desc limit 2")
    List<Painting> getHotPaintings();

    @Select("select * from `painting`")
    List<Painting> getPaintings();

    @Insert("insert into `painting`(`title`, `desc`, `from`, `content`) values(#{title}, #{desc}, #{from}, #{content})")
    void addPainting(Painting painting);

    @Select("select * from `painting` where `from`=#{name}")
    List<Painting> getPaintingsByName(String name);

    @Delete("delete from `painting` where `num`=#{num}")
    void deletePainting(int num);

}
