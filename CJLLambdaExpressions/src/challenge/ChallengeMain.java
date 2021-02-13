package challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ChallengeMain {

    public static void main(String[] args) {

        // 1. Convert an anonymous class to a lambda expression
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String myString = "Let's split this up into an array";
                String[] parts = myString.split(" ");
                for (String part : parts) {
                    System.out.println(part);
                }
            }
        };

        Runnable runnable1 = () -> {
            String myString = "Let's split this up into an array";
            String[] parts = myString.split(" ");
            for (String part : parts) {
                System.out.println(part);
            }
        };

        // 2. Solution
        // 3. Write the code that will execute the function with an argument of "1234567890"
        // 4. Instead of executing this function directly, pass it to a method called everySecondCharacter()
        //    that accepts the function as a parameter.
        Function<String, String> lambdaFunction = s -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i % 2 == 1) {
                    returnVal.append(s.charAt(i));
                }
            }
            return returnVal.toString();
        };

        // 3. Solution
        System.out.println(lambdaFunction.apply("1234567890"));

        // 5. Call the method with the lambdaFunction and the string "1234567890". Print result
        String result = everySecondCharacter(lambdaFunction, "1234567890");
        System.out.println(result);

        // 6. Write a lambda expression that maps to the java.util.Supplier interface.
        //    This lambda should return the string "I love Java!" Assign it to a variable called iLoveJava.
        //Supplier<String> iLoveJava = () -> "I Love Java!";
        Supplier<String> iLoveJava = () -> {return "I Love Java!"; };

        // 7. Use the supplier iLoveJava to a variable called supplierResult, then print the variable to the console
        String supplierResult = iLoveJava.get();
        System.out.println(supplierResult);

        // 9. (8 was only about theory) Print the items in the list in sorted order, and with the first letter
        //    in each name udder-cased.
        List<String> topNames2015 = Arrays.asList(
              "Amelia",
              "Olivia",
              "emily",
              "Isla",
              "Ava",
              "Oliver",
              "Jack",
              "Charlie",
              "harry",
              "Jacob"
        );

//        List<String> firstUpperCasedList = new ArrayList<>();
//        topNames2015.forEach(name ->
//                firstUpperCasedList.add(name.substring(0,1).toUpperCase() + name.substring(1)));
//        firstUpperCasedList.sort((s1, s2) -> s1.compareTo(s2));
//        firstUpperCasedList.forEach(s -> System.out.println(s));

        // 10. Change the code so that it uses method references. (like Class::MethodName)
//        List<String> firstUpperCasedList = new ArrayList<>();
//        topNames2015.forEach(name ->
//                firstUpperCasedList.add(name.substring(0, 1).toUpperCase() + name.substring(1)));
//        firstUpperCasedList.sort(String::compareTo);
//        firstUpperCasedList.forEach(System.out::println);

        // 11. Uppercase first letter, then sort and print the list using a stream and chain of stream operations
        topNames2015
                .stream()
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .sorted(String::compareTo)
                .forEach(System.out::println);

        // 12. Instead of printing out the sorted names, print out how many names begin with the letter 'A' instead
        long namesBeginningWithA = topNames2015
                .stream()
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .filter(name -> name.startsWith("A"))
                .count();
        System.out.println("Number of names beginning with A is: " + namesBeginningWithA);

        // 13. Use peek() to print out the names after the map() method has executed.
        //     (Base on the previous version of the code). What will be printed to the console?
        //     Solution: nothing will be printed. there is no terminal operation
        // 14. Add a terminal operation to this chain.
        topNames2015
                .stream()
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .peek(System.out::println)
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    // 2. Write the following method as lambda expression. Don't worry about assigning it to anything
    public static String everySecondChar(String source) {
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (i % 2 == 1) {
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    }

    // 4. Solution
    public static String everySecondCharacter(Function<String, String> func, String source) {
        return func.apply(source);
    }
}
    
