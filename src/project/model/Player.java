package project.model;

public class Player extends Entity
{
	private static final long serialVersionUID = -3223492405755701782L;
	
	private static final int DEFAULTHEIGHT = 10;
	private static final int DEFAULTWIDTH = 10;
	
	private String playerName;
	private boolean moveLeft = false, moveRight = false;
	
	public Player ( )
	{
		this ( "Player" );
	}
	
	public Player ( String name )
	{
		super ( 0, 400 );
		playerName = name;
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
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
			int newX = (int) ( getX ( ) - getSpeed ( ) );
			if ( newX < 0 )
			{
				newX = 0;
			}
			x = newX;
		}
		
		// Moves the Player left.
		
		else
		{
			int newX = (int) ( getX ( ) + getSpeed ( ) );
			int panelWidth = getGamePanel ( ).getWidth ( );
			if ( newX > panelWidth )
			{
				newX = panelWidth;
			}
			x = newX;
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
	
	public void shoot ( )
	{
		//TODO
	}
	
}
