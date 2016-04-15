package project.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class SpaceButton extends JButton
{
	private static final long serialVersionUID = 1278617103889476695L;
	
	private Color hoverBackgroundColor;
	private Color pressedBackgroundColor;
	
	public SpaceButton ( )
	{
		this ( "Button" );
	}
	
	public SpaceButton ( String text )
	{
		super ( text );
		super.setContentAreaFilled ( false );
	}
	
	@Override
	protected void paintComponent ( Graphics g )
	{
		if ( getModel ( ).isPressed ( ) )
		{
			g.setColor ( pressedBackgroundColor );
		}
		else if ( getModel ( ).isRollover ( ) )
		{
			g.setColor ( hoverBackgroundColor );
		}
		else
		{
			g.setColor ( getBackground ( ) );
		}
		g.fillRect ( 0, 0, getWidth ( ), getHeight ( ) );
		super.paintComponent ( g );
	}
	
	public Color getHoverBackground ( )
	{
		return hoverBackgroundColor;
	}
	
	public void setHoverBackground ( Color color )
	{
		this.hoverBackgroundColor = color;
	}
	
	public Color getPressedBackground ( )
	{
		return pressedBackgroundColor;
	}
	
	public void setPressedBackground ( Color color )
	{
		this.pressedBackgroundColor = color;
	}
	
}
