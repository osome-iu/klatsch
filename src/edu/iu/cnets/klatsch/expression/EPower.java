package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;


/**
 * Exponentiation expression.
 */
public class EPower extends Expression
{
	Expression expBase, expExp;
	
	
	public EPower(Expression expBase, Expression expExp)
	{
		this.expBase = expBase;
		this.expExp  = expExp;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valBase = expBase.evaluate(rt);
		Value valExp  = expExp .evaluate(rt);
		
		if (!(valBase instanceof VNumber) || !(valExp instanceof VNumber))
			throw new EvaluationException("type mismatch");
		
		return new VNumber(Math.pow(((VNumber) valBase).val, ((VNumber) valExp).val));
	}
	
	
	public String toString()
	{
		return expBase.toString() + " ** " + expExp.toString();
	}
}
