---
title: Data Struct By Hand
date: 2021-8-24
tags:
  - Datastruct
  - C/C++
---

## Node

~~~c
#pragma once
#include <iostream>
using namespace std;

struct LinkedNode {
	int val = 0;
	LinkedNode* next = nullptr;
	LinkedNode() {};
	LinkedNode(int val);
	int getVal();
};

struct DLinkedNode {
	int val = 0;
	DLinkedNode* next = nullptr;
	DLinkedNode* prev = nullptr;
	DLinkedNode() {};
	DLinkedNode(int val);
	int getVal();
};

struct TreeNode {
	int val = 0;
	TreeNode* left = nullptr;
	TreeNode* right = nullptr;
	TreeNode() {};
	TreeNode(int val);
	int getVal();

};
~~~

构造函数和get/set函数

~~~c
#include "Node.h"

LinkedNode::LinkedNode(int val) {
	this->val = val;
}

int LinkedNode::getVal() {
	return val;
}

DLinkedNode::DLinkedNode(int val) {
	this->val = val;
}

int DLinkedNode::getVal() {
	return val;
}

TreeNode::TreeNode(int val) {
	this->val = val;
}

int TreeNode::getVal() {
	return val;
}
~~~

## LinkedList

~~~c
#pragma once
#include "Node.h";

class LinkedList{

private:
	int length = 0;
	LinkedNode* head = new LinkedNode();
	LinkedNode* tail;
	LinkedNode* locate(int index);

public:
	void add(int val);
	int get(int index);
	int size();
	bool remove(int index);
	bool empty();
	void clear();
	void print();
};
~~~

常用功能实现

~~~c
#include "LinkedList.h";


void LinkedList::add(int val) {
	LinkedNode* node = new LinkedNode(val);
	if (this->length == 0) {
		head->next = node;
		tail = node;
		length++;
		return;
	}
	tail->next = node;
	tail = node;
	length++;
}

LinkedNode* LinkedList::locate(int index) {
	if (index + 1 > length || index < 0) {
		return nullptr;
	}
	int count = 0;
	LinkedNode* p = head;
	while (p != nullptr && count < index + 1) {
		p = p->next;
		count++;
	}
	return p;
}

int LinkedList::get(int index) {
	LinkedNode* target = locate(index);
	if (target == nullptr) {
		cout << "Out Of Broudary" << endl;
		return -1;
	}
	return target->getVal();
}

bool LinkedList::remove(int index) {
	if (index + 1 > length || index < 0) {
		cout << "Out Of Broudary" << endl;
		return false;
	}
	LinkedNode* prev = locate(index - 1);
	LinkedNode* curr = prev->next;
	prev->next = curr->next;
	free(curr);	
	if (index + 1 == length) {
		tail = prev;
	}
	length--;
	return true;
}

bool LinkedList::empty() {
	if (length == 0) {
		return true;
	}
	return false;
}

void LinkedList::print() {
	LinkedNode* p = head->next;
	while (p != nullptr) {
		cout << p->getVal() << " ";
		p = p->next;
	}
	cout << endl;
}

int LinkedList::size() {
	return length;
}

void LinkedList::clear() {
	head = new LinkedNode();
	tail = nullptr;
	length = 0;
}
~~~

## Stack

~~~c
#pragma once
#include "Node.h"

class Stack{

private:
	int length = 0;
	LinkedNode* top = new LinkedNode();

public:
	Stack() {};
	bool empty();
	void push(int val);
	void clear();
	int pop();
	int peek();
	int size();
};
~~~

常用功能实现

~~~c
#include "Stack.h"

void Stack::push(int val) {
	LinkedNode* node = new LinkedNode(val);
	node->next = top->next;
	top->next = node;
	length++;
}

void Stack::clear() {
	top = new LinkedNode();
	length = 0;
}

int Stack::pop() {
	if (empty()) {
		cout << "The Stack Is Empty" << endl;
		return -1;
	}
	LinkedNode* temp = top->next;
	int res =temp->getVal();
	top->next = temp->next;
	free(temp);
	length--;
	return res;
}

int Stack::peek() {
	if (empty()) {
		cout << "The Stack Is Empty" << endl;
		return -1;
	}
	return top->next->getVal();
}

bool Stack::empty() {
	if (length == 0) {
		return true;
	}
	return false;
}

int Stack::size() {
	return length;
}


~~~

## Deque

~~~c
#pragma once
#include "Node.h";

class Deque{
	
private:
	int length = 0;
	DLinkedNode* head;
	DLinkedNode* tail;

public:
	Deque();
	void offer(int val);
	void offerFirst(int val);
	int peek();
	int peekLast();
	int poll();
	int pollLast();
	int size();
	bool empty();
};
~~~

常用功能实现

~~~c
#include "Deque.h"

int Deque::size() {
	return length;
}

Deque::Deque() {
	head = new DLinkedNode();
	tail = new DLinkedNode();
	head->next = tail;
	tail->prev = head;
}

//入队，插入到队尾
void Deque::offer(int val) {
	DLinkedNode* node = new DLinkedNode(val);
	DLinkedNode* next = head->next;
	node->prev = head;
	node->next = next;
	head->next = node;
	next->prev = node;	
	length++;
}

//入队，插入到队首
void Deque::offerFirst(int val) {
	DLinkedNode* node = new DLinkedNode(val);
	DLinkedNode* prev = tail->prev;
	node->prev = prev;
	node->next = tail;
	tail->prev = node;
	prev->next = node;
	length++;
}

//检索队首
int Deque::peek() {
	if (length == 0) {
		cout << "The Deque Is Empty" << endl;
		return -1;
	}
	return tail->prev->getVal();
}

//检索队尾
int Deque::peekLast() {
	if (length == 0) {
		cout << "The Deque Is Empty" << endl;
		return -1;
	}
	return head->next->getVal();
}

int Deque::poll() {
	if (length == 0) {
		cout << "The Deque Is Empty" << endl;
		return -1;
	}
	DLinkedNode* temp = tail->prev;
	int res = temp->getVal();
	temp->prev->next = tail;
	tail->prev = temp->prev;
	free(temp);
	length--;
	return res;
}

int Deque::pollLast() {
	if (length == 0) {
		cout << "The Deque Is Empty" << endl;
		return -1;
	}
	DLinkedNode* temp = head->next;
	int res = temp->getVal();
	temp->next->prev = head;
	head->next = temp->next;
	free(temp);
	length--;
	return res;
}


bool Deque::empty() {
	if (length == 0) {
		return true;
	}
	return false;
}
~~~

## Tree

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
~~~

## Trie

字典树

~~~java
class Trie {

    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }
    
    public void insert(String word) {
        Trie p = this;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                p.children[index] = new Trie();
            }
            p = p.children[index];
        }
        p.isEnd = true;
    }

    private Trie searchPrefix(String prefix){
        Trie p = this;
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                return null;
            }
            p = p.children[index];
        }
        return p;
    }
    
    public boolean search(String word) {
        Trie n = searchPrefix(word);
        if(n == null || n.isEnd == false){
            return false;
        }
        return true;
    }
    
    public boolean startsWith(String prefix) {
        if(searchPrefix(prefix) == null){
            return false;
        }
        return true;
    }
}
~~~

## Map

自己乱写的无向图

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

网上的邻接表构造无向图

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







