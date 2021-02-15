package as;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge {
    public static void main(String[] args) {
        // 1. Write the string literal regular expression that will match the following String
        String challenge1 = "I want a bike.";
        System.out.println("1: " + challenge1.matches(".*"));

        // Tim's solution 1
        System.out.println("Tim 1: " + challenge1.matches("I want a bike."));

        // 2. Write a regular expression that will match "I want a bike." and "I want a ball."
        String challenge2 = "I want a ball.";
        System.out.println("2: " + challenge1.matches("(\\w)(.*)"));
        System.out.println("2: " + challenge2.matches("(\\w)(.*)"));

        // Tim's solution 2
        String regex2 = "I want a \\w+.";
        String regex2_1 = "I want a (bike|ball).";
        System.out.println("Tim 2: " + challenge1.matches(regex2));
        System.out.println("Tim 2: " + challenge2.matches(regex2));
        System.out.println("Tim 2: " + challenge1.matches(regex2_1));
        System.out.println("Tim 2: " + challenge2.matches(regex2_1));

        // 3. Use the Matcher.matches() method instead of String.matches()
        Pattern pattern3 = Pattern.compile(regex2);

        Matcher matcher3 = pattern3.matcher(challenge1);
        System.out.println("3: " + matcher3.matches());

        matcher3 = pattern3.matcher(challenge2);
        System.out.println("3: " + matcher3.matches());

        // 4. Replace all occurrences of blank with an underscore for the following string.
        String challenge4 = "Replace all blanks with underscores.";
        System.out.println("4: " + challenge4.replaceAll(" ", "_"));
        System.out.println("4: " + challenge4.replaceAll("\\s", "_"));

        // 5. Write a regular expression that will match the following string in tis entirety.
        String challenge5 = "aaabccccccccdddefffg";
        System.out.println("5: " + challenge5.matches("[a-g]+"));

        // 6. Write a regular expression that will only match the challenge 5 string in its entirety
        System.out.println("6: " + challenge5.matches("^a{3}bc{8}d{3}ef{3}g$"));

        // 7. Write a regular expression that will match a string that starts with a series of letters.
        //    The letters must be followed by a period. After the period, there must be a series of digits.
        //    The string "kjisl.22" would match. The string "f5.12a" would not.
        String challenge7 = "abcd.135";
        System.out.println("7: " + challenge7.matches("^\\D+\\.\\d+"));
        System.out.println("7: " + challenge7.matches("^[A-za-z]+\\.\\d+$"));

        // 8. Modify the regular expression in challenge7 to use a group, so that we can print all the digits
        //    that occur in a string that contains multiple occurrences of the pattern.
        String challenge8 = "abcd.135uvqz.7tzik.999";
        String regex8 = "[A-Za-z]+\\.(\\d+)";
        Pattern pattern8 = Pattern.compile(regex8);
        Matcher matcher8 = pattern8.matcher(challenge8);
        while (matcher8.find()) {
            System.out.println("8: " + matcher8.group(1));
        }

        // 9. Let's suppose we're reading strings that match the patterns we used from a file
        //    Tabs are used to separate the matches, and the last match is followed by a newline.
        //    extract all the numbers
        String challenge9 = "abcd.135\tuvqz.7\ttzik.999\n";
        //String regex9 = "[A-Za-z]+\\.(\\d+)[\\\t|\\\n]";
        String regex9 = "[A-Za-z]+\\.(\\d+)\\s";   // Tim's solution
        Pattern pattern9 = Pattern.compile(regex9);
        Matcher matcher9 = pattern9.matcher(challenge9);
        while (matcher9.find()) {
            System.out.println("9: " + matcher9.group(1));
        }

        // 10. Instead of printing out the numbers themselves, print out their start and end indices.
        //     Use the same string we used for challenge 9. Make those indices inclusive.
        matcher9.reset();
        while(matcher9.find()) {
            System.out.println("9: " + matcher9.start(1) + "-" + (matcher9.end(1)-1));
        }

        // 11. Suppose we have the following string containing points on a graph within curly braces.
        //     Extract what's in the curly braces
        String challenge11 = "{0, 2}, {0, 5}, {1, 3}, {2, 4}";
        Pattern pattern11 = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher11 = pattern11.matcher(challenge11);
        while (matcher11.find()) {
            System.out.println("11: " + matcher11.group(1));
        }
        String challenge11a = "{0, 2}, {0, 5}, {1, 3}, {2, 4}, {x, y}, {2, 4}, {11, 12}";
        Pattern pattern11a = Pattern.compile("\\{(\\d+, \\d+)\\}");
        Matcher matcher11a = pattern11a.matcher(challenge11a);
        while (matcher11a.find()) {
            System.out.println("11a: " + matcher11a.group(1));
        }

        // 12. Write a regular expression that will match a 5-digit US zip code. Use "11111" as a test string.
        String challenge12 = "11111";
        System.out.println("12: " + challenge12.matches("^\\d{5}$"));

        // 13. Write a regular expression that will match zip codes like this: "11111-1111"
        String challenge13 = "11111-1111";
        System.out.println("13: " + challenge13.matches("^\\d{5}-\\d{4}$"));

        // 14. Write a regular expression that will match zip codes like in challenge13 and also like in challenge12
        System.out.println("14: " + challenge12.matches("^\\d{5}(-\\d{4})?$"));
        System.out.println("14: " + challenge13.matches("^\\d{5}(-\\d{4})?$"));
    }
}
