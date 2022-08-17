---
title: Data Struct Algorithm
date: 2021-11-24
tags:
  - Datastruct
  - C/C++
---

## 链表

### 头插法

~~~c
#include <iostream>
using namespace std;

struct Node{
    int val;
    Node* next;
    Node(){val = -1; next = nullptr;};
    Node(int v){val = v; next = nullptr;};
};

class List{
    private:
        Node* head;
        int length;
    public:
        List(){head = new Node(); length = 0;};
        void insert(int val);
        int size();
        void print();
};

void List::insert(int val){
    Node* p = new Node(val);
    p->next = head->next;
    head->next = p;
    length++;
}

int List::size(){
    return length;
}

void buildList(List list){
    int val;
    cin >> val;
    while(val != -1){
        list.insert(val);
        cin >> val;
    }
}

void List::print(){
    Node* p = head->next;
    while(p != nullptr){
        
        cout << p->val << " ";
        p = p->next;
    }
}

int main(){
    List list;
    buildList(list);
    list.print();
    return 0;
}

~~~

### 循环链表

~~~c
#include <iostream>
using namespace std;

struct Person {
    int id;
    char name[20];
    int pwd;
    Person* next;
};

class PersonList {
private:
    int length = 0;
    Person* head = nullptr;
    Person* tail = nullptr;
public:
    Person* getTail() {
        return tail;
    }

    void init(Person* p) {
        head = p;
        tail = head;
        head->next = tail;
        length++;
    }

    void add(Person* q) {
        tail->next = q;
        tail = tail->next;
        tail->next = head;
        length++;
    }

    void print(int n, Person* tail) {
        if (length == 0) {
            return;
        }
        
        Person* prev = tail;
        for (int i = 0; i < n-1; i++) {
            prev = prev->next;
        }
        Person* curr = prev->next;

        if (length == 1) {
            cout << curr->id << "," << curr->name << "," << curr->pwd;
        }
        else {
            cout << curr->id << "," << curr->name << "," << curr->pwd << endl;
        }
        prev->next = curr->next;
        length--;
        print(curr->pwd, prev);
    }
};

int main()
{
    Person* p = new Person();
    PersonList list;
    int m;
    char dh = ',';
    cin >> m;// 人数

    if (m == 0)
        return 0;

    cin >> p->id >> dh;
    cin.getline(p->name, 10, dh);
    cin >> p->pwd;
    list.init(p);

    for (int i = 0; i < m-1; i++) {
        p = new Person();
        cin >> p->id >> dh;
        cin.getline(p->name, 10, dh);
        cin >> p->pwd;
        list.add(p);
    }
    int n;
    cin >> n;//报数
    list.print(n, list.getTail());
    return 0;
}

~~~

### 顺序表模拟链表寻址

~~~c
#include<iostream>
#include<iomanip>
using namespace std;




struct Node{
	char data;
	int next;
	
	Node(){
		next = -1;
	}
	
	Node(char d, int n){
		data = d;
		next = n;
	}
}; 

class List{
	private:
		Node* list;
		int* alpha;
        int length;
		
	public:
		List(){
            length = 0;
			alpha = new int[26];
			for(int i = 0; i < 26; i++){
				alpha[i] = 0;
			}
			list = new Node[100000];
		}
		
		void add(int address, char data, int next) {
			Node node(data, next);
			list[address] = node;
		}
		
		void calculate(int address){
			Node node = list[address];
			char data = node.data;
			int next = node.next;
			alpha[data-'A']++;
			length++;
			if(next == -1){
				return;
			}	
			calculate(next);
		}
		
		int size(){
			return length;
		}
		
		int* getAlpha(){
			return alpha;
		}
		
		Node get(int index){
			return list[index];
		}
		
};



void printRes(List list){
    int size = list.size();
    int* alpha = list.getAlpha();
	for(int i = 0; i < 26; i++){
		if(alpha[i] != 0){
			cout << (char)(65+i) << " " << setiosflags(ios::fixed) << setprecision(2) << alpha[i]*100.0/size << "%" << endl;
		}
	}
}

int main(){
	List list;
	int rootAddress, n;	//初始地址、数据个数 
	cin >> rootAddress >> n;
	int address, next;
	char data;
	for(int i = 0; i < n; i++){
		cin >> address >> data >> next;
		list.add(address, data, next);
	}
	list.calculate(rootAddress);
	printRes(list);
}
~~~



## 栈

### 有效括号

~~~c
#include <iostream>
#include <string>
using namespace std;

struct Node {
    char val;
    Node* next;
    Node(char val) { this->val = val; }
};

class Stack {
public:
    int length = 0;
    Node* top;
    Node* bottom;
    Stack() {
        top = nullptr;
        bottom = nullptr;
    }

