package edu.iu.cnets.klatsch.model;


/**
 * This is the interface for working with a node (actor or meme) from a feed of social media events.
 *	
 * The rest of the Klatsch interpreter operates ONLY in terms of this interface.
 * 
 * Note that nodes should override equals() and hashCode(), but because they're part of Object,
 * there's no way to force this through an interface.
 */
abstract public class Node implements Comparable<Node>
{
	/**
	 * It would be appropriate to use an enumerated type here, or something more mnenomic here,
	 * but the Gephi Attributes API doesn't support storage of non-primitive data type in a straightforward
	 * manner and breaks character attributes altogether when saving files.  This is extremely
	 * aggravating (can you detect my aggravation? plus, I'm irritated), but this is the best fix
	 * for the time being.
	 */
	public static final int    TYPE_ACTOR = 1;
	public static final int    TYPE_MEME  = 2;
	public static final char[] TYPE_CODE  = { 'X', 'A', 'M' };
	
	/** Returns the ID of this node. */
	abstract public int id();
	
	/** Returns the type of this node. */
	abstract public int type();
	
	/** Returns the color of this node. */
	abstract public int color();
}
