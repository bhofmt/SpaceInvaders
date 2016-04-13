package project.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SpaceDialog extends JDialog
{
	private static final long serialVersionUID = 3365359809911406812L;
	
	private SpaceView spaceView;

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

	private JLabel dbAdressRequest = new JLabel ( "Adresse der Datenbank:" );
	private JTextField dbAdress = new JTextField ();
	private JLabel dbUsernameRequest = new JLabel ( "Der Benutzername der verwendet werden soll:" );
	private JTextField dbUsername = new JTextField ();
	private JLabel dbPasswordRequest = new JLabel ( "Passwort des Benutzers:" );
	private JPasswordField dbPassword = new JPasswordField ();

	private JButton btnApply = new JButton ( "Bestätigen" );

	public SpaceDialog (SpaceView spaceView)
	{
		this.setSpaceView ( spaceView );
		
		setName ( "Datenbankverbindung" );
		setAlwaysOnTop ( true );
		setDefaultCloseOperation ( HIDE_ON_CLOSE );
		setSize ( leftSpaceToComponents + widthOfComponents + rightSpaceToDialogBorderFromComponents + 6, topSpaceToComponents + ( 6 * heightOfComponents ) + topSpaceToApplyButtonFromComponents + heightOfApplyButton + bottomSpaceToDialogBorderFromApplyButton + 28 );
		setResizable ( false );

		getPanel ().setBounds ( 0, 0, getWidth (), getHeight () );

		getDBAdressRequest ().setBounds ( leftSpaceToComponents, topSpaceToComponents, widthOfComponents,
				heightOfComponents );
		getDBAdress ().setBounds ( leftSpaceToComponents, topSpaceToComponents + heightOfComponents, widthOfComponents,
				heightOfComponents );
		getDBUsernameRequest ().setBounds ( leftSpaceToComponents, topSpaceToComponents + ( 2 * heightOfComponents ),
				widthOfComponents, heightOfComponents );
		getDBUsername ().setBounds ( leftSpaceToComponents, topSpaceToComponents + ( 3 * heightOfComponents ),
				widthOfComponents, heightOfComponents );
		getDBPasswordRequest ().setBounds ( 10, topSpaceToComponents + ( 4 * heightOfComponents ), widthOfComponents,
				heightOfComponents );
		getDBPassword ().setBounds ( leftSpaceToComponents, topSpaceToComponents + ( 5 * heightOfComponents ),
				widthOfComponents, heightOfComponents );

		getBTNApply ().setBounds ( leftSpaceToApplyButton,
				topSpaceToComponents + ( 6 * heightOfComponents ) + topSpaceToApplyButtonFromComponents,
				widthOfApplyButton, heightOfApplyButton );

		getPanel ().add ( getDBAdressRequest () );
		getPanel ().add ( getDBAdress () );
		getPanel ().add ( getDBUsernameRequest () );
		getPanel ().add ( getDBUsername () );
		getPanel ().add ( getDBPasswordRequest () );
		getPanel ().add ( getDBPassword () );

		getPanel ().add ( getBTNApply () );
		addBTNApplyListener ();

		add ( getPanel () );

		setVisible ( true );
	}

	private void addBTNApplyListener()
	{
		ActionListener listener;

		listener = new ActionListener ()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if ( e.getActionCommand ().equals ( btnApply.getText () ) )
				{
					String dbAdress = getDBAdress ().getText ();
					String dbUsername = getDBUsername ().getText ();
					
					String dbPassword = null;
				}
			}
		};

		btnApply.addActionListener ( listener );
	}
	
	public SpaceView getSpaceView()
	{
		return spaceView;
	}

	public void setSpaceView(SpaceView spaceView)
	{
		this.spaceView = spaceView;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}

	public void setPanel(JPanel panel)
	{
		this.panel = panel;
	}

	public JLabel getDBAdressRequest()
	{
		return dbAdressRequest;
	}

	public void setDBAdressRequest(JLabel dbAdressRequest)
	{
		this.dbAdressRequest = dbAdressRequest;
	}

	public JTextField getDBAdress()
	{
		return dbAdress;
	}

	public void setDBAdress(JTextField dbAdress)
	{
		this.dbAdress = dbAdress;
	}

	public JLabel getDBUsernameRequest()
	{
		return dbUsernameRequest;
	}

	public void setDBUsernameRequest(JLabel dbUsernameRequest)
	{
		this.dbUsernameRequest = dbUsernameRequest;
	}

	public JTextField getDBUsername()
	{
		return dbUsername;
	}

	public void setDBUsername(JTextField dbUsername)
	{
		this.dbUsername = dbUsername;
	}

	public JLabel getDBPasswordRequest()
	{
		return dbPasswordRequest;
	}

	public void setDBPasswordRequest(JLabel dbPasswordRequest)
	{
		this.dbPasswordRequest = dbPasswordRequest;
	}

	public JPasswordField getDBPassword()
	{
		return dbPassword;
	}

	public void setDBPassword(JPasswordField dbPassword)
	{
		this.dbPassword = dbPassword;
	}

	public JButton getBTNApply()
	{
		return btnApply;
	}

	public void setBTNApply(JButton btnApply)
	{
		this.btnApply = btnApply;
	}

	public int getHeightOfComponents()
	{
		return heightOfComponents;
	}

	public void setHeightOfComponents(int heightOfComponents)
	{
		this.heightOfComponents = heightOfComponents;
	}

	public int getWidthOfComponents()
	{
		return widthOfComponents;
	}

	public void setWidthOfComponents(int widthOfComponents)
	{
		this.widthOfComponents = widthOfComponents;
	}

	public int getLeftSpaceToComponents()
	{
		return leftSpaceToComponents;
	}

	public void setLeftSpaceToComponents(int leftSpaceToComponents)
	{
		this.leftSpaceToComponents = leftSpaceToComponents;
	}

	public int getTopSpaceToComponents()
	{
		return topSpaceToComponents;
	}

	public void setTopSpaceToComponents(int topSpaceToComponents)
	{
		this.topSpaceToComponents = topSpaceToComponents;
	}

	public int getHeightOfApplyButton()
	{
		return heightOfApplyButton;
	}

	public void setHeightOfApplyButton(int heightOfApplyButton)
	{
		this.heightOfApplyButton = heightOfApplyButton;
	}

	public int getWidthOfApplyButton()
	{
		return widthOfApplyButton;
	}

	public void setWidthOfApplyButton(int widthOfApplyButton)
	{
		this.widthOfApplyButton = widthOfApplyButton;
	}

	public int getLeftSpaceToApplyButton()
	{
		return leftSpaceToApplyButton;
	}

	public void setLeftSpaceToApplyButton(int leftSpaceToApplyButton)
	{
		this.leftSpaceToApplyButton = leftSpaceToApplyButton;
	}

	public int getTopSpaceToApplyButtonFromComponents()
	{
		return topSpaceToApplyButtonFromComponents;
	}

	public void setTopSpaceToApplyButtonFromComponents(int topSpaceToApplyButtonFromComponents)
	{
		this.topSpaceToApplyButtonFromComponents = topSpaceToApplyButtonFromComponents;
	}

	public int getBottomSpaceToDialogBorderFromApplyButton()
	{
		return bottomSpaceToDialogBorderFromApplyButton;
	}

	public void setBottomSpaceToDialogBorderFromApplyButton(int bottomSpaceToDialogBorderFromApplyButton)
	{
		this.bottomSpaceToDialogBorderFromApplyButton = bottomSpaceToDialogBorderFromApplyButton;
	}

	public int getRightSpaceToDialogBorderFromComponents()
	{
		return rightSpaceToDialogBorderFromComponents;
	}

	public void setRightSpaceToDialogBorderFromComponents(int rightSpaceToDialogBorderFromComponents)
	{
		this.rightSpaceToDialogBorderFromComponents = rightSpaceToDialogBorderFromComponents;
	}
}
