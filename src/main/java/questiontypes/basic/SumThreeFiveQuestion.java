package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * What is the sum of numbers from 1 to n divisible by 3 or 5, but not by both.
 * Code below suitable for use in autograder
 <pre>

public class Test {

    public static void main(String[] args) {
        SumThreeFive.sumRange = 37;

        long givenAns = SumThreeFive.answer();
        System.exit((givenAns == sumThreeFive(SumThreeFive.sumRange)) ? 0 : 1);
    }

    public static long sumThreeFive(int sumRange) {
        int runningTotal = 0;
        for (int i = 1; i <= sumRange; i++) {
            if ((i % 3 == 0) ^ (i % 5 == 0)) {
                runningTotal += i;
            }
        }
        return runningTotal;
    }
}
</pre>
 */

public class SumThreeFiveQuestion extends McqQuestion {

    private final Random rnd = new Random();

    private int sumRange;

    @Override
    public String createQuestionTitle() {
        return "Sum of Multiples";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Numbers Multiples of 3 or 5.**"
                + "What is the sum of numbers that are multiples of 3 or 5 BUT NOT BOTH from 1 to n, where n= "
                + sumRange + "? For example, the sum from 1 to 19 would be 3 + 5 + 6 + 9 + 10 + 12 + 18 = 63. "
                + "We do NOT include 15 because it is a multiple of BOTH 3 and 5.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("SumThreeFive",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int sumRange = %d;", sumRange))),
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
        sumRange = rnd.nextInt(20) + 17;
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        return IntStream.rangeClosed(1, sumRange)
                .filter(i -> (i % 3 == 0) ^ (i % 5 == 0))
                .sum() == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        int runningTotal = 0;
        for (int i = 1; i <= sumRange; i++) {
            if ((i % 3 == 0) ^ (i % 5 == 0)) {
                runningTotal += i;
            }
        }
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(runningTotal),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        int minBound = (sumRange * sumRange) / 6;
        int maxBound = (sumRange * sumRange) / 4;
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextInt(maxBound - minBound) + minBound),
                "some incorrect feedback");
    }
}
