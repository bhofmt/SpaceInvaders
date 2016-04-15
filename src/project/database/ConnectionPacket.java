package project.database;

import java.sql.Connection;

public class ConnectionPacket
{
	private Connection connection;
	private String[] errors;
	
	public ConnectionPacket ( )
	{
	
	}
	
	public ConnectionPacket ( Connection connection )
	{
		this.connection = connection;
	}
	
	public ConnectionPacket ( String[] errors )
	{
		this.errors = errors;
	}
	
	public ConnectionPacket ( Connection connection, String[] errors )
	{
		this.connection = connection;
		this.errors = errors;
	}
	
	public String[] getErrors ( )
	{
		return errors;
	}
	
	public void setErrors ( String[] errors )
	{
		this.errors = errors;
	}
	
	public Connection getConnection ( )
	{
		return connection;
	}
	
	public void setConnection ( Connection connection )
	{
		this.connection = connection;
	}
}
