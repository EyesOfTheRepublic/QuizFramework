package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.NumericQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 *Generate a random string, the correct checksum using algorithm defined in {@link questiontypes.checksum.utils.CheckSumQuestionUtils},
 * and a set of random incorrect checksums. The question asks which checksum is correct. The following code can be used in autograder:
 <pre>
 public class Test {
    public static void main(String[] args) {
        long givenAns = CheckSumValue.answer();
        System.exit((givenAns == simpleCheckSum(CheckSumValue.checkStr)) ? 0 : 1);
    }

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
}
 </pre>
 */
public class CheckSumValueQuestion extends NumericQuestion {

    private String checkString;

    @Override
    public String createQuestionTitle() {
        return "Basic checksum";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder( "What is the result of running the simple checksum algorithm on the string ``"
                + checkString + "``?").append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("CheckSumValue",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static String checkStr = \"%s\";", checkString))),
                "long");
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
        checkString = QuizUtils.genRandomString(65, 20, 'a', 'z');
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(Long.toString(CheckSumQuestionUtils.simpleCheckSum(checkString)),
                "some correct feedback");
    }
}
