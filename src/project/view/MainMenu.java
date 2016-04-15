package project.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import project.controller.SpaceController;

public class MainMenu extends JPanel
{
	private static final long serialVersionUID = 7189991230290800387L;
	
	private Image space;
	
	public MainMenu ( )
	{
		this ( new BorderLayout ( ) );
	}
	
	public MainMenu ( LayoutManager layout )
	{
		super ( layout );
		space = SpaceController.getInstanceOf ( ).readImage ( "images/space.jpg" );
	}
	
	@Override
	protected void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		
		Graphics2D g2d = ( Graphics2D ) g;
		
		g2d.drawImage ( space, 0, 0, null );
	}
}
