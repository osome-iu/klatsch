package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;


/**
 * This is a logical NOT expression.
 */
public class ENot extends Expression
{
	Expression exp;
	
	
	public ENot(Expression exp)
	{
		this.exp = exp;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value val = exp.evaluateToClass(rt, VBoolean.class);
		return (((VBoolean) val).val ? VBoolean.F : VBoolean.T);
	}
	
	
	public String toString()
	{
		return "!" + exp.toString();
	}
}
