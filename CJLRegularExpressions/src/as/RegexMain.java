package as;

/*
The simplest form if a regular expression is a "String Literal". So for example, "Hello" is a regular expression.

Character classes
Character classes like a wild card classes, they represent a set or class of character classes like a wild card

Boundary matches
A boundary matcher looks for boundaries such as the beginning, end of a string, or a word

Quantifiers
*/

/*
Boundaries matchers:

    ^ - the beginning of a pattern (but if it's in the [], [^...] is a character class, it's not a boundary matcher)
    $ - the end of a pattern

    \\d - all digits
    \\D - all non-digits
    \\s - all whitespaces
    \\S - all non-whitespaces
    \\w - all letters and digits
    \\b - all boundaries around the words

Quantifiers:

    {} - quantities of a symbol (like "a{3}" that matches "aaa")
    +  - one or more symbol (like "e+" that matches one or more "e")
    .   - every character
    *   - zero or more character (greedy quantifier)
    ?   - once or not at all
*/

/*
IntellyJ IDEA hint: press Alt + Enter on a regex and choose Check RegExp to test it
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMain {
    public static void main(String[] args) {
        String string = "I am a string. Yes, I am.";
        System.out.println(string);

        String yourString = string.replaceAll("I", "You");
        System.out.println(yourString);

        String alphanumeric = "abcDeeeF12Ghhiiiijkl99z";

        // output: YYYYYYYYYYYYYYYYYYYYYYY
        System.out.println(alphanumeric.replaceAll(".", "Y"));
        // output: YYYF12Ghhiiiijkl99z      ^ - the carrot boundary matcher
        System.out.println(alphanumeric.replaceAll("^abcDeee", "YYY"));

        String secondString = "abcDeeeF12GhhabcDeeeiiiijkl99z";

        // output: YYYF12GhhabcDeeeiiiijkl99z       only the first match is replaced
        System.out.println(secondString.replaceAll("^abcDeee", "YYY"));

        // output: false
        System.out.println(alphanumeric.matches("^hello"));
        // output: false
        System.out.println(alphanumeric.matches("^abcDeee"));
        // output: true
        System.out.println(alphanumeric.matches("abcDeeeF12Ghhiiiijkl99z"));

        // output: abcDeeeF12GhhiiiiTHE END
        System.out.println(alphanumeric.replaceAll("jkl99z$", "THE END"));

        // output: XbcDXXXF12GhhXXXXjkl99z      each letter from [aei] set is replaced by X
        System.out.println(alphanumeric.replaceAll("[aei]", "X"));
        // output: abcDeeX12GhhiiiXkl99z
        // if letters of the set [aei] are actually followed by letters form the set [Fj]
        System.out.println(alphanumeric.replaceAll("[aei][Fj]", "X"));

        // output: Harry
        System.out.println("Harry".replaceAll("[Hh]arry", "Harry"));

        String newAlphanumeric = "abcDeeeF12Ghhiiiijkl99z";

        // output: XXXXeeeXXXXXXXXXXjXXXXX  everything except e and j was replaced
        System.out.println(newAlphanumeric.replaceAll("[^ej]", "X"));
        // output: XXXDXXXF12Ghhiiiijkl99z      the regular expressions are case-sensitive
        System.out.println(newAlphanumeric.replaceAll("[abcdef345678]", "X"));
        // output: XXXDXXXF12Ghhiiiijkl99z      - specifies the range
        System.out.println(newAlphanumeric.replaceAll("[a-f3-8]", "X"));
        // output: XXXXXXXX12Ghhiiiijkl99z
        System.out.println(newAlphanumeric.replaceAll("[a-fA-F3-8]", "X"));
        // output: XXXXXXXX12Ghhiiiijkl99z      we're ignoring the case
        // this works for ASCII strings. For unicode we should use (?iu)
        System.out.println(newAlphanumeric.replaceAll("(?i)[a-f3-8]", "X"));
        // output: abcDeeeFXXGhhiiiijklXXz
        System.out.println(newAlphanumeric.replaceAll("[0-9]", "X"));
        // output: abcDeeeFXXGhhiiiijklXXz      all digits
        System.out.println(newAlphanumeric.replaceAll("\\d", "X"));
        // output: XXXXXXXX12XXXXXXXXXX99X      all that are not digits
        System.out.println(newAlphanumeric.replaceAll("\\D", "X"));

        String hasWhitespace = "I have blanks and\ta tab, and also a new line\n";

        System.out.println(hasWhitespace);
        // output: Ihaveblanksandatab,andalsoanewline   all whitespaces are removed
        System.out.println(hasWhitespace.replaceAll("\\s", ""));
        // output: I have blanks andXa tab, and also a new line
        System.out.println(hasWhitespace.replaceAll("\t", "X"));
        // output:    only whitespaces remained
        System.out.println(hasWhitespace.replaceAll("\\S", ""));
        // output:  XXXXXXXXXXXXXXXXXXXXXXX     all letters and digits are replaced
        System.out.println(newAlphanumeric.replaceAll("\\w", "X"));
        // output:  X XXXX XXXXXX XXX	X XXX, XXX XXXX X XXX XXXX     all letters and digits are replaced
        System.out.println(hasWhitespace.replaceAll("\\w", "X"));
        // output: XIX XhaveX XblanksX XandX	XaX XtabX, XandX XalsoX XaX XnewX XlineX
        // all boundaries of the words are replaced
        System.out.println(hasWhitespace.replaceAll("\\b", "X"));

        // Quantifiers

        String thirdAlphanumericString = "abcDeeeF12Ghhiiiijkl99z";
        String thirdAlphanumericString1 = "abcDF12Ghhiiiijkl99z";
        String thirdAlphanumericString2 = "abcDeF12Ghhiiiijkl99z";

        // output:  YYYF12Ghhiiiijkl99z
        System.out.println(thirdAlphanumericString.replaceAll("^abcDeee", "YYY"));
        // output:  YYYF12Ghhiiiijkl99z     symbol{number} - defines the quantity of the symbol in a regex
        System.out.println(thirdAlphanumericString.replaceAll("^abcDe{3}", "YYY"));
        // output:  YYYF12Ghhiiiijkl99z     +  - one or more "e"
        System.out.println(thirdAlphanumericString.replaceAll("^abcDe+", "YYY"));
        // output:  YYYF12Ghhiiiijkl99z
        System.out.println(thirdAlphanumericString.replaceAll("^abcDe*", "YYY"));

        // output:  abcDF12Ghhiiiijkl99z    no matches
        System.out.println(thirdAlphanumericString1.replaceAll("^abcDe{3}", "YYY"));
        // output:  abcDF12Ghhiiiijkl99z    no matches
        System.out.println(thirdAlphanumericString1.replaceAll("^abcDe+", "YYY"));
        // output:  YYYF12Ghhiiiijkl99z    matches because * means to match whether there is "e" or not
        System.out.println(thirdAlphanumericString1.replaceAll("^abcDe*", "YYY"));

        // output:  YYYF12Ghhiiiijkl99z
        System.out.println(thirdAlphanumericString.replaceAll("^abcDe{2,5}", "YYY"));
        // output:  abcDeF12Ghhiiiijkl99z   no matches (because we're looking between 2 and 5 "e")
        System.out.println(thirdAlphanumericString2.replaceAll("^abcDe{2,5}", "YYY"));

        // output: abcDeeeF12GYkl99z    all the "h, i, and j" symbols are replaces by "Y"
        System.out.println(thirdAlphanumericString.replaceAll("h+i*j", "Y"));

        StringBuilder htmlText = new StringBuilder("<h1>My Heading</h1>");
        htmlText.append("<h2>Sub-heading</h2>");
        htmlText.append("<p>This is a paragraph about something.</p>");
        htmlText.append("<p>This is another paragraph about something else.</p>");
        htmlText.append("<h2>Summary</h2>");
        htmlText.append("<p>Here is the summary.</p>");

        String h2pattern = "<h2>";
        Pattern pattern = Pattern.compile(h2pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(htmlText);
        System.out.println(matcher.matches()); // return true

        matcher.reset();
        int count = 0;
        while(matcher.find()) {
            count++;
            System.out.println("Occurrence " + count + " : " + matcher.start() + " to " + matcher.end());
        }

        String h2GroupPattern = "(<h2>.*?</h2>)";   // () - match group
        Pattern groupPattern = Pattern.compile(h2GroupPattern);
        Matcher groupMatcher = groupPattern.matcher(htmlText);
        System.out.println(groupMatcher.matches());
        groupMatcher.reset();

        while (groupMatcher.find()) {
            System.out.println("Occurrence: " + groupMatcher.group(1));
        }

        String h2TextGroups = "(<h2>)(.+?)(</h2>)";     // the text between tags is in the second group
        Pattern h2TextPattern = Pattern.compile(h2TextGroups);
        Matcher h2TextMatcher = h2TextPattern.matcher(htmlText);
        System.out.println(h2TextMatcher.matches());
        h2TextMatcher.reset();

        // group 0 is the entire pattern
        // group 1 is the opening tag
        // group 2 is the text between tags
        // group 3 is the closing tag
        while (h2TextMatcher.find()) {
            System.out.println("Occurrence: " + h2TextMatcher.group(2));    // we extract text from the second group
        }

        // a regex "abc" technically means "a" and "b" and "c" - and operator
        // [H|h]arry - or operator
        System.out.println("harry".replaceAll("[H|h]arry", "Larry"));
        System.out.println("Harry".replaceAll("[H|h]arry", "Larry"));

        // [^anc]  - a carrot means "not" here. there's also another symbol for "not", it's an exclamation sign "!"
        String tvTest = "tstvtkt";
        //String tNotVRegExp = "t[^v]";
        String tNotVRegExp = "t(?!v)";  // "t" that is not followed by "v" - a negative look ahead
        Pattern tNotVPattern = Pattern.compile(tNotVRegExp);
        Matcher tNotVMatcher = tNotVPattern.matcher(tvTest);

        count = 0;
        while (tNotVMatcher.find()) {
            count++;
            System.out.println("Occurrence " + count + " : " + tNotVMatcher.start() + " to " + tNotVMatcher.end());
        }
        // "t(?=v)" - a negative look ahead
        // ^([\(]{1}[0-9]{3}[\)]{1}[ ]{1}[0-9]{3}[\-]{1}[0-9]{4})$  - a US phone number regex "(800) 123-4567"
        String phone1 = "1234567890";   // shouldn't match
        String phone2 =  "(123) 456-7890";  // should match
        String phone3 =  "123 456-7890";  // shouldn't match
        String phone4 =  "(123)456-7890";  // shouldn't match

        System.out.println("phone1 = " +
                phone1.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{4})$"));
        System.out.println("phone1 = " +
                phone2.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{4})$"));
        System.out.println("phone1 = " +
                phone3.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{4})$"));
        System.out.println("phone1 = " +
                phone4.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{4})$"));

        // ^4[0-9]{12}([0-9]{3})?$  - a visa card number regex
        String visa1 = "4444444444444"; // should match
        String visa2 = "5444444444444"; // shouldn't match
        String visa3 = "4444444444444444";  // should match
        String visa4 = "4444";  // shouldn't match

        System.out.println("visa1 " + visa1.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println("visa2 " + visa2.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println("visa3 " + visa3.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println("visa4 " + visa4.matches("^4[0-9]{12}([0-9]{3})?$"));
    }
}
