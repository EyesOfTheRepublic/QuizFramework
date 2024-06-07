package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 *Generate a simple checksum question - create a random string and a corresponding checksum. Then create a set of
 *(incorrect) strings - the question asks which string the checksum belongs to. Uses algorithms defined in
 * {@link CheckSumQuestionUtils}
 */
public class CheckSumStringQuestion extends McqQuestion {

    private String correctAnswer;

    @Override
    public String createQuestionTitle(){
        return "Which string matches checksum?";
    }
    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder( "Which of the following strings generates the simple checksum "
                + CheckSumQuestionUtils.simpleCheckSum(correctAnswer) + " ?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String code = String.format(CodeUtils.CODE_FRAMEWORK, "CheckSumString",
                CodeUtils.indentTextBlock(String.format("public static int checkSum = %d;",
                        CheckSumQuestionUtils.simpleCheckSum(correctAnswer)),1));
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
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
