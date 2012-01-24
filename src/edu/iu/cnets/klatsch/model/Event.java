package edu.iu.cnets.klatsch.model;

import java.util.Iterator;


/**
 * This is the interface for working with an event from a feed of social events.
 *
 * The rest of the Klatsch interpreter operates ONLY in terms of this interface.
 */
public interface Event
{
	/** Returns the timestamp for this event. */
	public int            time();
	
	/** Returns an iterator over the edges in this event. */
	public Iterator<Edge> edges();
}
