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

//        Collections.sort(employees, (employee1, employee2)->
//                employee1.getName().compareTo(employee2.getName()));
//
//        for(Employee employee : employees) {
//            System.out.println(employee.getName());
//        }
//
//        UpperConcat uc = (s1, s2) -> {
//            String result = s1.toUpperCase() + " " + s2.toUpperCase();
//            return result;
//        };
//        String sillyString = doStringStuff(uc, employees.get(0).getName(), employees.get(1).getName());
//        System.out.println(sillyString);

        AnotherClass anotherClass = new AnotherClass();
        String s = anotherClass.doSomething();
        System.out.println(s);
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

        // output for the lambda expression class name is the same like for the AnotherClass, so actually
        // the lambda expression is a nested block of code (like this: { ... })
//        UpperConcat uc = (s1, s2)-> {
//            System.out.println("The lambda expression's class is: " + getClass().getSimpleName());
//            String result = s1.toUpperCase() + s2.toUpperCase();
//            return result;
//        };

//        System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
//        return LambdaExpressionsMain.doStringStuff(uc,"String1", "String2");

        int i = 0;

        UpperConcat uc = (s1, s2)-> {
            System.out.println("The lambda expression's class is " + getClass().getSimpleName());
            String result = s1.toUpperCase() + " " + s2.toUpperCase();
            return result;
        };

        System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
        return LambdaExpressionsMain.doStringStuff(uc, "String1", "String2");

//        {
//            UpperConcat uc = new UpperConcat() {
//                @Override
//                public String upperAndConcat(String s1, String s2) {
//                    System.out.println("i (within anonymous class) = " + i);
//                    return s1.toUpperCase() + " " + s2.toUpperCase();
//                }
//            };
//
//            System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
//            //i++;
//            System.out.println("i = " + i);
//            return LambdaExpressionsMain.doStringStuff(uc,"String1", "String2");
//        }

        // output for the anonymous class name is empty
//        System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
//        return LambdaExpressionsMain.doStringStuff(new UpperConcat() {
//            @Override
//            public String upperAndConcat(String s1, String s2) {
//                System.out.println("The anonymous class's name is: " + getClass().getSimpleName());
//                return s1.toUpperCase() + " " + s2.toUpperCase();
//            }
//        }, "String1", "String2");
    }
}
