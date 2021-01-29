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

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream("CJLIO/locations.dat")))) {
            for(Location location : locations.values()) {
                locFile.writeObject(location);
            }
        }
    }

    static {

        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream("CJLIO/locations.dat")))) {
            // A variable to exit the while loop below without throwing an exception
            boolean eof = false;
            while (!eof) {
                try {
                    Location location = (Location) locFile.readObject();
                    System.out.println("Read location " + location.getDescription() +
                            " : " + location.getDescription());
                    System.out.println("Found " + location.getExits().size() + " exits");

                    locations.put(location.getLocationID(), location);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
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
}
