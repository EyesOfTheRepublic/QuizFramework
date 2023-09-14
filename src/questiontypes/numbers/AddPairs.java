package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generate a question containing a list of numbers (as an array that can be cut-and-pasted) in which
 * every number can be added to another number in that list to make a target number - except for one.
 *
 * The point of the question is to identify that one number
 *
 * The number that the pairs sum to is generated from a constant that by default has a value  in the
 * range of MAX_VALUE / 6 to MAX_VALUE / 3.
 *
 * The number of numbers in the list is between the standard question-set wide constants MIN_VAL and MAX_VAL
 */
public class AddPairs extends Question {

    //The maximum value of any number in the list of 'pairs'
    private static final int LIM_VAL = CoreData.LIM_VAL / 2;
    //The minimum value of any number in the list of 'pairs'
    private static final int MIN_LIST_VAL = 12121;


    private int sumTarget; //The number the 'pairs' must sum to
    private int numPairs; //The number of 'pairs'
    private int correctAns; //The correct answer (which does not sum to any of the others to make sumTarget

    private ArrayList<Integer> listOfPairs = new ArrayList<>();

   @Override
    public String createQuestionTitle() {
        return "How Many Pairs?";
    }

    public String createQuestionText() {
        String qText = "In the following list of numbers, every number EXCEPT ONE can be added to another number "
                + "in the list to make " + sumTarget + ". What is the position of that number in the list? <pre>int listNums[] = {";

        for (int j = 0; j < listOfPairs.size() - 1; j++) {
            qText += listOfPairs.get(j) + ", ";
        }
        qText += listOfPairs.get(listOfPairs.size() - 1) + "};</pre>";
        return qText;
    }

    @Override
    public void createCalcData() {
        //Generate the number the pairs sum to, and the number of pairs in the list
        sumTarget = QuizUtils.genRandomInt(LIM_VAL, LIM_VAL * 2);
        numPairs = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        /*Generate the numbers - create a random number between the minimum acceptable (MIN_LIST_VAL) and the
        sum target minus MIN_LIST_VAL. Provided that number is not already in the list, add it and it's value
        minus sumTarget
        */
        for (int i = 0; i < numPairs; i++) {
            int splitPoint = QuizUtils.genRandomInt(MIN_LIST_VAL, sumTarget - MIN_LIST_VAL);
            if (!listOfPairs.contains(splitPoint)) {
                listOfPairs.add(splitPoint);
                listOfPairs.add(sumTarget - splitPoint);
            }
        }

        //Generate the correct answer - one that does not sum to any of others to make the target value
        correctAns = genCorrectAnswer(listOfPairs);
        listOfPairs.add(correctAns);

        Collections.shuffle(listOfPairs);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(correctAns));
    }

    //Question generation automatically handles checking that wrong answers are unique and don't match the correct one
    @Override
    public Answer createIncorrectAnswer() {
        int index = ThreadLocalRandom.current().nextInt(listOfPairs.size());
        return Answer.makeIncorrectAnswer(Integer.toString(listOfPairs.get(index)));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
       int ans = Integer.parseInt(answer.getAnswer());

       for(int i = 0; i < listOfPairs.size(); i++) {
           int candidate = listOfPairs.get(i);
           boolean found = false;
           for (int j = 0; j < listOfPairs.size(); j++) {
               if (i != j) {
                   if (candidate + listOfPairs.get(j) == sumTarget) {
                       found = true;
                       break;
                   }
               }
           }
           if (!found) {
               return candidate == ans;
           }
       }
        return false;
    }

    //Generate  number in the required range that doesn't sum with any other numbers in the list to make the target
    private int genCorrectAnswer(ArrayList<Integer> curList) {
        int target;
        boolean done;

        do {
            done = true;
            target = QuizUtils.genRandomInt(LIM_VAL / 2, LIM_VAL);
            for (int elt : curList) {
                if (elt == target || elt + target == sumTarget) {
                    done = false;
                }
            }
        } while (!done);
        return target;
    }

}
