# PrefixSum

> 前缀和算法

### 1、区域和检索-数组不可变（303）

~~~java
//优化解法：前缀和
class NumArray {

    private int[] nums;

    public NumArray(int[] nums) {
        int n = nums.length;
        this.nums = new int[n+1];
        for(int i = 0; i < n; i++){
            //储存前 i 个的和在数组 this.nums 中，用 nums[j+1]-nums[i] 可以得到第i个数到第j个数的和
            this.nums[i+1] = this.nums[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        int res = nums[right + 1] - nums[left]; 
        return res;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
~~~

### 2、二维区域和检索-矩阵不可变（304）

~~~java
//采用303的优化解法：一维前缀和
class NumMatrix {

    private int[][] matrix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        this.matrix = new int[m][n+1];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                this.matrix[i][j+1] = this.matrix[i][j] + matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = 0;
        for(int i = row1; i <= row2; i++){
            res += matrix[i][col2+1] - matrix[i][col1];
        }
        return res;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
~~~

环绕字符串中唯一的子字符串（467）

区间子数组个数（795）

水果成篮（904）

K个不同的整数的子数组（992）

航班预订统计（1109）

连续数组（525）
