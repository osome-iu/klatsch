package edu.iu.cnets.klatsch.model.gephi;

import org.gephi.graph.api.Attributes;
import org.gephi.graph.api.NodeData;

import edu.iu.cnets.klatsch.model.Node;


/**
 * This is an implementation of {@link Node} for use with the Gephi toolkit.
 */
public class GephiNode extends Node
{
	int   id;
	int   type;
	int   color;
	float x, y;
	
	
	public GephiNode(GephiGraph graph, org.gephi.graph.api.Node gNode)
	{
		NodeData   data = gNode.getNodeData();
		Attributes attr = data.getAttributes();
		id    = (Integer) attr.getValue(graph.colNodeId);
		type  = (Integer) attr.getValue(graph.colNodeType);
		color = (Integer) attr.getValue(graph.colNodeColor);
		x     = data.x();
		y     = data.y();
	}
	
	
	public GephiNode(int type, int id, int color)
	{
		this.type  = type;
		this.id    = id;
		this.color = color;
		this.x     = (float) Math.random() * 100;
		this.y     = (float) Math.random() * 100;
	}
	
	
	public GephiNode(int type, int id)
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
	
	
	public org.gephi.graph.api.Node toGephi(GephiGraph graph)
	{
		org.gephi.graph.api.Node gNode = graph.gGraph.getNode(toString());

		if (gNode == null)
			gNode = graph.gModel.factory().newNode(toString());

		NodeData   data = gNode.getNodeData();
		Attributes attr = data.getAttributes();
		attr.setValue(graph.colNodeId,    id);
		attr.setValue(graph.colNodeType,  type);
		attr.setValue(graph.colNodeColor, color);
		data.setX(x);
		data.setY(y);
		
		return gNode;
	}
		
	
	public int compareTo(Node other)
	{
		if (id() < other.id())  return -1;
		if (id() > other.id())  return  1;
		return 0;
	}
	
	
	public boolean equals(Object obj)
	{
		return (obj instanceof GephiNode) && (id() == ((GephiNode) obj).id());
	}
	
	
	public int hashCode()
	{
		return id();
	}
	
	
	public String toString()
	{
		return Node.TYPE_CODE[type] + Integer.toString(id);
	}
}
