import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;

public class SoundApp extends JFrame {

    static AudioInputStream audioInputStream;
    static JButton ok;
    static String filename;

    SoundApp() {
        super("Audio Playing Program");
        this.setSize(400, 400);
        this.setLocation((1024 - 400) / 2, (768 - 400) / 2);

        ok = new JButton("Play");

        this.getContentPane().setLayout(new GridLayout(2, 1));
        this.getContentPane().setBackground(Color.black);
        this.getContentPane().add(ok);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ok) {
                }
            }
        });

        AudioFileFormat(AudioFileFormat.Type type, AudioFormat format, int frameLength)
        Constructs an audio file format object.
        AudioFileFormat(AudioFileFormat.Type type, int byteLength, AudioFormat format,int frameLength)

    /*int getByteLength()
          Obtains the size in bytes of the entire audio file (not just its audio data).
     AudioFormat getFormat()
          Obtains the format of the audio data contained in the audio file.
     int getFrameLength()
          Obtains the length of the audio data contained in the file, expressed in sample frames.
 AudioFileFormat.Type getType()
          Obtains the audio file type, such as WAVE or AU.
 String toString()
*/
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.);
        audioInputStream = new AudioInputStream(TargetDataLine.open());

        //TargetDataLine.open(AudioFormat format, int bufferSize);
        //TargetDataLine.read(byte[] b, int off, int len);
        // getBufferSize getFormat start stop getMicrosecondPosition

        // AudioFormat(AudioFormat.Encoding encoding, float sampleRate, int sampleSizeInBits, int channels,
        // int frameSize, float frameRate, boolean bigEndian)

        // AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian)


    }

    public static void main(String[] args) {
        SoundApp soundApp = new SoundApp();
        soundApp.setVisible(true);
    }
}