    void push(char val) {
        Node* n = new Node(val);
        if (top == nullptr) {
            top = n;
            bottom = n;
        }
        else {
            top->next = n;
            top = top->next;
        }
        length++;
    }

    char pop() {
        if (length == 0) {
            return ' ';
        }
        char res = top->val;
        Node* p = bottom;
        for (int i = 1; i < length - 1; i++) {
            p = p->next;
        }
        p->next = top->next;
        top = p;
        length--;
        return res;
    }

    bool isEmpty() {
        if (length == 0) {
            return true;
        }
        return false;
    }
};

bool check(Stack stack, string str) {
    for (int i = 0; i < str.length(); i++) {
        char c = str.at(i);
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        }
        else {
            if (stack.isEmpty()) return false;
            char s = stack.pop();
            if (c == ')' && s != '(') {
                return false;
            }
            else if (c == ']' && s != '[') {
                return false;
            }
            else if (c == '}' && s != '{') {
                return false;
            }
        }
    }
    return stack.isEmpty();
}

int main() {
    Stack stack;
    string str;
    cin >> str;
    if (check(stack, str)) {
        cout << "True";
    }
    else {
        cout << "False";
    }

    return 0;
}

~~~

### 进制转换

~~~c
#include <iostream>
#include <string>
using namespace std;

struct Node {
    char val;
    Node* next;
    Node(char val) { this->val = val; next = NULL;}
};

class Stack {
public:
    int length;
    Node* top;
    Node* base;
    Stack() {
    	length = 0;
        top = NULL;
        base = NULL;
    }

    void push(char val) {
        Node* n = new Node(val);
        if (length == 0) {
            top = n;
            base = n;
        }
        else {
            top->next = n;
            top = n;
        }
        length++;
    }

    char pop() {
        if (length == 0) {
            return ' ';
        }
        char res = top->val;
        Node* p = base;
        for (int i = 1; i < length - 1; i++) {
            p = p->next;
        }
        p->next = top->next;
        top = p;
        length--;
        return res;
    }

    bool isEmpty() {
        if (length == 0) {
            return true;
        }
        return false;
    }
    
};

void calculate(Stack& stack, int num, int s){
	while(num != 0){
		int n = num%s;
		
		if(n >= 10){
			stack.push(n+'57');
		}else{
			stack.push(n+'0');
		}
		num /= s;
	}
}

int main(){
	int num, s;
	cin >> num >> s;
	Stack stack;
	calculate(stack, num, s);
	
	
	while(!stack.isEmpty()){
		cout << stack.pop();
	}
}
 



~~~

### 计算器

~~~c
#include <iostream>
#include <string>
#include <cmath>
using namespace std;
#include <stack>

bool isDigit(char c){
	if(c>='0' && c <='9'){
		return true;
	}
	return false;
}

bool prior(char x, char y){
	if(x=='+' || x=='-')	x = 1;
	else if(x=='*' || x=='/')	x = 2;
	else if(x=='^')		x = 3;
	else if(x=='(')		x = 4;
	
	if(y=='+' || y=='-')	y = 1;
	else if(y=='/' || y=='*')	y = 2;
	else if(y=='^')		y = 3;
	else if(y=='(')		y = 4;
	
	return x>y;
}

int cal(int a, char opr, int b){
	if(opr == '*')	return a*b;
	else if(opr == '+')	return a+b;
	else if(opr == '-')	return a-b;
	else if(opr == '/')	return a/b;
	else	return pow(a, b);
}

int caculate(stack<char> opr, stack<int> nums, string str){
	int n = str.length();
	for(int i = 0; i < n; i++){
		char c = str.at(i);
		if(isDigit(c)){
			int num = c-'0';
			while(i+1 < n && isDigit(str.at(i+1))){
				 num = num*10 + (str.at(++i)-'0');
			}
			nums.push(num); 
		}else if(c == ')'){
			while(opr.top() != '('){
				int n2 = nums.top();	nums.pop();
				int n1 = nums.top();	nums.pop();
				char o = opr.top();		opr.pop();
				int ans = cal(n1, o, n2);
				nums.push(ans);
			}
			opr.pop();
		}else{
			if(opr.empty() || prior(c, opr.top()) || c == '(' || opr.top() == '('){
				opr.push(c);
			}else{
				while(1){
					int n2 = nums.top();	nums.pop();
					int n1 = nums.top();	nums.pop();
					char o = opr.top();		opr.pop();
					int ans = cal(n1, o, n2);
					nums.push(ans);
					if(opr.empty() || opr.top() == '(' || prior(c, opr.top())){
						break;
					}
				}	
				opr.push(c);
			}
		}		
	}
	while(!opr.empty()){
		int n2 = nums.top();	nums.pop();
		int n1 = nums.top();	nums.pop();
		char o = opr.top();		opr.pop();
		int ans = cal(n1, o, n2);
		nums.push(ans);
	}

	if(!nums.empty())
		return nums.top();
	return -1;
}

