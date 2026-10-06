package questiontypes.basic;

import questiontypes.numbers.utils.CoreData;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.stream.IntStream;

/**
 * Find how many times you have to half a number (rounding down) before reaching zero
 * The following code is suitable for use in autograder
 <pre>
public class Test {

    public static void main(String[] args) {
        HalfToZero.n = 205;

        int givenAns = HalfToZero.answer();
        System.exit((givenAns == halfToZero(HalfToZero.n)) ? 0 : 1);
    }

    public static long halfToZero(int n) {
        int counter = 0;
        int runningTotal = n;
        while(runningTotal > 0) {
            runningTotal /= 2;
            counter++;
        }
        return counter;
    }
}
 </pre>
 */

public class HalfToZero extends McqQuestion {

    private int n;
    private int answerVal;

    @Override
    public String createQuestionTitle() {
        return "Half to Zero";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Half to Zero.** How many steps does it take when halving a starting number, "
        + "n=" + n + " and rounding down each time to reach 0? For example, suppose n=35. Step 1: 17 (35/2 rounded down); "
        + "step 2: 8; step 3: 4; step 4: 2; step 5: 1; step 6: 0 (rounding down). So in this case, six steps ")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("HalfToZero",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int n = %d;", n))),
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
        n = QuizUtils.genRandomInt(CoreData.LIM_VAL, Integer.MAX_VALUE );
        int count = 0;
        int workingVal = n;
        while (workingVal > 0) {
            workingVal /= 2;
            count++;
        }
        answerVal = count;
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        return IntStream.iterate(n, x -> x > 0, x -> x / 2)
                .count()  == Integer.parseInt(ans.getQuestionAnswer());
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
