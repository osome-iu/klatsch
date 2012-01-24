package edu.iu.cnets.klatsch.lang;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.expression.Expression;
import edu.iu.cnets.klatsch.misc.Utility;
import edu.iu.cnets.klatsch.stream.SProcedure;


/**
 * This value represents a closure, a user-defined procedure that captures any free
 * variables and can be invoked.
 */
public class VClosure extends Value implements Callable
{
	String[]    idList;
	Expression  bodyExp;
	Runtime     env;
	

	/**
	 * Initializes a new closure.
	 * 
	 * @param idList  the names of the formal parameters
	 * @param bodyExp  the body of the procedure
	 * @param env      the captured environment
	 */
	public VClosure(String[] idList, Expression bodyExp, Runtime env)
	{
		this.idList  = idList;
		this.bodyExp = bodyExp;
		this.env     = env;
	}
	
	
	/**
	 * Invokes the procedure with the given values as parameters.
	 * 
	 * @param args  the argument values
	 * @return the procedure result
	 * @throws EvaluationException if the procedure call fails
	 */
	public Value apply(Value ... args)
	throws EvaluationException
	{
		requireCount(args, idList.length, idList.length);
		
		Runtime callEnv = env.extend(idList, args);
		return bodyExp.evaluate(callEnv);
	}
	
	
	/**
	 * Returns a string representation of the closure.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return "<Closure>(" + Utility.listString(idList) + ")";
	}
	
	
	/**
	 * stream() : Turns this zero-argument closure into a (infinite) stream.
	 */
	public Value method_stream(Value ... args)
	throws EvaluationException
	{
		return new VStream(new SProcedure(this));
	}
}
