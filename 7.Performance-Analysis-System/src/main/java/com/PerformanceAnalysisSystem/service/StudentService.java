package com.PerformanceAnalysisSystem.service;

import com.PerformanceAnalysisSystem.dao.StudentDao;
import com.PerformanceAnalysisSystem.pojo.*;
import com.PerformanceAnalysisSystem.utils.SortUtils;
import java.util.ArrayList;
import java.util.List;


public class StudentService {

    private List<Student> students = null;

    public StudentService(){
        students = new ArrayList<>(StudentDao.getStudents());
    }

    //获取总览页面各科基本信息
    public List<SubjectInfo> getSubjectsInfo(){
        List<SubjectInfo> subjectInfos = new ArrayList<>();

        SubjectInfo chinese = new SubjectInfo("语文");
        SubjectInfo math = new SubjectInfo("数学");
        SubjectInfo english = new SubjectInfo("英语");
        SubjectInfo physics = new SubjectInfo("物理");
        SubjectInfo chemistry = new SubjectInfo("化学");
        SubjectInfo biology = new SubjectInfo("生物");
        SubjectInfo politics = new SubjectInfo("政治");
        SubjectInfo history = new SubjectInfo("历史");
        SubjectInfo geography = new SubjectInfo("地理");

        for(Student stu: students){
            chinese.updateInfo(stu);
            math.updateInfo(stu);
            english.updateInfo(stu);
            physics.updateInfo(stu);
            chemistry.updateInfo(stu);
            biology.updateInfo(stu);
            politics.updateInfo(stu);
            history.updateInfo(stu);
            geography.updateInfo(stu);
        }

        subjectInfos.add(chinese);
        subjectInfos.add(math);
        subjectInfos.add(english);
        subjectInfos.add(physics);
        subjectInfos.add(chemistry);
        subjectInfos.add(biology);
        subjectInfos.add(politics);
        subjectInfos.add(history);
        subjectInfos.add(geography);

        return subjectInfos;
    }

    //获取主页信息
    public MainInfo getMainInfo(){
        int join_num = students.size();
        int sum = 0;
        int failed_num = 0;
        int not_failed_num = 0;
        int top_grade = 0;
        int top_main_grade = 0;
        int top_science_grade = 0;
        int top_liberal_grade = 0;
        int good_num = 0;


        for(Student stu: students){
            if(stu.failed()){
                failed_num++;
            } else{
                not_failed_num++;
            }
            sum += stu.getGrade().getAllSum();
            if(stu.getGrade().getAllSum() > top_grade){
                top_grade = stu.getGrade().getAllSum();
                if(stu.getGrade().getAllSum() >= 840){
                    good_num++;
                }
            }
            if(stu.getGrade().getMainSum() > top_main_grade){
                top_main_grade = stu.getGrade().getMainSum();
            }
            if(stu.getGrade().getScienceSum() > top_science_grade){
                top_science_grade = stu.getGrade().getScienceSum();
            }
            if(stu.getGrade().getLiberalSum() > top_liberal_grade){
                top_liberal_grade = stu.getGrade().getLiberalSum();
            }
        }

        //保留三位小数
        double average = Double.parseDouble(String.format("%.3f", (double)sum/join_num));
        double good_percent = Double.parseDouble(String.format("%.3f", (double)good_num/join_num));
        double not_failed_percent = Double.parseDouble(String.format("%.3f", (double)not_failed_num/join_num));



        return new MainInfo(join_num, average, failed_num, not_failed_percent,
                top_grade, top_main_grade, top_science_grade,
                top_liberal_grade, good_num, good_percent);
    }

    //获取当前各科第一名
    public TopInfo getTopInfo(){
        int[] tops = new int[9];
        String[] names = new String[9];
        for(Student s: students){
            if(s.getGrade().getChinese() > tops[0]){
                tops[0] = s.getGrade().getChinese();
                names[0] = s.getName();
            }
            if(s.getGrade().getMath() > tops[1]){
                tops[1] = s.getGrade().getMath();
                names[1] = s.getName();
            }
            if(s.getGrade().getEnglish() > tops[2]){
                tops[2] = s.getGrade().getEnglish();
                names[2] = s.getName();
            }
            if(s.getGrade().getPhysics() > tops[3]){
                tops[3] = s.getGrade().getPhysics();
                names[3] = s.getName();
            }
            if(s.getGrade().getChemistry() > tops[4]){
                tops[4] = s.getGrade().getChemistry();
                names[4] = s.getName();
            }
            if(s.getGrade().getBiology() > tops[5]){
                tops[5] = s.getGrade().getBiology();
                names[5] = s.getName();
            }
            if(s.getGrade().getPolitics() > tops[6]){
                tops[6] = s.getGrade().getPolitics();
                names[6] = s.getName();
            }
            if(s.getGrade().getHistory() > tops[7]){
                tops[7] = s.getGrade().getHistory();
                names[7] = s.getName();
            }
            if(s.getGrade().getGeography() > tops[8]){
                tops[8] = s.getGrade().getGeography();
                names[8] = s.getName();
            }
        }


        return new TopInfo(names[0], names[1], names[2], names[3],
                names[4], names[5], names[6], names[7], names[8]);
    }

