import java.awt.*;
import java.applet.*;
import java.util.Hashtable;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * An applet which allows the user to play the computer's
 * keyboard as if it were a musical keyboard.
 *
 * @author David Rantanen
 * @version 1.3.jdk1.0 1999 September 3
 */

public class MusicKeyboard extends Applet {

    /**
     * How to make the applet playable.
     */
    private final String instruction = "Click on the keyboard image to activate.";
    /**
     * My web page URL.
     */
    private final String myWebPage = "http://members.aol.com/djrant/programming.html";
    /**
     * The label at the top of the applet.
     */
    private Label readyLabel;
    /**
     * The image representing the link to the author's web site.
     */
    private Image authorUrlImage;
    /**
     * Data structure to hold all the Notes for all the key characters.
     */
    private Hashtable notes;
    /**
     * Contains all the keyboard characters that have notes associated with them.
     */
    private String keys = "Q2W3ER5T6Y7UI9O0P[=]qwertyuiop";
    /**
     * The image of the musical keyboard showing which computer keys play which notes.
     */
    private Image keyboardImage;

    /**
     * Paints the applet, draws the keyboard image and the author url image
     * on the applet.
     *
     * @param g The graphics context to use for painting.
     */

    public void paint(Graphics g) {
        g.drawImage(keyboardImage, 24, 25, this);
        g.drawImage(authorUrlImage, 75, bounds().height - 21, this);
        super.paint(g);
    }

    /**
     * Plays the note corresponding to the value of key.
     * If there is no such note, nothing happens.
     *
     * @param key The integer value of the character that was pressed to play a note.
     */

    private void play(int key) {
        Note note = (Note) notes.get(new Integer(key));
        if (note == null)
            return;
        if (!note.isPlaying())
            note.play();
    }

    /**
     * Silences the note corresponding to the value of key.
     *
     * @param key The integer value of the character that was pressed to silence a note.
     */

    private void silence(int key) {
        Note note = (Note) notes.get(new Integer(key));
        if (note == null)
            return;
        note.stop();
    }

    /**
     * Silences all notes.
     */

    private void silenceAll() {
        int numKeys = keys.length();
        for (int i = 0; i < numKeys; i++)
            silence(keys.charAt(i));
    }

    /**
     * Gets all the sound files for all the notes and puts them in a data structure
     * for easy access.
     */

    private void loadSounds() {
        URL base = getCodeBase();

        int numkeys = keys.length();
        for (int i = 0; i < numkeys; i++) {
            int thisKey = keys.charAt(i);
            String soundFile = getFileNameForKey(thisKey);
            Note note = new Note(getAudioClip(base, "sounds/" + soundFile));
            notes.put(new Integer(thisKey), note);

            //make sure it's loaded into memory
            note.play();
            note.stop();
        }
    }

    /**
     * Makes an image containing the text of the author's URL.
     *
     * @return An image containing the text of the author's URL.
     */

    private Image makeAuthorUrlImage() {
        Image au;
        au = createImage(300, 17);
        Graphics auG = au.getGraphics();

        auG.setColor(getBackground());
        auG.fillRect(0, 0, 300, 17);

        auG.setColor(Color.blue);
        auG.drawRect(0, 0, 299, 16);
        auG.setFont(new Font("Courier", Font.PLAIN, 10));
        auG.drawString(myWebPage, 5, 11);

        return au;
    }

    /**
     * Makes an image indicating which computer keys
     * correspond to which musical keys.
     *
     * @return A keyboard image indicating which computer keys
     * correspond to which musical keys.
     */