int main(){
	stack<char> opr;
	stack<int> nums;
	string str;
	cin >> str;
	cout << caculate(opr, nums, str) << endl;
	return 0;
}
~~~

## 队列

### 顺序表实现队列

~~~c
#include <iostream>
using namespace std;

class Queue{
	private:
		int* queue;
		int front;
		int length;
		int capacity;
		
	public:
		Queue(){
			front = 0;
			length = 0;
			capacity = 12;
			queue = new int[capacity];
		}
		
		void offer(int val){
			if(length >= capacity*0.75){
				capacity *= 2;
				int* newQueue = new int[capacity];
				int count = 0;
				for(int i = front; i < length; i++){
					newQueue[count++] = queue[i];
				}
				length = length - front;
				front = 0;
				queue = newQueue;
			}
			queue[length++] = val;
		}
		
		int poll(){
			if(front == length){
				front = 0;
				length = 0;
				capacity = 12;
				queue = new int[capacity];				
				return -1;
			}
			int res = queue[front++];
			queue[front-1] = 0;
			return res;
		}
		
		bool isEmpty(){
			if(length-front == 0 || length == 0){
				return true;
			}
			return false;
		}
		
		int size(){
			return length-front;
		}
};

void test(Queue queue){
	for(int i = 0; i < 21; i++){
		queue.offer(i);
	}
	cout << queue.size() << endl;
	for(int i = 0; i < 4; i++){
		cout << queue.poll() << " ";
	}
	cout << endl << queue.size() << endl;
	for(int i = 0; i < 21; i++){
		cout << queue.poll() << " ";
	}
	cout << endl << queue.size();
}

int main(){
	Queue queue;
	test(queue);
	int n, command, val;
	cin >> n;	
	for(int i = 0; i < n; i++){
		cin >> command;
		if(command == 0){
			if(queue.isEmpty()){
				cout << "invalid" << endl;
			}else{
				cout << queue.poll() << endl;
			}
		}else if(command == 1){
			cin >> val;
			queue.offer(val);
		}
	}
}

~~~

### 栈模拟队列

~~~c
#include<iostream>
using namespace std;
#include<stack>

class Queue{
	private:
		int capacity;
		stack<int> s1, s2;
		
	public:
		Queue(int capacity){
			this->capacity = capacity;
		}
		
		bool offer(int val){
			if(s1.size() < capacity){
				s1.push(val);
				return true;
			}else{
				if(s2.empty()){
					while(!s1.empty()){
						s2.push(s1.top());
						s1.pop();
					}
					s1.push(val);
					return true;
				}else{
					return false;
				}
			}
			return false;
		}
		
		int poll(){
			if(s2.empty()){
				while(!s1.empty()){
					s2.push(s1.top());
					s1.pop();
				}
			}
			if(!s2.empty()){
				int res = s2.top();
				s2.pop();
				return res;
			}
			return -1;
		}
};

void test(Queue queue){
	for(int i = 0; i < 9; i++){
		if(!queue.offer(i)){
			cout << "ERROR:Full" << endl;
		}
	}
	for(int i = 0; i < 9; i++){
		int res = queue.poll();
		if(res != -1)
			cout << res << endl;
		else
			cout << "ERROR:Empty" << endl;
	}
}

int main(){
	int m, n;
	cin >> m >> n;
	int capacity = m>n?n:m; 
	Queue queue(capacity);
	//test(queue);
	char command;
	int val;
	while(1){
		cin >> command;
		if(command == 'A'){
			cin >> val;
			if(!queue.offer(val))
				cout << "ERROR:Full" << endl;
		}else if(command == 'D'){
			int res = queue.poll();
			if(res != -1)
				cout << res << endl;
			else
				cout << "ERROR:Empty" << endl;
		}else if(command == 'T'){
			break;
		}
	}
}

~~~

### 模拟阻塞队列

~~~c
#include <iostream>
using namespace std;
#include <queue>

void deal(int n, int* c, queue<int> a, queue<int> b){
	for(int i = 0; i < n; i++){
		if(c[i] % 2 == 1){
			a.push(c[i]);
		}else{
			b.push(c[i]);
		}
	}
	
	while(!a.empty() && !b.empty()){
		cout << a.front() << " ";
		a.pop();
		if(!a.empty()){
			cout << a.front() << " ";
			a.pop();
		}	
		cout << b.front() << " ";
		b.pop();
	}
	
	while(!a.empty()){
		if(a.size() == 1){
			cout << a.front();
		}else{
			cout << a.front() << " ";
		}	
		a.pop();
	}
	
	while(!b.empty()){
		if(b.size() == 1){
			cout << b.front();
		}else{
			cout << b.front() << " ";
		}
		b.pop();
	}
}

