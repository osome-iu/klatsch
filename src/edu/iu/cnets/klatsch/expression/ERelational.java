package edu.iu.cnets.klatsch.expression;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Runtime;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VString;


/**
 * Relational expression.
 */
public class ERelational extends Expression
{
	public enum Type { EQ, NEQ, GEQ, GT, LEQ, LT };
	
	Type       type;
	Expression expA, expB;
	
	
	public ERelational(Type type, Expression expA, Expression expB)
	{
		this.type = type;
		this.expA = expA;
		this.expB = expB;
	}
	
	
	public Value evaluate(Runtime rt)
	throws EvaluationException
	{
		Value valA = expA.evaluate(rt);
		Value valB = expB.evaluate(rt);
		
		if ((valA instanceof VNumber) && (valB instanceof VNumber)) {
			switch (type) {
 		    case EQ:   return (((VNumber) valA).val == ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    case NEQ:  return (((VNumber) valA).val != ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    case GEQ:  return (((VNumber) valA).val >= ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    case GT:   return (((VNumber) valA).val >  ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    case LEQ:  return (((VNumber) valA).val <= ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    case LT:   return (((VNumber) valA).val <  ((VNumber) valB).val) ? VBoolean.T : VBoolean.F;
		    default:
		    	throw new EvaluationException("internal error");
			}
		} else if ((valA instanceof VString) && (valB instanceof VString)) {
			switch (type) {	
		 	  case EQ:   return (((VString) valA).val.compareTo(((VString) valB).val) == 0) ? VBoolean.T : VBoolean.F;
			  case NEQ:  return (((VString) valA).val.compareTo(((VString) valB).val) != 0) ? VBoolean.T : VBoolean.F;
			  case GEQ:  return (((VString) valA).val.compareTo(((VString) valB).val) >= 0) ? VBoolean.T : VBoolean.F;
			  case GT:   return (((VString) valA).val.compareTo(((VString) valB).val) >  0) ? VBoolean.T : VBoolean.F;
			  case LEQ:  return (((VString) valA).val.compareTo(((VString) valB).val) <= 0) ? VBoolean.T : VBoolean.F;
			  case LT:   return (((VString) valA).val.compareTo(((VString) valB).val) <  0) ? VBoolean.T : VBoolean.F;
		    default:
		    	throw new EvaluationException("internal error");
			}
		} else
			throw new EvaluationException("type mismatch");
	}
	
	
	public String toString()
	{
		switch (type) {
 	  	case EQ:   return expA.toString() + " == " + expB.toString();
 		  case NEQ:  return expA.toString() + " != " + expB.toString();
 		  case GEQ:  return expA.toString() + " >= " + expB.toString();
 		  case GT:   return expA.toString() + " > "  + expB.toString();
 		  case LEQ:  return expA.toString() + " <= " + expB.toString();
 		  case LT:   return expA.toString() + " < "  + expB.toString();
 		  default:   return "internal error";
		}
	}
}
