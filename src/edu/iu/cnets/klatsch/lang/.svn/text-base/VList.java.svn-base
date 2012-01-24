package edu.iu.cnets.klatsch.lang;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.misc.Utility;
import edu.iu.cnets.klatsch.stream.SList;


/**
 * This value represents a list (i.e., a one-dimensional array).
 */
public class VList extends Value
{
	/** the values actually being stored */
	public Value[] ls;
	
	
	public VList(Value ... ls)
	{
		this.ls = ls;
	}

	
	/**
	 * Returns a comparator for sorting numeric values, in support of the sort() method.
	 */
	Comparator<Value> getComparator()
	{
		return new Comparator<Value>() {
			public int compare(Value o1, Value o2)
			{
				return Double.compare(((VNumber) o1).val, ((VNumber) o2).val);
			}
		};
	}

	
	/**
	 * Returns a comparator for sorting numeric value using a user-supplied
	 * procedure, in support of the sort(p) method.
	 */
	Comparator<Value> getComparator(final VClosure proc)
	{
		return new Comparator<Value>() {
			public int compare(Value o1, Value o2) {
				try {
					return (int) ((VNumber) proc.apply(o1, o2)).val;
				} catch (EvaluationException e) {
					throw new ClassCastException();
				}
			}
		};
	}
	
	
	/**
	 * Returns a printed representation of the list.
	 * 
	 * @return a printable string
	 */
	public String toString()
	{
		return "[" + Utility.listString(ls) + "]";
	}

	
	/**
	 * Returns a JSON representation of the list.
	 */
	public String toJson()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append('[');
		for (int i = 0; i < ls.length; ++i) {
			buffer.append(ls[i].toJson());
			if ((i + 1) < ls.length)
				buffer.append(',');
		}
		buffer.append(']');
		
