package edu.iu.cnets.klatsch.stream;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * A stream differs from a list in that it's not random-access and doesn't necessarily have finite length.
 * They can be produced in a variety of ways, each of which may have their own internal representation for
 * their state.
 */
abstract public class Stream {
		
	abstract public Value   getNext() throws EvaluationException;
	abstract public boolean done();

	/** used to buffer a single item */
	Value buffer = null;


	/**
	 * Returns the next value that would be returned by get().
	 * 
	 * @return the value
	 */
	public Value current()
	throws EvaluationException
	{
		if (buffer == null)
			return (buffer = get());
		else
			return buffer;
	}


	/**
	 * Reads the next value from the stream.
	 * 
	 * @return the value
	 */
	public Value get()
	throws EvaluationException
	{
		if (buffer != null) {
			Value val = buffer;
			buffer = null;
			return val;
		} else
			return getNext();
	}
}