int main(){
	queue<int> a, b;
	int n;
	cin >> n;
	int c[n];
	for(int i = 0; i < n; i++){
		cin >> c[i];
	}
	deal(n, c, a, b);
	return 0;
}


~~~

## 串和数组

### KMP

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

### 三元组实现稀疏矩阵转置

~~~c
#include <iostream>
using namespace std;


struct matrix{
	int r;
	int l;
	int val;
};

int main(){


	int m, n, t;
	cin >> m >> n >> t;
	 
	matrix a[t];
	matrix b[t];
	
	int i;
	for(i = 0; i < t; i++){
		cin >> a[i].r >> a[i].l >> a[i].val;
	}
	
	int count = 0;
	int pos = 0;
	while(count < t){
		for(i = 0; i < t; i++){
			if(a[i].l == count){
				b[pos].r = a[i].l;
				b[pos].l = a[i].r;
				b[pos].val = a[i].val;
				pos++;
			}
		}
		count++;
	}
	
	for(i = 0; i < t; i++){
		cout << b[i].r << " " << b[i].l << " " << b[i].val << endl;
	}

	return 0;
}


~~~

### 三元组实现稀疏矩阵加法

~~~c
#include <iostream>
using namespace std;

struct matrix{
	int r;
	int v;
	int val;
};


int main(){
	
	int m, n, t1, t2;
	cin >> m >> n >> t1 >> t2;
	
	matrix a[t1];
	matrix b[t2];
	
	int i;
	for(i = 0; i < t1; i++){
		cin >> a[i].r >> a[i].v >> a[i].val;
	}
	
	for(i = 0; i < t2; i++){
		cin >> b[i].r >> b[i].v >> b[i].val;
	}
	
	matrix res[t1+t2];
	int count = 0;
	
	int r;
	for(r = 0; r < m; r++){
		int v;
		for(v = 0; v < n; v++){
			int val = 0;
			for(i = 0; i < (t1>t2?t1:t2); i++){
				if(i < t1 && a[i].r == r && a[i].v == v){
					val += a[i].val;
				}
				if(i < t2 && b[i].r == r && b[i].v == v){
					val += b[i].val;
				}
			}
			if(val != 0){
				res[count].r = r;
				res[count].v = v;
				res[count].val = val;
				count++;
			}
		}
	}
	
	cout << m << " " << n << " " << count << endl;
	for(i = 0; i < count; i++){
		cout << res[i].r << " " << res[i].v << " " << res[i].val << endl;
	}

	return 0;
}
~~~

## 树

### 树的构造和遍历

~~~c
#include <stdio.h>
#include <malloc.h>
#include <iostream>
using namespace std;


struct node{
	char val;
	node* l;
	node* r;
};

node* node_new(){
	node* n = new node();
	n->l = NULL;
	n->r = NULL;
	return n;
}

void tree_del(node* root){
	if(root->l != NULL){
		tree_del(root->l);
	}
	if(root->r != NULL){
		tree_del(root->r);
	}
	free(root);
}


//前序遍历 
void preorder_traversal(node* root){
	if(root->val == '#'){
		return;
	}
	cout << root->val;
	preorder_traversal(root->l);
	preorder_traversal(root->r);
}

//中序遍历 
void inorder_traversal(node* root){
	if(root->val == '#'){
		return;
	}
	inorder_traversal(root->l);
	cout << root->val;
	inorder_traversal(root->r);
}

//后序遍历 
void postorder_traversal(node* root){
	if(root->val == '#'){
		return;
	}
	postorder_traversal(root->l);
	postorder_traversal(root->r);
	cout << root->val;
}


void traversal(node* root){
	if(root->val == '#'){
		return;
	}
	preorder_traversal(root);
	cout << endl;
	inorder_traversal(root);
	cout << endl;
	postorder_traversal(root);
	cout << endl;
}



//用于记录字符数组下标位置 
static int pos = 0;
//中序构造 
void node_add(node* root, char* vals){
	//cout << vals[pos] << endl;
	root->val = vals[pos];
	if(vals[pos++] == '#'){
		//cout << "hahaha" << endl; 
		return;
	}
	root->l = node_new(); 
	node_add(root->l, vals);
	root->r = node_new();
	node_add(root->r, vals);	
}

void tree_build(node* root, char* vals){
	node_add(root, vals);
	pos = 0;
}


