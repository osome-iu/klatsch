package edu.iu.cnets.klatsch.stream;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VNumber;


/**
 * This class builds a stream around a numeric iteration.
 */
public class SRange extends Stream
{
	/** the initial, final, and step values */
	double start, end, step;
	
	/** the current value */
	double current;
	
	
	/**
	 * Builds a new stream from the given iteration parameters.
	 */
	public SRange(double start, double end, double step)
	{
		this.start = start;
		this.end   = end;
		this.step  = step;
		
		current = start;
	}
	
	
	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		if (current <= end) {
			double val = current;
			current += step;
			return new VNumber(val);
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
		return (current > end);
	}
	
	
	/**
	 * Provides an indication of internal state.
	 * 
	 * @return the stream in string form
	 */
	public String toString()
	{
		return "range @ " + current;
	}
}
