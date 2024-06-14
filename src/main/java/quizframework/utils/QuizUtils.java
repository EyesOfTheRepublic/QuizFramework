package quizframework.utils;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Static methods and constants generally useful in generating quiz data and incorrect answers
 * to questions.
 * <ul>
 *     <li>{@link #similarLong} - generate a new long with the same number of digits.</li>
 *     <li>{@link #genRandomString} - generate a random string with a specified length range and a specified character
 *     range.</li>
 *     <li>{@link #MIN_PERMUTATION_RNG} - the minimum viable value for the permutation range when permuting strings.</li>
 *     <li>{@link #permuteString} - permute a specified String a specified number of times within a specified
 *     'range' between the start and end.</li>
 *     <li>{@link #genRandomInt} - generate a random int within a specified range.</li>
 *     <li>{@link #genRandomDouble} - generate a random double within a specified range.</li>
 *     <li>{@link #genRandomLong} - generate a random long within a specified range.</li>
 * </ul>
 */

public final class QuizUtils {

    //Prevent inadvertent instantiation
    private QuizUtils() {
    }

    /**
     * Generate a new long from one that is provided, that has the same number of digits and is the same sign. Like
     * {@code permuteString}, if you ask for variations on a very short long (that is, one with few digits), then it
     * may be impossible to generate enough incorrect answers (e.g. you want 11 options based on a 1-digit number) which
     * will lead to an infinite loop.
     *
     * @param arg an arbitrary long - note that there are limited options if this has very few digits
     * @return a new long that is the same number of (decimal) digits as the argument and the same sign
     */
    public static long similarLong(final long arg) {
        final int digits = String.valueOf(arg).length();
        final int base = (int) Math.pow(10, digits - 1.0);
        final long positiveVal = ThreadLocalRandom.current().nextLong(9L * base);
        return arg < 0 ? -positiveVal : positiveVal;
    }

    /**
     * Generate a random string from a specified range of characters, with a specified minimum length.
     * + some range.
     *
     * @param minLen The generated string will be at least this long.
     * @param low    The low end of the range of characters used in the string. If {@code low > high},
     *               {@code low} and {@code high} will be swapped.
     * @param high   The low end of the range of characters used in the string. If {@code low > high},
     *               {@code low} and {@code high} will be swapped.
     * @return The randomly generated string of size between {@code minlen} and {@code minLen + sizeRng} made up of
     * characters between {@code low} and {@code high} inclusive.
     *
     * <h3>Example</h3>
     * <p>{@code String example = genRandomString(50, 10, 'a', 'z');} - a string between 50 and 60 characters long
     * made up of lower-case latin characters.</p>
     */

    public static String genRandomString(final int minLen, final int maxLen,
                                         final char low, final char high) {

        final int minLenVal = Math.min(minLen, maxLen);
        final int maxLenVal = Math.max(minLen, maxLen);
        final char lowVal = low <= high ? low : high;
        final char highVal = low <= high ? high : low;

        //Generate target string length and prep buffer
        final int targetStringLength = genRandomInt(minLenVal, maxLenVal);

        final Random random = new Random();
        return random
                .ints(lowVal, highVal + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Controls the range over which random permutations of strings will be attempted in
     * {@code permuteString} - make it smaller at your peril! This is public, so it can be used as the rangeDecimal
     * argument in {@link #permuteString}. Represents the faction of the string that will be permuted - the smaller it
     * is, the less likely (and ultimately impossible) it is to generate a new string that is actually different to
     * the old one. Value chosen empirically based on experiments with the typical random string seen in questions.
     */
    public static final double MIN_PERMUTATION_RNG = 0.2;

    /**
     * Make random permutations to a string - this is useful when generating <strong>wrong</strong> answers that
     * must be visibly similar to the correct answer - e.g. by swapping two characters near the middle.
     *
     * <h2>NOTE</h2>
     * <p>{@code permuteString} goes to some trouble to avoid ending up in an infinite loop - "randomly" trying
     * to swap characters when the provided parameters don't allow that to happen because they don't give
     * enough freedom to make changes - by returning the original string if no permutations are possible.
     * <strong>HOWEVER</strong> it is possible to force it to effectively indirectly do this when generating wrong answers
     * - if the supplied parameters do not allow enough different incorrect answers to be generated. This is because
     * generation of wrong answers will continue until they are all unique - so if it's not possible to generate
     * enough unique wrong answers, the process won't terminate.</p>
     *
     * @param dataString      The string to be permuted
     * @param locationDecimal The centre point of the change expressed as a fraction 0.0 to 1.0 - 0.0 represents the
     *                        start of the string; 0.5 the middle; 1.0 the end
     * @param rangeDecimal    The amount of variation from {@code locationDecimal} allowed - so 0.5 means +/1 half of the string. Must
     *                        be at least MIN_PERMUTATION_RNG = {@value MIN_PERMUTATION_RNG}
     * @param permutations    The number of permutations attempted - note these are NOT guaranteed to be unique and picking
     *                        a small value for {@code rangeDecimal} will make it less likely they are, especially if the string
     *                        to permute is relatively short.
     * @return The permuted or original string - the original string is returned if no permutations are possible, or the
     * number of permutations requested is negative.
     *
     * <h2>Examples</h2>
     * {@code String val = permuteString(myString, 0.5, 0.3, 5); //5 permutations between 20% from the start & end of myString}
     * {@code String val = permuteString(myString, 1.0, 0.2, 1); //1 permutation within 20% of the end of myString}
     */

    public static String permuteString(final String dataString, final double locationDecimal,
                                       final double rangeDecimal, final int permutations) {

        /*We only make changes if the number of permutations is +ve and if the rangeDecimal is large enough to avoid
        a high chance that no permutations will be possible
        */
        if (permutations < 1 || rangeDecimal < MIN_PERMUTATION_RNG) {
            return dataString;
        }

        /* Transform the supplied decimal 0.0 - 1.0 location and range of changes into positions in the string,
        taking care to avoid values > strlen - 1 and < 0
         */
        final long strLen = dataString.length();
        final long rawLowRange = Math.min(Math.round((locationDecimal - rangeDecimal) * strLen), strLen - 1);
        final long rawHighRange = Math.round((locationDecimal + rangeDecimal) * strLen);
        final int highRange = (int) Math.min(strLen - 1, rawHighRange);

        //We will make random changes between lowRange and lowRange + range
        final int lowRange = (int) (rawLowRange < 0 ? 0 : rawLowRange);
        final int range = highRange - lowRange;

        /*
        If the rangeDecimal of characters is not at least two, then no permutations are possible so return original string
         */
        if (range < 2) {
            return dataString;
        }

        //Turn String into char array and make permutation swaps
        char[] strList = dataString.toCharArray();
        for (int i = 0; i < permutations; i++) {

            /*Generate two random locations to swap, ensuring they are not the same
            Note that if the rangeDecimal of characters to swap is not at least 2, we will
            have returned the original string above - otherwise this could be an
            infinite loop
             */
            final int loc1 = ThreadLocalRandom.current().nextInt(range) + lowRange;
            int loc2;
            do {
                loc2 = ThreadLocalRandom.current().nextInt(range) + lowRange;
            } while (loc2 == loc1);

            //perform the swap.
            final char temp = strList[loc1];
            strList[loc1] = strList[loc2];
            strList[loc2] = temp;
        }

        return String.valueOf(strList);
    }

    /**
     * Generate a random integer between min (inclusive) and max (exclusive)
     *
     * @param min minimum (inclusive) value
     * @param max maximum (exclusive) value
     * @return random integer between min (inclusive) and max (exclusive)
     */
    public static int genRandomInt(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Generate a random double in a specific range, optionally with a specific precision
     *
     * @param min      the minimum value
     * @param max      the maximum value
     * @param decimals the number of decimal places (if zero or negative this is ignored)
     * @return the generated double
     */
    public static double genRandomDouble(final double min, final double max, final int decimals) {
        final double val = ThreadLocalRandom.current().nextDouble(min, max);
        return decimals > 0 ? val * decimals / decimals : val;
    }

    /**
     * Generate a random long between min (inclusive) and max (exclusive)
     *
     * @param min minimum (inclusive) value
     * @param max maximum (exclusive) value
     * @return random long between min (inclusive) and max (exclusive)
     */
    public static long genRandomLong(final long min, final long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    /**
     *
     */
    public static boolean isNumber(String str) {
        try (Scanner scan = new Scanner(str)) {
            return (scan.hasNextInt() || scan.hasNextDouble());
        }
    }

    /**
     * Standard text to appear before code-based questions
     */
    public static final String CODE_QUESTION_BOILERPLATE = """
                            
            Copy the code below to the editor/IDE of your choice, and write your code in the method called answer below
            (you can write additional methods if you want) 
            - *but do not change the name, parameters or return type of the method below*.
                
            **Upload your code to autograder** *and* **enter the answer below**.
            
            """;
}
