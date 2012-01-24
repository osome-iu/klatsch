package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VList;
import edu.iu.cnets.klatsch.lang.VNull;
import edu.iu.cnets.klatsch.lang.VStream;
import edu.iu.cnets.klatsch.stream.SList;
import edu.iu.cnets.klatsch.stream.Stream;


/**
 * This class is used to represent FOR/IN loops.
 */
public class EFor extends Expression
{
	String     id;         // the variable to hold the values
	Expression expStream;  // the stream providing the values
	Expression expBody;    // the body of the loop

	
	public EFor(String id, Expression expStream, Expression expBody)
	{
		this.id        = id;
		this.expStream = expStream;
		this.expBody   = expBody;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value seq = expStream.evaluate(rt);
		
		if (seq instanceof VList)
			seq = new VStream(new SList((VList) seq));
		
		Stream  s     = ((VStream) seq.requireType(VStream.class)).s;
		Runtime rtNew = rt.extend(id, VNull.NULL);
		Value   val   = VNull.NULL;
		
		while (!s.done()) {
			rtNew.set(id, s.get());
			val = expBody.evaluate(rtNew);
		}
			
		return val;
	}
	
	
	public String toString()
	{
		return "FOR " + id + " IN " + expStream.toString() + " " + expBody.toString();
	}
}
