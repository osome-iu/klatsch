package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.lang.VString;
import edu.iu.cnets.klatsch.misc.Utility;


/**
 * This expression is a dictionary literal { k0:v0, ... }.
 */
public class EDictionary extends Expression
{
	Pair[] pairList;
		
		
	public EDictionary(Pair[] pairList)
	{
		this.pairList = pairList;
	}
		
		
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		VDictionary dict = new VDictionary();
		
		for (Pair p : pairList) {
			VString key = (VString) p.expKey.evaluate(rt).requireType(VString.class);
			dict.set(key, p.expVal.evaluate(rt));
		}
		
		return dict;
	}
		
		
	public String toString()
	{
		return "{" + Utility.listString(pairList) + "}";
	}
}
