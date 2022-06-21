import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

/**
 * The Sound class handles loading and playing a sound.
 */
public class Sound {
    Clip sound;

    /**
     * This constructs a {@code Sound} object from a WAV file.
     * @param soundName The sound file name.
     */
    Sound(String soundName) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundName));
            this.sound = AudioSystem.getClip();
            this.sound.open(audioStream);
        } catch (IOException ex) { 
            System.out.println("File not found [" + soundName + "]");
        } catch (UnsupportedAudioFileException ex) { 
            System.out.println("Unsupported file [" + soundName + "]"); 
        } catch (LineUnavailableException ex) { 
            System.out.println("Audio feed already in use");
        }

        this.addLineListener(new SoundLineListener());
    }

    /**
     * This method starts playing the sound.
     */
    public void start() {
        this.sound.start();
    }

    /**
     * This method stops playing the sound.
     */
    public void stop() {
        this.sound.stop();
    }

    /**
     * This method stops the sound if it running, and does nothing
     * otherwise.
     */
    public void tryStop() {
        if (this.checkRunning()) {
            this.stop();
        }
    }
    
    /**
     * This method clears the queued data for the sound.
     */
    public void flush() {
        this.sound.flush();
    }

    /**
     * This method sets the current playing position in the sound to the
     * specified frame.
     * @param frames The frame index to start playing the sound from.
     */
    public void setFramePosition(int frames) {
        this.sound.setFramePosition(frames);
    }

    /**
     * This method sets the current playing position in the sound to the
     * beginning.
     */
    public void reset() {
        this.sound.setFramePosition(0);
    }

    /**
     * This method adds a {@code LineListener} to the sound.
     * @param listener The {@code LineListener} to add.
     */
    public void addLineListener(LineListener listener) {
        this.sound.addLineListener(listener);
    }

    /**
     * This method checks if the sound is currently being played.
     * @return
     */
    public boolean checkRunning() {
        return this.sound.isRunning();
    }

    /**
     * This class implements a LineListener that resets the audio clip
     * when the sound finishes playing.
     */
    private class SoundLineListener implements LineListener {
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                flush();
                setFramePosition(0);
            }
        }
    }
}