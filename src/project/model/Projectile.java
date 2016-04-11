package project.model;

public class Projectile extends Entity
{
	
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