//求叶子个数 
int leaf(node* root){
	if(root->val == '#'){
		return 0;
	}
	
	if(root->l->val == '#' && root->r->val == '#'){
		return 1;
	}
	
	int n1 = 0, n2 = 0;
	n1 = leaf(root->l);
	n2 = leaf(root->r);
	
	return n1+n2;
}

int main(){
	char vals[1000];
	cin >> vals;
	node* root = node_new();
	tree_build(root, vals);
	int leaves = leaf(root);
	traversal(root);
	cout << leaves;
}


//ABD##EG###C#F##
//FCA##DB###EHM###G##
~~~

### 层序遍历

~~~c
#include <stdio.h>
#include <malloc.h>
#include <iostream>
#include <queue>
using namespace std;


struct node{
	char val;
	node* l;
	node* r;
};


node* node_new(){
	node* n = (node*)malloc(sizeof(node));
	n->l = NULL;
	n->r = NULL;
	return n;
}


void tree_del(node* root){
	if(root->l != NULL){
		tree_del(root->l);
	}
	if(root->r != NULL){
		tree_del(root->r);
	}
	free(root);
}


//用于记录字符数组下标位置 
static int pos = 0;
//中序构造 
void node_add(node* root, char* vals){
	//cout << vals[pos] << endl;
	root->val = vals[pos];
	if(vals[pos++] == '@'){
		//cout << "hahaha" << endl; 
		return;
	}
	root->l = node_new(); 
	node_add(root->l, vals);
	root->r = node_new();
	node_add(root->r, vals);	
}

void tree_build(node* root, char* vals){
	node_add(root, vals);
	pos = 0;
}


void lever_traversal(node* root){
	queue<node*> q;
	q.push(root);
	while(!q.empty()){
		node* cur = q.front();
		q.pop();
		if(cur->val == '@'){
			continue;
		}
		cout << cur->val;
		if(cur->l){
			q.push(cur->l);
		}
		if(cur->r){
			q.push(cur->r);
		}
	}
	cout << endl;
}


int main(){
	int n;
	cin >> n;
	char vals[1000];
	for(int i = 0; i < n; i++){
		node* root = node_new();
		cin >> vals;
		tree_build(root, vals);
		lever_traversal(root);
	}

}


//ABD##EG###C#F##
//FCA##DB###EHM###G##
~~~

## 图

### 邻接表构造无向图并计算度

> 自己写的

~~~c
#include <iostream>
using namespace std;
#define MAX 100

struct node{
	char val;
	int size;
	node** bro;
};

node* node_build(char v){
	node* n = new node();
	n->val = v;
	n->size = 0;
	n->bro = new node*[MAX]; 
	return n;
}

void node_del(node* v){
	/*for(int i = 0; i < v->size; i++){
		delete(v->bro[i]);
	}*/
	delete(v);
}

void node_add(node* n, node* v){
	n->bro[n->size++] = v;
	v->bro[v->size++] = n;
} 

node** map_build(int m, int n){
	char* tops = new char[m];
	cin >> tops;
	node** nodes = new node*[m];
	for(int i = 0; i < m; i++){
		nodes[i] = node_build(tops[i]);
	}
	delete(tops);
	char* connect = new char[2];
	for(int j = 0; j < n; j++){
		cin >> connect;
		node* v1 = NULL;
		node* v2 = NULL;
		for(int t = 0; t < m; t++){
			if(nodes[t]->val == connect[0]){
				v1 = nodes[t];
			}
			if(nodes[t]->val == connect[1]){
				v2 = nodes[t];
			}
		}
		node_add(v1, v2);
	}
	
	return nodes;
}



int main(){
	//输入顶点个数，边个数 
	int m, n;
	cin >> m >> n;
	node** map = map_build(m, n);
	
	for(int i = 0; i < m; i++){
		if(i != m-1)	{ cout << map[i]->size << " "; }
		else			{ cout << map[i]->size; }		
		delete map[i];
	}
	delete(map);
	
	return 0;
}

~~~

> 网上借鉴的

~~~c
#include<iostream>
using namespace std;


typedef struct ENode{ //表结点 
	char data;
	struct ENode* next; 
} ENode;


typedef struct VNode{  //头结点 
	char data;
	ENode* fistedges;
} VNode, vertex[20];


typedef struct{
	vertex v;
	int numNode,numedges;
} Graph;


int find(Graph G, char c) { //找到对应弧 
	for(int i = 0; i < G.numNode; i++)
	{
		if(G.v[i].data == c)
			return i;
	 } 
	return -1;
}


