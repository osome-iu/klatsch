package edu.iu.cnets.klatsch.model;

import edu.iu.cnets.klatsch.lang.VDictionary;


/**
 * This is the interface for working with a graph built from a feed of social media events.
 *	
 * The rest of the Klatsch interpreter operates ONLY in terms of this interface.
 */
public interface Graph
{
	public Graph             actorGraph    ();
	public Graph             actorMemeGraph();
	public void              add           (Edge e);
	public void              clear         ();
	public Graph             component     (int n);
	public int               componentCount();
	public int[]             componentSize ();
	public int               dstCount      ();
	public Iterable<Integer> dstK          ();
	public int               dstK          (Node n);
	public Iterable<Double>  dstS          ();
	public double            dstS          (Node n);
	public int               edgeCount     ();
	public Iterable<Edge>    edges         ();
	public Iterable<Edge>    edgesIn       (Node n);
	public Iterable<Edge>    edgesOut      (Node n);
	public boolean           export        (String path, String format);
	public Edge              getEdge       (Edge e);
	public Node              getNode       (Node n);
	public boolean           hasEdge       (Edge e);
	public boolean           hasNode       (Node n);
	public void              layout        (VDictionary config);
	public Graph             memeGraph     ();
	public int               nodeCount     ();
	public Iterable<Node>    nodes         ();
	public int               srcCount      ();
	public Iterable<Integer> srcK          ();
	public int               srcK          (Node n);
	public Iterable<Double>  srcS          ();
	public double            srcS          (Node n);
	public double            statClustering();
	public double            statDiameter  ();
	public double            statDstKPower ();
	public double            statModularity();
	public double            statPathLength();
	public double            statSrcKPower ();
	public Graph             trustedGraph  ();
	public double            weight        (Edge e);
}
