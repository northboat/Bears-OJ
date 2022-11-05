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
