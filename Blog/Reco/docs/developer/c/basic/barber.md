---
title: 理发店管理系统
date: 2020-12-14
tags:
  - C/C++
categories:
  - Toy
---

> 第一个小程序捏，黑框框

## Function

### Function.h

~~~c
#include <iostream>
#include <string>
#include "vipMember.h"
using namespace std;
void printMenu();//打印操作引导 
void PrintService(Member member);//打印普通会员服务菜单 
void PrintvipService(vipMember vip, Tony tonys[]);//打印vip会员服务菜单 
void PrintTony(Tony tonys[]);//打印三个理发师信息 
void PrintMember(Member members[]);//打印普通会员 
void PrintVip(vipMember vips[]);//打印Vip会员 
void UpdateTony(Tony tonys[]);//更新tony等级信息 
void SearchMember(Member members[], vipMember vip[], Tony tonys[]);//搜索普通会员 
void SearchVip(vipMember vips[], Tony tonys[]);//搜索vip会员 
void Update(Member member, vipMember vips[], Tony tonys[]);//升级普通会员为超级会员 
bool Loadin(string secret);//管理员登录 
string setSecret(string* secret);//设置密码 
void Cover(int t, Member members[]);//覆盖升级或注销的普通会员，达到 "删除"效果 
void coverVip(int t, vipMember vips[]);//与Cover同理 
void resetSecret(string* secret); 
~~~

### Function.cpp

