# KMP

Knuth-Morris-Pratt

### 1、已知next数组对字符串进行匹配

~~~c
#include <iostream>
#include <string.h>
#include <algorithm> 
using namespace std;


int main(){
	
	char str[1000];
    char dist[1000];
	cin >> str >> dist;
    
    //cout << str << " " << dist << endl;
	int count = 0, m = strlen(str), n = strlen(dist);
	int pos[m];
	
	
    int next[n];
    for(int i = 0; i < n; i++){
    	cin >> next[i];
	}
	


	//cout << m << " " << n << endl;
	int i = 0, j = 0;
	while(i < m && j < n){
		if(j == -1 || tolower(str[i])==tolower(dist[j])){
			i++;
			j++;
		} else {
			j = next[j];
		}
		if(j == n){
			pos[count++] = i-j+1;
			i = i-1-next[j-1];
			j = 0;
		}		
	}
	
	
	cout << count << endl;
	for(i = 0; i < count; i++){
		cout << pos[i] << endl;
	}

	return 0;
}

~~~



实现strStr()（28）

重复叠加字符串匹配（686）

重复的子字符串（459）

最短回文串（214）





