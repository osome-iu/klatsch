package edu.iu.cnets.klatsch.lang;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.stream.SIterator;


/**
 * This value represents a dictionary (i.e., associative array).
 */
public class VDictionary extends Value
{
	/** our actual storage mechanism */
	Map<VString,Value> map;
	
	
	/**
	 * Constructs a new, empty dictionary.
	 */
	public VDictionary()
	{
		map = new HashMap<VString,Value>();
	}
	
	
	/**
	 * Updates the given association.
	 * 
	 * @param key  the key to update
	 * @param val  the new value for that key
	 */
	public void set(VString key, Value val)
	{
		map.put(key, val);
	}
	
	
	/**
	 * Creates a (largely opaque) printed representation of the dictionary.
	 * 
	 * @return the printed representation
	 */
	public String toString()
	{
		return "Dictionary<N=" + map.size() + ">";
	}
	
	
	/**
	 * Creates a JSON representation of the dictionary.
	 */
	public String toJson()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append('{');
		
		Iterator<Map.Entry<VString, Value>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<VString, Value> entry = iter.next();
			buffer.append(entry.getKey().toJson());
			buffer.append(':');
			buffer.append(entry.getValue().toJson());
			if (iter.hasNext())
				buffer.append(',');
		}
		
		buffer.append('}');
		
		return buffer.toString();
	}
	
	
	/**
	 * delete(s) : Removes any association for s.  (It's okay if there wasn't one to begin with.)
	 * Returns the dictionary to support method chaining.
	 */
	public Value method_delete(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		VString key = (VString) args[0].requireType(VString.class);
		
		map.remove(key);
		return this;
	}
	
	
	/**
	 * has(s) : Returns true if the dictionary has an association for s.
	 */
	public Value method_has(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		VString key = (VString) args[0].requireType(VString.class);
		
		return new VBoolean(map.containsKey(key));
	}
	
	
	/**
	 * get(s) : Returns the value associated with s.
	 */
	public Value method_get(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 1, 1);
		VString key = (VString) args[0].requireType(VString.class);

		if (!map.containsKey(key))
			throw new EvaluationException("Key '" + key.toString() + "' not in dictionary");
		
		return map.get(key);
	}
	
	
	/**
	 * keys() : Returns a stream of keys for the dictionary.
	 */
	public Value method_keys(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SIterator(map.keySet().iterator()));
	}
	
		
	/**
	 * set(s, v) : Associates s with the value v, replacing any existing association.  Returns v to support chaining.
	 */
	public Value method_set(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 2, 2);
		VString key = (VString) args[0].requireType(VString.class);
		
		set(key, args[1]);
		return args[1];
	}
	
	
	/**
	 * values() : Returns a stream of values for the dictionary.
	 */
	public Value method_values(Value ... args)
	throws EvaluationException
	{
		requireCount(args, 0, 0);
		
		return new VStream(new SIterator(map.values().iterator()));
	}
}
