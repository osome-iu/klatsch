package edu.iu.cnets.klatsch.stream;

import java.util.Iterator;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This class wraps a stream around an iterator of {@link Value} objects.
 */
public class SIterator extends Stream
{
	/** the iterator we're using */
	Iterator<?> iter;
	

	/**
	 * Builds a new stream from the given iterator.
	 */
	public SIterator(Iterator<?> iter)
	{
		this.iter = iter;
	}
	
	
	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		if (iter.hasNext())
			return (Value) iter.next();
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
		return !iter.hasNext();
	}
	
	
	/**
	 * Provides an indication of internal state.
	 * 
	 * @return the stream in string form
	 */
	public String toString()
	{
		return "iter";
	}
}
