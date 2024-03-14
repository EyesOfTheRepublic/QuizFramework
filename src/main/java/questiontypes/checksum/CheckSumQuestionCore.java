package questiontypes.checksum;

import quizframework.Question;
/*
Class used as the basis of the checksum example questions - most importantly contains the actual checksum algorithms
This approach probably doesn't make any sense now given changes elsewhere
 */
public abstract class CheckSumQuestionCore extends Question {

    protected static final int MIN_LEN = 65;
    protected static final int MAX_LEN = 85;

    protected static final char HIGH_RNG = 'z';
    protected static final char LOW_RNG = 'a';

    protected long simpleCheckSum(String str) {
        long k = 7;//7
        for (int i = 0; i < str.length(); i++) {
            k *= 23;//23
            k += str.charAt(i);
            k *= 13;//13
            k %= 1000000009;
        }
        return k;
    }

    protected byte bitwiseCheckSum(String str) {
        byte[] input = str.getBytes();
        byte checksum = 0;
        for (byte cur_byte : input) {
            checksum = (byte) (((checksum & 255) >>> 1) + ((checksum & 1) << 7));
            checksum = (byte) ((checksum + cur_byte) & 255);
        }
        return checksum;
    }
}
