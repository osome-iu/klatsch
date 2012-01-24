package edu.iu.cnets.klatsch.stream;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This type of stream applies an element limit to an existing stream.  This can be accomplished
 * through other means, but it's still convenient.
 */
public class SLimit extends Stream
{
	/** the stream we've encapsulated */
	Stream s;
	
	/** the number of elements we have left to produce */
	int    left;
	
	
	/**
	 * Builds a new element-limited stream from the given one.
	 * 
	 * @param s     the stream to wrap
	 * @param left  how many elements to permit
	 */
	public SLimit(Stream s, int left)
	{
		this.s    = s;
		this.left = left;
	}
	
	
	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		if (left > 0) {
			left--;
			return s.getNext();
		} else
			throw new EvaluationException("end of stream");
	}
	
	
	/**
	 * Sees whether we're at the end of the stream.
	 * 
	 * @return true for end-of-stream
	 */
	public boolean done()
	{
		return (left <= 0);
	}
	
	
	/**
	 * Provides an indication of internal state.
	 * 
	 * @return the stream in string form
	 */
	public String toString()
	{
		return "limit @ " + left;
	}
}
