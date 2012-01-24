package edu.iu.cnets.klatsch.model.basic;

import edu.iu.cnets.klatsch.model.Node;


/**
 * This is a simple implementation of {@link Node} for feed types that don't need any advanced features.
 */
public class BasicNode extends Node
{
	int id;
	int type;
	int color;
	
	
	public BasicNode(int type, int id, int color)
	{
		this.type  = type;
		this.id    = id;
		this.color = color;
	}
	
	
	public BasicNode(int type, int id)
	{
		this(type, id, 0x000000);
	}
	
	
	public int id()
	{
		return id;
	}
	
	
	public int type()
	{
		return type;
	}
	
	
	public int color()
	{
		return color;
	}
	
	
	public int compareTo(Node other)
	{
		if (id < other.id())  return -1;
		if (id > other.id())  return  1;
		return 0;
	}
	
	
	public boolean equals(Object obj)
	{
		return (obj instanceof BasicNode) && (id == ((BasicNode) obj).id);
	}
	
	
	public int hashCode()
	{
		return id;
	}
}
