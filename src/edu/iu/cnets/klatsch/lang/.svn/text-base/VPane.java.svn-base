package edu.iu.cnets.klatsch.lang;

import java.lang.reflect.Method;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.gui.Pane;


/**
 * This makes {@link Pane} objects in the GUI a first-class value in Klatsch, enabling
 * manipulation, export, and other cool things.  Think of them as being a bit like handles
 * in MATLAB.
 */
public class VPane extends Value
{
	/** the pane we're encapsulating */
	public Pane p;
	
	
	/**
	 * Constructs a new pane.
	 */
	public VPane(Pane p)
	{
		this.p = p;
	}

	
	/**
	 * Construct a printable representation of the pane.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return "Pane<" + p.id() + ":" + p.name() + ">";
	}
	

	/**
	 * id() : Returns the ID of the pane.
	 */
	public Value method_id(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(p.id());
	}
	
	
	/**
	 * name() : Returns the name of the pane.
	 */
	public Value method_name(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VString(p.name());
	}
	
	
	/**
	 * prop(s) : Returns the property with the given name
	 */
	public Value method_prop(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		
		try {
			String name   = ((VString) args[0].requireType(VString.class)).val;
			Method method = p.getClass().getMethod("prop_" + name, Value[].class);
			return new VProperty(p, name, method);
		} catch (NoSuchMethodException e) {
			throw new EvaluationException("unknown property");
		}
	}
}
