package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Find the sum of even numbers from x to y inclusive
 * The following code is suitable for use in autograder
 <pre>
public class Test {

    public static void main(String[] args) {
        EvenSumRange.x = 19;
        EvenSumRange.x = 42;

        long givenAns = EvenSumRange.answer();
        System.exit((givenAns == evenSumRange(EvenSumRange.x, EvenSumRange.y)) ? 0 : 1);
    }

    public static long evenSumRange(int x, int y) {
        int runningTotal = 0;
        for (int i = x; i <= y; i++) {
            if (i % 2 == 0) {
                runningTotal += i;
            }
        }
        return runningTotal;
    }
}
 </pre>
 */

public class EvenSumRange extends McqQuestion {

    private int minVal;
    private int maxVal;
    private int answerVal;

    @Override
    public String createQuestionTitle() {
        return "Sum of Even Numbers in Range";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Even Numbers in Range.** What is the sum of even numbers from X to Y inclusive,"
                + "where X=" + minVal + " and Y=" + maxVal + "? For example, for X=2 and Y=6 it is 2 + 4 + 6 = 12.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("EvenSumRange",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int x = %d;\n public static int y = %d;", minVal, maxVal))),
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
        minVal = QuizUtils.genRandomInt(6, 18);
        maxVal = QuizUtils.genRandomInt(minVal + 10, minVal + 32);
        answerVal = IntStream.rangeClosed(minVal, maxVal).filter(i -> i % 2 == 0).sum();
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        int runningSum = 0;
        for (int i = minVal; i <= maxVal; i++) {
            if (i % 2 == 0) {
                runningSum += i;
            }
        }
        return runningSum == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(answerVal),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(QuizUtils.genRandomInt(50, 400)),
                "some incorrect feedback");
    }
}
