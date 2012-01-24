package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.exception.ParserException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This class is used to represent variable assignments.
 */
public class EAssign extends Expression
{
	ELValue    expVar;  // the expression for the variable
	Expression expVal;  // the expression for the value
	
	
	public EAssign(Expression expVar, Expression expVal)
	throws ParserException
	{
		if (!(expVar instanceof ELValue))
			throw new ParserException("bad lvalue");
		
		this.expVar = (ELValue) expVar;
		this.expVal = expVal;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valVal = expVal.evaluate(rt);
		
		expVar.set(rt, valVal);
		return valVal;
	}
	
	
	public String toString()
	{
		return expVar.toString() + " = " + expVal.toString();
	}
}
