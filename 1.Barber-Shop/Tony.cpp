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
