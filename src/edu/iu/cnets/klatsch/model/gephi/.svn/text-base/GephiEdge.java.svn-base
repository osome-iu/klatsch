package edu.iu.cnets.klatsch.model.gephi;

import org.gephi.graph.api.Attributes;
import org.gephi.graph.api.EdgeData;

import edu.iu.cnets.klatsch.model.Edge;


/**
 * This is an implementation of {@link Edge} for use with the Gephi toolkit.
 */
public class GephiEdge extends Edge
{
	float r, g, b;
	
	
	public GephiEdge(GephiGraph graph, org.gephi.graph.api.Edge gEdge)
	{
		src = new GephiNode(graph, gEdge.getSource());
		dst = new GephiNode(graph, gEdge.getTarget());
	
		EdgeData   data = gEdge.getEdgeData();
		Attributes attr = data.getAttributes();
		
		startTime = (Integer) attr.getValue(graph.colEdgeStartTime);
		endTime   = (Integer) attr.getValue(graph.colEdgeEndTime);
		weight    = (Double)  attr.getValue(graph.colEdgeWeight);
		trust     = (Double)  attr.getValue(graph.colEdgeTrust);
		color     = (Integer) attr.getValue(graph.colEdgeColor);
		r         = (float) ( (color >> 16)             / 255.0);
		g         = (float) (((color & 0x00ff00) >>  8) / 255.0);
		b         = (float) ( (color & 0x0000ff)        / 255.0);
	}
	

	public GephiEdge(GephiNode src, GephiNode dst, int startTime, int endTime, double weight, double trust, int color)
	{
		super(src, dst, startTime, endTime, weight, trust, color);
	}

	
	public GephiEdge(GephiNode src, GephiNode dst, int startTime, int endTime, double weight, double trust)
	{
		super(src, dst, startTime, endTime, weight, trust);
	}
	
	
	public GephiEdge(GephiNode src, GephiNode dst, double weight, double trust)
	{
		super(src, dst, weight, trust);
	}
	
	
  public GephiEdge(GephiNode src, GephiNode dst)
  {
  	super(src, dst);
  }
  	
	
	public GephiEdge(GephiEdge e1, GephiEdge e2)
	{
		super(e1, e2);
	}
	
	
	public org.gephi.graph.api.Edge toGephi(GephiGraph graph)
	{
	  org.gephi.graph.api.Node gSrc  = ((GephiNode) src).toGephi(graph);
	  org.gephi.graph.api.Node gDst  = ((GephiNode) dst).toGephi(graph);
	  org.gephi.graph.api.Edge gEdge = graph.gModel.factory().newEdge(gSrc, gDst, (float) weight, true);
	  //org.gephi.graph.api.Edge gEdge = graph.gModel.factory().newEdge(gSrc, gDst, (float) Math.log(weight) + 1, true);

	  EdgeData   data = gEdge.getEdgeData();
		Attributes attr = data.getAttributes();

		attr.setValue(graph.colEdgeStartTime, startTime);
		attr.setValue(graph.colEdgeEndTime,   endTime);
		attr.setValue(graph.colEdgeWeight,    weight);
		attr.setValue(graph.colEdgeTrust,     trust);
		attr.setValue(graph.colEdgeColor,     color);
		data.setR(r);
		data.setG(g);
		data.setB(b);
		
		return gEdge;
	}
}