~~~c
#include "Function.h"
void printMenu()
{
	cout << "* * * * * * * * * * * * * * * * * * * * * * * *\n";	
	cout << "* * * * * * * * * * * * * * * * * * * * * * * *\n";
	cout << "* * *     1、新建会员                 * * * * *\n";
	cout << "* * *     2、显示理发师信息           * * * * *\n";
	cout << "* * *     3、显示所有普通会员信息     * * * * *\n";
	cout << "* * *     4、显示所有vip会员信息      * * * * *\n";
	cout << "* * *     5、修改理发师级别           * * * * *\n";
	cout << "* * *     6、查找普通会员并选择服务   * * * * *\n";
	cout << "* * *     7、查找vip会员并选择服务    * * * * *\n";
	cout << "* * *     8、新建vip会员              * * * * *\n"; 
	cout << "* * *     9、重置密码                 * * * * *\n"; 
	cout << "* * *     0、退出系统                 * * * * *\n";	
	cout << "* * * * * * * * * * * * * * * * * * * * * * * *\n";
	cout << "* * * * * * * * * * * * * * * * * * * * * * * *\n";
	cout << "* * * * * * * * * * * * * * * * * * * * * * * *\n";
	cout << endl;	 
}
void PrintService(Member member)
{
	cout << "已搜索到用户" << member.getName() << "，请选择您需要的服务：\n";
	cout << "* * *   1、消费           * * *\n";
	cout << "* * *   2、充值           * * *\n";
	cout << "* * *   3、注销账户       * * *\n"; 
	cout << "* * *   4、显示用户信息   * * *\n\n";
}
void PrintvipService(vipMember vip, Tony tonys[])
{
	cout << "已搜索到用户" << vip.getName() << "，请选择您需要的服务：\n";
	cout << "* * *   1、消费              * * *\n";
	cout << "* * *   2、充值              * * *\n";
	cout << "* * *   3、注销账户          * * *\n";
	cout << "* * *   4、修改专用理发师    * * *\n";
	cout << "* * *   5、积分换现          * * *\n";
	cout << "* * *   6、显示用户信息      * * *\n\n";
}
string setSecret(string* secret)
{
	cout << "初次进入系统，请设置管理员密码：";
	cin >> *secret;
	return *secret;
}
bool Loadin(string secret)
{
	string password;
	cout << "需要管理员权限才能进行此项操作，请输入密码：";
	cin >> password;
	cout << endl; 
	if(password == secret)
	{
		return true;
	}
	else
	{
		cout << "密码错误！退出本次操作\n\n";
		return false;
	}
}
void PrintTony(Tony tonys[])//输出三个已录入的理发师信息 
{
	for(int t = 0; t < 3; t++)
	{
		tonys[t].Show();
	}
}
void PrintMember(Member members[])
{
	if(Member::Count == 0)
	{
		cout << "暂无普通会员信息！" << endl << endl;	
	} 
	for(int t = 0; t < Member::Count; t++)
	{
		members[t].Display();
	}
}
void PrintVip(vipMember vips[])
{
	if(vipMember::count == 0)
	{
		cout << "暂无vip会员信息！" << endl << endl;	
	} 
	for(int t = 0; t < vipMember::count; t++)
	{
		vips[t].Display();
	}
}
void UpdateTony(Tony tonys[])
{
	f:
	int num;
	cout << "请选择需要修改的理发师编号：";
	cin >> num;
	cout << endl;
	for(int t = 0; t < 3; t++)
	{
		if(num == tonys[t].getNum())
		{
			tonys[t].Update();
			break;
		}
		else if(t == 2 && num != tonys[t].getNum())
		{
			cout << "查无此理发师！\n";
			int i;	
			cout << "1、重新选择\t2、退出选择\n请做出您的选择：";
			cin >> i;
			switch(i)
			{
			case 1:
				goto f;
				break;
			case 2:
				cout << "已退出操作！\n\n";
				break;
			default:
				cout << "错误的操作，操作中断\n\n";
				break;
			}
		} 
	}
}
void SearchMember(Member members[], vipMember vip[], Tony tonys[])
{
	if(Member::Count == 0)
	{
		cout << "暂无普通会员信息\n\n";
	}
	else
	{
		int num;
		cout << "请输入您要查找的会员账号：";
		cin >> num;
		for(int t = 0; t < Member::Count; t++)
		{
			if(num == members[t].getNum())
			{
				PrintService(members[t]);
				int i;
				cout << "请选择您需要的服务：";
				cin >> i;
				switch(i)
				{
				case 1://消费服务 
					members[t].Cost();
					break;
				case 2://充值服务 
					members[t].Recharge();
					if(members[t].getCredits() >= 100)
					{
						Update(members[t], vip, tonys);
						Cover(t, members);
					}
					break;
				case 3://删除会员信息
					Cover(t, members);
					cout << "已注销！\n\n";
					break;
				case 4://显示会员信息 
					members[t].Display();
					break;
				default:
					cout << "输入错误，服务中断\n\n"; 
					break;
				}
				break;
			}
			if(t == Member::Count-1 && num != members[Member::Count-1].getNum())
			{
				cout << "账户不存在！\n\n";
			}
		}
	}
}
void Update(Member member, vipMember vips[], Tony tonys[])
{
	cout << "积分大于等于100，恭喜您升级为vip会员！您可以选择一位理发师作为您的专用理发师\n\n"; 
 	vips[vipMember::count].setNum(member.getNum());
 	vips[vipMember::count].setName(member.getName());
 	vips[vipMember::count].setPhone(member.getPhone());
 	vips[vipMember::count].setRemaining(member.getRemaining());
 	vips[vipMember::count].setCredits(member.getCredits());
 	vips[vipMember::count].setDiscount(0.75);
	PrintTony(tonys);
	vips[vipMember::count].Select(tonys);
	vips[vipMember::count].Display();
	vipMember::count++;
}
void Cover(int t, Member members[])
{
	for(int i = t; i < Member::Count-1; i++)
	{
		members[i].setNum(members[i+1].getNum());
		members[i].setName(members[i+1].getName());
		members[i].setPhone(members[i+1].getPhone());
		members[i].setRemaining(members[i+1].getRemaining());
		members[i].setCredits(members[i+1].getCredits());
	}
	Member::Count--;
}
void SearchVip(vipMember vips[], Tony tonys[])
{
	if(vipMember::count == 0)
	{
		cout << "暂无vip会员信息\n\n";
	}
	else
	{ 
		int num;
		cout << "请输入您要查找的vip账号：";
		cin >> num;
		for(int t = 0; t < vipMember::count; t++)
		{
			if(num == vips[t].getNum())
			{
				PrintvipService(vips[t], tonys);
				int i;
				cout << "请选择您需要的服务：";
				cin >> i;
				switch(i)
				{
				case 1:
					vips[t].Cost();
					break;
				case 2:
					vips[t].Recharge();
					break;
				case 3:
					coverVip(t, vips);
					cout << "已注销！\n\n";
					break;
				case 4:
					PrintTony(tonys);				
					vips[t].Select(tonys);
					vips[t].Display();
					break;
				case 5:
					vips[t].Transform();
					break;
				case 6:
					vips[t].Display();
					break;
				default:
					cout << "输入错误，服务中断\n\n"; 
				break;
				}
				break;
			}
			if(t == vipMember::count-1 && num != vips[vipMember::count-1].getNum())
			{
				cout << "账户不存在！\n\n";
			}		
		}	
	}
}
void coverVip(int t, vipMember vips[])
{
	for(int i = t; i < vipMember::count-1; i++)
	{
		vips[i].setNum(vips[i+1].getNum());
		vips[i].setName(vips[i+1].getName());
		vips[i].setPhone(vips[i+1].getPhone());
		vips[i].setRemaining(vips[i+1].getRemaining());
		vips[i].setCredits(vips[i+1].getCredits());
	}
	vipMember::count--;	
}
void resetSecret(string* Secret)
{
	R:
	string oldSecret;
	cout << "输入旧密码：";
	cin >> oldSecret;
	if(*Secret == oldSecret)
	{
		string newSecret;
		cout << "\n输入新密码：";
		cin >> newSecret;
		*Secret = newSecret;
		cout << "修改成功！\n\n";
	}
	else
	{
		char n;
		cout << "\n密码错误\n\n1、重试\t2、退出操作\n\n";
		cin >> n;
		switch(n)
		{
		case '1':
			goto R;
		case '2':
			cout << "已退出！\n\n";
			break;
		default:
			cout << "错误的操作，操作中断！\n\n";
			break;
		}
	}
}
~~~

