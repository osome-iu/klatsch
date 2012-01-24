package edu.iu.cnets.klatsch.lang;

import java.util.Iterator;

import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.stream.SWrapperIterator;


/**
 * This value type encapsulates a graph object.
 */
public class VGraph extends Value
{
	Graph g;
	
	
	/**
	 * Initializes with the underlying graph data.
	 * 
	 * @param g  the graph to encapsulate
	 */
	VGraph(Graph g)
	{
		this.g = g;
	}
	
	
	/**
	 * Returns a printed representation of the graph.
	 * 
	 * @return a printable string
	 */
	public String toString()
	{
		return "Graph<N=" + g.nodeCount() + ", E=" + g.edgeCount() + ">";
	}
	
	
	/**
	 * Returns a JSON representation of the graph.
	 */
	public String toJson()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("{\"nodes\":[");
		Iterator<Node> nIter = g.nodes().iterator();
		while (nIter.hasNext()) {
			buffer.append(new VNode(nIter.next()).toJson());
			if (nIter.hasNext())
				buffer.append(',');
		}
		
		buffer.append("],\"edges\":[");
		Iterator<Edge> eIter = g.edges().iterator();
		while (eIter.hasNext()) {
			buffer.append(new VEdge(eIter.next()).toJson());
			if (eIter.hasNext())
				buffer.append(',');
		}
		
		buffer.append("]}");
		
