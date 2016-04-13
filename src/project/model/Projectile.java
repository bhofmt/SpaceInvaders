package project.model;

import project.controller.SpaceController;

public class Projectile extends Entity
{
	private static final long serialVersionUID = -7311127654177673361L;
	
	private static final int DEFAULTHEIGHT = 8;
	private static final int DEFAULTWIDTH = 4;
	private static final int DEFAULTSPEED = 10;
	private static final int DEFAULTSTARTINGHEALTH = 1;
	
	public Projectile ( )
	{
		this ( 0, 0 );
	}
	
	public Projectile ( int x, int y )
	{
		super ( x, y );
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		setSpeed ( DEFAULTSPEED );
		setImage ( SpaceController.getInstanceOf ( ).readImage ( "images/projectile.png" ));
		setHealthPoints ( DEFAULTSTARTINGHEALTH );
	}
	
	@Override
	public void move ( )
	{
		int newYCoord = ( int ) ( getY ( ) - getSpeed ( ) );
		if ( newYCoord + getHeight ( ) < 0 )
		{
			newYCoord = 0 - (int)getHeight ( );
		}
		y = newYCoord;
	}
	
}
