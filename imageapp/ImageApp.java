import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.*;

public class ImageApp extends JFrame {

    static int num_files;
    static Dimension screen;
    static File file_d, file[];
    static Image image;
    static ImageIcon imageicon;
    static ImageApp imageApp;
    static JButton b_image;
    static JLabel lbl_image;
    static String[] str_files;
    static String path;

    public ImageApp() {
        super("Image Application");
        this.setSize(400, 400);
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((screen.getWidth() - 400) / 2), (int) ((screen.getHeight() - 400) / 2));
        this.getContentPane().setLayout(new GridLayout(2, 1));

        file_d = new File(new JApplet().getDocumentBase().toString());
        path = file_d.toString();
        file_d = new File(path);
        str_files = new String[file_d.list().length];
        str_files = file_d.list();
        num_files = Array.getLength(str_files);
        System.out.println(num_files);

        file = new File[num_files];
        for (int i = 0; i < num_files; i++) {
            file[i] = new File(path, str_files[i]);
        }

        b_image = new JButton("Next");
        image = this.getToolkit().getImage("1.jpg");
        imageicon = new ImageIcon(image);
        lbl_image = new JLabel(imageicon);
        this.getContentPane().add(lbl_image);

        b_image.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b_image) {
                }
            }
        });
    }

    public static void main(String[] args) {
        imageApp = new ImageApp();
        imageApp.setVisible(true);
    }
}
