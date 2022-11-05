//#include "mpc.h"
//#ifdef _WIN32
//
//// * 1100000000000 10000000000:可能存在一些问题，也有可能是c标准造成的差异 
//
// 
//static char* buffer[2084];
//
//char* readLine(char* prompt) {
//	fputs(prompt, stdout);
//	fgets(buffer, 2084, stdin);
//	
//	char* cpy = (char*)malloc(strlen(buffer)+1); 
//	strcpy(cpy, buffer);
//	
//	cpy[strlen(buffer)-1] = '\0';
//	return cpy;
//}
//
//void add_history(char* unused){}
//
//#else
//#include <readline/readline.h>
//#include <readline/history.h>
//#endif
//
//
//
///**
// * prompt:促使 
// * lvalue:左值
// * expression:表达式
// * evalution:求值 
// * parser:编译 
// * S-expression:lisp特有的表达式 
// * mpc_ast_t:是mpc_parser_t(编译树)的结果式(tree.output)，一颗语法树 
// * errno\ERANGE:是c语言stdlib.h库中的定义的错误码，定义为整数值的宏 
//*/ 
//
////宏定义断言 
//#define LASSERT(args, cond, err) \
//	if(cond){ lval_del(args); return lval_err(err); }
//
//
////枚举左推导类型 
//enum{ LVAL_ERR, LVAL_NUM, LVAL_SYM, LVAL_SEXPR, LVAL_QEXPR };
//
//
//typedef struct lval{
//	int type;
//	long num;
//	//用字符串来表示错误和符号更完整高效 
//	char* err;
//	char* sym;
//	//记录当前左值能推出的表达式数量
//	int count;
//	//储存表达式（类型为lval*），用lval*的指针储存，即双指针lval** 
//	struct lval** cell; 
//} lval;
//
//
//
////使用指针构造提升效率 
//lval* lval_num(long x){
//	lval* v = malloc(sizeof(lval));
//	v->type = LVAL_NUM;
//	v->num = x;
//	return v;
//}
//
//
//
//lval* lval_err(char* m){
//	lval* v = malloc(sizeof(lval));
//	v->type = LVAL_ERR;
//	v->err = malloc(strlen(m)+1);
//	strcpy(v->err, m);
//	return v;
//}
//
//
//
//lval* lval_sym(char* s){
//	lval* v = malloc(sizeof(lval));
//	v->type = LVAL_SYM;
//	v->sym = malloc(strlen(s)+1);
//	strcpy(v->sym, s);
//	return v;
//}
//
//
//
//lval* lval_sexpr(void){
//	lval* v = malloc(sizeof(lval));
//	v->type = LVAL_SEXPR;
//	v->count = 0;
//	v->cell = NULL;
//	return v; 
//}
//
//
//lval* lval_qexpr(void){
//	lval* v = malloc(sizeof(lval));
//	v->type = LVAL_QEXPR;
//	v->count = 0;
//	v->cell = NULL;
//	return v;
//}
//
//
//
//void lval_del(lval* v){
//	
//	switch(v->type){
//		case LVAL_NUM: break;
//		//释放字符指针 
//		case LVAL_ERR: free(v->err); break;
//		case LVAL_SYM: free(v->sym); break;
//		
//		//二者删除逻辑一模一样，碰到Qexpr不退出继续执行即可 
//		case LVAL_QEXPR:
//		case LVAL_SEXPR:{
//			int i;
//			//将后继表达式依次释放，递归调用lval_del
//			for(i = 0; i < v->count; i++){
//				lval_del(v->cell[i]);
//			}
//			//释放lval双指针 
//			free(v->cell);
//			break;
//		}
//	}
//	//释放自身lval指针 
//	free(v);
//}
//
//
//
////realloc函数，重新对指针分配空间大小 
////将左值x加入到v的推导中 
//lval* lval_add(lval* v, lval* x){
//	v->count++;
//	v->cell = realloc(v->cell, sizeof(lval*)*v->count);
//	v->cell[v->count-1] = x;
//	return v;
//}
//
//
////将指定子节点弹栈，同时重定义指针空间 
//lval* lval_pop(lval* v, int i){
//	lval* x = v->cell[i];
//	
//	memmove(&v->cell[i], &v->cell[i+1], sizeof(lval*)*(v->count-i-1));
//	
//	v->count--;
//	v->cell = realloc(v->cell, sizeof(lval*)*v->count);
//	return x;
//}
//
//lval* lval_take(lval* v, int i){
//	lval* x = lval_pop(v, i);
//	lval_del(v);
//	return x; 
//}
//
//
//
////打印 
////打印输出一个左推导，与打印输出一个s-express表达式互相嵌套，实现表达式的打印 
//void lval_expr_print(lval* v, char open, char close);
//
//void lval_print(lval* v){
//	switch(v->type){
//		case LVAL_NUM: printf("%li", v->num); 			break;
//		case LVAL_ERR: printf("Error: %s", v->err); 	break;
//		case LVAL_SYM: printf("%s", v->sym); 			break;
//		case LVAL_SEXPR: lval_expr_print(v, '(', ')'); 	break;
//		case LVAL_QEXPR: lval_expr_print(v, '{', '}'); 	break;
//	}
//}
//
//void lval_expr_print(lval* v, char open, char close){
//	putchar(open);
//	int i;
//	for(i = 0; i < v->count; i++){
//		lval_print(v->cell[i]);
//		if(i != v->count-1){
//			putchar(' ');
//		}
//	}
//	putchar(close);
//}
//
//void lval_println(lval* v){
//	lval_print(v);
//	putchar('\n');
//}
//
//
////内建函数们 
////1、数的计算 
//lval* builtin_op(lval* a, char* op){
//	int i;
//	for(i = 0; i < a->count; i++){
//		if(a->cell[i]->type != LVAL_NUM){
//			lval_del(a);
//			return lval_err("Cannot operate on non-number!");
//		}
//	}
//	
//	//开始弹栈，进行计算 
//	lval* x = lval_pop(a, 0);
//	if((strcmp(op, "-") == 0) && a->count == 0){
//		x->num = -x->num;
//	}
//	
//	//因为已经由语法树构造好了计算顺序，已经考虑过了优先级问题 
//	//尾递归的循环 
//	while(a->count > 0){
//		lval* y = lval_pop(a, 0);
//		
//		if(strcmp(op, "+") == 0){
//			x->num += y->num;
//		}else if(strcmp(op, "-") == 0){
//			x->num -= y->num;
//		}else if(strcmp(op, "*") == 0){
//			x->num *= y->num;
//		}else if(strcmp(op, "/") == 0){
//			if(y->num == 0){
//				//释放空间 
//				lval_del(x);
//				lval_del(y);
//				x = lval_err("Division By Zero!");
//				break;
//			}
//			x->num /= y->num;
//		}
//		lval_del(y);
//	}
//	
//	lval_del(a);
//	return x;
//}
//
////2、head函数，没用宏断言:删除传入左值a->cell中下标为1到最后的所有元素，即只返回cell[0] 
//lval* builtin_head(lval* a){
//	//错误检测 
//	if(a->count != 1){
//		lval_del(a);
//		return lval_err("Function 'head' passed too many arguments!");
//	}
//	if(a->cell[0]->type != LVAL_QEXPR){
//		lval_del(a);
//		return lval_err("Function 'head' passed incorrect types!");
//	}
//	if(a->cell[0]->count == 0){
//		lval_del(a);
//		return lval_err("Function 'head' passed {}!");
//	}
//	
//	//排除错误后取出第一个推导 
//	lval* v = lval_take(a, 0);
//	
//	while(v->count >1){
//		lval_del(lval_pop(v, 1));
//	} 
//	//获得最右推导 
//	return v;
//}
//
////3、tail函数，不用宏断言:删除传入左值a->cell中下标为0的元素，即第一个元素 
//lval* builtin_tail(lval* a){
//	//检错 
//	if(a->count != 1){
//		lval_del(a);
//		return lval_err("Function 'tail' passed too many arguments!");
//	}
//	if(a->cell[0]->type != LVAL_QEXPR){
//		lval_del(a);
//		return lval_err("Function 'tail' passed incorrect type!"); 
//	}
//	if(a->cell[0]->count == 0){
//		lval_del(a);
//		return lval_err("Function 'tail' passed {}!");
//	}
//	
//	lval* v = lval_take(a, 0);
//	
//	lval_del(lval_pop(v, 0));
//	return v;
//}
//
////4、list函数，将S-expression转换为Q-expression 
//lval* builtin_list(lval* a){
//	a->type = LVAL_QEXPR;
//	return a;
//}
//
////声明lval_eval函数:其功能是，若a->type==SEXPR，则返回S-expression表达式处理结果，否则直接返回a 
//lval* lval_eval(lval* v); 
//
////5、eval函数，将Q-expression转换为S-expression
//lval* builtin_eval(lval* a){
//	//用宏断言处理的错误检查，若cond，则lval_del(args)并且return lval_err(err) 
//	LASSERT(a, a->count!=1, "Function 'eval' passed too many arguments!");
//	LASSERT(a, a->cell[0]->type!=LVAL_QEXPR, "Function 'eval' passed incorrect type!");
//	
//	lval* x = lval_take(a, 0);
//	x->type = LVAL_SEXPR;
//	return lval_eval(x);
//} 
//
////lval_join，类似于builtin_op函数，返回两个Q-expression的合并结果
////合并即指:将y加入到x的cell中，同时释放y原有空间 
//lval* lval_join(lval* x, lval* y){
//	while(y->count > 0){
//		x = lval_add(x, lval_pop(y, 0));
//	}
//	
//	lval_del(y);
//	return x;
//}
//
//
////7、通过循环调用lval_join计算一个Q-expression表达式 
//lval* builtin_join(lval* a){
//	int i;
//	//检查推导中是否含有不属于Q-expression的产生式 
//	for(i = 0; i < a->count; i++){
//		LASSERT(a, a->cell[i]->type!=LVAL_QEXPR, "Function 'join' passed incorrect type");
//	}
//	
//	lval* x = lval_pop(a, 0);
//	while(a->count > 0){
//		x = lval_join(x, lval_pop(a, 0));
//	}
//	
//	lval_del(a);
//	return x;
//} 
//
////查找内建函数
//lval* builtin(lval* a, char* func){
//	if(strcmp("list", func) == 0){
//		return builtin_list(a);
//	} 
//	if(strcmp("head", func) == 0){
//		return builtin_head(a);
//	} 
//	if(strcmp("tail", func) == 0){
//		return builtin_tail(a);
//	} 
//	if(strcmp("join", func) == 0){
//		return builtin_join(a);
//	} 
//	if(strcmp("eval", func) == 0){
//		return builtin_eval(a);
//	} 
//	if(strstr("+-*/", func)){
//		return builtin_op(a, func);
//	}
//	//如果都没有匹配成功 
//	lval_del(a);
//	return lval_err("Unknown Function!");
//} 
//
////计算S-expression表达式的结果
//lval* lval_eval_sexpr(lval* v){
//	//计算相邻子节点之和 
//	int i;
//	for(i = 0; i < v->count; i++){
//		v->cell[i] = lval_eval(v->cell[i]);
//	}
//	for(i = 0; i < v->count; i++){
//		if(v->cell[i]->type == LVAL_ERR){
//			return lval_take(v, i);
//		}
//	}
//	
//	if(v->count == 0)	return v;
//	if(v->count == 1)	return lval_take(v, 0);
//	
//	lval* f = lval_pop(v, 0);
//	if(f->type != LVAL_SYM){
//		lval_del(f);
//		lval_del(v);
//		return lval_err("S-expression Does not start with symbol!");
//	}
//	
//	//在此处调用了内建函数，让f->sym(函数名)得以识别，无需在Symbol中直接定义函数 
//	lval* result = builtin(v, f->sym);
//	lval_del(f);
//	return result;
//}
//
//
//lval* lval_eval(lval* v){
//	if(v->type == LVAL_SEXPR){
//		return lval_eval_sexpr(v);
//	}
//	return v;
//}
//
//
//
////将语法树的数字部分转换为token返回 
//lval* lval_read_num(mpc_ast_t* t){
//	errno = 0;
//	//strtol:将字符串转换为长整型，第二个参数为指向不可转换的char*的位置(即在此处停止转换)，10为进制 
//	long x = strtol(t->contents, NULL, 10);
//	return errno != ERANGE ?
//		lval_num(x): lval_err("invalid number"); 
//}
//
//
////将生成的抽象语法树转化为s-expression表达式 
//lval* lval_read(mpc_ast_t* t){
//	//strstr:匹配两字符串 
//	if(strstr(t->tag, "number"))	return lval_read_num(t);
//	if(strstr(t->tag, "symbol"))	return lval_sym(t->contents);
//	
//	lval* x = NULL;
//	//strcmp:比较两字符串，返回字符串(大小)str1-str2，当为0二者相同 
//	if(strcmp(t->tag, ">") == 0 || strstr(t->tag, "sexpr")){
//		x = lval_sexpr();
//	}
//	if(strstr(t->tag, "qexpr")){
//		x = lval_qexpr();
//	}
//	
//	int i;
//	for(i = 0; i< t->children_num; i++){
//		if(strcmp(t->children[i]->contents, "(") == 0
//			|| strcmp(t->children[i]->contents, ")") == 0
//			|| strcmp(t->children[i]->contents, "{") == 0
//			|| strcmp(t->children[i]->contents, "}") == 0
//			|| strcmp(t->children[i]->tag, "regex") == 0)
//			{
//			continue;
//		}
//		x = lval_add(x, lval_read(t->children[i]));
//	}		
//	return x;
//}
//
//
// 
//int main(){
//	
//	//定义新的文法 
//	mpc_parser_t* Number = mpc_new("number");	
//	mpc_parser_t* Symbol = mpc_new("symbol");	
//	mpc_parser_t* Sexpr = mpc_new("sexpr");
//	mpc_parser_t* Qexpr = mpc_new("qexpr");	
//	mpc_parser_t* Expr = mpc_new("expr");	
//	mpc_parser_t* Lispy = mpc_new("lispy");	
//	mpca_lang(MPCA_LANG_DEFAULT,
//		//在symbol中定义内建函数，让语法树能够识别 
//		"															\
//			number: /-?[0-9]+/;										\
//			symbol: \"list\" | \"head\" | \"tail\" | \"join\" 		\
//					| \"eval\" | '+' | '-' | '*' | '/';				\
//			sexpr: '(' <expr>* ')';									\
//			qexpr: '{' <expr>* '}';									\
//			expr: <number> | <symbol> | <sexpr> | <qexpr>;			\
//			lispy: /^/ <expr>* /$/;									\
//		",
//		Number, Symbol, Sexpr, Qexpr, Expr, Lispy
//	);
//	
//	puts("Lispy Version 0.6\nPress Ctrl+c to Exit\n");
//	while(1){
//		char* input = readLine("lispy>");
//		add_history(input);
//		
//		mpc_result_t res;
//		if (mpc_parse("<stdin>", input, Lispy, &res)) {
//      		lval* x = lval_eval(lval_read(res.output));
//      		//mpc_ast_print(res.output);
//      		lval_println(x);
//      		lval_del(x);
//      		mpc_ast_delete(res.output);
//    	} else {    
//      		mpc_err_print(res.error);
//      		mpc_err_delete(res.error);
//   		}
//		
//		free(input);
//	}
//	
//	//释放树的空间 
//	mpc_cleanup(6, Number, Symbol, Sexpr, Qexpr, Expr, Lispy);
//	return 0; 
//}
//

