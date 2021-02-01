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

/*
   read(ByteBuffer) -  reads bytes beginning at the channel's current position, and after the read,
                       updates the position accordingly.
                       Note that now we're talking about the channel's position, not the byte buffer's position.
                       Of course, the bytes will be placed into the buffer starting at its current position.

   write(ByteBuffer) - the same as read, except it writes. There's one exception.
                       If a datasource is opened in APPEND mode, then the first write will take place starting
                       at the end of the datasource, rather than at position 0. After the write, the position
                       will be updated accordingly.

   position() -        returns the channel's position.

   position(long) -    sets the channel's position to the passed value.

   truncate(long) -    truncates the size of the attached datasource to the passed value.

   size() -            returns the size of the attached datasource
*/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class NioMain {
    public static void main(String[] args) {
        try (FileOutputStream binFile = new FileOutputStream("CJLNIO/data.dat");
            FileChannel binChannel = binFile.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(100);

            // Chained put method
//            byte[] outputBytes = "Hello World!".getBytes();
//            byte[] outputBytes2 = "Nice to meet you".getBytes();
//            buffer.put(outputBytes).putInt(245).putInt(-98765).put(outputBytes2).putInt(1000);
//            buffer.flip();

            // Unchained put method
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);
            long int1Pos = outputBytes.length;
            buffer.putInt(245);
            long int2Pos = int1Pos + Integer.BYTES;
            buffer.putInt(-98765);
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2);
            long int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
            buffer.putInt(1000);
            buffer.flip();

            binChannel.write(buffer);


            RandomAccessFile ra = new RandomAccessFile("CJLNIO/data.dat", "rwd");
            FileChannel channel = ra.getChannel();

            // Reading the file backwards
            ByteBuffer readBuffer = ByteBuffer.allocate(Integer.BYTES);
            channel.position(int3Pos);
            channel.read(readBuffer);
            readBuffer.flip();

            System.out.println("int3 = " + readBuffer.getInt());
            readBuffer.flip();
            channel.position(int2Pos);
            channel.read(readBuffer);
            readBuffer.flip();

            System.out.println("int2 = " + readBuffer.getInt());
            readBuffer.flip();
            channel.position(int1Pos);
            channel.read(readBuffer);
            readBuffer.flip();

            System.out.println("int1 = " + readBuffer.getInt());

            byte[] outputString = "Hello, World!".getBytes();
            long str1Pos = 0;
            long newInt1Pos = outputString.length;
            long newInt2Pos = newInt1Pos + Integer.BYTES;
            byte[] outputString2 = "Nice to meet you".getBytes();
            long str2Pos = newInt2Pos + Integer.BYTES;
            long newInt3Pos = str2Pos + outputString2.length;

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            intBuffer.putInt(245);
            intBuffer.flip();
            binChannel.position(newInt1Pos);
            binChannel.write(intBuffer);

            intBuffer.flip();
            intBuffer.putInt(-98765);
            intBuffer.flip();
            binChannel.position(newInt2Pos);
            binChannel.write(intBuffer);

            intBuffer.flip();
            intBuffer.putInt(1000);
            intBuffer.flip();
            binChannel.position(newInt3Pos);
            binChannel.write(intBuffer);

            binChannel.position(str1Pos);
            binChannel.write(ByteBuffer.wrap(outputString));
            binChannel.position(str2Pos);
            binChannel.write(ByteBuffer.wrap(outputString2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
