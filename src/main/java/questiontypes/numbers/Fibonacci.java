package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.FibSequence;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Identify the number of Fibonacci numbers is a list
 */
public class Fibonacci extends Question {

    private static final int FAKE_FIB_OFFSET = 15;

    private ArrayList<Long> fibList = new ArrayList<>();
    private ArrayList<Long> baseList = new ArrayList<>();
    private ArrayList<Long> fakeFibs = new ArrayList<>();
    private ArrayList<Long> questionList = new ArrayList<>();

    private int numCorrect;

    @Override
    public String createQuestionTitle() {
        return "How Many Fibonacci Numbers?";
    }

    @Override
    public String createQuestionText() {

        StringBuilder questionText = new StringBuilder("""
                How many of the numbers in the following list are Fibonacci numbers? It is ESSENTIAL that you use long
                for the Fibonacci numbers you calculate and NOT int.""");

        ArrayFormatter<Long> formatter = new ArrayFormatter<Long>("long[] posFibNumbers", questionList) {
            @Override
            public String outputItem(final Long item) {
                return item + "L";
            }
        };
        return questionText.append(Utils.toCodeBlock(formatter.format())).toString();
    }


    @Override
    public void createCalcData() {
        //Copy the list of fibonacci numbers from FibSequence into two array lists - one to provide the data
        //for the question; the other just to check if a random number is a fibonacci number (easier with an array list
        for (long elt : FibSequence.FIB_ARRAY) {
            fibList.add(elt);
            baseList.add(elt);
        }

        numCorrect = Utils.genRandomInt( CoreData.MIN_NUM, CoreData.MAX_NUM);
        final int numWrong = Utils.genRandomInt( CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Copy actual fibonaacci numbers to the list for the question, deleting them from the source to avoid duplicates
        for (int i = 0; i < numCorrect; i++) {
            int index = ThreadLocalRandom.current().nextInt(fibList.size());
            questionList.add(fibList.get(index));
            fibList.remove(index);
        }

        //Generate fake but plausible "fibonacci" numbers
        for (int j = 0; j < numWrong; j++) {
            int fakeFibIndex;
            long fakeFib;
            do {
                //Pick a random fibonacci number and 'adjust' it by a random amount
                fakeFibIndex = ThreadLocalRandom.current().nextInt(FibSequence.FIB_ARRAY.length);
                fakeFib = FibSequence.FIB_ARRAY[fakeFibIndex]
                        + Utils.genRandomInt(FAKE_FIB_OFFSET, FAKE_FIB_OFFSET * 2);
                //If it's negative, also a fibonacci number, or in the list of fibonacci numbers for the question, skip it
            } while (fakeFib < FibSequence.FIB_ARRAY[0]
                    || fakeFibs.contains(fakeFib)
                    || baseList.contains(fakeFib));
            //Otherwise, add it to the list of fake numbers
            fakeFibs.add(fakeFib);
        }

        questionList.addAll(fakeFibs);
        Collections.shuffle(questionList);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Long.toString(numCorrect));
    }

    @Override
    public Answer createIncorrectAnswer() {
        int candidate = Utils.genRandomInt( CoreData.MIN_NUM, CoreData.MAX_NUM);
        return Answer.makeIncorrectAnswer(Integer.toString(Math.min(candidate, questionList.size())));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        int fibCount = 0;
        for (long elt : questionList) {
            long f1 = 1;
            long f2 = 1;
            while (f2 <= elt) {
                long temp = f2;
                f2 += f1;
                f1 = temp;
            }
            if (f1 == elt) {
                fibCount++;
            }
        }
        return fibCount == Integer.parseInt(answer.getAnswer());
    }
}
