package com.PerformanceAnalysisSystem.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranks {
    private Integer chi;
    private Integer math;
    private Integer en;
    private Integer phy;
    private Integer chem;
    private Integer bio;
    private Integer pol;
    private Integer his;
    private Integer geo;
    private Integer main;
    private Integer liberal;
    private Integer science;
    private Integer mainLib;
    private Integer mainSci;

    private Integer all;
}
