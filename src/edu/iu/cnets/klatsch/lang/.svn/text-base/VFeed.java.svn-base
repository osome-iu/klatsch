package edu.iu.cnets.klatsch.lang;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.model.Feed;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.stream.SWrapperIterator;


/**
 * This makes {@link Feed} values from the model a first-class value in Klatsch.
 */
public class VFeed extends Value
{
	/** the feed we're encapsulating */
	Feed f;
	

	/**
	 * Constructs a new feed.
	 */
	public VFeed(Feed f)
	{
		this.f = f;
	}
	
	
	/**
	 * Construct a printable representation of the feed.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return "Feed<" + f.name() + ">";
	}

	
	/**
	 * actorevents(n)         : Returns stream of events for the actor with the given ID.
	 * actorevents(n, t1, t2) : Returns stream of events between times t1 and t2.
	 */
	public Value method_actorevents(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 3);
		int actor = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		
		if (args.length == 3) {
			int startTime = (int) ((VNumber) args[1].requireType(VNumber.class)).val;
			int endTime   = (int) ((VNumber) args[2].requireType(VNumber.class)).val;
			return new VStream(new SWrapperIterator(VEvent.class, f.actorEvents(actor, startTime, endTime)));
		}
		
		return new VStream(new SWrapperIterator(VEvent.class, f.actorEvents(actor)));
	}

	
	/**
	 * actorid(s) : Returns the ID of the actor with the given label.
	 */
	public Value method_actorid(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		String label = ((VString) args[0].requireType(VString.class)).val;
		
		try {
			return new VNumber(f.actorId(label));
		} catch (IllegalArgumentException e) {
			throw new EvaluationException("Unknown actor");
		}
	}
	
	
	/**
	 * actorlabel(n) : Returns the label of the actor with ID n.
	 */
	public Value method_actorlabel(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		
		try {
			return new VString(f.actorLabel(id));
		} catch (IllegalArgumentException e) {
			throw new EvaluationException("Unknown actor");
		}
	}
	
	
	/**
	 * connect() : Attempts to connect to the feed.
	 */
	public Value method_connect(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		return new VBoolean(f.connect());
	}
	

	/**
	 * memeevents(n)         : Returns stream of events for the meme with the given ID.
	 * memeevents(n, t1, t2) : Returns stream of events between times t1 and t2.
	 */
	public Value method_memeevents(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 3);
		int meme = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		
		if (args.length == 3) {
			int startTime = (int) ((VNumber) args[1].requireType(VNumber.class)).val;
			int endTime   = (int) ((VNumber) args[2].requireType(VNumber.class)).val;
			return new VStream(new SWrapperIterator(VEvent.class, f.memeEvents(meme, startTime, endTime)));
		}
		
		return new VStream(new SWrapperIterator(VEvent.class, f.memeEvents(meme)));
	}

	
	/**
	 * memeid(s) : Returns the ID of the meme with the given label.
	 */
	public Value method_memeid(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		String label = ((VString) args[0].requireType(VString.class)).val;
		
		try {
			return new VNumber(f.memeId(label));
		} catch (IllegalArgumentException e) {
			throw new EvaluationException("Unknown meme");
		}
	}
	
	
	/**
	 * memelabel(n) : Returns the label of the meme with ID n.
	 */
	public Value method_memelabel(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		
		try {
			return new VString(f.memeLabel(id));
		} catch (IllegalArgumentException e) {
			throw new EvaluationException("Unknown meme");
		}
	}
	
	
	/**
	 * memelinks(n) : Returns memes linked to the meme with the given ID.
	 */
	public Value method_memelinks(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		
		try {
			return new VStream(new SWrapperIterator(VNumber.class, f.memeLinks(id)));
		} catch (IllegalArgumentException e) {
			throw new EvaluationException("Unknown meme");
		}
	}

	
	/**
	 * name() : Returns the name of the feed.
	 */
	public Value method_name(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VString(f.name());
	}
	
	
	/**
	 * update(g) : Applies this feed's updateTrust() rules to the given graph.
	 */
	public Value method_update_trust(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Graph g = ((VGraph) args[0].requireType(VGraph.class)).g;
		
		f.updateTrust(g);
		return VNull.NULL;
	}
}
