package edu.iu.cnets.klatsch.gui;

import edu.iu.cnets.klatsch.exception.EvaluationException;


/**
 * This is the basic class of all the display widgets in the Klatsch GUI.
 */
public abstract class Widget
{
	/** the boundaries of the widget */
	protected int        x1, y1, x2, y2;
	
	/** the applet we're working for */
	protected KlatschGui parent;
	
	protected abstract void draw()
	throws EvaluationException;
	
	protected void keyPressed   (int which) {}
	protected void keyReleased  (int which) {}
	protected void mouseDragged (int which, int x, int y) {}
	protected void mousePressed (int which, int x, int y) {}
	protected void mouseReleased(int which, int x, int y) {}

	
	/**
	 * Default constructor.
	 * 
	 * @param parent  the applet this widget belongs to
	 * @param x1      x-coordinate of the upper-left corner
	 * @param y1      y-coordinate of the upper-left corner
	 * @param x2      x-coordinate of the lower-right corner
	 * @param y2      y-coordinate of the lower-right corner
	 */
	public Widget(KlatschGui parent, int x1, int y1, int x2, int y2)
	{
		this.parent = parent;
		this.x1     = x1;
		this.y1     = y1;
		this.x2     = x2;
		this.y2     = y2;
	}
	
	
	/**
	 * Clears the rectangle belonging to the widget.
	 */
	protected void clear()
	{
		parent.fill(0);
		parent.noStroke();
		parent.rect(x1, y1, x2 - x1 + 1, y2 - y1 + 1);
	}

	
	/**
	 * Returns true if the given position is in the widget.
	 * 
	 * @param x  x-coordinate to test
	 * @param y  y-coordinate to test
	 * @return truth value of containment
	 */
	protected boolean in(int x, int y)
	{
		return ((x >= x1) && (x <= x2) && (y >= y1) && (y <= y2));
	}
}
