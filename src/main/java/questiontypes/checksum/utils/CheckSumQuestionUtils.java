package questiontypes.checksum.utils;

/**
 *Class used as the basis of the checksum example questions - most importantly contains the actual checksum algorithms
 *This approach probably doesn't make any sense now given changes elsewhere
 */
public class CheckSumQuestionUtils {

    public static final int MIN_LEN = 65;
    public static final int MAX_LEN = 85;

    public static final char HIGH_RNG = 'z';
    public static final char LOW_RNG = 'a';

    public static long simpleCheckSum(String str) {
        long k = 7;//7
        for (int i = 0; i < str.length(); i++) {
            k *= 23;//23
            k += str.charAt(i);
            k *= 13;//13
            k %= 1000000009;
        }
        return k;
    }

    public static byte bitwiseCheckSum(final String str) {
        byte[] input = str.getBytes();
        byte checksum = 0;
        for (byte cur_byte : input) {
            checksum = (byte) (((checksum & 255) >>> 1) + ((checksum & 1) << 7));
            checksum = (byte) ((checksum + cur_byte) & 255);
        }
        return checksum;
    }
}
