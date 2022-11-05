#include <iostream>
#include <graphics.h>//包含了windows.h
#include <mmsystem.h>//包含多媒体设备头文件
#include <conio.h>//系统自带库
#include <cstdlib>//生成随机数
#include <ctime>//根据时间生成随机数
#pragma comment(lib, "winmm.lib")//加载静态库
//#include <setjmp.h>
using namespace std;



struct cube
{
	double a, b, c, d;
};
static cube formation1[] = { {340, 408, 695, 415}, {100, 340, 310, 343}, {725, 340, 935, 343}, {290, 218, 735, 225} };
static cube formation2[] = { {50, 408, 482, 415}, {532, 408, 974, 415}, {50, 318, 482, 325},
							 {532, 318, 974, 325}, {50, 228, 482, 235}, {532, 228, 974, 235} };
static cube formation3[] = { {275, 408, 760, 415}, {50, 168, 310, 175}, {725, 168, 975, 175} };
static cube formation4[] = { {0, 408, 422, 415}, {602, 408, 1024, 415}, {700, 318, 935, 325}, {90, 318, 330, 325}, {422, 228, 602, 235} };


class Matchman
{
private:
	int life1 = 3, life2 = 3;
	double x = 385, y = 380;
	double _x = 635, _y = 380;
	double HP = 100, MP = 100;
	double _HP = 100, _MP = 100;
public:
	Matchman(){}
	virtual ~Matchman() {}
	virtual void setX(double z);
	virtual void setY(double z);
	virtual void set_X(double z);
	virtual void set_Y(double z);
	virtual double getHP();
	virtual double get_HP();
	virtual int getLife1();
	virtual int getLife2();
	virtual void paint1();
	virtual void paint2();
	virtual void move1(int a);
	virtual void move2(int a);
	virtual void fight1();
	virtual void fight2();
	virtual void death();
	void getLocation();
//	virtual void skill() const = 0;
};

void Matchman::setX(double z)
{
	x = z;
}
void Matchman::setY(double z)
{
	y = z;
}
void Matchman::set_X(double z)
{
	_x = z;
}
void Matchman::set_Y(double z)
{
	_y = z;
}
double Matchman::getHP()
{
	return HP;
}
double Matchman::get_HP()
{
	return _HP;
}
int Matchman::getLife1()
{
	return life1;
}
int Matchman::getLife2()
{
	return life2;
}

