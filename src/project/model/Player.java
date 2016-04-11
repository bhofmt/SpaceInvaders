package project.model;

public class Player extends Entity
{
	private String playerName;
	private boolean moveLeft = false, moveRight = false;
	
	public Player ( String name )
	{
		playerName = name;
	}
	
	@Override
	void move ( )
	{
		
		// Doesn't move the Player at all.
		
		if ( moveLeft == moveRight )
		{
		
		}
		
		// Moves the Player right.
		
		else if ( moveLeft )
		{
			// TODO move left
		}
		
		// Moves the Player left.
		
		else
		{
			// TODO move right
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
