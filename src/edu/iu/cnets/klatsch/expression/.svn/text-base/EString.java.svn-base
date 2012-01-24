package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VString;


/**
 * This expression is a string literal.
 */
public class EString extends Expression
{
	VString value;
	
	
	public EString(String value)
	{
		this.value = new VString(value);
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		return value;
	}
	
	
	public String toString()
	{
		return "\"" + value.toString() + "\"";
	}
}
