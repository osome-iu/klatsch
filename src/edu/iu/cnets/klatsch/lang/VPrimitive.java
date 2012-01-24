package edu.iu.cnets.klatsch.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Stack;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.exception.ParserException;
import edu.iu.cnets.klatsch.expression.Expression;
import edu.iu.cnets.klatsch.gui.Pane;
import edu.iu.cnets.klatsch.misc.Utility;
import edu.iu.cnets.klatsch.model.Feed;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.model.gephi.GephiEdge;
import edu.iu.cnets.klatsch.model.gephi.GephiGraph;
import edu.iu.cnets.klatsch.model.gephi.GephiNode;
import edu.iu.cnets.klatsch.parser.Parser;
import edu.iu.cnets.klatsch.parser.TokenStream;
import edu.iu.cnets.klatsch.stream.SRange;


/**
 * This class encapsulates primitive functions.
 * 
 * The exact mechanism is a little strange, so read this carefully!  To create a new primitive, all you
 * need to do is declare a new method "Value prim_NAME(Value[] args)", and the {@link Runtime} class
 * will automatically take care of adding it to the base environment using reflection.  It does that by
 * creating an instance of VPrimitive for each primitive that's set up to call that particular primitive.
 * That's the only place this class ever needs to be instantiated.
 */
public class VPrimitive extends Value implements Callable
{
	/** the name of the primitive */
	String name;
	
	/** the method to invoke */
	Method prim;
	
	
	/**
	 * Create a new primitive wrapper for a primitive that has the given name and is implemented by the
	 * given method.
	 * 
	 * @param name  the primitive name
	 * @param prim  the primitive implementation
	 */
	public VPrimitive(String name, Method prim)
	{
		this.name = name;
		this.prim = prim;
	}
	
	
	/**
	 * Invoke the primitive for which we were instantiated.
	 * 
	 * @param args  the arguments to pass in
	 * @return the return value
	 * @throws EvaluationException for any runtime errors
	 */
	public Value apply(Value ...args)
	throws EvaluationException
	{
		try {
			return (Value) prim.invoke(this, (Object) args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw (EvaluationException) e.getCause();
		} catch (IllegalAccessException e) {
			throw (EvaluationException) e.getCause();
		}
	}


	/**
	 * Returns a string representation of the primitive.
	 * 
	 * @return the string
	 */
	public String toString()
	{
		return "Primitive<" + name + ">";
	}

	
	/*===================================================================
	 * ONLY PRIMITIVE DEFINITIONS LIVE BELOW THIS POINT.
	 *===================================================================*/
	
	/**
	 * abs(n) : Returns the absolute value of n.
	 */
	public Value prim_abs(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.abs(val));
	}
	
	
	/**
	 * acos(n) : Returns the arc cosine (in radians) of n.
	 */
	public Value prim_acos(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).requireRange(-1, 1).val;
		
