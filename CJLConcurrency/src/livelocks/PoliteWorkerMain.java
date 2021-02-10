package livelocks;

/*
Let's now move to the next potential problem we can encounter when working with threads.
"Live Lock" is similar to a deadlock, but instead of the threads been blocked they're actually constantly active
and usually waiting for all the other threads to complete their tasks. Now since all the threads are waiting for
others to complete, non of them can actually progress. So, let's say that the thread A will loop until thread B
complete its task, and thread B will loop until thread A complete its task. Thread A and Thread B can get into a state
in which they're both looping and waiting for the other to complete. That's actually what's called a "Live Lock".
The threads will never progress but they're not actually blocked.
*/

/*
The next potential problem that can arise in a multi-threaded application is called a "Slipped Condition".
This is a specific type of race condition (aka thread interference).

It can occur when a thread can be suspended between reading a condition and acting on it.

Let's say we have two threads reading from a buffer. Each thread does the following:

        1. Checks the status.
        2. If it's OK, reads data from the buffer.
        3. If the data is EOF, it sets the status to EOF and terminates. If the data isn't EOF,
           it sets the status to OK.

If we haven't synchronized the code properly, the following can happen:

        1. Thread1 checks the status and gets OK. It suspends.
        2. Thread2 checks the status and gets OK. It reads EOF from the buffer
           and sets the status to EOF, then terminates.
        3. Thread1 runs again. It tries to read data from the buffer, but there's no data.
           It throws an exception or crashes.

Because the threads can interfere with each other when checking and setting the condition, Thread1 tried to do
something based on obsolete information. When it checked the status, it was OK.

But by the time it acted on the condition it checked, the status had been updated by Thread2. Unfortunately,
Thread1 doesn't see the updated information, and because of that, it does something erroneous.

The solution to this is the same as it is for any type of thread interference: use synchronized blocks or locks
to synchronize the critical section of code.

If the cod is already synchronized, then sometimes the placement of the synchronization may cause the problem.
When using multiple locks, the order in which the locks can be acquired can also result in a slipped condition.
*/

public class PoliteWorkerMain {
    public static void main(String[] args) {
        final Worker worker1 = new Worker("Worker 1", true);
        final Worker worker2 = new Worker("Worker 2", true);

        final SharedResource sharedResource = new SharedResource(worker1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker1.work(sharedResource, worker2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker2.work(sharedResource, worker1);
            }
        }).start();
    }
}
