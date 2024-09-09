package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.FibSequence;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Identify the number of Fibonacci numbers is a list - version suitable for use with autograder.
 * Use this code in autograder:
 <pre>
 import java.util.stream.LongStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class Test {
    public static final long[] FIB_ARRAY = {
       //Contents of FIB_ARRAY in {@link FibSequence} goes here
    };

    public final static long[] posFibNumbers = {
                        4807526976L, 53316291173L, 14472334024676221L, 591286729879L, 3416454622906707L,
                        27777890035310L, 8944394323791490L, 956722026057L, 514229L, 10610209857723L,
                        267914316L, 2111485077978050L, 2971215092L, 9227465L, 7778742066L, 7778742073L,
                        117669030461014L, 3524578L, 39088196L, 17167680177593L, 190392490709159L,
                        433494437L, 3416454622906732L, 61305790721611591L, 832069L, 37889062373143921L,
                        37889062373143906L, 6557470319842L, 701408750L, 7778742077L, 1836311903L,
                        2178333L, 2504730781961L, 10964L, 20365011074L, 32951280126L, 63246009L,
                        832040L, 121393L, 2111485077978070L, 61305790721611618L, 225851433717L,
                        7778742049L, 44945570212879L, 190392490709135L, 139583862445L, 4807526993L,
                        10946L, 37889062373143932L, 267914296L, 39088169L, 86267571272L, 4052739537901L,
                        5702887L, 1346289L, 1346269L
                };

    public static void main(String[] args) {

        List<Long> fibList = Arrays.stream(FIB_ARRAY).boxed().collect(Collectors.toList());
        Fibonacci.posFibNumbers = posFibNumbers;

        long count = LongStream.of(Fibonacci.posFibNumbers)
        .filter(x -> fibList.contains(x)).count();
        System.exit(count == Fibonacci.answer() ? 0 : 1);
    }
}
 </pre>
 */
public class Fibonacci extends NumericQuestion {

    private static final int FAKE_FIB_OFFSET = 15;

    private final ArrayList<Long> fibList = new ArrayList<>();
    private final ArrayList<Long> baseList = new ArrayList<>();
    private final ArrayList<Long> fakeFibs = new ArrayList<>();
    private final ArrayList<Long> questionList = new ArrayList<>();

    private int numCorrect;

    @Override
    public String createQuestionTitle() {
        return "How Many Fibonacci Numbers?";
    }

    @Override
    public String createQuestionText() {

        final StringBuilder builder = new StringBuilder("""
                **Fibonacci.** How many of the numbers in the following list are Fibonacci numbers? It is ESSENTIAL that you use long
                for the Fibonacci numbers you calculate and NOT int.""")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final ArrayFormatter<Long> formatter = new ArrayFormatter<>("public final static long[] posFibNumbers", questionList) {
            @Override
            public String outputItem(final Long item) {
                return item + "L";
            }
        };
        final StringBuilder code = CodeUtils.questionCode("Fibonacci", formatter.format(2), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }


    @Override
    public void createCalcData() {
        //Copy the list of fibonacci numbers from FibSequence into two array lists - one to provide the data
        //for the question; the other just to check if a random number is a fibonacci number (easier with an array list
        for (long elt : FibSequence.FIB_ARRAY) {
            fibList.add(elt);
            baseList.add(elt);
        }

        numCorrect = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        final int numWrong = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

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
                        + QuizUtils.genRandomInt(FAKE_FIB_OFFSET, FAKE_FIB_OFFSET * 2);
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
    public boolean checkAnswer(final Answer answer) {
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
        return fibCount == Integer.parseInt(answer.getQuestionAnswer());
    }
}
