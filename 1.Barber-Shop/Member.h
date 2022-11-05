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