void Create(Graph G) {
	cin >> G.numNode >> G.numedges;
	
	for(int i = 0; i < G.numNode; i++) {	//保存顶点 
		char a;
		cin >> a;
		G.v[i].data = a; 
		G.v[i].fistedges = NULL;
	}
	
	for(int i = 0; i < G.numedges; i++) {	//保存边 
		char a, b;
		cin >> a >> b;
		int p = find(G, a);
		int q = find(G, b);
		ENode* pre = new ENode();

 		//将b接在表头a的后面	
		pre = G.v[p].fistedges;
		if(pre == NULL){
			ENode* qre = new ENode();
			qre->data = b;
			qre->next = NULL;
			G.v[p].fistedges = qre; 
		} else {
			while(pre->next != NULL) {
				pre = pre->next;
			}
			ENode* qre = new ENode();
			qre->data = b;
			qre->next = NULL;
			pre->next = qre;			
		}
		//将a接到表头b的后面
		pre = G.v[q].fistedges;
		if(pre == NULL) {
			ENode* qre = new ENode();
			qre->data = a;
			qre->next = NULL;
			G.v[q].fistedges = qre; 
		} else {
			while(pre->next != NULL) {
				pre = pre->next;
			}
			ENode* qre = new ENode();
			qre->data = a;
			qre->next = NULL;
			pre->next = qre;			
		}
	}
	//输出各个顶点的度
	for(int i = 0; i < G.numNode; i++) {
		int s = 0;
		for(ENode* p = G.v[i].fistedges; p != NULL; p = p->next) {
			s++;
		}
		if(i == 0)
			cout << s;
		else
			cout << " " << s;
	 }
}



int main() {
	Graph G;
	Create(G);
	return 0;
}
~~~

### DFS/BFS遍历并输出连通集

复习一手快排，将边从小到大排序

~~~c
#include <iostream>
using namespace std;
#define MAX 100
#include <queue>

struct Node{
	int val;
	int size;
	bool visited;
	Node** bro;
};

Node* node_build(int v){
	Node* n = new Node();
	n->val = v;
	n->size = 0;
	n->visited = false;
	n->bro = new Node*[MAX]; 
	return n;
}

void node_del(Node* v){
	/*for(int i = 0; i < v->size; i++){
		delete(v->bro[i]);
	}*/
	delete(v);
}

void node_add(Node* n, Node* v){
	n->bro[n->size++] = v;
	v->bro[v->size++] = n;
} 

void node_clean(Node* n){
	n->visited = false;
}


typedef struct{
	int size;
	Node** tops;
} Map;

Map* map_new(){
	Map* map = new Map();
	map->size = 0;
	map->tops = NULL;
	return map;
}

Map* map_build(int m, int n){
	//初始化map 
	Map* map = map_new();
	map->size = m;
	map->tops = new Node*[m];
	
	//直接将下标值赋给顶点值 
	for(int i = 0; i < m; i++){
		map->tops[i] = node_build(i);
	}
	
	//连结顶点 
	int n1, n2;
	for(int j = 0; j < n; j++){
		cin >> n1 >> n2;
		node_add(map->tops[n1], map->tops[n2]);
	}
	
	return map;
}

void map_clean(Map* m){
	for(int i = 0; i < m->size; i++){
		m->tops[i]->visited = false;
	}
}

void node_dfs(Node* n){
	if(n->visited){
		return;
	}
	cout << n->val << " ";
	n->visited = true;
	for(int i = 0; i < n->size; i++){
		node_dfs(n->bro[i]);
	}
}

void map_dfs(Map* m){
	for(int i = 0; i < m->size; i++){
		if(!m->tops[i]->visited){
			cout << "{ ";
			node_dfs(m->tops[i]);
			cout << "}" << endl;
		}
	}
	map_clean(m); 
}


void node_bfs(Node* n){
	if(n->visited){
		return;
	}
	queue<Node*> q;
	q.push(n);
	n->visited = true;
	while(!q.empty()){
		Node* cur = q.front();
		q.pop();
		cout << cur->val << " ";
		for(int i = 0; i < cur->size; i++){
			if(!cur->bro[i]->visited){
				q.push(cur->bro[i]);
				cur->bro[i]->visited = true;
			}
		}
	}
}

void map_bfs(Map* m){
	for(int i = 0; i < m->size; i++){
		if(!m->tops[i]->visited){
			cout << "{ ";
			node_bfs(m->tops[i]);
			cout << "}" << endl;
		}
	}
	map_clean(m);
}

void node_swap(Node* n, int i, int j){
	Node* temp = n->bro[i];
	n->bro[i] = n->bro[j];
	n->bro[j] = temp;
}

