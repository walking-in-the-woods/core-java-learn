package streams;

/*
A Stream<T> is a sequence of elements supporting sequential and parallel aggregate operations.
"::" - a method reference. We can use it when all a lambda doesn't call an existing method.
The map() method wants a function (not a BiFunction), and of course we don't want to have to pass a method reference,
and we can also pass a lambda that matches the function pattern accept one argument returns one value.
In this case it juct happens to be a method in the string class that does what we want to do. Obviously, the method
that we use has to work with the item types in the stream. Now we couldn't use a String method if the items in the
stream weren't strings for example. So the map() method ultimately returns a stream. The stream will contain all the
uppercased bingo numbers. In other words, it will contain all the results of calling the toUppercase() method on the
input stream. Now, it's called "map" by the way because it's essentially mapping each item in the input stream to the
results returned by the function argument. Now the resulting stream will have the same number of items as the source
stream. In this case, the source stream is the result of the someBingoNumbers.stream() call. When we chained together
functions, and a function in the chain would operate on the result from the last function in the chain, essentially
this is what's happening here. Each stream operation operates on the stream result from the last step.
Now, so far we've created a stream from our collection and passed it to the next operation in the chain, a map
in this case, and now we have a stream containing upper-cased bingo numbers. Next, we want to filter the stream,
so that we end up with a string that contains only those numbers that start with "G". So, we're going to use the
filter method to accomplish this. Now, the filter method wants a predicate, not a function, so in this case we're
passing a lambda expression that takes one parameter and returns a true/false value. The resulting stream will then
contain only those items for which the predicate return true. So at the end of this operation, will have a stream
that contains ultimately the 5 "G" numbers. So, in the next step we sort the items by calling the sorted() method.
This method sorts based on the natural ordering of the items in the stream. Now there's also a version of the sorted
method that accepts the comparator parameter, but we don't need to provide one in this case.
The natural ordering would suffice. (But again, the option is there if we choose to do that.)
Then finally, we're printing out the results using the forEach() method. But, important note here, this forEach()
method isn't the same one that we previously used with the Iterable interface. In this case we're using the
forEach() method from the Stream class, and in fact, all the steps have used methods from the Stream class,
which should make sense, and that's because the object type return from the stream() method is a Stream, and
therefore from that point forward, every method we call operates on a stream, namely the stream returned from the
previous step. So the stream().forEach() method does what the Iterator one does. Essentially it accepts a Consumer
as a parameter, and evaluate the consumer for each item in the stream. Now since the System.out.println() method
accepts an argument and doesn't return a value, we can map that to Consumer. We can then pass it to the forEach()
method and we've done so using a method references.

        list.forReach(System.out::println);
        (String s) -> System.out.println(s); // this is the longer version of "(System.out::println)"
        Class::Method

forEach() method at the end of the chain is called a "Terminal Operation". So a terminal operation returns
either void or a non-stream result. Now since every operation in a chain requires a source stream, ultimately the chain
has to end when we use a terminal operation. Now operations that return a stream are called "Intermediate Operations"
because they don't force the end of the chain.
*/

/*
    Input:                                  Method/Operation:               Output:

    The ArrayList someBingoNumbers          stream()                        A stream containing all the items in the
                                                                            someBingoNumbers list

    Stream containing all bingo numbers     map(String::toUpperCase)        A stream containing all the bingo numbers
                                                                            upper-cased

    Upper-cased stream                       filter(s->s.startsWith("G"))    A stream containing all the "G" items
                                                                            ("G53", "G49", "G60", "G50", "G64")

    "G" items stream                        sorted()                        A stream containing the sorted items
                                                                            ("G49", "G50", "G53", "G60", "G64")

    Sorted "G" items stream                 forEach(System.out::println)    Each "G" item is printed to the console
                                                                            Void result. The chain ends.

When a chain is evaluated, a stream pipeline is created. The stream pipeline consists of a source, zero or more
intermediate operations, and a terminal operation. In our example, we used a collection as the source, but it could
also be an array or an I/O channel, and we can build streams from scratch.

The items in the source enter the pipeline, and the chain result emerges at the other end of the pipe. As we've seen,
elements may be removed from the stream as a result of an operation, so the set of elements that comes out at the
other end ot the pipe doesn't have to match the number that entered the pipe.

We noticed that when we weren't using streams, our "g64" number was printed with a lower-cased "g". But when we used
streams, it was printed with an upper-cased "G". In the non-stream case, we didn't use the result of the
toUpperCase() call. When an item passed the test, we assigned the original string with the lower-cased "g" to the
new list.

In the stream case, the map() method maps each source string to the Function result, therefore the upper-cased string
is added to the resulting stream and passed to the next step in the chain. That's why the non-stream case prints
a lower-cased "g", and the stream case prints an upper-cased "G".
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

        // Let's create a stream from scratch
        Stream<String> ioNumberStream = Stream.of("I26", "I17", "I29", "O71");
        Stream<String> inNumberStream = Stream.of("N40", "N36", "I26", "I17", "I29", "O71");

        // concat() method is static, we can't use it in a chain, but we can use it as a source for a chain
        Stream<String> concatStream = Stream.concat(ioNumberStream, inNumberStream);
        System.out.println("----------------------------");
        // we use distinct() method to remove duplicates
        System.out.println(concatStream
                .distinct()
                .peek(System.out::println)
                .count());
    }
}
