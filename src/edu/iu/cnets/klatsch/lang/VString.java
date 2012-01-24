package edu.iu.cnets.klatsch.lang;

import java.util.regex.PatternSyntaxException;

import edu.iu.cnets.klatsch.exception.EvaluationException;


/**
 * String values.
 */
public class VString extends Value
{
	/** the value actually being stored */
	public final String val;
	
	
	/**
	 * Initialize a new string value.
	 *
	 * @param val  the string
	 */
	public VString(String val)
	{
		this.val = val;
	}
	
	
	/**
	 * This override is necessary to use the class as a key in a {@link java.util.HashMap} object.
	 */
	public boolean equals(Object other)
	{
		return (other instanceof VString) && val.equals(((VString) other).val);
	}
	

	/**
	 * This override is necessary to use the class as a key in a {@link java.util.HashMap} object.
	 */
	public int hashCode()
	{
		return val.hashCode();
	}

	
	/**
	 * Convert the string to a string, which isn't difficult. Double quotes
	 * are escaped so that JSON encoding won't break.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return val.replace("\"", "\\\"");
	}

	

	/**
	 * cap() : Capitalizes the first letter of the string.
	 */
	public Value method_cap(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (val.length() > 0)
			return new VString(val.substring(0, 1).toUpperCase() + val.substring(1));
		else
			return this;
	}

	
	/**
	 * contains(s) : Tests whether the string contains s.
	 */
	public Value method_contains(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		String other = ((VString) args[0].requireType(VString.class)).val;
		
		return new VBoolean(val.indexOf(other) != -1);
	}

	
	/**
	 * len() : Returns the length of the string.
	 */
	public Value method_len(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(val.length());
	}
	
	
	/**
	 * list() : Converts the string into a list of single-character substrings.
	 */
	public Value method_list(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		VString[] ls = new VString[val.length()];
		for (int i = 0; i < val.length(); ++i)
			ls[i] = new VString(val.substring(i, i+1));
		
		return new VList(ls);
	}
	
	
	/**
	 * lower() : Returns the lowercase version of the string.
	 */
	public Value method_lower(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VString(val.toLowerCase());
	}

	
	/**
	 * s.matches(r) : Tests whether the string matches r.
	 */
	public Value method_matches(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		String regex = ((VString) args[0].requireType(VString.class)).val;
		
		try {
			return new VBoolean(val.matches(regex));
		} catch (PatternSyntaxException e) {
			throw new EvaluationException("illegal regex");
		}
	}
	
	
	/**
	 * num() : Returns a numeric version of the string.
	 */
	public Value method_num(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		try {
			return new VNumber(Double.parseDouble(val));
		} catch (NumberFormatException e) {
			throw new EvaluationException("illegal value");
		}
	}
	
	
	/**
	 * split(r, {n}) : Returns the string as split by the regex r (limit of n pieces).
	 */
	public Value method_split(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 2);
		String regex = ((VString) args[0].requireType(VString.class)).val;
		int    limit = (args.length == 2) ? (int) ((VNumber) args[1].requireType(VString.class)).val : 0;

		String[] parts = val.split(regex, limit);
		
		VString[] ls = new VString[parts.length];
		for (int i = 0; i < parts.length; ++i)
			ls[i] = new VString(parts[i]);
		
		return new VList(ls);
	}
	
	
	/**
	 * substr(n1, {n2}) : Returns the substring from n1 to n2-1 or from n1 to the end.
	 */
	public Value method_substr(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 2);
		int start = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		int end   = (args.length == 2) ? (int) ((VNumber) args[1].requireType(VNumber.class)).val : val.length();
				
		require((start >= 0)     && (start <  val.length()));
		require((end   >= start) && (end   <= val.length()));
		
		return new VString(val.substring(start, end));
	}
	
	
	/**
	 * trim() : Strips leading and trailing whitespace from the string. 
	 */
	public Value method_trim(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
	
		return new VString(val.trim());
	}
	
	
	/**
	 * upper() : Returns the uppercase version of the string.
	 */
	public Value method_upper(Value ... args)
	throws EvaluationException
  {
		requireCount(args, 0, 0);

		return new VString(val.toUpperCase());
	}
}