    //获取"当前顺序"下所有学生信息
    public List<Student> getStudents(){
        return students;
    }

    //获取尖子生（总分前九）信息，根据总分排序
    public List<Student> getTopStudent(){
        SortUtils.sortStuBySum(students, 0, students.size()-1);
        List<Student> topStu = new ArrayList<>();
        int count = 0;
        for(Student stu: students){
            topStu.add(stu);
            count++;
            if(count == 9){
                break;
            }
        }
        return topStu;
    }

    //获取三科排名
    public List<Student> getStudentsSortedByMainSubjects(){
        SortUtils.sortStuByMainSum(students);
        return students;
    }

    //获取理科排名
    public List<Student> getStudentsSortedByScienceSubjects(){
        SortUtils.sortStuByScienceMain(students);
        return students;
    }

    //获取文科排名
    public List<Student> getStudentSortedByLiberalSubjects(){
        SortUtils.sortStuByLiberalMain(students, 0, students.size()-1);
        return students;
    }

    //获取理综排名
    public List<Student> getStudentSortedByScience(){
        SortUtils.sortStuByScience(students);
        return students;
    }

    //获取文综排名
    public List<Student> getStudentSortedByLiberal(){
        SortUtils.sortStuByLiberal(students);
        return students;
    }

    //获取单科排名
    public List<Student> getStudentSortedBySingleSubject(String subject){
        SortUtils.sortStuBySingleSubject(students, 0, students.size()-1, subject);
        return students;
    }

    //获取排名信息
    private int[] getRanks(Student stu){
        int[] ranks = new int[15];
        for(int i = 0; i < 15; i++){
            ranks[i] = 1;
        }
        for(Student s: students){
            if(s.getGrade().getChinese() > stu.getGrade().getChinese()){
                ranks[0]++;
            }
            if(s.getGrade().getMath() > stu.getGrade().getMath()){
                ranks[1]++;
            }
            if(s.getGrade().getEnglish() > stu.getGrade().getEnglish()){
                ranks[2]++;
            }
            if(s.getGrade().getPhysics() > stu.getGrade().getPhysics()){
                ranks[3]++;
            }
            if(s.getGrade().getChemistry() > stu.getGrade().getChemistry()){
                ranks[4]++;
            }
            if(s.getGrade().getBiology() > stu.getGrade().getBiology()){
                ranks[5]++;
            }
            if(s.getGrade().getPolitics() > stu.getGrade().getPolitics()){
                ranks[6]++;
            }
            if(s.getGrade().getHistory() > stu.getGrade().getHistory()){
                ranks[7]++;
            }
            if(s.getGrade().getGeography() > stu.getGrade().getGeography()){
                ranks[8]++;
            }
            if(s.getGrade().getMainSum() > stu.getGrade().getMainSum()){
                ranks[9]++;
            }
            if(s.getGrade().getLiberalSum() > stu.getGrade().getLiberalSum()){
                ranks[10]++;
            }
            if(s.getGrade().getScienceSum() > stu.getGrade().getScienceSum()){
                ranks[11]++;
            }
            if(s.getGrade().getLiberalMainSum() > stu.getGrade().getLiberalMainSum()){
                ranks[12]++;
            }
            if(s.getGrade().getScienceMainSum() > stu.getGrade().getScienceMainSum()){
                ranks[13]++;
            }
            if(s.getGrade().getAllSum() > stu.getGrade().getAllSum()){
                ranks[14]++;
            }
        }

        return ranks;
    }
    //设置学生排名
    public void setRanks(Student stu){
        int[] ranks = getRanks(stu);
        Ranks rank = new Ranks(ranks[0], ranks[1], ranks[2], ranks[3], ranks[4], ranks[5], ranks[6], ranks[7], ranks[8],
                ranks[9], ranks[10], ranks[11], ranks[12], ranks[13], ranks[14]);
        stu.setRank(rank);
    }
}
