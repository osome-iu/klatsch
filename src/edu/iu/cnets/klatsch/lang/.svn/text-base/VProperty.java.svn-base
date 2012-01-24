package edu.iu.cnets.klatsch.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.gui.Pane;


/**
 * This class encapsulates "properties", which are wrappers around methods contained in the subclasses
 * of {@link Pane}.  In order to invoke one of these methods in Klatsch, you get one of these values
 * using the prop() method in {@link VPane}, then invoke it like any other procedure.  The Pane itself
 * is stored in the VProperty object, so you don't need to include it at invocation time.
 * 
 * Exposure of a method as a property in a subclass of Pane is as simple as making sure it has the
 * name "prop_[NAME]".
 */
public class VProperty extends Value implements Callable
{
	/** the pane containing this property */
	Pane   pane;
	
	/** the name of the primitive */
	String name;
	
	/** the method to invoke */
	Method prop;
	
	
	/**
	 * Create a new wrapper for a property for the given pane, name, and method.
	 *
	 * @param pane  the pane containing the property
	 * @param name  the property name
	 * @param prop  the property implementation
	 */
	public VProperty(Pane pane, String name, Method prop)
	{
		this.pane = pane;
		this.name = name;
		this.prop = prop;
	}
	
	
	/**
	 * Invoke the property for which we were instantiated.
	 * 
	 * @param args  the arguments to pass in
	 * @return the return value
	 * @throws EvaluationException for any runtime errors
	 */
	public Value apply(Value ...args)
	throws EvaluationException
	{
		try {
			return (Value) prop.invoke(pane, (Object) args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw (EvaluationException) e.getCause();
		} catch (IllegalAccessException e) {
			throw (EvaluationException) e.getCause();
		}
	}


	/**
	 * Returns a string representation of the property.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return "Property<" + pane.name() + "." + name + ">";
	}
}
