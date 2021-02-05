package counter;

/*
If we use "for(int i=10; i > 0; i--) { ... }" in the Countdown class, we get:

    Thread 2: i = 10
    Thread 1: i = 10
    Thread 2: i = 9
    Thread 1: i = 9
    Thread 2: i = 8
    Thread 1: i = 8
    Thread 2: i = 7
    Thread 1: i = 7
    Thread 2: i = 6
    Thread 1: i = 6
    Thread 2: i = 5
    Thread 1: i = 5
    Thread 2: i = 4
    Thread 1: i = 4
    Thread 2: i = 3
    Thread 1: i = 3
    Thread 2: i = 2
    Thread 1: i = 2
    Thread 2: i = 1
    Thread 1: i = 1

If we use "private int i; AND for(i=10; i > 0; i--) { ... }" in the Countdown class, we get:

    Thread 1: i = 10
    Thread 2: i = 10
    Thread 1: i = 9
    Thread 2: i = 8
    Thread 1: i = 7
    Thread 2: i = 6
    Thread 1: i = 5
    Thread 2: i = 4
    Thread 1: i = 3
    Thread 2: i = 2
    Thread 1: i = 1

So, changing the instance variable we got a completely different result with our multiple threads.
Thread 1 can't access threads 2's thread stack and vice versa, but they can both access the heap. Local variables
are stored in the thread stack. That means that each thread has its own copy of a local variable.
In contrast, the memory required to store an object instance value is allocated on the heap.
In other words, when multiple threads are working with the same object they in fact share the same object.
So, when one thread changes the value of one og the objects instance variables, the other threads will see the new
value from that point forward.

We're passing the same Countdown object to both threads. When we used a local variable for "i" that was ok,
because when the JVM allocated the space for the local variable it used each threads stack. Consequently, each thread
has their own copy of "i" on their respective thread stacks. However, when we switched to using an instance variable
the JVM allocated the space for "i" when it created the Countdown object and it did so at that point on the heap
threads share the heap. So in that case, the two threads don't have their own copy of the variable "i", but in fact
share the variable that's on the heap. So what that means is that when "Thread 1" executes and changes
the value of "i" and then get suspended so that "Thread 2" can execute, "Thread 2" will then see the new value of "i"
as it has been updated from "Thread 1". It won't see the value of "i" that it had when it was last suspended and
that's why each thread is sometimes skipping a number and why numbers can appear out of order.

We have to keep in mind that a for loop is actually several statements. The first time it's executed it assigns
the initial value to the variable. It then checks to see whether the for condition is met. If it is, the code within
the loop is executed, but when we return to the for statement it will then decrement "i" in the case and then
check the condition again. So, basically a for loop consists of several steps and a thread can be suspended between
each step. *** A thread can be suspended between steps ***. So, let's list our potential suspension points here.

In the FOR loop a thread could be suspended:

    1. just before decrementing "i"
    2. just before the condition
    3. or just before printing out the value.

So there are three spots. And actually there are more of them. The print line statement involves concatenation and I/O,
and effectively calls multiple things. There's multiple points where the current thread could be suspended.
So when analyze the output all we can really be sure of is what the value of "i" was when each thread printed is
at that point in time. So both threads thoughts the value of "i" was 10 the first time they executed the print
statement, That means that "Thread 1" was suspended before it could decrement "i" from 10 (reducing it to 9)
because when "Thread 2" printed the value it still had the value of 10. Now when "Thread 1" gets the chance to print
the line again, the value is 9, so from this we can guess that "Thread 1" decremented the value from 10 to 9.
So, how do we know that? Well, if the "Thread 2" had beaten "Thread 1" to it and decremented the value from 10 to 9,
when "Thread 1" had the chance to run again, it would have decremented the value from 9 to 8, so it would never
have printed the value 9 at all, but because it does, we know that "Thread 1" decremented it and by the same
reasoning we know that the "Thread 1" was suspended before it could decrement the value from 9 to 8. "Thread 2" then
decremented the value from 9 to 8 and then prints 8. So the two treads go back and forth like this for a bit with
"Thread 1" decrementing the value, printing it and suspending, and then "Thread 2" decrementing the value, printing it,
and suspending. Now, rather than each thread being able to count down from 10 to 1 that keeps skipping numbers
because of the interference from the other thread. In fact, when problems arise because threads are interleaving
and accessing the same resources the situation has a name its known as "Thread Interference", and we can see why
it's called that.

These two threads keep interfering with each other because whenever they gat a chance to run, the other thread has
changed the value of "i" and as a result each thread can no longer count down from 10 to 1 and hit all the numbers.
We also cam see this situation referred to as a race condition which is the more common term for when two or more
threads are writing a shared resource. It's important to note that there wouldn't be a problem if both threads
are only reading the shared resource, problem will only arise when at least one other thread is writing or updating
the resource.
*/

public class CounterMain {
    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class Countdown {

    //private int i;

    public void doCountdown() {
        String color;

        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }

        for(int i=10; i > 0; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}