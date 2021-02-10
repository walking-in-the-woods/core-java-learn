package moredeadlocks;

/*
Let's take a look at another way that a deadlock can occur. Let's suppose we have two classes that contain
synchronized methods, and each class calls a method in the other class.

We'll recall that when a thread is running an object's synchronized method, no other thread can run a synchronized
method using the same object until the first thread exit the method it's running. Let's assume that we use the above
two classes in the following way:

        //Construct the objects
        Data data = new Data();
        Display display = new Display();
        data.setDisplay(display);
        display.setData(data);

If one thread (Thread1) calls Data.updateData() while another tread (Thread2) calls Display.updateDisplay(),
the following could happen depending on timing:

       1. Thread1 enters data.updateData() and writes to the console, then suspends
       2. Thread2 enters display.updateDisplay() and writes to the console, then suspends
       3. Thread1 runs and tries to call display.dataChanged(), but Thread2 is still running display.updateDisplay(),
          so it's holding the lock on the Display object. Thread1 blocks.
       4. Thread2 wakes up and tries to run data.getData(), but Thread1 is still running data.updateData(),
          so Thread2 blocks.

We have a deadlock. The underlying cause of the deadlock is actually the same as the first example.
Two threads are trying to get the same two locks in a different order. Thread1 wants to get the Data lock and then
wants the Display lock.
Thread2 gets the Display lock. We would avoid this problem in the same way we did for the first example.
We'd have to rewrite the code so that the two threads try to obtain the locks in the same order, which in case,
would mean not calling each other in this circular fashion.
We could also see if it's possible to pass information to other objects using parameters, rather than having them
call methods to get the information. (In a real world application, we wouldn't mix UI and model code like this)
*/

/*
Thread1 acquires the lock on the jane object and enters the sayHello() method. It prints to the console, then suspends.
Thread1 acquires the lock on the john object and enters the sayHello() method. It prints to the console, then suspends.
Thread1 runs again and wants to say hello back to john object.
It tries to call the sayHelloBack() method using the john object that was passed into the sayHello(),
but Thread2 is holding the john lock, so Thread1 suspends.
Thread2 runs again and wants to say hello back to jane object.
It tries to call the sayHelloBack() method using the jane object that was passed into the sayHello(),
but Thread1 is holding the jane lock, so Thread2 suspends.
*/

public class SayHelloMain {
    public static void main(String[] args) {
        final PolitePerson jane = new PolitePerson("Jane");
        final PolitePerson john = new PolitePerson("John");

        new Thread(new Runnable() {
            @Override
            public void run() {
                jane.sayHello(john);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                john.sayHello(jane);
            }
        }).start();

    }

    static class PolitePerson {
        private final String name;

        public PolitePerson(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello to me!\n", this.name, person.getName());
            person.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello back to me!\n", this.name, person.getName());
        }
    }
}
