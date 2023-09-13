package quizframework;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Static methods generally useful in generating quiz data and incorrect answers
 * to questions.
 */

public class QuizUtils {

    //Prevent instance of this class being (pointlessly) created
    private QuizUtils(){}

    /**
     * Generate a new long from one that is provided, that has the same number of digits and is the same sign. Like
     * {@code permuteString}, if you ask for variations on a very short long (that is, one with few digits), then it
     * may be impossible to generate enough incorrect answers (e.g. you want 11 options based on a 1-digit number) which
     * will lead to an infinite loop.
     *
     * @param arg an arbitrary long - note that there are limited options if this has very few digits
     * @return a new long that is the same number of (decimal) digits as the argument and the same sign
     */
    public static final long similarLong(Long arg) {
        int digits = String.valueOf(arg).length();
        int base = (int) Math.pow(10, digits - 1);
        long positiveVal = ThreadLocalRandom.current().nextLong(9 * base);
        return arg < 0 ? -positiveVal : positiveVal;
    }

    /**
     * Generate a random string from a specified range of characters, with a specified minimum length.
     * + some range.
     * @param minLen The generated string will be at least this long.
     * @param sizeRng The final string will be between {@code minLen} and {@code minLen + sizeRng} in
     *                length ({@code sizeRng} can be zero - in which case the string will be {@code minLen} long).
     * @param low The low end of the range of characters used in the string. If {@code low > high},
     *           {@code low} and {@code high} will be swapped.
     * @param high The low end of the range of characters used in the string. If {@code low > high},
     *           {@code low} and {@code high} will be swapped.
     * @return The randomly generated string of size between {@code minlen} and {@code minLen + sizeRng} made up of
     *              characters between {@code low} and {@code high} inclusive.
     *
     *  <h3>Example</h3>
     *  <p>{@code String example = genRandomString(50, 10, 'a', 'z');} - a string between 50 and 60 characters long
     *  made up of lower-case latin characters.</p>
     */

    public static final String genRandomString(int minLen, int maxLen,
                                               char low, char high) {

        if (low > high) {
            final char temp = low;
            low = high;
            high = temp;
        }

        if (minLen > maxLen) {
            final int temp = minLen;
            minLen = maxLen;
            maxLen = temp;
        }

        //Generate target string length and prep buffer
        final int targetStringLength = ThreadLocalRandom.current().nextInt(minLen, maxLen);
        StringBuilder buffer = new StringBuilder(targetStringLength);

        //use the low and high characters to set the random generation range
        final int leftLmt = low;
        final int limLen = high - low + 1;

        //Generate and return the string
        for (int i = 0; i < targetStringLength; i++) {
            final int randomLimitedInt = leftLmt + ThreadLocalRandom.current().nextInt(limLen);// * (limLen);
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    /*Controls the range over which random permutations of strings will be attempted in
     * {@code permuteString} - make it smaller at your peril!
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
     * <strong>HOWEVER</strong> it is possible to force it to effectively do this when generating wrong answers - if
     * the supplied parameters do not allow enough different incorrect answers to be generated. This is because
     * generation of wrong answers will continue until they are all unique - so if it's not possible to generate
     * enough unique wrong answers, the process won't terminate.</p>
     * @param dataString The string to be permuted
     * @param locationDecimal The centre point of the change expressed as a fraction 0.0 to 1.0 - 0.0 represents the
     *                 start of the string; 0.5 the middle; 1.0 the end
     * @param rangeDecimal The amount of variation from {@code locationDecimal} allowed - so 0.5 means +/1 half of the string. Must
     *              be at least MIN_PERMUTATION_RNG = 0.2
     * @param permutations The number of permutations attempted - note these are NOT guaranteed to be unique and picking
     *                     a small value for {@code rangeDecimal} will make it less likely they are, especially if the string
     *                     to permute is relatively short.
     * @return The permuted or original string - the original string is returned if no permutations are possible, or the
     * number of permutations requested is negative.
     *
     * <h2>Examples</h2>
     * {@code String val = permuteString(myString, 0.5, 0.3, 5); //5 permutations between 20% from the start & end of myString}
     * {@code String val = permuteString(myString, 1.0, 0.2, 1); //1 permutation within 20% of the end of myString}
     */

    public static final String permuteString(final String dataString, final double locationDecimal,
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
        final int highRange = (int)(rawHighRange > strLen - 1 ? strLen - 1 : rawHighRange);

        //We will make random changes between lowRange and lowRange + range
        final int lowRange = (int)(rawLowRange < 0 ? 0 : rawLowRange);
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
            int loc1 = ThreadLocalRandom.current().nextInt(range) + lowRange;
            int loc2;
            do {
                loc2 = ThreadLocalRandom.current().nextInt(range) + lowRange;
            } while (loc2 == loc1);

            //perform the swap.
            char temp = strList[loc1];
            strList[loc1] = strList[loc2];
            strList[loc2] = temp;
        }

        return String.valueOf(strList);
    }

    /**
     * Generate a random integer between min (inclusive) and max (exclusive)
     * @param min minimum (inclusive) value
     * @param max maximum (exclusive) value
     * @return random integer between min (inclusive) and max (exclusive)
     */
    public static int genRandomInt(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Generate a random long between min (inclusive) and max (exclusive)
     * @param min minimum (inclusive) value
     * @param max maximum (exclusive) value
     * @return random long between min (inclusive) and max (exclusive)
     */
    public static long genRandomLong(final long min, final long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
