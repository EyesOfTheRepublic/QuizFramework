package questiontypes.verybasic;/*
Simple example question asking what is the square of a (random) number
 */
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Random;

public class SquareQuestionExample extends McqQuestion {

    private final Random rnd = new Random();

    private int number;

    @Override
    public String createQuestionTitle() {
        return "Squaring Numbers";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Squares.** What is the square of " + number + " ?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("Square",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int numToSquare = %d;", number))),
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

    public void createCalcData() {
        number = rnd.nextInt(15) + 5;
    }

    @Override
    public int createQuestionPoints() {
        return 3;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Integer.toString(number * number),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Integer.toString(rnd.nextInt(20)),
                "some incorrect feedback");
    }
}
