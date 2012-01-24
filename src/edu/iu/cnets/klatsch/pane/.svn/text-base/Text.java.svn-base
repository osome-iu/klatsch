package edu.iu.cnets.klatsch.pane;

import java.util.ArrayList;
import java.util.List;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.gui.Pane;
import edu.iu.cnets.klatsch.lang.VNull;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VStream;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.stream.Stream;


/**
 * The text pane simply displays a number of lines of output.
 */
public class Text extends Pane
{
	/** hardwired defaults for the pane configuration */
	private static final int    DEFAULT_LINES = 25;
	private static final String DEFAULT_NAME  = "Console";
	
	/** the stored lines of text */
	List<String> buffer;
	
	/** the maximum number of lines */
	int          lines;
	

	/**
	 * Creates a new text pane.  The single parameter, if present, should be a dictionary containing
	 * overrides for the default parameters, which are listed below.
	 * 
	 *   lines  (Number)  [default is 25, use 0 for no limit]
	 *   name   (String)  [default is "Console"]
	 */
	public Text(Value ... ls)
	throws EvaluationException
	{
		super();
		
		name  = DEFAULT_NAME;
		lines = DEFAULT_LINES;
		
		if (ls.length > 0) {
			VDictionary dict = (VDictionary) ls[0].requireType(VDictionary.class);
			if (((VBoolean) dict.method_has(new VString("lines"))).val)  lines = (int) ((VNumber) dict.method_get(new VString("lines"))).val;
			if (((VBoolean) dict.method_has(new VString("name"))) .val)  name  =       ((VString) dict.method_get(new VString("name"))) .val;
		}

		buffer = new ArrayList<String>(lines);
		buffer.add("");
	}
	
	
	/**
	 * Draws the current buffer.
	 */
	protected void draw()
	throws EvaluationException
	{
		clear();
		
		parent.fill(0xffcccccc);
		for (int i = 0; i < buffer.size(); ++i) {
			int y = y2 - 3 - (i * 16);
			parent.text(buffer.get(buffer.size() - 1 - i), 3, y);
		}
	}
		

	/**
	 * Returns the contents of the buffer as a stream of strings.
	 */
	public Value prop_contents(Value ... args)
	throws EvaluationException
	{
		return new VStream(new Stream() {

			int i = 0;
			
			public Value getNext()
			throws EvaluationException
			{
				if (i >= Text.this.buffer.size())
					throw new EvaluationException("end of buffer");
				else
					return new VString(Text.this.buffer.get(i++));
			}
			
			public boolean done() {
				return (i >= Text.this.buffer.size());
			}
			
			public String toString() {
				return name;
			}
		});
	}
	
	
	/**
	 * Returns the current maximum size of the buffer in lines.
	 * Include a numeric parameter to change the limit.
	 */
	public Value prop_lines(Value ... args)
	throws EvaluationException
	{
		Value.requireCount(args, 0, 1);
		
		if (args.length == 1) {
			lines = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
			if (lines > 0)
				while (buffer.size() > lines)
					buffer.remove(0);
		}
		
		return new VNumber(lines);
	}
	
	
	/**
	 * Writes the given text to the buffer.
	 */
	public Value prop_print(Value ... args)
	throws EvaluationException
	{
		StringBuffer s = new StringBuffer();
		for (Value v : args)
			s.append(v.toString());
		
		buffer.set(buffer.size() - 1, buffer.get(buffer.size() - 1) + s);

		return VNull.NULL;
	}
	
	
	/**
	 * Writes the given text to the buffer and adds a newline.
	 */
	public Value prop_println(Value ... args)
	throws EvaluationException
	{
		prop_print(args);
		
		buffer.add("");
		if ((lines > 0) && (buffer.size() > lines))
			buffer.remove(0);
		
		return VNull.NULL;
	}
}
