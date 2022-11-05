/*#include "mpc/mpc.h"

#ifdef _WIN32

static char buffer[2084];

char* readLine(char* prompt){
	fputs(prompt, stdout);
	fgets(buffer, 2084, stdin);
	char * cpy = malloc(strlen(buffer)+1);
	
	strcpy(cpy, buffer);
	cpy[strlen(buffer)-1] = '\0';
	return cpy;
} 

void add_history(char* unused){}


#else	
#include <readline/readline.h>
#include <readline/history.h>
#endif



int main(int argc, char** argv){
	
	mpc_parser_t* Number = mpc_new("number");
	
	mpc_parser_t* Operator = mpc_new("operator");
	
	mpc_parser_t* Expr = mpc_new("expr");
	
	mpc_parser_t* Lispy = mpc_new("lispy");
	
	mpca_lang(MPCA_LANG_DEFAULT,
		"number:/-?[0-9]+/; operator:'+'|'-'|'*'|'/'; expr:<number>|'('<operator><expr>+')'; lispy:/^/<operator><expr>+/$/;",
		Number, Operator, Expr, Lispy
	);
	
	puts("Lispy Version 0.2\nPress Ctrl+c to Exit\n");
	
	while(1){
		char* input = readLine("lispy>");
		add_history(input);
		
		mpc_result_t res;
		if(mpc_parse("<stdin>", input, Lispy, &res)){
			mpc_ast_print(res.output);
			mpc_ast_delete(res.output);
		}else{
			mpc_err_print(res.error);
			mpc_err_delete(res.error);
		}
		
		free(input);
	}
	
	mpc_cleanup(4, Number, Operator, Expr, Lispy);
	
	return 0;
}*/
