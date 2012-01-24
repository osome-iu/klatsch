package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VNull;


/**
 * This class is used to represent conditional (IF) expressions.
 */
public class EIf extends Expression
{
	Expression expTest;  // the test expression
	Expression expThen;  // the consequent expression
	Expression expElse;  // the alternative expression
	
	
	public EIf(Expression expTest, Expression expThen)
	{
		this.expTest = expTest;
		this.expThen = expThen;
		this.expElse = null;
	}
	
	
	public EIf(Expression expTest, Expression expThen, Expression expElse)
	{
		this.expTest = expTest;
		this.expThen = expThen;
		this.expElse = expElse;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		boolean test = ((VBoolean) expTest.evaluate(rt).requireType(VBoolean.class)).val;
		
		if (test)
			return expThen.evaluate(rt);
		else if (expElse != null)
			return expElse.evaluate(rt);
		else
			return VNull.NULL;
	}
	
	
	public String toString()
	{
		return "IF " + expTest.toString() + " THEN " + expThen.toString() + 
			     ((expElse != null) ? (" ELSE " + expElse.toString()) : "");
	}
}
