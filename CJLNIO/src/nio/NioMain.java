package nio;

/*
java.nio was added in Java 1.4 as an improvement to java.io because the classes in the
package perform I/O in a non-blocking manner. java.nio was also meant to fix some of the
problems developers could run into when using the java.io classes to work with file system.

The java.nio classes fall into one of two buckets: those that deal with the file system,
and those that deal with reading and writing data.
 */

/*
When using classes in the java.io package, a thread will block while it's waiting to read
or write to a stream or buffer.

However, threads using the java.nio classes will not block. They are free to continue
executing, so java.nio was introduced as a performance improvement.

However, many developers have argued that the java.nio package was a step backwards.
Some have shown that blocking I/O is often faster than non-blocking I/O.
 */

/*
java.io classes work with streams (character and binary). Data is read one byte or character at a time
and sometimes buffered, depending on which classes are used.
When using java.nio, we deal with data in blocks, and rather than processing one byte or character at a time,
one block will be processed at a time.

We can use java.nio methods to create a java.io stream.
 */

public class NioMain {
    public static void main(String[] args) {

    }
}
