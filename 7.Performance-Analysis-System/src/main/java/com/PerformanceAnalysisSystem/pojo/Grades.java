package com.PerformanceAnalysisSystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grades {

    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer physics;
    private Integer chemistry;
    private Integer biology;
    private Integer politics;
    private Integer history;
    private Integer geography;
    private Integer mainSum;
    private Integer scienceSum;
    private Integer liberalSum;
    private Integer allSum;

    private Integer scienceMainSum;
    private Integer liberalMainSum;

    public Grades(Integer chinese, Integer math, Integer english, Integer physics, Integer chemistry,
                  Integer biology, Integer politics, Integer history, Integer geography) {
        this.chinese = chinese;
        this.math = math;
        this.english = english;
        this.physics = physics;
        this.chemistry = chemistry;
        this.biology = biology;
        this.politics = politics;
        this.history = history;
        this.geography = geography;
        mainSum = chinese + english + math;
        scienceSum = physics + chemistry + biology;
        liberalSum = politics + history + geography;
        scienceMainSum = mainSum + scienceSum;
        liberalMainSum = mainSum + liberalSum;
        allSum = chinese + math + english + physics + chemistry + biology + politics + history + geography;
    }
}
