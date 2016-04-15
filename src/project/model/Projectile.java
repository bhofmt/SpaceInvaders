package project.model;

import project.controller.SpaceController;

public class Projectile extends Entity
{
	private static final long serialVersionUID = -7311127654177673361L;
	
	private static final int DEFAULTHEIGHT = 8;
	private static final int DEFAULTWIDTH = 4;
	private static final int DEFAULTSPEED = 12;
	private static final int DEFAULTSTARTINGHEALTH = 1;
	
	private int maxHealth;
	
	public Projectile ( )
	{
		this ( 0, 0 );
	}
	
	public Projectile ( int x, int y )
	{
		this ( x, y, DEFAULTSTARTINGHEALTH );
	}
	
	public Projectile ( int x, int y, int healthPoints )
	{
		super ( x, y );
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		setSpeed ( DEFAULTSPEED );
		setImage ( SpaceController.getInstanceOf ( ).readImage ( "images/projectile.png" ));
		setHealthPoints ( healthPoints );
		maxHealth = healthPoints;
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

	public int getMaxHealth ( )
	{
		return maxHealth;
	}

	public void setMaxHealth ( int maxHealth )
	{
		this.maxHealth = maxHealth;
	}
	
}
