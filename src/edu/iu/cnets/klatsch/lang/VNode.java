package edu.iu.cnets.klatsch.lang;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.model.gephi.GephiNode;


/**
 * This makes {@link Node} values from the model a first-class value in Klatsch.
 */
public class VNode extends Value
{
	/** the node we're encapsulating */
	Node n;

	
	/**
	 * Constructs a new node.
	 */
	public VNode(GephiNode n)
	{
		this.n = n;
	}
	
	
	/**
	 * Constructs a new node.
	 */
	public VNode(Node n)
	{
		this.n = n;
	}
	

	/**
	 * Converts this node into a string representation.
	 */
	public String toString()
	{
		return "Node<" + Node.TYPE_CODE[n.type()] + n.id() + ">"; 
	}
	
	
	/**
	 * Creates a JSON representation of this node.
	 */
	public String toJson()
	{
		return "[\"" + Node.TYPE_CODE[n.type()] + "\"," + n.id() + "]"; 
	}
	
	
	/**
	 * actor() : Returns true if this is an actor node.
	 */
	public Value method_actor(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
	
		return new VBoolean(n.type() == Node.TYPE_ACTOR);
	}
	
	
	/**
	 * id() : Returns the ID of the node.
	 */
	public Value method_id(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(n.id());
	}
	
	
	/**
	 * meme() : Returns true if this is a meme node.
	 */
	public Value method_meme(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VBoolean(n.type() == Node.TYPE_MEME);
	}
}
