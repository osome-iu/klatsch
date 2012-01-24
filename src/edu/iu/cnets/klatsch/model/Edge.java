package edu.iu.cnets.klatsch.model;


/**
 * This is the basic class for representing an edge from a feed of social events.
 */
public abstract class Edge implements Comparable<Edge>
{
	/** the source node */
	protected Node   src;
	
	/** the destination node */
	protected Node   dst;
	
	/** the timestamp of the earliest data contributing to the weight of the edge */
	protected int    startTime;
	
	/** the timestamp of the latest data contributing to the weight of the edge */
	protected int    endTime;
	
	/** the weight of the edge */
	protected double weight;
	
	/** the trusted weight of the edge */
	protected double trust;
	
	/** the color of the edge */
	protected int    color;
	
	
	/**
	 * Default constructor for internal use.
	 */
	protected Edge() {}

	
	/**
	 * Constructor specifying all fields.
	 * 
	 * @param src        the source node
	 * @param dst        the destination node
	 * @param startTime  the start time for the edge
	 * @param endTime    the end time for the edge
	 * @param weight     the weight of the edge
	 * @param trust      the trusted weight of the edge
	 * @param color      the color of the edge
	 */
	public Edge(Node src, Node dst, int startTime, int endTime, double weight, double trust, int color)
	{
		this.src       = src;
		this.dst       = dst;
		this.startTime = startTime;
		this.endTime   = endTime;
		this.weight    = weight;
		this.trust     = trust;
		this.color     = color;
	}
	
	
	/**
	 * Constructor specifying all fields except color.
	 * 
	 * @param src        the source node
	 * @param dst        the destination node
	 * @param startTime  the start time for the edge
	 * @param endTime    the end time for the edge
	 * @param weight     the weight of the edge
	 * @param trust      the trusted weight of the edge
	 */
	public Edge(Node src, Node dst, int startTime, int endTime, double weight, double trust)
	{
		this.src       = src;
		this.dst       = dst;
		this.startTime = startTime;
		this.endTime   = endTime;
		this.weight    = weight;
		this.trust     = trust;
		this.color     = 0x000000;
	}

	
	/**
	 * Simplified constructor that uses "now" as a timestamp.
	 * 
	 * @param src     the source node
	 * @param dst     the destination node
	 * @param weight  the weight of the edge
	 * @param trust   the trusted weight of the edge
	 */
	public Edge(Node src, Node dst, double weight, double trust)
	{
		int now = (int) (System.currentTimeMillis() / 1000);
		
		this.src       = src;
		this.dst       = dst;
		this.startTime = now;
		this.endTime   = now;
		this.weight    = weight;
		this.trust     = trust;
		this.color     = 0x000000;
	}


	/**
	 * Simplied constructor with a default weight of 1.0, that is completely trusted.
	 * 
	 * @param src  the source node
	 * @param dst  the destination node
	 */
	public Edge(Node src, Node dst)
	{
		this(src, dst, 1.0, 1.0);
	}
	
	
	/**
	 * Builds a new edge by merging two existing edges, which should have the same endpoints.  If the endpoints
	 * don't match, those of the first edge will be treated as the authoritative values.
	 * 
	 * @param a  the first edge
	 * @param b  the second edge
	 */
	public Edge(Edge a, Edge b)
	{
		src       = a.src();
		dst       = a.dst();
		startTime = Math.min(a.startTime(), b.startTime());
		endTime   = Math.max(a.endTime(),   b.endTime());
		weight    = a.weight() + b.weight();
		trust     = a.trust()  + b.trust();
		color     = a.color();
	}
	
	
	/** Returns the source node for this edge. */
	public Node src()
	{
		return src;
	}
	
	
	/** Returns the destination node for this edge. */
	public Node dst()
	{
		return dst;
	}

	
	/** Returns the beginning timestamp for the edge. */
	public int startTime()
	{
		return startTime;
	}
	
	
	/** Returns the ending timestamp for the edge. */
	public int endTime()
	{
		return endTime;
	}
	
	
	/** Returns the weight of the edge. */
	public double weight()
	{
		return weight;
	}
	
	
	/** Returns the trusted weight of the edge. */
	public double trust()
	{
		return trust;
	}
	
	
	/** Returns the color of the edge. */
	public int color()
	{
		return color;
	}


	/** Implemented for Comparable. */
	public int compareTo(Edge other)
	{
		if (src.id() < other.src.id())  return -1;
		if (src.id() > other.src.id())  return  1;
		if (dst.id() < other.dst.id())  return -1;
		if (dst.id() > other.dst.id())  return  1;
		return 0;
	}
	
	
	/** Implemented for use with {@link java.util.Hashtable}. */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Edge))
			return false;
		
		Edge other = (Edge) obj;
		
		return (src.id() == other.src.id()) && (dst.id() == other.dst.id());
	}
	
	
	/** Implemented for use with {@link java.util.Hashtable}. */
	public int hashCode()
	{
		int key = src.id();
		
	  key = ~key + (key <<  15);
	  key =  key ^ (key >>> 12);
	  key =  key + (key <<   2);
	  key =  key ^ (key >>>  4);
	  key = (key + (key <<   3)) + (key << 11);
	  key =  key ^ (key >>> 16);
	  
	  return key + dst.id();
	}
}
