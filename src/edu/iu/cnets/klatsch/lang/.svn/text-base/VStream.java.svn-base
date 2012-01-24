package edu.iu.cnets.klatsch.lang;

import java.util.LinkedList;
import java.util.List;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.stream.Stream;
import edu.iu.cnets.klatsch.stream.SLimit;


/**
 * This is the language-level encapsulation of a stream.
 */
public class VStream extends Value
{
	public Stream s;
	
	
	public VStream(Stream s)
	{
		this.s = s;
	}
	
	
	public String toString()
	{
		return "Stream<" + s.toString() + ">";
	}
	
	
	/**
	 * current() : Returns the current value in the stream.
	 */
	public Value method_current(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return s.current();
	}
	
	
	/**
	 * done() : Tests whether we're at the end of the stream.
	 */
	public VBoolean method_done(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VBoolean(s.done());
	}
	
	
	/**
	 * filter(p) : Applies a procedure filter to the stream, yielding a new stream.
	 */
	public Value method_filter(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);

		return ((VClosure) Main.rt.get("_stream_filter")).apply(this, args[0]);
	}

	
	/**
	 * get() : Returns the next item in the stream.
	 */
	public Value method_get(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return s.get();
	}
	
	
	/**
	 * limit(n) : Wrap the stream in a limited-element stream.
	 */
	public Value method_limit(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int n = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Double.MAX_VALUE).val;
		
		return new VStream(new SLimit(s, n));
	}
	
	
	/**
	 * list() : Converts the stream into a list.  Better hope it's not an infinite stream!
	 */
	public Value method_list(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		List<Value> ls = new LinkedList<Value>();
		while (!s.done()) {
			ls.add(s.get());
		}
		
		return new VList(ls.toArray(new Value[ls.size()]));
	}
	
	
	/**
	 * map(p) : Applies a procedural mapping to the stream, yielding a new stream.
	 */
	public Value method_map(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);

		return ((VClosure) Main.rt.get("_stream_map")).apply(this, args[0]);
	}
	
	
	/**
	 * more() : Tests if there are more values to read.
	 */
	public VBoolean method_more(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VBoolean(!s.done());
	}

	
	/**
	 * reduce(p, acc) : Applies a reduction to the stream, yielding a new stream.
	 */
	public Value method_reduce(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		
		return ((VClosure) Main.rt.get("_stream_reduce")).apply(this, args[0], args[1]);
	}
}
