package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Calculate the sum of squares from 1 to n
 * The following code is suitable for use in autograder
 <pre>
public class Test {

    public static final int numSquares = 9;

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= numSquares; i++) {
            sum += i * i;
        }
        SumSquares.numSquares = numSquares;
        System.exit(SumSquares.answer() == sum ? 0 : 1);
    }
}
 
 </pre>
 */

public class SumSquareQuestion extends McqQuestion {

    private final Random rnd = new Random();

    private int numSquares;

    @Override
    public String createQuestionTitle() {
        return "Sum of Squares";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Squares.** What is the sum of squares from 1 to n, where n="
                + numSquares + "? For example, the sum of squares from 1 to 3 is (1 * 1) + (2 * 2) + (3 * 3) = 14. YOUR CODE MUST"
                + " NOT hard code the value n=" + numSquares + " because it will be replaced by a different value in testing.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("SumSquares",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int numSquares = %d;", numSquares))),
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
        numSquares = rnd.nextInt(12) + 5;
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        return IntStream.rangeClosed(1, numSquares).mapToLong(i -> (long) i * i).sum()
                == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        int runningTotal = 0;
        for (int i = 1; i <= numSquares; i++) {
            runningTotal += i * i;
        }
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(runningTotal),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextInt(200)),
                "some incorrect feedback");
    }
}
