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
