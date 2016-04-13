package project.database;

public class BooleanPacket
{
	private boolean b;
	private String[] errors;

	public BooleanPacket ()
	{

	}

	public BooleanPacket ( boolean b )
	{
		this.b = b;
	}

	public BooleanPacket ( String[] errors )
	{
		this.errors = errors;
	}

	public BooleanPacket ( boolean b, String[] errors )
	{
		this.b = b;
		this.errors = errors;
	}

	public boolean isB()
	{
		return b;
	}

	public void setB(boolean b)
	{
		this.b = b;
	}

	public String[] getErrors()
	{
		return errors;
	}

	public void setErrors(String[] errors)
	{
		this.errors = errors;
	}
}
