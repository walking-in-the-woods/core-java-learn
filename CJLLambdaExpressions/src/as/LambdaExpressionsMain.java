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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaExpressionsMain {
    public static void main(String[] args) {
        // named class method run
//        new Thread(new CodeToRun()).start();

        // an anonymous class method run
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Anonymous Class. Printing from the runnable");
//            }
//        }).start();

        // lambda expression run
//        new Thread(()-> System.out.println("Lambda Expression. Printing from the runnable")).start();

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

        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee employee1, Employee employee2) {
                return employee1.getName().compareTo(employee2.getName());
            }
        });

        for(Employee employee : employees) {
            System.out.println(employee.getName());
        }
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

//class CodeToRun implements Runnable {
//    @Override
//    public void run() {
//        System.out.println("Named Class. Printing from the Runnable");
//    }
//}
