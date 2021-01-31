package AdventureGame;

/*
File Pointer.

A file pointer is an offset in the file where the next read or write will start from.
For example, if a file pointer is set to 100, then the next time we call a read or write method, the read or write
will start at byte position 100 in the file.

Now if we were reading an int, the byte set position 100 to 103 would be read, since an int is 4 bytes.
The byte position is zero-based. So the first byte in the file is at position 0.
Whenever we read or write the file, the file pointer is advanced by the number of bytes we read or wrote.
So for example, if the file pointer is equal to 100, and we read five bytes, then the file pointer will be
equal to 105 after the read/write has taken place.

Now also we use the term Offset when randomly accessing files. So an offset is a byte location in the file.
So for example, if the value has an offset of 100, that would mean that the value is located ot byte 100 in the file,
so we would want the file pointer to be set to 100 when we read/write the value. The value could occupy more than
one byte. But the offset is whether the value's first byte is located. The remaining bytes would follow that first
byte sequentially.

Now when using random access files, we refer to each set of related data as a record.
In our application, the locationID, description, and exits, make up the record for a location.
When the user moves to a location, how we know which bytes to be read from the file? Well, if all of the locations
occupy the same links, and we wrote them out in order, the location one followed by location two, etc.,
well that would be easy. We could figure out a location's offset from its ID and the fixed location length.
For example, let say every location occupied 30 bytes of our file. So the first location's offset would be zero,
and its data would occupy bytes 0 through 29, and the second location would begin at byte zero, and end at byte 59, etc.
Then when a played moved to location end, we'd figure out which bytes to read using a formula, assuming
locations IDs were one based, so the formula would be something like:

                 startByte = (n - 1) * 30

So that would be great if we knew that each location was of a fixed length. But unfortunately, our records
don't have the same length. The length for each location is different, and that;s because their description length
and the number of exits differ from location to location. So because of that, we have to include an index in our file.
And that index stores the offset and record length for each location. We also have to include the location ID
in the index. Now, given that, reading a location's going to be a two step process.

Firstly, we're gonna get the index record for the location, and secondly, we're gonna use the index values
and read the location data.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdventureGameMain {

    private static Locations locations = new Locations();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> vocabulary = new HashMap<String, String>();
        vocabulary.put("QUIT", "Q");
        vocabulary.put("NORTH", "N");
        vocabulary.put("SOUTH", "S");
        vocabulary.put("WEST", "W");
        vocabulary.put("EAST", "E");

        Location currentLocation = locations.getLocation(1);
        while(true) {
            System.out.println(currentLocation.getDescription());

            if(currentLocation.getLocationID() == 0) {
                break;
            }

            Map<String, Integer> exits = currentLocation.getExits();
            System.out.print("Available exits are: ");
            for(String exit: exits.keySet()) {
                System.out.print(exit + ", ");
            }
            System.out.println();

            String direction = scanner.nextLine().toUpperCase();
            if(direction.length() > 1) {
                String[] words = direction.split(" ");
                for(String word : words) {
                    if(vocabulary.containsKey(word)) {
                        direction = vocabulary.get(word);
                        break;
                    }
                }
            }

            if(exits.containsKey(direction)) {
                currentLocation = locations.getLocation(currentLocation.getExits().get(direction));
            } else {
                System.out.println("You cannot go in that direction");
            }
        }

        locations.close();
    }
}
