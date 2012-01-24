package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Callable;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.misc.Utility;


/**
 * This class is used to represent function calls.
 */
public class ECall extends Expression
{
	Expression   expFunc;  // the expression for the function
	Expression[] expArgs;  // the set of arguments
	
	
	public ECall(Expression expFunc, Expression[] expArgs)
	{
		this.expFunc = expFunc;
		this.expArgs = expArgs;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value   valFunc = expFunc.evaluate(rt);
		Value[] valArgs = new Value[expArgs.length];
		for (int i = 0; i < expArgs.length; ++i)
			valArgs[i] = expArgs[i].evaluate(rt);
		
		if (valFunc instanceof Callable)
	    return ((Callable) valFunc).apply(valArgs);
		else
			throw new EvaluationException("value cannot be called");
	}
	
	
	public String toString()
	{
		return expFunc.toString() + "(" + Utility.listString(expArgs) + ")";
	}
}
