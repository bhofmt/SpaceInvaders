package project.model;

import java.awt.Rectangle;

import project.controller.SpaceController;
import project.view.SpacePanel;

public abstract class Entity extends Rectangle
{
	private static final long serialVersionUID = 1061935831766442067L;
	
	private SpacePanel gamePanel = SpaceController.getGamePanel ( );
	private int speed;
	
	public abstract void move ( );
	
	public Entity ( int x, int y )
	{
		this.x = x;
		this.y = y;
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
