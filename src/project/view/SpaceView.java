package project.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.controller.SpaceController;
import project.model.Player;
import project.thread.Time;

public class SpaceView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final JLabel levelStatus = new JLabel ( );
	private static final JLabel cannonStatus = new JLabel ( );
	
	private static SpaceView gui = null;
	private static SpaceController controller = SpaceController.getInstanceOf ( );
	private static SpacePanel gamePanel = null;
	
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
	
	private static void addKeyListener ( )
	{
		KeyListener keyListener = new KeyListener ( )
		{
			private SpaceController ctrl = SpaceController.getInstanceOf ( );
			
			@Override
			public void keyTyped ( KeyEvent e )
			{
				// Do nothing.
			}
			
			@Override
			public void keyPressed ( KeyEvent e )
			{
				switch ( e.getKeyCode ( ) )
				{
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					ctrl.movePlayerLeft ( true );
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					ctrl.movePlayerRight ( true );
					break;
				case KeyEvent.VK_SPACE:
					ctrl.makePlayerShoot ( );
					break;
				}
			}
			
			@Override
			public void keyReleased ( KeyEvent e )
			{
				switch ( e.getKeyCode ( ) )
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
			}
			
		};
		
		gamePanel.addKeyListener ( keyListener );
	}
	
	private static void addGameEnvironment ( )
	{
		JPanel borderPanel = new JPanel ( new BorderLayout ( ) );
		JPanel userInterface = new JPanel ( );
		Dimension expectedDimension = new Dimension ( gui.getWidth ( ) - 100, gui.getHeight ( ) - 100 );
		gamePanel = new SpacePanel ( );
		
		borderPanel.setPreferredSize ( expectedDimension );
		borderPanel.setMaximumSize ( expectedDimension );
		borderPanel.setMinimumSize ( expectedDimension );
		
		borderPanel.setBackground ( Color.RED );
		
		Box box = new Box ( BoxLayout.Y_AXIS );
		
		box.add ( Box.createVerticalGlue ( ) );
		box.add ( borderPanel );
		box.add ( Box.createVerticalGlue ( ) );
		box.setBackground ( Color.GRAY );
		box.setOpaque ( true );
		
		userInterface.setBackground ( Color.BLACK );
		userInterface.setPreferredSize ( new Dimension ( borderPanel.getWidth ( ), 100 ) );
		
		gamePanel.setBackground ( Color.DARK_GRAY );
		
		cannonStatus.setForeground ( Color.WHITE );
		userInterface.add ( cannonStatus );
		
		levelStatus.setForeground ( Color.WHITE );
		userInterface.add ( levelStatus );
		
		borderPanel.add ( gamePanel, BorderLayout.CENTER );
		borderPanel.add ( userInterface, BorderLayout.NORTH );
		
		gui.add ( box, BorderLayout.CENTER );
	}
	
	private static void initiateGui ( )
	{
		gui = getInstanceOf ( );
		
		gui.setLayout ( new BorderLayout ( ) );
		gui.setSize ( 1200, 800 );
		gui.setTitle ( "Space Invaders" );
		gui.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		gui.setAutoRequestFocus ( false );
		gui.setResizable ( false );
		
		addGameEnvironment ( );
		
		centerFrameAndMakeItVisible ( gui );
		addKeyListener ( );
		
		gui.validate ( );
		gui.repaint ( );
	}
	
	public void changeLevelStatusText ( )
	{
		levelStatus.setText ( "<html>Level: " + controller.getLevel ( ) + "</html>" );
	}
	
	public void changeCannonStatusText ( String s )
	{
		cannonStatus.setText ( s );
	}
	
	public static void main ( String[] args )
	{
		initiateGui ( );
		
		controller.setPlayer ( new Player ( 0, gamePanel.getHeight ( ) - 50 ) );
		
		Time timemaker = new Time ( );
		controller.setTimer ( timemaker );
		
		timemaker.addListener ( controller );
		timemaker.start ( );
	}
	
	public static SpacePanel getGamePanel ( )
	{
		return gamePanel;
	}
	
}
