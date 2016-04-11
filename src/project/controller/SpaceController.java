package project.controller;

import project.listeners.TimeListener;
import project.model.Player;

public class SpaceController implements TimeListener
{
	private static SpaceController controller = null;
	
	private Player player;
	
	private SpaceController ( )
	{
	
	}
	
	public static SpaceController getInstanceOf ( )
	{
		if ( controller != null )
		{
			return controller;
		}
		else
		{
			controller = new SpaceController ( );
			return controller;
		}
	}
	
	public void timeTick ( )
	{
	
	}
	
	public Player getPlayer ( )
	{
		return player;
	}
	
	public void setPlayer ( Player player )
	{
		this.player = player;
	}
	
	public void movePlayerLeft ( boolean b )
	{
		player.movesLeft ( b );
	}
	
	public void movePlayerRight ( boolean b )
	{
		player.movesRight ( b );
	}
}
