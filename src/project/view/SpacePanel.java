package project.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import project.controller.SpaceController;
import project.model.Enemy;
import project.model.Entity;
import project.model.Player;
import project.model.Projectile;

public class SpacePanel extends JPanel
{
	private static final long serialVersionUID = -3891267326305936579L;
	private SpaceController controller;
	private String gameOverText = "";
	private String waveTimerText = "";
	
	public SpacePanel ( )
	{
		controller = SpaceController.getInstanceOf ( );
		setFocusable ( true );
		requestFocusInWindow ( true );
	}
	
	@Override
	protected void paintComponent ( Graphics g )
	{
		Graphics2D g2d = ( Graphics2D ) g;
		super.paintComponent ( g2d );
		
		ArrayList<Enemy> enemies = controller.getEnemies ( );
		ArrayList<Projectile> projectiles = controller.getProjectiles ( );
		ArrayList<Entity> entities = new ArrayList<Entity> ( );
		Player player = controller.getPlayer ( );
		
		for ( Enemy enemy : enemies )
		{
			if ( enemy instanceof Entity )
			{
				entities.add ( enemy );
			}
		}
		
		for ( Projectile projectile : projectiles )
		{
			if ( projectile instanceof Entity )
			{
				entities.add ( projectile );
			}
		}
		
		if ( player instanceof Entity )
		{
			entities.add ( player );
		}
		
		// Draw all entities.
		for ( Entity entity : entities )
		{
			entity.paintEntity ( g2d );
		}
		
		g2d.setColor ( Color.WHITE );
		
		int stringWidth = 0;
		int stringAccent = 0;
		int xCoordinate = 0;
		int yCoordinate = 0;
		
		/** Centering the text */
		// Change the fontSize.
		g2d.setFont ( g.getFont ( ).deriveFont ( 20F ) );
		// get the FontMetrics for the current font
		FontMetrics fm = g2d.getFontMetrics ( );
		// find the center location to display
		stringWidth = fm.stringWidth ( gameOverText );
		stringAccent = fm.getAscent ( );
		// get the position of the leftmost character in the baseline
		xCoordinate = getWidth ( ) / 2 - stringWidth / 2;
		yCoordinate = getHeight ( ) / 2 + stringAccent / 2;
		
		// draw String
		g2d.drawString ( gameOverText, xCoordinate, yCoordinate );
		
		// Change the fontSize.
		g2d.setFont ( g.getFont ( ).deriveFont ( 20F ) );
		// get the FontMetrics for the current font
		fm = g2d.getFontMetrics ( );
		
		/** Putting the text in the corner */
		// Change the fontSize.
		g2d.setFont ( g.getFont ( ).deriveFont ( 40F ) );
		// get the FontMetrics for the current font
		fm = g2d.getFontMetrics ( );
		// find the center location to display
		stringWidth = fm.stringWidth ( waveTimerText );
		stringAccent = fm.getAscent ( );
		// get the position of the leftmost character in the baseline
		xCoordinate = getWidth ( ) - stringWidth - 10;
		yCoordinate = getHeight ( ) - 10;
		
		// draw String
		g2d.drawString ( waveTimerText, xCoordinate, yCoordinate );
	}
	
	public void setGameOverText ( String s )
	{
		gameOverText = s;
	}
	
	public void setWaveTimerText ( String s )
	{
		waveTimerText = s;
	}
}
