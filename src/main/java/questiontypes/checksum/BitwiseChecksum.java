package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.QuizUtils;

/**
 * Generate a bitwise checksum question - based on the algorithm implemented in {#link CheckSumQuestionUtils}
 */

public class BitwiseChecksum extends McqQuestion {

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
        correctString = QuizUtils.genRandomString(CheckSumQuestionUtils.MIN_LEN,
                CheckSumQuestionUtils.MAX_LEN,CheckSumQuestionUtils.LOW_RNG, CheckSumQuestionUtils.HIGH_RNG);
        checkSum = CheckSumQuestionUtils.bitwiseCheckSum(correctString);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(correctString);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.genRandomString(CheckSumQuestionUtils.MIN_LEN,
                CheckSumQuestionUtils.MAX_LEN,CheckSumQuestionUtils.LOW_RNG, CheckSumQuestionUtils.HIGH_RNG));
    }
}
