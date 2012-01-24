package edu.iu.cnets.klatsch.model.basic;

import java.util.Arrays;
import java.util.Iterator;

import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Event;


/**
 * This is a simple implementation of {@link Event} for feed types that don't need any advanced features.
 */
public class BasicEvent implements Event
{
	int    time;
	Edge[] edges;
	
	
	public BasicEvent(int time, Edge ... edges)
	{
		this.time  = time;
		this.edges = edges;
	}
	
	
	public int time()
	{
		return time;
	}
	
	
	public Iterator<Edge> edges()
	{
		return Arrays.asList(edges).iterator();
	}
}
