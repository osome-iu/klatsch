package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNull;


/**
 * This class is used to represent block (BEGIN ... END) expressions.
 */
public class EBlock extends Expression
{
	Expression[] expList;  // the sequence of expressions
	
	
	public EBlock(Expression[] expList)
	{
		this.expList = expList;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value val = VNull.NULL;
		
		for (Expression e : expList)
			val = e.evaluate(rt);
		
		return val;
	}
	
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer("BEGIN ");
		for (Expression e : expList)
			buffer.append(e.toString() + " ");
		buffer.append("END");

		return buffer.toString();
	}
}
