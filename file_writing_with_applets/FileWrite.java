import java.awt.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class FileWrite extends JApplet {
    BufferedReader reader;
    PrintWriter writer;

    public void init() {
        reader = new BufferedReader(new InputStreamReader(new URL(getCodeBase().toString()
                + "MyFile.dat").openStream()));
        reader.readLine();
        reader.close();

        //writer = new PrintWriter(new OutputStreamWriter(
        //	new URL(getCodeBase().toString() + "MyFile.dat").openStream()));
        //writer.close();
    }
}
