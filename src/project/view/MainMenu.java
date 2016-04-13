package project.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainMenu extends JPanel
{
	private static final long serialVersionUID = 7189991230290800387L;

	private Image space;

	public MainMenu ()
	{
		space = readImage ( "images/space.jpg" );
	}

	public MainMenu ( BorderLayout layout )
	{
		super ( layout );
		space = readImage ( "images/space.jpg" );
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent ( g );

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage ( space, 0, 0, null );
	}

	private Image readImage(String path)
	{
		InputStream stream;
		BufferedImage image = null;

		stream = this.getClass ().getClassLoader ().getResourceAsStream ( path );

		try
		{
			image = ImageIO.read ( stream );
		}
		catch ( IOException e )
		{
			System.err.println ( "Couldn't load the Texture! Cause: " + e.getMessage () );
			System.exit ( 67 );
		}
		catch ( IllegalArgumentException e )
		{
			System.err.println ( "Couldn't read the Texture! Cause: " + e.getMessage () );
			System.exit ( 68 );
		}

		return image;
	}
}
