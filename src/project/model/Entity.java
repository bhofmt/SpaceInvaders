package project.model;

import project.controller.SpaceController;
import project.view.SpacePanel;

public abstract class Entity
{
	private final SpacePanel gamePanel = SpaceController.getGamePanel ( );
	private Position position = new Position ( );
	private int speed;
	private int size;
	
	public abstract void move ( );
	
	public Entity ( )
	{
		this ( new Position ( 0, 0 ) );
	}
	
	public Entity ( Position position )
	{
		setPosition ( position );
	}
	
	public Entity ( int x, int y )
	{
		setX ( x );
		setY ( y );
	}
	
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

	public int getSize ( )
	{
		return size;
	}

	public void setSize ( int size )
	{
		this.size = size;
	}
}
