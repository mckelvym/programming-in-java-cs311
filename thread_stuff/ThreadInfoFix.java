// Utility for displaying information on currently running threads
public class ThreadInfo {
    public static void main(String[] args) {
        Thread[] threads = new Thread[4];
        ThreadGroup group = new ThreadGroup("MyThreadGroup");

        if (args.length > 0) {
            Thread thread = Thread.currentThread(); //main thread
            thread.setName(args[0]);
        }

        for (int i = 0; i < 4; i++)
            threads[i] = new Thread(group, "MyThread#" + (i + 1));

        ThreadInfo.printAllThreadInfo();
    }

    //lists info about all threads inthe current thread group
    public static void printAllThreadInfo() {
        ThreadGroup parent, root;
        root = parent = Thread.currentThread().getThreadGroup();    //gets the parent thread grp

        while ((parent = parent.getParent()) != null) {
            root = parent;
        }

        System.out.println();
        printThreadGroupInfo("\t", root);
    }


    public static void printThreadGroupInfo(String indent, ThreadGroup group) {
        //final int safety = 5;

        if (group == null)
            return;

        System.out.println(indent + "Thread Group : " + group.getName() + "  " + "MAX PRIORITY: " +
                group.getMaxPriority() + (group.isDaemon() ? "[DAEMON] " : " "));

        //print info about component threads
        int numThreads = group.activeCount();
        Thread threads[] = new Thread[numThreads];

        numThreads = group.enumerate(threads, false);

        for (int i = 0; i < numThreads; i++) {
            printThreadInfo(indent + "\t", threads[i]);
        }

        int numGroups = group.activeGroupCount();    //returns the # of active thread groups
        ThreadGroup groups[] = new ThreadGroup[numGroups];
        group.enumerate(groups);

        for (int i = 0; i < numGroups; i++) {
            System.out.println();
            printThreadGroupInfo(indent + "\t", groups[i]);
        }
    }


    public static void printThreadInfo(String indent, Thread thread) {
        if (thread == null)
            return;

        System.out.println(indent + "THREAD : " + thread.getName() + "; PRIORITY : "
                + thread.getPriority() + (thread.isDaemon() ? "[DAEMON]" : " ") +
                (thread.isAlive() ? "[ISALIVE]" : "[DEAD]") +
                ((Thread.currentThread() == thread) ? "<==CURRENT" : " "));
    }
}
