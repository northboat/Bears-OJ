package com.PerformanceAnalysisSystem.dao;

import com.PerformanceAnalysisSystem.pojo.Teacher;
import java.util.HashMap;
import java.util.Map;

public class TeacherDao {
    private static Map<String, Teacher> teachers = null;

    static{
        teachers = new HashMap<>();
        teachers.put("NorthBoat", new Teacher("NorthBoat", "011026"));
        teachers.put("hahaha", new Teacher("hahaha", "123456"));
        teachers.put("sad", new Teacher("sad", "123456"));
    }

    public static Map getTeachers(){
        return teachers;
    }
}
