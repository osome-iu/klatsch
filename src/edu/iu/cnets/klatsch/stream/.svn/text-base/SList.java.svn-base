package edu.iu.cnets.klatsch.stream;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VList;


/**
 * This class wraps a stream around a {@link VList}.
 */
public class SList extends Stream
{
	/** the list we're iterating over */
	VList ls;
	
	/** our current index in the list (the next one to be returned) */
	int   index;


	/**
	 * Builds a new stream from the given list.
	 */
	public SList(VList ls)
	{
		this.ls    = ls;
		this.index = 0;
	}
	
	
	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		if (index < ls.ls.length)
			return ls.ls[index++];
		else
			throw new EvaluationException("end of stream");
	}
	
	
	/**
	 * Sees whether we're at the end of the stream.
	 * 
	 * @return true for end-of-stream
	 */
	public boolean done()
	{
		return (index >= ls.ls.length);
	}
	
	
	/**
	 * Provides an indication of internal state.
	 * 
	 * @return the stream in string form
	 */
	public String toString()
	{
		return "list @ " + index;
	}
}
