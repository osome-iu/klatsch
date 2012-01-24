/**
 * This is the main part of the Klatsch application, which manages the runtime environment
 * and other top-level aspects of the interpreter.
 */

package edu.iu.cnets.klatsch;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.exception.ParserException;
import edu.iu.cnets.klatsch.expression.Expression;
import edu.iu.cnets.klatsch.gui.KlatschGui;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.VPrimitive;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VList;
import edu.iu.cnets.klatsch.lang.VNull;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.misc.LineEditor;
import edu.iu.cnets.klatsch.parser.Parser;
import edu.iu.cnets.klatsch.parser.TokenStream;
import edu.iu.cnets.klatsch.registry.Registry;


/**
 * This is the main class.  It used to extend PApplet, but the GUI has been made strictly optional
 * so that Klatsch can run headless.
 */
public class Main
{
	/** for serialization */
	private static final long serialVersionUID = -7275707651011570274L;
	
	/** a link to ourselves */
	public static Main        self;
	
	/** our current runtime environment */
	public static Runtime     rt;
	
	/** the GUI, if activated */
	public static KlatschGui  gui = null;
	
	/** the configuration registry */
	public static Registry    registry;
		
	
	public static void main(String[] args)
	{
		// initialize the registry
		registry = new Registry(System.getenv("KLATSCH") + File.separator + "etc" + File.separator + "registry.xml");
		
		// dispatch to the GUI if requested
		if ((args.length > 0) && args[0].equals("--gui")) {
			System.err.println("Initializing Klatsch in graphical mode.");
			self = new Main();
			KlatschGui.initialize();

	  // otherwise, no GUI
		} else {
			
			// execute a script
			if ((args.length > 0) && !args[0].startsWith("--")) {

				System.err.println("Executing script " + args[0]);
				self = new Main();
				rt   = new Runtime();
				rt.addLibrary();

				try {
					
					// set up the command-line arguments
					VString[] vArgs = new VString[args.length];
					for (int i = 0; i < args.length; ++i)
						vArgs[i] = new VString(args[i]);
					rt.set("args", new VList(vArgs));
					
					// execute the string
					VPrimitive exec = (VPrimitive) rt.get("exec");
					exec.apply(new VString(args[0]));

				} catch (EvaluationException e) {
					System.err.println(e);
					e.printStackTrace();
				}
				
		  // or enter the read-eval-print loop
			} else {
				
				System.err.println("Initializing Klatsch in interactive mode.");
				self = new Main();
				rt   = new Runtime();
				rt.addLibrary();

				boolean    noEscape = (args.length > 0) && args[0].equals("--dumb");
				LineEditor editor   = new LineEditor(new InputStreamReader(System.in), "%d> ", noEscape);

				// the actual REPL
				try {
					
					// repeat until heat death of universe
					while (true) {

						// read in a full line of input
						String buffer = editor.readLine(); 

						// process that line
						self.process(buffer.toString());
					}

				} catch (IOException e) {
					System.err.println("I/O Error: " + e);
				}
			}
		}
	}
				
	
	/**
	 * Processes a new line of input.
	 */
	public void process(String buffer)
	{
		// echo the input for the GUI
		if (gui != null) {
			gui.writeln();
			gui.writeln("> " + buffer);
		}
		
		// build a parser around the input
		if (!buffer.endsWith(";"))
			buffer += ';';
		Parser parser = new Parser(new TokenStream(buffer));
		
		// try to read and evaluate a program
		try {

			Expression[] program = parser.read();
			Value        value   = null;
			for (Expression e : program)
				value = e.evaluate(rt);
			
			if (!(value instanceof VNull))
				writeln(value.toString());

		} catch (ParserException e) {
			writeln(e.toString());
		} catch (EvaluationException e) {
			writeln(e.toString());
		}
	}
	
	
	/**
	 * Prints text to either standard output or the graphical console, depending on whether
	 * the GUI is running.
	 * 
	 * This is named "write" rather than "print" to avoid overloading routines in the Processing
	 * core library.
	 * 
	 * @param text  the text to print
	 */
	public void write(String ... text)
	{
		for (String s : text)
			if (gui != null)
				gui.write(s);
			else
				System.out.print(s);
	}
	
	
	/**
	 * Like write(), but for standard error rather than standard output.
	 * 
	 * @param text  the text to print
	 */
	public void write_err(String ... text)
	{
		for (String s : text)
			if (gui != null)
				gui.write(s);
			else
				System.err.print(s);
	}
	
	
	/**
	 * Prints text followed by a line break to either standard output or the graphical console,
	 * depending on whether the GUI is running.
	 * 
	 * This is named "writeln" rather than "println" to avoid overloading routines in the Processing
	 * core library.
	 *
	 * @param text  the text to print
	 */
	public void writeln(String ... text)
	{
		if (gui != null) {
			for (String s : text)
				gui.write(s);
			gui.writeln();
		} else {
			for (String s : text)
				System.out.print(s);
			System.out.println();
		}
	}

	
	/**
	 * Like writeln(), but for standard error rather than standard output.
	 *
	 * @param text  the text to print
	 */
	public void writeln_err(String ... text)
	{
		if (gui != null) {
			for (String s : text)
				gui.write(s);
			gui.writeln();
		} else {
			for (String s : text)
				System.err.print(s);
			System.err.println();
		}
	}
}
