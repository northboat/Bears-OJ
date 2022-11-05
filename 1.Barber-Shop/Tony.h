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
