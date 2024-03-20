package questiontypes.simpleexamples;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Purely used for a demo video to show issue in c&p long strings
 */
public class OddNumberExample extends McqQuestion {

    private final List<Integer> list = new ArrayList<>();
    private int oddCount = 0;

    @Override
    public String createQuestionTitle() {
        return "Odd numbers";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder val =
                new StringBuilder("How many numbers in the following array are odd?\n```\nint[] nums = {");
        for(int i = 0; i < list.size() - 1; i++) {
            val.append(i).append(", ");
        }
        val.append(list.get(list.size() - 1)).append("};\n```\n'");
        return val.toString();
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
