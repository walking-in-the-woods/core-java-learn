package streams;

/*
A Stream<T> is a sequence of elements supporting sequential and parallel aggregate operations.
"::" - a method reference. We can use it when all a lambda doesn't call an existing method.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamsMain {
    public static void main(String[] args) {
        List<String> someBingoNumbers = Arrays.asList(
                "N40", "N36",
                "B12", "B6",
                "G53", "G49", "G60", "G50", "g64",
                "I26", "I17", "I29",
                "O71");

        List<String> gNumbers = new ArrayList<>();

        // this code does the same stuff that the one statement below does
//        someBingoNumbers.forEach(number -> {
//            if(number.toUpperCase().startsWith("G")) {
//                gNumbers.add(number);
////                System.out.println(number);
//            }
//        });
//
//        gNumbers.sort((String s1, String s2) -> s1.compareTo(s2));
//        gNumbers.forEach((String s) -> System.out.println(s));

        // this one statement does the same stuff that the commented code above
        someBingoNumbers.stream()
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("G"))
                .sorted()
                .forEach(System.out::println);
    }
}
