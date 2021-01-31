package AdventureGame;

/*
There are CHECKED and UNCHECKED exceptions. We can't ignore Checked Exceptions. IOExceptions is one of them.

BufferedReader reads texts from the input stream and buffers the characters into a character array.
The BufferedReader size is 8k bytes.
The BufferedReader class close() method throws an unchecked exception so we would have had to caught that
in the "finally" block as well, but after the converting the static block content to the "try with resources"
it's
BufferedWriter allows to allocate data in temporary memory during the program work that makes the program
much faster than using a disk memory.
*/

/*
Now, if an object had 20 fields, we'd actually have to call 20 methods. So instead of that, we can use
the ObjectInputStream and ObjectOutputStream classes to read and write objects as a single unit.
Now, the process of translating a data structure or object into a format that can be stored and recreated
is called SERIALIZATION. We use an interface Serializable to make the class serializable.
Now, when we make a class serializable, it's strongly recommended that we declare a long field called
"serialVersionUID". If we don't do so, the compiler will give us a warning. We can suppress the warning but
different compiler implementations can calculate different default values, and that can lead to problems down the road
if we change the compiler we're using. It's also recommended to make the field serialVersionUID private.
If we don't create the serialVersionUID field, a compiler will make it based on class details such as the number
of fields and methods, etc. And if we ever change a class, for example, by adding another field or method,
or change a field type, we'd have to change the serialVersionUID value. When we read an object from a stream,
the runtime checks the stored serialVersionUID against the one contained within the compiled class file.
If they don't match, then there's a compatibility problem and the runtime will throw an InvalidClassException.
Now, if we wanted later versions of an application to be able to load files created by earlier versions,
we'd have to provide custom versions of the writeObject and readObject methods by overriding them.

We don't have to worry about declaring a serialVersionUID field unless:
A: we explicitly use serialization as we do when we use ObjectInputStream and the ObjectOutputStream classes.
B: there may be a compatibility issue down the road.
*/

/*
Now we're going to use the locations.main() method to use random access file to write out the locations.
1. Our first step is going to be to write the number of locations to the file.
   (we have to remain locations data file open while the application runs).
   The first option of the "rao" is the file path. The second option "rwd" indicates that we want to open the file
   for reading and writing and also that we want writes to occur synchronously.
   We can open a file that's read only, we can also not specify that updates to the file have to occur synchronously,
   but if we do that, and modable threads can't access the file, we would actually be responsible for synchronising
   the code ourselves. So in this case, it doesn't matter since only one thread will access the file.
   But generally speaking, it's good practice to have the RandomAccessFile class handle the synchronisation,
   when it does matter.

        rao.writeInt(locations.size());

2. Then we're gonna write the start offset of the locations section.
   And once again, since the file pointer is already positioned correctly, we can just go ahead and use writeInt()

        int indexSize = locations.size() * 3 * Integer.BYTES;
        int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.BYTES);
        rao.writeInt(locationStart);

   Each index record will contain three integers:
        - the location ID,
        - the offset for the location,
        - and the size or length of the location record.

   Now we're calculating the index size, by multiplying the number of locations by the number of integers
   contained in each record, in this case three, by the number of bytes contained in an Integer.
   So once we've done that, on the next line, we then calculate the current position of the file pointer
   to the index size to count for the value that we've already written to the file.
   And we also have to count for the integer, we're about to write to the file, the location section offset,
   we just calculated. So we also have to add the number of bytes to an integer. So we're doing that on the end
   of the line. So this will give us the offset for the location section.
   And over here, we had cast to an int. That's because the file pointer is a Long value. Now we could have
   written a Long, but we're gonna write ints wherever possible. And if we were working with an application
   with lots of data, such that the file offsets could be larger than an integer could hold, then of course,
   we'd have to stick to using longs to make sure we didn't exceed the maximum size of an int.

   So we've written out that location start now. Now the next section in the file is the index.
   But before we can write the index, we have to write the locations. That's because each index record requires
   the offset for the location. And, of course, we don't know the offset until we've written the location.
   Now I guess we could write a location and then write the index record for it. And then write the next location
   and then write the index record for it. And so on, and so forth. But that would actually involve a lot of
   jumping back and forth in the file. Now a disk access is expensive, and it's even more expensive when it's not
   sequential. So, instead, what we're gonna do is write all the locations and then we'll write the index as a whole.
   Now to do that, what we have to do is build the index in memory as we write the locations.

   Now because we wanna jump back to the file when we finished writing the locations, we'll save the current
   position of the file pointer, so that we can jump back to it when we want to write the index, since we'll
   write the index after the two integers we've already written. We'd be writing it to offset eight.
   Which is where the file pointer is currently positioned, after writing the number of locations in the
   location section offset.

        long indexStart = rao.getFilePointer();

   At this point, we're ready to write the locations. Now we've already calculated the offset of the locations
   data sections. And we're gonna assign that to a variable called startPointer, which will update after writing
   each location. And then we'll loop through the locations, write out each location, create an index for it,
   and add an index record to a Map<>().

   Now to do all this, we really need an IndexRecord class.

            int startPointer = locationStart;
            rao.seek(startPointer);
            for() {
               for() {
               .......
               }
            }
*/

