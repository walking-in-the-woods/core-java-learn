package func;

/*
We've got our last names working OK then if this was something we wanted to frequently do, we would go probably added
a getLasName() method to the Employee class, but we'll pretend that we don't want to do it for some reason.
However, let's pretend that there are several places in our application we need to get the last name of a single
employee. We want to use a lambda expression that maps to one of the java.util.function interfaces.
Now, non of the interfaces we looked at so far matches what we want to do. Consumer doesn't return a value,
Supplier doesn't accept any parameters, and Predicate only return true or false. So we want to actually pass in a
String and we then expect a String to be returned. And this sounds like a job for the function interface.
Now the interface represents a function that takes one parameter and returns a value. The functional method that one
that we use with lambdas is the apply() method.
*/

/*
An example of using functions:

Old code:

    public interface ResizeImage {
        public Image resizeImage(Image image);
    }

    public List<Image> runWhenDone() {

        // pretend we have access to an ImageResizer instance
        // (probably provided when a Runnable object was constructed),
        // and to a list called images that has all the images fetched from the Internet

        for (Image image : images) {
            resizedImages.add(resizer.resizeImage(image));
        }
    }

New code:

    Function<Image, Image> resizer1 = (Image image) -> { resize using algorithm 1 }
    Function<Image, Image> resizer2 = (Image image) -> { resize using algorithm 2 }

    public List<Image> runWhenDone(Function<Image, Image> resizer) {
        for (Image image : images) {
            resizedImages.add(resizer.apply(image));
        }
    }
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class FuncMain {
    public static void main(String[] args) {

        Employee john = new Employee("John Doe", 30);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee jack = new Employee("Jack Hill", 40);
        Employee snow = new Employee("Snow White", 22);
        Employee red = new Employee("Red RidingHood", 35);
        Employee charming = new Employee("Prince Charming", 31);

        List<Employee> employees = new ArrayList<>();

        employees.add(john);
        employees.add(tim);
        employees.add(jack);
        employees.add(snow);
        employees.add(red);
        employees.add(charming);

        Function<Employee, String> getLastName = (Employee employee) -> {
            return employee.getName().substring(employee.getName().indexOf(' ') + 1);
        };

//        String lastName = getLastName.apply(employees.get(5));
//        System.out.println(lastName);

        Function<Employee, String> getFirstName = (Employee employee) -> {
            return employee.getName().substring(0, employee.getName().indexOf(' '));
        };

        Random random1 = new Random();
        for(Employee employee : employees) {
            if(random1.nextBoolean()) {
                System.out.println(getAName(getFirstName, employee));
            } else {
                System.out.println(getAName(getLastName, employee));
            }
        }
    }

    private static String getAName(Function<Employee, String> getName, Employee employee) {
       return  getName.apply(employee);
    }

    private static void printEmployeesByAge(List<Employee> employees,
                                            String ageText, Predicate<Employee> ageCondition) {
        System.out.println("\n" + ageText + "\n");
        for(Employee employee : employees) {
            if(ageCondition.test(employee)) {
                System.out.println(employee.getName());
            }
        }
    }
}