## Member

### Member.h

~~~c
#include <iostream>
#include <string>
#include <fstream>
using namespace std;
class Member
{
public:
	static int Count;//记录普通会员个数	
	Member(){}//构造函数 
	Member(int num, string name, long long phone, double remaining, int credits);
	int getNum();//定义接口调用私有成员 
	string getName();
	long long getPhone();
	double getRemaining();
	double getCredits();
	void setDiscount(double discount);
	void setNum(int num);
	void setName(string name);
	void setPhone(long long phone);
	void setRemaining(double remaining);
	void setCredits(double credits);
	virtual void Set();//录入信息函数
	virtual void Display();//打印信息函数
	virtual void Cost();//修订函数：用以修改用户消费次数
	virtual void Recharge();//充值函数：用以充费
protected:
	double Discount;//普通会员统一折扣 
private:	
	int Num;
	string Name;
	long long Phone;
	double Remaining;//账户余额
	double Credits;//用户积分数	
};
~~~

### Member.cpp

~~~c
#include "Member.h" 
int Member::Count = 0;
Member::Member(int num, string name, long long phone, double remaining, int credits)
{
	Num = num;
	Name = name;
	Phone = phone;
	Remaining = remaining;
	Credits = credits;
}
void Member::Set()
{
	cout << "输入会员号：";
	cin >> Num;
	cout << "会员姓名：";
	cin >> Name;
	cout << "手机号：";
	cin >> Phone;
	Credits = 0;
	Remaining = 0;
	Discount = 0.9;	
	Recharge(); //在创建会员时同时充费一次 
	cout << "已录入！" << endl;
	Member::Count++;	
	Display();
}
void Member::Display()
{
	cout << "会员号：" << Num << "  姓名：" << Name  << "  联系方式：" << Phone << "  会员积分：" ;
	cout << Credits << "  账户余额：" << Remaining << "  当前折扣：" << Discount*10 << "折" << endl << endl;
}
void Member::Cost()
{
	cout << "输入用户本次消费项目：1.洗(10元/次)	   2.剪（25元/次）	   	3.吹（100元/次）\n";
	cout << "                      4.洗剪(30元/次)      5.洗剪吹(120元/次)\n";
	cout << "当前折扣："  << Discount << endl; 
	int n;
	cout << "请选择您的消费项目：";
	cin >> n;
	switch (n)
	{
	case 1:
		if(Remaining >= 10*Discount)
		{
			Remaining = Remaining - 10*Discount;//每"洗"一次余额减去10元
			cout << "消费成功！本次服务共120元，折扣" << Discount << "折,实际扣费" << 10*Discount << "元\n";
			cout << "当前余额：" << Remaining << "\n\n";
		}
		else
		{
			cout << "余额不足，消费失败\n\n";
		}
		break;
	case 2:
		if(Remaining >= 25*Discount)
		{
			Remaining = Remaining - 25 * Discount;//每"剪"一次余额减去25元
			cout << "消费成功！本次服务共120元，折扣" << Discount << "折,实际扣费" << 25*Discount << "元\n";
			cout << "当前余额：" << Remaining << "\n\n";
		}
		else
		{
			cout << "余额不足，消费失败\n\n";
		}
		break;
	case 3:
        if(Remaining >= 25*Discount)
        {
			Remaining = Remaining - 100 * Discount;//每"吹"一次余额减去100元
			cout << "消费成功！本次服务共120元，折扣" << Discount << "折,实际扣费" << 100*Discount << "元\n";
			cout << "当前余额：" << Remaining << "\n\n";
		}
		else
		{
			cout << "余额不足，消费失败\n\n";
		}		
		break;
	case 4:
		if(Remaining >= 30*Discount)
		{
			Remaining = Remaining - 30 * Discount;//每"洗剪"一次余额减去30元
			cout << "消费成功！本次服务共120元，折扣" << Discount << "折,实际扣费" << 30*Discount << "元\n";
			cout << "当前余额：" << Remaining << "\n\n";
		}
		else
		{
			cout << "余额不足，消费失败\n\n";
		}
		break;
	case 5:
		if(Remaining >= 120*Discount)
		{
			Remaining = Remaining - 120 * Discount;//每"洗剪吹"一次余额减去120元
			cout << "消费成功！本次服务共120元，折扣" << Discount << "折,实际扣费" << 120*Discount << "元\n";
			cout << "当前余额：" << Remaining << "\n\n";
		}
		else
		{
			cout << "余额不足，消费失败\n\n";
		}
		break;
	default:
		cout << "无此服务，已退出本次操作\n\n";
		break;
	}
}
void Member::Recharge()
{
	double remaining;	
	cout << "输入充值金额（每充值50元送30元，每充值100元积25分，积分累计100升级为超级会员）：";
	cin >> remaining;
	if(remaining < 0)
	{
		cout << "\n你在试图找Bug，但我明确告诉你这是不可能的\n\n";
	}
	else if(remaining >= 50)
	{
		Remaining += remaining + (int)(remaining/50) * 30;
		Credits += remaining * 0.25; 
	}
	else if(remaining < 50)
	{
		Remaining += remaining;
		Credits += remaining * 0.25;
	}
	cout << "当前余额：" << Remaining << "  当前积分：" << Credits << "\n\n";
}
int Member::getNum()
{
	return Num;
}
string Member::getName()
{
	return Name;
}
long long Member::getPhone()
{
	return Phone;
}
double Member::getRemaining()
{
	return Remaining;
}
double Member::getCredits()
{
	return Credits;
}
void Member::setNum(int num)
{
	Num = num;
}
void Member::setName(string name)
{
	Name = name;
}
void Member::setPhone(long long phone)
{
	Phone = phone;
}
void Member::setRemaining(double remaining)
{
	Remaining = remaining;
}
void Member::setCredits(double credits)
{
	Credits = credits;
}
void Member::setDiscount(double discount)
{
	Discount = discount;
}
~~~

