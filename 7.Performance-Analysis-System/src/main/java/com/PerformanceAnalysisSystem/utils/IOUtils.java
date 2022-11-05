package com.PerformanceAnalysisSystem.utils;

import com.PerformanceAnalysisSystem.pojo.Student;
import com.PerformanceAnalysisSystem.service.StudentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class IOUtils {

    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;

    //根据理科排名写文件
    public static void write(String subject) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter("D:\\" + subject + ".txt"));
        List<Student> studentList = new StudentService().getStudents();
        if(subject.equals("all")){
            SortUtils.sortStuBySum(studentList, 0, studentList.size()-1);
        } else if(subject.equals("sciMain")){
            SortUtils.sortStuByScienceMain(studentList);
        } else if(subject.equals("libMain")){
            SortUtils.sortStuByLiberalMain(studentList, 0, studentList.size()-1);
        } else if(subject.equals("main")) {
            SortUtils.sortStuByMainSum(studentList);
        } else if(subject.equals("sci")){
            SortUtils.sortStuByScience(studentList);
        } else if(subject.equals("lib")){
            SortUtils.sortStuByLiberal(studentList);
        } else{
            SortUtils.sortStuBySingleSubject(studentList, 0, studentList.size()-1, subject);
        }

        for(Student stu: studentList) {
            bufferedWriter.write("学号:" + stu.getId() + "\t姓名:" + stu.getName() + "\t性别:" + stu.getGender()
                    + "\n单科成绩: " + "语:" + stu.getGrade().getChinese() + "\t数:" + stu.getGrade().getMath()
                    + "\t英:" + stu.getGrade().getEnglish() + "\t物:" + stu.getGrade().getPhysics()
                    + "\t化:" + stu.getGrade().getChemistry() + "\t生:" + stu.getGrade().getBiology()
                    + "\t政:" + stu.getGrade().getPolitics() + "\t史:" + stu.getGrade().getHistory()
                    + "\t地:" + stu.getGrade().getGeography() + "\n综合成绩: 文综:" + stu.getGrade().getLiberalSum()
                    + "\t理综:" + stu.getGrade().getScienceSum() + "\t文科:" + stu.getGrade().getLiberalMainSum()
                    + "\t理科:" + stu.getGrade().getScienceMainSum() + "\t总分:" + stu.getGrade().getAllSum() +
                    "\r\n\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    public static void merge(MultipartFile file) throws Exception{
        bufferedReader = new BufferedReader((Reader) file);
        String str = bufferedReader.readLine();
        System.out.println(str);
    }


}
