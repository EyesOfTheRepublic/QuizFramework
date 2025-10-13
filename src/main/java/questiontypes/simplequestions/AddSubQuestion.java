package questiontypes.simplequestions;/*
A relatively simple question - find the sum of numbers 2i - i for i = 1 to n.
 */
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class AddSubQuestion extends McqQuestion {

    private final Random rnd = new Random();

    private int maxVal;

    @Override
    public String createQuestionTitle() {
        return "Sum of Numbers";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Sum of Numbers.** What is the sum of 2 * i - 1 for i = 1 to "
                + maxVal + "? For example, for n = 3 it is (2 * 1 - 1) + (2 * 2 - 1) + (2 * 3 - 1) = 1 + 3 + 5 = 10")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("SumNumbers",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int sumNumbers = %d;", maxVal))),
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
        maxVal = rnd.nextInt(12) + 6;
    }

    @Override
    public int createQuestionPoints() {
        return 5;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        Map<String, Integer> integers;
        return IntStream.rangeClosed(1, maxVal).mapToLong(i -> (long)2 * i - 1).sum()
                == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        int runningTotal = 0;
        for(int i = 1; i <= maxVal; i++) {
            runningTotal += 2 * i - 1;
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
