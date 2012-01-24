package edu.iu.cnets.klatsch.feed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import edu.iu.cnets.klatsch.Main;
import edu.iu.cnets.klatsch.exception.EvaluationException;
import edu.iu.cnets.klatsch.exception.RegistryException;
import edu.iu.cnets.klatsch.misc.SqlIntegerIterator;
import edu.iu.cnets.klatsch.model.Edge;
import edu.iu.cnets.klatsch.model.Event;
import edu.iu.cnets.klatsch.model.Feed;
import edu.iu.cnets.klatsch.model.Graph;
import edu.iu.cnets.klatsch.model.Node;
import edu.iu.cnets.klatsch.model.basic.BasicEvent;
import edu.iu.cnets.klatsch.model.gephi.GephiEdge;
import edu.iu.cnets.klatsch.model.gephi.GephiNode;
import edu.iu.cnets.klatsch.lang.Value;
import edu.iu.cnets.klatsch.lang.VBoolean;
import edu.iu.cnets.klatsch.lang.VDictionary;
import edu.iu.cnets.klatsch.lang.VNumber;
import edu.iu.cnets.klatsch.lang.VString;


/**
 * This class handles all importation of data from the Buzz database into the Klatsch framework.
 */
public class Buzz implements Feed
{
	/** enumeration for our prepared statements */
	enum Query {
		ACTOR_ID, ACTOR_LABEL, EVENTS_BY_ACTOR, EVENTS_BY_ACTOR_DATE, EVENTS_BY_MEME, EVENTS_BY_MEME_DATE,
		EVENTS_BY_DATE, MEME_ID, MEME_LABEL, MEME_LINKS
	};
	Map<Query,PreparedStatement> query;
	
	/** the database connection string */
	String     db;

	/** the connection */
	Connection cxn;
	
	/** the username for connecting */
	String     username;
	
	/** the password for connecting */
	String     password;
	
	
	/**
	 * Internal class for converting a {@link ResultSet} into a series of {@link Event} objects.
	 */
	class SqlEventIterator implements Iterator<Event>
	{
		ResultSet result;
		boolean   done;
		
