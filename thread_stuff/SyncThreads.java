import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SyncThreads extends JFrame {

    static SyncThreads syncThreads;

    SyncThreads() {
    }

    public static void main(String[] args) {
        syncThreads = new SyncThreads();
        syncThreads.setVisible(true);
    }
}
