/* This class uses API introduced in 1.1. */

import java.util.Locale;

public class FirstClass {
    public String getString() {
        return Locale.getDefault().getDisplayName();
    }
}