		SqlEventIterator(ResultSet result)
		throws SQLException
		{
			this.result = result;
			this.done   = !result.next();
		}

		
		public boolean hasNext()
		{
			return !done;
		}

		
		public Event next()
		{
			try {
				int       when  = (int) (result.getTimestamp(1).getTime() / 1000);
				GephiNode actor = new GephiNode(Node.TYPE_ACTOR, result.getInt(2));
				GephiNode meme  = new GephiNode(Node.TYPE_MEME,  result.getInt(3));
			
				List<Edge> ls = new LinkedList<Edge>();
				ls.add(new GephiEdge(actor, meme, when, when, 1.0, 1.0));

				Event event = new BasicEvent(when, ls.toArray(new Edge[ls.size()]));
				done = !result.next();
			
				return event;
			} catch (SQLException e) {
				return null;
			}
		}
		
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	
	/**
	 * Creates a new object for connecting to the Buzz database.  The single parameter should be a dictionary
	 * containing overrides for the default parameters, which are stored in the registry:
	 * 
	 *   host     (String)
	 *   port     (Number)
	 *   database (String)
	 *   username (String)
	 *   password (String)
	 *   
	 * Note that this does not try to establish the connection -- that must be done with {@link #connect}.
	 */
	public Buzz(Value ... ls)
	throws EvaluationException
	{
		String host, database;
		int    port;
			
		try {
			host     = Main.registry.getString("truthy.host");
			port     = Main.registry.getInt   ("truthy.port");
			database = Main.registry.getString("truthy.database");
			username = Main.registry.getString("truthy.username");
			password = Main.registry.getString("truthy.password");
		} catch (RegistryException e) {
			throw new EvaluationException(e.getMessage());
		}
		
		if (ls.length > 0) {
			VDictionary dict = (VDictionary) ls[0].requireType(VDictionary.class);
			if (((VBoolean) dict.method_has(new VString("host")))    .val)  host     =       ((VString) dict.method_get(new VString("host")))    .val;
			if (((VBoolean) dict.method_has(new VString("port")))    .val)  port     = (int) ((VNumber) dict.method_get(new VString("port")))    .val;
			if (((VBoolean) dict.method_has(new VString("database"))).val)  database =       ((VString) dict.method_get(new VString("database"))).val;
			if (((VBoolean) dict.method_has(new VString("username"))).val)  username =       ((VString) dict.method_get(new VString("username"))).val;
			if (((VBoolean) dict.method_has(new VString("password"))).val)  password =       ((VString) dict.method_get(new VString("password"))).val;
		}
		
		db  = "jdbc:mysql://" + host + ":" + port + "/" + database;
		cxn = null;
	}
	
	
	/** Returns an iterator over all events for the given actor. */
	public Iterator<Event> actorEvents(int actorId)
	{
		try {
			
			PreparedStatement q = query.get(Query.EVENTS_BY_ACTOR);
			q.setInt(1, actorId);
			
			return new SqlEventIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Returns an iterator over all events for the given actor in the given time interval. */
	public Iterator<Event> actorEvents(int actorId, int startTime, int endTime)
	{
		try {
			
			PreparedStatement q = query.get(Query.EVENTS_BY_ACTOR_DATE);
			q.setInt (1, actorId);
			q.setDate(2, new java.sql.Date(startTime * 1000L));
			q.setDate(3, new java.sql.Date(endTime   * 1000L));
			
			return new SqlEventIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Maps from an actor's label to its ID. */
	public int actorId(String actor)
	throws IllegalArgumentException
	{
		try {
		
			PreparedStatement q = query.get(Query.ACTOR_ID);
			q.setString(1, actor);
		
			ResultSet result = q.executeQuery();
			if (result.next())
				return result.getInt(1);
			else
				throw new IllegalArgumentException("unknown actor");
	
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Maps from an actor's ID to its label. */
	public String actorLabel(int actorId)
	throws IllegalArgumentException
	{
	try {
			
			PreparedStatement q = query.get(Query.ACTOR_LABEL);
			q.setInt(1, actorId);
			
			ResultSet result = q.executeQuery();
			if (result.next())
				return result.getString(1);
			else
				throw new IllegalArgumentException("unknown actor");
		
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/**
	 * Attempts to establish a connection to the database using our credentials.
	 * 
	 * @throws EvaluationException if the connection failed
	 */
	public boolean connect()
	{
		if (cxn != null) {
			System.err.println("Warning: feed " + db + " already connected");
			return true;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			cxn = DriverManager.getConnection(db, username, password);
			
			query = new HashMap<Query,PreparedStatement>();
			query.put(Query.ACTOR_ID,             cxn.prepareStatement(
					"SELECT id FROM users WHERE buzz_name = ?"));
			query.put(Query.ACTOR_LABEL,          cxn.prepareStatement(
					"SELECT buzz_name FROM users WHERE id = ?"));
			query.put(Query.EVENTS_BY_ACTOR,      cxn.prepareStatement(
					"SELECT time_stamp, user_id, meme_id FROM events WHERE user_id = ?"));
			query.put(Query.EVENTS_BY_ACTOR_DATE, cxn.prepareStatement(
					"SELECT time_stamp, user_id, meme_id FROM events WHERE " +
				  "user_id = ? AND (time_stamp >= ? AND time_stamp <= ?)"));
			query.put(Query.EVENTS_BY_MEME,        cxn.prepareStatement(
					"SELECT time_stamp, user_id, meme_id FROM events WHERE meme_id = ?"));
			query.put(Query.EVENTS_BY_MEME_DATE,   cxn.prepareStatement(
					"SELECT time_stamp, user_id, meme_id FROM events WHERE " +
				  "meme_id = ? AND (time_stamp >= ? AND time_stamp <= ?)"));
			query.put(Query.EVENTS_BY_DATE,       cxn.prepareStatement(
					"SELECT time_stamp, user_id, meme_id FROM events WHERE " +
					"(time_stamp >= ? AND time_stamp <= ?"));
			query.put(Query.MEME_ID,              cxn.prepareStatement(
					"SELECT id FROM memes WHERE text = ?"));
			query.put(Query.MEME_LABEL,           cxn.prepareStatement(
					"SELECT text FROM memes WHERE id = ?"));
			query.put(Query.MEME_LINKS,           cxn.prepareStatement(
					"SELECT DISTINCT a.meme_id " +
					"FROM events a, events b " +
					"WHERE b.meme_id = ? AND a.meme_id <> b.meme_id AND a.user_id = b.user_id"));

			return true;
		} catch (Exception e) {
			System.err.println("Warning: database connection failed: " + e.getMessage());
			return false;
		}
	}
	
	
	/** Returns an iterator over all events in the given time range. */
	public Iterator<Event> events(int startTime, int endTime)
	{
		try {
			
			PreparedStatement q = query.get(Query.EVENTS_BY_DATE);
			q.setInt(1, startTime);
			q.setInt(2, endTime);
			
			return new SqlEventIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	
	/**
	 * Called to close the database connection during object finalization. 
	 */
	protected void finalize()
	throws Throwable
	{
		try {
			if (cxn != null)
				cxn.close();
		} catch (SQLException e) {}
	}
	

	/** Returns an iterator over all events for the given meme. */
	public Iterator<Event> memeEvents(int memeId)
	{
		try {
			
			PreparedStatement q = query.get(Query.EVENTS_BY_MEME);
			q.setInt(1, memeId);
			
			return new SqlEventIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Returns an iterator over all events for the given meme in the given time interval. */
	public Iterator<Event> memeEvents(int memeId, int startTime, int endTime)
	{
		try {
			
			PreparedStatement q = query.get(Query.EVENTS_BY_MEME_DATE);
			q.setInt (1, memeId);
			q.setDate(2, new java.sql.Date(startTime * 1000L));
			q.setDate(3, new java.sql.Date(  endTime * 1000L));
			
			return new SqlEventIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Maps from a meme's label to its ID. */
	public int memeId(String meme)
	throws IllegalArgumentException
	{
		try {
			
			PreparedStatement q = query.get(Query.MEME_ID);
			q.setString(1, meme);
			
			ResultSet result = q.executeQuery();
			if (result.next())
				return result.getInt(1);
			else
				throw new IllegalArgumentException("unknown meme");
		
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Maps from a meme's ID to its label. */
	public String memeLabel(int memeId)
	throws IllegalArgumentException
	{
		try {
			
			PreparedStatement q = query.get(Query.MEME_LABEL);
			q.setInt(1, memeId);
			
			ResultSet result = q.executeQuery();
			if (result.next())
				return result.getString(1);
			else
				throw new IllegalArgumentException("unknown meme");
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	
	/** Returns an iterator over all memes connected to the given meme. */
	public Iterator<Integer> memeLinks (int memeId)
	{
		
		try {
			
			PreparedStatement q = query.get(Query.MEME_LINKS);
			q.setInt(1, memeId);
			
			return new SqlIntegerIterator(q.executeQuery());
			
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/** Returns the name of this feed. */
	public String name()
	{
		return "Buzz(" + db + ")";
	}
	
	
	/**
	 * Takes care of actually submitting a query to the database and returning the results.
	 */
	public ResultSet query(String queryText)
	throws IllegalArgumentException
	{
		Statement statement;
		
		try {
			statement = cxn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			return statement.executeQuery(queryText);
		} catch (SQLException e) {
			System.err.println("SQL error");
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/**
	 * Performs post-construction updating of the trusted weights in a graph derived from
	 * the Buzz feed.  This is a null operation.
	 */
	public void updateTrust(Graph g) {}
}
