package project.controller;

import java.util.ArrayList;

import project.listeners.TimeListener;
import project.model.Enemy;
import project.model.Player;
import project.view.SpacePanel;
import project.view.SpaceView;

public class SpaceController implements TimeListener
{
	private static SpaceController controller = null;
	
	private Player player;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy> ( );
	
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
	
	public SpacePanel getGamePanel ( )
	{
		return SpaceView.getGamePanel ( );
	}
	
	public static SpaceView getGui ( )
	{
		return SpaceView.getInstanceOf ( );
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
