package project.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import project.controller.SpaceController;

public class SpaceView extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SpaceView gui = null;
	private SpaceController controller = null;
	private SpacePanel gamePanel = null;
	
	private SpaceView ( )
	{
	
	}
	
	public static SpaceView getInstanceOf ( )
	{
		if ( gui == null )
		{
			gui = new SpaceView ( );
			return gui;
		}
		
		else
		{
			return gui;
		}
	}
	
	private static void centerFrameAndMakeItVisible ( JFrame frame )
	{
		Dimension windowSize = frame.getSize ( );
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ( );
		Point centerPoint = ge.getCenterPoint ( );
		
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		
		frame.setLocation ( dx, dy );
		frame.setVisible ( true );
		frame.setResizable ( false );
	}
	
	private static void initiateGui ( )
	{
		gui = getInstanceOf ( );
		
		gui.setLayout ( new BorderLayout ( ) );
		gui.setSize ( 1200, 800 );
		gui.setTitle ( "Space Invaders" );
		gui.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		gui.setAutoRequestFocus ( false );
		
		centerFrameAndMakeItVisible ( gui );
		
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher ( )
		{
			@Override
			public boolean dispatchKeyEvent ( final KeyEvent ke )
			{
				SpaceController ctrl = SpaceController.getInstanceOf ( );
				
				if ( ke.getKeyCode ( ) == KeyEvent.KEY_PRESSED )
				{
					switch ( ke.getKeyChar ( ) )
					{
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						ctrl.movePlayerLeft ( true );
						break;
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						ctrl.movePlayerRight ( true );
						break;
					}
					return true;
				}
				else if ( ke.getKeyCode ( ) == KeyEvent.KEY_RELEASED )
				{
					switch ( ke.getKeyChar ( ) )
					{
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						ctrl.movePlayerLeft ( false );
						break;
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						ctrl.movePlayerRight ( false );
						break;
					}
					return true;
				}
				
				return false;
				
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager ( ).addKeyEventDispatcher ( keyEventDispatcher );
	}
	
	public static void main ( String[] args )
	{
		initiateGui ( );
	}

	public SpacePanel getGamePanel ( )
	{
		return gamePanel;
	}
	
}
