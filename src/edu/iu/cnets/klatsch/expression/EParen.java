package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This is a parenthetical expression.
 */
public class EParen extends Expression
{
	Expression exp;
	
	
	public EParen(Expression exp)
	{
		this.exp = exp;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		return exp.evaluate(rt);
	}
	
	
	public String toString()
	{
		return "(" + exp.toString() + ")";
	}
}
