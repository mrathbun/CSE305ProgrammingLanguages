//Mitchell Rathbun

import java.util.HashMap;

public class TinyPL {
	private static HashMap<Character, String> ST;
	Program p;
	public static void main(String args[]) throws Redeclaration, TypeUnknown, TypeMismatch {
		   ST = new HashMap<Character, String>();
           Lexer.lex(); //begin
           try{
        	   new Program(ST);
           }
           catch(Redeclaration r){
        	   System.out.println(r);
           }
           catch(TypeUnknown t){
        	   System.out.println(t);
           }
           catch(TypeMismatch t){
        	   System.out.println(t);
           }
	}
}

@SuppressWarnings("serial")
class Redeclaration extends Exception {
    String message;
    public Redeclaration(String s) {
    	super(s);
    	message = s;
    }
}

@SuppressWarnings("serial")
class TypeUnknown extends Exception {
	String message;
	public TypeUnknown(String s) {
		super(s);
		message = s;
	}
}

@SuppressWarnings("serial")
class TypeMismatch extends Exception {
	String message;
	public TypeMismatch(String s) {
		super(s);
		message = s;
	}
}

class Program {
    Decls d;
    Stmts s;
    public Program(HashMap<Character, String> ST) throws Redeclaration, TypeUnknown, TypeMismatch {
    	Lexer.lex(); //Pass begin
		d = new Decls(ST);//decls
		s = new Stmts(ST);//stmts
	 }
}

class Decls {
	Idlist i1, i2, i3;
	public Decls(HashMap<Character, String> ST) throws Redeclaration {
		String type;
		if(Lexer.nextToken==Token.KEY_INT) {
			type = "int";
			Lexer.lex(); //Pass to idlist
			i1 = new Idlist(ST, type);
			Lexer.lex(); //Skip ;
		}
		if(Lexer.nextToken==Token.KEY_REAL) {
			type = "real";
			Lexer.lex(); //Pass to idlist
			i2 = new Idlist(ST, type);
			Lexer.lex(); //Skip ;			
		}
		if(Lexer.nextToken==Token.KEY_BOOL) {
			type = "bool";
			Lexer.lex(); //Pass to idlist
			i3 = new Idlist(ST, type);
			Lexer.lex(); //Skip ;
		}
	}
}

class Idlist {
	Id_Lit id; 
	Idlist idl;
	public Idlist(HashMap<Character, String> ST, String type) throws Redeclaration {
		id = new Id_Lit();
		if(ST.containsKey(id.id)) {
			throw new Redeclaration("" + id.id);
		}
		else {
			ST.put(id.id, type);
		}
		Lexer.lex();
		if(Lexer.nextToken==Token.COMMA) {
			Lexer.lex();
			idl = new Idlist(ST, type);  
		}
	}
}

class Stmts {
	 Stmt s;
	 Stmts ss;
	 public Stmts(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 int hold = Lexer.nextToken;
		 if(hold==Token.ID||hold==Token.KEY_IF||hold==Token.KEY_WHILE||hold==Token.LEFT_BRACE){
			 s = new Stmt(ST); //Correct?
		 }
		 hold = Lexer.nextToken;
		 if(hold==Token.ID||hold==Token.KEY_IF||hold==Token.KEY_WHILE||hold==Token.LEFT_BRACE){
			 ss = new Stmts(ST); //Correct?
		 }		 
	 }
}

class Stmt {
	Assign a;
	Cond c;
	Loop l;
	Cmpd cmp;
	public Stmt(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		switch(Lexer.nextToken){
		case Token.ID:  //assign
			a = new Assign(ST);
			break;
		case Token.KEY_IF:  //cond
			c = new Cond(ST);
			break;
		case Token.KEY_WHILE:  //loop
			l = new  Loop(ST);
			break;
		case Token.LEFT_BRACE:  //cmpd
			cmp = new Cmpd(ST);
			break;
		}
	}
} 

class Assign { 
	 Var v;
	 Expr e;
	 public Assign(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 v = new Var(ST);
		 Lexer.lex(); // =
		 e = new Expr(ST);
		 if(v.type!=e.type){
			 throw new TypeMismatch("Can't assign " + e.type + " to " + v.type); 
		 }
		 Lexer.lex(); // ;
	 }
}

class Var{
	Id_Lit id;
	String type;
	public Var(HashMap<Character, String> ST) throws TypeUnknown{
		id = new Id_Lit();
		if(!ST.containsKey(id.id)) {
			throw new TypeUnknown(id.id + " on LHS of assignment");
		}
		type = ST.get(id.id);
		Lexer.lex(); //Move passed id
	}
}

