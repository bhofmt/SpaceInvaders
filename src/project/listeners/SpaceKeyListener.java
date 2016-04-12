package project.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import project.controller.SpaceController;

public class SpaceKeyListener implements KeyListener
{
	private SpaceController ctrl;
	
	public SpaceKeyListener ()
	{
		ctrl = SpaceController.getInstanceOf ( );
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch ( e.getKeyCode () )
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
			ctrl.makePlayerShoot ();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch ( e.getKeyCode () )
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

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

}