/*
So we've set the offset for the first location into a variable called startPointer. Now we need to use this value to
calculate the location's record length after we've written it to the file. We then use the seek() method to move
the file pointer to the first location's offset. We only have to do this for the first location, because after that,
we write all the data sequentially.

Now one drawback to using the RandomAccessFile is that we can't read/write objects. The RandomAccessFile class
doesn't contain readObject and writeObject methods, unfortunately. So it's back to writing each piece of data
individually. We're not using a buffered stream. It wouldn't make sense when accessing a file randomly,
because buffering is just like a queue, what goes in first is read or written first. So, in another words,
it's sequential. So, RandomAccessFile can't be chained with other types of IO classes. Also, unlike the stream
and file classes we've previously used, it can be used for both reading and writing.

           // direction, locationId, direction, locationId
           // N,1,U,2

After we've written the data we create the IndexRecord for it. Now the start byte, for this location, is equal to
the startPointer value and the record length is the current position of the file pointer, which we're getting here
with rao.getFilePointer, but then we're deducting the startPointer, so it's the current position of the file pointer
minus the startPointer. And we're then adding the IndexRecord for the location, using the locationId as key,
and then we update the startPointer for the next location.
*/

/*
And what we wanna do now is change the static initializer, so that it reads the index from the file.

Now, remember that we won't be loading the locations into memory anymore. We load locations on demand.
It means that our locations.main() method will no linger work, because it depends on all the locations being loaded
into memory.

Let's now write the static initializer that uses the RandomAccessFile.

So we open the file and then we read the number of locations and also the offset of the location section.
Then we load the index into memory. To read the index, we're looping through until the file pointer reaches the
location's offset, so we've doing the while loop and we're reading the index and creating the records as we go.
So we're not actually using the number of locations, but it's a good practice to write the number of records
in each file section at the beginning of a file that will be accessed in a random fashion. So we might need this
later on, if we were to modify the application.
*/

import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();
    private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private static RandomAccessFile ra;

    public static void main(String[] args) throws IOException {
        // "rwd" - reading and writing access. the file is also should be synchronized
        try (RandomAccessFile rao = new RandomAccessFile("CJLIOnext/locations_rand.dat", "rwd")) {
            rao.writeInt(locations.size());
            int indexSize = locations.size() * 3 * Integer.BYTES;
            int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.BYTES);
            rao.writeInt(locationStart);

            long indexStart = rao.getFilePointer();

            int startPointer = locationStart;
            rao.seek(startPointer);

            for(Location location : locations.values()) {
                rao.writeInt(location.getLocationID());
                rao.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();
                for(String direction : location.getExits().keySet()) {
                    if(!direction.equalsIgnoreCase("Q")) {
                        builder.append(direction);
                        builder.append(",");
                        builder.append(location.getExits().get(direction));
                        builder.append(",");
                    }
                }
                rao.writeUTF(builder.toString());

                IndexRecord record = new IndexRecord(startPointer, (int) (rao.getFilePointer() - startPointer));
                index.put(location.getLocationID(), record);

                startPointer = (int) rao.getFilePointer();
            }

            rao.seek(indexStart);
            for(Integer locationID : index.keySet()) {
                rao.writeInt(locationID);
                rao.writeInt(index.get(locationID).getStartByte());
                rao.writeInt(index.get(locationID).getLength());
            }
        }
    }

    /*
    * 1. This first four bytes will contain the number of locations (bytes 0-3)
    * 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
    * 3. The next section of the file will contain the index (the index is 1692 bytes long).
    *    It will start at byte 8 and ends at byte 1699.
    * 4. The final section of the file will contain the location records (the data).
    *    It will start at byte 1700
    */

    static {
        try {
            ra = new RandomAccessFile("CJLIOnext/locations_rand.dat", "rwd");
            int numLocations = ra.readInt();
            long locationStartPoint = ra.readInt();

            while(ra.getFilePointer() < locationStartPoint) {
                int locationId = ra.readInt();
                int locationStart = ra.readInt();
                int locationLength = ra.readInt();

                IndexRecord record = new IndexRecord(locationStart, locationLength);
                index.put(locationId, record);
            }
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
       }
    }

    public Location getLocation(int locationId) throws IOException {

        IndexRecord record = index.get(locationId);
        ra.seek(record.getStartByte());
        int id = ra.readInt();
        String description = ra.readUTF();
        String exits = ra.readUTF();
        String[] exitPart = exits.split(",");

        Location location = new Location(locationId, description, null);

        if(locationId != 0) {
            for(int i=0; i<exitPart.length; i++) {
                System.out.println("exitPart = " + exitPart[i]);
                System.out.println("exitPart[+1] = " + exitPart[i+1]);
                String direction = exitPart[i];
                int destination = Integer.parseInt(exitPart[++i]);
                location.addExit(direction, destination);
            }
        }
        return location;
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }

    public void close() throws IOException {
        ra.close();
    }
}
