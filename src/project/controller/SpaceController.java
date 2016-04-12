package project.controller;

import java.util.ArrayList;

import project.listeners.TimeListener;
import project.model.Enemy;
import project.model.Player;
import project.model.Projectile;
import project.view.SpacePanel;
import project.view.SpaceView;

public class SpaceController implements TimeListener
{
	private static SpaceController controller = null;
	
	private Player player = null;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy> ( );
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile> ( );
	
	private SpaceController ( )
	{
	
	}
	
	public static SpaceController getInstanceOf ( )
	{
		if ( controller != null )
		{
			return controller;
		}
		else
		{
			controller = new SpaceController ( );
			return controller;
		}
	}
	
	private void moveActions ( )
	{
		if ( player != null )
		{
			player.move ( );
		}
		for ( Enemy enemy : enemies )
		{
			enemy.move ( );
		}
		for ( Projectile projectile : projectiles )
		{
			projectile.move ( );
		}
	}
	
	private void checkActions ( )
	{
		
		ArrayList<Projectile> clone = new ArrayList<Projectile> ( );
		for ( Projectile projectile : projectiles )
		{
			clone.add ( projectile );
		}
		
		for ( Projectile projectile : clone )
		{
			if ( projectile.getY ( ) <= 0 )
			{
				projectiles.remove ( projectile );
			}
		}
	}
	
	private void updateActions ( )
	{
		SpaceView gui = SpaceView.getInstanceOf ( );
		gui.validate ( );
		gui.repaint ( );
		
		SpacePanel gamePanel = getGamePanel ( );
		gamePanel.validate ( );
		gamePanel.repaint ( );
	}
	
	public void timeTick ( )
	{
		moveActions ( );
		checkActions ( );
		updateActions ( );
	}
	
	public void makePlayerShoot ( )
	{
		player.shoot ( );
	}
	
	public void addEnemy ( Enemy enemy )
	{
		enemies.add ( enemy );
	}
	
	public void addProjectile ( Projectile projectile )
	{
		projectiles.add ( projectile );
	}
	
	public static SpacePanel getGamePanel ( )
	{
		return SpaceView.getGamePanel ( );
	}
	
	public static SpaceView getGui ( )
	{
		return SpaceView.getInstanceOf ( );
	}
	
	public Player getPlayer ( )
	{
		return player;
	}
	
	public void setPlayer ( Player player )
	{
		this.player = player;
	}
	
	public void movePlayerLeft ( boolean b )
	{
		player.movesLeft ( b );
	}
	
	public void movePlayerRight ( boolean b )
	{
		player.movesRight ( b );
	}
	
	public ArrayList<Enemy> getEnemies ( )
	{
		return enemies;
	}
	
	public ArrayList<Projectile> getProjectiles ( )
	{
		return projectiles;
	}
}
