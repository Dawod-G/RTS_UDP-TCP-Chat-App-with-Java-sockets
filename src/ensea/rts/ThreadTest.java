package ensea.rts;

public class ThreadTest extends Thread {
    private int counter = 0; // Counter to track successive calls

    /**
     * The run method is executed when the thread is started.
     * It continuously prints the thread name and counter value,
     * then sleeps for 250 milliseconds.
     */
    @Override
    public void run() {
        while (true) {
            System.out.println(getName() + " : " + counter++);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ThreadTest thread = new ThreadTest();
            thread.start();
        }
    }
}