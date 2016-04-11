package project.model;

public class Projectile extends Entity
{
	
	private static final int DEFAULTSIZE = 4;
	
	public Projectile ( )
	{
		this ( new Position ( 0, 0 ) );
	}
	
	public Projectile ( Position position )
	{
		super ( position );
		setSize ( DEFAULTSIZE );
	}
	
	@Override
	public void move ( )
	{
		int newYCoord = getY ( ) - getSpeed ( );
		if ( newYCoord < 0 )
		{
			newYCoord = 0;
		}
		setY ( newYCoord );
	}
	
}
