package edu.iu.cnets.klatsch.parser;

import java.util.LinkedList;
import java.util.List;

import edu.iu.cnets.klatsch.exception.ParserException;
import edu.iu.cnets.klatsch.exception.TokenException;
import edu.iu.cnets.klatsch.expression.*;


/**
 * This class contains the recursive-descent parser for the Klatsch language.
 */
public class Parser
{
	TokenStream stream;  // the stream of tokens we're parsing
	
	
	/**
	 * Initializes a new parser with the given stream of tokens.
	 * 
	 * @param stream  the stream of tokens
	 */
	public Parser(TokenStream stream)
	{
		this.stream = stream;
	}
	
	
	/**
	 * Attempts to read a program from the token stream.  Returns the program (i.e. sequence of
	 * {@link Expression}s on success and null if nothing could be read.
	 */
	public Expression[] read()
	throws ParserException
	{
		return readProgram();
	}

	
	/**
	 * [block] ::= BEGIN [program] END
	 */
	private EBlock readBlock()
	throws ParserException
	{
		try {
			stream.requireKeyword("BEGIN");
			Expression[] expList = readProgram();
			stream.requireKeyword("END");
			return new EBlock(expList);
		} catch (TokenException e) {
			throw new ParserException(e, "block expression");
		}
	}
	
	
	/**
	 * [dictionary] ::= { [bind-list] }
	 */
	private EDictionary readDictionary()
	throws ParserException
	{
		try {
			stream.require(Token.Type.LBRACE);
			Pair[] pairList = readPairList();
			stream.require(Token.Type.RBRACE);
			return new EDictionary(pairList);
		} catch (TokenException e) {
			throw new ParserException(e, "dictionary");
		}
	}
	
	
	/**
	 * [do] ::= DO [exp] WHILE [exp]
	 */
	private EDo readDo()
	throws ParserException
	{
		try {
			stream.requireKeyword("DO");
			Expression expBody = readExpression();
			stream.requireKeyword("WHILE");
			return new EDo(readExpression(), expBody);
		} catch (TokenException e) {
			throw new ParserException(e, "DO/WHILE loop");
		}
	}

	
	/**
	 * [expression] ::= [exp0] | [if] | [begin] | [proc] | [while] | [do] | [for]
	 */
	private Expression readExpression()
	throws ParserException
	{
		try {
			Token token = stream.peek();
			     if (token.isKeyword("BEGIN"))  return readBlock();
			else if (token.isKeyword("DO"))     return readDo();
			else if (token.isKeyword("FOR"))    return readFor(); 
			else if (token.isKeyword("IF"))     return readIf();
			else if (token.isKeyword("PROC"))   return readProc();
			else if (token.isKeyword("WHILE"))  return readWhile();
			else                                return readExp0();
		} catch (TokenException e) {
			throw new ParserException("expression");
		}
	}
	
	
	/**
	 * [exp-list] ::= @ | [expression] , [exp-list]
	 */
	private Expression[] readExpressionList()
	throws ParserException
	{
		List<Expression> list = new LinkedList<Expression>();
		
		try {
			while (!stream.atTerminator()) {
				list.add(readExpression());
				if (!stream.peek().isType(Token.Type.COMMA))
					break;
				else
					stream.require(Token.Type.COMMA);
			}
			return list.toArray(new Expression[list.size()]);

		} catch (TokenException e) {
			throw new ParserException(e, "expression list");
		}
	}

	
	/**
	 * [exp0] ::= [exp1] | [exp1] = [expression]
	 */
	private Expression readExp0()
	throws ParserException
	{
		Expression exp = readExp1();
		
		try {
			if (stream.peek().isType(Token.Type.ASSIGN)) {
				stream.get();
				return new EAssign(exp, readExpression());
			} else
				return exp; 
		} catch (TokenException e) {
			throw new ParserException(e, "assignment");
		}
	}

	
	/**
	 * [exp1] ::= [exp2] | [exp2] && [exp1]
	 */
	private Expression readExp1()
	throws ParserException
	{
		Expression exp = readExp2();

		try {
			Token token = stream.peek();
			do {
				if (token.isType(Token.Type.OR)) {
					stream.get(); 
					exp = new EOr(exp, readExp2());
				}
				token = stream.peek();
		  } while (token.isType(Token.Type.OR));
		} catch (TokenException e) {
			throw new ParserException("|| expression");
		}

		return exp;
	}

		
	/**
	 * [exp2] ::= [exp3] | [exp3] && [exp2]
	 */
	private Expression readExp2()
	throws ParserException
	{
		Expression exp = readExp3();

		try {
			Token token = stream.peek();
			do {
				if (token.isType(Token.Type.AND)) {
					stream.get(); 
					exp = new EAnd(exp, readExp3());
				}
				token = stream.peek();
		  } while (token.isType(Token.Type.AND));
		} catch (TokenException e) {
			throw new ParserException("&& expression");
		}

		return exp;
	}

		
	/**
	 * [exp3] ::= [exp4] | ! [exp3]
	 */
	private Expression readExp3()
	throws ParserException
	{
		try {
			Token token = stream.peek();
			if (token.isType(Token.Type.NOT)) {
				stream.require(Token.Type.NOT);
				return new ENot(readExp3());
			} else
				return readExp4();
		} catch (TokenException e) {
			throw new ParserException(e, "! expression");
		}
	}

	
	/**
	 * [exp4] ::= [exp5] | [exp5] == [exp4] | [exp5] != [exp4]
	 */
	private Expression readExp4()
	throws ParserException
	{
		Expression exp = readExp5();

		try {
			Token token = stream.peek();
			do {
				     if (token.isType(Token.Type.EQ))  { stream.get();  exp = new ERelational(ERelational.Type.EQ,  exp, readExp5()); }
				else if (token.isType(Token.Type.NEQ)) { stream.get();  exp = new ERelational(ERelational.Type.NEQ, exp, readExp5()); }
				token = stream.peek();
		  } while (token.isType(Token.Type.EQ) || token.isType(Token.Type.NEQ));
		} catch (TokenException e) {
			throw new ParserException("==,!= expression");
		}

		return exp;
	}

	
	/**
	 * [exp5] ::= [exp6] | [exp6] < [exp5] | [exp6] <= [exp5] | [exp6] > [exp5] | [exp6] >= [exp5]
	 */
	private Expression readExp5()
	throws ParserException
	{
		Expression exp = readExp6();

		try {
			Token token = stream.peek();
			do {
				     if (token.isType(Token.Type.GEQ))  { stream.get();  exp = new ERelational(ERelational.Type.GEQ, exp, readExp6()); }
				else if (token.isType(Token.Type.GT))   { stream.get();  exp = new ERelational(ERelational.Type.GT,  exp, readExp6()); }
				else if (token.isType(Token.Type.LEQ))  { stream.get();  exp = new ERelational(ERelational.Type.LEQ, exp, readExp6()); }
				else if (token.isType(Token.Type.LT))   { stream.get();  exp = new ERelational(ERelational.Type.LT,  exp, readExp6()); }
				token = stream.peek();
		  } while (token.isType(Token.Type.GEQ) || token.isType(Token.Type.GT) ||
		  				 token.isType(Token.Type.LEQ) || token.isType(Token.Type.LT));
		} catch (TokenException e) {
			throw new ParserException("<,<=,>,>= expression");
		}

		return exp;
	}
	
	
	/**
	 * [exp6] ::= [exp7] | [exp7] + [exp6] | [exp7] - [exp6]
	 */
	private Expression readExp6()
	throws ParserException
	{
		Expression exp = readExp7();

		try {
			Token token = stream.peek();
			do {
				     if (token.isType(Token.Type.PLUS))  { stream.get();  exp = new EAdd     (exp, readExp7()); }
				else if (token.isType(Token.Type.MINUS)) { stream.get();  exp = new ESubtract(exp, readExp7()); }
				token = stream.peek();
			} while (token.isType(Token.Type.PLUS) || token.isType(Token.Type.MINUS));
		} catch (TokenException e) {
			throw new ParserException("+,- expression");
		}
		
		return exp;
	}
	
	
	/**
	 * [exp7] ::= [exp8] | [exp8] * [exp7] | [exp8] / [exp7] | [exp8] % [exp7]
	 */
	private Expression readExp7()
	throws ParserException
	{
		Expression exp = readExp8();
		
		try {
			Token token = stream.peek();
			do {
				     if (token.isType(Token.Type.TIMES))  { stream.get();  exp = new EMultiply(exp, readExp8()); }
				else if (token.isType(Token.Type.DIVIDE)) { stream.get();  exp = new EDivide  (exp, readExp8()); }
				else if (token.isType(Token.Type.MOD))    { stream.get();  exp = new EModulus (exp, readExp8()); }
				token = stream.peek();
			} while (token.isType(Token.Type.TIMES) || token.isType(Token.Type.DIVIDE) || token.isType(Token.Type.MOD));
		} catch (TokenException e) {
			throw new ParserException("*,/,% expression");
		}
		
		return exp;
	}
	
	
	/**
	 * [exp8] ::= [exp9] | + [exp8] | - [exp8]
	 */
	private Expression readExp8()
	throws ParserException
	{
		try {
			Token token = stream.peek();
			if (token.isType(Token.Type.PLUS))  { stream.require(Token.Type.PLUS);   return readExp8(); }
			if (token.isType(Token.Type.MINUS)) { stream.require(Token.Type.MINUS);  return new ENegate(readExp8()); }
			return readExp9();
		} catch (TokenException e) {
			throw new ParserException(e, "unary +/-");
		}
	}
	
	
	/**
	 * [exp9] ::= [exp10] | [exp10] ** [exp9]
	 */
	private Expression readExp9()
	throws ParserException
	{
		try {
			
			Expression exp = readExp10();
			
			if (stream.peek().isType(Token.Type.POWER)) {
				stream.require(Token.Type.POWER);
				return new EPower(exp, readExp9());
			}
			
			return exp;
			
		} catch (TokenException e) {
			throw new ParserException(e, "exponentiation");
		}
	}
	
	
	/**
	 * [exp10] ::= [exp11] | [exp11] ( [exp-list] ) | [exp11] . [ID] ( [exp-list] )
	 */
	private Expression readExp10()
	throws ParserException
	{
		try {
		
			Expression exp = readExp11();
		
			// function calls
			if (stream.peek().isType(Token.Type.LPAREN)) {
				stream.require(Token.Type.LPAREN);
				Expression[] args = readExpressionList();
				stream.require(Token.Type.RPAREN);
				exp = new ECall(exp, args);
			}
			
		  // method calls
			if (stream.peek().isType(Token.Type.DOT)) {
				do {
					stream.require(Token.Type.DOT);
					String method = stream.require(Token.Type.ID).stringValue();
					stream.require(Token.Type.LPAREN);
					Expression[] args = readExpressionList();
					stream.require(Token.Type.RPAREN);
					exp = new ECallMethod(exp, method, args);
				} while (stream.peek().isType(Token.Type.DOT));
			}
			
			return exp;
			
		} catch (TokenException e) {
			throw new ParserException("function call");
		}
	}
	
	
	/**
	 * [exp11] ::= [literal] | [lvalue] | ( [expression] )
	 */
	private Expression readExp11()
	throws ParserException
	{
		try {
			
			Token token = stream.peek();
			
			// if this is a parenthetical expression
			if (token.isType(Token.Type.LPAREN)) {
				stream.require(Token.Type.LPAREN);
				Expression exp = readExpression();
				stream.require(Token.Type.RPAREN);
				return new EParen(exp);
			
			// if this is an lvalue
			} else if (token.isType(Token.Type.ID)) {
				return readLValue();
				
			// otherwise, it must be a literal
			} else
				return readLiteral();
			
		} catch (TokenException e) {
			throw new ParserException(e, "expression");
		}
	}


