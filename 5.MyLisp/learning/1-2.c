//#include <stdio.h>
//#include <string.h>
//#include "mpc.h"
////如果在WIN32环境，用自己写的readLine
//#ifdef _WIN32
////维护一个字符数组作为输入缓冲 
//static char buffer[2084];
//                                                                                 
////fputs、fgets、strcpy 
//char* readLine(char* prompt){
//	fputs(prompt, stdout);
//	fgets(buffer, 2084, stdin);
//	
//	char* cpy = malloc(strlen(buffer)+1);
//	
//	strcpy(cpy, buffer);
//	cpy[strlen(cpy) - 1] = '\0';
//	
//	return cpy;
//}
//
//
////记录输入历史，上键复制上条指令的功能WIN32控制台自带 
//void add_history(char* unused){}
//
////若在linux环境，引入包readline.h、history.h 
//#else
//#include <readline/readline.h>
//#include <readline/history.h>
//#endif
//
//
//
// 
//
////run this program using the console pauser or add your own getch, system("pause") or input loop
//int main(int argc, char *argv[]) {
//	
//	puts("Lispy Version 0.1\nPress Ctrl+c to Exit\n");
//	
//	while(1){
//		char* input = readLine("lispy>");
//		add_history(input);
//		printf("Now you're a %s\n", input);
//		free(input);
//	}
//	
//	return 0;
//}
//

