package deadlocks;

/*
The first common pitfall is a Deadlock.

Deadlock occurs when two or more threads are blocked on locks and every thread that's blocked is holding a lock
that another blocked thread wants. For example thread 1 is holding lock 1 and waiting to acquire lock 2, but
thread 2 is holding lock 2 and waiting to acquire lock 1. Because all the threads holding the locks a blocked
they will never release the locks they're holding, and so none of the waiting threads will actually ever run.
*/

/*
So, how to prevent a deadlock situation from happening?

    1. One solution would be try to lock on a single object, if we run the cod so that only one lock is required.
       The code won't deadlock, but unfortunately that has not a practical solution for many applications that need
       multiple locks.

    2. The second way to prevent deadlocks is to require that all threads must first try to obtain the locks
       in the same order.
*/

public class DeadlocksMain {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1: Has lock 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 1: Waiting for lock 2");
                synchronized (lock2) {
                    System.out.println("Thread 1: Has lock 1 and lock 2");
                }
                System.out.println("Thread 1: Released lock 2");
            }
            System.out.println("Thread 1: Released lock 1. Exiting...");
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            //synchronized (lock2) {
            synchronized (lock1) {
                //System.out.println("Thread 2: Has lock 2");
                System.out.println("Thread 2: Has lock 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                //System.out.println("Thread 2: Waiting for lock 1");
                System.out.println("Thread 2: Waiting for lock 2");
                //synchronized (lock1) {
                synchronized (lock2) {
                    //System.out.println("Thread 2: Has lock 2 and lock 1");
                    System.out.println("Thread 2: Has lock 1 and lock 2");
                }
                //System.out.println("Thread 2: Released lock 1");
                System.out.println("Thread 2: Released lock 2");
            }
            //System.out.println("Thread 2: Released lock 2. Exiting...");
            System.out.println("Thread 2: Released lock 1. Exiting...");
        }
    }
}
