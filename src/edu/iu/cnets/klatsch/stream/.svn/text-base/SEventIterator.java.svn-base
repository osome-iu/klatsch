package edu.iu.cnets.klatsch.stream;

import java.util.Iterator;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VEvent;
import edu.iu.cnets.klatsch.lang.VFeed;
import edu.iu.cnets.klatsch.model.Event;


/**
 * This class wraps a stream around an iterator over {@link Event} objects.
 * 
 * This provides functionality to the {@link VFeed} class that {@link SIterator} cannot,
 * since we need to translate {@link Event} objects into {@link VEvent} objects.
 */
public class SEventIterator extends SIterator
{
	/**
	 * Builds a new stream from the given iterator.
	 */
	public SEventIterator(Iterator<Event> iter)
	{
		super(iter);
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
			return new VEvent((Event) iter.next());
		else
			throw new EvaluationException("end of stream");
	}
}
