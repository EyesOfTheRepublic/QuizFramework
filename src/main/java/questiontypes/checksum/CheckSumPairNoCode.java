package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.QuizUtils;

/**
 *An example checksum question - generate a list of pairs of strings and checksums (using the algorithm in
 *{@link CheckSumQuestionUtils} - one of which will be correct and the others incorrect.
 * As currently written, not really suitable for use with autograder
 */
public class CheckSumPairNoCode extends McqQuestion {

    private String checkedString;
    private long checkSum;

    @Override
    public String createQuestionTitle() {
        return "Matching string checksum pair";
    }

    @Override
    public String createQuestionText() {
        return "Which of the following pairs represents a string and it's simple checksum?";
    }

    @Override
    public void createCalcData() {
        String dataString = QuizUtils.genRandomString(65, 20, 'a', 'z');
        checkedString = dataString;
        checkSum = CheckSumQuestionUtils.simpleCheckSum(dataString);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(checkedString
                        + " " + checkSum,
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(checkedString,
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2) + " "
                + QuizUtils.genRandomLong(checkSum - 500, checkSum + 500), "some incorrect feedback");
    }
}
