//runs both applets and applications from the console
public class ConsoleRun {
    //extracts the word "class" fro the name of the file
    public static String title(Object o) {
        String t = o.getClass().toString();
        if (t.indexOf(".class") != -1)
            t = t.substring(0, (t.length() - 6));
    }

    public static void setUpClosing(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void run(JFrame frame, int w, int h) {
        setUpClosing(frame);
        frame.setSize(w, h);
        frame.setVisible(true);
    }

    public static void run(JApplet applet, int w, int h) {
        Jframe frame = new JFrame(title(applet));
        setUpClosing(frame);
        frame.getContentPane().add(applet);
        frame.setSize(w, h);
        applet.init();
        applet.start();
        frame.setVisible(true);
    }
}
