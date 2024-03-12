package questiontypes.checksum;

import quizframework.Answer;
import quizframework.QuizUtils;

/*
Generate a simple checksum question - create a random string and a corresponding checksum. Then create a set of
(incorrect) strings - the question asks which string the checksum belongs to.
 */
public class CheckSumStringQuestion extends CheckSumQuestionCore {

    private String correctAnswer;

    @Override
    public String createQuestionTitle(){
        return "Which string matches checksum?";
    }
    @Override
    public String createQuestionText() {
        return "Which of the following strings generates the simple checksum ``" + correctAnswer + "`` ?";
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
        correctAnswer = QuizUtils.genRandomString(65, 20, 'a', 'z');
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(this.correctAnswer,
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(this.correctAnswer,
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2), "some incorrect feedback");
    }
}
