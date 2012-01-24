package edu.iu.cnets.klatsch.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;


/**
 * Class for converting a SQL {@link ResultSet} into a series of {@link Integer} objects.
 */
public class SqlIntegerIterator implements Iterator<Integer>
{
	ResultSet result;
	boolean   done;
	
	public SqlIntegerIterator(ResultSet result)
	throws SQLException
	{
		this.result = result;
		this.done   = !result.next();
	}

	
	public boolean hasNext()
	{
		return !done;
	}

	
	public Integer next()
	{
		try {
			int val = result.getInt(1);
			done = !result.next();
			return val;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
