package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This is the base class of every variety of expression.
 */
public abstract class Expression
{
	abstract public Value  evaluate(Runtime rt) throws EvaluationException;
	abstract public String toString();
	
	
	/**
	 * The method is used to require that the expression evaluate to a particular data type.
	 * 
	 * @param rt    the current runtime environment
	 * @param type  the class required 
	 * @return the expression value
	 * @throws EvaluationException if evaluation doesn't produce the right class
	 */
	public Value evaluateToClass(Runtime rt, Class<?> type)
	throws EvaluationException
	{
		Value val = evaluate(rt);
		if (type.isInstance(val))
			return val;
		else
			throw new EvaluationException("type mismatch");
	}
}
