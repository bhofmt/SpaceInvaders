package project.controller;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import project.database.ConnectionPacket;
import project.database.DatabaseManager;
import project.database.ResultSetPacket;
import project.database.StatementExecutor;
import project.listeners.TimeListener;
import project.model.Enemy;
import project.model.Entity;
import project.model.Player;
import project.model.Projectile;
import project.model.Spaceship;
import project.thread.Time;
import project.view.SpaceDialog;
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
	private static Connection conn = null;
	
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
	
	public static void destroyController ( )
	{
		controller = null;
	}
	
	private void moveActions ( )
	{
		ArrayList<Projectile> cloneProjectiles = controller.cloneArrayList ( projectiles );
		ArrayList<Enemy> cloneEnemies = controller.cloneArrayList ( enemies );
		
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
		
		String healthText = "x " + Integer.toString ( player.getHealthPoints ( ) );
		
		// Assembles the timer text for the next spawn.
		
		String waveTimerText = "";
		if ( nextWaveIsComing )
		{
			String timeText = Integer.toString ( timeUntilNextSpawn );
			String seconds = "";
			String splitSeconds = "";
			if ( timeText.length ( ) > 3 )
			{
				seconds += timeText.substring ( 0, timeText.length ( ) - 3 );
			}
			else if ( timeText.length ( ) == 3 )
			{
				seconds += timeText.substring ( 0, 1 );
			}
			
			splitSeconds = timeText.substring ( seconds.length ( ) );
			
			if ( splitSeconds.length ( ) < 2 )
			{
				splitSeconds = "0" + splitSeconds;
			}
			
			if ( seconds.length ( ) <= 1 )
			{
				waveTimerText = "0" + waveTimerText;
			}
			
			if ( seconds.length ( ) < 1 )
			{
				seconds += "0";
			}
			
			waveTimerText += seconds + ":" + splitSeconds;
		}
		
		else
		{
			waveTimerText += "00:00";
		}
		
		waveTimerText = "Next enemy in: " + waveTimerText;
		
		gui.changeCannonStatusText ( cannonText );
		gui.changeLevelStatusText ( "<html>Level: " + getLevel ( ) + "</html>" );
		gamePanel.setWaveTimerText ( waveTimerText );
		gamePanel.setHealthText ( healthText );
		gamePanel.setMaxShotsText ( "Max shots: " + Integer.toString ( controller.playerMaxShots ( ) ) );
		gamePanel.setScoreText ( "Score: " + Integer.toString ( controller.getPlayerScore ( ) ) );
		
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
		hitDetectionActions ( );
		checkActions ( );
		updateActions ( );
		levelActions ( );
	}
	
	/**
	 * Spawns the next enemy.
	 */
	public void spawnNextEnemy ( )
	{
		Random random = new Random ( );
		incomingEnemies--;
		if ( incomingEnemies <= 0 )
		{
			nextWaveIsComing = false;
			timeUntilNextSpawn = 500;
		}
		else
		{
			timeUntilNextSpawn = random.nextInt ( 60 ) + 60;
		}
		
		Enemy enemy;
		
		if ( level % 5 == 0 )
		{
			// Boss-type enemies in their own wave are slower than those spawned
			// randomly, but are obviously more numerous.
			enemy = new Spaceship ( 0, 60, random.nextInt ( 3 ) + 2, 3 + ( level / 5 ) );
		}
		else if ( level < 10 )
		{
			enemy = new Enemy ( 0, 60, random.nextInt ( 2 ) + 3 );
		}
		else
		{
			int dice = random.nextInt ( 10 );
			if ( dice < 1 )
			{
				enemy = new Spaceship ( 0, 60, random.nextInt ( 2 ) + 3, 3 + ( level / 5 ) );
			}
			else
			{
				enemy = new Enemy ( 0, 60, random.nextInt ( 2 ) + 3 );
			}
		}
		addEnemy ( enemy );
	}
	
	public void hitDetectionActions ( )
	{
		ArrayList<Projectile> cloneProjectiles = controller.cloneArrayList ( projectiles );
		ArrayList<Enemy> cloneEnemies = controller.cloneArrayList ( enemies );
		
		for ( Projectile projectile : cloneProjectiles )
		{
			for ( Enemy enemy : cloneEnemies )
			{
				if ( attackerHitsTarget ( projectile, enemy ) )
				{
					if ( projectile.getTargetList ( ).size ( ) < projectile.getMaxHealth ( ) )
					{
						enemy.getHit ( projectile );
						projectile.getHit ( enemy );
						
						cleanDead ( enemy, enemies );
						cleanDead ( projectile, projectiles );
					}
				}
			}
		}
	}
	
	public <T> void cleanDead ( Entity target, ArrayList<T> targetList )
	{
		if ( !target.isAlive ( ) )
		{
			targetList.remove ( target );
			if ( target instanceof Enemy )
			{
				player.addScore ( ( ( Enemy ) target ).getPoints ( ) );
			}
			
			if ( target instanceof Spaceship )
			{
				player.killedBoss ( );
			}
		}
	}
	
	/**
	 * Performs all wave-related actions.
	 */
	public void waveActions ( )
	{
		if ( timeUntilNextSpawn > 0 )
		{
			timeUntilNextSpawn--;
		}
		else
		{
			spawnNextEnemy ( );
		}
	}
	
	/**
	 * Initiates the next spawn wave
	 */
	public void nextLevel ( )
	{
		if ( player.getHealthPoints ( ) > 0 )
		{
			nextWaveIsComing = true;
			timeUntilNextSpawn = 500;
			level++;
			if ( level % 5 == 0 && level > 0 )
			{
				incomingEnemies = level / 5;
			}
			else
			{
				incomingEnemies = level + 1;
			}
		}
	}
	
	/**
	 * Checks if the level was cleared.
	 */
	public boolean levelWasCleared ( )
	{
		if ( enemies.size ( ) == 0 && !nextWaveIsComing )
		{
			if ( level % 5 == 0 && level > 0 )
			{
				player.setMaxProjectiles ( player.getMaxProjectiles ( ) + 1 );
				player.setHealthPoints ( player.getHealthPoints ( ) + 1 );
				player.setProjectileHealth ( player.getProjectileHealth ( ) + 1 );
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Makes the player shoot.
	 */
	public void makePlayerShoot ( )
	{
		player.shoot ( );
	}
	
	/**
	 * Is performed when an enemy manages to get completely through.
	 */
	public void enemyPassed ( Entity enemy )
	{
		enemies.remove ( enemy );
		if ( enemy instanceof Spaceship )
		{
			player.setHealthPoints ( player.getHealthPoints ( ) - 2 );
		}
		else
		{
			player.setHealthPoints ( player.getHealthPoints ( ) - 1 );
		}
		
		if ( !player.isAlive ( ) )
		{
			gameOver ( );
		}
	}
	
	public void gameOver ( )
	{
		timer.requestPause ( );
		getGamePanel ( ).setGameOverText ( "Game Over | Press Enter to submit your score" );
		KeyListener enterListener = new KeyListener ( )
		{
			
			@Override
			public void keyTyped ( KeyEvent e )
			{
			
			}
			
			@Override
			public void keyPressed ( KeyEvent e )
			{
				if ( e.getKeyCode ( ) == KeyEvent.VK_ENTER )
				{
					getGamePanel ( ).removeKeyListener ( this );
					SpaceView.initiateGameOverMenu ( );
				}
			}
			
			@Override
			public void keyReleased ( KeyEvent e )
			{
			
			}
			
		};
		getGamePanel ( ).addKeyListener ( enterListener );
	}
	
	/**
	 * Returns the player's max amounth of projectiles.
	 */
	public int playerMaxShots ( )
	{
		return player.getMaxProjectiles ( );
	}
	
	/**
	 * Returns the player's score.
	 */
	public int playerScore ( )
	{
		return player.getPoints ( );
	}
	
	/**
	 * Returns true if the attacker hits ( / intersects with) the target.
	 */
	public boolean attackerHitsTarget ( Entity attacker, Entity target )
	{
		if ( attacker.intersects ( target ) )
		{
			return true;
		}
		return false;
	}
	
	public void addEnemy ( Enemy enemy )
	{
		enemies.add ( enemy );
	}
	
	public void addProjectile ( Projectile projectile )
	{
		projectiles.add ( projectile );
	}
	
	/**
	 * Returns an ArrayList with the highscore data. The output's format looks
	 * like this: {username, level, bossesKilled, score, sideScore, sideString}
	 */
	public ArrayList<String> getHighscoreData ( String username, String playerSide )
	{
		ArrayList<String> results = new ArrayList<String> ( );
		results.add ( username );
		results.add ( Integer.toString ( getLevel ( ) ) );
		results.add ( Integer.toString ( player.getBossesKilled ( ) ) );
		results.add ( Integer.toString ( player.getPoints ( ) ) );
		results.add ( Integer.toString ( player.getSidePoints ( ) ) );
		results.add ( playerSide );
		return results;
	}
	
	public ArrayList<String> getPossibleSides ( )
	{
		ArrayList<String> results = new ArrayList<> ( );
		ResultSetPacket rp = StatementExecutor.select ( conn, "side", "side", "", new ArrayList<Object> ( ) );
		ResultSet rs = rp.getResultSet ( );
		try
		{
			while ( rs.next ( ) )
			{
				results.add ( rs.getString ( "side" ) );
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace ( );
		}
		return results;
	}
	
	public int getPlayerScore ( )
	{
		return player.getPoints ( );
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
	
	public static void createConnection ( )
	{
		new SpaceDialog ( SpaceView.getInstanceOf ( ) );
	}
	
	public static String[] connect ( String adress, String username, String password )
	{
		ConnectionPacket cp = DatabaseManager.getConnection ( adress, username, password );
		setConn ( cp.getConnection ( ) );
		return cp.getErrors ( );
	}
	
	@SuppressWarnings (
	{ "rawtypes", "unchecked" } )
	public static void insertHighscoreDataIntoDatabase ( ArrayList data )
	{
		ArrayList<Object> selectParam = new ArrayList<Object> ( );
		ArrayList<Object> results = new ArrayList<Object> ( );
		selectParam.add ( data.get ( data.size ( ) - 1 ) );
		
		ResultSet rs = ( StatementExecutor.select ( conn, "side", "id", "side = ?", selectParam ) ).getResultSet ( );
		try
		{
			while ( rs.next ( ) )
			{
				results.add ( rs.getString ( "id" ) );
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace ( );
		}
		
		data.set ( data.size ( ) - 1, results.get ( 0 ) );
		StatementExecutor.insert ( conn, "spaceintruders", "username, level, bosses_killed, score, side_score, side_id", "?, ?, ?, ?, ?, ?", data );
		for ( Object dataEntry : data )
		{
			System.out.println ( dataEntry );
		}
	}
	
	public static String getHighscoreHTMLText ( )
	{
		// CSS formatting for the highscore ledger.
		String s =
					"<html><head><style>"
					+ "th, td{text-align: left;padding-top: 8px;padding-right:40px;padding-bottom:8px;padding-left:40px;} td {color: black} th {font-size: 20pt}"
					+ "tr { background-color: #f2f2f2 }" + "th{background-color: #4CAF50;" + "color: white;" + "}" + "</style></head>";
					
		// The actual table.
		s +=
				"<body><table><tr><th>Username</th><th>Level</th><th>Zerstörte Bosse</th><th>Punkte</th><th>Punkte für die Seite</th><th>Seite</th></tr>";
		ResultSet rs =
						( StatementExecutor
											.select (	conn, "spaceintruders as si JOIN side AS s ON si.side_id = s.id ORDER BY score DESC",
														"username, level, bosses_killed, score, side_score, s.side", "", null )
											.getResultSet ( ) );
											
		try
		{
			while ( rs.next ( ) )
			{
				s +=
						"<tr><td>"
						+ rs.getString ( "username" ) + "</td><td>" + rs.getString ( "level" ) + "</td><td>" + rs.getString ( "bosses_killed" )
						+ "</td><td>" + rs.getString ( "score" ) + "</td><td>" + rs.getString ( "side_score" ) + "</td><td>" + rs.getString ( "side" )
						+ "</td></tr>";
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace ( );
		}
		s += "</table></body></html>";
		return s;
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
	
	public static Connection getConn ( )
	{
		return conn;
	}
	
	public static void setConn ( Connection conn )
	{
		SpaceController.conn = conn;
	}
	
	public Image readImage ( String path )
	{
		InputStream stream;
		BufferedImage image = null;
		
		stream = getClass ( ).getClassLoader ( ).getResourceAsStream ( path );
		
		try
		{
			image = ImageIO.read ( stream );
		}
		catch ( IOException e )
		{
			System.err.println ( "Couldn't load the Texture! Cause: " + e.getMessage ( ) );
			System.exit ( 67 );
		}
		catch ( IllegalArgumentException e )
		{
			System.err.println ( "Couldn't read the Texture! Cause: " + e.getMessage ( ) );
			System.exit ( 68 );
		}
		
		return image;
	}
}
