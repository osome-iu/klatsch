package edu.iu.cnets.klatsch.lang;

import edu.iu.cnets.klatsch.exception.EvaluationException;


/**
 * Both numeric and floating-point values are subsumed into this class.
 */
public class VNumber extends Value
{
	/** the value actually being stored */
	public final double val;
	
	
	/**
	 * Initialize a new numeric value.
	 * 
	 * @param val  the number
	 */
	public VNumber(double val)
	{
		this.val = val;
	}
	
	
	/**
	 * Initialize a new numeric value.
	 * 
	 * @param val  the number
	 */
	public VNumber(Double val)
	{
		this.val = val.doubleValue();
	}
	
	
	/**
	 * Initialize a new numeric value.
	 * 
	 * @param val  the number
	 */
	public VNumber(int val)
	{
		this.val = val;
	}
	
	
	/**
	 * Initialize a new numeric value.
	 * 
	 * @param val  the number
	 */
	public VNumber(Integer val)
	{
		this.val = val.doubleValue();
	}
	
	
	/**
	 * Require this number be in a particular range to avoid causing an error.  We return ourselves to
	 * permit chaining calls.
	 * 
	 * @param min  the minimum permissible value
	 * @param max  the maximum permissible value
	 * @return this
	 * @throws EvaluationException if the value is out of range
	 */
	public VNumber requireRange(double min, double max)
	throws EvaluationException
	{
		if ((val < min) || (val > max))
			throw new EvaluationException("argument out of range");
		else
			return this;
	}
	
	
	/**
	 * Convert this number to a string representation.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		if (val == ((int) val))
			return Integer.toString((int) val);
		else
			return Double.toString(val);
	}
	
	
	/**
	 * Create a JSON represention of this number.
	 */
	public String toJson()
	{
		     if (Double.isInfinite(val))  return (val > 0) ? "\"+Inf\"" : "\"-Inf\"";
		else if (Double.isNaN     (val))  return "\"NaN\"";
		else                              return toString();
	}
	
	
	/**
	 * n.str() : Convert the number to a string.
	 */
	public Value method_str(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		return new VString(toString());
	}
}
