package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.stream.IntStream;

/**
 * Find the product of odd numbers from x to y inclusive
 * The following code is suitable for use in autograder
 <pre>
public class Test {

    public static void main(String[] args) {
        ProductOddRange.x = 5;
        ProductOddRange.y = 9;

        long givenAns = ProductOddRange.answer();
        System.exit((givenAns == productOddRange(ProductOddRange.x, ProductOddRange.y)) ? 0 : 1);
    }

    public static long productOddRange(int x, int y) {
        int runningProduct = 1;
        int firstOdd = x % 2 == 1 ? x : x + 1;
        for (int i = firstOdd; i <= y; i+=2) {
            runningProduct *= i;
        }
        return runningProduct;
    }
}
 </pre>
 */

public class ProductOddRange extends McqQuestion {

    private int minVal;
    private int maxVal;
    private int answerVal;

    @Override
    public String createQuestionTitle() {
        return "Progression";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Product of Odd Numbers in Range.** What is the product of odd numbers from X to Y inclusive,"
                + "where X=" + minVal + " and Y=" + maxVal + "? For example, for X=2 and Y=7 it is 3 * 5 * 7 = 105.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("ProductOddRange",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int x = %d;\npublic static int y = %d;", minVal, maxVal))),
                "int");
        return builder.append(code).toString();
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
        minVal = QuizUtils.genRandomInt(3, 9);
        maxVal = QuizUtils.genRandomInt(minVal + 4, minVal + 8);
        answerVal = IntStream.rangeClosed(minVal, maxVal).filter(i -> i % 2 == 1).reduce(1, (a, b) -> a * b);
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        int runningProduct = 1;
        for (int i = minVal; i <= maxVal; i++) {
            if (i % 2 == 1) {
                runningProduct *= i;
            }
        }
        return runningProduct == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(answerVal),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(QuizUtils.genRandomInt(answerVal - 20, answerVal + 20)),
                "some incorrect feedback");
    }
}
