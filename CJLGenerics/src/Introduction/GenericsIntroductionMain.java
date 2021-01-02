package Introduction;

import java.util.ArrayList;

public class GenericsIntroductionMain {
    public static void main(String[] args) {
        // ArrayList items = new ArrayList(); // Raw type (not recommended to use)
        ArrayList<Integer> items = new ArrayList<>(); // Parameterized type (<> - diamond)
        items.add(1);
        items.add(2);
        items.add(3);
        // items.add("tim"); // Highlighted as an error with parameterized type only
        items.add(4);
        items.add(5);

        printDoubled(items);
    }

    private static void printDoubled(ArrayList<Integer> n) {
        for(int i : n) {
            System.out.println(i * 2);
        }
    }
}