	/**
	 * [for] ::= FOR [id] IN [exp] [exp]
	 */
	private EFor readFor()
	throws ParserException
	{
		try {
			stream.requireKeyword("FOR");
			String id = stream.require(Token.Type.ID).stringValue();
			stream.requireKeyword("IN");
			Expression expStream = readExpression();
			return new EFor(id, expStream, readExpression());
		} catch (TokenException e) {
			throw new ParserException(e, "FOR/IN loop");
		}
	}
	
	
	/**
	 * [id-list] ::= @ | [id] , [id-list]
	 */
	private String[] readIdList()
	throws ParserException
	{
		List<String> list = new LinkedList<String>();
		
		try {
			while (!stream.atTerminator()) {
				list.add(stream.require(Token.Type.ID).stringValue());
				if (!stream.peek().isType(Token.Type.COMMA))
					break;
				else
					stream.require(Token.Type.COMMA);
			}
			return list.toArray(new String[list.size()]);

		} catch (TokenException e) {
			throw new ParserException(e, "parameter list");
		}
	}

	
	/**
	 * [if] ::= IF [exp] THEN [exp] | IF [exp] THEN [exp] ELSE [exp]
	 */
	private EIf readIf()
	throws ParserException
	{
		try {

			stream.requireKeyword("IF");
			Expression expTest = readExpression();
			stream.requireKeyword("THEN");
			Expression expThen = readExpression();
			
			if (stream.peek().isKeyword("ELSE")) {
				stream.requireKeyword("ELSE");
				return new EIf(expTest, expThen, readExpression());
			} else
				return new EIf(expTest, expThen);

		} catch (TokenException e) {
			throw new ParserException("IF expression");
		}
	}
	
	
	/**
	 * [list] ::= [ [exp-list] ]
	 */
	private EList readList()
	throws ParserException
	{
		try {
			stream.require(Token.Type.LBRACKET);
			EList ls = new EList(readExpressionList());
			stream.require(Token.Type.RBRACKET);
			return ls;
		} catch (TokenException e) {
			throw new ParserException(e, "list");
		}
	}
	
	
	/**
	 * [literal] ::= [STRING] | [NUMBER] | [set] | [list]
	 */
	private Expression readLiteral()
	throws ParserException
	{
		try {
			Token token = stream.peek();
			     if (token.isType(Token.Type.STRING))    return new EString(stream.get().stringValue());
			else if (token.isType(Token.Type.NUMBER))    return new ENumber(stream.get().numberValue());
			else if (token.isType(Token.Type.LBRACE))    return readDictionary();
			else if (token.isType(Token.Type.LBRACKET))  return readList(); 
			else
				throw new ParserException("constant");
		} catch (TokenException e) {
			throw new ParserException(e, "constant");
		}
	}

	
	/**
	 * [lvalue] ::= [ID]
	 */
	private Expression readLValue()
	throws ParserException
	{
		try {
			return new ELValue(stream.require(Token.Type.ID).stringValue());
		} catch (TokenException e) {
			throw new ParserException(e, "variable reference");
		}
	}

	
	/**
	 * [pair] ::= [exp] : [exp]
	 */
	private Pair readPair()
	throws ParserException
	{
		try {
			Expression exp = readExpression();
			stream.require(Token.Type.COLON);
			return new Pair(exp, readExpression());
		} catch (TokenException e) {
			throw new ParserException(e, "binding");
		}
	}
	
	
	/**
	 * [pair-list] ::= @ | [pair] , [pair-list]
	 */
	private Pair[] readPairList()
	throws ParserException
	{
		List<Pair> list = new LinkedList<Pair>();
		
		try {
			while (!stream.atTerminator()) {
				list.add(readPair());
				if (!stream.peek().isType(Token.Type.COMMA))
					break;
				else
					stream.require(Token.Type.COMMA);
			}
			return list.toArray(new Pair[list.size()]);

		} catch (TokenException e) {
			throw new ParserException(e, "binding list");
		}
	}
	
	
	/**
	 * [proc] ::= PROC ( [id-list] ) [expression]
	 */
	private EProcedure readProc()
	throws ParserException
	{
		try {
			stream.requireKeyword("PROC");
			stream.require(Token.Type.LPAREN);
			String[] idList = readIdList();
			stream.require(Token.Type.RPAREN);
			return new EProcedure(idList, readExpression());
		} catch (TokenException e) {
			throw new ParserException(e, "procedure definition");
		}
	}

	
	/**
	 * [program] ::= @ | [expression] ; [program]
	 */
	private Expression[] readProgram()
	throws ParserException
	{
		List<Expression> list = new LinkedList<Expression>();
		
		try {
			while (!stream.eof() && !stream.peek().isKeyword("END")) {
				Expression exp = readExpression();
				stream.require(Token.Type.SEMICOLON);
				list.add(exp);
			}
			return list.toArray(new Expression[list.size()]);

		} catch (TokenException e) {
			throw new ParserException(e, "program");
		}
	}
	
	
	/**
	 * [while] ::= WHILE [exp] [exp]
	 */
	private EWhile readWhile()
	throws ParserException
	{
		try {
			stream.requireKeyword("WHILE");
			Expression expTest = readExpression();
			return new EWhile(expTest, readExpression());
		} catch (TokenException e) {
			throw new ParserException(e, "WHILE loop");
		}
	}
}
