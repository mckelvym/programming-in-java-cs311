public class MyLookAndFeel extends JFrame {
    String[] toppings = {"Pepperoni", "Sausage", "Cheese", "Jalapenos"};
    Component[] examples = {new JButton("Button"), new JTextField("TextField"), new JLabel("Label"),
            new JRadioButton("Radio"), new JCheckBox("CheckBox"), new JList(toppings), new JComboBox(toppings)};

    public MyLookAndFeel() {
        super("Look and Feel");
        Container cp = getContentPane();
        cp.setLayout(new FlowFlayout());
        for (int i = 0; i < toppings.length; i++)
            cp.add(toppings[i]);
    }

    private static void usageError() {
        System.out.println("Usage:LookAndFeel [cross | system|motif]");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length == 0)
            usageError();
        if (args[0].equals("cross") {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }else if (args[0].equals("system") {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStack...} ;
        }else if (args[0].equals("motif") {
            try {
                UIManager.setLookAndFeel("com.sun.java." + "swing.plaf.motif.MotifLookAndFeel");
            } catch (Exception e) {
                e....
            }
        }
        else usageError();
        Console.run(new MyLookAndFeel(), 300, 200);
    }
}
