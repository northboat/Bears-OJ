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
