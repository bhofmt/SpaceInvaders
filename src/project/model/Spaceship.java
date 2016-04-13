package project.model;

import java.awt.Color;
import java.awt.Graphics;

import project.controller.SpaceController;

public class Spaceship extends Enemy
{
	private static final int DEFAULTSTARTINGHEALTH = 3;
	private static final int DEFAULTHEIGHT = 20;
	private static final int DEFAULTWIDTH = 30;
	
	public Spaceship ( int x, int y, int speed )
	{
		this ( x, y, speed, DEFAULTSTARTINGHEALTH );
	}
	
	public Spaceship ( int x, int y, int speed, int healthPoints )
	{
		super ( x, y, speed, healthPoints, SpaceController.getInstanceOf ( ).readImage ( "images/spaceship.png" ), DEFAULTWIDTH, DEFAULTHEIGHT );
	}
	
	/* 
	 * Paints the entity and a health bar.
	 */
	@Override
	public void paintEntity ( Graphics g )
	{
		super.paintEntity ( g );
		
		// Draws the border of the health bar.

		int boxX = x;
		int boxY = y - 8;
		int boxWidth = width;
		int boxHeight = 8;
		
		g.setColor ( Color.WHITE );
		g.drawRect ( boxX, boxY, boxWidth, boxHeight );
		
		// Draws the red background of the health bar.

		boxX = x - 1;
		boxY = y - 7;
		boxWidth = width - 2;
		boxHeight = 6;
		
		g.setColor ( Color.RED );
		g.drawRect ( boxX, boxY, boxWidth, boxHeight );
		
		// Draws the green overlay that displays the remaining health.

		boxX = x - 1;
		boxY = y - 7;
		boxWidth = ( width - 2 ) * ( getHealthPoints ( ) / DEFAULTSTARTINGHEALTH );
		boxHeight = 6;

		g.setColor ( Color.WHITE );
		g.drawRect ( boxX, boxY, boxWidth, boxHeight );
	}
}
