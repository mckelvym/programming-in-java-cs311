/*
 * This class uses FirstClass, which in turn uses API
 * introduced in 1.1.
 */

public class SecondClass {

    public static void main(String[] args) {
        FirstClass stringGetter = new FirstClass();

        System.out.println(stringGetter.getString());
    }
}
