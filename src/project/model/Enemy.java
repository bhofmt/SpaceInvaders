package project.model;

public class Enemy extends Entity
{
	private static final long serialVersionUID = 5919647216294900080L;
	
	private static final int DEFAULTHEIGHT = 8;
	private static final int DEFAULTWIDTH = 8;
	private static final int ROWHEIGHT = 20;
	
	public Enemy ( int x, int y, int speed )
	{
		super ( x, y );
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		setSpeed ( speed );
	}
	
	
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
			y = ( int ) getY ( ) + ROWHEIGHT;
			movement = -movement;
		}
		
		x = newX;
	}
	
}
