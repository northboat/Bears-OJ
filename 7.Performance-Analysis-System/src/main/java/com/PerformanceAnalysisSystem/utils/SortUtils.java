package com.PerformanceAnalysisSystem.utils;

import com.PerformanceAnalysisSystem.dao.StudentDao;
import com.PerformanceAnalysisSystem.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class SortUtils {

    //交换两学生在表中位置
    public static void swap(List<Student> students, int i, int j){
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }

    //按照总分从大往小排序：快排
    public static void sortStuBySum(List<Student> students, int left, int right){
        if(left >= right){
            return;
        }
        int mid = (left+right)/2;
        int cur = students.get(mid).getGrade().getAllSum();
        swap(students, mid, right);
        int position = left;
        for(int i = left; i < right; i++){
            if(students.get(i).getGrade().getAllSum() >= cur){
                swap(students, position, i);
                position++;
            }
        }
        swap(students, position, right);
        sortStuBySum(students, left, position-1);
        sortStuBySum(students, position+1, right);
    }

    //按照三科总分排序：二分插入
    public static void sortStuByMainSum(List<Student> students){
        int n = students.size();
        for(int i = 1; i < n; i++){
            //初始左边界和右边界，记录当前元素
            int left = 0, right = i-1;
            Student cur = students.get(i);
            while(right>=left){
                //记录中间元素
                int mid = (right+left)/2;
                //当中间元素大于当前元素，将右边界记为中间-1
                if(students.get(mid).getGrade().getMainSum() <= cur.getGrade().getMainSum()){
                    right = mid-1;;
                }else{ //当中间元素小于等于当前元素，将左边界记为中间+1
                    left = mid+1;
                }
            }
            //此时left>right，已然满足条件left右侧全小于等于cur，左侧全大于cur
            //将left右侧元素向右移一位，给cur腾出位置用于插入
            for(int j = i; j > left; j--){
                students.set(j, students.get(j-1));
            }
            //将第i位元素插入left位，实现一次插入
            students.set(left, cur);
        }
    }

    //按照理科排序：堆排序
    public static void sortStuByScienceMain(List<Student> students){
        int n = students.size() - 1;
        //构造小顶堆，一定要从后往前构造
        //这样在树中为从下层向上层构造，逐步将大根上移，防止漏移
        for (int i = n / 2; i >= 0; i--) {
            heapAdjust(students, i, n);
        }

        //将最小元素移到最后，再对前i-1个元素进行调整，构造新的小顶堆
        for(int i = n; i > 0; i--){
            swap(students, 0, i);
            heapAdjust(students, 0, i-1);
        }
    }
    //调整小顶堆
    public static void heapAdjust(List<Student> students, int parent, int length){
        int child = parent*2+1;
        while(child <= length){

            if(child+1 <= length &&
               students.get(child).getGrade().getScienceMainSum() >
               students.get(child+1).getGrade().getScienceMainSum()){
                child++;
            }

            if(students.get(parent).getGrade().getScienceMainSum() <
               students.get(child).getGrade().getScienceMainSum()){
                break;
            }
            swap(students, parent, child);
            parent = child;
            child = parent*2+1;
        }
    }


    //按照文科排序：归并排序
    private static List<Student> temp = new ArrayList<>(StudentDao.getStudents());
    public static void sortStuByLiberalMain(List<Student> students, int left, int right){
        if(left >= right){
            return;
        }
        int mid = (left+right)/2;
        sortStuByLiberalMain(students, left, mid);
        sortStuByLiberalMain(students, mid+1, right);
        int i = left, j = mid+1, count = 0;
        while(i <= mid && j <= right){
            if(students.get(i).getGrade().getLiberalMainSum() >
               students.get(j).getGrade().getLiberalMainSum()){
                temp.set(count++, students.get(i++));
            } else{
                temp.set(count++, students.get(j++));
            }
        }
        while(i <= mid){
            temp.set(count++, students.get(i++));
        }
        while(j <= right){
            temp.set(count++, students.get(j++));
        }
        for(int k = 0; k <= right-left; k++){
            students.set(k+left, temp.get(k));
        }
    }


    //按照理综排序：选择
    public static void sortStuByScience(List<Student> students){
        int n = students.size();
        for(int i = 0; i < n; i++){
            int maxIndex = i;
            for(int j = i+1; j < n; j++){
                if(students.get(j).getGrade().getScienceSum() >
                   students.get(maxIndex).getGrade().getScienceSum()){
                    maxIndex = j;
                }
            }
            swap(students, i, maxIndex);
        }
    }

    //按照文综排序：冒泡
    public static void sortStuByLiberal(List<Student> students){
        int n = students.size();
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                if(students.get(j).getGrade().getLiberalSum() > students.get(i).getGrade().getLiberalSum()){
                    swap(students, i, j);
                }
            }
        }
    }

    //按照单科排序：快排
    //根据String获取单科成绩
    public static int getGrade(int index, String subject, List<Student> students){
        if(subject.equals("chi")){ return students.get(index).getGrade().getChinese(); }
        if(subject.equals("math")){ return students.get(index).getGrade().getMath(); }
        if(subject.equals("en")){ return students.get(index).getGrade().getEnglish(); }
        if(subject.equals("phy")){ return students.get(index).getGrade().getPhysics(); }
        if(subject.equals("chem")){ return students.get(index).getGrade().getChemistry(); }
        if(subject.equals("bio")){ return students.get(index).getGrade().getBiology(); }
        if(subject.equals("pol")){ return students.get(index).getGrade().getPolitics(); }
        if(subject.equals("his")){ return students.get(index).getGrade().getHistory(); }
        if(subject.equals("geo")){ return students.get(index).getGrade().getGeography(); }
        return -1;
    }
    public static void sortStuBySingleSubject(List<Student> students, int left, int right, String subject){
        if(left >= right){
            return;
        }
        int mid = (left+right)/2;
        int cur = getGrade(mid, subject, students);

        swap(students, mid, right);
        int position = left;
        for(int i = left; i < right; i++){
            int grade = getGrade(i, subject, students);
            if(grade >= cur){
                swap(students, position, i);
                position++;
            }
        }
        swap(students, position, right);
        sortStuBySingleSubject(students, left, position-1, subject);
        sortStuBySingleSubject(students, position+1, right, subject);
    }

}
