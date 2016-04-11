package project.model;

public class Player extends Entity
{
	private static final int DEFAULTSIZE = 10;
	
	private String playerName;
	private boolean moveLeft = false, moveRight = false;
	
	public Player ( )
	{
		this ( "Player" );
	}
	
	public Player ( String name )
	{
		super ( new Position ( 0, 400 ) );
		playerName = name;
		this.setSize ( DEFAULTSIZE );
	}
	
	@Override
	public void move ( )
	{
		
		// Doesn't move the Player at all.
		
		if ( moveLeft == moveRight )
		{
		
		}
		
		// Moves the Player right.
		
		else if ( moveLeft )
		{
			int newX = getX ( ) - getSpeed ( );
			if ( newX < 0 )
			{
				newX = 0;
			}
			setX ( newX );
		}
		
		// Moves the Player left.
		
		else
		{
			int newX = getX ( ) + getSpeed ( );
			int panelWidth = getGamePanel ( ).getWidth ( );
			if ( newX > panelWidth )
			{
				newX = panelWidth;
			}
			setX ( newX );
		}
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
