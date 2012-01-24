package edu.iu.cnets.klatsch.lang;

import java.util.Stack;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.stream.SIterator;


/**
 * This value represents a push-down stack (i.e., LIFO).
 */
public class VStack extends Value
{
	/** the values actually being stored */
	Stack<Value> s;
	
	
	public VStack(Stack<Value> s)
	{
		this.s = s;
	}
	
	
	public String toString()
	{
		return "Stack<N=" + s.size() + ">";
	}
	
	
	/**
	 * empty() : Returns true if the stack is empty.
	 */
	public Value method_empty(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VBoolean(s.isEmpty());
	}
	
	
	/**
	 * len() : Returns the length of the stack.
	 */
	public Value method_len(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(s.size());
	}
	
	
	/**
	 * list() : Converts the stack to a list.
	 */
	public Value method_list(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		Value[] ls = s.toArray(new Value[s.size()]);
		return new VList(ls);
	}
	
	
	/**
	 * peek() : Returns the top element without popping it.
	 */
	public Value method_peek(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (!s.isEmpty())
			return s.peek();
		else
			throw new EvaluationException("Stack is empty");
	}
	
	
	/**
	 * pop() : Remove and return the top element in the stack.
	 */
	public Value method_pop(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		if (!s.isEmpty())
			return s.pop();
		else
			throw new EvaluationException("Stack is empty");
	}
	
	
	/**
	 * push(v) : Pushes the value v onto the top of the stack.
	 * Returns the stack to support method chaining.
	 */
	public Value method_push(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		
		s.push(args[0]);
		return this;
	}
	
	
	/**
	 * stream() : Converts the stack to a stream.
	 */
	public Value method_stream(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SIterator(s.iterator()));
	}
}
