package project.model;

import java.awt.Color;
import java.awt.Graphics;

import project.controller.SpaceController;

public class Spaceship extends Enemy
{
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULTSTARTINGHEALTH = 3;
	private static final int DEFAULTHEIGHT = 20;
	private static final int DEFAULTWIDTH = 40;
	
	private int maxHealth;
	
	public Spaceship ( int x, int y, int speed )
	{
		this ( x, y, speed, DEFAULTSTARTINGHEALTH );
	}
	
	public Spaceship ( int x, int y, int speed, int healthPoints )
	{
		super ( x, y, speed, healthPoints, SpaceController.getInstanceOf ( ).readImage ( "images/spaceship.png" ), DEFAULTWIDTH, DEFAULTHEIGHT );
		maxHealth = healthPoints;
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
		int boxY = y - 18;
		int boxWidth = width;
		int boxHeight = 6;
		
		g.setColor ( Color.WHITE );
		g.fillRect ( boxX, boxY, boxWidth, boxHeight );
		
		// Draws the red background of the health bar.
		
		boxX = x + 1;
		boxY = y - 17;
		boxWidth = width - 2;
		boxHeight = 4;
		
		g.setColor ( Color.RED );
		g.fillRect ( boxX, boxY, boxWidth, boxHeight );
		
		// Draws the green overlay that displays the remaining health.
		
		boxX = x + 1;
		boxY = y - 17;
		boxWidth = ( int ) ( ( ( double ) ( width - 2 ) ) * ( ( double ) getHealthPoints ( ) / ( double ) maxHealth ) );
		boxHeight = 4;
		
		g.setColor ( Color.GREEN );
		g.fillRect ( boxX, boxY, boxWidth, boxHeight );
	}
}
