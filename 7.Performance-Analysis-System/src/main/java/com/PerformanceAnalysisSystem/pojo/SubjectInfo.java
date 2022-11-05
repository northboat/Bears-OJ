package com.PerformanceAnalysisSystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectInfo {

    String subject;
    Integer level1;
    Integer level2;
    Integer level3;
    Integer level4;
    Integer level5;
    Integer topNum;
    Integer baseNum;
    Integer sum;
    Double average;

    public SubjectInfo(String subject) {
        this.subject = subject;
        level1 = 0;
        level2 = 0;
        level3 = 0;
        level4 = 0;
        level5 = 0;
        topNum = 0;
        baseNum = 150;
        sum = 0;
        average = 0.0;
    }

    public void updateInfo(Student stu){
        int grade = -1;
        if(subject.equals("语文"))    grade = stu.getGrade().getChinese();
        if(subject.equals("数学"))    grade = stu.getGrade().getMath();
        if(subject.equals("英语"))    grade = stu.getGrade().getEnglish();
        if(subject.equals("物理"))    grade = stu.getGrade().getPhysics();
        if(subject.equals("化学"))    grade = stu.getGrade().getChemistry();
        if(subject.equals("生物"))    grade = stu.getGrade().getBiology();
        if(subject.equals("政治"))    grade = stu.getGrade().getPolitics();
        if(subject.equals("历史"))    grade = stu.getGrade().getHistory();
        if(subject.equals("地理"))    grade = stu.getGrade().getGeography();

        if(grade < 60){
            this.level1++;
        } else if(grade < 70){
            this.level2++;
        } else if(grade < 80){
            this.level3++;
        } else if(grade < 90){
            this.level4++;
        } else{
            this.level5++;
        }
        if(grade > this.topNum){
            this.topNum = grade;
        }
        if(grade < this.baseNum){
            this.baseNum = grade;
        }
        this.sum += grade;
        //保留两位小数
        average = Double.valueOf(String.format("%.1f", sum/(level1+level2+level3+level4+level5+0.0)));
    }
}
