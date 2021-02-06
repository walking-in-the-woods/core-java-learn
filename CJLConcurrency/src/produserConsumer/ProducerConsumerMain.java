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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static produserConsumer.ProducerConsumerMain.EOF;

public class ProducerConsumerMain {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<String>();
        ReentrantLock bufferLock = new ReentrantLock();

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable {
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for(String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                bufferLock.lock();
                buffer.add(num);
                bufferLock.unlock();

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");
        bufferLock.lock();
        buffer.add("EOF");
        bufferLock.unlock();
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
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        while (true) {
            bufferLock.lock();
            if (buffer.isEmpty()) {
                bufferLock.unlock();
                continue;
            }
            if (buffer.get(0).equals(EOF)) {
                System.out.println(color + "Exiting");
                bufferLock.unlock();
                break;
            } else {
                System.out.println(color + "Removed" + buffer.remove(0));
            }
            bufferLock.unlock();
        }
    }
}
