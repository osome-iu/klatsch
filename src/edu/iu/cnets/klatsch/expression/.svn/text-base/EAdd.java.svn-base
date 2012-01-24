package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VString;


/**
 * Addition expression.
 */
public class EAdd extends Expression
{
	Expression expA, expB;
	
	
	public EAdd(Expression expA, Expression expB)
	{
		this.expA = expA;
		this.expB = expB;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valA = expA.evaluate(rt);
		Value valB = expB.evaluate(rt);
		
		if (!(valA instanceof VNumber) || !(valB instanceof VNumber)) {
			if ((valA instanceof VString) || (valB instanceof VString))
				return new VString(valA.toString() + valB.toString());
			else
				throw new EvaluationException("type mismatch");
		}
		
		return new VNumber(((VNumber) valA).val + ((VNumber) valB).val);
	}
	
	
	public String toString()
	{
		return expA.toString() + " + " + expB.toString();
	}
}
