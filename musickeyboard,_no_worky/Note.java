import java.applet.*;

/**
 * This class is for a musical note or sound that can be played continuously, or silenced.
 *
 * @author David Rantanen
 * @version 1999 July 16
 */

public class Note {
    /**
     * The sound to be played
     */
    private AudioClip tone;

    /**
     * Whether the sound is currently playing
     */
    private boolean playing;

    /**
     * Constructs Note object
     *
     * @param sound A sound to be used as a note
     */
    public Note(AudioClip sound) {
        playing = false;
        tone = sound;
    }

    /**
     * Tells whether the note is currently playing
     *
     * @return true if it's playing, false otherwise.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Starts the note continuously playing
     */
    public void play() {
        tone.loop();
        playing = true;
    }

    /**
     * Stops the note.
     */

    public void stop() {
        tone.stop();
        playing = false;
    }
}
