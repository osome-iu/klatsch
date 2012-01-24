package edu.iu.cnets.klatsch.gui;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.misc.History;


/**
 * The entry widget accepts user input and delivers complete lines to the Main applet when appropriate.
 */
public class WidgetEntry extends Widget
{
	/** our current input */
	StringBuffer input;
	
	/** our current point within the input */
	int          point;
	
	/** our input history */
	History      history = new History();
	
	
	/**
	 * Initializes a new entry widget.
	 */
	WidgetEntry(KlatschGui parent, int x1, int y1, int x2, int y2)
	{
		super(parent, x1, y1, x2, y2);
		input = new StringBuffer();
		point = 0;
	}
	
	
	/**
	 * Draws the widget.
	 */
	protected void draw()
	{
		parent.stroke(0xff9999cc);
		parent.strokeWeight(2);
		parent.fill(0);
		parent.rect(x1, y1, x2 - x1 + 1, y2 - y1 + 1);
		
		parent.fill(0xffffffff);
		parent.textAlign(KlatschGui.LEFT);
		
		String text = input.toString();
		parent.text(text, x1 + 3, y2 - 3);
		parent.text("_", x1 + parent.textWidth(text.substring(0, point)) + 3, y2 - 3);
	}
	
	
	/**
	 * Receives another keystroke.
	 * 
	 * @param which  the key pressed
	 */
	protected void keyPressed(int which)
	{
		switch (which) {

			// ^A: move to the beginning of the line
			case 0x01:
				point = 0;
				break;
				
			// ^E: move to the end of the line
			case 0x05:
				point = input.length();
				break;
				
			// ^K: erase to the end of the line
			case 0x0b:
				input.delete(point, input.length());
				break;
			
			// delete the last character
			case KlatschGui.BACKSPACE:
				if (point > 0)
					input.deleteCharAt(--point);
				break;
				
			// submit a new line of code
			case KlatschGui.ENTER:
				String line = input.toString();
				input = new StringBuffer();
				point = 0;
				history.add(line);
				history.reset();
				Main.self.process(line);
				
		  // handle special characters
			case KlatschGui.CODED:
				switch (parent.keyCode) {

					case KlatschGui.DOWN:
						history.next();
						input = new StringBuffer(history.get());
						point = input.length();
						break;

					case KlatschGui.LEFT:
						if (point > 0)
							--point;
						break;
						
					case KlatschGui.RIGHT:
						if (point < input.length())
							++point;
						break;
						
					case KlatschGui.UP:
						history.prev();
						input = new StringBuffer(history.get());
						point = input.length();
						break;
				}
				break;
				
			// type a new character
			default:
				if (point == input.length()) {
					input.append((char) which);
					++point;
				} else
					input.insert(point++, (char) which);
				break;
		}
	}
}
