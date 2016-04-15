package project.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.keecode.event.EventManager;
import project.controller.SpaceController;
import project.listeners.SoundListener;

public class SpaceDialog extends JDialog
{
	private static final long serialVersionUID = 3365359809911406812L;
	
	private SpaceView spaceView;
	
	private int heightOfErrorSpace = 0;
	
	private int heightOfComponents = 40;
	private int widthOfComponents = 400;
	private int leftSpaceToComponents = 10;
	private int topSpaceToComponents = 10;
	
	private int heightOfApplyButton = 50;
	private int widthOfApplyButton = 100;
	private int leftSpaceToApplyButton = 309;
	private int topSpaceToApplyButtonFromComponents = 5;
	
	private int bottomSpaceToDialogBorderFromApplyButton = 10;
	private int rightSpaceToDialogBorderFromComponents = 10;
	
	private JPanel panel = new JPanel ( null );
	
	private JLabel dbError = new JLabel ( "" );
	
	private JLabel dbAdressRequest = new JLabel ( "Adresse der Datenbank:" );
	private JTextField dbAdress = new JTextField ( );
	private JLabel dbUsernameRequest = new JLabel ( "Der Benutzername der verwendet werden soll:" );
	private JTextField dbUsername = new JTextField ( );
	private JLabel dbPasswordRequest = new JLabel ( "Passwort des Benutzers:" );
	private JPasswordField dbPassword = new JPasswordField ( );
	
	private JButton btnApply = new JButton ( "Best�tigen" );
	
	private boolean showDBAdressError = false;
	private boolean showDBUsernameError = false;
	
	public SpaceDialog ( SpaceView spaceView )
	{
		this.setSpaceView ( spaceView );
		
		setName ( "Datenbankverbindung" );
		setAlwaysOnTop ( true );
		setDefaultCloseOperation ( HIDE_ON_CLOSE );
		
		setAllBoundsAndSizes ( );
		
		setResizable ( false );
		
		panel.add ( getDBError ( ) );
		panel.add ( getDBAdressRequest ( ) );
		panel.add ( getDBAdress ( ) );
		panel.add ( getDBUsernameRequest ( ) );
		panel.add ( getDBUsername ( ) );
		panel.add ( getDBPasswordRequest ( ) );
		panel.add ( getDBPassword ( ) );
		
		
		getDBError ( ).setForeground ( Color.RED );
		
		panel.add ( getBTNApply ( ) );
		addBTNApplyListener ( );
		
		add ( panel );
		
		setVisible ( true );
	}
	
