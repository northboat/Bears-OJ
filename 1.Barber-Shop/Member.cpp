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
