import java.util.*;

public class Hash {

    static Hash hash;

    Hash() {
        Hashtable hs = new Hashtable();
        hs.put("vini", "facehater");
        hs.put("sanjeetha", "teacher");
        hs.put("conan", "done");
        System.out.println("conan: " + ((String) hs.get("conan")));
        System.out.println("vini: " + ((String) hs.get("vini")));
        System.out.println("sanjeetha: " + ((String) hs.get("sanjeetha")));
    }

    public static void main(String[] args) {
        hash = new Hash();
    }
}