//获取坐标
void Matchman::getLocation()
{
	cout << x << " " << y << "\t" << _x << " " << _y << endl;
}
//P1出生
void Matchman::paint1()
{
	setfillcolor(BLACK);
	fillcircle(x, y, 5);
	setlinecolor(BLACK);
	setlinestyle(PS_SOLID, 3);
	line(x, y + 5, x, y + 15);
	line(x, y + 5, x - 7, y + 17);
	line(x, y + 5, x + 7, y + 17);
	line(x, y + 15, x - 7, y + 27);
	line(x, y + 15, x + 7, y + 27);
	setfillcolor(RED);
	fillroundrect(-30, 0, HP*4, 30, 40, 40);
	setfillcolor(BLUE);
	fillroundrect(-30, 30, MP*4-20, 50, 10, 10);
}
//P2出生
void Matchman::paint2()
{
	setfillcolor(WHITE);
	solidcircle(_x, _y, 6);
	setlinecolor(WHITE);
	setlinestyle(PS_SOLID, 3);
	line(_x, _y + 5, _x, _y + 15);
	line(_x, _y + 5, _x - 7, _y + 17);
	line(_x, _y + 5, _x + 7, _y + 17);
	line(_x, _y + 15, _x - 7, _y + 27);
	line(_x, _y + 15, _x + 7, _y + 27);
	setlinecolor(BLACK);
	setfillcolor(RED);
	fillroundrect(1020 - _HP * 4, 0, 1050, 30, 40, 40);
	setfillcolor(BLUE);
	fillroundrect(1040 - _MP * 4, 30, 1050, 50, 10, 10);
}
//P1移动 不同地形移动
void Matchman::move1(int a)
{
	//被击退穿越地图
	if (x <= 5)
	{
		x = 1014;
	}
	if (x >= 1024)
	{
		x = 10;
	}
	//跳跃/飞行
	if (GetAsyncKeyState('W'))
	{
		if (y > 60 && MP > 4)//飞行
		{
			y -= 5;
			MP -= 0.1;
		}
		if (MP <= 4)
		{
			switch (a)
			{
			case 1:
				if (x > 305 && y >= 408 && x < 720 && y < 465 || x > 255 && y >= 218 && x < 785 && y < 225 ||
					x > 55 && y >= 341 && x < 340 && y <= 342 || x > 675 && y >= 341 && x < 995 && y <= 342)
				{
					y -= 100;
				}
				break;
			case 2:
				if (x > 50 && y > 408 && x < 482 && y < 415 || x > 532 && y > 408 && x < 974 && y < 415 || x > 50 && y > 318 && y < 482 && y < 325 ||
					x > 532 && y > 318 && x < 974 && y < 325 || x > 50 && y > 228 && x < 482 && y < 235 || x > 532 && y > 228 && x < 974 && y < 235)
				{
					y -= 80;
				}
				break;
			case 3:
				if (x > 275 && y > 408 && x < 760 && y < 415 || x > 50 && y> 168 && x < 310 && y < 175 || x < 725 && y > 168 && x < 975 && y < 175)
				{
					y -= 90;
				}
			case 4:
				if (x > 0 && y > 408 && x < 422 && y < 415 || x > 602 && y > 408 && x < 1024 && y < 415 || x > 700 && y > 318 && x < 935 && y < 325
					|| x > 90 && y > 318 && x < 330 && y < 325 || x > 422 && y > 228 && x < 602 && y < 235)
				{
					y -= 95;
				}
			}
		}		
	}
	//跨越地形下坠
	if (GetAsyncKeyState('S'))
	{
		switch (a)
		{
		case 1:
			for (int n = 0; n < 4; n++)
			{
				if (x > formation1[n].a && y > formation1[n].b && x < formation1[n].c && y < formation1[n].d)
				{
					y += 5;
					break;
				}
			}
			break;
		case 2:
			for (int n = 0; n < 6; n++)
			{
				if (x > formation2[n].a && y > formation2[n].b && x < formation2[n].c && y < formation2[n].d)
				{
					y += 5;
					break;
				}
			}
			break;
		case 3:
			for (int n = 0; n < 3; n++)
			{
				if (x > formation3[n].a && y > formation3[n].b && x < formation3[n].c && y < formation3[n].d)
				{
					y += 5;
					break;
				}
			}
			break;
		case 4:
			for (int n = 0; n < 5; n++)
			{
				if (x > formation4[n].a && y > formation4[n].b && x < formation4[n].c && y < formation4[n].d)
				{
					y += 5;
					break;
				}
			}
			break;
		}	
	}
	//向左移动
	if (GetAsyncKeyState('A'))
	{
		if (x > 10)//正常移动
		{
			x -= 5;
		}
		if (x <= 12)//穿越左侧地图到右侧
		{
			x = 1014;
		}
	}
	//向右移动
	if (GetAsyncKeyState('D'))
	{
		if (x < 1015)//正常移动
		{
			x += 5;
		}
		if (x >= 1014)//穿越右侧地图到左侧
		{
			x = 10;
		}
	}
	
	//重力和支持力
	switch (a)
	{
	case 1:
		if (x > 340 && y > 410 && x < 695 && y < 415)
		{
			y = y;
		}
		else if (x > 100 && y > 340 && x < 310 && y < 345)
		{
			y = y;
		}
		else if (x > 725 && y > 340 && x < 935 && y < 345)
		{
			y = y;
		}
		else if (x > 290 && y > 220 && x < 735 && y < 225)
		{
			y = y;
		}
		else if (y < 700)
		{
			y += 2;
		}
		break;
	case 2:
		if (x > 50 && y > 410 && x < 482 && y < 415)
		{
			y = y;
		}
		else if (x > 532 && y > 410 && x < 974 && y < 415)
		{
			y = y;
		}
		else if (x > 50 && y > 320 && x < 482 && y < 325)
		{
			y = y;
		}
		else if (x > 532 && y > 320 && x < 974 && y < 325)
		{
			y = y;
		}
		else if (x > 50 && y > 230 && x < 482 && y < 235)
		{
			y = y;
		}
		else if (x > 532 && y > 230 && x < 974 && y < 235)
		{
			y = y;
		}
		else if (y < 700)
		{
			y += 2;
		}
		break;
	case 3:
		if (x > 275 && y > 410 && x < 760 && y < 415)
		{
			y = y;
		}
		else if (x > 50 && y > 170 && x < 310 && y < 175)
		{
			y = y;
		}
		else if (x > 725 && y > 170 && x < 975 && y < 175)
		{
			y = y;
		}
		else if (y < 700)
		{
			y += 2;
		}
		break;
	case 4:
		if (x > 0 && y > 408 && x < 422 && y < 415)
		{
			y = y;
		}
		else if (x > 602 && y > 408 && x < 1024 && y < 415)
		{
			y = y;
		}
		else if (x > 700 && y > 318 && x < 935 && y < 325)
		{
			y = y;
		}
		else if (x > 90 && y > 318 && x < 330 && y < 325)
		{
			y = y;
		}
		else if (x > 422 && y > 228 && x < 602 && y < 235)
		{
			y = y;
		}
		else if (y < 700)
		{
			y += 2;
		}
		break;
	}
}
//P2移动
void Matchman::move2(int a)
{
	//被击退穿越地图
	if (_x <= 5)
	{
		_x = 1014;
	}
	if (_x >= 1024)
	{
		_x = 10;
	}
	//跳跃/飞行
	if (GetAsyncKeyState(VK_UP))
	{
		if (_y > 60 && _MP > 4)//飞行
		{
			_y -= 5;
			_MP -= 0.1;
		}
		if (_MP <= 4)
		{
			switch (a)
			{
			case 1:
				if (_x > 305 && _y >= 408 && _x < 720 && _y < 465 || _x > 255 && _y >= 218 && _x < 785 && _y < 225 ||
					_x > 55 && _y >= 341 && _x < 340 && _y <= 342 || _x > 675 && _y >= 341 && _x < 995 && _y <= 342)
				{
					_y -= 100;
				}
				break;
			case 2:
				if (_x > 50 && _y > 408 && _x < 482 && _y < 415 || _x > 532 && _y > 408 && _x < 974 && _y < 415 || _x > 50 && _y > 318 && _y < 482 && _y < 325 ||
					_x > 532 && _y > 318 && _x < 974 && _y < 325 || _x > 50 && _y > 228 && _x < 482 && _y < 235 || _x > 532 && _y > 228 && _x < 974 && _y < 235)
				{
					_y -= 80;
				}

				break;
			case 3:
				if (_x > 275 && _y > 408 && _x < 760 && _y < 415 || _x > 50 && _y> 168 && _x < 310 && _y < 175
					|| _x < 725 && _y > 168 && _x < 975 && _y < 175)
				{
					_y -= 90;
				}
				break;
			case 4:
				if (_x > 0 && _y > 408 && _x < 422 && _y < 415 || _x > 602 && _y > 408 && _x < 1024 && _y < 415 || _x > 700 && _y > 318 && _x < 935 && _y < 325
					|| _x > 90 && _y > 318 && _x < 330 && _y < 325 || _x > 422 && _y > 228 && _x < 602 && _y < 235)
				{
					_y -= 95;
				}
			}
		}

	}
	//跨越地形下坠
	if (GetAsyncKeyState(VK_DOWN))
	{
		switch (a)
		{
		case 1:
			for (int n = 0; n < 4; n++)
			{
				if (_x > formation1[n].a && _y > formation1[n].b && _x < formation1[n].c && _y < formation1[n].d)
				{
					_y += 5;
					break;
				}
			}
			break;
		case 2:
			for (int n = 0; n < 6; n++)
			{
				if (_x > formation2[n].a && _y > formation2[n].b && _x < formation2[n].c && _y < formation2[n].d)
				{
					_y += 5;
					break;
				}
			}
			break;
		case 3:
			for (int n = 0; n < 3; n++)
			{
				if (_x > formation3[n].a && _y > formation3[n].b && _x < formation3[n].c && _y < formation3[n].d)
				{
					_y += 5;
					break;
				}
			}
			break;
		case 4:
			for (int n = 0; n < 5; n++)
			{
				if (_x > formation4[n].a && _y > formation4[n].b && _x < formation4[n].c && _y < formation4[n].d)
				{
					_y += 5;
					break;
				}
			}
			break;
		}
	}
	//向左移动
	if (GetAsyncKeyState(VK_LEFT))
	{
		if (_x > 10)//正常移动
		{
			_x -= 5;
		}
		if (_x <= 12)//穿越左侧地图到右侧
		{
			_x = 1014;
		}
	}
	//向右移动
	if (GetAsyncKeyState(VK_RIGHT))
	{
		if (_x < 1015)//正常移动
		{
			_x += 5;
		}
		if (_x >= 1014)//穿越右侧地图到左侧
		{
			_x = 10;
		}
	}

	//重力和支持力
	switch (a)
	{
	case 1:
		if (_x > 340 && _y > 410 && _x < 695 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 100 && _y > 340 && _x < 310 && _y < 345)
		{
			_y = _y;
		}
		else if (_x > 725 && _y > 340 && _x < 935 && _y < 345)
		{
			_y = _y;
		}
		else if (_x > 290 && _y > 220 && _x < 735 && _y < 225)
		{
			_y = _y;
		}
		else if (_y < 700)
		{
			_y += 2;
		}
		break;
	case 2:
		if (_x > 50 && _y > 410 && _x < 482 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 532 && _y > 410 && _x < 974 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 50 && _y > 320 && _x < 482 && _y < 325)
		{
			_y = _y;
		}
		else if (_x > 532 && _y > 320 && _x < 974 && _y < 325)
		{
			_y = _y;
		}
		else if (_x > 50 && _y > 230 && _x < 482 && _y < 235)
		{
			_y = _y;
		}
		else if (_x > 532 && _y > 230 && _x < 974 && _y < 235)
		{
			_y = _y;
		}
		else if (_y < 700)
		{
			_y += 2;
		}
		break;
	case 3:
		if (_x > 275 && _y > 410 && _x < 760 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 50 && _y > 170 && _x < 310 && _y < 175)
		{
			_y = _y;
		}
		else if (_x > 725 && _y > 170 && _x < 975 && _y < 175)
		{
			_y = _y;
		}
		else if (_y < 700)
		{
			_y += 2;
		}
		break;
	case 4:
		if (_x > 0 && _y > 408 && _x < 422 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 602 && _y > 408 && _x < 1024 && _y < 415)
		{
			_y = _y;
		}
		else if (_x > 700 && _y > 318 && _x < 935 && _y < 325)
		{
			_y = _y;
		}
		else if (_x > 90 && _y > 318 && _x < 330 && _y < 325)
		{
			_y = _y;
		}
		else if (_x > 422 && _y > 228 && _x < 602 && _y < 235)
		{
			_y = _y;
		}
		else if (_y < 700)
		{
			_y += 2;
		}
		break;
	}
}
//P1攻击
void Matchman::fight1()
{
	//轰波
	double a = x, b = y;
	if (GetAsyncKeyState('K'))
	{
		while (MP > 4)
		{
			MP -= 0.0005;
			setfillcolor(LIGHTGREEN);
			solidcircle(a, b, 2);
			if (x <= _x)
			{
				//攻击距离
				if (a - x == 240 || x - a == 240)
				{
					a = -1;
					break;
				}
				//击中敌人
				else if (a >= _x - 10 && b >= _y - 5 && a <= _x + 10 && b <= _y + 30)
				{
					a = -1;
					_HP -= 0.3;
					_x += 10;
					break;
				}
				//子弹飞行
				else
				{
					a += 1;
					if (a > 1020 || a < 0)
					{
						a = -5;
						break;
					}
				}
			}
			else if (x > -x)
			{
				//攻击距离
				if (a - x == 245 || x - a == 245)
				{
					a = -1;
					break;
				}
				//击中敌人
				else if (a >= _x - 10 && b >= _y - 5 && a <= _x + 10 && b <= _y + 30)
				{
					a = -1;
					_HP -= 0.3;
					_x -= 10;//击退效果
					break;
				}
				//子弹飞行
				else
				{
					a -= 1;
					if (a > 1020 || a < 0)
					{
						a = -5;
						break;
					}
				}
			}
		}
	}
	//平A
	double c = x, d = y, z = 0, t = 0;
	setlinecolor(WHITE);
	if (GetAsyncKeyState('J'))
	{
		z = 15;
		t = 10;
		if (c >= _x - 35 && d >= _y - 5 && c <= _x + 35 && d <= _y + 30)
		{
			_HP -= 3;
			MP += 0.3;
			if (x <= _x)//击退效果
			{
				_x += 15;
			}
			else
			{
				_x -= 15;
			}
		}
	}
	if (x <= _x)
		line(c + 9, d + 14, c + 18 + t, d - 1 + z);
	else
		line(c - 9, d + 14, c - 18 - t, d - 1 + z);
}
//P2攻击
void Matchman::fight2()
{
	//轰波
	double a = _x, b = _y;
	if (GetAsyncKeyState(VK_NUMPAD2))
	{
		while (_MP > 2)
		{
			_MP -= 0.0005;
			setfillcolor(LIGHTGREEN);
			solidcircle(a, b, 2);
			if (_x <= x)
			{
				//攻击距离
				if (a - _x == 245 || _x - a == 245)
				{
					a = -1;
					break;
				}
				//击中敌人
				else if (a >= x - 10 && b >= y - 5 && a <= x + 10 && b <= y + 30)
				{
					a = -1;
					HP -= 0.3;
					x += 10;
					break;
				}
				//子弹飞行
				else
				{
					a += 1;
					if (a > 1020 || a < 0)
					{
						a = -5;
						break;
					}
				}
			}
			else
			{
				//攻击距离
				if (a - _x == 240 || _x - a == 240)
				{
					a = -1;
					break;
				}
				//击中敌人
				else if (a >= x - 10 && b >= y - 5 && a <= x + 10 && b <= y + 30)
				{
					a = -1;
					HP -= 0.3;
					x -= 10;
					break;
				}
				//子弹飞行
				else
				{
					a -= 1;
					if (a > 1020 || a < 0)
					{
						a = -5;
						break;
					}
				}
			}
		}
	}
	//平A
	double c = _x, d = _y, z = 0, t = 0;
	setlinecolor(BLACK);
	if (GetAsyncKeyState(VK_NUMPAD1))
	{
		z = 15;
		t = 10;
		//造成伤害
		if (c >= x - 35 && d >= y - 5 && c <= x + 35 && d <= y + 30)
		{
			HP -= 3;
			_MP += 0.3;
			if (_x <= x)
			{
				x += 15;
			}
			else
			{
				x -= 15;
			}
		}
	}
	if (_x <= x)
		line(c + 9, d + 14, c + 18 + t, d - 1 + z);
	else
		line(c - 9, d + 14, c - 18 - t, d - 1 + z);
}
//角色空血
void Matchman::death()
{
	//当掉出地图
	if (y > 600)
	{
		HP -= 100;
	}
	if (_y > 600)
	{
		_HP -= 100;
	}
	//空血后重生（有命时）
	if (HP <= 0)
	{
		life1--;
		HP = 100;
		MP = 100;
		x = 385;
		y = 100;
	}
	if (_HP <= 0)
	{
		life2--;
		_HP = 100;
		_MP = 100;
		_x = 635;
		_y = 100;
	}
}


