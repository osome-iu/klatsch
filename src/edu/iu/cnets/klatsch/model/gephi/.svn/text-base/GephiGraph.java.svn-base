package edu.iu.cnets.klatsch.model.gephi;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.project.api.ProjectController;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.EdgeData;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.NodeData;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.plugin.ExporterCSV;
import org.gephi.io.exporter.plugin.ExporterGEXF;
import org.gephi.io.exporter.plugin.ExporterGraphML;
import org.gephi.io.exporter.preview.PDFExporter;
import org.gephi.io.exporter.preview.SVGExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.preview.api.ColorizerFactory;
import org.gephi.preview.api.EdgeColorizer;
import org.gephi.preview.api.EdgeColorizerClient;
import org.gephi.preview.api.NodeColorizer;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.util.color.SimpleColor;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.ClusteringCoefficient;
import org.gephi.statistics.plugin.ConnectedComponents;
import org.gephi.statistics.plugin.DegreeDistribution;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.openide.util.Lookup;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.model.Node;


public class GephiGraph implements Graph
{
	/** the project controller is our source of new graph models */
	static ProjectController pc;
	static {
		// initialize the Gephi project controller
    pc = Lookup.getDefault().lookup(ProjectController.class);
    pc.newProject();
	}
	
	/** the columns in which we store Klatsch's private attributes */ 
	int            colNodeType;
	int            colNodeId;
	int            colNodeColor;
	int            colEdgeStartTime;
	int            colEdgeEndTime;
	int            colEdgeWeight;
	int            colEdgeTrust;
	int            colEdgeColor;
			
	/** the actual graph */
  DirectedGraph  gGraph;
		
	/** the model for the graph */
	GraphModel     gModel;
	
	/** the attribute model for the graph */
	AttributeModel gAttributeModel;
	
	/** the workspace for the graph */
	Workspace      gWorkspace;
			
	
	/**
	 * Basic constructor for an empty graph.
	 */
	public GephiGraph()
	{
		gWorkspace = pc.newWorkspace(pc.getCurrentProject());
		pc.openWorkspace(gWorkspace);
		
		gModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		gGraph = gModel.getDirectedGraph();

		gAttributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();

		colNodeType      = gAttributeModel.getNodeTable().addColumn("type",  AttributeType.INT)   .getIndex();
		colNodeId        = gAttributeModel.getNodeTable().addColumn("nid",   AttributeType.INT)   .getIndex();
		colNodeColor     = gAttributeModel.getNodeTable().addColumn("color", AttributeType.INT)   .getIndex();
		
		colEdgeStartTime = gAttributeModel.getEdgeTable().addColumn("start", AttributeType.INT)   .getIndex();
		colEdgeEndTime   = gAttributeModel.getEdgeTable().addColumn("end",   AttributeType.INT)   .getIndex(); 
		colEdgeWeight    = gAttributeModel.getEdgeTable().addColumn("w",     AttributeType.DOUBLE).getIndex();
		colEdgeTrust     = gAttributeModel.getEdgeTable().addColumn("trust", AttributeType.DOUBLE).getIndex();
		colEdgeColor     = gAttributeModel.getEdgeTable().addColumn("color", AttributeType.INT)   .getIndex();
	}


	/**
	 * Constructor for an existing graph.
	 * 
	 * @param path  path to the graph file
	 */
	public GephiGraph(String path)
	throws EvaluationException
	{
		Workspace space = pc.newWorkspace(pc.getCurrentProject());
		pc.openWorkspace(space);

		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		Container        container;

		try {
			File file = new File(path);
			container = importController.importFile(file);
			container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);
		} catch (FileNotFoundException e) {
			throw new EvaluationException("error reading graph file");
		}

		importController.process(container, new DefaultProcessor(), space);
		
		gModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		gGraph = gModel.getDirectedGraph();

		gAttributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
		
		colNodeType      = gAttributeModel.getNodeTable().getColumn("type") .getIndex();
		colNodeId        = gAttributeModel.getNodeTable().getColumn("nid")  .getIndex();
		colNodeColor     = gAttributeModel.getNodeTable().getColumn("color").getIndex();
		
