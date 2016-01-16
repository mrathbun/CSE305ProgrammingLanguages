//Mitchell Rathbun
public class TinyPL {
		
	public static void main(String args[]) {   
           Lexer.lex(); //begin
	   new Program();  
	}
}

class Program {
    Decls d;
    Stmts s;
    public Program(){
    	Lexer.lex(); //Pass begin
		d = new Decls();//decls
		s = new Stmts();//stmts
	 }
}

class Decls {
	Idlist i1, i2, i3;
	public Decls(){
		if(Lexer.nextToken==Token.KEY_INT){
			Lexer.lex(); //Pass to idlist
			i1 = new Idlist();
			Lexer.lex(); //Skip ;
		}
		if(Lexer.nextToken==Token.KEY_REAL){
			Lexer.lex(); //Pass to idlist
			i2 = new Idlist();
			Lexer.lex(); //Skip ;			
		}
		if(Lexer.nextToken==Token.KEY_BOOL){
			Lexer.lex(); //Pass to idlist
			i3 = new Idlist();
			Lexer.lex(); //Skip ;
		}
	}
}

class Idlist {
	Id_Lit id; 
	Idlist idl;
	public Idlist(){
		id = new Id_Lit();
		Lexer.lex();
		if(Lexer.nextToken==Token.COMMA){
			Lexer.lex();
			idl = new Idlist();  
		}
	}
}

class Stmts {
	 Stmt s;
	 Stmts ss;
	 public Stmts(){
		 int hold = Lexer.nextToken;
		 if(hold==Token.ID||hold==Token.KEY_IF||hold==Token.KEY_WHILE||hold==Token.LEFT_BRACE){
			 s = new Stmt(); //Correct?
		 }
		 hold = Lexer.nextToken;
		 if(hold==Token.ID||hold==Token.KEY_IF||hold==Token.KEY_WHILE||hold==Token.LEFT_BRACE){
			 ss = new Stmts(); //Correct?
		 }		 
	 }
}

class Stmt {
	Assign a;
	Cond c;
	Loop l;
	Cmpd cmp;
	public Stmt(){
		switch(Lexer.nextToken){
		case Token.ID:  //assign
			a = new Assign();
			break;
		case Token.KEY_IF:  //cond
			c = new Cond();
			break;
		case Token.KEY_WHILE:  //loop
			l = new  Loop();
			break;
		case Token.LEFT_BRACE:  //cmpd
			cmp = new Cmpd();
			break;
		}
	}
} 

class Assign {
	 Var v;
	 Expr e;
	 public Assign(){
		 v = new Var();
		 Lexer.lex(); // =
		 e = new Expr();
		 Lexer.lex(); // ;
	 }
}

class Var{
	Id_Lit id;
	public Var(){
		id = new Id_Lit();
		Lexer.lex(); //Move passed id
	}
}

class Cond{
	 Expr e;
	 Stmt s1;
	 Stmt s2;
	 public Cond(){
		 Lexer.lex(); //if
		 e = new Expr();
		 s1 = new Stmt();
		 if(Lexer.nextToken==Token.KEY_ELSE){
			 Lexer.lex(); //else
			 s2 = new Stmt();
		 }
	 }
}

class Loop {
	Expr e;
	Stmt s;
	public Loop(){
		 Lexer.lex(); //while
		 e = new Expr();
		 s = new Stmt();
	}
}

class Cmpd  {
	Stmts s;
	public Cmpd(){
		Lexer.lex(); // Pass {
		s = new Stmts(); //stmts
		Lexer.lex(); //Skip }
	}	 
}


class Expr {  
	 Term t;
	 Expr e;
	 public Expr(){
		 t = new Term();
		 if(Lexer.nextToken==Token.ADD_OP||Lexer.nextToken==Token.SUB_OP||Lexer.nextToken==Token.OR_OP){
			 Lexer.lex();
			 e = new Expr();
		 }
	 }
}

class Term {
	Factor f;
	Term t;
	public Term(){
		f = new Factor();
		if(Lexer.nextToken==Token.MULT_OP||Lexer.nextToken==Token.DIV_OP||Lexer.nextToken==Token.AND_OP){
			Lexer.lex();
			t = new Term();
		 }
	}
}


class Factor {  
	 Int_Lit i;
	 Real_Lit r;
	 Bool_Lit b;
	 Id_Lit id;
	 Factor f;
	 Expr e1;
	 Expr e2;
	 public Factor(){
		 switch(Lexer.nextToken){
		 case Token.INT_LIT:
			 i = new Int_Lit();
			 Lexer.lex();
			 break;
		 case Token.REAL_LIT:
			 r = new Real_Lit();
			 Lexer.lex();
			 break;
		 case Token.ID:
			 id = new Id_Lit();
			 Lexer.lex();
			 break;
		 case Token.TRUE_LIT:
		 case Token.FALSE_LIT:
			 b = new Bool_Lit();
			 Lexer.lex();
			 break;
		 case Token.NEG_OP:
			 Lexer.lex(); // !
			 f = new Factor();
			 break;
		 case Token.LEFT_PAREN:
		     Lexer.lex(); // (
		     e1 = new Expr();
		     if(Lexer.nextToken==Token.RIGHT_PAREN){
		    	 Lexer.lex(); // )
		     }
		     else{
		     	 Lexer.lex(); // relop
		     	 e2 = new Expr();
		     	 Lexer.lex(); // )
		     }	 
			 break;
		 }
	 }
}

class Literal {
	 protected int i;
	 protected double r;
	 protected boolean b;
	 protected char id;
}

class Int_Lit extends Literal {
	public Int_Lit(){
		i = Lexer.intValue;
	}
}

class Real_Lit extends Literal {
	public Real_Lit(){
		r = Lexer.realValue;
	}
}

class Bool_Lit extends Literal { 
	public Bool_Lit(){
		if(Lexer.nextToken == Token.TRUE_LIT){
			b = true;
		}
		else{
			b = false;
		}
	}
}

class Id_Lit extends Literal {
	public Id_Lit(){
		id = Lexer.ident;
	}
}

