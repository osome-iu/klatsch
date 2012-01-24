package edu.iu.cnets.klatsch.lang;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.gephi.GephiEdge;


/**
 * This makes {@link Edge} values from the model a first-class value in Klatsch.
 */
public class VEdge extends Value
{
	/** the edge we're encapsulating */
	Edge e;
	
	
	/**
	 * Constructs a new edge.
	 */
	public VEdge(Edge e)
	{
		this.e = e;
	}

	
	/**
	 * Constructs a new edge.
	 */
	public VEdge(GephiEdge e)
	{
		this.e = e;
	}
	
	
	/**
	 * Converts this edge into a string representation.
	 */
	public String toString()
	{
		return "Edge<" + e.src().type() + e.src().id() + "," + e.dst().type() + e.dst().id() + ">";
	}
	
	
	/**
	 * Creates a JSON representation of the edge.
	 */
	public String toJson()
	{
		return "[" + new VNode(e.src()).toJson() + "," + new VNode(e.dst()).toJson() + "]";
	}

	
	/**
	 * dst() : Returns the destination node for this edge.
	 */
	public Value method_dst(Value ... args)
	throws EvaluationException
	{
		return new VNode(e.dst());
	}
	
	
	/**
	 * e() : Returns the ending timestamp for this edge.
	 */
	public Value method_e(Value ... args)
	throws EvaluationException
	{
		return new VNumber(e.endTime());
	}
	
	
	/**
	 * src() : Returns the source node for this edge.
	 */
	public Value method_src(Value ... args)
	throws EvaluationException
	{
		return new VNode(e.src());
	}
	

	/**
	 * s() : Returns the starting timestamp for this edge.
	 */
	public Value method_s(Value ... args)
	throws EvaluationException
	{
		return new VNumber(e.startTime());
	}
	
	
	/**
	 * trust() : Returns the trusted weight of the edge.
	 */
	public Value method_trust(Value ... args)
	throws EvaluationException
	{
		return new VNumber(e.trust());
	}
	
	
	/**
	 * w() : Returns the total weight of the edge.
	 */
	public Value method_w(Value ... args)
	throws EvaluationException
	{
		return new VNumber(e.weight());
	}
}
