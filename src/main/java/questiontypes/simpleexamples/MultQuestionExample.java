package questiontypes.simpleexamples;/*
A (trivial) multiplication question - asks what is the product of two (random) numbers. Generates one correct and a set of random
incorrect answers.
 */
import quizframework.Answer;
import quizframework.McqQuestion;

import java.util.Random;

public class MultQuestionExample extends McqQuestion {

    private final Random rnd = new Random();

    private long val1;
    private long val2;

    @Override
    public String createQuestionTitle() {
        return "Multiplying Numbers";
    }

    @Override
    public String createQuestionText() {
        return "What is " + val1 + " * " + val2 + " ?";
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
        val1 = rnd.nextInt(15);
        val2 = rnd.nextInt(15);
    }

    @Override
    public int createQuestionPoints() {
        return 5;
    }

    @Override
    public boolean checkAnswer(final Answer ans) {
        return val1 * val2 == Integer.parseInt(ans.getQuestionAnswer());
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(val1 * val2),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextInt(30)),
                "some incorrect feedback");
    }
}
