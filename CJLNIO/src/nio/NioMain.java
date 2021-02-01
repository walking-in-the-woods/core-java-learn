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

/*
Doing I/O using java.nio means that data is processed in blocks rather than one byte or character at a time.
To accomplish this, java.nio uses Channels and Buffers and also sometimes Selectors. In terms of definitions,
a Channel is the data source we're reading from or writing to. That can be a file or socket or any other
DataSource. To use a DataSource as a channel, we need a class that implements the java.nio.Channel interface
and can connect to the DataSource. A Buffer on the other hand, is the container for the block of data that we
want to read or write. Buffers are typed and that means that they can only hold one type of data.
We can actually specify the size of the buffer as well. And finally, Selectors allow a single thread to manage
the I/O for multiple channels.
*/

/*
When using channels, we only need one channel for both reading and writing. The FileChannel is an exception
to the rule. If we create a FileChannel from a FileInputStream, it's actually only open for reading.
If we create a file of a FileOutputStream, it's only open for writing. And for RandomAccessFile, it would depend on
a parameter we passed to the RandomAccessFile constructor ("r", "rw" .. ).
*/

// When something goes wrong with nio, flip.

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.util.List;

public class NioMain {
    public static void main(String[] args) {
        try (FileOutputStream binFile = new FileOutputStream("CJLNIO/data.dat");
            FileChannel binChannel = binFile.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(100);
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);
            buffer.putInt(245);
            buffer.putInt(-98765);
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2);
            buffer.putInt(1000);
            buffer.flip();
            binChannel.write(buffer);

//            //ByteBuffer buffer = ByteBuffer.wrap(outputBytes);
//            ByteBuffer buffer = ByteBuffer.allocate(outputBytes.length);
//            buffer.put(outputBytes);
//
//            buffer.flip();
//            int numBytes = binChannel.write(buffer);
//            System.out.println("numBytes written was: " + numBytes);
//
//            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
//            intBuffer.putInt(245);
//            intBuffer.flip(); // to reset the position to zero
//            numBytes = binChannel.write(intBuffer);
//            System.out.println("numBytes written was: " + numBytes);
//
//            intBuffer.flip();
//            intBuffer.putInt(-98765);
//            intBuffer.flip();
//            numBytes = binChannel.write(intBuffer);
//            System.out.println("numBytes written was: " + numBytes);
//
//            RandomAccessFile ra = new RandomAccessFile("CJLNIO/data.dat", "rwd");
//            FileChannel channel = ra.getChannel();
//            outputBytes[0] = 'a';
//            outputBytes[1] = 'b';
//            buffer.flip(); // if we comment this flip(), we'll get "abllo World!" in output
//            long numBytesRead = channel.read(buffer);
//            if(buffer.hasArray()) {
//                System.out.println("byte buffer = " + new String(buffer.array()));
////                System.out.println("byte buffer = " + new String(outputBytes));
//            }
//
//            // Absolute read
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));
//            intBuffer.flip();
//            numBytesRead = channel.read(intBuffer);
//            intBuffer.flip(); // if we put this flip() here, next we do the relative read
//            System.out.println(intBuffer.getInt(0));
//            System.out.println(intBuffer.getInt());
//
//            // Relative read
////            intBuffer.flip();
////            numBytesRead = channel.read(intBuffer);
////            intBuffer.flip();
////            System.out.println(intBuffer.getInt());
////            intBuffer.flip();
////            numBytesRead = channel.read(intBuffer);
////            intBuffer.flip();
////            System.out.println(intBuffer.getInt());
//
//            channel.close();
//            ra.close();

//            System.out.println("outputBytes = " + new String(outputBytes));

//            RandomAccessFile ra = new RandomAccessFile("CJLNIO/data.dat", "rwd");
//            byte[] b = new byte[outputBytes.length];
//            ra.read(b);
//            System.out.println(new String(b));
//
//            long int1 = ra.readInt();
//            long int2 = ra.readInt();
//            System.out.println(int1);
//            System.out.println(int2);

//            FileInputStream file = new FileInputStream("CJLNIO/data.txt");
//            FileChannel channel = file.getChannel();

//            Path dataPath = FileSystems.getDefault().getPath("CJLNIO/data.txt");
//            Files.write(dataPath, "\nLine 4".getBytes("UTF-8"), StandardOpenOption.APPEND);
//            List<String> lines = Files.readAllLines(dataPath);
//            for(String line : lines) {
//                System.out.println(line);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
