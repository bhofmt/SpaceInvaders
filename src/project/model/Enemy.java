package project.model;

public class Enemy extends Entity
{
	private static final int ROWHEIGHT = 20;
	
	/**
	 * Used to move the enemy around.
	 */
	
	private int movement = getSpeed ( );
	
	@Override
	public void move ( )
	{
		int newX = movement + getX ( );
		
		if ( newX > getGamePanel ( ).getWidth ( ) )
		{
			newX = getGamePanel ( ).getWidth ( );
			setY ( getY ( ) + ROWHEIGHT );
			movement = -movement;
		}
		else if ( newX < 0 )
		{
			newX = 0;
			setY ( getY ( ) + ROWHEIGHT );
			movement = -movement;
		}
		
		setX ( newX );
	}
	
}
