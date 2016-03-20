import sun.audio.*;
import java.util.*;
import java.io.*;

public class backgroundMusic
{
	AudioStream backgroundMusics;
    AudioPlayer musicPlayer = AudioPlayer.player;    
    
	public backgroundMusic()
	{
	}

    public void playBackgroundMusic()
    {
        try  // have this try catch statement to stop the program from shutting down if there is an error
        {
            backgroundMusics = new AudioStream(new FileInputStream("test.wav")); // play the music from this file
            musicPlayer.start(backgroundMusics);
        }catch(IOException error)
        {
            error.printStackTrace(); // print the error to screen
        }
    }
    
    public void stopMusic()
    {
		musicPlayer.stop(backgroundMusics);
	}
}