//句柄弹窗
void change()
{
	//修改窗口句柄
	HWND window = GetHWnd();
	SetWindowText(window, "击剑火柴人");
	//弹出窗口，提示用户操作
/*	int isok = MessageBox(NULL, "Round 1", "提示", MB_OKCANCEL);
	if (isok == IDOK)
	{
		cout << "你点击了OK\n";
	}
	else if (isok == IDCANCEL)
	{
		cout << "你点击了取消\n";
	}*/
}
//背景音乐
void BGM()//播放音乐
{
	//随机选择音乐
	srand(time(NULL));
	int num = rand() % 5;
	//打开音乐
	switch (num)
	{
	case 1:
		mciSendString("open ./笑忘书.mp3 alias BGM", 0, 0, 0);
		break;
	case 2:
		mciSendString("open ./你给我听好.mp3 alias BGM", 0, 0, 0);
		break;
	case 3:
		mciSendString("open ./白玫瑰.mp3 alias BGM", 0, 0, 0);
		break;
	default:
		mciSendString("open ./K歌之王.mp3 alias BGM", 0, 0, 0);
		break;
	}
	//播放音乐
	mciSendString("play BGM", 0, 0, 0);
}
//登陆界面 游戏说明
int loadIn()
{
	while (1)
	{
	    F:
		BeginBatchDraw();
		IMAGE img;
		loadimage(&img, "./登陆界面.jpg", 1024, 573);
		putimage(0, 0, &img);
		setlinecolor(BLACK);
		setlinestyle(PS_SOLID, 3);
		MOUSEMSG msg = GetMouseMsg();
		//点击开始游戏
		if (msg.x > 445 && msg.x < 612 && msg.y > 321 && msg.y < 355)
		{
			line(440, 318, 440, 360);
			line(440, 318, 615, 318);
			line(440, 360, 615, 360);
			line(615, 318, 615, 360);
			if (msg.uMsg == WM_LBUTTONDOWN)
			{
				while (1)//选择地图
				{
					BeginBatchDraw();
					IMAGE img;
					loadimage(&img, "./背景集合.jpg", 1024, 534);
					putimage(0, 0, &img);
					MOUSEMSG msg = GetMouseMsg();
					setlinecolor(WHITE);
					//回到开始界面
					if (GetAsyncKeyState('B'))
					{
						goto F;
					}
					//选择图片
					if (msg.x >= 0 && msg.y >= 0 && msg.x <= 512 && msg.y <= 269)
					{
						line(0, 0, 0, 269);
						line(0, 0, 512, 0);
						line(0, 269, 512, 269);
						line(512, 0, 512, 269);
						if (msg.uMsg == WM_LBUTTONDOWN)
						{
							return 1;
						}
					}
					if (msg.x > 512 && msg.y >=0 && msg.x <= 1024 && msg.y <= 269)
					{
						line(512, 0, 1024, 0);
						line(512, 0, 512, 269);
						line(512, 269, 1024, 269);
						line(1024, 0, 1024, 269);
						if (msg.uMsg == WM_LBUTTONDOWN)
						{
							return 2;
						}
					}
					if (msg.x >= 0 && msg.y >= 269 && msg.x <= 512 && msg.y <= 534)
					{
						line(0, 269, 0, 534);
						line(0, 269, 512, 269);
						line(0, 534, 512, 534);
						line(512, 269, 512, 534);
						if (msg.uMsg == WM_LBUTTONDOWN)
						{
							return 3;
						}
					}
					if (msg.x > 512 && msg.y >= 269 && msg.x <= 1024 && msg.y <=534)
					{
						line(512, 269, 512, 534);
						line(512, 269, 1024, 269);
						line(512, 534, 1024, 534);
						line(1024, 269, 1024, 534);
						if (msg.uMsg == WM_LBUTTONDOWN)
						{
							return 4;
						}
					}
					FlushBatchDraw();
				}
			}
		}
		//点击游戏说明
		else if (msg.x > 445 && msg.x < 612 && msg.y > 379 && msg.y < 416)
		{
			line(440, 375, 615, 375);
			line(440, 375, 440, 420);
			line(440, 420, 615, 420);
			line(615, 375, 615, 420);
			if (msg.uMsg == WM_LBUTTONDOWN)
			{
				while (1)
				{
					BeginBatchDraw();
					IMAGE img;
					loadimage(&img, "./游戏说明.png", 1024, 534);
					putimage(0, 0, &img);
					if (GetAsyncKeyState('B'))
					{
						goto F;
					}
					EndBatchDraw();
				}
			}
		}
		//点击退出游戏
		if (msg.x > 445 && msg.x < 612 && msg.y > 441 && msg.y < 477)
		{
			line(440, 435, 440, 480);
			line(440, 435, 615, 435);
			line(440, 480, 615, 480);
			line(615, 435, 615, 480);
			if (msg.uMsg == WM_LBUTTONDOWN)
			{
				return 0;
			}
		}
		FlushBatchDraw();
	}
}
//背景图像
void BackImag(int a)
{
	//定义图片变量
	IMAGE img;
	switch (a)
	{
	case 1:
		loadimage(&img, "./背景1.jpg", 1024, 534);
		break;
	case 2:
		loadimage(&img, "./背景2.jpg", 1024, 534);
		break;
	case 3:
		loadimage(&img, "./背景3.jpg", 1024, 534);
		break;
	case 4:
		loadimage(&img, "./背景4.jpg", 1024, 534);
	}
	putimage(0, 0, &img);
}
//地形显示 新地图！
void Formation(int a)
{
	switch (a)
	{
	case 1:
		setfillcolor(BLACK);
		fillroundrect(340, 440, 695, 445, 7, 7);
		fillroundrect(100, 370, 310, 375, 5, 5);
		fillroundrect(725, 370, 935, 375, 5, 5);
		fillrectangle(290, 250, 735, 255);
		break;
	case 2:
		setfillcolor(DARKGRAY);
		fillroundrect(50, 440, 482, 445, 7, 7);
		fillroundrect(532, 440, 974, 445, 5, 5);
		fillroundrect(50, 350, 482, 355, 5, 5);
		fillroundrect(532, 350, 974, 355, 5, 5);
		fillroundrect(50, 260, 482, 265, 5, 5);
		fillroundrect(532, 260, 974, 265, 5, 5);
		break;
	case 3:
		setfillcolor(CYAN);
		fillroundrect(275, 440, 760, 445, 7, 7);
		fillroundrect(50, 200, 310, 205, 5, 5);
		fillroundrect(725, 200, 975, 205, 5, 5);
		break;
	case 4:
		setfillcolor(MAGENTA);
		fillroundrect(0, 440, 422, 445, 7, 7);
		fillroundrect(602, 440, 1024, 445, 5, 5);
		fillroundrect(700, 350, 935, 355, 5, 5);
		fillrectangle(90, 350, 330, 355);
		fillrectangle(422, 260, 602, 265);
		break;
	}
}
//胜利显示
bool Winning(int a, int b, int c)
{
	if (a == 0 || b == 0)
	{
		switch (c)
		{
		case 1:
			settextcolor(WHITE);
			break;
		case 2:
			settextcolor(WHITE);
			break;
		case 3:
			settextcolor(BLACK);
			break;
		case 4:
			settextcolor(BLACK);
			break;
		}
		char str[] = "按B键继续";
		char str1[] = "P2! y y d 击剑王!";
		char str2[] = "P1! y y d 击剑王!";
		setbkmode(TRANSPARENT);//反式：== 透明（transparent）
		while (1)
		{
			BeginBatchDraw();
			if (a == 0)
			{
				settextstyle(80, 0, "宋体");
				outtextxy(200, 170, str1);
				settextstyle(20, 0, "宋体");
				outtextxy(480, 300, str);
			}
			else if (b == 0)
			{
				settextstyle(80, 0, "宋体");
				outtextxy(200, 170, str2);
				settextstyle(20, 0, "宋体");
				outtextxy(480, 300, str);
			}
			if (GetAsyncKeyState('B'))
			{
				break;
			}
			FlushBatchDraw();
		}
		return true;
	}
	return false;
}

