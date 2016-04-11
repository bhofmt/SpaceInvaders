package project.model;

public class Projectile extends Entity
{
	private Player player;

	public Projectile ( Player player )
	{
		this.setPlayer ( player );
		setPosition ( new Position ( player.getPosition ().getX (), player.getPosition ().getY () ) );
	}

	@Override
	void move()
	{
		int newYCorrd = getPosition ().getY () - getSpeed ();
		if ( newYCorrd < 0 )
		{
			newYCorrd = 0;
		}
		setY ( newYCorrd );
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

}
