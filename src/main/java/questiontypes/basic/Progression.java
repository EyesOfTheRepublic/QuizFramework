package questiontypes.basic;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.stream.IntStream;

/**
 * What is the value of after n steps from a starting value m when applying the Collatz sequence function?
 * The following code is suitable for use in autograder.
 * <pre>
public class Test {

    public static void main(String[] args) {
        Progression.threshold = 205;

        int givenAns = Progression.answer();
        System.exit((givenAns == progression(Progression.threshold)) ? 0 : 1);
    }

    public static long progression(int threshold) {

        int runningTotal = 0;
        int counter = 2;
        do {
            runningTotal += counter * 3;
            counter ++;
        } while(runningTotal <= threshold);
        return counter - 1;
    }
}
 * </pre>
 */

public class Progression extends McqQuestion {

    private int threshold;

    private int finalVal;

    @Override
    public String createQuestionTitle() {
        return "Numeric Progression";
    }

    @Override
    public String createQuestionText() {
        String questionText = "**Numeric Progression.** What is the value of n when the sum of the sequence 2 * x + 3 * x + ... n * x "
                + "become greater than threshold, where threshold = " + threshold + " ? "
                + "For example, if threshold = 40, then n = 5 because (2 * 3) + (3 * 3) + (4 * 3) + (5 * 3) = 42, which is the "
                + "first value in the sequence that is > 40.";
        final StringBuilder builder = new StringBuilder(questionText).append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final StringBuilder code = CodeUtils.questionCode("Progression",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int threshold = %d;",
                        threshold))), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        //Generate the factor and the number of correct and incorrect ones in the generated list
        threshold = QuizUtils.genRandomInt(70, 200);
        int runningTotal = 0;
        int i = 2;
        while (runningTotal <= threshold) {
            runningTotal += i * 3;
            i++;
        }
        finalVal = i - 1;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(finalVal));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {

        int result = IntStream.iterate(2, i -> i + 1)
                .filter(k ->
                        3 * ((k * (k + 1) / 2) - 1) > threshold)
                .findFirst()
                .orElseThrow();

        return result == Integer.parseInt(answer.getQuestionAnswer());
    }

    @Override
    public Answer createIncorrectAnswer() {

        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(QuizUtils.genRandomInt(Math.max(finalVal - 5, 6), finalVal + 15)),
                "some incorrect feedback");
    }
}
