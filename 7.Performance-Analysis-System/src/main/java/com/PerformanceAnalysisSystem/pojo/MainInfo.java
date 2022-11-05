package com.PerformanceAnalysisSystem.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainInfo {
    Integer join_num;
    //平均分
    Double average;
    Integer failed_num;
    //及格率
    Double not_failed_percent;
    //最高分
    Integer top_grade;
    //三科最高分
    Integer top_main_grade;
    //理综最高分
    Integer top_science_grade;
    //文综最高分
    Integer top_liberal_grade;
    //优秀人数
    Integer good_num;
    //优秀率
    Double good_percent;
}
