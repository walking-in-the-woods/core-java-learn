package starvation;

/*
When Starvation occurs, it's not that threads will never progress because they'll never get a lock,
but that they rarely have the opportunity to run and progress. Starvation often occurs due to thread priority.
When we assign a high priority as to a thread, we are suggesting to the operating system that it should try
and run the thread before other waiting threads. When we use synchronized blocks it's not first come first served.
When there's more than one thread waiting for a lock and that lock becomes available, the operating system won't
necessarily choose the thread that has been waiting the longest to run. So the first thread could wait for a long time,
especially when the other threads have higher priority then the first one.
*/

public class StarvationMain {
    private static Object lock = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Worker(ThreadColor.ANSI_RED), "Priority 10");
        Thread t2 = new Thread(new Worker(ThreadColor.ANSI_BLUE), "Priority 8");
        Thread t3 = new Thread(new Worker(ThreadColor.ANSI_GREEN), "Priority 6");
        Thread t4 = new Thread(new Worker(ThreadColor.ANSI_CYAN), "Priority 4");
        Thread t5 = new Thread(new Worker(ThreadColor.ANSI_PURPLE), "Priority 2");

        // we're setting priority here, but we're getting a random output anyway
//        t1.setPriority(10);
//        t2.setPriority(8);
//        t3.setPriority(6);
//        t4.setPriority(4);
//        t5.setPriority(2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    private static class Worker implements Runnable {
        private int runCount = 1;
        private String threadColor;

        public Worker(String threadColor) {
            this.threadColor = threadColor;
        }

        @Override
        public void run() {
            for(int i=0; i<100; i++) {
                synchronized (lock) {
                    System.out.format(threadColor + "%s: runCount = %d\n",
                            Thread.currentThread().getName(), runCount++);
                    // execute critical section of code
                }
            }
        }
    }
}