		return buffer.toString();
	}
	
	
	/**
	 * actorgraph() : Returns the actor-only subgraph. 
	 */
	public Value method_actorgraph(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VGraph(g.actorGraph());
	}
	
	
	/**
	 * actormemegraph() : Returns the actor-meme (bipartite) subgraph.
	 */
	public Value method_actormemegraph(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VGraph(g.actorMemeGraph());
	}

	
	/**
	 * add(e) : Adds the given edge to the graph.
	 */
	public Value method_add(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		
		g.add(((VEdge) args[0].requireType(VEdge.class)).e);
		return this;
	}
	

	/**
	 * cc() : Returns the mean clustering coefficient.
	 */
	public Value method_cc(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		return new VNumber(g.statClustering());
	}

	
	/**
	 * clear() : Make the graph empty and hopefully free memory for the GC.
	 */
	public Value method_clear(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		g.clear();
		return this;
	}
	
	
	/**
	 * comp(n) : Returns component #n in the graph.  
	 */
	public Value method_comp(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int n = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Integer.MAX_VALUE).val;
		
		return new VGraph(g.component(n));
	}
	
	
	/**
	 * comp_count() : Returns the number of connected components.
	 */
	public Value method_comp_count(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		return new VNumber(g.componentCount());
	}
	
	
	/**
	 * comp_size() : Returns a list containing the size of each connected component.
	 */
	public Value method_comp_size(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		int[]   size  = g.componentSize();
		Value[] vSize = new Value[size.length];
		for (int i = 0; i < size.length; ++i)
			vSize[i] = new VNumber(size[i]);
		
		return new VList(vSize);
	}
	
	
	/**
	 * diameter() : Returns the graph diameter.
	 */
	public Value method_diameter(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		return new VNumber(g.statDiameter());
	}

	
	/**
	 * e() : Returns the number of edges in the graph.
	 */
	public Value method_e(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.edgeCount());
	}
	
	
	/**
	 * edges() : Returns a stream of edges in the graph.
	 */
	public Value method_edges(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SWrapperIterator(VEdge.class, g.edges().iterator()));
	}
	
	
	/**
	 * ei(n) : Returns a stream of edges with node n as a destination.
	 */
	public Value method_ei(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Node n = ((VNode) args[0].requireType(VNode.class)).n;
		
		return new VStream(new SWrapperIterator(VEdge.class, g.edgesIn(n).iterator()));
	}
	
	
	/**
	 * eo(n) : Returns a stream of edges with node n as a source.
	 */
	public Value method_eo(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Node n = ((VNode) args[0].requireType(VNode.class)).n;
		
		return new VStream(new SWrapperIterator(VEdge.class, g.edgesOut(n).iterator()));
	}
	
	
	/**
	 * export(s1, s2) : Exports the graph to file s1, using format s2.
	 */
	public Value method_export(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		String path   = ((VString) args[0].requireType(VString.class)).val;
		String format = ((VString) args[1].requireType(VString.class)).val;
		
		return new VBoolean(g.export(path, format));
	}

	
	/**
	 * get(e) : Returns edge e from the graph.
	 */
	public Value method_get(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Edge e = ((VEdge) args[0].requireType(VEdge.class)).e;
		
		return new VEdge(g.getEdge(e));
	}
	
	
	/**
	 * has(e) : Returns true if edge e is in the graph.
	 * has(n) : Returns true if node n is in the graph.
	 */
	public Value method_has(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		args[0].requireType(VEdge.class, VNode.class);
		
		if (args[0] instanceof VEdge)  return new VBoolean(g.hasEdge(((VEdge) args[0]).e));
		else                           return new VBoolean(g.hasNode(((VNode) args[0]).n));
	}
	
	
	/**
	 * ki(n) : Returns the in-degree of node n.
	 * ki()  : Returns a stream of in-degrees in the graph. 
	 */
	public Value method_ki(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1) {
			Node n = ((VNode) args[0].requireType(VNode.class)).n;
			return new VNumber(g.dstK(n));
		} else
			return new VStream(new SWrapperIterator(VNumber.class, g.dstK().iterator())); 
	}
	
	
	/**
	 * kipower() : Returns a power-law fit for the in-degree.
	 */
	public Value method_kipower(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.statDstKPower());
	}
	
	
	/**
	 * ko(n) : Returns the out-degree of node n.
	 * ko()  : Returns a stream of out-degrees in the graph. 
	 */
	public Value method_ko(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1) {
			Node n = ((VNode) args[0].requireType(VNode.class)).n;
			return new VNumber(g.srcK(n));
		} else
			return new VStream(new SWrapperIterator(VNumber.class, g.srcK().iterator())); 
	}
	
	
	/**
	 * kopower() : Returns a power-law fit for the out-degree.
	 */
	public Value method_kopower(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.statSrcKPower());
	}
	
	
	/**
	 * layout()  : Performs a layout operation using default parameters.
	 * layout(d) : Overrides the default parameters.
	 */
	public Value method_layout(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1)
			g.layout((VDictionary) args[0].requireType(VDictionary.class));
		else
			g.layout(new VDictionary());
		
		return this;
	}

	
	/**
	 * memegraph() : Returns the meme-only subgraph. 
	 */
	public Value method_memegraph(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VGraph(g.memeGraph());
	}
	
	
	/**
	 * modularity(): Returns the modularity measure.
	 */
	public Value method_modularity(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.statModularity());
	}
	
	
	/**
	 * n() : Returns the number of nodes in the graph.
	 */
	public Value method_n(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.nodeCount());
	}
	
	
	/**
	 * ni() : Returns the number of destination nodes in the graph.
	 */
	public Value method_ni(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.dstCount());
	}
	
	
	/**
	 * no() : Returns the number of source nodes in the graph.
	 */
	public Value method_no(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(g.srcCount());
	}
	
	
	/**
	 * nodes() : Returns a stream of nodes in the graph.
	 */
	public Value method_nodes(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SWrapperIterator(VNode.class, g.nodes().iterator()));
	}

	
	/**
	 * pathlength() : Returns the mean path length. (?)
	 */
	public Value method_pathlength(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		return new VNumber(g.statPathLength());
	}

	
	/**
	 * si(n) : Returns the in-strength of node n.
	 * si()  : Returns a stream of in-strengths in the graph. 
	 */
	public Value method_si(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1) {
			Node n = ((VNode) args[0].requireType(VNode.class)).n;
			return new VNumber(g.dstS(n));
		} else
			return new VStream(new SWrapperIterator(VNumber.class, g.dstS().iterator())); 
	}


	/**
	 * so(n) : Returns the out-strength of node n.
	 * so()  : Returns a stream of out-strengths in the graph. 
	 */
	public Value method_so(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1) {
			Node n = ((VNode) args[0].requireType(VNode.class)).n;
			return new VNumber(g.srcS(n));
		} else
			return new VStream(new SWrapperIterator(VNumber.class, g.srcS().iterator())); 
	}
	
	
	/**
	 * trusted() : Returns the trusted subgraph.
	 */
	public Value method_trusted(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VGraph(g.trustedGraph());
	}
}
