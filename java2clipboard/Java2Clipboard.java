import java.awt.datatransfer.*;
import java.awt.*;

public class Java2Clipboard implements ClipboardOwner {
    public static void main(String[] args) throws Exception {
        Java2Clipboard jc = new Java2Clipboard();
        jc.toClipboard();
        Frame f = new Frame("Open a text editor and paste the message from Java");
        f.setSize(600, 10);
        f.show();
    }

    public void toClipboard() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            try {
                sm.checkSystemClipboardAccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toolkit tk = Toolkit.getDefaultToolkit();
        StringSelection st = new StringSelection("Hello world from Java");
        Clipboard cp = tk.getSystemClipboard();
        cp.setContents(st, this);
    }

    public void lostOwnership(Clipboard clip, Transferable tr) {
        System.out.println("Lost Clipboard Ownership?!?");
    }
}
