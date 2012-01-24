package edu.iu.cnets.klatsch.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.misc.Utility;


/**
 * The graph widget displays a visual layout of a graph.
 */
public class WidgetGraph extends Widget
{
	class Location { double x, y;  Location(double x, double y) { this.x = x;  this.y = y; } }
	class Velocity { double x, y;  Velocity(double x, double y) { this.x = x;  this.y = y; } }
	
	/** the graph we're showing */
	Graph  g;
	
	/** the current layout for the graph */
	Map<Object,Location> layout;

	
	/**
	 * Initializes a new graph widget.
	 */
	public WidgetGraph(KlatschGui parent, Graph g, String layout)
	{
		super(parent, 0, 16, parent.width - 1, parent.height - 16);
		this.g    = g;
		
		layoutRandom();
		
		     if (layout.equals("energy"))  layoutEnergy();
		else if (layout.equals("radial"))  layoutRadial();
	}
	
	
	/**
	 * Draws the current graph.
	 */
	protected void draw()
	{
		clear();
				
		if (g != null) {
			
			parent.stroke(0xff777777);
			
			for (Node src : g.nodes())
				for (Edge e : g.edgesOut(src)) {
					Node dst = e.dst();
					Location locSrc = layout.get(src), locDst = layout.get(dst);
					if ((locSrc != null) && (locDst != null))
						parent.line((float) locSrc.x, (float) locSrc.y, (float) locDst.x, (float) locDst.y);
				}
			
			parent.fill(0xffcc3333);
			parent.stroke(0xffff5555);

			for (Node n : g.nodes()) {
				Location loc = layout.get(n);
				if (loc != null)
					parent.ellipse((float) loc.x, (float) loc.y, 10, 10);
			}
			
			parent.fill(0xff33cccc);
			parent.textAlign(KlatschGui.CENTER);

			for (Node n : g.nodes()) {
				Location loc = layout.get(n);
				if (loc != null)
					parent.text(n.toString(), (float) loc.x, (float) loc.y - 7);
			}
		}
	}
	
	
	/**
	 * Updates the layout using the Kamada-Kawai energy-based algorithm (or a crappy approximation to it).
	 */
	void layoutEnergy()
	{
		if (g == null)
			return;

		// set random initial locations
		layoutRandom();

		// set initial node velocities to (0, 0)
		Map<Node,Velocity> vels = new HashMap<Node,Velocity>();
		for (Node n : g.nodes())
			vels.put(n, new Velocity(0.0, 0.0));
		
		// the layout loop
		double energy = 0.0;
		do {

			// reset energy
			energy = 0.0;
			
			// for each node
			for (Node n : g.nodes()) {
				
				// get the node's location
				Location here = layout.get(n);
				
				// net force on this node
				double forceX = 0.0;
			  double forceY = 0.0;
		
				// for each other node
			  for (Node other : g.nodes())
					if (other != n) {
						Location there  = layout.get(other);
						double   theta  = Math.atan2(there.y - here.y, there.x - here.x);
						double   cForce = -1e4 / ((there.y - here.y) * (there.y - here.y) + (there.x - here.x) * (there.x - here.x));
						forceX += cForce * Math.cos(theta);
						forceY += cForce * Math.sin(theta);
					}

				// for each connected node
				for (Edge e : g.edgesOut(n)) {
					Node other = e.dst();
					if (other != n) {
						Location there = layout.get(other);
						double   theta  = Math.atan2(there.y - here.y, there.x - here.x);
						double   cForce = 1e5 * Math.sqrt((there.y - here.y) * (there.y - here.y) + (there.x - here.x) * (there.x - here.x));
						forceX += cForce * Math.cos(theta);
						forceY += cForce * Math.sin(theta);
					}
				}
			
				// update velocity with damping = 0.99, timestep = 1.0
				Velocity vel = vels.get(n);
				vel.x = 0.7 * (vel.x + 1.0 * forceX);
				vel.y = 0.7 * (vel.y + 1.0 * forceY);
			
				// update position with timestep = 1
				here.x += 1.0 * vel.x;
				here.y += 1.0 * vel.y;
				
				// update energy with mass = 1.0
				energy += 1.0 * ((vel.x * vel.x) + (vel.y * vel.y));
			}
			System.err.println("E = " + energy);
			
		} while (energy > 0.001);
		
		// get ready to rescale
		double minX = x2, maxX = x1, minY = y2, maxY = y1;
		Iterator<Location> iterLoc;
		
		// find the location extremes
		iterLoc = layout.values().iterator();
		while (iterLoc.hasNext()) {
			Location loc = iterLoc.next();
			if (loc.x < minX)  minX = loc.x;  if (loc.x > maxX)  maxX = loc.x;
			if (loc.y < minY)  minY = loc.y;  if (loc.y > maxY)  maxY = loc.y;
		}
		
		// and rescale the location
		iterLoc = layout.values().iterator();
		while (iterLoc.hasNext()) {
			Location loc = iterLoc.next();
			loc.x = x1 + (x2 - x1) * ((loc.x - minX) / (maxX - minX) * 0.90 + 0.05);
			loc.y = y1 + (y2 - y1) * ((loc.y - minY) / (maxY - minY) * 0.90 + 0.05);
		}
	}
	
	
	/**
	 * Positions the nodes radially.
	 */
	void layoutRadial()
	{
		double inc = 2.0 * Math.PI / g.nodeCount();
		double r   = Math.min(0.45 * (y2 - y1), 0.45 * (x2 - x1));
		
		int i = 0;
		for (Node n : g.nodes()) {
			Location loc = layout.get(n);
			loc.x = x1 + (0.5 * (x2 - x1)) + r * Math.cos(inc * i);
			loc.y = y1 + (0.5 * (y2 - y1)) + r * Math.sin(inc * i);
			i++;
		}
	}
	
	
	/**
	 * Positions the nodes randomly.
	 */
	void layoutRandom()
	{
		layout = new HashMap<Object,Location>();

		for (Node n : g.nodes())
			layout.put(n, new Location((0.90 * Utility.rnd.nextFloat() + 0.05) * (x2 - x1) + x1,
					                       (0.90 * Utility.rnd.nextFloat() + 0.05) * (y2 - y1) + y1));
	}
}
