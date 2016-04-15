package project.model;

import project.controller.SpaceController;

public class Player extends Entity
{

	private static final long serialVersionUID = -3223492405755701782L;
	
	private static final int DEFAULTHEIGHT = 40;
	private static final int DEFAULTWIDTH = 40;
	private static final int DEFAULTSPEED = 5;
	private static final int DEFAULTMAXPROJECTILES = 1;
	private static final int DEFAULTSTARTINGHEALTH = 3;
	
	private int maxProjectiles;
	private String playerName;
	private boolean moveLeft = false, moveRight = false;
	private int points;
	private int projectileHealth;
	private int bossesKilled = 0;
	
	public Player ( )
	{
		this ( "Player", 0, SpaceController.getGamePanel ( ).getHeight ( ) - 50 );
	}
	
	public Player ( int x, int y )
	{
		this ( "Player", x, y );
	}
	
	public Player ( String name, int x, int y )
	{
		super ( x, y );
		playerName = name;
		height = DEFAULTHEIGHT;
		width = DEFAULTWIDTH;
		maxProjectiles = DEFAULTMAXPROJECTILES;
		setSpeed ( DEFAULTSPEED );
		setImage ( SpaceController.getInstanceOf ( ).readImage ( "images/ship.png" ));
		setHealthPoints ( DEFAULTSTARTINGHEALTH );
		projectileHealth = 1;
	}
	
	@Override
	public void move ( )
	{
		
		// Doesn't move the Player at all.
		
		if ( moveLeft == moveRight )
		{
		
		}
		
		// Moves the Player left.
		
		else if ( moveLeft )
		{
			int newX = ( int ) ( getX ( ) - getSpeed ( ) );
			if ( newX < 0 )
			{
				newX = 0;
			}
			x = newX;
			
		}
		
		// Moves the Player right.
		else
		{
			int newX = ( int ) ( getX ( ) + getSpeed ( ) );
			int panelWidth = getGamePanel ( ).getWidth ( );
			if ( newX > panelWidth - getWidth ( ) )
			{
				newX = ( int ) ( panelWidth - getWidth ( ) );
			}
			x = newX;
		}
	}
	
	public void shoot ( )
	{
		if ( canFire ( ) )
		{
			Projectile projectile = new Projectile ( ( int ) ( getX ( ) + getWidth ( ) / 2 ), ( int ) getY ( ) - 2, projectileHealth );
			projectile.x -= projectile.getWidth ( ) / 2;
			
			SpaceController controller = SpaceController.getInstanceOf ( );
			controller.addProjectile ( projectile );
		}
	}
	
	public boolean canFire ( )
	{
		SpaceController controller = SpaceController.getInstanceOf ( );
		if ( controller.getProjectiles ( ).size ( ) < maxProjectiles )
		{
			return true;
		}
		return false;
	}
	
	public int getSidePoints ( )
	{
		// TODO calculate side points
		int score = 0;
		return score;
	}
	
	public void killedBoss ( )
	{
		bossesKilled++;
	}
	
	public void addScore ( int score )
	{
		points += score;
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
	
	public int getMaxProjectiles ( )
	{
		return maxProjectiles;
	}
	
	public void setMaxProjectiles ( int maxProjectiles )
	{
		this.maxProjectiles = maxProjectiles;
	}

	public int getPoints ( )
	{
		return points;
	}

	public void setPoints ( int points )
	{
		this.points = points;
	}

	public int getProjectileHealth ( )
	{
		return projectileHealth;
	}

	public void setProjectileHealth ( int projectileHealth )
	{
		this.projectileHealth = projectileHealth;
	}

	public int getBossesKilled ( )
	{
		return bossesKilled;
	}
	
}
