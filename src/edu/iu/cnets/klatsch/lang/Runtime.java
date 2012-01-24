package edu.iu.cnets.klatsch.lang;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.expression.ELValue;


/**
 * This class holds all of the variables for the current runtime environment.  It is simply a mapping from
 * variable names to {@link Value} objects -- the language is dynamically typed, so one mapping works for
 * everything.
 */
public class Runtime
{
	Map<String, Value> env;     // the ID-value pairings for variables
	Runtime            parent;  // the parent environment (if any)
	
	
	/**
	 * Initialize a new top-level environment.
	 */
	public Runtime()
	{
		env = new HashMap<String, Value>();
		
		// add the built-in variables
		set("pi",    new VNumber(Math.PI));
		set("true",  VBoolean.T);
		set("false", VBoolean.F);
		
		// add the primitives
		Method[] methods = VPrimitive.class.getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("prim_")) {
				String name = m.getName().split("_", 2)[1];
				set(name, new VPrimitive(name, m));
				//System.err.println("Registered primitive " + name);
			}
		}
	}
	
	
	/**
	 * Creates a new environment with the given one as parent.  Client classes have no need to use this
	 * constructor -- they should call {link #extend} instead.
	 * 
	 * @param parent  the parent environment
	 */
	Runtime(Runtime parent)
	{
		this.env    = new HashMap<String, Value>();
		this.parent = parent;
	}
	
	
	/**
	 * Extends the environment to include the standard utility functions.  These are necessary to
	 * include features like stream.map(), which are difficult to implement in the interpreter but
	 * easy to implement in Klatsch.
	 */
	public void addLibrary()
	{
		try {
			String path = System.getenv("KLATSCH") + File.separator + "library.klat";
			((VPrimitive) get("exec")).apply(new Value[] { new VString(path) });
		} catch (EvaluationException e) {
			System.err.println("Error in standard library!");
		}
	}
	
	
	/**
	 * Extends this environment with a single ID/value binding.
	 * 
	 * @param id   the ID
	 * @param val  the value
	 */
	public Runtime extend(String id, Value val)
	{
		Runtime newEnv = new Runtime(this);

		newEnv.env.put(id, val);
		
		return newEnv;
	}
	
	
	/**
	 * Extends this environment with multiple ID and value bindings.
	 * 
	 * @param idList   the set of IDs
	 * @param valList  the list of values
	 */
	public Runtime extend(String[] idList, Value[] valList)
	{
		Runtime newEnv = new Runtime(this);
	
		for (int i = 0; i < idList.length; ++i)
			newEnv.env.put(idList[i], valList[i]);
		
		return newEnv;
	}
	
	
	/**
	 * Returns the value of the given variable.
	 * 
	 * @param id  the name of the variable
	 * @return current value
	 * @throws EvaluationException if the variable is undefined
	 */
	public Value get(String id)
	throws EvaluationException
	{
		if (env.containsKey(id))
			return env.get(id);
		else if (parent != null)
			return parent.get(id);
		else
			throw new EvaluationException("undefined variable '" + id + "'");
	}
	
	
	/**
	 * Checks to see if the given variable is defined in this environment.
	 * 
	 * @param id  the name of the variable
	 * @return true if the variable is defined
	 */
	boolean isDefined(String id)
	{
		return (env.containsKey(id) || ((parent != null) && parent.isDefined(id)));
	}

	
	/**
	 * Sets the value of the given variable as specified.  This should be called only by the {@link ELValue} class.
	 * 
	 * @param id     the variable to set
	 * @param value  the value to set it to
	 */
	public void set(String id, Value value)
	{
		if (!env.containsKey(id) && (parent != null) && parent.isDefined(id))
			parent.set(id, value);
		else
			env.put(id, value);
	}
}
