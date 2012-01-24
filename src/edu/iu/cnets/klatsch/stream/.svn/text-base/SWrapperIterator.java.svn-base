package edu.iu.cnets.klatsch.stream;

import java.util.Iterator;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.lang.Value;


/**
 * This class wraps a stream around an iterator over non-Klatsch values.
 * 
 * For the class to work successfully, the given value class must be a subclass of {@link Value}
 * and have a single-argument constructor that takes a single value of the same type as the iterator.   
 */
public class SWrapperIterator extends SIterator
{
	Class<? extends Value> wrapperClass;
	
	
	/**
	 * Builds a new stream from the given iterator.  The individual elements will be wrapped in the
	 * given class, which should be a subclass of {@link Value}.
	 */
	public SWrapperIterator(Class<? extends Value> wrapperClass, Iterator<?> iter)
	{
		super(iter);
		this.wrapperClass = wrapperClass;
	}

	
	/**
	 * Gets the next value in the list, if possible.
	 * 
	 * @return the value
	 */
	public Value getNext()
	throws EvaluationException
	{
		if (iter.hasNext()) {
			Object   o = iter.next();
			Class<?> c = o.getClass();
			try {
				return wrapperClass.getConstructor(c).newInstance(o);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EvaluationException("failure in wrapper iterator");
			}
		} else
			throw new EvaluationException("end of stream");
	}
}
