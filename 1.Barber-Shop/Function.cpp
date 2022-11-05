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


