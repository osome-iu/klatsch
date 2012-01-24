package edu.iu.cnets.klatsch.gui;


/**
 * The index widget shows us a horizontal list of the current panes.
 */
class WidgetIndex extends Widget
{
	/**
	 * Initializes a new index widget.
	 */
	WidgetIndex(KlatschGui parent, int x1, int y1, int x2, int y2)
	{
		super(parent, x1, y1, x2, y2);
	}
	
	
	/**
	 * Redraws the list of panes.
	 */
	public void draw()
	{
		int x = 3, i = 0;

		for (Pane pane : parent.paneList) {
			parent.fill((i++ == parent.topIndex) ? 0xffccccff : 0xff4444aa);
			String str = Integer.toString(pane.id) + ":" + pane.name;
			parent.text(str, x, y2 - 3);
			x += parent.textWidth(str) + 7;
		}
	}
}
