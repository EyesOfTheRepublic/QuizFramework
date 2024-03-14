package questiontypes.directanswer.crypto.utils;

public class CypherUtils {

    public static final int MIN_STR_LENG = 45;

    public static final int MAX_STR_LEN = 90;

    public static final int MIN_KEY = 9;
    public static final int MAX_KEY = 15;

    public static final char HIGH_CHAR = 'm';
    public static final char LOW_CHAR = 'a';

    /*
     * Prevent unintentionally instantiating this
     */
    private CypherUtils() {}


    public static String encode(String plainText, int key) {
        if (plainText.length() % key != 0) {
            return null;
        }
        return codeToString(codeToArray(plainText, key));
    }

    public static String decode(String cypherText, int key) {
        if (cypherText.length() % key != 0) {
            return null;
        }
        return codeToString(codeToArray(cypherText, cypherText.length() / key));
    }


    /*
     * Encode a string to an array using a specific number of columns as the key
     */
    private static char[][] codeToArray(String plainText, int cols) {

        //Work out number of rows in the array
        int len =  plainText.length() / cols;

        //Remember in Java it's rows first!
        char [][] transposeArray = new char[len][cols];

        int strLen = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < cols; j++) {
                transposeArray[i][j] = plainText.charAt(strLen);
                strLen++;
            }
        }
        return transposeArray;
    }

    /*
     * Turn a 2D char array into a string
     */
    private static String codeToString(char[][] encoded) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < encoded[0].length; i++) {
            for (int j = 0; j < encoded.length; j++) {
                builder.append(encoded[j][i]);
            }
        }
        return builder.toString();
    }
}
