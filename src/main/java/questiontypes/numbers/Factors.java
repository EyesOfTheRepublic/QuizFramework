package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import quizframework.Answer;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Checking how many numbers in a list have a specific number as a factor - version that is suitable for use with
 * autograder. Code suitable for checking correctness on autograder:
 <pre>
 import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        long count = IntStream.of(Factors.numbers)
        .filter(x -> (x % Factors.factor) == 0).count();

        System.exit(count == Factors.answer() ? 0 : 1);
    }
}
 </pre>
 */

public class Factors extends NumericQuestion {

    private int ansFactor;
    private final ArrayList<Integer> listOfPosFactors = new ArrayList<>();

    private int numCorrect;

    @Override
    public String createQuestionTitle() {
        return "How many factors?";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("How many numbers in the following sequence have "
                + ansFactor + " as a factor?").append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final ArrayFormatter<Integer> formatter =
                new ArrayFormatter<>("public static int[] numbers", listOfPosFactors);
        final StringBuilder code = CodeUtils.questionCode( "Factors",
                formatter.format(2).append(CodeUtils
                        .indentTextBlock(String.format("public static int factor = %s;",ansFactor))), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        //Generate the factor and the number of correct and incorrect ones in the generated list
        ansFactor = QuizUtils.genRandomInt(CoreData.MULT_LIM, CoreData.MULT_LIM * 2);
        numCorrect = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        int numIncorrect = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Create the numbers that have ansFactor as a factor
        for (int i = 0; i < numCorrect; i++) {
            int multFactor;
            multFactor = QuizUtils.genRandomInt(CoreData.MULT_LIM, CoreData.MULT_LIM * 2);
            int correctAnswer = ansFactor * multFactor;
            listOfPosFactors.add(correctAnswer);
        }

        //Generate the ones that do not have ansFactor as a factor
        int j = 0;
        do {
            int candidate = QuizUtils.genRandomInt(CoreData.LIM_VAL, CoreData.LIM_VAL * 2);
            if( candidate % ansFactor != 0) {
                listOfPosFactors.add(QuizUtils.genRandomInt(CoreData.LIM_VAL, CoreData.LIM_VAL * 2));
                j++;
            }
        } while (j < numIncorrect);

        Collections.shuffle(listOfPosFactors);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(numCorrect));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        int factorCount = 0;
        for(int elt: listOfPosFactors) {
            if (elt % ansFactor == 0) {
                factorCount ++;
            }
        }

        return factorCount == Integer.parseInt(answer.getQuestionAnswer());
    }
}
