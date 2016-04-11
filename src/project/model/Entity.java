package project.model;

import project.controller.SpaceController;
import project.view.SpacePanel;

public abstract class Entity
{
	private final SpacePanel gamePanel = SpaceController.getInstanceOf ( ).getGamePanel ( );
	private Position position;
	private int speed;
	
	abstract void move ( );
	
	public int getX ( )
	{
		return position.getX ( );
	}

	public void setX ( int x )
	{
		position.setX ( x );
	}

	public int getY ( )
	{
		return position.getY ( );
	}

	public void setY ( int y )
	{
		position.setY ( y );
	}

	public Position getPosition ( )
	{
		return position;
	}

	public void setPosition ( Position position )
	{
		this.position = position;
	}

	public int getSpeed ( )
	{
		return speed;
	}

	public void setSpeed ( int speed )
	{
		this.speed = speed;
	}

	public SpacePanel getGamePanel ( )
	{
		return gamePanel;
	}
}
