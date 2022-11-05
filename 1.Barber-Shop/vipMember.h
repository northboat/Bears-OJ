#pragma once
#include "Member.h"
#include "Tony.h"
class vipMember: virtual public Member//继承普通会员类
{
public:
	static int count;//记录vip会员数量 	
	vipMember(){} //构造函数 ① 
	vipMember(int num, string name, int phone, double remaining, int credits):Member(num,name,phone,remaining,credits){Discount = 0.75;}//构造函数 ② 		
	void Select(Tony tonys[]);//用以选定专用理发师 
    void Display();	
	void Set();
	void Transform(); 
//	void Cost();
//  void Recharge();  
private:
	Tony* p;
};
