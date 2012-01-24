package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VList;
import edu.iu.cnets.klatsch.misc.Utility;


/**
 * This expression is a list literal [e0, ...].
 */
public class EList extends Expression
{
	Expression[] expList;
		
		
	public EList(Expression[] expList)
	{
		this.expList = expList;
	}
		
		
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value[] valList = new Value[expList.length];

		for (int i = 0; i < expList.length; ++i)
			valList[i] = expList[i].evaluate(rt);
		
		return new VList(valList);
	}
		
		
	public String toString()
	{
		return "[" + Utility.listString(expList) + "]";
	}
}
