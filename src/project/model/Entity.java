package project.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import project.controller.SpaceController;
import project.view.SpacePanel;

public abstract class Entity extends Rectangle
{
	private static final long serialVersionUID = 1061935831766442067L;
	
	private SpacePanel gamePanel = SpaceController.getGamePanel ( );
	private ArrayList<Entity> targetList = new ArrayList<Entity> ( );
	private int speed;
	private Image image;
	private int healthPoints;
	
	public abstract void move ( );
	
	public Entity ( int x, int y )
	{
		this.x = x;
		this.y = y;
	}
	
	public void getHit ( Entity attacker )
	{
		if ( !targetList.contains ( attacker ) )
		{
			healthPoints--;
			targetList.add ( attacker );
		}
	}
	
	public boolean isAlive ( )
	{
		if ( healthPoints > 0 )
		{
			return true;
		}
		return false;
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
	
	public Image getImage ( )
	{
		return image;
	}
	
	public void setImage ( Image image )
	{
		this.image = image;
	}
	
	public int getHealthPoints ( )
	{
		return healthPoints;
	}
	
	public void setHealthPoints ( int healthPoints )
	{
		this.healthPoints = healthPoints;
	}
	
	public void paintEntity ( Graphics g )
	{
		g.drawImage ( image, x, y, width, height, null );
	}

	public ArrayList<Entity> getTargetList ( )
	{
		return targetList;
	}
}
