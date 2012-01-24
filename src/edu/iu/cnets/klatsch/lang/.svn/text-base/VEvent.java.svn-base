package edu.iu.cnets.klatsch.lang;

import java.util.Iterator;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.model.basic.BasicEvent;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Event;
import edu.iu.cnets.klatsch.stream.SWrapperIterator;


/**
 * This makes {@link Event} values from the model a first-class value in Klatsch.
 */
public class VEvent extends Value
{
	/** the event we're encapsulating */
	Event e;

	
	/**
	 * Constructs a new event.
	 */
	public VEvent(BasicEvent e)
	{
		this.e = e;
	}

	
	/**
	 * Constructs a new event.
	 */
	public VEvent(Event e)
	{
		this.e = e;
	}
	
	
	/**
	 * Converts this event into a string representation.
	 */
	public String toString()
	{
		return "Event<@" + e.time() + ">";
	}
	
	
	/**
	 * Creates a JSON representation of this event.
	 */
	public String toJson()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("{\"ts\":");
		buffer.append(e.time() + ",[");
		
		Iterator<Edge> iter = e.edges();
		while (iter.hasNext()) {
			buffer.append(new VEdge(iter.next()).toJson());
			if (iter.hasNext())
				buffer.append(',');
		}
		
		buffer.append("]}");
		
		return buffer.toString();
	}

	
	/**
	 * edges() : Returns a stream of edges in this event.
	 */
	public Value method_edges(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SWrapperIterator(VEdge.class, e.edges()));
	}
	
	
	/**
	 * time() : Returns the timestamp for this event.
	 */
	public Value method_time(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(e.time());
	}
}