    private Image makeKeyboard() {
        Image kb;
        kb = createImage(400, 100);
        Graphics kbG = kb.getGraphics();

        kbG.setColor(Color.black);
        kbG.fillRect(0, 0, 400, 100);

        //draw the "naturals"
        kbG.setColor(Color.white);
        for (int n = 0; n < 12; n++) {
            kbG.fillRect(3 + (n * 33), 2, 31, 96);
        }
        //draw the "accidentals"
        kbG.setColor(Color.black);
        for (int n = 0; n < 11; n++) {
            switch (n) {
                case 0:
                case 1:
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 10:
                    kbG.fillRect(24 + (n * 33), 2, 22, 65);
                    break;
            }
        }
        //draw the characters
        kbG.setFont(new Font("Courier", Font.BOLD, 24));
        for (int n = 0, whiteIndex = 0; n < 20; n++) {
            int thisChar = keys.charAt(n);

            switch (thisChar) {
                case 'Q':
                case 'W':
                case 'E':
                case 'R':
                case 'T':
                case 'Y':
                case 'U':
                case 'I':
                case 'O':
                case 'P':
                case '[':
                case ']':
                    kbG.setColor(Color.black);
                    kbG.drawString(keys.substring(n, n + 1), 9 + (whiteIndex * 33), 90);
                    whiteIndex++;
                    break;
                case '2':
                case '3':
                case '5':
                case '6':
                case '7':
                case '9':
                case '0':
                case '=':
                    kbG.setColor(Color.white);
                    kbG.drawString(keys.substring(n, n + 1), 28 + ((whiteIndex - 1) * 33), 60);
                    break;
            }
        }
        return kb;
    }

    /**
     * Gets the name of the sound file for a given character.
     *
     * @param key The integer value of the character to be associated with a given note.
     * @return The sound file name of the note for the given character.
     */

    private String getFileNameForKey(int key) {
        switch (key) {
            case 'Q':
            case 'q':
                return "c2.au";
            case 'W':
            case 'w':
                return "d2.au";
            case 'E':
            case 'e':
                return "e2.au";
            case 'R':
            case 'r':
                return "f2.au";
            case 'T':
            case 't':
                return "g2.au";
            case 'Y':
            case 'y':
                return "a2.au";
            case 'U':
            case 'u':
                return "b2.au";
            case 'I':
            case 'i':
                return "c3.au";
            case 'O':
            case 'o':
                return "d3.au";
            case 'P':
            case 'p':
                return "e3.au";
            case '[':
                return "f3.au";
            case ']':
                return "g3.au";
            case '2':
                return "csharp2.au";
            case '3':
                return "dsharp2.au";
            case '5':
                return "fsharp2.au";
            case '6':
                return "gsharp2.au";
            case '7':
                return "asharp2.au";
            case '9':
                return "csharp3.au";
            case '0':
                return "dsharp3.au";
            case '=':
                return "fsharp3.au";
        }
        return null;
    }

    /**
     * Tells the web browser to go to the author's web site.
     */

    private void visitAuthor() {
        try {
            this.getAppletContext().showDocument(new URL(myWebPage));
        } catch (MalformedURLException e) {
        }
    }

    /**
     * Initializes the images, loads the sounds.
     */

    public void init() {
        setLayout(new BorderLayout());

        keyboardImage = makeKeyboard();
        authorUrlImage = makeAuthorUrlImage();
        notes = new Hashtable(127);
        loadSounds();

        readyLabel = new Label("", Label.CENTER);
        add("North", readyLabel);

        readyLabel.setText(instruction);
    }

    /**
     * Handles the key events which play and stop the notes, and the
     * mouse event which opens the browser to the author's web site.
     * <p>
     * All other events are referred to the superclass.
     *
     * @param evt The event which is to be handled.
     * @return true if the event was handled, false otherwise
     */

    public boolean handleEvent(Event evt) {
        if (evt.id == Event.KEY_PRESS) {
            play(evt.key);
            return true;
        } else if (evt.id == Event.KEY_RELEASE) {
            silence(evt.key);
            return true;
        } else if (evt.id == Event.MOUSE_DOWN) {
            //See if the author url link image was clicked
            if (evt.x >= 75 && evt.x <= (75 + authorUrlImage.getWidth(this)) &&
                    evt.y >= (bounds().height - 21) &&
                    evt.y <= (bounds().height - 21 + authorUrlImage.getHeight(this))) {
                visitAuthor();
                return true;
            }
        } else if (evt.id == Event.LOST_FOCUS) {
            silenceAll();
            return true;
        }

        return super.handleEvent(evt);
    }

    /**
     * Returns a string containing information about the author, version
     *
     * @return a string containing information about the author, version
     */

    public String getAppletInfo() {
        String me = "David Rantanen";
        String program = "Musical Keyboard Applet";
        return me + "'s " + program + " version 1.3.jdk1.0 djrant@aol.com";
    }
}
