package questiontypes.simpleexamples;/*
A (trivial) multiplication question - asks what is the product of two (random) numbers. Generates one correct and a set of random
incorrect answers.
 */
import quizframework.Answer;
import quizframework.Question;

import java.util.Random;

public class MultQuestionExample extends Question {

    private final Random rnd = new Random();

    private long val1;
    private long val2;

    public String createQuestionTitle() {
        return "Multiplying Numbers";
    }
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

    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(val1 * val2),
                "some correct feedback");
    }

    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextInt(30)),
                "some incorrect feedback");
    }
}
