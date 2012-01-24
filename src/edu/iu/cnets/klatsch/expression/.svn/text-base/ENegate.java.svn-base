package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;


/**
 * This is a negation expression.
 */
public class ENegate extends Expression
{
	Expression exp;
	
	
	public ENegate(Expression exp)
	{
		this.exp = exp;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value val = exp.evaluate(rt);
		
		if (!(val instanceof VNumber))
			throw new EvaluationException("type mismatch");
		
		return new VNumber(-((VNumber) val).val);
	}
	
	
	public String toString()
	{
		return "-" + exp.toString();
	}
}
