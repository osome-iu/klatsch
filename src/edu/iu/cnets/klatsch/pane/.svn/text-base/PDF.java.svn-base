package edu.iu.cnets.klatsch.pane;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.gui.Pane;
import edu.iu.cnets.klatsch.lang.VNull;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.lang.VList;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VStream;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.stream.Stream;


/**
 * The PDF pane displays a probability density function for a list of numeric values.
 */
public class PDF extends Pane
{
	/** enumerated type for the possible axis scales */
	private enum Scale {
		LOG    ("log"),
		LINEAR ("linear");
		
		private static final Map<String,Scale> lookup = new HashMap<String,Scale>();
		
		static {
			for (Scale s : EnumSet.allOf(Scale.class))
				lookup.put(s.text, s); 
		}
		
		private String text;
		
		private Scale(String text)
		{
			this.text = text;
		}

		public static Scale get(String text) {
			return lookup.get(text);
		}
	}

	/** hardwired defaults for the pane configuration */
	private static final String DEFAULT_NAME   = "PDF";
	private static final String DEFAULT_TITLE  = "PDF";
	private static final Scale  DEFAULT_XSCALE = Scale.LOG;
	private static final Scale  DEFAULT_YSCALE = Scale.LOG;
	
	/** the data we're working with */
	VList  data;
	
	/** the graph title */
	String title;
	
	/** the scale type for the x-axis */
	Scale  xScale;

	/** the scale type for the y-axis */
	Scale  yScale;
	
	/** the minimum value for the x-axis */
	double xMin;
	
	/** the maximum value for the x-axis */
	double xMax;
	
	/** the minimum value for the y-axis */
	double yMin;
	
	/** the maximum value for the y-axis */
	double yMax;

	
	/**
	 * Creates a new PDF pane.  The single parameter, if present, should be a dictionary containing
	 * overrides for the default parameters, which are listed below.
	 * 
	 *   data   (List)    [data points,     default is NULL]
	 *   name   (String)  [pane name,       default is "PDF"]
	 *   title  (String)  [graph title,     default is "PDF"]
	 *   xscale (String)  [mode for x-axis, default is "log"]
	 *   yscale (String)  [mode for y-axis, default is "log"]
	 */
	public PDF(Value ... ls)
	throws EvaluationException
	{
		super();
		
		name   = DEFAULT_NAME;
		data   = null;
		title  = DEFAULT_TITLE;
		xScale = DEFAULT_XSCALE;
		yScale = DEFAULT_YSCALE;

		if (ls.length > 0) {
			VDictionary dict = (VDictionary) ls[0].requireType(VDictionary.class);
			if (((VBoolean) dict.method_has(new VString("data")))  .val)  data   =            (VList)   dict.method_get(new VString("data"));
			if (((VBoolean) dict.method_has(new VString("name")))  .val)  name   =           ((VString) dict.method_get(new VString("name")))  .val;
			if (((VBoolean) dict.method_has(new VString("title"))) .val)  title  =           ((VString) dict.method_get(new VString("title"))) .val;
			if (((VBoolean) dict.method_has(new VString("xscale"))).val)  xScale = Scale.get(((VString) dict.method_get(new VString("xscale"))).val);
			if (((VBoolean) dict.method_has(new VString("yscale"))).val)  yScale = Scale.get(((VString) dict.method_get(new VString("yscale"))).val);
		}
	}
	
	
	/**
	 * Draws the current buffer.
	 */
	protected void draw()
	throws EvaluationException
	{
		clear();
		
		parent.stroke(0xff808080);
		parent.strokeWeight(2);
		parent.line(x1 + 15, y2 - 15, x2 -  5, y2 - 15);
		parent.line(x1 + 15, y2 - 15, x1 + 15, y1 +  5);
		
		double xMin = computeMin(((VNumber) data.method_min()).val, xScale);
		double xMax = computeMax(((VNumber) data.method_max()).val, yScale);

		int      bins  = 50;
		double[] tally = new double[bins];
		
		for (Value v : data.ls) {
			double val = ((VNumber) v).val;
			int bin = 0;
			switch (xScale) {
				case LINEAR: bin = (int) Math.round(bins * (         val -  xMin) / (xMax - xMin));  break;
				case LOG:    bin = (int) Math.round(bins * (Math.log(val) - xMin) / (xMax - xMin));  break;
			}
			
			System.err.format("min = %f, max = %f, data = %f, bin = %d\n", xMin, xMax, val, bin);
		}
	}
	
	
	/**
	 * Given a minimum value, returns an appropriate lower bound for an axis.
	 * Right now, this is the next lowest tenth of a power of 10.
	 * 
	 * @param minValue  the minimum value
	 * @param scale     the axis scale type
	 * @return the lower bound for an axis
	 */
	protected double computeMin(double minValue, Scale scale)
	{
		double val = 0;
		
		switch (scale) {
			case LINEAR:  val = Math.pow(10, Math.floor(Math.log10(minValue) * 10) / 10);  break;
			case LOG:     val =              Math.floor(Math.log10(minValue) * 10) / 10;   break;
		}
		
		return Double.isInfinite(val) ? 0 : val;
	}


	/**
	 * Given a maximum value, returns an appropriate upper bound for an axis.
	 * Right now, this is the next highest tenth of a power of 10.
	 * 
	 * @param maxValue  the maximum value
	 * @param scale     the axis scale type
	 * @return the upper bound for an axis 
	 */
	protected double computeMax(double maxValue, Scale scale)
	{
		double val = 0;
		
		switch (scale) {
			case LINEAR:  val = Math.pow(10, Math.ceil(Math.log10(maxValue) * 10) / 10);  break;
			case LOG:     val =              Math.ceil(Math.log10(maxValue) * 10) / 10;   break;
		}
		
		return Double.isInfinite(val) ? 0 : val;
	}
}
