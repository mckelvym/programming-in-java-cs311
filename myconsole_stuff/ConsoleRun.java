import javax.swing.*;
import java.applet.*;

// runs an applet or application from the console
public class ConsoleRun {

    public static void run(Object object, int width, int height) {
        if (object instanceof JFrame) {
            setClose(object);
            object.setSize(width, height);
            object.setVisible(true);
        }
        if (object instanceof JApplet) {
            JFrame jframe = new JFrame(getTitle(object));
            setClose(jframe);
            jframe.getContentPane().add(object);
            jframe.setSize(width, height);
            object.init();
            object.start();
            jframe.setVisible(true);
        }
        if (!((object instanceof JFrame) || (object instanceof JApplet))) {
            System.exit(0);
        }
    }

    public static String getTitle(Object object) {
        String title = object.getClass().toString();
        if (title.indexOf(".class") != -1) {
            return title.substring(0, title.length() - 6);
        } else {
            return "Error!";
        }
    }
}
