package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Checking how many numbers in a list have a specfic number as a factor
 */

public class Factors extends Question {

    private int ansFactor;
    private final ArrayList<Integer> listOfPosFactors = new ArrayList<>();

    private int numCorrect;

    @Override
    public String createQuestionTitle() {
        return "How many factors?";
    }

    @Override
    public String createQuestionText() {
        StringBuilder questionText = new StringBuilder("How many numbers in the following sequence have "
                + ansFactor + " as a factor?");

        ArrayFormatter<Integer> formatter = new ArrayFormatter<>("int[] numbers", listOfPosFactors);
        return questionText.append(Utils.toCodeBlock(formatter.format())).toString();
    }

    @Override
    public void createCalcData() {
        //Generate the factor and the number of correct and incorrect ones in the generated list
        ansFactor = Utils.genRandomInt(CoreData.MULT_LIM, CoreData.MULT_LIM * 2);
        numCorrect = Utils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        int numIncorrect = Utils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Create the numbers that have ansFactor as a factor
        for (int i = 0; i < numCorrect; i++) {
            int multFactor;
            multFactor = Utils.genRandomInt(CoreData.MULT_LIM, CoreData.MULT_LIM * 2);
            int correctAnswer = ansFactor * multFactor;
            listOfPosFactors.add(correctAnswer);
        }

        //Generate the ones that do not have ansFactor as a factor
        int j = 0;
        do {
            int candidate = Utils.genRandomInt(CoreData.LIM_VAL, CoreData.LIM_VAL * 2);
            if( candidate % ansFactor != 0) {
                listOfPosFactors.add(Utils.genRandomInt(CoreData.LIM_VAL, CoreData.LIM_VAL * 2));
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
    public Answer createIncorrectAnswer() {
        int candidate = Utils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        //listOfPosFactors.size() won't be > MAX_NUM for current values but just in case...
        return Answer.makeIncorrectAnswer(Integer.toString(Math.min(candidate, listOfPosFactors.size())));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        int factorCount = 0;
        for(int elt: listOfPosFactors) {
            if (elt % ansFactor == 0) {
                factorCount ++;
            }
        }

        return factorCount == Integer.parseInt(answer.getAnswer());
    }
}
