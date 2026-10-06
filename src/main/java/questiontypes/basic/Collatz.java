package questiontypes.basic;

import questiontypes.numbers.utils.CoreData;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.function.LongUnaryOperator;
import java.util.stream.IntStream;

/**
 * What is the value of after n steps from a starting value m when applying the Collatz sequence function?
 * The following code is suitable for use in autograder.
 * <pre>
public class Test {

    public static void main(String[] args) {
        Collatz.startingVal = 31;
        Collatz.steps = 25;

        long givenAns = Collatz.answer();
        System.exit((givenAns == collatz(Collatz.startingVal, Collatz.steps)) ? 0 : 1);
    }

    public static long collatz(int startingVal, int steps) {

        return java.util.stream.IntStream.range(0, steps)
                .boxed()
                .reduce(
                        startingVal,
                        (n, ignored) -> (n % 2 == 0) ? n / 2 : 3 * n + 1,
                        (a, b) -> b
                );
    }
}
 * </pre>
 */

public class Collatz extends McqQuestion {

    private int startingVal;
    private int steps;

    private int finalVal;

    @Override
    public String createQuestionTitle() {
        return "Collatz Sequence";
    }

    @Override
    public String createQuestionText() {
        String questionText = "**Collatz Sequence.** What is the result of applying the following operation "
                + steps + " times from a starting value of " + startingVal + "?"
                + " If value is even, divide it by two; if the value is odd multiply it by three and add one."
                + " for example, if the starting value was 5 and the number of times was 2: step 1 - starting value is odd so set it to"
                + " 16 (5*3+1); step 2 - value is now even so set it to 8 (16/2) - so final value is 8 after two steps.";
        final StringBuilder builder = new StringBuilder(questionText).append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final StringBuilder code = CodeUtils.questionCode("Collatz",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int startingVal = %d;\npublic static int steps = %d;",
                        startingVal, steps))), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    private int collatz(int startingVal, int steps) {
        int value = startingVal;

        //Create the numbers that have ansFactor as a factor
        for (int i = 0; i < steps; i++) {
            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = value * 3 + 1;
            }
        }
        return value;
    }

    @Override
    public void createCalcData() {
        //Generate the factor and the number of correct and incorrect ones in the generated list
        startingVal = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        steps = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        finalVal = collatz(startingVal, steps);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(finalVal));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        LongUnaryOperator collatz =
                n -> (n % 2 == 0) ? n / 2 : 3 * n + 1;

        LongUnaryOperator repeated =
                IntStream.range(0, steps)
                        .mapToObj(i -> collatz)
                        .reduce(
                                LongUnaryOperator.identity(),
                                LongUnaryOperator::andThen
                        );

        long result = repeated.applyAsLong(startingVal);

        return result == Integer.parseInt(answer.getQuestionAnswer());
    }

    @Override
    public Answer createIncorrectAnswer() {
        int falseStart = startingVal + QuizUtils.genRandomInt(-3,3);
        falseStart = falseStart <= 2 ? falseStart + QuizUtils.genRandomInt(0,3) : falseStart;
        int falseSteps = steps + QuizUtils.genRandomInt(-3,3);
        falseSteps = falseSteps <= 2 ? falseSteps + QuizUtils.genRandomInt(0,3) : falseSteps;

        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(collatz(falseStart, falseSteps)),
                "some incorrect feedback");
    }
}
