//#include "mpc.h"
//#ifdef _WIN32
//
//static char buffer[2084];
//
//char* readLine(char* prompt){
//	fputs(prompt, stdout);
//	fgets(buffer, 2084, stdin);
//	char* cpy = malloc(strlen(buffer)+1);
//
//	strcpy(cpy, buffer);
//	cpy[strlen(buffer)-1] = '\0';
//	return cpy;
//} 
//
//void add_history(char* unused){}
//
//
//#else
//#include <readline/readline.h>
//#include <readline/history.h>
//#endif
//
////枚举：0，1，2 
//enum{
//	LERR_DIV_ZERO, 
//	LERR_BAD_OP, 
//	LERR_BAD_NUM 
//};
//
//enum{
//	LVAL_NUM,
//	LVAL_ERR
//};
//
//typedef struct{
//	int type;
//	//如果是数字，num储存其值 
//	long num;
//	//如果是错误，err存储其错误类型，枚举类LERR_DIV_ZERO...
//	int err;
//} lval;
//
//lval lval_num(long x){
//	lval l;
//	l.type = LVAL_NUM;
//	l.num = x;
//	return l;
//}
//
//lval lval_err(int x){
//	lval l;
//	l.type = LVAL_ERR;
//	l.err = x;
//	return l;
//}
//
//void lval_print(lval v){
//	
//	switch(v.type){
//		case LVAL_NUM: printf("%li", v.num); break;
//		case LVAL_ERR:
//			if(v.err == LERR_DIV_ZERO){
//				printf("Error: Division By Zero!");
//			}
//			if(v.err == LERR_BAD_OP){
//				printf("Error: Invalid Operator!");
//			}
//			if(v.err == LERR_BAD_NUM){
//				printf("Error: Invalid Number!");
//			}
//			//printf("hahaha");
//			break;
//	}
//}
//
//void lval_println(lval v){
//	lval_print(v);
//	putchar('\n');
//}
//
//lval eval_op(lval x, char* op, lval y){
//	
//	if(x.type == LVAL_ERR){
//		return x;
//	}
//	if(y.type == LVAL_ERR){
//		return y;
//	}
//	
//	if(strcmp(op, "+") == 0)	return lval_num(x.num+y.num);
//	if(strcmp(op, "-") == 0)	return lval_num(x.num-y.num);
//	if(strcmp(op, "*") == 0)	return lval_num(x.num*y.num);
//	if(strcmp(op, "/") == 0){
//		return y.num == 0
//			? lval_err(LERR_DIV_ZERO)
//			: lval_num(x.num/y.num);
//	}
//	
//	return lval_err(LERR_BAD_OP);
//}
//
//lval eval(mpc_ast_t* t){
//	
//	if(strstr(t->tag, "number")){
//		errno = 0;
//		long x = strtol(t->contents, NULL, 10);
//		//ERANGE(erange)是c标准函式库中的标头档，定义了通过误码来汇报错误咨询的宏 
//		return errno != ERANGE
//			? lval_num(x)
//			: lval_err(LERR_BAD_NUM);
//	}
//	
//	char * op = t->children[1]->contents;
//	lval x = eval(t->children[2]);
//	
//	int i = 3;
//	while(strstr(t->children[i]->tag, "expr")){
//		x = eval_op(x, op, eval(t->children[i]));
//		i++;
//	}
//	
//	return x; 
//} 
//
//int main(int argc, char** argv){
//	
//	mpc_parser_t* Number = mpc_new("number");	
//	mpc_parser_t* Operator = mpc_new("operator");	
//	mpc_parser_t* Expr = mpc_new("expr");	
//	mpc_parser_t* Lispy = mpc_new("lispy");	
//	mpca_lang(MPCA_LANG_DEFAULT,
//		//用mpc进行文法定义 
//		"\
//			number: /-?[0-9]+/; 						\
//			operator: '+'|'-'|'*'|'/'; 					\
//			expr: <number>|'('<operator><expr>+')'; 	\
//			lispy: /^/<operator><expr>+/$/;				\
//		",
//		Number, Operator, Expr, Lispy
//	);
//	
//	puts("Lispy Version 0.4\nPress Ctrl+c to Exit\n");
//	
//	while(1){
//		char* input = readLine("lispy>");
//		add_history(input);
//		
//		mpc_result_t res;
//		if(mpc_parse("<stdin>", input, Lispy, &res)){
//			lval ans = eval(res.output);
//			mpc_ast_print(res.output);
//			lval_println(ans);
//			mpc_ast_delete(res.output);
//		}else{
//			mpc_err_print(res.error);
//			mpc_err_delete(res.error);
//		}
//		
//		free(input);
//	}
//	
//	mpc_cleanup(4, Number, Operator, Expr, Lispy);
//	
//	return 0;
//}

