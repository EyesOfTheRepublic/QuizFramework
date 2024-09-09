package questiontypes.simpleexamples;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Purely used for a demo video to show issue in c&p long strings
 */
public class OddNumberExample extends McqQuestion {

    private final List<Integer> dataSet = new ArrayList<>();
    private int oddCount = 0;

    @Override
    public String createQuestionTitle() {
        return "Odd numbers";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Odd Numbers.** How many numbers in the following array are odd?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Integer> formatter = new ArrayFormatter<>("public static int[] possOdd", dataSet);
        final StringBuilder code = CodeUtils.questionCode("Odds", formatter.format(2), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        for(int i = 0; i < 50; i++) {
            dataSet.add(i);
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