## Tony

### Tony.h

~~~c
#include <iostream>
#include <string>
#include <fstream>
using namespace std;
class Tony
{
public:
	Tony() {}
	Tony(int num, string name, string grade) :Num(num), Name(name), Grade(grade) {};
	void Set();//建立理发师 
	int getNum();//获得私有成员编号 
	void Show();//打印理发师信息 
	void Update();//更新理发师级别 
private:
	int Num;
	string Name;
	string Grade;
};
~~~

### Tony.cpp

~~~c
#include "Tony.h"
void Tony::Set()
{
	cin >> Num >> Name >> Grade; 		
} 
void Tony::Show()
{
	cout << "专用理发师编号： " << Num << " 	理发师姓名：";
	cout << Name << "          理发师级别：" << Grade << endl << endl; 
}
void Tony::Update()
{
	string grade;
	cout << "请输入理发师需要修改的级别：";
	cin >> grade;
	Grade = grade;
	cout << "\n修改成功！\n\n";
	Show();
}
int Tony::getNum()
{
	return Num;
}
~~~

## vipMember

### vipMember.h

~~~c
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
~~~

### vipMember.cpp

~~~c
#include "vipMember.h"
int vipMember::count = 0;
void vipMember::Set()
{
	int num;
	cout << "输入会员号：";
	cin >> num;
	setNum(num);
	string name;
	cout << "会员姓名：";
	cin >> name;
	setName(name);
	long long phone;
	cout << "手机号：";
	cin >> phone;
	setPhone(phone);
	setCredits(0);
	setRemaining(0);
	Discount = 0.75;	
	Recharge(); //在创建会员时同时充费一次 
	cout << "已录入！\n\n";
	vipMember::count++;	
}
void vipMember::Select(Tony tonys[])
{
	F:
 	int n;
 	cout << "请选择您的专用理发师：";
 	cin >> n;
 	switch(n)
 	{
 	case 1:
 		p = tonys;
 		break;
 	case 2:
 		p = &tonys[1];
 		break;
 	case 3:
 		p = &tonys[2];
 		break;
 	default:
 		cout << "请输入正确的编号！" << endl;
 		goto F;
	}
}
void vipMember::Display()
{
	cout << "会员号：" << getNum() << "  姓名：" << getName();
	cout << "  联系方式：" << getPhone() << "  会员积分：";
	cout << getCredits() << "  账户余额：" << getRemaining();
	cout << "  当前折扣：" << Discount*10 << "折" << endl;
	p->Show();
}
void vipMember::Transform()
{
	cout << "当前用户积分：" << getCredits() << endl;
	double credits;
	cout << "请输入您要兑换的积分：";
	cin >> credits;
	if(credits <= getCredits())
	{
		setRemaining(getRemaining() + credits*0.5);
		setCredits(getCredits() - credits);
		cout << "当前余额：" << getRemaining() << "  当前积分：" << getCredits() << "\n\n";
	}
	else
	{
		cout << "积分不足，服务中断" << "\n\n";
	}
}
~~~

