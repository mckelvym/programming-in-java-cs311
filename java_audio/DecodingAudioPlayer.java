// DecodingAudioPlayer.java
// jsresources.com

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class DecodingAudioPlayer {
    //private static final int EXTERNAL_BUFFER_SIZE = 128000;
    static int EXTERNAL_BUFFER_SIZE = 128000;

    public static void main(String[] args) {
        String strFilename = new String("LazzieBoy.mp3");//args[0];
        File file = new File(strFilename);
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (audioInputStream == null) {
            out("###  cannot read input file: " + strFilename);
        }
        AudioFormat sourceFormat = audioInputStream.getFormat();
        //
        AudioFormat.Encoding targetEncoding = AudioFormat.Encoding.PCM_SIGNED;
        audioInputStream = AudioSystem.getAudioInputStream(targetEncoding, audioInputStream);
        AudioFormat audioFormat = audioInputStream.getFormat();

        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        line.start();
        int nBytesRead = 0;
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }
        // line.close();
    }

    private static void out(String strMessage) {
        System.out.println(strMessage);
    }
}
