// mark mckelvy
// april 20, 2004
// copy, cut, paste app

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;

public class TextEditor extends JFrame {

    static int width = 700, height = 500;
    static int xpos, ypos;
    static Clipboard clipboard;
    static Dimension screen_dimension;
    static Font font;
    static JMenuItem jcopy, jcut, jpaste;
    static JPopupMenu popup;
    static JTextArea textArea;
    static SecurityManager securitymanager;
    static StringSelection stringselection;
    static Toolkit toolkit;

    static TextEditor textEditor;

    TextEditor() {
        super("TextEditor");
        this.setSize(width, height);

        // center the window on the screen
        screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
        xpos = (int) ((screen_dimension.getWidth() - width) / 2);
        ypos = (int) ((screen_dimension.getHeight() - height) / 2);
        this.setLocation(xpos, ypos);

        // initialize variables
        popup = new JPopupMenu();
        jcopy = new JMenuItem("Copy");
        jcut = new JMenuItem("Cut");
        jpaste = new JMenuItem("Paste");
        textArea = new JTextArea();
        toolkit = Toolkit.getDefaultToolkit();

        // add objects
        this.getContentPane().add(textArea);

        popup.add(jcopy);
        popup.add(jcut);
        popup.addSeparator();
        popup.add(jpaste);
        textArea.add(popup);

        // set the font
        font = new Font("Comic Sans MS", Font.PLAIN, 16);
        textArea.setFont(font);

        // cuts all text and sends it to the clipboard
        jcut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jcut) {
                    System.out.println("Trying to Cut..");
                    SendToClipboard s2c = new SendToClipboard(textArea.getText());
                    textArea.setText("");
                }
            }
        });
        // copies all text and sends it to the clipboard
        jcopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jcopy) {
                    System.out.println("Trying to Copy..");
                    SendToClipboard s2c = new SendToClipboard(textArea.getText());
                }
            }
        });
        // pastes texts from clipboard (if any)
        jpaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jpaste) {
                    System.out.println("Trying to Paste..");
                    GetClipboard gc = new GetClipboard();
                    textArea.setText(gc.getData());
                    System.out.println("Paste Successful");
                }
            }
        });
        // shows the popupmenu for right clicks
        textArea.addMouseListener(new MouseListener() {
            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(textArea, e.getX(), e.getY());
                    System.out.println("Right Click");
                }
            }
        });
        // stops program if window is closed
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        textEditor = new TextEditor();
        textEditor.setVisible(true);
    }

    // gets text from clipboard
    class GetClipboard {

        boolean returntemp = true;
        String temp;
        Transferable transferable;

        public String getData() {
            // obtain access to the clipboard
            clipboard = toolkit.getSystemClipboard();
            // requests the contents of the clipboard passing an owner (null)
            transferable = clipboard.getContents(null);
            try {
                // if the contents are of datatype String
                if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    temp = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    returntemp = true;
                    System.out.println("String Copied from Clipboard");
                } else {
                    returntemp = false;
                    System.out.println("No String on Clipboard");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // contents copied into temp variable
            if (returntemp)
                return temp;
                // contents not copied into temp variable
            else
                return "";
        }
    }

    // copies text to clipboard
    class SendToClipboard implements ClipboardOwner {

        boolean ok2proceed = true;

        SendToClipboard(String info) {
            // used to check for clipboard access
            securitymanager = System.getSecurityManager();
            // throws SecurityException if access the system clipboard not allowed.
            if (securitymanager != null) {
                try {
                    securitymanager.checkSystemClipboardAccess();
                } catch (SecurityException e) {
                    ok2proceed = false;
                }
            }
            if (ok2proceed) {
                // a datatransfer type object must be used for setting objects on the system clipboard
                stringselection = new StringSelection(info);
                // "gets" the system clipboard
                clipboard = toolkit.getSystemClipboard();
                // sets the string on the clipboard, using the parameters of a datatransfer object
                // and a clipboard owner (this)
                clipboard.setContents(stringselection, this);
                System.out.println("Copy Successful");
            } else
                System.out.println("Copy Error");
        }

        public void lostOwnership(Clipboard c, Transferable t) {
            System.out.println("Lost Ownership of System Clipboard");
        }
    }
}