		colEdgeStartTime = gAttributeModel.getEdgeTable().getColumn("start").getIndex();
		colEdgeEndTime   = gAttributeModel.getEdgeTable().getColumn("end")  .getIndex(); 
		colEdgeWeight    = gAttributeModel.getEdgeTable().getColumn("w")    .getIndex();
		colEdgeTrust     = gAttributeModel.getEdgeTable().getColumn("trust").getIndex();
		colEdgeColor     = gAttributeModel.getEdgeTable().getColumn("color").getIndex();
	}
	
	
	/**
	 * Returns the subgraph containing only actors and actor-actor edges.
	 */
	public Graph actorGraph()
	{
		GephiGraph g = new GephiGraph();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes()) {
			GephiNode node = new GephiNode(this, gNode);
			if (node.type == Node.TYPE_ACTOR)
				g.gGraph.addNode(node.toGephi(g));
		}

		for (org.gephi.graph.api.Edge gEdge : gGraph.getEdges())
			if (((new GephiNode(this, gEdge.getSource())).type == Node.TYPE_ACTOR) &&
					((new GephiNode(this, gEdge.getTarget())).type == Node.TYPE_ACTOR))
				g.add(new GephiEdge(this, gEdge));

		return g;
	}

	
	/**
	 * Returns the subgraph containing only actor-meme and meme-actor edges.
	 */
	public Graph actorMemeGraph()
	{
		GephiGraph g = new GephiGraph();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes()) {
			GephiNode node = new GephiNode(this, gNode);
			g.gGraph.addNode(node.toGephi(g));
		}

		for (org.gephi.graph.api.Edge gEdge : gGraph.getEdges()) {
			int srcType = new GephiNode(this, gEdge.getSource()).type;
			int dstType = new GephiNode(this, gEdge.getTarget()).type;
			if (((srcType == Node.TYPE_ACTOR) && (dstType == Node.TYPE_MEME)) ||
					((srcType == Node.TYPE_MEME)  && (dstType == Node.TYPE_ACTOR)))
				g.add(new GephiEdge(this, gEdge));
		}
		
		return g;
	}

	
	/**
	 * Adds the given edge to the graph. 
	 */
	public void add(Edge e)
	{
		org.gephi.graph.api.Edge gEdge = ((GephiEdge) e).toGephi(this);
		org.gephi.graph.api.Node gSrc  = gEdge.getSource();
		org.gephi.graph.api.Node gDst  = gEdge.getTarget();
		
		if (gGraph.contains(gEdge)) {
			GephiEdge oldEdge = new GephiEdge(this, gGraph.getEdge(gSrc, gDst));
			gGraph.removeEdge(gEdge);
			gEdge = new GephiEdge(oldEdge, (GephiEdge) e).toGephi(this);
		}

		if (!gGraph.contains(gSrc))  gGraph.addNode(gSrc);
		if (!gGraph.contains(gDst))  gGraph.addNode(gDst);

		gGraph.addEdge(gEdge);
	}
	
	
	/**
	 * Called to clear the contents of the graph in an effort to make its memory reclaimable by the Java garbage
	 * collector.
	 */
	public void clear()
	{
		gGraph.clear();
	}
	

	/**
	 * Extract a single component from the graph.
	 */
	public Graph component(int n)
	{
		// initialize the component column, just in case
		componentCount();
		
		int        col = gAttributeModel.getNodeTable().getColumn("componentnumber").getIndex();
		GephiGraph g   = new GephiGraph();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			if ((Integer) gNode.getNodeData().getAttributes().getValue(col) == n)
				g.gGraph.addNode(new GephiNode(this, gNode).toGephi(g));
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getEdges())
			if ((Integer) gEdge.getSource().getNodeData().getAttributes().getValue(col) == n)
				g.add(new GephiEdge(this, gEdge));
		
		return g;
	}
	

	/**
	 * Returns the number of components in the graph.
	 */
	public int componentCount()
	{
		ConnectedComponents cc = new ConnectedComponents();
		cc.execute(gModel, gAttributeModel);
		return cc.getConnectedComponentsCount();
	}
	

	/**
	 * Returns an array containing the size of each component in the graph.
	 */
	public int[] componentSize()
	{
		int[] size = new int[componentCount()];
		int   col  = gAttributeModel.getNodeTable().getColumn("componentnumber").getIndex();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			size[(Integer) gNode.getNodeData().getAttributes().getValue(col)]++;
		
		return size;
	}

	
	/**
	 * Returns the number of destination nodes in the graph.
	 */
	public int dstCount()
	{
		int count = 0;
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			if (gGraph.getInDegree(gNode) > 0)
				++count;
		
		return count;
	}
	

	/**
	 * Returns an iterable over the in-degrees of the nodes in the graph.
	 */
	public Iterable<Integer> dstK()
	{
		Map<org.gephi.graph.api.Node, Integer> map = new HashMap<org.gephi.graph.api.Node, Integer>();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			map.put(gNode, gGraph.getInDegree(gNode));
		
		return map.values();
	}

	
	/**
	 * Returns the in-degree of the given node.
	 */
	public int dstK(Node n)
	{
		return gGraph.getInDegree(((GephiNode) n).toGephi(this));
	}
	
	
	/**
	 * Returns an iterable over the in-strengths of the nodes in the graph.
	 */
	public Iterable<Double> dstS()
	{
		Map<org.gephi.graph.api.Node, Double> map = new HashMap<org.gephi.graph.api.Node, Double>();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes()) {
			double sum = 0.0;
			for (org.gephi.graph.api.Edge gEdge : gGraph.getInEdges(gNode))
				sum += gEdge.getWeight();
			map.put(gNode, sum);
		}
		
		return map.values();
	}
	
	
	/**
	 * Returns the in-strength of the given node.
	 */
	public double dstS(Node n)
	{
		double sum = 0.0;
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getInEdges(((GephiNode) n).toGephi(this)))
			sum += gEdge.getWeight();

		return sum;
	}
	
	
	/**
	 * Returns the total number of edges.
	 */
	public int edgeCount()
	{
		return gGraph.getEdgeCount();
	}
	
	
	/**
	 * Returns an iterable over the edges.
	 */
	public Iterable<Edge> edges()
	{
		Set<Edge> edges = new HashSet<Edge>();
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getEdges())
			edges.add(new GephiEdge(this, gEdge));
		
		return edges;
	}
	
	
	/**
	 * Returns an iterable over the edges with this node as a destination.
	 */
	public Iterable<Edge> edgesIn(Node n)
	{
		Set<Edge> edges = new HashSet<Edge>();
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getInEdges(((GephiNode) n).toGephi(this)))
			edges.add(new GephiEdge(this, gEdge));
		
		return edges;
	}
	
	
	/**
	 * Returns an iterable over the edges with this node as a source.
	 */
	public Iterable<Edge> edgesOut(Node n)
	{
		Set<Edge> edges = new HashSet<Edge>();
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getOutEdges(((GephiNode) n).toGephi(this)))
			edges.add(new GephiEdge(this, gEdge));
		
		return edges;
	}
	
	
	/**
	 * Exports the graph in the given format.
	 */
	public boolean export(String path, String format)
	{
    ExportController ec = Lookup.getDefault().lookup(ExportController.class);

    Exporter exp;
    	   if (format.equals("csv"))      exp = new ExporterCSV();
    else if (format.equals("gexf"))     exp = new ExporterGEXF();
    else if (format.equals("graphml"))  exp = new ExporterGraphML();
    else if (format.equals("pdf"))      exp = new PDFExporter();
    else if (format.equals("svg"))      exp = new SVGExporter();
    else
    	return false;
    	   
    try {
      ec.exportFile(new File(path + "." + format), exp);
      return true;
    } catch (IOException e) {
    	return false;
    } catch (Exception e) {
    	System.err.println(e);
    	e.printStackTrace();
    	return false;
    }
	}
	

	/**
	 * Returns the given edge.
	 */
	public Edge getEdge(Edge e)
	{
		GephiNode src = (GephiNode) ((GephiEdge) e).src();
		GephiNode dst = (GephiNode) ((GephiEdge) e).dst();
		
		return new GephiEdge(this, gGraph.getEdge(src.toGephi(this), dst.toGephi(this)));
	}
	
	
	/**
	 * Returns the given node.
	 */
	public Node getNode(Node n)
	{
		return new GephiNode(this, gGraph.getNode(((GephiNode) n).toString()));
	}
	
	
	/**
	 * Returns true if the given edge is part of the graph.
	 */
	public boolean hasEdge(Edge e)
	{
		GephiNode src = (GephiNode) ((GephiEdge) e).src();
		GephiNode dst = (GephiNode) ((GephiEdge) e).dst();
		
		return (gGraph.getEdge(src.toGephi(this), dst.toGephi(this)) != null);
	}
	

	/**
	 * Returns true if the given node is part of the graph.
	 */
	public boolean hasNode(Node n)
	{
		return (gGraph.getNode(((GephiNode) n).toString()) != null);
	}
	
	
	/**
	 * Uses the given configuration dictionary to perform layout on the graph.
	 * 
	 * This is based on highly l33t Gephi code written by Snehal Patil (srpatil@umail.iu.edu).
	 * If it works well, it's to her credit.  If it doesn't, it's because Mark messed it up. 
	 */
	public void layout(VDictionary config)
	{
		// retrieve the layout parameters
		float pDistance = 50.0f;
		float pStep     = 20.0f;
		float pStrength =  0.8f;
		int   pIter     = 100;
    
		try {
			if (((VBoolean) config.method_has(new VString("distance"))).val) pDistance = (float) ((VNumber) config.method_get(new VString("distance"))).val;
			if (((VBoolean) config.method_has(new VString("step")))    .val) pStep     = (float) ((VNumber) config.method_get(new VString("step")))    .val;
			if (((VBoolean) config.method_has(new VString("strength"))).val) pStrength = (float) ((VNumber) config.method_get(new VString("strength"))).val;
			if (((VBoolean) config.method_has(new VString("iter")))    .val) pIter     = (int)   ((VNumber) config.method_get(new VString("iter")))    .val;
		} catch (EvaluationException e) {
			System.err.println("Parameter error in configuration dictionary for Gephi layout.");
		}
		
		// use the color attribute of each node to give it an RGB value
		for (org.gephi.graph.api.Node n : gGraph.getNodes()) {
			NodeData data  = n.getNodeData();
			int      color = (Integer) data.getAttributes().getValue(colNodeColor);
			data.setR((float) (((color & 0xff0000) >> 16) / 255.0));
			data.setG((float) (((color & 0x00ff00) >>  8) / 255.0));
			data.setB((float) ( (color & 0x0000ff)        / 255.0));
		}
		
		// use the color attribute of each edge to give it an RGB value
    for (org.gephi.graph.api.Edge e : gGraph.getEdges()) {
    	EdgeData data  = e.getEdgeData();
			int      color = (Integer) data.getAttributes().getValue(colEdgeColor);
			e.getEdgeData().setR((float) (((color & 0xff0000) >> 16) / 255.0));
			e.getEdgeData().setG((float) (((color & 0x00ff00) >>  8) / 255.0));
			e.getEdgeData().setB((float) ( (color & 0x0000ff)        / 255.0));
		}
		
		// get the necessary controllers 
		PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
		previewController.setBackgroundColor(Color.WHITE);
			
		// run YifanHuLayout for [iter] passes -- the layout always takes the current visible view
		YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
		layout.setGraphModel(gModel);
		layout.resetPropertiesValues();
		layout.setOptimalDistance (pDistance);
		layout.setInitialStep     (pStep);
		layout.setRelativeStrength(pStrength);
		for (int i = 0; (i < pIter) && layout.canAlgo(); i++)
			layout.goAlgo();
		
		// configure the preview
		PreviewModel     model            = previewController.getModel();
		ColorizerFactory colorizerFactory = Lookup.getDefault().lookup(ColorizerFactory.class);
		model.getNodeSupervisor().setShowNodeLabels(false);			
		model.getNodeSupervisor().setNodeColorizer ((NodeColorizer) colorizerFactory.createCustomColorMode(Color.BLACK));
	
		// NOTE: The code below outlines how explicit node colorization would be done if it isn't
		// currently working and the Gephi folks add a getOriginalColor() method to the preview nodes
		// to match the preview edges.  Uncomment it if you want to try it out, but it won't compile
		// unless the node class has been updated.
		/*
		model.getNodeSupervisor().setNodeColorizer(new NodeColorizer() {
			public void color(NodeColorizerClient client)
			{
				Color color = ((org.gephi.preview.api.Node) client).getOriginalColor();
				client.setColor(new SimpleColor(color.getRed(), color.getGreen(), color.getBlue()));
			}
		});
		*/
		
		// color each edge with the "original color" set at the top of this method
		EdgeColorizer ec = new EdgeColorizer() {
			public void color(EdgeColorizerClient client)
			{
				Color color = ((org.gephi.preview.api.Edge) client).getOriginalColor();
				client.setColor(new SimpleColor(color.getRed(), color.getGreen(), color.getBlue()));
			}
		};
			
		model.getUniEdgeSupervisor().setColorizer(ec);
		model.getBiEdgeSupervisor() .setColorizer(ec);

		model.getSelfLoopSupervisor()      .setShowFlag  (false);
		model.getUndirectedEdgeSupervisor().setCurvedFlag(false);
		model.getUniEdgeSupervisor()       .setCurvedFlag(false);
		model.getBiEdgeSupervisor()        .setCurvedFlag(false);
		model.getUniEdgeSupervisor()       .setArrowSize (0.0f);
		model.getUniEdgeSupervisor()       .setEdgeScale (5.0f);
	}

	
	/**
	 * Returns the subgraph containing only meme-meme edges.
	 */
	public Graph memeGraph()
	{
		GephiGraph g = new GephiGraph();

		for (org.gephi.graph.api.Node gNode : gGraph.getNodes()) {
			GephiNode node = new GephiNode(this, gNode);
			if (node.type == Node.TYPE_MEME)
				g.gGraph.addNode(node.toGephi(g));
		}

		for (org.gephi.graph.api.Edge gEdge : gGraph.getEdges())
			if (((new GephiNode(this, gEdge.getSource())).type == Node.TYPE_MEME) &&
					((new GephiNode(this, gEdge.getTarget())).type == Node.TYPE_MEME))
				g.add(new GephiEdge(this, gEdge));

		return g;
	}
	
	
	/**
	 * Returns the number of nodes in the graph.
	 */
	public int nodeCount()
	{
		return gGraph.getNodeCount();
	}
	
	
	/**
	 * Returns an iterable over the nodes.
	 */
	public Iterable<Node> nodes()
	{
		Set<Node> nodes = new HashSet<Node>();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			nodes.add(new GephiNode(this, gNode));
		
		return nodes;
	}
	
	
	/**
	 * Returns the number of source nodes in the graph.
	 */
	public int srcCount()
	{
		int count = 0;
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			if (gGraph.getOutDegree(gNode) > 0)
				++count;
		
		return count;
	}
	

	/**
	 * Returns an iterable over the out-degrees of the nodes in the graph.
	 */
	public Iterable<Integer> srcK()
	{
		Map<org.gephi.graph.api.Node, Integer> map = new HashMap<org.gephi.graph.api.Node, Integer>();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes())
			map.put(gNode, gGraph.getOutDegree(gNode));
		
		return map.values();
	}

	
	/**
	 * Returns the out-degree of the given node.
	 */
	public int srcK(Node n)
	{
		return gGraph.getOutDegree(((GephiNode) n).toGephi(this));
	}
	
	
	/**
	 * Returns an iterable over the out-strengths of the nodes in the graph.
	 */
	public Iterable<Double> srcS()
	{
		Map<org.gephi.graph.api.Node, Double> map = new HashMap<org.gephi.graph.api.Node, Double>();
		
		for (org.gephi.graph.api.Node gNode : gGraph.getNodes()) {
			double sum = 0.0;
			for (org.gephi.graph.api.Edge gEdge : gGraph.getOutEdges(gNode))
				sum += gEdge.getWeight();
			map.put(gNode, sum);
		}
		
		return map.values();
	}
	
	
	/**
	 * Returns the out-strength of the given node.
	 */
	public double srcS(Node n)
	{
		double sum = 0.0;
		
		for (org.gephi.graph.api.Edge gEdge : gGraph.getOutEdges(((GephiNode) n).toGephi(this)))
			sum += gEdge.getWeight();

		return sum;
	}
	
	
	/**
	 * Returns the mean clustering coefficient of the graph. 
	 */
	public double statClustering()
	{
    ClusteringCoefficient cc = new ClusteringCoefficient();
    cc.execute(gModel, gAttributeModel);
    return cc.getAverageClusteringCoefficient();
	}
	
	
	/**
	 * Returns the largest finite diameter within the graph. (?)
	 */
	public double statDiameter()
	{
		GraphDistance dist = new GraphDistance();
		dist.execute(gModel, gAttributeModel);
		return dist.getDiameter();
	}
	
	
	/**
	 * Returns an estimated power-law fit for in-degree.
	 */
	public double statDstKPower()
	{
		DegreeDistribution dd = new DegreeDistribution();
		dd.execute(gModel, gAttributeModel);
		return dd.getInPowerLaw();
	}
	
	
	/**
	 * Returns a modularity measure for the graph. (?)
	 */
	public double statModularity()
	{
		Modularity mod = new Modularity();
		mod.execute(gModel, gAttributeModel);
		return mod.getModularity();
	}
	
	
	/**
	 * Returns the mean path length within the components of the graph. (?)
	 */
	public double statPathLength()
	{
		GraphDistance dist = new GraphDistance();
		dist.execute(gModel, gAttributeModel);
		return dist.getPathLength();
	}
	
	
	/**
	 * Returns an estimated power-law fit for out-degree.
	 */
	public double statSrcKPower()
	{
		DegreeDistribution dd = new DegreeDistribution();
		dd.execute(gModel, gAttributeModel);
		return dd.getOutPowerLaw();
	}
	
	
	/**
	 * Returns the trusted subset of the graph.
	 */
	public Graph trustedGraph()
	{
		GephiGraph g = new GephiGraph();
		
		for (Edge e : edges())
			if (e.trust() > 0.0)
				g.add(e);
		
		return g;
	}

	
	/**
	 * Returns the total weight of the given edge.
	 * Returns zero if there is no such edge.
	 */
	public double weight(Edge e)
	{
		return gGraph.getEdge(((GephiEdge) e).toGephi(this).getSource(),
											    ((GephiEdge) e).toGephi(this).getTarget()).getWeight();
	}
}
