package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatementExecutor
{
	protected static ResultSetPacket select(Connection connection, String tablename, String what, String conditions,
			ArrayList<Object> params)
	{
		String statement = "SELECT " + what + " FROM " + tablename + " WHERE " + conditions;
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement ( statement );
			int i = 1;
			for ( Object param : params )
			{
				if ( param instanceof String )
				{
					preparedStatement.setString ( i, (String) param );
				}
				else if ( param instanceof Integer )
				{
					preparedStatement.setInt ( i, (Integer) param );
				}
				else if ( param instanceof Double )
				{
					preparedStatement.setDouble ( i, (Double) param );
				}
				++i;
			}
			preparedStatement.execute ();
			return new ResultSetPacket ( preparedStatement.executeQuery (), null );

		}
		catch ( SQLException e )
		{
			return new ResultSetPacket ( null, new String[]
			{ e.getMessage () } );
		}
	}

	protected static BooleanPacket exists(Connection connection, String tablename, String what, String conditions,
			ArrayList<Object> params)
	{
		BooleanPacket existing = new BooleanPacket ( false, null );
		try
		{
			ResultSetPacket rsp = select ( connection, tablename, what, conditions, params );
			ResultSet rs = rsp.getResultSet ();
			existing.setErrors ( rsp.getErrors () );
			
			while ( rs.next () )
			{
				existing.setB ( true );
			}

		}
		catch ( SQLException e )
		{
			String[] cache = expandOne (existing.getErrors () );
			cache[-1] = e.getMessage ();
			existing.setErrors ( cache );
		}
		return existing;
	}

	protected static String delete(Connection connection, String tablename, String conditions, ArrayList<Object> params)
	{
		String statement = "DELETE FROM " + tablename + " WHERE " + conditions;
		return executeStatement ( connection, statement, params );
	}

	protected static String update(Connection connection, String tablename, String what, String conditions, ArrayList<Object> params)
	{
		String statement = "UPDATE " + tablename + " SET " + what + " = ? WHERE " + conditions;
		return executeStatement ( connection, statement, params );
	}

	protected static String insert(Connection connection, String tablename, String columns, String values, ArrayList<Object> params)
	{
		String statement = "INSERT INTO " + tablename + " ( " + columns + " ) VALUES ( " + values + " )";
		return executeStatement ( connection, statement, params );
	}

	protected static String executeStatement(Connection connection, String statement, ArrayList<Object> params)
	{
		String error = null;
		int i = 1;
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement ( statement );
			for ( Object param : params )
			{
				if ( param instanceof String )
				{
					preparedStatement.setString ( i, (String) param );
				}
				else if ( param instanceof Integer )
				{
					preparedStatement.setInt ( i, (Integer) param );
				}
				else if ( param instanceof Double )
				{
					preparedStatement.setDouble ( i, (Double) param );
				}
				++i;
			}
			preparedStatement.execute ();
		}
		catch ( SQLException e )
		{
			error = e.getMessage ();
		}
		
		return error;
	}

	private static String[] expandOne(String[] stringArray)
	{
		if ( stringArray != null )
		{
			int counter = 1;

			for ( String string : stringArray )
			{
				if ( string != null )
				{
					counter++;
				}
			}

			String[] newStringArray = new String[counter];

			counter = 0;

			for ( String string : stringArray )
			{
				if ( string != null )
				{
					newStringArray[counter] = string;
					counter++;
				}
			}

			return newStringArray;
		}
		else
		{
			return new String[1];
		}
	}

}
