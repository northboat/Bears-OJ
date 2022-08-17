---
title: LeetCode in C/C++
date: 2022-7-29
tags:
  - Datastruct
  - C/C++
---

> C/C++数据结构算法力扣实操

## 数据结构入门

### 数组

#### 存在重复元素

给定一个整型数组，判断是否存在重复元素，存在则返回 true

- vector
  - `#include <vector>`
  - `vector.push_back()`
- set
  - `#include <set>`
  - `set.insert()`
  - `set.count(val)`要么是 1 要么是 0
- stl 迭代器
  - `vector/set<int>::iterator it`
  - `for(it=set.begin(); it!=set.end(); it++){}`
  - 通过`*it`取值

~~~c
#include <iostream>
using namespace std;
#include <vector>
#include <set>

class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        set<int> s;
        vector<int>::iterator it;
        for(it = nums.begin(); it != nums.end(); it++){
            if(s.count(*it) == 0){
                s.insert(*it);
            } else {
                return true;
            }
        }
        return false;
    }
};

int main(){
    Solution s;
    vector<int> nums;
    nums.push_back(1);
    nums.push_back(2);
    nums.push_back(3);
    cout << s.containsDuplicate(nums) << endl;
}
~~~

#### 最大子数组和

给一个整型数组，返回它和最大的连续子数组的值，如`[1,2,-1,2]`返回 4

- 整型最小值：`INT32_MIN, INT16_MIN`
- 遍历 vector：`for(vector<int>::iterator it = nums.begin(); it != nums.end(); it++){}`
- 这个算法还是很熟悉，维护一个整数值 sum 作为当前子数组的和，它有如下规则
  - 当大于 res 时，更新 res 为当前 sum
  - 当小于 0 时重置为 0，因为此时不管后面的数如何，前面的和已经造成拖累，因此应该丢弃
  - 其余时候不管，处于观察状态

~~~c
#include <iostream>
using namespace std;

#include <vector>

class Solution {
public:
    int maxSubArray(vector<int>& nums) {
        int res = INT32_MIN;
        int sum = 0;
        for(vector<int>::iterator it = nums.begin(); it != nums.end(); it++){
            sum += *it;
            if(sum > res){
                res = sum;
            }
            if(sum < 0){
                sum = 0;
            }
        }
        return res;
    }
};

int main(){
    Solution s;
    vector<int> nums;
    for(int i = 0; i < 5; i++){
        nums.push_back(i);
    }
    cout << s.maxSubArray(nums) << endl;

}
~~~

#### 两数之和

给定一个 vector 和 target，找出数组中和为 target 的两数的下标并以 vector 的形式返回

- 可以通过`for(int i; i < vector.size(); i++){ e = vector[i] }`的形式从 vector 中取出数据，而不使用迭代器`vector::iterator it`
- 可以直接通过大括号的形式构造 vector，如`vector<int> v = {1,2,3}`，如此，在 return 的时候，直接返回一个大括号就行
- 使用哈希表储存数据`(key=nums[i], value=i)`，键为数值，值为下标，若哈希表中存在`目标-当前值`，即`map.count(target-curVal) != 0`，则说明找到和为 target 的两个数，返回下标即可

~~~c++
#include <iostream>
using namespace std;

#include <map>
#include <vector>

class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        map<int, int> m;
        for(int i = 0; i < nums.size(); i++){
            if(m.count(target-nums[i]) == 1){
                return {i, m.at(target-nums[i])};
            }
            m.insert(pair<int, int>(nums[i], i));
        }
        return {};
    }
};

int main(){
    Solution s;
    map<int,int> m;
    m.insert(pair<int, int>(1, 2));
    m.insert(pair<int, int>(3,4));
    cout << m.at(5) << endl;
}
~~~

#### 合并两个有序数组

双指针，将两个有序数组合并成一个有序数组

- 直接合并，然后排序
- 双指针从前往后遍历原数组构造一个新的有序数组
- 逆向双指针原地改造数组：从后往前遍历，将更大的数放在 nums1 的尾部
- 需要注意对于三目运算符`c = a>b ? a--:b--`，若`a>b`则`b--`不会执行，`a<b`反之

~~~c
#include <iostream>
using namespace std;

#include <vector>

class Solution {
public:
    void merge(vector<int>& nums1, int m, vector<int>& nums2, int n) {
        int tail = m+n-1;
        int p1 = m-1, p2 = n-1;
        while(tail >=0){
            int cur;
            if(p1 < 0){
                cur = nums2[p2--];
            } else if(p2 < 0){
                cur = nums1[p1--];
            } else {
                cur = nums1[p1]>nums2[p2] ? nums1[p1--]:nums2[p2--];
            }
            nums1[tail--] = cur;
        }
    }
};

int main(){
    int a = 2;
    int b = 3;
    int c = a>b ? a--:b--;
    cout << a << endl << b << endl << c;
}
~~~

#### 两个数组的交集 II

