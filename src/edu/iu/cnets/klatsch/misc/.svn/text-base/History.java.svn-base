package edu.iu.cnets.klatsch.misc;

import java.util.ArrayList;
import java.util.List;


/**
 * This class encapsulates the idea of a history of previous lines of input, which is
 * useful to both the line editor and the GUI.
 */
public class History
{
	/** the default history size */
	public static final int DEFAULT_SIZE = 25;
	
	/** the previous lines of input */
	List<String> ls;
	
	/** the maximum number of lines to retain */
	int          maxLines;
	
	/** the current pointer within the history */
	int          current;
	
	
	/**
	 * Initialize a new history of the default size.
	 */
	public History()
	{
		this(DEFAULT_SIZE);
	}
	
	
	/**
	 * Initialize a new history of the given size.
	 */
	public History(int size)
	{
		ls       = new ArrayList<String>(size);
		maxLines = size;
		current  = 0;
	}
	
	
	/**
	 * Adds a new line to the history.
	 * 
	 * @param line  the line to add
	 */
	public void add(String line)
	{
		if (ls.size() >= maxLines)
			ls.remove(0);
		ls.add(line);
	}
	
	
	/**
	 * Gets the current line in the history.
	 * 
	 * @return the line currently pointed to
	 */
	public String get()
	{
		return (current < ls.size()) ? ls.get(current) : ""; 
	}
	
	
	/**
	 * Moves the current pointer to the next position.
	 */
	public void next()
	{
		if (current < ls.size())
			++current;
	}
	
	
	/**
	 * Moves the current pointer to the previous position.
	 */
	public void prev()
	{
		if (current > 0)
			--current;
	}
	
	
	/**
	 * Resets the position of the current pointer.
	 */
	public void reset()
	{
		current = ls.size();
	}
}
