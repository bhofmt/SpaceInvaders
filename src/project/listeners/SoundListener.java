package project.listeners;

import net.keecode.event.EventHandler;
import net.keecode.event.SoundStopEvent;
import net.keecode.listener.Listener;
import project.sound.SoundManager;

public class SoundListener implements Listener
{
	
	@EventHandler
	public void onSoundStop ( SoundStopEvent event )
	{
		if ( event.getAudio ( ).getAssetName ( ) == "music.wav" )
		{
			SoundManager.playSound ( event.getAudio ( ) );
		}
	}
	
}