需要注意的是，若两个数组都有两个 2，那么交集中也要有两个 2，于是我维护一个 map 用于记录各元素出现次数，即 key 为数值，value 为出现次数，如此各自遍历两个数组一次即可

- 先遍历数组 1，将各值和出现次数插入 map
  - 这里 map 的插入操作需要用 insert
  - map 的修改操作使用 map[2] = map[2]+1 的形式（直接 insert 新值将插入失败）
- 再遍历数组 2，每遇到 map 中存在的键 val 且其出现次数大于 0，则将该数值加入 vector，同时 map[val] = map[val] - 1

~~~c
#include <iostream>
using namespace std;

#include <vector>
#include <set>
#include <map>

class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        map<int, int> m;
        vector<int> res;
        for(int i = 0; i < nums1.size(); i++){
            if(m.count(nums1[i]) == 0){
                m.insert(pair<int, int> (nums1[i], 1));
                continue;
            }
            m[nums1[i]] = m[nums1[i]] + 1;
        }
        for(int i = 0; i < nums2.size(); i++){
            if(m.count(nums2[i]) != 0 && m[nums2[i]] > 0){
                res.push_back(nums2[i]);
                m[nums2[i]] = m[nums2[i]] - 1;
            }
        }
        return res;
    }
};

int main(){
    Solution s;
    vector<int> nums1 = {1,2,2,1};
    vector<int> nums2 = {2,2};
    vector<int> res = s.intersect(nums1, nums2);
    for(int i = 0; i < res.size(); i++){
        cout << res[i] << endl;
    }
    // map<int, int> m;
    // m[0] = 2;
    // cout << m.find(0)->second-1;
    // m.insert(pair<int,int>(0, m.find(0)->second-1));
    // cout << "hahaha: " << m[0] << endl; 
}
~~~

#### 买卖股票的最佳时机

众所周知，当天的最大利润只与当天售价和以往最低售价有关，于是维护一个 int 值储存`以往最低售价`，然后遍历一次数组即可

~~~c
#include <iostream>
using namespace std;
#include <vector>

class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int profit = 0;
        int minPrice = INT32_MAX;
        for(auto price: prices){
            profit = max(profit, price-minPrice);
            minPrice = min(minPrice, price);
        }
        return profit;
    }
};
~~~

- 该题还有**动态规划**和**单调栈**的解法

#### 重塑矩阵

就是 numpy 里的 reshape((row, colume)) 方法

直观的朴素解法就是模拟，如何模拟更高效呢？

- 先考虑将二维数组元素转化为一维数组，再根据一维数组构建二维数组，即遍历原矩阵（二层循环）获取一个`vector<int>`，再经过一个二层循环构造新矩阵，时间复杂度为
  $$
  O(n^mc^r)
  $$
  其中 n/c 为原/新矩阵列，m/r 为原/新矩阵行

- 由上述描述可知关键在于将二维数组一维化，我们考虑一个列数为 n 的二维数组 arr，将其视为元素数为 k 的一维数组，则第 i 个元素为 `arr[i/n][i%n]`，一推就知道这是正确的，并且对于任意二维数组都是如此，于是我们同时遍历原矩阵和构造新矩阵
  $$
  newMatrix[i/c][i\%c]=oldMatrix[i/n][i\%n],\quad 0\leq i\leq m
  $$
  其中 n/c 为原/新矩阵的列数，m 为原矩阵行数

~~~c
#include <iostream>
using namespace std;
#include <vector>

class Solution {
public:
    vector<vector<int>> matrixReshape(vector<vector<int>>& mat, int r, int c) {
        int m = mat.size();
        if(m == 0){
            return {};
        }
        int n = mat[0].size();
        if(m*n != r*c){
            return mat;
        }
        vector<vector<int>> matrix (r, vector<int> (c));
        for(int i = 0; i < m*n; i++){
            matrix[i/c][i%c] = mat[i/n][i%n];
        }
        return matrix;
    }
};
~~~

#### 杨辉三角

同样是模拟，注意边界和迭代元素位置：`matrix[i][j] = matrix[i-1][j-1] + matrix[i-1][j]`

~~~c
#include <iostream>
using namespace std;

#include <vector>

class Solution {
public:
    vector<vector<int>> generate(int numRows) {
        vector<vector<int>> matrix;
        for(int i = 0; i < numRows; i++){
            vector<int> row;
            for(int j = 0; j < i+1; j++){
                if(j == 0 || j==i) { row.push_back(1); continue; }
                row.push_back(matrix[i-1][j-1]+matrix[i-1][j]);
            }
            matrix.push_back(row);            
        }
        return matrix;
    }
};

int main(){
    Solution s;
    vector<vector<int>> m = s.generate(5);
    for(int i = 0; i < m.size(); i++){
        for(int j = 0; j < m[i].size(); j++){
            cout << m[i][j] << " ";
        }
        cout << endl;
    }
}
~~~



## 数据结构基础



## 数据结构进阶