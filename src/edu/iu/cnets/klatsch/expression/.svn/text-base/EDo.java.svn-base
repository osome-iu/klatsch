package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VNull;


/**
 * This class is used to represent DO/WHILE loops.
 */
public class EDo extends Expression
{
	Expression expTest;  // the test expression
	Expression expBody;  // the body expression
	
	
	public EDo(Expression expTest, Expression expBody)
	{
		this.expTest = expTest;
		this.expBody = expBody;
	}

	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value val = VNull.NULL;
	
		do {
			val = expBody.evaluate(rt);
		}	while (((VBoolean) expTest.evaluate(rt).requireType(VBoolean.class)).val);
		
		return val;
	}
	
	
	public String toString()
	{
		return "DO " + expBody.toString() + " WHILE " + expTest.toString();
	}
}
