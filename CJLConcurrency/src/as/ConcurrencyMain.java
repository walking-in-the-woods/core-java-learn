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

/*
An atomic action can't be suspended in the middle of being executed. It either completes, or it doesn't happen at all.
Once a thread starts to run an atomic action, we can be confident that it won't be suspended until it has completed
the action.

In Java, we mentioned that the following operations are atomic:

    1. Reading and writing reference variables. For example, the statement myObject1 = myObject2 would be atomic.
       A thread can't be suspended in the middle of executing statement.

    2. Reading and writing primitive variables, except those of type long and double. The JVM may require two
       operations to read and write longs and doubles, and a thread can be suspended between each operation.
       For example, a thread can't be suspended in the middle of executing myInt = 10. But it can be suspended
       in the middle of executing myDouble = 1.234.

    3. Reading and writing all variables declared volatile.

Let's take a look at the third item: volatile variables. Because of the way Java manages memory, it's possible
to get memory consistency errors when multiple threads can read and write the same variable.

Each thread has a CPU cache, which can contain copies of values that are in main memory. Since it's faster to read
form the cache, this can improve the performance of an application. There wouldn't be a problem if there was only
one CPU, but most computers have more than one CPU.

When running an application, each thread may be running on a different CPU, and each CPU has it's own cache.
It's possible for the values in the caches to become out of synch with each other, and with the value in main
memory - a memory consistency error.

Suppose we have two threads that use the same int counter. Thread1 reads and writes the counter. Thread2 only reads
the counter. Reading and writing to an int is an atomic action. A thread won't be suspended in the middle of
reading or writing the value to memory. But let's suppose that Thread1 is running on CPU1, and Thred2 is running
on CPU2. Because of CPU caching, the following can happen:

    1. The value of the counter is 0 in main memory
    2. Thread1 reads the value of 0 from main memory.
    3. Thread1 adds 1 to the value.
    4. Thread1 writes the value of 1 to its CPU cache.
    5. Thread2 reads the value of counter from main memory and gets 0, rather than the latest value, which is 1.

This is where volatile variables come in. When we use a non-volatile variable the JVM doesn't guarantee when it
writes an updated value back to main memory. But when we use a volatile variable, the JVM writes the value back
to main memory immediately after a thread updates the value in its CPU cache. It also guarantees that every time
a variable reads from a volatile variable, it will get the latest value.

To make a variable volatile, we use the "volatile" keyword.

    public volatile int counter;

Note that, if more than one thread can update the value of a volatile variable, we can still get a race condition.

Let's assume that we have two threads that share a volatile int counter, and each thread can run the following code:

    counter++

This isn't an atomic operation. A thread has to do the following:

    1. Read the value of counter from memory
    2. Add 1 to counter
    3. Write the new value of counter back to memory

A thread can be suspended after any of these steps. Because of that, the following can happen:

    1. The value of the counter is 1 in main memory and in Thread1 and Thread2's CPU caches.
    2. Thread1 reads the value of counter and gets 1.
    3. Thread2 reads the value of counter and gets 1.
    4. Thread1 increments the value and gets 2. It writes 2 to its cache. The JVM immediately writes 2
       to main memory.
    5. Thread2 increments the value and gets 2. It writes 2 to its cache. The JVM immediately writes 2
       to main memory.
    6. Oops! The counter has been incremented twice, so its value should now be 3.

A memory consistency error like this can occur when a thread can update the value of the variable in a way that
depends on the existing value of the variable. In the counter++ case, the result of the increment depends on
the existing value of the variable.

In other words, a thread has to read the value of the counter variable in order to generate a new value for counter.
By the time the thread operations on the value it has read, the value could be state, as it is in this example.

Whether to synchronize when using a volatile will depend on the code and what the thread will be doing.
A common use of volatile is with variables of type long and double. Reading and writing longs and doubles isn't atomic.

Using volatile makes them atomic but we'd still have to use synchronization in the situation when a thread reads
the value of a variable and then uses the value, which may be state, to generate a new value for the variable.

However, when only one thread can change the value of a shared variable, or none of the threads update the value
of a shared variable in a way that depends on its existing value, using the volatile keyword does mean that we
don't need to synchronize the code. We can be confident that the value in main memory is always the latest value.

That's great, and we can see that there will be times when using the volatile keyword will eliminate the need for
synchronization. But it would be nice if we could read and write variables without having to worry about thread
interference or memory consistency errors. Fortunately, Java provides classes that allows us to do just that,
in specific cases.

The java.util.concurrent.atomic package provides us with classes that we can use to ensure that reading and writing
variables is atomic.

Let's suppose we have multiple threads using the following Counter class:

    class Counter {

        public int counter = 0;

        public void inc() {
            counter++;
        }

        public void dec() {
            counter--;
        }

        public int value() {
            return counter;
        }
    }

Well, increment and decrement operations aren't atomic. Declaring the counter as volatile is one potential solution,
but the memory consistency errors can still be possible, which would be true here for the inc() and dec() method.
Since a thread changes the value of counter in a way that depends on the existing value of counter, the values
within the thread CPU caches may get out of synch with the value in main memory.

Instead of using an int counter, we'll update the code to use an AtomicInteger object. When using one of the Atomic
types, we don't have to worry about thread interference. As the Java documentation states, the classes in the
java.util.concurrent.atomic package "support lock-free thread-safe programming on single variables."

We declare the counter as type AtomicInteger and pass 0 as the initial value. In the inc() method, we use
incrementAndGet(). This atomically increases the value by 1. The method decrementAndGet() decrease the value by 1.
To get the value, we call the get() method. We don't have to synchronize the increment or decrement operations
in any way.

    private AtomicInteger counter = new AtomicInteger(0);

    public void inc() {
        counter.incrementAndGet();
    }

    public void dec() {
        counter.decrementAndGet();
    }

    public int value() {
        return counter.get();
    }

We can't use AtomicInteger as a substitute for an Integer object. AtomicIntegers are meant to be used in specific
situations like the one above: when thread interference can take place because a thread is changing the value of the
variable in a way that depends on the existing value.

There are Atomic classes for the following types: boolean, integer, integer array, long, long array, object reference,
and double. So, we could use the AtomicLong and AtomicDouble classes to make longs and doubles atomic.

The Atomic classes have set() and get() methods that allow us to set a value, and get the current value.
But these classes are intended to be used when the code is using a loop counter, or generating a sequence of numbers
for some other reason.

And there is one more method should be mentioned, it's the compareAndSet() method.This method takes two parameters:
the excepted value, and the new value that we want to set. If the current value doesn't equal the expected value,
the method returns false and the set doesn't take place. If the current value equals the expected value, then the set
goes ahead and the method returns true.
*/

public class ConcurrencyMain {
    public static void main(String[] args) {
        System.out.println(ThreadColor.ANSI_PURPLE + "Hello from the main thread.");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();

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
                try {
                    anotherThread.join(); // anotherThread.join(2000);
                    System.out.println(ThreadColor.ANSI_RED +
                            "AnotherThread terminated, or timed out, so I'm running again");
                } catch (InterruptedException e) {
                    System.out.println(ThreadColor.ANSI_RED + "I couldn't wait after all. I was interrupted");
                }
            }
        });

        myRunnableThread.start();
//        anotherThread.interrupt(); // to call an InterruptedException

        System.out.println(ThreadColor.ANSI_PURPLE + "Hello again from the main thread.");
    }
}
