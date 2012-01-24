package edu.iu.cnets.klatsch.stream;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Callable;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This class wraps a stream around a procedure.  The elements of the stream are produced by repeatedly calling the
 * procedure with the previous value until an error is produced, at which point the stream ends.
 */
public class SProcedure extends Stream
{
	/** the procedure we're invoking */
	Callable proc;
	
	/** set to true when we're finished */
	boolean  finished = false;
	
	
	/**
	 * Builds a new stream from the given procedure.
	 */
	public SProcedure(Callable proc)
	{
		this.proc = proc;
	}
	

	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		Value[] args = {};
		
		if (finished)
			throw new EvaluationException("end of stream");
		
		return proc.apply(args);
	}
	
	
	/**
	 * Sees whether we're at the end of the stream.
	 * 
	 * @return true for end-of-stream
	 */
	public boolean done()
	{
		if (buffer != null)
			return false;
		
		try {
			buffer = proc.apply();
			return false;
		} catch (EvaluationException e) {
			finished = true;
			return true;
		}
	}
	
	
	/**
	 * Provides an indication of internal state.
	 * 
	 * @return the stream in string form
	 */
	public String toString()
	{
		return "proc";
	}
}
