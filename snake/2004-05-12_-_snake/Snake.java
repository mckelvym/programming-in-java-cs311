import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Snake extends JFrame {

    static int x, y;
    static int width, height;
    static Color background;
    static Dimension screen;
    static JButton box[][];
    static Snake snake;
    static Toolkit toolkit;

    Snake() {
        super("Snake");
        this.setSize(400, 400);
        this.getContentPane().setBackground(Color.black);

        // center on the screen
        toolkit = this.getToolkit();
        screen = toolkit.getScreenSize();
        width = (int) screen.getWidth();
        height = (int) screen.getHeight();
        this.setLocation((width - 400) / 2, (height - 400) / 2);

        // allocate memory for all the buttons, and the bg color
        box = new JButton[20][20];
        background = new Color(0, 170, 0);

        // initialize and color the buttons
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                box[x][y] = new JButton();
                box[x][y].setBackground(Color.black);
            }
        }

        // add the buttons to the frame
        this.getContentPane().setLayout(new GridLayout(20, 20));
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                this.getContentPane().add(box[x][y]);
            }
        }

        // box[column][row]
        x = (int) (Math.random() * 20);
        y = (int) (Math.random() * 20);
        box[x][y].setBackground(background);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    if (y > 0)
                        y--;
                    box[x][y].setBackground(background);
                }
                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (y < 19)
                        y++;
                    box[x][y].setBackground(background);
                }
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (x > 0)
                        x--;
                    box[x][y].setBackground(background);
                }
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (x < 19)
                        x++;
                    box[x][y].setBackground(background);
                }
            }

            public void keyReleased(KeyEvent ke) {
            }

            public void keyTyped(KeyEvent ke) {
            }
        });
    }

    public static void main(String[] args) {
        snake = new Snake();
        snake.setVisible(true);
    }
}
