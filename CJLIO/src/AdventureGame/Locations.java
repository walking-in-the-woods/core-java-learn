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

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        try (BufferedWriter locFile = new BufferedWriter(new FileWriter("CJLIO/locations.txt"));
            BufferedWriter dirFIle = new BufferedWriter(new FileWriter("CJLIO/directions.txt"))) {
            for(Location location : locations.values()) {
                locFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
                for(String direction : location.getExits().keySet()) {
                    if(!direction.equalsIgnoreCase("Q")) {
                        dirFIle.write(location.getLocationID() + "," +
                                direction + "," + location.getExits().get(direction) + "\n");
                    }
                }
            }
        }
    }

    static {

        try (Scanner scanner = new Scanner(new BufferedReader(
                new FileReader("CJLIO/locations_big.txt")))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                int loc = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.nextLine();
                System.out.println("Imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Now read the exits
        try (BufferedReader dirFile = new BufferedReader(new FileReader("CJLIO/directions_big.txt"))) {
            String input;
            while ((input = dirFile.readLine()) != null) {
                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);

                System.out.println(loc + ": " + direction + ": " + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
