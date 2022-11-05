package com.PerformanceAnalysisSystem.dao;
import com.PerformanceAnalysisSystem.pojo.*;
import java.util.*;



public class StudentDao {

    //模拟数据库
    private static Map<Integer, Student> students = null;

    static{
        students = new HashMap<>();
        students.put(1, new Student(1,"球吊",1, new Grades(136,148,136,100,100,96,98,97,92)));
        students.put(2, new Student(2,"郭郭",1, new Grades(107,115,129,93,81,73,72,78,70)));
        students.put(3, new Student(3,"戴某",1, new Grades(112,112,132,103,87,80,88,82,66)));
        students.put(4, new Student(4,"熊熊",1, new Grades(112,112,136,104,83,79,60,66,59)));
        students.put(5, new Student(5,"强哥",1, new Grades(112,112,121,105,85,81,60,60,72)));
        students.put(6, new Student(6,"涂涂",1, new Grades(101,118,132,83,86,76,77,77,77)));
        students.put(7, new Student(7,"陶末",1, new Grades(118,114,137,88,86,82,85,90,89)));
        students.put(8, new Student(8,"彭奇",1, new Grades(98,118,119,84,74,72,80,80,80)));
        students.put(9, new Student(9,"兴根",1, new Grades(108,113,120,76,77,69,83,87,90)));
    }

    //ID作为主键自增
    private static Integer initId = 10;
    //增加学生，当stu.id为空时，将initId自动赋值
    public static void add(Student stu){
        if(stu.getId() == null) {
            stu.setId(initId++);
        }
        if(students.containsKey(stu.getId())){
            update(stu.getId(), stu.getGrade());
            return;
        }
        students.put(stu.getId(), stu);
    }

    public static Map<Integer, Student> getStudentMap(){
        return students;
    }

    //获取全部学生信息
    public static Collection<Student> getStudents(){
        return students.values();
    }

    //通过id获取单个学生
    public static Student getStudentById(Integer id){
        return students.get(id);
    }

    //通过name获取单个学生
    public static Collection<Student> getStudentsByName(String name){
        Collection<Student> student = new ArrayList<>();
        for(Student stu: students.values()){
            if(stu.getName().equals(name)) {
                student.add(stu);
            }
        }
        return student;
    }

    //删除学生
    public static void delete(Integer id){
        students.remove(id);
    }

    //修改学生信息
    public static void update(Integer id, Grades grades){
        Student stu = students.get(id);
        Student student = new Student(id, stu.getName(), stu.getGender(), grades);
        students.put(id, student);
    }
}
