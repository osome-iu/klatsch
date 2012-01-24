package edu.iu.cnets.klatsch.model;

import java.util.Iterator;


/**
 * This is the interface for working with a feed of social events.
 *
 * The rest of the Klatsch interpreter operates ONLY in terms of this interface.
 */
public interface Feed
{
	/** Returns an iterator over all events for the given actor. */
	public Iterator<Event>   actorEvents(int actorId);
	
	/** Returns an iterator over all events for the given actor in the given time interval. */
	public Iterator<Event>   actorEvents(int actorId, int startTime, int endTime);
	
	/** Maps from an actor's label to its ID. */
	public int               actorId    (String actor) throws IllegalArgumentException;
	
	/** Maps from an actor's ID to its label. */
	public String            actorLabel (int actorId)  throws IllegalArgumentException;
	
	/** Establishes a network connect for the feed (if necessary). */
	public boolean           connect    ();
	
	/** Returns an iterator over all events in the given time interval. */
	public Iterator<Event>   events     (int startTime, int endTime);
	
	/** Returns an iterator over all events for the given meme. */
	public Iterator<Event>   memeEvents (int memeId);
	
	/** Returns an iterator over all events for the given meme in the given time interval. */
	public Iterator<Event>   memeEvents (int memeId, int startTime, int endTime);
	
	/** Maps from a meme's label to its ID. */
	public int               memeId     (String meme)  throws IllegalArgumentException;
	
	/** Maps from a meme's ID to its label. */
	public String            memeLabel  (int memeId)   throws IllegalArgumentException;

	/** Returns an iterator over all memes connected to the given meme. */
	public Iterator<Integer> memeLinks  (int memeId);

	/** Returns the name of this feed. */
	public String            name       ();
	
	/** Performs any trust updates necessary on a graph assembled from this feed. */
	public void              updateTrust(Graph g);
}
