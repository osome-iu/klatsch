package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VClosure;
import edu.iu.cnets.klatsch.misc.Utility;


/**
 * This class is used to represent procedure definitions, which evaluate to closures.
 */
public class EProcedure extends Expression
{
	String[]   idList;   // the list of formal parameters
	Expression expBody;  // the body expression
	
	
	public EProcedure(String[] idList, Expression expBody)
	{
		this.idList  = idList;
		this.expBody = expBody;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		return new VClosure(idList, expBody, rt);
	}
	
	
	public String toString()
	{
		return "PROC (" + Utility.listString(idList) + ") " + expBody.toString();
	}
}
