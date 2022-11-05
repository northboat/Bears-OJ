package com.PerformanceAnalysisSystem.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopInfo {

    private String chinese_topStu_name;
    private String math_topStu_name;
    private String english_topStu_name;
    private String physics_topStu_name;
    private String chemistry_topStu_name;
    private String biology_topStu_name;
    private String politics_topStu_name;
    private String history_topStu_name;
    private String geography_topStu_name;

}
