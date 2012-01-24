package edu.iu.cnets.klatsch.model.basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.model.Node;


public class BasicGraph implements Graph
{
	static BasicNode zero = new BasicNode(Node.TYPE_ACTOR, 0);
	
	
	/** the set of all nodes in the graph */
	SortedSet<Node> nodes;
	
	/** the set of all forward edges in the graph */
	SortedSet<Edge> edges;
	
	/** the set of all backward edges in the graph */
	SortedSet<Edge> backEdges;
	
	
	/**
	 * Basic constructor for an empty graph.
	 */
	public BasicGraph()
	{
		nodes     = new TreeSet<Node>();
		edges     = new TreeSet<Edge>();
		backEdges = new TreeSet<Edge>();
	}
	
	
	/**
	 * Returns the subgraph containing only actor-actor edges.
	 */
	public Graph actorGraph()
	{
		BasicGraph g = new BasicGraph();
		
		for (Node n : nodes)
			if (n.type() == Node.TYPE_ACTOR)
				g.nodes.add(n);
		
		for (Edge e : edges)
			if ((e.src().type() == Node.TYPE_ACTOR) && (e.dst().type() == Node.TYPE_ACTOR))
				g.add(e);
		
		return g;
	}

	
	/**
	 * Returns the subgraph containing only actor-meme and meme-actor edges.
	 */
	public Graph actorMemeGraph()
	{
		BasicGraph g = new BasicGraph();
		
		for (Node n : nodes)
			g.nodes.add(n);
		
		for (Edge e : edges)
			if (((e.src().type() == Node.TYPE_ACTOR) && (e.dst().type() == Node.TYPE_MEME)) ||
					((e.src().type() == Node.TYPE_MEME)  && (e.dst().type() == Node.TYPE_ACTOR)))
				g.add(e);
		
		return g;
	}

	
	/**
	 * Adds the given edge to the graph. 
	 */
	public void add(Edge e)
	{
		if (!nodes.contains(e.src()))  nodes.add(e.src());
		if (!nodes.contains(e.dst()))  nodes.add(e.dst());
		
		Edge fwd  = e;
		Edge back = new BasicEdge(e.dst(), e.src(), e.startTime(), e.endTime(), e.weight(), e.trust());
		
		if (edges.contains(e)) {
			fwd  = new BasicEdge(edges    .tailSet(fwd) .first(), fwd);   edges    .remove(fwd);
			back = new BasicEdge(backEdges.tailSet(back).first(), back);  backEdges.remove(back);
		}
		
		edges    .add(fwd);
		backEdges.add(back);
	}
	
	
	/**
	 * Called to clear the graph in an effort to make its memory reclaimable by the Java garbage
	 * collector.
	 */
	public void clear()
	{
		nodes     = new TreeSet<Node>();
		edges     = new TreeSet<Edge>();
		backEdges = new TreeSet<Edge>();
	}
	
	
	/**
	 * Returns the number of destination nodes in the graph.
	 */
	public int dstCount()
	{
		Set<Node> set = new HashSet<Node>();
		
		for (Edge e : edges)
			set.add(e.dst());
		
		return set.size();
	}
	

	/**
	 * Returns an iterable over the in-degrees of the nodes in the graph.
	 */
	public Iterable<Integer> dstK()
	{
		Map<Node, Integer> map = new HashMap<Node, Integer>();
	
		for (Edge e : edges)
			map.put(e.dst(), map.containsKey(e.dst()) ? 1 : map.get(e.dst()) + 1);
		
		return map.values();
	}

	
	/**
	 * Returns the in-degree of the given node.
	 */
	public int dstK(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);
		
		int sum = 0;
		
