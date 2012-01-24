package edu.iu.cnets.klatsch.gui;

import edu.iu.cnets.klatsch.Main;


/**
 * A Pane is the subclass of {@link Widget} that is displayed in the main part of the GUI.  All panes
 * share some functionality that normal widgets lack, especially the ability to export their data.
 */
abstract public class Pane extends Widget
{
	static int uniqueId = 0;

	/** the ID of this pane */
	protected int    id;
	
	/** the name of this pane */
	protected String name;
	
	
	/**
	 * Default constructor.
	 */
	public Pane()
	{
		super(Main.gui, Main.gui.paneX1, Main.gui.paneY1, Main.gui.paneX2, Main.gui.paneY2);
		this.id = uniqueId++;
		
		Main.gui.add(this);
	}
	
	
	/**
	 * Returns the ID number of the pane.
	 */
	public int id()
	{
		return id;
	}
	
	
	/**
	 * Returns the name of the pane.
	 */
	public String name()
	{
		return name;
	}
}
