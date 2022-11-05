//#include "mpc.h"
//#ifdef _WIN32
//
//// * 1100000000000 10000000000：可能存在一些问题
//
// 
//static char* buffer[2084];
//
//char* readLine(char* prompt) {
//	fputs(prompt, stdout);
//	fgets(buffer, 2084, stdin);
//	
//	//强转一下，免得警告 
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
////构造 
//enum{ LVAL_ERR, LVAL_NUM, LVAL_SYM, LVAL_SEXPR };
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
//
//void lval_del(lval* v){
//	
//	switch(v->type){
//		case LVAL_NUM: break;
//		//释放字符指针 
//		case LVAL_ERR: free(v->err); break;
//		case LVAL_SYM: free(v->sym); break;
//		
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
//		case LVAL_NUM: printf("%li", v->num); break;
//		case LVAL_ERR: printf("Error: %s", v->err); break;
//		case LVAL_SYM: printf("%s", v->sym); break;
//		case LVAL_SEXPR: lval_expr_print(v, '(', ')'); break;
//	}
//}
//
//void lval_expr_print(lval* v, char open, char close){
//	putchar(open);
//	int i;
//	for(i = 0; i < v->count; i++){
//		lval_print(v->cell[i]);
//		if(i == v->count-1){
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
//
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
//
//
////计算
//lval* lval_eval(lval* v); 
//lval* lval_eval_sexpr(lval* v){
//	//计算子节点
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
//	lval* result = builtin_op(v, f->sym);
//	lval_del(f);
//	return result;
//}
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
////将生成的抽象语法树转化为s-expression表达式 
//lval* lval_read(mpc_ast_t* t){
//	//strstr:匹配两字符串 
//	if(strstr(t->tag, "number"))	return lval_read_num(t);
//	if(strstr(t->tag, "symbol"))	return lval_sym(t->contents);
//	
//	lval* x = NULL;
//	//strcmp:比较两字符串，返回字符串(大小)str1-str2，当为0二者相同 
//	if(strcmp(t->tag, ">") == 0 || strstr(t->tag, "sexpr"))	x = lval_sexpr();
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
//	mpc_parser_t* Expr = mpc_new("expr");	
//	mpc_parser_t* Lispy = mpc_new("lispy");	
//	mpca_lang(MPCA_LANG_DEFAULT,
//		"											\
//			number: /-?[0-9]+/;						\
//			symbol: '+' | '-' | '*' | '/';			\
//			sexpr: '(' <expr>* ')';					\
//			expr: <number> | <symbol> | <sexpr>;	\
//			lispy: /^/ <expr>* /$/;					\
//		",
//		Number, Symbol, Sexpr, Expr, Lispy
//	);
//	
//	puts("Lispy Version 0.5\nPress Ctrl+c to Exit\n");
//	while(1){
//		char* input = readLine("lispy>");
//		add_history(input);
//		
//		mpc_result_t res;
//		if (mpc_parse("<stdin>", input, Lispy, &res)) {
//      		lval* x = lval_eval(lval_read(res.output));
//      		mpc_ast_print(res.output);
//     		lval_println(x);
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
//	mpc_cleanup(5, Number, Symbol, Sexpr, Expr, Lispy);
//	return 0; 
//}
//

