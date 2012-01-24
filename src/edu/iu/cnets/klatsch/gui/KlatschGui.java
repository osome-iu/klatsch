package edu.iu.cnets.klatsch.gui;

import java.util.LinkedList;
import java.util.List;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.pane.Text;

import processing.core.*;


/**
 * The parts of the {@link Main} class that interfaced with the Processing library
 * now live here instead.
 */
public class KlatschGui extends PApplet
{
	/** for serialization */
	private static final long serialVersionUID = 5405630614090627203L;

	/** the widgets we always have */
	WidgetIndex widgetIndex;
	WidgetEntry widgetEntry;
	Text        paneText;
	
	/** for keeping track of the main pane */
	List<Pane>  paneList;
	int         topIndex;
	
	/** the boundaries to use for a new pane */
	int         paneX1, paneY1, paneX2, paneY2;
	

	public static void initialize()
	{
		KlatschGui.main(new String[] { "edu.iu.cnets.klatsch.gui.KlatschGui" });
	}
	
	
	public KlatschGui()
	{
		Main.gui = this;
	}
	
	
	/**
	 * Boot up by setting Processing parameters and making ourselves available to other classes.
	 */
	public void setup()
	{
		size(800, 600);
		textFont(loadFont("consoleFont.vlw"));
		smooth();

		paneX1 = 0;          paneY1 = 16;
		paneX2 = width - 1;  paneY2 = height - 16;
		
		widgetIndex = new WidgetIndex(this, 0,      0,           width - 1, 16);
		widgetEntry = new WidgetEntry(this, 0,      height - 16, width - 1, height - 1);
		
		paneList = new LinkedList<Pane>();
		topIndex = 0;

		try {
			paneText = new Text();
		} catch (EvaluationException e) {}
		
		Main.rt = new Runtime();
		Main.rt.addLibrary();
	}

	
	/**
	 * Updates the screen.
	 */
	public void draw()
	{
		background(0);

		widgetIndex.draw();
		widgetEntry.draw();
		
		try {
			paneList.get(topIndex).draw();
		} catch (EvaluationException e) {
			try {
				paneText.prop_println(new VString(e.toString()));
			} catch (EvaluationException ee) {}
		}
	}


	/**
	 * Delivers keystrokes.
	 */
	public void keyPressed()
	{
		if (key == TAB) {
			topIndex = (topIndex + 1) % paneList.size();
	    return;
		}
		
		widgetEntry.keyPressed(key);
	}
	
	
	/**
	 * Adds a new pane to the top display.
	 * 
	 * @param pane  the pane to add
	 */
	public void add(Pane pane)
	{
		paneList.add(pane);
		topIndex = paneList.size() - 1;
	}
	
	
	/**
	 * Directs text to the current output pane.
	 * 
	 * @param text  the text to print
	 */
	public void write(String text)
	{
		try {
			paneText.prop_print(new VString(text));
		} catch (EvaluationException e) {}
	}

	
	/**
	 * Directs a newline to the current output pane.
	 */
	public void writeln()
	{
		try {
			paneText.prop_println();
		} catch (EvaluationException e) {}
	}
	
	
	/**
	 * Directs text plus a newline to the current output pane.
	 * 
	 * @param text  the text to print
	 */
	public void writeln(String text)
	{
		try {
			paneText.prop_println(new VString(text));
		} catch (EvaluationException e) {}
	}
}
