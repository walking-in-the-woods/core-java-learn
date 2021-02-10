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

/*
There is an alternative to use a synchronized block. We're going to use a "FairLock". Fair locks try to be
First Come First Served. Previously, we used the re-entrant lock class which implements the lock interface.
The re-entrant lock implementation allow us to create fair locks. Not all lock implementations do that. The interface
doesn't dictate that locks have to be fair. So it's important to read the documentation for any lock before to use it.
(to see what type it is).

The parameter for the ReentrantLock(true) is whether it's a fair lock, so first come first served or not so.
True means that we are doing it that way, we want it to be set up to be a FCFS order or to use a FCFS order.
Now a few important notes about using a fair re-entrant lock.

    1. Only fairness in acquiring the lock is guaranteed
       not fairness in threads scheduling. So it's possible that the thread that gets the lock will execute a task
       that takes a long time. So when using fair locks it's possible for threads to still have to wait a long to run.
       The only thing a fair lock guarantees is the FCFS ordering for getting the lock.

    2. The tryLock() method doesn't honor the fairness settings, so it will not be FCFS.

    3. When using fair locks with a lot of threads performance will be impacted. To ensure fairness there has to be
       an extra layer of processing that manages which thread gets the lock. So that can ultimately slow things down
       when there's a lot of threads competing for that lock.
*/

import java.util.concurrent.locks.ReentrantLock;

public class StarvationMain {
    private static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {

        Thread t1 = new Thread(new Worker(ThreadColor.ANSI_RED), "Priority 10");
        Thread t2 = new Thread(new Worker(ThreadColor.ANSI_BLUE), "Priority 8");
        Thread t3 = new Thread(new Worker(ThreadColor.ANSI_GREEN), "Priority 6");
        Thread t4 = new Thread(new Worker(ThreadColor.ANSI_CYAN), "Priority 4");
        Thread t5 = new Thread(new Worker(ThreadColor.ANSI_PURPLE), "Priority 2");

        // we're setting priority here, but we're getting a random output anyway
        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(6);
        t4.setPriority(4);
        t5.setPriority(2);

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
                lock.lock();
                try {
                    System.out.format(threadColor + "%s: runCount = %d\n",
                            Thread.currentThread().getName(), runCount++);
                    // execute critical section of code
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