class Cond{ 
	 Expr e;
	 Stmt s1;
	 Stmt s2;
	 public Cond(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 Lexer.lex(); //if
		 e = new Expr(ST);
		 if(e.type!="bool"){
			 throw new TypeMismatch("if condition must have bool type");
		 }
	     s1 = new Stmt(ST);
		 if(Lexer.nextToken==Token.KEY_ELSE){
			 Lexer.lex(); //else
			 s2 = new Stmt(ST);
		 }
	 }
}

class Loop { 
	Expr e;
	Stmt s;
	public Loop(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 Lexer.lex(); //while
		 e = new Expr(ST);
		 if(e.type!="bool"){
			 throw new TypeMismatch("while condition must have bool type");
		 }
		 s = new Stmt(ST);
	}
}

class Cmpd {
	Stmts s;
	public Cmpd(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		Lexer.lex(); // Pass {
		s = new Stmts(ST); //stmts
		Lexer.lex(); //Skip }
	}	 
}


class Expr {  
	 Term t;
	 Expr e;
	 char op;
	 String type;
	 public Expr(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 t = new Term(ST);
		 type = t.type;
		 if(Lexer.nextToken==Token.ADD_OP||Lexer.nextToken==Token.SUB_OP){
			 if(t.type=="bool") {
				 throw new TypeMismatch("bool type in arithmetic expression");
			 }
			 if(Lexer.nextToken==Token.ADD_OP){
				 op = '+';
			 }
			 else{
				 op = '-';
			 }
			 Lexer.lex();
			 e = new Expr(ST);
			 if(t.type != e.type){
				 throw new TypeMismatch(t.type + " " + op + " " + e.type);
			 }
		 }
		 else if(Lexer.nextToken==Token.OR_OP){
			 if(t.type!="bool"){
				 throw new TypeMismatch(t.type + " type in a boolean expression");
			 }
			 Lexer.lex();
			 e = new Expr(ST);
			 if(t.type != e.type){
				 throw new TypeMismatch(t.type + " || " + e.type);
			 }
		 }
	 }
}

class Term { 
	Factor f;
	Term t;
	char op;
	String type;
	public Term(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		f = new Factor(ST);
		type = f.type;
		if(Lexer.nextToken==Token.MULT_OP||Lexer.nextToken==Token.DIV_OP){
			if(f.type=="bool") {
				 throw new TypeMismatch("bool type in arithmetic expression");
			}
			if(Lexer.nextToken==Token.MULT_OP){
			     op = '*';
			}
			else{
				op = '/';
			}
			Lexer.lex();
			t = new Term(ST);
			if(f.type != t.type){
				throw new TypeMismatch(f.type + " " + op + " " + t.type);
			}
		}
		else if(Lexer.nextToken==Token.AND_OP) {
			if(f.type!="bool"){
				throw new TypeMismatch(f.type + " in a boolean expression");
			}
			Lexer.lex();
			t = new Term(ST);
			if(f.type != t.type){
				throw new TypeMismatch(f.type + " && " + t.type);
			}
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
	 String type;
	 public Factor(HashMap<Character, String> ST) throws TypeUnknown, TypeMismatch{
		 switch(Lexer.nextToken){
		 case Token.INT_LIT:
			 i = new Int_Lit();
			 type = "int";
			 Lexer.lex();
			 break;
		 case Token.REAL_LIT:
			 r = new Real_Lit();
			 type = "real";
			 Lexer.lex();
			 break;
		 case Token.ID:
			 id = new Id_Lit();
			 if(!ST.containsKey(id.id)){
				 throw new TypeUnknown(id.id + " in an expression");
			 }
			 type = ST.get(id.id);
			 Lexer.lex();
			 break;
		 case Token.TRUE_LIT:
		 case Token.FALSE_LIT:
			 b = new Bool_Lit();
			 type = "bool";
			 Lexer.lex();
			 break;
		 case Token.NEG_OP:
			 Lexer.lex(); // !
			 type = "bool";
			 f = new Factor(ST);
			 if(f.type!="bool"){
				 throw new TypeMismatch("Can't negate a non boolean type");
			 }
			 break;
		 case Token.LEFT_PAREN: //check
		     Lexer.lex(); // (
		     e1 = new Expr(ST);
		     if(Lexer.nextToken==Token.RIGHT_PAREN){
		    	 Lexer.lex(); // )
		    	 type = e1.type;
		     }
		     else{
		    	 if(e1.type=="bool"){
		    		 throw new TypeMismatch("Bool expression with relop");
		    	 }
		     	 Lexer.lex(); // relop
		     	 e2 = new Expr(ST);
		     	 if(e1.type!=e2.type){
		     		 throw new TypeMismatch(e1.type + " relop " + e2.type);
		     	 }
		     	 type = "bool";
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
