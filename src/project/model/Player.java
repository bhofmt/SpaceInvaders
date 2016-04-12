package project.model;

import project.controller.SpaceController;

public class Player extends Entity
{
	private static final long serialVersionUID = -3223492405755701782L;
	
	private static final int DEFAULTHEIGHT = 10;
	private static final int DEFAULTWIDTH = 10;
	private static final int DEFAULTSPEED = 4;
	
	private String playerName;
	private boolean moveLeft = false, moveRight = false;
	
	public Player ( )
	{
		this ( "Player", 0, SpaceController.getGamePanel ( ).getHeight ( ) - 50 );
	}
	
	public Player ( int x, int y )
	{
		this ( "Player", x, y );
	}
	
	public Player ( String name, int x, int y )
	{
		super ( x, y );
		playerName = name;
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		setSpeed ( DEFAULTSPEED );
	}
	
	@Override
	public void move ( )
	{
		
		// Doesn't move the Player at all.
		
		if ( moveLeft == moveRight )
		{
		
		}
		
		// Moves the Player left.
		
		else if ( moveLeft )
		{
			int newX = ( int ) ( getX ( ) - getSpeed ( ) );
			if ( newX < 0 )
			{
				newX = 0;
			}
			x = newX;
			
		}
		
		// Moves the Player right.
		else
		{
			int newX = ( int ) ( getX ( ) + getSpeed ( ) );
			int panelWidth = getGamePanel ( ).getWidth ( );
			if ( newX > panelWidth - getWidth ( ) )
			{
				newX = ( int ) ( panelWidth - getWidth ( ) );
			}
			x = newX;
		}
	}
	
	public void shoot ( )
	{
		Projectile projectile = new Projectile ( ( int ) getX ( ), ( int ) getY ( ) - 2 );
		
		SpaceController.getInstanceOf ( ).addProjectile ( projectile );
	}
	
	public String getPlayerName ( )
	{
		return playerName;
	}
	
	public void movesLeft ( boolean movesLeft )
	{
		moveLeft = movesLeft;
	}
	
	public void movesRight ( boolean movesRight )
	{
		moveRight = movesRight;
	}
	
}
