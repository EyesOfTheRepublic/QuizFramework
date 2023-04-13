/*
A (trivial) multiplication question - asks what is the product of two (random) numbers. Generates one correct and a set of random
incorrect answers.
 */
import java.util.Random;

public class MultQuestionExample extends Question {

    private final Random rnd = new Random();

    private int val1;
    private int val2;

    public String createQuestionTitle() {
        return "Multiplying Numbers";
    }
    public String createQuestionText() {
        return "What is " + val1 + " * " + val2 + " ?";
    }

    public String createGeneralFeedback() {
        return "Some generic feedback";
    }

    public String createCorrectFeedback() {
        return "Some feedback for the correct answer";
    }

    public String createIncorrectFeedback() {
        return "Some general feedback for incorrect answers";
    }

    public void createCalcData() {
        val1 = rnd.nextInt(15);
        val2 = rnd.nextInt(15);
    }

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
