// mark mckelvy
// java tr
// jan 15, 2004
// hw 1

// 1

import java.applet.*;
import java.awt.*;

public class HelloWorld extends Applet {
    public void paint(Graphics g) {
        g.drawString("Hola Mundo!", 50, 25);
    }
}

// 2
/* The output of useless.java is:
java.lang.NoClassDefFoundError: 01
Exception in thread "main"
*/

// 3
// The error was that the string was missing an endquote.
class HelloWorldApp2 {
    public static void main(String[] args) {
        System.out.println("Hello World!"); //Display the string.
    }
}

// 4
// The applet looks the same, but with everything bigger or stretched.

// 5
// FirstClass' output was:
java.lang.NoClassDefFoundError:01
        Exception in thread"main"

// SecondClass' would not compile because of errors.
