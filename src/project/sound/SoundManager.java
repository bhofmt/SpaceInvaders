package project.sound;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import net.keecode.asset.Audio;
import net.keecode.event.EventManager;
import net.keecode.event.SoundStartEvent;
import net.keecode.event.SoundStopEvent;
import net.keecode.logger.Logger;

/**
 * Created by Michael on 19.03.2016.
 */
public class SoundManager
{
	
	private static InputStream bufferedIn;
	private static AudioInputStream stream;
	private static AudioFormat format;
	private static DataLine.Info info;
	private static Timer timer;
	private static TimerTask timerTask;
	private static Clip clip;
	
	public static void playSound ( Audio audio )
	{
		try
		{
			SoundStartEvent soundStartEvent = new SoundStartEvent ( audio );
			EventManager.fireEvent ( soundStartEvent );
			audio = soundStartEvent.getAudio ( );
			final Audio finalAudio = audio;
			bufferedIn = audio.getBufferedInputStream ( );
			stream = AudioSystem.getAudioInputStream ( bufferedIn );
			format = stream.getFormat ( );
			info = new DataLine.Info ( Clip.class, format );
			clip = ( Clip ) AudioSystem.getLine ( info );
			clip.open ( stream );
			long frames = stream.getFrameLength ( );
			final double durationInSeconds = ( frames + 0.0 ) / format.getFrameRate ( );
			timer = new Timer ( );
			timerTask = new TimerTask ( )
			{
				@Override
				public void run ( )
				{
					SoundStopEvent soundStopEvent = new SoundStopEvent ( finalAudio );
					EventManager.fireEvent ( soundStopEvent );
				}
			};
			timer.schedule ( timerTask, ( int ) ( durationInSeconds * 1000 ) - 25 );
			clip.start ( );
			Logger.info ( "Playing sound " + audio.getAssetName ( ) );
		}
		catch ( Exception e )
		{
			Logger.error ( "Error while playing sound: " + audio.getAssetName ( ) );
			e.printStackTrace ( );
		}
	}
	
	public static void playEffect ( Audio audio )
	{
		
		InputStream bufferedIn;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		Timer timer;
		TimerTask timerTask;
		Clip clip;
		try
		{
			SoundStartEvent soundStartEvent = new SoundStartEvent ( audio );
			EventManager.fireEvent ( soundStartEvent );
			audio = soundStartEvent.getAudio ( );
			final Audio finalAudio = audio;
			bufferedIn = audio.getBufferedInputStream ( );
			stream = AudioSystem.getAudioInputStream ( bufferedIn );
			format = stream.getFormat ( );
			info = new DataLine.Info ( Clip.class, format );
			clip = ( Clip ) AudioSystem.getLine ( info );
			clip.open ( stream );
			long frames = stream.getFrameLength ( );
			final double durationInSeconds = ( frames + 0.0 ) / format.getFrameRate ( );
			timer = new Timer ( );
			timerTask = new TimerTask ( )
			{
				@Override
				public void run ( )
				{
					SoundStopEvent soundStopEvent = new SoundStopEvent ( finalAudio );
					EventManager.fireEvent ( soundStopEvent );
				}
			};
			timer.schedule ( timerTask, ( int ) ( durationInSeconds * 1000 ) - 25 );
			clip.start ( );
			Logger.info ( "Playing sound " + audio.getAssetName ( ) );
		}
		catch ( Exception e )
		{
			Logger.error ( "Error while playing sound: " + audio.getAssetName ( ) );
			e.printStackTrace ( );
		}
	}
	
	public static void stopSound ( )
	{
		if ( clip == null )
			return;
		clip.stop ( );
		timer.cancel ( );
		timerTask.cancel ( );
	}
	
}
