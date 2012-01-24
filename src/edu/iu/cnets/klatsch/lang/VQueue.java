package edu.iu.cnets.klatsch.lang;

import java.util.Queue;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.stream.SIterator;


/**
 * This value represents a queue (i.e. FIFO).
 */
public class VQueue extends Value
{
	/** the values actually being stored */
	Queue<Value> q;
	
	
	public VQueue(Queue<Value> q)
	{
		this.q = q;
	}
	
	
	public String toString()
	{
		return "Queue<N=" + q.size() + ">";
	}
	
	
	/**
	 * deq() : Dequeue the head element from the queue.
	 */
	public Value method_deq(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (!q.isEmpty())
			return q.remove();
		else
			throw new EvaluationException("Queue is empty");
	}
	
	
	/**
	 * empty() : Returns true if the queue is empty.
	 */
	public Value method_empty(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VBoolean(q.isEmpty());
	}
	
	
	/**
	 * enq(v) : Enqueues the value v to the end of the queue.
	 * Returns the queue to support method chaining.
	 */
	public Value method_enq(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		
		q.offer(args[0]);
		return this;
	}
	
	
	/**
	 * len() : Returns the length of the queue.
	 */
	public Value method_len(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(q.size());
	}
	
	
	/**
	 * list() : Converts the queue to a list.
	 */
	public Value method_list(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		Value[] ls = q.toArray(new Value[q.size()]);
		return new VList(ls);
	}
	
	
	/**
	 * peek() : Returns the head elements without dequeuing it.
	 */
	public Value method_peek(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (!q.isEmpty())
			return q.peek();
		else
			throw new EvaluationException("Queue is empty");
	}
	
	
	/**
	 * stream() : Converts the queue to a stream.
	 */
	public Value method_stream(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SIterator(q.iterator()));
	}
}
