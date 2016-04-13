package project.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

import project.controller.SpaceController;

public class Enemy extends Entity
{
	private static final long serialVersionUID = 5919647216294900080L;
	
	private static final int DEFAULTHEIGHT = 20;
	private static final int DEFAULTWIDTH = 28;
	private static final int ROWHEIGHT = 40;
	private static final int MAXROWS = 5;
	private static final int DEFAULTSTARTINGHEALTH = 1;
	
	private int rowCounter = 0;
	private int points;
	
	/**
	 * Used to move the enemy around.
	 */
	
	private int movement;
	
	public Enemy ( int x, int y, int speed )
	{
		this ( x, y, speed, DEFAULTSTARTINGHEALTH );
	}
	
	public Enemy ( int x, int y, int speed, int healthPoints )
	{
		this ( x, y, speed, healthPoints, Enemy.getRandomEnemyImage ( ), DEFAULTWIDTH, DEFAULTHEIGHT );
	}
	
	public Enemy ( int x, int y, int speed, int healthPoints, Image image, int width, int height )
	{
		super ( x, y );
		this.height = height;
		this.width = width;
		setSpeed ( speed );
		movement = getSpeed ( );
		setImage ( image );
		setHealthPoints ( healthPoints );
	}
	
	@Override
	public void move ( )
	{
		int newX = movement + x;
		
		if ( newX > getGamePanel ( ).getWidth ( ) - getWidth ( ) )
		{
			newX = ( int ) ( getGamePanel ( ).getWidth ( ) - getWidth ( ) );
			y += ROWHEIGHT;
			movement = -movement;
			rowCounter++;
		}
		else if ( newX < 0 )
		{
			newX = 0;
			y = ( int ) getY ( ) + ROWHEIGHT;
			movement = -movement;
			rowCounter++;
		}
		
		x = newX;
		
		if ( rowCounter >= MAXROWS )
		{
			SpaceController.getInstanceOf ( ).enemyPassed ( this );
		}
	}
	
	private static Image getRandomEnemyImage ( )
	{
		ArrayList<Image> possibleImages = new ArrayList<Image> ( );
		possibleImages.add ( SpaceController.getInstanceOf ( ).readImage ( "images/alien.png" ) );
		possibleImages.add ( SpaceController.getInstanceOf ( ).readImage ( "images/alien2.png" ) );
		
		Collections.shuffle ( possibleImages );
		
		return possibleImages.get ( 0 );
	}

	public int getPoints ( )
	{
		return points;
	}

	public void setPoints ( int points )
	{
		this.points = points;
	}
	
}
