package com.PerformanceAnalysisSystem.test;

public class PopSortTest {
    public static void sort(int[] nums){
        int n = nums.length;
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                if(nums[j] > nums[i]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8,9};
        sort(nums);
        for(int i: nums){
            System.out.println(i);
        }
    }
}
