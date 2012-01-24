package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This class is used to represent assignable locations.
 */
public class ELValue extends Expression
{
	String id;  // the identifier for the location
	
	
	public ELValue(String id)
	{
		this.id = id;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		return rt.get(id);
	}
	
	
	/**
	 * Sets the location to the given value.
	 * 
	 * @param rt     the current runtime environment
	 * @param value  the value to store
	 */
	public void set(Runtime rt, Value value)
	{
		rt.set(id, value);
	}
	
	
	public String toString()
	{
		return id;
	}
}
