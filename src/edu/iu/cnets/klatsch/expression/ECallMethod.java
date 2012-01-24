package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VPane;
import edu.iu.cnets.klatsch.misc.Utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * This class is used to represent function calls.
 */
public class ECallMethod extends Expression
{
	Expression   expObj;   // the expression for the object
	String       method;   // the method to invoke
	Expression[] expArgs;  // the set of arguments
	
	
	public ECallMethod(Expression expObj, String method, Expression[] expArgs)
	{
		this.expObj  = expObj;
		this.method  = method;
		this.expArgs = expArgs;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		try {
		
			Value   valObj  = expObj.evaluate(rt);
			Value[] valArgs = new Value[expArgs.length];
			for (int i = 0; i < expArgs.length; ++i)
				valArgs[i] = expArgs[i].evaluate(rt);

			if (valObj instanceof VPane) {
				Method func = ((VPane) valObj).p.getClass().getMethod("prop_" + method, Value[].class);
				return (Value) func.invoke(((VPane) valObj).p, (Object) valArgs);
			} else {
				Method func   = valObj.getClass().getMethod("method_" + method, Value[].class);
				return (Value) func.invoke(valObj, (Object) valArgs);
			}

		} catch (NoSuchMethodException e) {
			throw new EvaluationException("unknown method " + method);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof EvaluationException)
				throw (EvaluationException) cause;
			else {
				cause.printStackTrace();
				throw new EvaluationException(cause.toString());
			}
		} catch (IllegalAccessException e) {
			System.err.println(e);
			throw new EvaluationException("method invocation failed");
		}
	}
	
	
	public String toString()
	{
		return expObj.toString() + "." + method + "(" + Utility.listString(expArgs) + ")";
	}
}
