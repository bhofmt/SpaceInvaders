package project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager
{

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static Connection con;

	private DatabaseManager ()
	{

	}

	public static ConnectionPacket getConnection(String adress, String databaseUsername, String databasePassword)
	{
		try
		{
			if ( con == null || con.isClosed () )
			{
				Class.forName ( JDBC_DRIVER );
				String DB_URL = "jdbc:mysql://" + adress;
				con = DriverManager.getConnection ( DB_URL, databaseUsername, databasePassword );
			}
			return new ConnectionPacket ( con, null );
		}
		catch ( ClassNotFoundException | SQLException e )
		{
			return new ConnectionPacket ( null, new String[]
			{ e.getMessage () } );
		}
	}
}
