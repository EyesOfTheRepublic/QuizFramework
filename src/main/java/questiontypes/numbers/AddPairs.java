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
    public static int[] numList = {
                        440686007, 454259340, 53590326, 209358344, 352656517, 169432126, 209041101,
                        143787097, 41212669, 312466550, 391709798, 231361804, 182333746, 467334514,
                        41924260, 231710917, 32848238, 475279196, 457739912, 6619480, 14564162,
                        353224942, 364389179, 77097550, 439974416, 63612455, 250536872, 431919549,
                        90188878, 381813762, 418286221, 15393058, 449050438, 478409804, 428308350,
                        247883687, 76232419, 272540332, 129242159, 404801126, 338111579, 199575491,
                        466505618, 222241375, 181661558, 117509497, 250187759, 49979127, 405666257,
                        3488872, 24158764, 389226239, 100084914, 234014989, 282323185, 128673734,
                        259657301, 299564930, 27639336, 272857575, 92672437
                };
    public static int pairSum = 481898676;

    public static void main(String[] args) {
        AddPairs.numList = numList;
        AddPairs.pairSum = pairSum;

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
        return "Which Number?";
    }

    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Add Pairs.** In the array of numbers in the code below, "
                + "every number EXCEPT ONE can be added to another number in the list to make " + sumTarget
                + ". What is that number? It is guaranteed that all numbers in the list are unique.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final ArrayFormatter<Integer> formatter = new ArrayFormatter<>("public static int[] numList", numList);
        final StringBuilder code = CodeUtils.questionCode( "AddPairs",
                formatter.format(2)
                        .append(CodeUtils
                                .indentTextBlock(String.format("public static int pairSum = %s;",sumTarget), 1)),
                "int");
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
