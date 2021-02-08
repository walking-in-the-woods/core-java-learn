package produserConsumer;

/*
The introduction of the java.util.concurrent package synchronization was really the only error we had in our quiver
when it came to dealing with thread interference, but it does still have its drawbacks:

    1. The first drawback is that threads are blocked waiting to execute synchronize code can't be interrupted.
       Once they're blocked, they are stuck there until they get the lock for the object the code is synchronizing on.

    2. The second drawback is that the synchronized block must be within the same method. In other words,
       we can't start a synchronized block in one method and end the synchronization block in another for
       obvious reasons.

    3. The third drawback is that we can't test to see if an object's intrinsic lock is available or find out
       any other information about that lock. Also if the lock isn't available, we can't timeout after we waited
       for the lock for a while. When we reach the beginning of a synchronized block, we can either get the lock
       and continue executing or block at that line of code until we get the lock.

    4. The fourth drawback is that if multiple threads are waiting to get a lock, it's not first come first served.
       There isn't a set order in which the JVM will choose the next thread that gets the lock. So, the first thread
       that blocked could be the last to get the lock and vice versa.

So instead of using synchronization, we can prevent thread interference using classes that implement the
java.util.ConcurrentLocks.Lock interface
*/

/*
ReentrantLock

If a thread is already holding a reentrant lock when it reaches the code that requires the same lock, it can continue
executing. It doesn't have to obtain the lock again. Not all implementations of the lock interface are re-entrant.

Re-entrant lock object can keep track of how many times they locked. It a thread gets the same lock twice it has ot
release it twice before another thread can get the lock.
*/

/*
The "Thread Poll" is a managed set of threads. It reduces the overhead of thread creation, especially in applications
that use a large number of threads. A thread poll may also limit the number of threads that are active running
a blocked at any one particular time. When using a certain types of thread polls, an application can't run wild
and create an expensive number of threads. In Java we use thread polls through the executive service implementations.
Now, if we want to, we can actually implement our own thread poll by creating a class that implements one of the thread
poll interfaces, and by doing so we can configure how the underlying thread poll ultimately is managed.
But it's recommended that we use the implementations provided by the JVM in most situations.
Now, since thread polls can limit the number of active threads, it's possible that when we asked the service to run
a task, it won't be able to run it straight away. For example, if the maximum number of threads has been set to 20,
they may already be 20 active threads when we submitted a task. In that case the task will have to wait on the
services queue until one of the active threads actually terminates. The executive service interface extends
the executor interface which is only got one method execute().
 */

/*
Now, as with the lock interface, sub interfaces and implementations of executor offer more functionality than
the base interface. We have to use factory methods in the executors class to create the instances that implement
executor service. We can create several different types of executor services based on the type of thread pool
we want to service to use. A fixed thread poll means that there's any really ever going to be a specific number
of threads available to process tasks at any one time. If all the threads are busy and more tasks as a better execution,
those tasks will just have to wait in a queue. so let's
 */

/*
When we want to recieve a value back from a thread that we are executing in the background, we can use the submit()
method. The submit() method accepts a callable object which is very similar to a runnable object except that it can
return a value. The value can be returned as an object of type "future". So  just to be clear, the call to the
future.get() method blocks until the result is available. So, when we calling it from the main thread, application
will be frozen until the results available. So, we wouldn't use this method in a UI application, and in fact, we
wouldn't use the services in the java.util.concurrent package in a UI application. When working with JavaFX,
we instead use the classes in the javafx.concurrent package to run on background threads and to process the results.
The classes in the JavaFX package leverage the classes in java.util.concurrent package in a way that makes sense
for UI applications.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static produserConsumer.ProducerConsumerMain.EOF;

public class ProducerConsumerMain {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_WHITE + "I'm being printed for the Callable class");
                return "This is the callable result";
            }
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("Something went wrong");
        } catch (InterruptedException e) {
            System.out.println("Thread running the task was interrupted");
        }

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for(String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");
        try {
            buffer.put("EOF");
        } catch (InterruptedException e) {

        }
    }
}

/*
MyConsumer class implements Runnable, so that it can run its code on the thread and accepts the buffer shared from the
producer and the color its going to use when it prints to the console. When the run() method the Consumer will loop
until it reads the EOF, the "End Of File" from the buffer. Inside the loop, we first check to see if there's
anything to read, and if there isn't we loop back around and check again, and then keep checking until the buffer
isn't empty. When that's the case, it then looks at the first element in the buffer to see if it's that end of file
(EOF). If it is, it prints that it's exiting to the console, and then breaks out of the loop. It doesn't remove th EOF
string because if it did that, other consumer threads would loop indefinitely. If EOF is removed, the condition
that checks for EOF would never be true for the other consumer threads. ANd that's why we specifically exiting
without removing that particular entry.
*/

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> buffer;
    private String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        while (true) {
            synchronized (buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    if (buffer.peek().equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed" + buffer.take());
                    }
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
