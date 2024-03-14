package questiontypes.directanswer.checksum;

import quizframework.Answer;
import quizframework.utils.QuizUtils;

/**
 * Generate a bitwise checksum question - based on the algorithm implemented in {#link CheckSumQuestionCore}
 */

public class BitwiseChecksum extends CheckSumQuestionCore {

    private byte checkSum;
    private String correctString;

    @Override
    public String createQuestionTitle() {
        return "Bitwise checksum question";
    }

    @Override
    public String createQuestionText() {
        return "Which of the following strings generates the bitwise checksum ``" + checkSum + "``?";
    }

    @Override
    public void createCalcData() {
        correctString = QuizUtils.genRandomString(MIN_LEN, MAX_LEN, LOW_RNG, HIGH_RNG);
        checkSum = bitwiseCheckSum(correctString);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(correctString);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.genRandomString(MIN_LEN, MAX_LEN, LOW_RNG, HIGH_RNG));
    }
}
