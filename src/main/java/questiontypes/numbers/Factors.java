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
    public static int[] numbers = {
                        904247591, 963023295, 707956470, 1319534347, 1173670849, 1056508856, 987162072,
                        802606510, 1081104480, 798923814, 1404298695, 995500506, 1214500238, 1190311850,
                        1187079138, 1208454658, 1060922226, 946823631, 1391650636, 1129199656,
                        1178824086, 1134604522, 741918096, 1429934816, 1410499091, 1169035995,
                        813065868, 744620069, 1269555133, 742961589, 760489642, 1159348537, 1049265737,
                        666055416, 1114635535, 684383882, 933038340, 717255243, 913808264, 965048253,
                        742276912, 891207431, 1317889406, 1338863725, 1290489412, 1059831864, 1043995654,
                        956161626, 673713911, 815175400, 1079899717, 938957448, 1394427680, 604268236,
                        1088795335, 918367631, 733500703, 1178395751, 922670083, 1166406084, 1016844437,
                        1048538829, 1171905501, 1137507176, 1043891810, 839696461
                };
    public static int factor = 25961;

    public static void main(String[] args) {
        Factors.numbers = numbers;
        Factors.factor = factor;

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
        final StringBuilder builder = new StringBuilder("**Factors.** How many numbers in the following sequence have "
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
