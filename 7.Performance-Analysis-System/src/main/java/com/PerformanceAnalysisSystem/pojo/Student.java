package com.PerformanceAnalysisSystem.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private Integer gender; //0为女，1为男
    private Grades grade;
    private Ranks rank;

    public boolean failed(){
        if(grade.getAllSum() < 630){
            return true;
        }
        if(grade.getChinese() < 90 || grade.getMath() < 90 || grade.getEnglish() < 90 ||
           grade.getPhysics() < 60 || grade.getChemistry() < 60 || grade.getBiology() < 60 ||
           grade.getPolitics() < 60 || grade.getHistory() < 60 || grade.getGeography() < 60){
            return true;
        }
        return false;
    }

    public Student(Integer id, String name, Integer gender, Grades grade) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
    }

}
