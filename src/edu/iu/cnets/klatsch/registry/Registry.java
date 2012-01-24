package edu.iu.cnets.klatsch.registry;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.iu.cnets.klatsch.exception.RegistryException;


/**
 * This class implements a simple configuration registry for the Klatsch framework.
 * The registry is represented as a simple XML file that lives in KLATSCH/etc/registry.xml.
 * At startup time, all of the keys in the registry are read into memory.
 */
public class Registry
{
	/** the actual registry, which is a map from keys to values */
	protected Map<String,Object> registry;


  /**
   * Initializes the registry system using the given path.
   * 
   * @param filename  the path to the "registry.xml" file
   */
  public Registry(String filename)
  {
  	// allocate the hash table
  	registry = new HashMap<String,Object>();

  	try {

  		// parse the registry file
  		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("file:" + filename);

  		// get the list of top-level groups
  		Element  root   = document.getDocumentElement();
  		NodeList groups = root.getChildNodes();

  		// process each of those groups
  		for (int i = 0; i < groups.getLength(); ++i) {
  			Node child = groups.item(i);
  			if (child.getNodeName().equals("group"))
  				addGroup("", child);
  		}

  	} catch (ParserConfigurationException e) { System.err.println("Error reading registry: " + e.getMessage());
  	} catch (IOException                  e) { System.err.println("Error reading registry: " + e.getMessage());
  	} catch (SAXException                 e) { System.err.println("Error reading registry: " + e.getMessage());
  	} catch (RegistryException            e) { System.err.println("Error reading registry: " + e.getMessage()); }
  }


  /**
   * Adds the registry subgroups and attributes in the given group to the registry map.
   *
   * @param prefix  the current built-up prefix for this group
   * @param group   the Node containing the group
   */
  private void addGroup(String prefix, Node group)
  throws RegistryException
  {
  	// extend the prefix with this group's name
  	prefix += group.getAttributes().getNamedItem("name").getNodeValue().trim() + "."; 

  	// for each child element
  	NodeList children = group.getChildNodes();
  	for (int i = 0; i < children.getLength(); ++i) {

  		// get a hold of the element
  		Node child = children.item(i);	

    	// recur on groups
  		if (child.getNodeName().equals("group"))
  			addGroup(prefix, child);

    	// and create a new object for entries
  		else if (child.getNodeName().equals("entry")) {
  			RegistryEntry entry = new RegistryEntry(prefix, child);
  			registry.put(prefix + entry.getName(), entry);
    	}
  	}
  }


  /**
   * Takes the given pathname and returns a new pathname with every '/'
   * replaced with the local file separator.
   *
   * @param filename  the input path
   * @return the translated output path
   */
  private String fixFilename(String filename)
  {
  	StringBuffer buffer = new StringBuffer(filename.length());

  	// copy the buffer over, substituting for '/'
   	for (int index = 0; index < filename.length(); ++index)
   		if (filename.charAt(index) == '/')
   			buffer.append(File.separator);
   		else
   			buffer.append(filename.charAt(index));

   	// and return the new path
   	return buffer.toString();
  }


  /**
   * Returns the value of the given registry entry, which had better be a boolean.
   *
   * @param entryName  the registry entry we want the value for
   * @return the value of the entry
   * @throws RegistryException for missing keys or type mismatches
   */
  public boolean getBoolean(String entryName)
  throws RegistryException
  {
  	// retrieve the entry
   	RegistryEntry entry = validateAccess(entryName, RegistryEntry.Type.BOOLEAN);

   	// return the value
   	return (Boolean) entry.getValue();
  }


  /**
   * Returns the value of the given registry entry, which had better be a double.
   *
   * @param entryName  the registry entry we want the value for
   * @return the value of the entry
   * @throws RegistryException for missing keys or type mismatches
   */
  public double getDouble(String entryName)
  throws RegistryException
  {
  	// retrieve the entry
   	RegistryEntry entry = validateAccess(entryName, RegistryEntry.Type.DOUBLE);

   	// return the value
   	return (Double) entry.getValue();
  }


  /**
   * Returns the value of the given registry entry, which had better be a filename.
   *
   * @param entryName  the registry entry we want the value for
   * @return the value of the entry
   * @throws RegistryException for missing keys or type mismatches
   */
  public String getFilename(String entryName)
	throws RegistryException
  {
  	// retrieve the entry
  	RegistryEntry entry = validateAccess(entryName, RegistryEntry.Type.FILENAME);

  	// return the value
  	return fixFilename((String) entry.getValue());
  }


  /**
   * Returns the value of the given registry entry, which had better be an integer.
   *
   * @param entryName  the registry entry we want the value for
   * @return the value of the entry
   * @throws RegistryException for missing keys or type mismatches
   */
  public int getInt(String entryName)
	throws RegistryException
  {
  	// retrieve the entry
  	RegistryEntry entry = validateAccess(entryName, RegistryEntry.Type.INT);

  	// return the value
  	return (Integer) entry.getValue();
  }


  /**
   * Returns the value of the given registry entry, which had better be a string.
   *
   * @param entryName  the registry entry we want the value for
   * @return the value of the entry
   * @throws RegistryException for missing keys or type mismatches
   */
  public String getString(String entryName)
	throws RegistryException
  {
  	// retrieve the entry
  	RegistryEntry entry = validateAccess(entryName, RegistryEntry.Type.STRING);

  	// return the value
  	return ((String) entry.getValue());
  }


  /**
   * Validates the registry access operation for the given entry name and type.
   *
   * @param entry   the entry we're validating
   * @param type    the type we must match
   * @return the registry entry
   * @throws RegistryException if the entry does not validate
   */
  private RegistryEntry validateAccess(String entryName, RegistryEntry.Type type)
  throws RegistryException
  {
  	// retrieve the entry
  	RegistryEntry entry = (RegistryEntry) registry.get(entryName);

  	// make sure we know about this key
  	if (entry == null)
  		throw new RegistryException("Missing registry entry '" + entry + "'");

  	// make sure the type matches
   	if (entry.getType() != type)
   		throw new RegistryException("Registry entry '" + entryName + "' has the wrong type");

    // return the entry
    return entry;
  }
}
