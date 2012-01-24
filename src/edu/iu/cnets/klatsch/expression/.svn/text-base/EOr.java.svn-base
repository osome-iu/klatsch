package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;


/**
 * Logical OR expression.
 */
public class EOr extends Expression
{
	Expression expA, expB;
	
	
	public EOr(Expression expA, Expression expB)
	{
		this.expA = expA;
		this.expB = expB;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valA = expA.evaluateToClass(rt, VBoolean.class);
		if (((VBoolean) valA).val)
			return VBoolean.T;
		
		Value valB = expB.evaluateToClass(rt, VBoolean.class);
		if (((VBoolean) valB).val)
			return VBoolean.T;
		
		return VBoolean.F;
	}
	
	
	public String toString()
	{
		return expA.toString() + " && " + expB.toString();
	}
}
