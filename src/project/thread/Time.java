package project.thread;

import java.util.HashSet;
import java.util.Set;

import project.listeners.TimeListener;

public class Time extends Thread
{
	private volatile boolean pauseRequested = false;
	
	private Set<TimeListener> listeners = new HashSet<> ( );
	
	@Override
	public void run ( )
	{
		while ( true )
		{
			try
			{
				/*
				 * Equals 0.01 seconds.
				 */
				Thread.sleep ( 10 );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace ( );
			}
			
			if ( pauseRequested )
			{
				synchronized ( this )
				{
					pauseRequested = false;
					
					try
					{
						this.wait ( );
					}
					catch ( InterruptedException e )
					{
						e.printStackTrace ( );
					}
				}
				
			}
			
			if ( listeners.isEmpty ( ) )
			{
				System.out.println ( "ERROR" );
			}
			else
			{
				for ( TimeListener listener : listeners )
				{
					listener.timeTick ( );
				}
			}
		}
	}
	
	public void addListener ( TimeListener listener )
	{
		listeners.add ( listener );
	}
	
	public void removeListener ( TimeListener listener )
	{
		listeners.remove ( listener );
	}
	
	public void requestPause ( )
	{
		this.pauseRequested = true;
	}
	
	public synchronized void startAgain ( )
	{
		this.notifyAll ( );
	}
	
}
