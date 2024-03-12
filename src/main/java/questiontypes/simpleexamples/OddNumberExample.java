package questiontypes.simpleexamples;

import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

import java.util.ArrayList;

/**
 * Purely used for a demo video to show issue in c&p long strings
 */
public class OddNumberExample extends Question {

    private ArrayList<Integer> list = new ArrayList<>();
    private int oddCount = 0;

    @Override
    public String createQuestionTitle() {
        return "Odd numbers";
    }

    @Override
    public String createQuestionText() {
        String val = "How many numbers in the following array are odd?\n```\nint[] nums = {";
        for(int i = 0; i < list.size() - 1; i++) {
            val += i + ", ";
        }
        val += list.get(list.size() - 1) + "};\n```\n'";
        return val;
    }

    @Override
    public void createCalcData() {
        for(int i = 0; i < 50; i++) {
            list.add(i);
            if (i % 2 != 0) {
                oddCount++;
            }
        }
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(oddCount));
    }

    @Override
    public Answer createIncorrectAnswer() {
        final int wrongAnswer = QuizUtils.genRandomInt(0, 25);
        return Answer.makeIncorrectAnswer(Integer.toString(wrongAnswer));
    }
}