		return new VNumber(Math.acos(val));
	}

	
	/**
	 * anode(n) : Creates a new actor node with ID n. 
	 */
	public Value prim_anode(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Integer.MAX_VALUE).val; 
		
		return new VNode(new GephiNode(Node.TYPE_ACTOR, id));
	}
	

	/**
	 * asin(n) : Returns the arc sine (in radians) of n. 
	 */
	public Value prim_asin(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).requireRange(-1, 1).val;
		
		return new VNumber(Math.asin(val));
	}
	
	
	/**
	 * atan(n) : Returns the arc tangent (in radians) of n.
	 * Note: atan2(y, x) is better and you should really use it instead. 
	 */
	public Value prim_atan(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.atan(val));
	}
	
	
	/**
	 * atan2(y, x) : Returns the arc tangent (in radians) of the value y/x.
	 */
	public Value prim_atan2(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		double y = ((VNumber) args[0].requireType(VNumber.class)).val;
		double x = ((VNumber) args[1].requireType(VNumber.class)).val;
		
		return new VNumber(Math.atan2(y, x));
	}
	
	
	/**
	 * bench(p) : Evaluates the given thunk for timing information.
	 */
	public Value prim_bench(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		VClosure proc = (VClosure) args[0].requireType(VClosure.class);
		
		long  start  = System.currentTimeMillis();
		Value result = proc.apply();
		long  stop   = System.currentTimeMillis();
		
		return new VList(new VNumber((stop - start) / 1000.0), result);
	}

	
	/**
	 * ceil(n) : Returns the ceiling of n.
	 */
	public Value prim_ceil(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.ceil(val));
	}
	

	/**
	 * close(i) : Closes the widget with ID i.
	 */
	/*
	public Value prim_close(Value[] args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
					
		Iterator<Widget> iter = KlatschGui.widgetList.iterator();
		while (iter.hasNext()) {
			Widget widget = iter.next();
			if (widget.id == id) {
				if (KlatschGui.widgetList.get(KlatschGui.topIndex).id == id)
					KlatschGui.topIndex--;
				iter.remove();
				break;
			}
		}
			
		if (KlatschGui.topIndex >= KlatschGui.widgetList.size())
			KlatschGui.topIndex--;
			
		return VNull.NULL;
	}
	*/

	
	/**
	 * cos(n) : Returns the cosine of n, with n in radians.
	 */
	public Value prim_cos(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double theta = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.cos(theta));
	}
	
	
	/**
	 * edge(n1, n2)             : Creates a new edge from node n1 to node n2 (when = now, weight = 1).
	 * edge(n1, n2, w)          : Additionally specifies a weight.
	 * edge(n1, n2, w, t)       : Additionally specifies a weight and trusted weight.
	 * edge(n1, n2, s, e, w, t) : Additionally specifies start and end times.
	 */
	public Value prim_edge(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 6);
		GephiNode src = (GephiNode) ((VNode) args[0].requireType(VNode.class)).n;
		GephiNode dst = (GephiNode) ((VNode) args[1].requireType(VNode.class)).n;
		
		if (args.length == 3) {
			double w = ((VNumber) args[2].requireType(VNumber.class)).val;
			return new VEdge(new GephiEdge(src, dst, w, w));
		
	  } else if (args.length == 4) {
	  	double w = ((VNumber) args[2].requireType(VNumber.class)).val;
	  	double t = ((VNumber) args[3].requireType(VNumber.class)).val;
	  	return new VEdge(new GephiEdge(src, dst, w, t));
	  	
	  } else if (args.length == 6) {
			int    s = (int) ((VNumber) args[2].requireType(VNumber.class)).val;
			int    e = (int) ((VNumber) args[3].requireType(VNumber.class)).val;
			double w =       ((VNumber) args[4].requireType(VNumber.class)).val;
			double t =       ((VNumber) args[5].requireType(VNumber.class)).val;
			return new VEdge(new GephiEdge(src, dst, s, e, w, t));
		}
		
		return new VEdge(new GephiEdge(src, dst));
	}
	
	
	/**
	 * exec(s) : Executes a script in the current environment.
	 */
	public Value prim_exec(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		String path = ((VString) args[0].requireType(VString.class)).val;
		
		try {
			
			// retrieve the source string
			String script = Utility.readFile(path);
	
			// parse and execute it
			//System.err.println(script.toString());
			Expression[] expList = new Parser(new TokenStream(script.toString())).read();
			for (Expression e : expList)
				e.evaluate(Main.rt);
			
			// all done
			return VNull.NULL;
			
		} catch (ParserException e) {
			throw new EvaluationException("Error in '" + path + "': " + e);
		}
	}
	
	
	/**
	 * exit() : Exits the Klatsch interpreter.
	 */
	public Value prim_exit(Value ... args)
	throws EvaluationException
	{
		System.exit(0);
		return null;
	}
	
	
	/**
	 * exp(n) : Returns e^n.
	 */
	public Value prim_exp(Value[] args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.exp(val));
	}
	
	
	/**
	 * feed(s)       : Returns a new feed of type s, using default values.
	 * feed(s, dict) : Returns a new feed of type s, using the given configuration dictionary.
	 * This translates to calling a constructor for edu.iu.cnets.klatsch.feed.[s] with dict as a parameter.
	 */
	public Value prim_feed(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 2);
		String      name = ((VString) args[0].requireType(VString.class)).val;
		VDictionary dict = (args.length == 2) ? (VDictionary) args[1].requireType(VDictionary.class) : new VDictionary();
		
		try {
			
			// get the constructor types and arguments ready to go
			Class<?>[] constructorTypes = { Value[].class };
			Object[]   constructorArgs  = { new Value[] { dict } };
			
			// find the constructor
			Class<?>       feedClass = Class.forName("edu.iu.cnets.klatsch.feed." + name);
			Constructor<?> feedMaker = feedClass.getConstructor(constructorTypes);
			
			// and make a new feed
			return new VFeed((Feed) feedMaker.newInstance(constructorArgs));
			
		} catch (ClassNotFoundException e) {
			throw new EvaluationException("Unknown feed type '" + name + "'");
		} catch (Exception e) {
			System.err.println(e);
			throw new EvaluationException("Internal error for feed type '" + name + "'");
		}
	}
	
	
	/**
	 * floor(n) : Returns the floor of n.
	 */
	public Value prim_floor(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.floor(val));
	}
	

	/**
	 * graph()  : Creates a new graph.
	 * graph(s) : Imports a graph from the file s. 
	 */
	public Value prim_graph(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		if (args.length == 1)
			return new VGraph(new GephiGraph(((VString) args[0].requireType(VString.class)).val));
		else
			return new VGraph(new GephiGraph());
	}

	
	/**
	 * grapher(n, p) : Creates a new Erdos-Renyi random graph with n nodes and edge probability p. 
	 */
	public Value prim_grapher(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		int    n = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Integer.MAX_VALUE).val;
		double p =       ((VNumber) args[1].requireType(VNumber.class)).requireRange(0, 1).val;

		GephiGraph g = new GephiGraph();
			
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				if (Math.random() <= p)
					g.add(new GephiEdge(new GephiNode(Node.TYPE_ACTOR, i), new GephiNode(Node.TYPE_ACTOR, j)));

		return new VGraph(g);
	}

	
	/**
	 * json(v) : Returns a JSON-parsable version of the given value.
	 */
	public Value prim_json(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		
		return new VString(args[0].toJson());
	}
	
	
	/**
	 * list(n) : Returns a list pre-allocated to have n slots.
	 */
	public Value prim_list(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int n = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Integer.MAX_VALUE).val;
		
		Value[] ls = new Value[n];
		for (int i = 0; i < n; ++i)
			ls[i] = VNull.NULL;
		return new VList(ls);
	}

	
	/**
	 * log(n) : Returns log base e of n.
	 */
	public Value prim_log(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Double.MAX_VALUE).val;
		
		return new VNumber(Math.log(val));
	}
	
	
	/**
	 * log10(n) : Returns log base 10 of n.
	 */
	public Value prim_log10(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Double.MAX_VALUE).val;
		
		return new VNumber(Math.log10(val));
	}

	
	/**
	 * mnode(n) : Creates a new meme node with ID n. 
	 */
	public Value prim_mnode(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int id = (int) ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Integer.MAX_VALUE).val; 
		
		return new VNode(new GephiNode(Node.TYPE_MEME, id));
	}
	
	
	/**
	 * pane(s)       : Returns a new pane of type s, using default values.
	 * pane(s, dict) : Returns a new pane of type s, using the given configuration dictionary.
	 * This translates to calling a constructor for edu.iu.cnets.klatsch.pane.[s] with dict as a parameter.
	 */
	public Value prim_pane(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 2);
		String      name = ((VString) args[0].requireType(VString.class)).val;
		VDictionary dict = (args.length == 2) ? (VDictionary) args[1].requireType(VDictionary.class) : new VDictionary();
		
		try {
			
			// get the constructor types and arguments ready to go
			Class<?>[] constructorTypes = { Value[].class };
			Object[]   constructorArgs  = { new Value[] { dict } };
			
			// find the constructor
			Class<?>       paneClass = Class.forName("edu.iu.cnets.klatsch.pane." + name);
			Constructor<?> paneMaker = paneClass.getConstructor(constructorTypes);
			
			// and make a new pane
			return new VPane((Pane) paneMaker.newInstance(constructorArgs));
			
		} catch (ClassNotFoundException e) {
			throw new EvaluationException("Unknown pane type '" + name + "'");
		} catch (Exception e) {
			System.err.println(e);
			throw new EvaluationException("Internal error for pane type '" + name + "'");
		}
	}

	
	/**
	 * print(v1, ...) : Prints v1, ... to the console.
	 */
	public Value prim_print(Value ... args)
	throws EvaluationException
	{
		for (Value v : args)
			Main.self.write(v.toString());
		return VNull.NULL;
	}

	
	/**
	 * print_err(v1, ...) : Prints v1, ... to the error console.
	 */
	public Value prim_print_err(Value ... args)
	throws EvaluationException
	{
		for (Value v : args)
			Main.self.write_err(v.toString());
		return VNull.NULL;
	}

	
	/**
	 * println(v1, ...) : Prints v1, ..., and a newline to the console.
	 */
	public Value prim_println(Value ... args)
	throws EvaluationException
	{
		for (Value v : args)
			Main.self.write(v.toString());
		Main.self.writeln();
		return VNull.NULL;
	}

	
	/**
	 * println_err(v1, ...) : Prints v1, ..., and a newline to the error console.
	 */
	public Value prim_println_err(Value ... args)
	throws EvaluationException
	{
		for (Value v : args)
			Main.self.write_err(v.toString());
		Main.self.writeln_err();
		return VNull.NULL;
	}
	
	
	/**
	 * queue() : Returns a new, empty queue.
	 */
	public Value prim_queue(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VQueue(new LinkedList<Value>());
	}

	
	/**
	 * random()  : Returns a random number between 0 and 1.
	 * random(n) : Returns a random number between 0 and n.
	 */
	public Value prim_random(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		double val = (args.length == 1) ? ((VNumber) args[0].requireType(VNumber.class)).val : 1;
		
		return new VNumber(Math.random() * val);
	}
	
	
	/**
	 * range(end)              : Returns a stream ranging from 1 to end, inclusive
	 * range(start, end)       : Returns a stream ranging from start to end, inclusive
	 * range(start, end, step) : Returns a stream ranging from start to end, stepping by step.
	 */
	public Value prim_range(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 3);
		double start = (args.length >= 2) ? ((VNumber) args[0].requireType(VNumber.class)).val : 1.0;
		double end   = ((VNumber) args[(args.length == 1) ? 0 : 1].requireType(VNumber.class)).val;
		double step  = (args.length == 3) ? ((VNumber) args[2].requireType(VNumber.class)).val : 1.0;
		
		return new VStream(new SRange(start, end, step));
	}
	
	
	/**
	 * round(n) : Returns the result of rounding n to the nearest integer.
	 */
	public Value prim_round(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.rint(val));
	}
	
	
	/**
	 * sign(n) : Returns the sign of n (-1, 0, or 1).
	 */
	public Value prim_sign(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.signum(val));
	}
	
	
	/**
	 * sin(n) : Returns the sine of n, with n in radians.
	 */
	public Value prim_sin(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double theta = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.sin(theta));
	}
	
	
	/**
	 * sqrt(n) : Returns the square root of n.
	 */
	public Value prim_sqrt(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double val = ((VNumber) args[0].requireType(VNumber.class)).requireRange(0, Double.MAX_VALUE).val;
		
		return new VNumber(Math.sqrt(val));
	}
	
	
	/**
	 * stack() : Returns a new, empty stack.
	 */
	public Value prim_stack(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStack(new Stack<Value>());
	}

	
	/**
	 * tan(n) : Returns the tangent of n, with n in radians. 
	 */
	public Value prim_tan(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		double theta = ((VNumber) args[0].requireType(VNumber.class)).val;
		
		return new VNumber(Math.tan(theta));
	}
	
	
	/**
	 * test() : General-purpose testing primitive for the feature du jour.
	 */
	public Value prim_test(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		return VNull.NULL;
	}
}
