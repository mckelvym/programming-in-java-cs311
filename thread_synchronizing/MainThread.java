public class MainThread {
    static PrintWriter out = new PrintWriter(System.out, true);

    public static void main() {
        FirstThread first =
                SecondThread second =
                first.start();
        second.start();
        try {
            out.println("Waiting for first thread to finish...");
            first.join(); //main thread waits until first thread finishes
            out.println("It's a long wait");
            out.println("Waking up for second thread...");
            synchronized (second) {
                second.notify();
            }
            Sys("Waiting for seonc thread to finish..");
            second.join();
        } catch (InterruptedException e) {
        }
        Sys("I'm ready to finish too");
    }
}

class FirstThread extends Thread {
    public void run() {
        try {
            MainThread.out.println("   First  thread starts running ");
            sleep(10000);
            MainThread.out.println("    First thread finishes running");
        } catch (InterruptedException e) {
        }
    }
}

class SecondThread extends Thread {
    public synchronized void run() {
        try {
            MainThread.out.println("   Second Thread starts runnning");
            MainThread.out.println("   Second thread suspends running");
            wait();
            MainThread.out.println("   Second Thread runs again and finishes");
        } catch (...){
        }
    }
}
