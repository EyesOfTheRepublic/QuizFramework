
import java.util.Random;

public class SquareQuestionExample extends Question {

    private final Random rnd = new Random();

    private int number;

    @Override
    public String createQuestionTitle() {
        return "Squaring Numbers";
    }

    @Override
    public String createQuestionText() {
        return "What is the square of " + number + " ?";
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
        number = rnd.nextInt(15);
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
