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
 * Generate a question containing a list of numbers (as an array that can be cut-and-pasted) in which
 * every number can be added to another number in that list to make a target number - except for one.
 * <p>
 * The point of the question is to identify that one number
 * <p>
 * The number that the pairs sum to is generated from a constant that by default has a value  in the
 * range of MAX_VALUE / 6 to MAX_VALUE / 3.
 * <p>
 * The number of numbers in the list is between the standard question-set wide constants MIN_VAL and MAX_VAL
 * This version is a numeric question (not an MCQ) and is suitable for use with autograder. Suitable code is:
 <pre>
 public class Test {
    public static void main(String[] args) {
        for(int candidate : AddPairs.numList) {
            boolean notFound = true;
            for(int sum : AddPairs.numList) {
                if (sum + candidate == AddPairs.pairSum) {
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                System.exit(candidate == AddPairs.answer() ? 0 : 1);
            } else {
                System.exit(1);
            }
        }
    }
}
 </pre>
 */
public class AddPairs extends NumericQuestion {

    //The maximum value of any number in the list of 'pairs'
    private static final int LIM_VAL = CoreData.LIM_VAL / 2;
    //The minimum value of any number in the list of 'pairs'
    private static final int MIN_LIST_VAL = 12121;


    private int sumTarget; //The number the 'pairs' must sum to
    private int correctAns; //The correct answer (which does not sum to any of the others to make sumTarget

    private final ArrayList<Integer> numList = new ArrayList<>();

    @Override
    public String createQuestionTitle() {
        return "How Many Pairs?";
    }

    public String createQuestionText() {
        StringBuilder builder = new StringBuilder("In the following list of numbers, every number EXCEPT ONE can be added to another number "
                + "in the list to make " + sumTarget
                + ". What is that number? It is guaranteed that all numbers in the list are unique.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        ArrayFormatter<Integer> formatter = new ArrayFormatter<>("public static int[] numList", numList);
        final String code = String.format(CodeUtils.CODE_FRAMEWORK, "AddPairs",
                formatter.format(2)
                        .append(CodeUtils
                                .indentTextBlock(String.format("public final static int pairSum = %s;",sumTarget), 1)));
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        //Generate the number the pairs sum to, and the number of pairs in the list
        sumTarget = QuizUtils.genRandomInt(LIM_VAL, LIM_VAL * 2);
        final int numPairs = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        /*Generate the numbers - create a random number between the minimum acceptable (MIN_LIST_VAL) and the
        sum target minus MIN_LIST_VAL. Provided that number is not already in the list, add it and it's value
        minus sumTargetVal
        */
        int i = 0;
        while (i < numPairs) {
            int splitPoint = QuizUtils.genRandomInt(MIN_LIST_VAL, sumTarget - MIN_LIST_VAL);
            //check it's not present and it won't mean adding itself twice
            if (!numList.contains(splitPoint) && splitPoint / 2 != sumTarget) {
                numList.add(splitPoint);
                numList.add(sumTarget - splitPoint);
                i++;
            }
        }

        //Generate the correct answer - one that does not sum to any of others to make the target value
        correctAns = genCorrectAnswer(numList);
        numList.add(correctAns);

        Collections.shuffle(numList);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(correctAns));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        int ans = Integer.parseInt(answer.getQuestionAnswer());

        for (int i = 0; i < numList.size(); i++) {
            int candidate = numList.get(i);
            boolean found = false;
            for (int j = 0; j < numList.size(); j++) {
                if (i != j && candidate + numList.get(j) == sumTarget) {
                    found = true;
                    break;
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
                    break;
                }
            }
        } while (!done);
        return target;
    }

}
