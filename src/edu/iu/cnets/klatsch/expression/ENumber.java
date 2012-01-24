package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;


/**
 * This expression is a numeric literal.
 */
public class ENumber extends Expression
{
	VNumber value;
	
	
	public ENumber(double value)
	{
		this.value = new VNumber(value);
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		return value;
	}
	
	
	public String toString()
	{
		return value.toString();
	}
}
