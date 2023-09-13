package questiontypes.checksum;

import quizframework.Question;

import java.util.Random;
/*
Class used as the basis of the checksum example questions - most importantly contains the actual checksum algorithm
 */
public abstract class CheckSumQuestionCore extends Question {

    protected Random rnd = new Random();

    @Override
    public String createQuestionTitle() {
        return "Basic Checksum";
    }

    @Override
    public int createQuestionPoints() {
        return 8;
    }

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
}
