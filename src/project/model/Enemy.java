package project.model;

public class Enemy extends Entity
{
	private static final int ROWHEIGHT = 20;
	private static final int DEFAULTSIZE = 8;
	
	/**
	 * Used to move the enemy around.
	 */
	
	private int movement = getSpeed ( );
	
	public Enemy ( )
	{
		this ( new Position ( 0, 0 ) );
	}
	
	public Enemy ( Position position )
	{
		super ( position );
		setSize ( DEFAULTSIZE );
	}
	
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
