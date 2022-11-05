package com.PerformanceAnalysisSystem.test;

import java.util.ArrayList;
import java.util.List;

public class HeapSortTest {

    //调整小顶堆
    public static void heapAdjust(int nums[], int parent, int length){
        int child = parent*2+1;
        while(child <= length){
            if(child+1 <= length && nums[child] > nums[child+1]){
                child++;
            }
            if(nums[parent] < nums[child]){
                break;
            }
            swap(nums, parent, child);
            parent = child;
            child = parent*2+1;
        }
    }

    public static void swap(int[] nums, int parent, int child){
        int temp = nums[child];
        nums[child] = nums[parent];
        nums[parent] = temp;
    }


    public static void heapSort(int[] nums) {
        int n = nums.length - 1;
        //构造小顶堆，一定要从后往前构造
        //这样在树中为从下层向上层构造，逐步将大根上移，防止漏移
        for (int i = n / 2; i >= 0; i--) {
            heapAdjust(nums, i, n);
        }

        //将最小元素移到最后，再对前i-1个元素进行调整，构造新的小顶堆
        for(int i = n; i > 0; i--){
            swap(nums, 0, i);
            heapAdjust(nums, 0, i-1);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,2,3,4,5,6,7,7,8,9};
        heapSort(nums);
        for(int i: nums){
            System.out.println(i);
        }
    }
}
