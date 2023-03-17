
import java.util.Random;

public class MultQuestionExample extends Question {

    private final Random rnd = new Random();

    public String createQuestionTitle() {
        return "Multiplying Numbers";
    }
    public String createQuestionText() {
        CalcData<Integer> item1 = getQuizDataItem("number1");
        int seedVal1 = item1.get();
        CalcData<Integer> item2 = getQuizDataItem("number2");
        int seedVal2 = item2.get();
        return "What is " + seedVal1 + " * " + seedVal2 + " ?";
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
        CalcData<Integer> val = new CalcData<Integer>(rnd.nextInt(15));
        addQuizDataItem("number1", val);
        CalcData<Integer> val2 = new CalcData<Integer>(rnd.nextInt(15));
        addQuizDataItem("number2", val2);
    }

    public int createQuestionPoints() {
        return 5;
    }

    public Answer createCorrectAnswer() {
        CalcData<Integer> item1 = getQuizDataItem("number1");
        int seedVal1 = item1.get();
        CalcData<Integer> item2 = getQuizDataItem("number2");
        int seedVal2 = item2.get();
        //int seedVal = (int)getSeedItem("number").get();
        return Answer.makeCorrectAnswerWithFeedback(Long.toString(seedVal1 * seedVal2),
                "some correct feedback");
    }

    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextInt(20)),
                "some incorrect feedback");
    }
}
