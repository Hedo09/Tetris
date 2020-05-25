package pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.audio.*;
public class playmusic {
	
	public playmusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\adam\\git\\repository\\Tetris\\tetris_music.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
	}
}