//快速排序
void node_sort(Node* n, int left, int right){
	if(left >= right){
		return;
	}
	node_swap(n, (left+right)/2, right);
	int pivot = n->bro[right]->val;
	int pos = left;
	for(int i = left; i < right; i++){
		if(n->bro[i]->val <= pivot){
			node_swap(n, i, pos);
			pos++;
		}
	}
	node_swap(n, pos, right);
	node_sort(n, left, pos-1);
	node_sort(n, pos+1, right);
}

void map_sort(Map* m){
	for(int i = 0; i < m->size; i++){
		if(!m->tops[i]->visited){
			node_sort(m->tops[i], 0, m->tops[i]->size-1);
		}
	}
}


int main(){
	int m, n;
	cin >> m >> n;
	Map* map = map_build(m, n);
	map_sort(map);
	map_dfs(map);
	map_bfs(map); 
}
~~~

## 查找

### 二叉排序树的构造和遍历

~~~c
#include <iostream>
using namespace std;


struct tree_node{
	int val;
	tree_node* left;
	tree_node* right;
}; 

tree_node* new_node(int val){
	tree_node* node = new tree_node();
	node->val = val;
	node->left = NULL;
	node->right = NULL;
	return node;
}

tree_node* create_binary_search_tree(int* vals, int length){
	tree_node* root = new_node(vals[0]);
	for(int i = 1; i < length; i++){

		tree_node* p = root;
		int val = vals[i];
		while(p){
			if(val <= p->val){
				if(!p->left){
					p->left = new_node(val);
					//cout << val << endl;
					break;
				} else{
					p = p->left;
				}
			} else{
				if(!p->right){
					//cout << val << endl;
					p->right = new_node(val);
					break;
				}else{
					p = p->right;
				}
				
			}
		}
	}
	return root;
}

void pre_travel(tree_node* root){
	if(!root){
		return;
	}
	if(root){
		cout << root->val << " ";
	}
	pre_travel(root->left);
	pre_travel(root->right);
}


int main(){

	int* nums = new int[1000];
	int i;
	for(i = 0; i < 1000; i++){
		int num;
		cin >> num;
		if(num == 0){
			break;
		}
		nums[i] = num;
	}
	
	tree_node* root = create_binary_search_tree(nums, i);
	pre_travel(root);

	return 0;
}

~~~

### 二分查找

~~~c
#include <iostream>
using namespace std;

void swap(int* nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}

void quickSort(int* nums, int left, int right){
    if(left>=right){
        return;
    }
    swap(nums, right, (left+right)/2);
    int pivot = nums[right];
    int position = left;
    for(int i = left; i < right; i++){
    	if(nums[i] <= pivot){
            swap(nums, i, position);
            position++;
        }
    }
    swap(nums, position, right);
    quickSort(nums, left, position-1);
    quickSort(nums, position+1, right);
}



void insertSort(int* nums, int n){
	for(int i = 1; i < n; i++){
		//记录要插入的元素cur 
		int cur = nums[i], j;
		for(j = i-1; j >= 0; j--){
			//当第j位元素小于当前元素，退出，将cur插入第j位 
			if(nums[j] > cur){
				nums[j+1] = nums[j];
			} else {	//当第j位元素大于等于当前元素，将第j位元素右移一位 
			break;
			}
		}
		nums[j+1] = cur;
	}
}

void binaryInsertSort(int* nums, int n){
	for(int i = 1; i < n; i++){
		int left = 0, right = i-1, cur = nums[i];
		//找出插入点left 
		while(right >= left){
			int mid = (left+right)/2;
			if(nums[mid] > cur){
				right = mid-1;
			} else {
				left = mid+1;
			}
		}
		//将插入点右侧到要插入元素之间元素全部右移一位
		//必须要从右往左移动，从左往右移动会把元素逐一覆盖 
		for(int j = i; j > left; j--){
			nums[j] = nums[j-1];
		}
		//插入 
		nums[left] = cur;
	}
} 

int binarySearch(int* nums, int left, int right, int target){
	while(left <= right){
		int mid = (left+right)/2;
		if(nums[mid] == target){
			return mid+1;
		} else if(nums[mid] > target){
			right = mid-1;
		} else {
			left = mid + 1; 
		}
	}
	return 0;
} 


int main(){

	int n;
	cin >> n;
	int nums[n];
	for(int i = 0; i < n; i++){
		cin >> nums[i];
	}
	binaryInsertSort(nums, n);
	
	int m;
	cin >> m;
	int targets[m];
	for(int i = 0; i < m; i++){
		cin >> targets[i];
	}
	
	//打印排列好的数组 
	for(int i = 0; i < n; i++){
		if(i != n-1)
			cout << nums[i] << " ";
		else
			cout << nums[i] << endl;
	}
	//打印搜索结果 
	for(int i = 0; i < m; i++){
		if(i != m-1)
			cout << binarySearch(nums, 0, n-1, targets[i]) << " ";
		else
			cout << binarySearch(nums, 0, n-1, targets[i]) << endl;
	}
	
	return 0;
}

