package edu.iu.cnets.klatsch.lang;


/**
 * I think you can probably guess the two possible values of this class.
 */
public class VBoolean extends Value {
		
	/** the value actually being stored */
	public final boolean val;
	
	/** defined for convenience to save on memory churn */
	public static final VBoolean T = new VBoolean(true);
	public static final VBoolean F = new VBoolean(false);
		
		
	public VBoolean(boolean val)
	{
		this.val = val;
	}
		
		
	public String toString()
	{
		return Boolean.toString(val);
	}
	
	
	public String toJson()
	{
		return Boolean.toString(val);
	}
}
