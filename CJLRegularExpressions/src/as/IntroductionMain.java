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
    ^ - the beginning of a pattern (but if it's in the [], [^...] is a character class, it's not a boundary matcher)
    $ - the end of a pattern
*/

/*
IntellyJ IDEA hint: press Alt + Enter on a regex and choose Check RegExp to test it
*/

public class IntroductionMain {
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
    }
}
