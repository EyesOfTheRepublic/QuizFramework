package questiontypes.simplequestions;/*
A relatively simple question - find the sum of squares from 1 to n.
 */
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class SumSquareQuestion extends McqQuestion {

    private final Random rnd = new Random();

    private int numSquares;

    @Override
    public String createQuestionTitle() {
        return "Sum of Squares";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Squares.** What is the sum of squares from 1 to "
                + numSquares + "? For example, the sum of squares from 1 to 3 is (1 * 1) + (2 * 2) + (3 * 3) = 14.")
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
        return 5;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        Map<String, Integer> integers;
        return IntStream.rangeClosed(1, numSquares).mapToLong(i -> (long)i * i).sum()
                == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        int runningTotal = 0;
        for(int i = 1; i <= numSquares; i++) {
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
