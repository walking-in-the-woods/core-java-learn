package messages;

/*
Deadlocks.

The problem is that - once one of the threads starts looping, the other one can't change the value of empty
because the thread is blocked. Only one method can run at a time so the thread that's looping is holding the lock
for the message object and the other one is blocked waiting for the other thread to release the lock.
This is what called a "Deadlock". So, we absolutely did need to synchronize both methods. And this is where the
wait(), the notify(), and the notifyAll() methods come into play.

    wait()      When a thread calls the wait() method it will suspend execution and release whatever locks
                it's holding until another thread issues a notification that something important has happened

    notify(), notifyAll()   the other thread issues the notification by calling the notify() or notifyAll() methods.

*/

/*
So, are we calling the notifyAll() rather than notify()? Well, because we can't notify a specific thread, and that's
because the notify() thread doesn't accept any parameters, it's conventional to use notifyAll() unless we're dealing
with a situation when there are a significant number of threads that all perform a similar task waiting for a lock.
So in that case we don't want to wake up every thread because when there's a lot of them that could result in a huge
performance here. So we call notify() so that only one thread is woken up. Since all the threads are waiting to do a
similar task whichever thread is woken up should be able to proceed.
*/

/*
There's a few atomic operations in Java that happen all at once. A thread can't be suspended in the middle of doing
them. Here are the atomic operations in Java: reading / writing reference variables, for example a statement
myObject1 = myObject2 would be atomic, also a thread can't be suspended if reading and writing primitive variables
except those of type long and double. And the third scenario was reading / writing all variables declared volatile.
*/

import java.util.Random;

public class MessagesMain {
    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}

class Message {
    private String message;
    private boolean empty = true;

    // for a consumer
    public synchronized String read() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    // for a producer
    public synchronized void write(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Humpty Dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the king's horses and the king's men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();

        for(int i=0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for(String latestMessage = message.read(); !latestMessage.equals("Finished");
            latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {

            }
        }
    }
}
