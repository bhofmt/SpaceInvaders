package project.model;

public class Enemy extends Entity
{
	private static final long serialVersionUID = 5919647216294900080L;

	public Enemy ( int x, int y )
	{
		super ( x, y );
	}

	private static final int ROWHEIGHT = 20;
	
	/**
	 * Used to move the enemy around.
	 */
	
	private int movement = getSpeed ( );
	
	@Override
	public void move ( )
	{
		int newX = movement + x;
		
		if ( newX > getGamePanel ( ).getWidth ( ) )
		{
			newX = getGamePanel ( ).getWidth ( );
			y += ROWHEIGHT;
			movement = -movement;
		}
		else if ( newX < 0 )
		{
			newX = 0;
			y = (int)getY ( ) + ROWHEIGHT;
			movement = -movement;
		}
		
		x = newX;
	}
	
}
