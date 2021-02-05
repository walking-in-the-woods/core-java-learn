package as;

/*
A PROCESS is a unit of execution that has its own memory space.

Each instance of a JVM runs as a process (this isn't true for all JVM implementations, but for most of them).
When we run Java application, we're kicking off a process. Many people will use the terms process and application
interchangeably, and will, too. If one Java application is running and we run another one, each application has
its own memory space of HEAP. The first Java application can't access the heap that belongs to the second
Java application. The heap isn't shared between them. They have their own.

A THREAD is a unit of execution within a process. Each process can have multiple threads. In Java, every process
(or application) has at least one thread, the "main thread" (for UI applications, this is called
"JavaFX application thread"). In fact, just about every Java process also has multiple system threads that handle
tasks memory management and I/O. We, the developers, don't explicitly create and code those threads. Our code runs
on the main thread, or in other threads that we explicitly create.

Creating a thread doesn't require as many resources as creating a process. Every thread created by a process shares
the process's memory and files. This can create problems.

In addition th the process's memory, or heap, each thread has what's called a "thread stack", which is the memory
that only that thread can access.

So, every Java application runs a single process, and each process can have multiple threads.
Every process has a heap, and every thread has a thread stack.

There are two main reasons to use multiple threads instead of sticking with the main thread.

    1. We sometimes want to perform a task that's going to take a long time. For example, we might want to query
       a database, or we might want to fetch data from somewhere on the internet. We could do this on the main thread,
       but the code within each main thread executes in a linear fashion. The main thread won't be able to do
       anything else while it's waiting for the data.

       Another way of putting this is that the execution of the main thread will be suspended.It has to wait for
       the data to be returned before it can execute the next line of code. To the user, this could appear as if our
       application has died or is frozen, especially when we're dealing with a UI application.

    2. A second reason we might want to use threads is because an API requires us to provide one. Sometimes we
       have to provide the code that will run when a method we've called reaches a certain point in it's execution.
       In this instance, we usually don't create the thread. We pass in the code that we want to run on the thread.

If it's a concurrent application, it can download a bit of data, then switch to drawing pert of the shape, then
switch back to downloading some more data, then switch back to drawing more of the shape, etc.
Concurrency means that one task doesn't have to complete before another can start. Java provides thread-related
classes so that we can create Java concurrent applications.
*/

/*
Most of time developers use the runnable way of doing things. The reason is that - it's more convenient and there's
also many methods in the Java API that when a runnable for instance passed to them. FOr example since the
introduction of lambda expressions it's become even more convenient to use anonymous runnable instances. So when
we have a choice because we're not calling a method that requires one or the other there isn't really a right or wrong
answer, but most developers use a runnable because it's more flexible.

A thread will terminate when it returns from its run() method either implicitly because it reaches the methods end
or explicitly because it executes a return state. A common mistake when creating and running threads is to call
the threads instance run() method instead of the start() method. We have to implement the run() method but we don't
call it directly. The jvm will actually call it for us. What happens if we make this mistake? Our application won't
crash, but instead of running the code in the run method on a new thread, the code is going to be run on the same
thread that called the run() method.
*/

public class ConcurrencyMain {
    public static void main(String[] args) {
        System.out.println(ThreadColor.ANSI_PURPLE + "Hello from the main thread.");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();
//        anotherThread.run(); // we'll get "Hello from main"

        // New thread in an anonymous class
        new Thread() {
            public void run() {
                System.out.println(ThreadColor.ANSI_GREEN + "Hello from the anonymous class thread");
            }
        }.start();

//        Thread myRunnableThread = new Thread(new MyRunnable());
        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ThreadColor.ANSI_RED + "Hello from the anonymous class's implementation of run()");
            }
        });
        myRunnableThread.start();
        System.out.println(ThreadColor.ANSI_PURPLE + "Hello again from the main thread.");

//        anotherThread.start(); // throws an exception because the calling thread has already started
    }
}
