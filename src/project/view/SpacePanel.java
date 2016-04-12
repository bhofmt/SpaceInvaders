package project.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import project.controller.SpaceController;
import project.model.Enemy;
import project.model.Player;
import project.model.Projectile;

public class SpacePanel extends JPanel
{
	private static final long serialVersionUID = -3891267326305936579L;
	private SpaceController controller;

	public SpacePanel ()
	{
		controller = SpaceController.getInstanceOf ();
		setFocusable ( true );
		requestFocusInWindow ( true );
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		ArrayList<Enemy> enemies = controller.getEnemies ();
		ArrayList<Projectile> projectiles = controller.getProjectiles ();
		
		Player player = controller.getPlayer ();

		Graphics2D g2d = (Graphics2D) g;

		super.paintComponent ( g2d );

		g2d.setColor ( Color.RED );

		for ( Enemy enemy : enemies )
		{
			g2d.fillRect ( (int) enemy.getX (), (int) enemy.getY (), (int) enemy.getWidth (),
					(int) enemy.getHeight () );
		}

		g2d.setColor ( Color.BLUE );

		g2d.fillRect ( (int) player.getX (), (int) player.getY (), (int) player.getWidth (),
				(int) player.getHeight () );

		g2d.setColor ( Color.RED );

		for ( Projectile projectile : projectiles )
		{
			g2d.fillRect ( (int) projectile.getX (), (int) projectile.getY (), (int) projectile.getWidth (),
					(int) projectile.getHeight () );
			
			
		}
		
		for ( Projectile projectile : projectiles )
		{
			for ( Enemy enemy : enemies )
			{
				if ( projectile.intersects ( enemy ) )
				{
					enemies.remove ( enemy );
					projectiles.remove ( projectile );
					System.out.println ( "HIT" );
				}
			}
		}
	}
}