		for (Edge e : backEdges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero)))
			if (e.src().equals(n))
				++sum;
		
		return sum;
	}
	
	
	/**
	 * Returns an iterable over the in-strengths of the nodes in the graph.
	 */
	public Iterable<Double> dstS()
	{
		Map<Node, Double> map = new HashMap<Node, Double>();
		
		for (Edge e : edges) {
			double sum = map.containsKey(e.dst()) ? 0.0 : map.get(e.dst());
			map.put(e.dst(), sum + e.weight());
		}
		
		return map.values();
	}
	
	
	/**
	 * Returns the in-strength of the given node.
	 */
	public double dstS(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);

		double sum = 0.0;
		
		for (Edge e : backEdges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero)))
			if (e.src().equals(n))
				sum += e.weight();
		
		return sum;
	}
	
	
	/**
	 * Returns the total number of edges.
	 */
	public int edgeCount()
	{
		return edges.size();
	}
	
	
	/**
	 * Returns an iterable over the edges.
	 */
	public Iterable<Edge> edges()
	{
		return edges;
	}
	
	
	/**
	 * Returns an iterable over the edges with this node as a destination.
	 */
	public Iterable<Edge> edgesIn(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);

		return backEdges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero));
	}
	
	
	/**
	 * Returns an iterable over the edges with this node as a source.
	 */
	public Iterable<Edge> edgesOut(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);

		return edges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero));
	}
	
	
	/**
	 * Exports the graph in the given format.
	 */
	public boolean export(String path, String format)
	{
		return false;
	}
	
	
	/**
	 * Returns the given edge.
	 */
	public Edge getEdge(Edge e)
	{
		return edges.tailSet(e).first();
	}
	
	
	/**
	 * Returns the given node.
	 */
	public Node getNode(Node n)
	{
		return n; 
	}
	
	
	/**
	 * Returns true if the given edge is part of the graph.
	 */
	public boolean hasEdge(Edge e)
	{
		return edges.contains(e);
	}
	

	/**
	 * Returns true if the given node is part of the graph.
	 */
	public boolean hasNode(Node n)
	{
		return nodes.contains(n);
	}
	
	
	/**
	 * Returns the subgraph containing only meme-meme edges.
	 */
	public Graph memeGraph()
	{
		BasicGraph g = new BasicGraph();
		
		for (Node n : nodes)
			if (n.type() == Node.TYPE_MEME)
				g.nodes.add(n);

		for (Edge e : edges)
			if ((e.src().type() == Node.TYPE_MEME) && (e.dst().type() == Node.TYPE_MEME))
				g.add(e);
		
		return g;
	}
	
	
	/**
	 * Returns the number of nodes in the graph.
	 */
	public int nodeCount()
	{
		return nodes.size();
	}
	
	
	/**
	 * Returns an iterable over the nodes.
	 */
	public Iterable<Node> nodes()
	{
		return nodes;
	}
	
	
	/**
	 * Returns the number of source nodes in the graph.
	 */
	public int srcCount()
	{
		Set<Node> set = new HashSet<Node>();
		
		for (Edge e : edges)
			set.add(e.src());
		
		return set.size();
	}
	

	/**
	 * Returns an iterable over the out-degrees of the nodes in the graph.
	 */
	public Iterable<Integer> srcK()
	{
		Map<Node, Integer> map = new HashMap<Node, Integer>();
	
		for (Edge e : edges)
			map.put(e.src(), map.containsKey(e.src()) ? 1 : map.get(e.src()) + 1);
		
		return map.values();
	}

	
	/**
	 * Returns the out-degree of the given node.
	 */
	public int srcK(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);

		int sum = 0;

		for (Edge e : edges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero)))
			if (e.src().equals(n))
				++sum;
		
		return sum;
	}
	
	
	/**
	 * Returns an iterable over the out-strengths of the nodes in the graph.
	 */
	public Iterable<Double> srcS()
	{
		Map<Node, Double> map = new HashMap<Node, Double>();
		
		for (Edge e : edges) {
			double sum = map.containsKey(e.src()) ? 0.0 : map.get(e.src());
			map.put(e.src(), sum + e.weight());
		}
		
		return map.values();
	}
	
	
	/**
	 * Returns the out-strength of the given node.
	 */
	public double srcS(Node n)
	{
		BasicNode node    = (BasicNode) n;
		BasicNode nodeInc = new BasicNode(n.type(), n.id() + 1);

		double sum = 0.0;
		
		for (Edge e : edges.subSet(new BasicEdge(node, zero), new BasicEdge(nodeInc, zero)))
			if (e.src().equals(n))
				sum += e.weight();
		
		return sum;
	}
	
	
	/**
	 * These methods are not yet implemented for the basic graph because its development
	 * has stagnated since the creation of GephiGraph. 
	 */
	public int    componentCount()                   { throw new UnsupportedOperationException(); }
	public int[]  componentSize ()                   { throw new UnsupportedOperationException(); }
	public Graph  component     (int n)              { throw new UnsupportedOperationException(); }
	public void   layout        (VDictionary config) { throw new UnsupportedOperationException(); }
	public double statClustering()                   { throw new UnsupportedOperationException(); }
	public double statDiameter  ()                   { throw new UnsupportedOperationException(); }
	public double statDstKPower ()                   { throw new UnsupportedOperationException(); }
	public double statModularity()                   { throw new UnsupportedOperationException(); }
	public double statPathLength()                   { throw new UnsupportedOperationException(); }
	public double statSrcKPower ()                   { throw new UnsupportedOperationException(); }
	public Graph  trustedGraph  ()                   { throw new UnsupportedOperationException(); }

	
	/**
	 * Returns the total weight of this edge.
	 * Returns zero if there is no such edge.
	 */
	public double weight(Edge e)
	{
		double sum = 0.0;
		
		SortedSet<Edge> edgeSet = edges.tailSet(e);
		if ((edgeSet.size() > 0) && edgeSet.first().equals(e))
			sum += e.weight();

		return sum;
	}
}
