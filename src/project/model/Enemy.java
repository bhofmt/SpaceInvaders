package project.model;

import project.controller.SpaceController;

public class Enemy extends Entity
{
	private static final long serialVersionUID = 5919647216294900080L;
	
	private static final int DEFAULTHEIGHT = 8;
	private static final int DEFAULTWIDTH = 8;
	private static final int ROWHEIGHT = 40;
	private static final int MAXROWS = 5;
	
	private int rowCounter = 0;
	
	/**
	 * Used to move the enemy around.
	 */
	
	private int movement;
	
	public Enemy ( int x, int y, int speed )
	{
		super ( x, y );
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		setSpeed ( speed );
		movement = getSpeed ( );
	}
	
	@Override
	public void move ( )
	{
		int newX = movement + x;
		
		if ( newX > getGamePanel ( ).getWidth ( ) - getWidth ( ) )
		{
			newX = ( int ) ( getGamePanel ( ).getWidth ( ) - getWidth ( ) );
			y += ROWHEIGHT;
			movement = -movement;
			rowCounter++;
		}
		else if ( newX < 0 )
		{
			newX = 0;
			y = ( int ) getY ( ) + ROWHEIGHT;
			movement = -movement;
			rowCounter++;
		}
		
		x = newX;
		
		if ( rowCounter >= MAXROWS )
		{
			SpaceController.getInstanceOf ( ).enemyPassed ( );
		}
	}
	
}
