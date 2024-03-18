package questiontypes.termrewriting.utils;

/**
 * Class containing operations common to some or all of the term rewrting questions
 */
public class RewritingUtils {

    /*
    Prevent inadvertent instantiation
     */
    private RewritingUtils() {}

    /*Run the rules once in the order they appear in the array and return the resulting string */
    public static String runOneStep(String input, final String[][] rules) {
        for (int i = 0; i < rules.length; i++) {
            input = input.replaceAll(rules[i][0], rules[i][1]);
        }
        return input;
    }


    //Method that generates a random string and then runs until no more rewriting changes occure
    public static String runToCompletion(final String inString, final String[][] rewriteMap) {
        String tempString = inString;
        boolean done = false;
        //Generate a random string and term rewrite until no more changes happen
        do {
            String rewrittenString = RewritingUtils.runOneStep(tempString, rewriteMap);
            if (rewrittenString.equals(tempString)) {
                done = true;
            } else {
                tempString = rewrittenString;
            }
        } while (!done);
        return tempString;
    }

    //USED FOR SAMPLE SOLUTION TESTING
    public static String replaceAll(String workingString, final String[][] rewriteMap) {
        for(String[] elt : rewriteMap) {
            workingString = workingString.replaceAll(elt[0], elt[1]);
        }
        return workingString;
    }
}
