package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VNull;


/**
 * This class is used to represent WHILE loops.
 */
public class EWhile extends Expression
{
	Expression expTest;  // the test expression
	Expression expBody;  // the body expression
	
	
	public EWhile(Expression expTest, Expression expBody)
	{
		this.expTest = expTest;
		this.expBody = expBody;
	}

	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value val = VNull.NULL;
		
		while (((VBoolean) expTest.evaluate(rt).requireType(VBoolean.class)).val)
			val = expBody.evaluate(rt);
		
		return val;
	}
	
	
	public String toString()
	{
		return "WHILE " + expTest.toString() + " " + expBody.toString();
	}
}