		return buffer.toString();
	}
	
	
	/**
	 * filter(p) : Returns the members of the list for which p is true.
	 */
	public Value method_filter(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Callable proc = (Callable) args[0].requireType(Callable.class);
		
		List<Value> lsNew = new LinkedList<Value>();
		
		for (Value v : ls) {
			if (((VBoolean) proc.apply(v).requireType(VBoolean.class)).val)
				lsNew.add(v);
		}
		
		return new VList(lsNew.toArray(new Value[lsNew.size()]));
	}
	
	
	/**
	 * get(n) : Returns element n from the list.
	 */
	public Value method_get(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		int index = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		require((index >= 0) && (index < ls.length));
		
		return ls[index];
	}
	
	
	/**
	 * join(ls) : Creates a new list containing the combination of this list and ls.
	 */
	public Value method_join(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		VList ls2 = (VList) args[0].requireType(VList.class);
		
		Value[] lsNew = new Value[ls.length + ls2.ls.length];
		for (int i = 0; i < ls.length;     ++i)  lsNew[i]             = ls[i];
		for (int i = 0; i < ls2.ls.length; ++i)  lsNew[i + ls.length] = ls2.ls[i];
		
		return new VList(lsNew);
	}
	
	
	/**
	 * len() : Returns the length of the list.
	 */
	public Value method_len(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VNumber(ls.length);
	}
	
	
	/**
	 * map(p) : Returns the list obtained by calling p on each element.
	 */
	public Value method_map(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		Callable proc = (Callable) args[0].requireType(Callable.class);
		
		Value[] lsNew    = new Value[ls.length];

		for (int i = 0; i < ls.length; ++i)
			lsNew[i] = proc.apply(ls[i]);

		return new VList(lsNew);
	}

	
	/**
	 * max : Returns the largest element in the list.
	 */
	public Value method_max(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		if (ls.length == 0)
			return VNull.NULL;
		
		double max = ((VNumber) ls[0].requireType(VNumber.class)).val;
		
		for (Value v : ls) {
			double val = ((VNumber) v.requireType(VNumber.class)).val;
			if (val > max)
				max = val;
		}

 	  return new VNumber(max);
	}
	
	
	/**
	 * max_index : Returns the index of the largest element in the list.
	 */
	public Value method_max_index(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (ls.length == 0)
			return VNull.NULL;
		
		double max   = ((VNumber) ls[0].requireType(VNumber.class)).val;
		int    index = 0;
		
		for (int i = 1; i < ls.length; ++i) {
			double val = ((VNumber) ls[i].requireType(VNumber.class)).val;
			if (val > max) {
				max   = val;
				index = i;
			}
		}
		
		return new VNumber(index);
	}

	
	/**
	 * mean : Returns the mean of the values in the list.
	 */
	public Value method_mean(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		double sum = 0.0;
		for (Value v : ls)
			sum += ((VNumber) v.requireType(VNumber.class)).val;
		
		return new VNumber(sum / ls.length);
	}

	
	/**
	 * min : Returns the smallest element in the list.
	 */
	public Value method_min(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		if (ls.length == 0)
			return VNull.NULL;
		
		double min = ((VNumber) ls[0].requireType(VNumber.class)).val;
		
		for (Value v : ls) {
			double val = ((VNumber) v.requireType(VNumber.class)).val;
			if (val < min)
				min = val;
		}

 	  return new VNumber(min);
	}

	
	/**
	 * min_index : Returns the index of the smallest element in the list.
	 */
	public Value method_min_index(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
	
		if (ls.length == 0)
			return VNull.NULL;
		
		double min   = ((VNumber) ls[0].requireType(VNumber.class)).val;
		int    index = 0;
		
		for (int i = 1; i < ls.length; ++i) {
			double val = ((VNumber) ls[i].requireType(VNumber.class)).val;
			if (val < min) {
				min   = val;
				index = i;
			}
		}
		
		return new VNumber(index);
	}

	
	/**
	 * reduce(p, acc) : Reduces the list to a single value by repeating acc = p(x, acc) for each x in the list. 
	 */
	public Value method_reduce(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		Callable proc = (Callable) args[0].requireType(Callable.class);
		
		Value acc = args[1];

		for (Value v : ls)
			acc = proc.apply(v, acc);
		
		return acc;
	}
	
	
	/**
	 * set(n, v) : Sets element n to the value v.  Returns the list to support chaining.
	 */
	public Value method_set(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		int index = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		require((index >= 0) && (index < ls.length));
		
		ls[index] = args[1];
		return this;
	}
	
	
	/**
	 * skew : Returns the skew of the values in the list.
	 */
	public Value method_skew(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		double mean = ((VNumber) method_mean(new Value[] {})).val;
		double m2   = 0.0;
		double m3   = 0.0;
		
		for (Value v : ls) {
			double val   = ((VNumber) v).val;
			double diff  = val - mean;
			double diff2 = diff * diff;
			m2 += diff2;
			m3 += diff2 * diff;
		}
		
		return new VNumber((m3 / ls.length) / Math.pow(m2 / ls.length, 1.5));
	}

	
	/**
	 * slice(n1, n2) : Returns the slice of elements from ls[n1] to ls[n2], inclusive.
	 */
	public Value method_slice(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		int n1 = (int) ((VNumber) args[0].requireType(VNumber.class)).val;
		int n2 = (int) ((VNumber) args[1].requireType(VNumber.class)).val;
		require((n1 >= 0) && (n1 <= n2) && (n2 < ls.length));
		
		Value[] lsNew = new Value[n2 - n1 + 1];
		for (int i = 0; i < (n2 - n1 + 1); ++i)
			lsNew[i] = ls[n1 + i];
		
		return new VList(lsNew);
	}
	
	
	/**
	 * sort()  : Sorts the numeric list in-place and returns it as a value.
	 * sort(p) : Like sort(), but with a sorting procedure p(a, b).
	 */
	public Value method_sort(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 1);
		
		Comparator<Value> comp = (args.length == 1) ? getComparator((VClosure) args[0].requireType(VClosure.class)) : getComparator();
		
		try {
			Arrays.sort(ls, comp);
		} catch (ClassCastException e) {
			throw new EvaluationException("sorting error (check your types)");
		}
		
		return this;
	}
	
	
	/**
	 * std : Returns the standard deviation of the values in the list.
	 */
	public Value method_std(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);

		double mean = ((VNumber) method_mean(new Value[] {})).val;
		double sum  = 0.0;
		
		for (Value v : ls) {
			double val = ((VNumber) v).val;
			sum += (val - mean) * (val - mean);
		}
		
		return new VNumber(Math.sqrt(sum / ls.length));
	}

	
	/**
	 * stream() : Converts the list to a stream.
	 */
	public Value method_stream(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SList(this));
	}

	
	/**
	 * sum : Returns the sum of the values of the list.
	 */
	public Value method_sum(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		double sum = 0.0;
		for (Value v : ls)
			sum += ((VNumber) v.requireType(VNumber.class)).val;
		
		return new VNumber(sum);
	}
}