//程序运行
int main()
{
	initgraph(1024, 534);//创建图形窗口不保留控制台
	change();//设置句柄、弹窗
	FLAG://重开
	BGM();//播放音乐
	int enter = loadIn();//获得用户选择的背景及地形编号
	Matchman matchman;
	while (enter)
	{
		BeginBatchDraw();
		cleardevice();

		BackImag(enter);//加载背景图片
		Formation(enter);//加载地形

		matchman.paint1();//创建P1形象
		matchman.paint2();//创建P2形象
		matchman.move1(enter);//P1移动规则
		matchman.move2(enter);//P2移动规则
		matchman.fight1();//P1攻击
		matchman.fight2();//P2攻击

		matchman.death();//空血减命重生

		//当生命为零宣布胜者并返回开始界面
		if (Winning(matchman.getLife1(), matchman.getLife2(), enter) || GetAsyncKeyState('B'))
		{
			goto FLAG;
		}
		FlushBatchDraw();// 比EndBatchDraw()稳不止一点
	}
	closegraph();
	return 0;
}

/*	笔记
	setfillcolor(BLUE);
	fillcircle(50, 50, 50);


	setlinecolor(BLACK);
	setlinestyle(PS_SOLID, 3);
	settextcolor(BLACK);
	setbkmode(TRANSPARENT);//反式：== 透明（transparent）
	settextstyle(50, 0, "宋体");
	outtextxy(275, 300, "wdnmd!");


	fillrectangle(200, 350, 500, 400);//在创建的矩形中居中显示文字
	char str[] = "我带你们打";
	int width = 200 + (300 - textwidth(str)) / 2;
	int height = 350 + (50 - textheight(str)) / 2;
	outtextxy(width, height, str);*/