## Main

~~~c
#include <iostream>
#include <math.h>
#include "Function.h"
#define MAX 1000
#define N 3
#include <fstream> 
static string Secret;
using namespace std;

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
Member members[MAX];
vipMember vips[MAX/10];
Tony tonys[N] =
{
	Tony(1, "李国瑞", "高级"), 
	Tony(2, "王和兴", "高级"),
	Tony(3, "熊舟桐", "初级")
};

int main(int argc, char** argv) 
{
	cout << "                Hello World!" << endl;
	printMenu();//打印操作提示菜单 
	setSecret(&Secret);
	while(true)//true死循环 
	{
		system("pause");
		system("cls"); 
		printMenu();
		char t;//通过输入不同t值进入不同语句功能 
		cout << "* * * * * * * \n";
		cout << "请输入指令：";	
		cin >> t;
		cout << endl;
		switch(t)
		{
			case '0'://退出系统 
			{
		        goto FLAG;
			}
			case '1'://新建会员 
			{	
				members[Member::Count].Set();
				if(members[Member::Count-1].getCredits() >= MAX/10)
				{
					Update(members[Member::Count-1], vips, tonys);
					Cover(Member::Count-1, members);
				}
	   			break;
			}
			case '2'://打印理发师信息 
			{
				PrintTony(tonys);
				break;
			} 
			case '3'://打印普通会员信息 
			{
				PrintMember(members);
				break;
			} 
			case '4'://打印vip会员信息 
			{ 
				PrintVip(vips);
				break;
			} 
			case '5'://更新理发师信息 
			{ 
				if(Loadin(Secret) == true)
				{
					PrintTony(tonys);
					UpdateTony(tonys);
				}
				break;
			}
			case '6'://查找普通会员并进行下一步操作 
			{
				SearchMember(members, vips, tonys);
				break;
			}
			case '7'://查找vip会员并进行下一步操作
			{
				SearchVip(vips, tonys);
				break; 
			}
			case '8':
			{ 
				if(Loadin(Secret) == true)
				{
					vips[vipMember::count].Set();
					PrintTony(tonys);
					vips[vipMember::count-1].Select(tonys);
					vips[vipMember::count-1].Display();
				}
				break;
			}
			case '9':
			{
				resetSecret(&Secret);
				break;
			}
			default:
			{
				cout << "请输入正确的指令!\n\n";
			 	break;
			}
        }
    }
	
	FLAG:
	cout << "已退出系统！按任意键关闭窗口" << endl; 
	system("pause"); 
	return 0;
}
~~~



