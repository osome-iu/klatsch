package edu.iu.cnets.klatsch.lang;


/**
 * The null value.
 */
public class VNull extends Value
{
	public static VNull NULL = new VNull();
	
	public VNull() {}
	
	public String toString() { return ""; }
	
	public String toJson() { return "null"; }
}
