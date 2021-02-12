package as;

/*
Every lambda expression got three parts:

    1. Arguments list
    2. Arrow token
    3. Body

When a compiler sees a lambda expression, how does it know what to do? Well, it knows that one of the thread classes
constructors accepts a runnable parameter, and in addition it also knows that the runnable interface only has one
method, in this case run() which doesn't take any parameters. So, it's able to match the lambda expressions argument
list (no parameters) with the run method. Because the compiler needs to match the lambda expression to a method,
lambda expressions can only be used with interfaces that contain only one method that has to be implemented,
so these interfaces are also referred to as functional interfaces. Now, by using a lambda expression instead of
creating a class that implements Runnable or using an anonymous class actually able to reduce the lines of code
we have to write. And allows us more to focus on what we care about this case the code we want to run.
*/

import java.util.*;

public class LambdaExpressionsMain {
    public static void main(String[] args) {

        new Thread(()-> {
            System.out.println("Lambda Expression. Printing from the runnable");
            System.out.println("Line 2");
            System.out.format("This is line %d\n", 3);
        }).start();

        Employee john = new Employee("John Doe", 30);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee jack = new Employee("Jack Hill", 40);
        Employee snow = new Employee("Snow White", 22);

        List<Employee> employees = new ArrayList<>();

        employees.add(john);
        employees.add(tim);
        employees.add(jack);
        employees.add(snow);

        AnotherClass anotherClass = new AnotherClass();
        String s = anotherClass.doSomething();
        System.out.println(s);

        employees.forEach(employee -> {
            System.out.println(employee.getName());
            System.out.println(employee.getAge());
        });

//        for(Employee employee : employees) {
//            System.out.println(employee.getName());
//            System.out.println(employee.getAge());
//        }

//        System.out.println("------------------------");
//        for(int i = 0; i < employees.size(); i++) {
//            Employee employee = employees.get(i);
//            System.out.println(employee.getName());
//            new Thread(() -> System.out.println(employee.getAge())).start();
//        }
    }

    public final static String doStringStuff(UpperConcat uc, String s1, String s2) {
        return uc.upperAndConcat(s1, s2);
    }
}

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

interface UpperConcat {
    public String upperAndConcat(String s1, String s2);
}

class AnotherClass {
    public String doSomething() {

        int i = 0;
        // i++; // Exception: local variables referenced from a lambda expression must be final or effectively final

        UpperConcat uc = (s1, s2)-> {
            System.out.println("The lambda expression's class is " + getClass().getSimpleName());
            System.out.println("i in the lambda expression = " + i);
            String result = s1.toUpperCase() + " " + s2.toUpperCase();
            return result;
        };

        System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
        return LambdaExpressionsMain.doStringStuff(uc, "String1", "String2");
    }

    public void printValue() {

        int number = 25;

        Runnable r = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The value is " + number);
        };

        new Thread(r).start();
    }
}
