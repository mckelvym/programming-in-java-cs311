// prints info about a threadgroup and its subthreads, including daemons
public class ThreadInfo {

    public static void main(String[] args) {

        Thread[] threads = new Thread[5];
        ThreadGroup group = new ThreadGroup("Thread Group");

        if (args.length > 0) {
            //main thread
            Thread thread = Thread.currentThread();
            thread.setName(args[0]);
        }
        // creates new threads and puts them in one threadgroup
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(group, "Thread #" + (i + 1));
        }
        ThreadInfo.printAllThreadInfo();
    }

    // print information on every thread
    public static void printAllThreadInfo() {

        ThreadGroup parent, root;
        //gets the parent thread group
        root = parent = Thread.currentThread().getThreadGroup();

        // goes to the "top of the list", starting from the parent
        while ((parent = parent.getParent()) != null) {
            root = parent;
        }
        System.out.println();
        ThreadInfo.printThreadGroupInfo("\t", root);
    }

    // prints information about the subthreads in a threadgroup
    public static void printThreadGroupInfo(String indent, ThreadGroup group) {

        int numThreads;
        int numGroups;
        Thread threads[];
        ThreadGroup groups[];
        // final int safety = 5;

        if (group == null) {
            return;
        }
        System.out.println(indent + "Thread Group: " + group.getName()
                + " " + "MAX PRIORITY: " + group.getMaxPriority()
                + (group.isDaemon() ? "[DAEMON] " : " "));

        // print info about component threads
        numThreads = group.activeCount();
        threads[] =new Thread[numThreads]; // + safety];
        // copies all active threads into new array
        numThreads = group.enumerate(threads, false);

        // print information about each thread in this threadgroup
        for (int i = 0; i < numThreads; i++) {
            printThreadInfo(indent + " ", threads[i]);
        }

        // gets the # active thread groups
        numGroups = group.activeGroupCount();
        groups[] =new ThreadGroup[numGroups]; //+ safety];

        // this was left out:
        // copies all the remaining groups into this group?
        group.enumerate(groups);

        // print information about all the threadgroups
        for (int i = 0; i < numGroups; i++) {
            printThreadGroupInfo(indent + " ", groups[i]);
        }
    }

    // prints information about a single thread
    public static void printThreadInfo(String indent, Thread thread) {

        if (thread != null) {
            System.out.println(indent + "*THREAD: " + thread.getName()
                    + "; [PRIORITY: " + thread.getPriority() + "]"
                    + (thread.isDaemon() ? " [DAEMON] " : " ")
                    + (thread.isAlive() ? " [ALIVE] " : " [DEAD] ")
                    + ((Thread.currentThread() == thread) ? " [CURRENT] " : " "));
        } else {
            return;
        }
    }
}
