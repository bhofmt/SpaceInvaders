package project.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.keecode.asset.AssetFactory;
import net.keecode.asset.Audio;
import project.controller.SpaceController;
import project.listeners.SideSelectionList;
import project.model.Player;
import project.sound.SoundManager;
import project.thread.Time;

public class SpaceView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final JLabel levelStatus = new JLabel ( );
	private static final JLabel cannonStatus = new JLabel ( );
	
	private static final int STARTBTNHEIGHT = 30;
	private static final int STARTBTNWIDTH = 150;
	
	private static SpaceView gui = null;
	private static SpaceController controller = null;
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
	
	public static void initiateGameOverMenu ( )
	{
		gui.getContentPane ( ).removeAll ( );
		
		ArrayList<String> possibleSides = controller.getPossibleSides ( );
		
		JPanel borderPanel = new JPanel ( new BorderLayout ( ) );
		JPanel gameOverPanel = new JPanel ( );
		JPanel inputPanel = new JPanel ( );
		JPanel listPanel = new JPanel ( );
		JPanel buttonPanel = new JPanel ( );
		JLabel userNameLabel = new JLabel ( "Enter name here:" );
		SideSelectionList sideSelection = new SideSelectionList ( possibleSides.toArray ( new String[0] ) );
		JTextField userName = new JTextField ( );
		Dimension expectedDimension = new Dimension ( gui.getWidth ( ) - 400, gui.getHeight ( ) - 600 );
		SpaceButton mainMenuButton = new SpaceButton ( "Submit HighScore" );
		
		mainMenuButton.setBounds (	( int ) ( ( gui.getWidth ( ) / 2 ) - ( STARTBTNWIDTH / 2 ) ),
									( int ) ( ( gui.getHeight ( ) / 2 ) - ( STARTBTNHEIGHT / 2 ) ), STARTBTNWIDTH, STARTBTNHEIGHT );
									
		mainMenuButton.addActionListener ( new ActionListener ( )
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				ArrayList<String> highscoreData = controller.getHighscoreData ( userName.getText ( ), sideSelection.getSelectedValue ( ) );
				SpaceController.insertHighscoreDataIntoDatabase ( highscoreData );
				initiateMainMenu ( );
			}
		} );
		mainMenuButton.setForeground ( Color.WHITE );
		mainMenuButton.setBackground ( Color.BLACK );
		mainMenuButton.setPressedBackground ( Color.decode ( "#750000" ) );
		mainMenuButton.setHoverBackground ( Color.decode ( "#2B0000" ) );
		mainMenuButton.setBorderPainted ( false );
		
		gameOverPanel.setBackground ( Color.DARK_GRAY );
		gameOverPanel.setLayout ( new BoxLayout ( gameOverPanel, BoxLayout.Y_AXIS ) );
		
		borderPanel.setPreferredSize ( expectedDimension );
		borderPanel.setMaximumSize ( expectedDimension );
		borderPanel.setMinimumSize ( expectedDimension );
		
		Image space = gui.readImage ( "images/space.jpg" );
		Box borderBox = new Box ( BoxLayout.Y_AXIS)
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent ( Graphics g )
			{
				super.paintComponent ( g );
				
				Graphics2D g2d = ( Graphics2D ) g;
				
				g2d.drawImage ( space, 0, 0, null );
			}
		};
		
		borderBox.add ( Box.createVerticalGlue ( ) );
		borderBox.add ( borderPanel );
		borderBox.add ( Box.createVerticalGlue ( ) );
		borderBox.setBackground ( Color.GRAY );
		borderBox.setOpaque ( true );
		
		int sideSelectionWidth = possibleSides.size ( ) * 30;
		sideSelection.setFont ( sideSelection.getFont ( ).deriveFont ( 20.0F ) );
		sideSelection.setPreferredSize ( new Dimension ( 160, sideSelectionWidth ) );
		
		userNameLabel.setForeground ( Color.WHITE );
		inputPanel.add ( userNameLabel );
		
		userName.setFont ( userName.getFont ( ).deriveFont ( 18 ) );
		userName.setPreferredSize ( new Dimension ( 300, 20 ) );
		inputPanel.add ( userName );
		
		listPanel.setOpaque ( false );
		listPanel.setPreferredSize ( new Dimension ( gameOverPanel.getWidth ( ), sideSelection.getHeight ( ) ) );
		listPanel.add ( sideSelection );
		
		buttonPanel.setOpaque ( false );
		buttonPanel.add ( mainMenuButton );
		
		inputPanel.setOpaque ( false );
		
		gameOverPanel.add ( Box.createRigidArea ( new Dimension ( 0, 40 ) ) );
		gameOverPanel.add ( inputPanel );
		gameOverPanel.add ( listPanel );
		gameOverPanel.add ( buttonPanel );
		gameOverPanel.add ( Box.createVerticalGlue ( ) );
		
		borderPanel.add ( gameOverPanel );
		
		gui.add ( borderBox );
		
		gui.validate ( );
		gui.repaint ( );
	}
	
	public static void initiateMainMenu ( )
	{
		gui.getContentPane ( ).removeAll ( );
		ActionListener startGameLSTN = new ActionListener ( )
		{
			
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				if ( e.getActionCommand ( ).equals ( "Spiel starten" ) )
				{
					initiateGameEnvironment ( );
				}
				else if ( e.getActionCommand ( ).equals ( "Spiel beenden" ) )
				{
					System.exit ( 0 );
				}
				
				else if ( e.getActionCommand ( ).equals ( "Highscores" ) )
				{
					initiateHighscoreLedger ( );
				}
			}
		};
		
		JPanel mainMenu = new MainMenu ( null );
		SpaceButton startGameBTN = new SpaceButton ( "Spiel starten" );
		SpaceButton highscoreBTN = new SpaceButton ( "Highscores" );
		SpaceButton exitGameBTN = new SpaceButton ( "Spiel beenden" );
		
		startGameBTN.setBounds (	( int ) ( ( gui.getWidth ( ) / 2 ) - ( STARTBTNWIDTH / 2 ) ),
									( int ) ( ( gui.getHeight ( ) / 2 ) - ( STARTBTNHEIGHT / 2 ) ), STARTBTNWIDTH, STARTBTNHEIGHT );
									
		highscoreBTN.setBounds (	( int ) ( ( gui.getWidth ( ) / 2 ) - ( STARTBTNWIDTH / 2 ) ),
									( int ) ( ( gui.getHeight ( ) / 2 ) - ( STARTBTNHEIGHT / 2 ) + 150 ), STARTBTNWIDTH, STARTBTNHEIGHT );
									
		exitGameBTN.setBounds (	( int ) ( ( gui.getWidth ( ) / 2 ) - ( STARTBTNWIDTH / 2 ) ),
								( int ) ( ( gui.getHeight ( ) / 2 ) - ( STARTBTNHEIGHT / 2 ) + 250 ), STARTBTNWIDTH, STARTBTNHEIGHT );
								
		mainMenu.setBounds ( 0, 0, gui.getWidth ( ), gui.getHeight ( ) );
		
		startGameBTN.setForeground ( Color.WHITE );
		startGameBTN.setBackground ( Color.BLACK );
		startGameBTN.setPressedBackground ( Color.decode ( "#750000" ) );
		startGameBTN.setHoverBackground ( Color.decode ( "#2B0000" ) );
		startGameBTN.setBorderPainted ( false );
		
		highscoreBTN.setForeground ( Color.WHITE );
		highscoreBTN.setBackground ( Color.BLACK );
		highscoreBTN.setPressedBackground ( Color.decode ( "#750000" ) );
		highscoreBTN.setHoverBackground ( Color.decode ( "#2B0000" ) );
		highscoreBTN.setBorderPainted ( false );
		
		exitGameBTN.setForeground ( Color.WHITE );
		exitGameBTN.setBackground ( Color.BLACK );
		exitGameBTN.setPressedBackground ( Color.decode ( "#750000" ) );
		exitGameBTN.setHoverBackground ( Color.decode ( "#2B0000" ) );
		exitGameBTN.setBorderPainted ( false );
		
		startGameBTN.addActionListener ( startGameLSTN );
		highscoreBTN.addActionListener ( startGameLSTN );
		exitGameBTN.addActionListener ( startGameLSTN );
		
		mainMenu.add ( startGameBTN );
		mainMenu.add ( highscoreBTN );
		mainMenu.add ( exitGameBTN );
		
		gui.add ( mainMenu );
		
		gui.validate ( );
		gui.repaint ( );
	}
	
	public static void initiateGameEnvironment ( )
	{
		gui.getContentPane ( ).removeAll ( );
		JPanel borderPanel = new JPanel ( new BorderLayout ( ) );
		JPanel userInterface = new JPanel ( new BorderLayout ( ) );
		Dimension expectedDimension = new Dimension ( gui.getWidth ( ) - 100, gui.getHeight ( ) - 100 );
		gamePanel = new SpacePanel ( );
		borderPanel.setPreferredSize ( expectedDimension );
		borderPanel.setMaximumSize ( expectedDimension );
		borderPanel.setMinimumSize ( expectedDimension );
		
		Image space = gui.readImage ( "images/space.jpg" );
		Box box = new Box ( BoxLayout.Y_AXIS)
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent ( Graphics g )
			{
				super.paintComponent ( g );
				
				Graphics2D g2d = ( Graphics2D ) g;
				
				g2d.drawImage ( space, 0, 0, null );
			}
		};
		
		box.add ( Box.createVerticalGlue ( ) );
		box.add ( borderPanel );
		box.add ( Box.createVerticalGlue ( ) );
		box.setBackground ( Color.GRAY );
		box.setOpaque ( true );
		
		userInterface.setBackground ( Color.BLACK );
		userInterface.setPreferredSize ( new Dimension ( borderPanel.getWidth ( ), 100 ) );
		
		gamePanel.setBackground ( Color.DARK_GRAY );
		
		cannonStatus.setForeground ( Color.WHITE );
		cannonStatus.setFont ( cannonStatus.getFont ( ).deriveFont ( 20F ) );
		cannonStatus.setHorizontalAlignment ( JLabel.CENTER );
		cannonStatus.setVerticalAlignment ( JLabel.CENTER );
		userInterface.add ( cannonStatus, BorderLayout.CENTER );
		
		levelStatus.setForeground ( Color.WHITE );
		levelStatus.setFont ( levelStatus.getFont ( ).deriveFont ( 20F ) );
		levelStatus.setHorizontalAlignment ( JLabel.CENTER );
		levelStatus.setVerticalAlignment ( JLabel.CENTER );
		levelStatus.setPreferredSize ( new Dimension ( userInterface.getWidth ( ), 50 ) );
		userInterface.add ( levelStatus, BorderLayout.SOUTH );
		
		borderPanel.add ( gamePanel, BorderLayout.CENTER );
		borderPanel.add ( userInterface, BorderLayout.NORTH );
		
		gui.add ( box, BorderLayout.CENTER );
		
		gui.validate ( );
		gui.repaint ( );
		
		gamePanel.requestFocusInWindow ( );
		
		controller = SpaceController.getInstanceOf ( );
		
		controller.setPlayer ( new Player ( 0, SpaceController.getGamePanel ( ).getHeight ( ) - 120 ) );
		
		addKeyListener ( );
		
		Time timemaker = new Time ( );
		controller.setTimer ( timemaker );
		
		timemaker.addListener ( controller );
		timemaker.start ( );
	}
	
	public static void initiateHighscoreLedger ( )
	{
		gui.getContentPane ( ).removeAll ( );
		
		Image space = gui.readImage ( "images/space.jpg" );
		JPanel ledgerPanel = new JPanel ( );
		JLabel ledger = new JLabel ( );
		JScrollPane ledgerScroller = new JScrollPane ( ledgerPanel );
		
		SpaceButton mainMenuButton = new SpaceButton ( "Hauptmenü" );
		
		mainMenuButton.setBounds (	( int ) ( ( gui.getWidth ( ) / 2 ) - ( STARTBTNWIDTH / 2 ) ),
									( int ) ( ( gui.getHeight ( ) / 2 ) - ( STARTBTNHEIGHT / 2 ) ), STARTBTNWIDTH, STARTBTNHEIGHT );
									
		mainMenuButton.setForeground ( Color.BLACK );
		mainMenuButton.setBackground ( Color.WHITE );
		mainMenuButton.setPressedBackground ( Color.decode ( "#AAAAAA" ) );
		mainMenuButton.setHoverBackground ( Color.decode ( "#CCCCCC" ) );
		mainMenuButton.setBorderPainted ( false );
		mainMenuButton.addActionListener ( new ActionListener ( )
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				initiateMainMenu ( );
			}
		} );
		
		Box backgroundBox = new Box ( BoxLayout.Y_AXIS)
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent ( Graphics g )
			{
				super.paintComponent ( g );
				
				Graphics2D g2d = ( Graphics2D ) g;
				
				g2d.drawImage ( space, 0, 0, null );
			}
		};
		
		Dimension expectedDimension = new Dimension ( gui.getWidth ( ) - 400, gui.getHeight ( ) - 200 );
		ledgerScroller.setPreferredSize ( expectedDimension );
		ledgerScroller.setMaximumSize ( expectedDimension );
		ledgerScroller.setMinimumSize ( expectedDimension );
		
		ledger.setForeground ( Color.WHITE );
		ledger.setText ( SpaceController.getHighscoreHTMLText ( ) );
		
		ledgerPanel.setBackground ( Color.DARK_GRAY );
		ledgerPanel.add ( ledger );
		
		backgroundBox.add ( Box.createVerticalGlue ( ) );
		backgroundBox.add ( ledgerScroller );
		backgroundBox.add ( Box.createRigidArea ( new Dimension ( 0, 15 ) ) );
		backgroundBox.add ( mainMenuButton );
		backgroundBox.add ( Box.createVerticalGlue ( ) );
		backgroundBox.setBackground ( Color.GRAY );
		backgroundBox.setOpaque ( true );
		
		gui.add ( backgroundBox, BorderLayout.CENTER );
		
		gui.validate ( );
		gui.repaint ( );
	}
	
	public static void initiateGui ( )
	{
		gui = getInstanceOf ( );
		
		gui.setLayout ( new BorderLayout ( ) );
		gui.setTitle ( "Space Invaders" );
		gui.setExtendedState ( Frame.MAXIMIZED_BOTH );
		gui.setUndecorated ( true );
		gui.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		gui.setAutoRequestFocus ( false );
		gui.setResizable ( false );
		
		centerFrameAndMakeItVisible ( gui );
		
		gui.validate ( );
		gui.repaint ( );
		
		Audio audio = ( Audio ) AssetFactory.loadAudio ( "music.wav" );
		SoundManager.playSound ( audio );
	}
	
	public void changeLevelStatusText ( String s )
	{
		levelStatus.setText ( s );
	}
	
	public void changeCannonStatusText ( String s )
	{
		cannonStatus.setText ( s );
	}
	
	public static void main ( String[] args )
	{
		SpaceController.createConnection ( );
	}
	
	public static SpacePanel getGamePanel ( )
	{
		return gamePanel;
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
