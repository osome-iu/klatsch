package edu.iu.cnets.klatsch.feed;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.misc.Utility;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Event;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.model.basic.BasicNode;
import edu.iu.cnets.klatsch.model.basic.BasicEdge;
import edu.iu.cnets.klatsch.model.basic.BasicEvent;


/**
 * This is a feed that serves as a bottomless source of completely random data for testing purposes.
 */
public class Random //implements Feed
{
	String       name;
	List<String> actors;
	List<String> memes;

	
	/**
	 * Initializes a new Random feed.  The only extra argument is a name for the feed.
	 * 
	 * @param args  { "Random", name of feed }
	 */
	public Random(Value[] args)
	throws EvaluationException
	{
		name = ((VString) args[1].requireType(VString.class)).val;
		actors = new Vector<String>();
		memes  = new Vector<String>();
	}
	
	
	/**
	 * Standard interface function.
	 */
	public String name()
	{
		return name;
	}
	
	
	/**
	 * Standard interface function.
	 * 
	 * This just so happens to returns a (nearly) infinitely long series of events.
	 */
	public Iterator<Event> events(int startTime)
	{
		return events(startTime, Integer.MAX_VALUE);
	}
	
	
	/**
	 * Standard interface function.
	 * 
	 * This just so happens to return (end - start + 1) events.
	 */
	public Iterator<Event> events(int startTime, int endTime)
	{
		final int start = startTime;
		final int end   = endTime;
		
		return new Iterator<Event>() {
			
			int time = start;
			
			
			public boolean hasNext()
			{
				return (time <= end);
			}
			
			
			public Node pickActor(List<String> ls)
			{
				int id;
				if ((Utility.rnd.nextDouble() < 0.1) || (ls.size() < 1)) {
					ls.add(Utility.randomName());
					id = ls.size() - 1;
				} else
					id = Utility.rnd.nextInt(ls.size());
				return new BasicNode(Node.TYPE_ACTOR, id);
			}

			
			public Node pickMeme(List<String> ls)
			{
				int id;
				if ((Utility.rnd.nextDouble() < 0.1) || (ls.size() < 1)) {
					ls.add(Utility.randomName());
					id = ls.size() - 1;
				} else
					id = Utility.rnd.nextInt(ls.size());
				return new BasicNode(Node.TYPE_MEME, id);
			}
			
			
			public Event next()
			{
				// pick an actor and meme
				Node  actor = pickActor(actors);
				Node  meme  = pickMeme (memes);

				// construct the edge
				Edge  edge  = new BasicEdge(actor, meme, time, time, Utility.rnd.nextDouble(), 1.0); 
				
				// construct the event
				Event event = new BasicEvent(time, edge);
				
				// increment the time counter
				++time;
				return event;
			}

			
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	/**
	 * Standard interface function.
	 */
	public Iterator<String> actorLabels()
	{
		return actors.iterator();
	}
	
	
	/**
	 * Standard interface function.
	 */
	public String actorLabel(Node n)
	throws IndexOutOfBoundsException
	{
		return actors.get(n.id());
	}
	
	
	/**
	 * Standard interface function.
	 */
	public Iterator<String> memeLabels()
	{
		return memes.iterator();
	}

	
	/**
	 * Standard interface function.
	 */
	public String memeLabel(Node n)
	{
		return memes.get(n.id());
	}
}
