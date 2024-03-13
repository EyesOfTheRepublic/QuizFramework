package questiontypes.checksum;

import quizframework.Answer;
import quizframework.utils.QuizUtils;

/*
Generate a random string, the correct checksum, and a set of random incorrect checksums. The question asks which checksum is correct
 */
public class CheckSumValueQuestion extends CheckSumQuestionCore {

    private String checkString;

    @Override
    public String createQuestionTitle() {
        return "Basic checksum";
    }

    @Override
    public String createQuestionText() {
        return "What is the result of running the simple checksum algorithm on the string ``" + checkString + "``?";
    }

    @Override
    public String createGeneralFeedback() {
        return "Some generic feedback";
    }

    @Override
    public String createCorrectFeedback() {
        return "Some feedback for the correct answer";
    }

    @Override
    public String createIncorrectFeedback() {
        return "Some general feedback for incorrect answers";
    }

    @Override
    public void createCalcData() {
        checkString = QuizUtils.genRandomString(65, 20, 'a', 'z');
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(Long.toString(simpleCheckSum(checkString)),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(QuizUtils.genRandomLong(Long.MIN_VALUE, Long.MAX_VALUE)),
                "some incorrect feedback");
    }
}