	private void setAllBoundsAndSizes ( )
	{
		setSize ( leftSpaceToComponents
					+ widthOfComponents + rightSpaceToDialogBorderFromComponents
					+ 6, topSpaceToComponents
							+ getHeightOfErrorSpace ( ) + ( 6 * heightOfComponents ) + topSpaceToApplyButtonFromComponents + heightOfApplyButton
							+ bottomSpaceToDialogBorderFromApplyButton + 28 );
							
		getPanel ( ).setBounds ( 0, 0, getWidth ( ), getHeight ( ) );
		
		getDBError ( ).setBounds ( leftSpaceToComponents, topSpaceToComponents, widthOfComponents, getHeightOfErrorSpace ( ) );
		getDBAdressRequest ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ), widthOfComponents,
											heightOfComponents );
		getDBAdress ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ) + heightOfComponents, widthOfComponents,
									heightOfComponents );
		getDBUsernameRequest ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ) + ( 2 * heightOfComponents ),
												widthOfComponents, heightOfComponents );
		getDBUsername ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ) + ( 3 * heightOfComponents ),
										widthOfComponents, heightOfComponents );
		getDBPasswordRequest ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ) + ( 4 * heightOfComponents ),
												widthOfComponents, heightOfComponents );
		getDBPassword ( ).setBounds (	leftSpaceToComponents, topSpaceToComponents + getHeightOfErrorSpace ( ) + ( 5 * heightOfComponents ),
										widthOfComponents, heightOfComponents );
										
		getBTNApply ( ).setBounds ( leftSpaceToApplyButton, topSpaceToComponents
															+ getHeightOfErrorSpace ( ) + ( 6 * heightOfComponents )
															+ topSpaceToApplyButtonFromComponents,
									widthOfApplyButton, heightOfApplyButton );
	}
	
	private void addBTNApplyListener ( )
	{
		ActionListener listener;
		
		listener = new ActionListener ( )
		{
			
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				if ( e.getActionCommand ( ).equals ( btnApply.getText ( ) ) )
				{
					String dbAdress = getDBAdress ( ).getText ( );
					String dbUsername = getDBUsername ( ).getText ( );
					StringBuilder dbPasswordBuilder = new StringBuilder ( "" );
					
					for ( char i : getDBPassword ( ).getPassword ( ) )
					{
						dbPasswordBuilder.append ( i );
					}
					
					String dbPassword = dbPasswordBuilder.toString ( );
					
					setShowDBAdressError ( dbAdress == null || dbAdress.equals ( "" ) );
					setShowDBUsernameError ( dbUsername == null || dbUsername.equals ( "" ) );
					
					validateErrors ( );
					
					if ( isShowDBAdressError ( ) || isShowDBUsernameError ( ) )
					{
						return;
					}
					
					String[] errors = SpaceController.connect ( dbAdress, dbUsername, dbPassword );
					
					if ( errors != null )
					{
						for ( String i : errors )
						{
							if ( i != null && !i.equals ( "" ) )
							{
								System.err.println ( i );
							}
						}
					}
					else
					{
						setVisible ( false );
						
						SpaceView.initiateGui ( );
						SpaceView.initiateMainMenu ( );
						EventManager.registerListener ( new SoundListener ( ) );
					}
				}
			}
		};
		
		btnApply.addActionListener ( listener );
	}
	
	public void validateErrors ( )
	{
		String errorOutput = "<html>";
		setHeightOfErrorSpace ( 0 );
		
		if ( isShowDBAdressError ( ) )
		{
			setHeightOfErrorSpace ( getHeightOfErrorSpace ( ) + getHeightOfComponents ( ) );
			
			if ( errorOutput.equals ( "<html>" ) )
			{
				errorOutput += "Die Datenbank muss eine Adresse haben!";
			}
			else
			{
				errorOutput += "<br/>" + "Die Datenbank muss eine Adresse haben!";
			}
		}
		
		if ( isShowDBUsernameError ( ) )
		{
			setHeightOfErrorSpace ( getHeightOfErrorSpace ( ) + getHeightOfComponents ( ) );
			
			if ( errorOutput.equals ( "<html>" ) )
			{
				errorOutput += "Um bei der Datenbank einzuloggen, benötigt man einen Benutzernamen!";
			}
			else
			{
				errorOutput += "<br/>" + "Um bei der Datenbank einzuloggen, benötigt man einen Benutzernamen!";
			}
		}
		
		getDBError ( ).setText ( errorOutput );
		setAllBoundsAndSizes ( );
		validate ( );
		repaint ( );
		
		errorOutput += "</html>";
	}
	
	public SpaceView getSpaceView ( )
	{
		return spaceView;
	}
	
	public void setSpaceView ( SpaceView spaceView )
	{
		this.spaceView = spaceView;
	}
	
	public JPanel getPanel ( )
	{
		return panel;
	}
	
	public void setPanel ( JPanel panel )
	{
		this.panel = panel;
	}
	
	public JLabel getDBAdressRequest ( )
	{
		return dbAdressRequest;
	}
	
	public void setDBAdressRequest ( JLabel dbAdressRequest )
	{
		this.dbAdressRequest = dbAdressRequest;
	}
	
	public JTextField getDBAdress ( )
	{
		return dbAdress;
	}
	
	public void setDBAdress ( JTextField dbAdress )
	{
		this.dbAdress = dbAdress;
	}
	
	public JLabel getDBUsernameRequest ( )
	{
		return dbUsernameRequest;
	}
	
	public void setDBUsernameRequest ( JLabel dbUsernameRequest )
	{
		this.dbUsernameRequest = dbUsernameRequest;
	}
	
	public JTextField getDBUsername ( )
	{
		return dbUsername;
	}
	
	public void setDBUsername ( JTextField dbUsername )
	{
		this.dbUsername = dbUsername;
	}
	
	public JLabel getDBPasswordRequest ( )
	{
		return dbPasswordRequest;
	}
	
	public void setDBPasswordRequest ( JLabel dbPasswordRequest )
	{
		this.dbPasswordRequest = dbPasswordRequest;
	}
	
	public JPasswordField getDBPassword ( )
	{
		return dbPassword;
	}
	
	public void setDBPassword ( JPasswordField dbPassword )
	{
		this.dbPassword = dbPassword;
	}
	
	public JButton getBTNApply ( )
	{
		return btnApply;
	}
	
	public void setBTNApply ( JButton btnApply )
	{
		this.btnApply = btnApply;
	}
	
	public int getHeightOfComponents ( )
	{
		return heightOfComponents;
	}
	
	public void setHeightOfComponents ( int heightOfComponents )
	{
		this.heightOfComponents = heightOfComponents;
	}
	
	public int getWidthOfComponents ( )
	{
		return widthOfComponents;
	}
	
	public void setWidthOfComponents ( int widthOfComponents )
	{
		this.widthOfComponents = widthOfComponents;
	}
	
	public int getLeftSpaceToComponents ( )
	{
		return leftSpaceToComponents;
	}
	
	public void setLeftSpaceToComponents ( int leftSpaceToComponents )
	{
		this.leftSpaceToComponents = leftSpaceToComponents;
	}
	
	public int getTopSpaceToComponents ( )
	{
		return topSpaceToComponents;
	}
	
	public void setTopSpaceToComponents ( int topSpaceToComponents )
	{
		this.topSpaceToComponents = topSpaceToComponents;
	}
	
	public int getHeightOfApplyButton ( )
	{
		return heightOfApplyButton;
	}
	
	public void setHeightOfApplyButton ( int heightOfApplyButton )
	{
		this.heightOfApplyButton = heightOfApplyButton;
	}
	
	public int getWidthOfApplyButton ( )
	{
		return widthOfApplyButton;
	}
	
	public void setWidthOfApplyButton ( int widthOfApplyButton )
	{
		this.widthOfApplyButton = widthOfApplyButton;
	}
	
	public int getLeftSpaceToApplyButton ( )
	{
		return leftSpaceToApplyButton;
	}
	
	public void setLeftSpaceToApplyButton ( int leftSpaceToApplyButton )
	{
		this.leftSpaceToApplyButton = leftSpaceToApplyButton;
	}
	
	public int getTopSpaceToApplyButtonFromComponents ( )
	{
		return topSpaceToApplyButtonFromComponents;
	}
	
	public void setTopSpaceToApplyButtonFromComponents ( int topSpaceToApplyButtonFromComponents )
	{
		this.topSpaceToApplyButtonFromComponents = topSpaceToApplyButtonFromComponents;
	}
	
	public int getBottomSpaceToDialogBorderFromApplyButton ( )
	{
		return bottomSpaceToDialogBorderFromApplyButton;
	}
	
	public void setBottomSpaceToDialogBorderFromApplyButton ( int bottomSpaceToDialogBorderFromApplyButton )
	{
		this.bottomSpaceToDialogBorderFromApplyButton = bottomSpaceToDialogBorderFromApplyButton;
	}
	
	public int getRightSpaceToDialogBorderFromComponents ( )
	{
		return rightSpaceToDialogBorderFromComponents;
	}
	
	public void setRightSpaceToDialogBorderFromComponents ( int rightSpaceToDialogBorderFromComponents )
	{
		this.rightSpaceToDialogBorderFromComponents = rightSpaceToDialogBorderFromComponents;
	}
	
	public boolean isShowDBAdressError ( )
	{
		return showDBAdressError;
	}
	
	public void setShowDBAdressError ( boolean showDBAdressError )
	{
		this.showDBAdressError = showDBAdressError;
	}
	
	public boolean isShowDBUsernameError ( )
	{
		return showDBUsernameError;
	}
	
	public void setShowDBUsernameError ( boolean showDBUsernameError )
	{
		this.showDBUsernameError = showDBUsernameError;
	}
	
	public JLabel getDBError ( )
	{
		return dbError;
	}
	
	public void setDBError ( JLabel dbError )
	{
		this.dbError = dbError;
	}
	
	public int getHeightOfErrorSpace ( )
	{
		return heightOfErrorSpace;
	}
	
	public void setHeightOfErrorSpace ( int heightOfErrorSpace )
	{
		this.heightOfErrorSpace = heightOfErrorSpace;
	}
}
