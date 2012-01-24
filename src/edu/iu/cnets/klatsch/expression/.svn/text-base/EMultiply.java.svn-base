package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;


/**
 * Multiplication expression.
 */
public class EMultiply extends Expression
{
	Expression expA, expB;
	
	
	public EMultiply(Expression expA, Expression expB)
	{
		this.expA = expA;
		this.expB = expB;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valA = expA.evaluate(rt);
		Value valB = expB.evaluate(rt);
		
		if (!(valA instanceof VNumber) || !(valB instanceof VNumber))
			throw new EvaluationException("type mismatch");
		
		return new VNumber(((VNumber) valA).val * ((VNumber) valB).val);
	}
	
	
	public String toString()
	{
		return expA.toString() + " * " + expB.toString();
	}
}
