
import java.util.Random;

public class SquareQuestionExample extends Question {

    private final Random rnd = new Random();

    @Override
    public String createQuestionTitle() {
        return "Squaring Numbers";
    }

    @Override
    public String createQuestionText() {
        Seed<Integer> item = getQuizDataItem("number");
        int seedVal = item.get();
        return "What is the square of " + seedVal + " ?";
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

    public void createDataSeeds() {
        Seed<Integer> val1 = new Seed<>(rnd.nextInt(15));
        addQuizDataItem("number", val1);
    }

    @Override
    public int createQuestionPoints() {
        return 3;
    }

    @Override
    public Answer createCorrectAnswer() {
        Seed<Integer> item = getQuizDataItem("number");
        int seedVal = item.get();
        return Answer.makeCorrectAnswerWithFeedback(Integer.toString(seedVal * seedVal),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Integer.toString(rnd.nextInt(20)),
                "some incorrect feedback");
    }
}
