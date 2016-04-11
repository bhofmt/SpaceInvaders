package project.model;

import project.controller.SpaceController;

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
			int panelWidth = SpaceController.getGui ( ).getGamePanel ( ).getWidth ( );
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
