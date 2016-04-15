package project.database;

import java.sql.ResultSet;

public class ResultSetPacket
{
	private ResultSet resultSet = null;
	private String[] errors = null;
	
	public ResultSetPacket ( )
	{
	
	}
	
	public ResultSetPacket ( ResultSet resultSet )
	{
		this.resultSet = resultSet;
	}
	
	public ResultSetPacket ( String[] errors )
	{
		this.errors = errors;
	}
	
	public ResultSetPacket ( ResultSet resultSet, String[] errors )
	{
		this.resultSet = resultSet;
		this.errors = errors;
	}
	
	public ResultSet getResultSet ( )
	{
		return resultSet;
	}
	
	public void setResultSet ( ResultSet resultSet )
	{
		this.resultSet = resultSet;
	}
	
	public String[] getErrors ( )
	{
		return errors;
	}
	
	public void setErrors ( String[] errors )
	{
		this.errors = errors;
	}
}
