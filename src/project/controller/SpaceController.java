package project.controller;

import java.util.ArrayList;

import project.listeners.TimeListener;
import project.model.Enemy;
import project.model.Player;
import project.model.Projectile;
import project.thread.Time;
import project.view.SpacePanel;
import project.view.SpaceView;

public class SpaceController implements TimeListener
{
	private static SpaceController controller = null;
	
	private boolean nextWaveIsComing;
	private int timeUntilNextSpawn;
	private int incomingEnemies = 1;
	private int level = 0;
	private Player player = null;
	private Time timer = null;
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
		ArrayList<Projectile> cloneProjectiles = new ArrayList<Projectile> ( );
		for ( Projectile projectile : projectiles )
		{
			cloneProjectiles.add ( projectile );
		}
		ArrayList<Enemy> cloneEnemies = new ArrayList<Enemy> ( );
		for ( Enemy enemy : enemies )
		{
			cloneEnemies.add ( enemy );
		}
		
		if ( player != null )
		{
			player.move ( );
		}
		for ( Enemy enemy : cloneEnemies )
		{
			enemy.move ( );
		}
		for ( Projectile projectile : cloneProjectiles )
		{
			projectile.move ( );
		}
	}
	
	private void checkActions ( )
	{
		
		ArrayList<Projectile> clone = controller.cloneArrayList ( projectiles );
		
		for ( Projectile projectile : clone )
		{
			if ( projectile.getY ( ) <= 0 - projectile.getHeight ( ) )
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
		
		String cannonText = "<html>";
		if ( player.canFire ( ) )
		{
			cannonText += "Cannon Ready";
		}
		else
		{
			cannonText += "Overheated";
		}
		cannonText += "</html>";
		gui.changeCannonStatusText ( cannonText );
		gui.changeLevelStatusText ( );
	}
	
	private void levelActions ( )
	{
		if ( levelWasCleared ( ) )
		{
			nextLevel ( );
		}
		
		if ( nextWaveIsComing )
		{
			waveActions ( );
		}
	}
	
	public void timeTick ( )
	{
		moveActions ( );
		checkActions ( );
		updateActions ( );
		levelActions ( );
	}
	
	public void waveActions ( )
	{
		if ( timeUntilNextSpawn > 0 )
		{
			timeUntilNextSpawn--;
		}
		else
		{
			incomingEnemies--;
			if ( incomingEnemies <= 0 )
			{
				nextWaveIsComing = false;
				timeUntilNextSpawn = 10000;
			}
			else
			{
				timeUntilNextSpawn = 80;
			}
			addEnemy ( new Enemy ( 0, 0, 3 ) );
		}
	}
	
	public void nextLevel ( )
	{
		nextWaveIsComing = true;
		timeUntilNextSpawn = 1000;
		level++;
		incomingEnemies = level + 1;
	}
	
	public boolean levelWasCleared ( )
	{
		if ( enemies.size ( ) == 0 && !nextWaveIsComing )
		{
			return true;
		}
		return false;
	}
	
	public void makePlayerShoot ( )
	{
		player.shoot ( );
	}
	
	public void enemyPassed ( )
	{
		timer.requestPause ( );
		getGamePanel ( ).setGameOverText ( "Game Over" );
	}
	
	public void addEnemy ( Enemy enemy )
	{
		enemies.add ( enemy );
	}
	
	public void addProjectile ( Projectile projectile )
	{
		projectiles.add ( projectile );
	}
	
	public <T> ArrayList<T> cloneArrayList ( ArrayList<T> list )
	{
		ArrayList<T> clonedList = new ArrayList<T> ( );
		for ( T entry : list )
		{
			clonedList.add ( entry );
		}
		return clonedList;
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
	
	public Time getTimer ( )
	{
		return timer;
	}
	
	public void setTimer ( Time timer )
	{
		this.timer = timer;
	}
	
	public int getLevel ( )
	{
		return level;
	}
}
