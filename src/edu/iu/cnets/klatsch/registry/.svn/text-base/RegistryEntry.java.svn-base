package edu.iu.cnets.klatsch.registry;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;

import edu.iu.cnets.klatsch.exception.RegistryException;


/**
 * This is the ADT for a single entry stored in the registry.
 */
class RegistryEntry
{
	enum Type {
		BOOLEAN ("boolean"),
		DOUBLE  ("double"),
		INT     ("int"),
		STRING  ("string"),
		FILENAME("filename");
		
		private static final Map<String,Type> lookup = new HashMap<String,Type>();
		static {
			for (Type t : EnumSet.allOf(Type.class))
				lookup.put(t.getCode(), t);
		}
		
		private String code;
		
		private              Type   (String code)	{	this.code = code;        }
		public        String getCode()	          {	return code;             }
		public static Type   get    (String code)	{	return lookup.get(code); }
	}
	
	/** the name of the entry */
	private String name;

	/** the type of the entry */
	private Type   type;

	/** the value of the entry */
	private Object value;


	/**
   * Initializes a new entry with the data in the given node from the parsed registry document.
   *
   * @param group      the group that the entry is part of
   * @param entryNode  the XML node containing the entry data
   * @throws RegistryError on type mismatches or missing data
   */
	protected RegistryEntry(String group, Node entryNode)
	throws RegistryException
	{
		String typeString  = null;
		String valueString = null;

		// store the value and the name and type attributes
		name        = entryNode.getAttributes().getNamedItem("name").getNodeValue();
		typeString  = entryNode.getAttributes().getNamedItem("type").getNodeValue();
		valueString = entryNode.getFirstChild().getNodeValue().trim();

		// make sure we got all the information
		if ((name == null) || (typeString == null) || (valueString == null))
	    throw new RegistryException("Missing field in entry in group " + group);
	
		// store the value based on the type
		type = Type.get(typeString);
		switch (type) {
			case BOOLEAN:   value = new Boolean(valueString.equals("yes") || valueString.equals("true"));  break;
			case DOUBLE:    value = new Double (valueString);                                              break;
			case INT:       value = new Integer(valueString);                                              break;
			case STRING:    value = valueString;                                                           break;
			case FILENAME:  value = valueString;                                                           break;
		}
	}


	/**
   * Returns the name of the entry.
   *
   * @return the name of the entry
   */
  protected String getName()
  {
  	return name;
  }


  /**
   * Returns the type of the entry.
   *
   * @return the type of the entry
   */
  protected Type getType()
  {
  	return type;
  }


  /**
   * Returns the value of the entry.
   *
   * @return the value of the entry
   */
  protected Object getValue()
  {
  	return value;
  }
}