~~~

## 排序

### 堆排

~~~c
#include <iostream>
using namespace std;


void print(int* nums, int length){
    for(int i = 0; i < length; i++){
        if(i != length-1)
            cout << nums[i] << " ";
        else   
            cout << nums[i];
    }
}

void println(int* nums, int length){
    print(nums, length);
    cout << endl;
}

void swap(int* nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}


//调整大顶堆
void heapAdjust(int* nums, int parent, int length){
    int child = parent*2+1;
    while(child <= length){

        if(child+1 <= length && nums[child] < nums[child+1]){
            child++;
        }

        if(nums[parent] > nums[child]){
            break;
        }
        swap(nums, parent, child);
        parent = child;
        child = parent*2+1;
    }
}


void heapSort(int* nums, int length){
    int n = length - 1;
    //构造大顶堆，一定要从后往前构造
    //这样在树中为从下层向上层构造，逐步将大根上移，防止漏移
    for (int i = n / 2; i >= 0; i--) {
        heapAdjust(nums, i, n);
    }

    //将最大元素移到最后，再对前i-1个元素进行调整，构造新的小顶堆
    for(int i = n; i > 0; i--){
        println(nums, length);
        swap(nums, 0, i);
        heapAdjust(nums, 0, i-1);
    }
    print(nums, length);
}

int main(){
    int length;
    cin >> length;
    int* nums = new int[length];
    for(int i = 0; i < length; i++){
        cin >> nums[i];
    }
    heapSort(nums, length);
    
}

~~~

### 快排

~~~c
#include <iostream>
using namespace std;


void swap(int* nums, int i, int j){
	int temp = nums[i];
	nums[i] = nums[j];
	nums[j] = temp;
}

void quickSort(int* nums, int left, int right){
	if(left >= right){
		return;
	}
	int mid = (left+right)/2;
	int cur = nums[mid];
	int pos = left;
	swap(nums, mid, right);
	for(int i = left; i < right; i++){
		if(nums[i] <= cur){
			swap(nums, pos, i);
			pos++;
		}
	}
	swap(nums, pos, right);
	quickSort(nums, left, pos-1);
	quickSort(nums, pos+1, right);
}

int main(){

	int length;
	int* nums;
	cin >> length;
	nums = new int[length];
	for(int i = 0; i < length; i++){
		cin >> nums[i];
	}
	
	quickSort(nums, 0, length-1);
	for(int i = 0; i < length; i++){
		cout << nums[i] << " ";
	}

	return 0;
}

~~~

### 选排

~~~c
#include <iostream>
using namespace std;

void swap(int* nums, int i, int j){
	int temp = nums[i];
	nums[i] = nums[j];
	nums[j] = temp;
}

void println(int* nums, int length, int step){
	cout << "step " << step << ": "; 
	for(int i = 0; i < length; i++){
		cout << nums[i] << " ";
	}
	cout << endl;
}

void selectSort(int* nums, int length){
	for(int i = 0; i < length-1; i++){
		int minIndex = i;
		for(int j = i+1; j < length; j++){
			if(nums[j] < nums[minIndex]){
				minIndex = j;
			}
		}
		swap(nums, i, minIndex);
		println(nums, length, i+1);
	}
	cout << "sorted array: ";
	for(int i = 0; i < length; i++){
		cout << nums[i] << " ";
	}
}

int main(){

	int length;
	cin >> length;
	int* nums = new int[length];
	for(int i = 0; i < length; i++){
		cin >> nums[i];
	}
	selectSort(nums, length);
	return 0;
}
~~~

### 插排

~~~c
#include <iostream>
using namespace std;

void print(int* nums, int length){
	for(int i = 0; i < length; i++){
		cout << nums[i] << " ";
	}
}

void println(int* nums, int length){
	print(nums, length);
	cout << endl;
}

void insertSort(int* nums, int length){
	for(int i = 1; i < length; i++){
		int cur = nums[i];
		int j;
		for(j = i-1; j >= 0; j--){
			if(nums[j] > cur){
				nums[j+1] = nums[j];
			} else{
				break;
			}
		}
		nums[j+1] = cur;
		if(i < length-1){
			println(nums, length);
		}		
	}
	print(nums, length);
}


int main(){

	int* nums;
	int length;
	cin >> length;
	nums = new int[length];
	for(int i = 0; i < length; i++){
		cin >> nums[i];
	}
	
	insertSort(nums, length);

	return 0;
}
~~~

