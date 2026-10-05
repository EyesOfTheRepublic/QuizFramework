package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.stream.IntStream;

/**
 * What is the sum of numbers from 1 to n divisible by 3 or 5, but not by both.
 * Code below suitable for use in autograder
 <pre>
public class Test {

    public static void main(String[] args) {
        SumFour.sumRange = 205;

        int givenAns = SumFour.answer();
        System.exit((givenAns == sumFour(SumFour.sumRange)) ? 0 : 1);
    }

    public static long sumFour(int range) {
        int total = 0;
        for(int i = 1; i <= range; i++) {
            if (i % 4 == 0) {
                total += i;
            }
        }
        return total;
    }
}
</pre>
 */

public class SumMultiplesFour extends McqQuestion {

    private int sumRange;

    @Override
    public String createQuestionTitle() {
        return "Sum of Multiples of Four";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Multiples of Four.**"
                + "What is the sum of numbers from 1 to n (inclusive) that are multiples of 4, where n= "
                + sumRange + "? For example, the sum from 1 to 19 would be 4 + 8 + 12 + 16 = 40. ")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("SumFour",
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
        do {
            sumRange = QuizUtils.genRandomInt(17, 37);
        } while (sumRange == 19);
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        return IntStream.rangeClosed(1, sumRange)
                .filter(i -> (i % 4 == 0) )
                .sum() == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        int runningTotal = 0;
        for (int i = 1; i <= sumRange; i++) {
            if (i % 4 == 0) {
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
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(QuizUtils.genRandomInt(minBound, maxBound)),
                "some incorrect feedback");
    }
}
