package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PrimeData;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Sophie Germain prime is a prime number p such that 2p + 1 is also prime. The question is about how many primes
 * in a list of primes are Sophie Germain primes.
 * This version requires a specific code format and is suitable for autograder (but is still an MCQ).
 * Here's the code for autograder.
 <pre>
 public class Test {
    public static void main(String[] args) {
        int count = 0;
        for(int sg : SophieGermain.primes) {
            if (isSg(sg)) {
                count++;
            }
        }
        System.exit(count == SophieGermain.answer() ? 0 : 1);
    }

    private static boolean isSg(int p) {
        int factors = 0;
        final int n = 2 * p + 1;

        for(int i = 2; i < n; i++){
            if(n % i == 0) {
                factors++;
            }
        }

        return factors == 0;
    }
}
 </pre>>
 */
public class SophieGermain extends McqQuestion {

    private static final int MAX_ANS = 8; //A plausible maximum value for the wrong answers

    private int numGermainPrimes;
    private final ArrayList<Integer> primeList = new ArrayList<>();
    private final ArrayList<Integer> dataSet = new ArrayList<>();

    @Override
    public String createQuestionTitle() {
        return "How Many Sophie Germain Primes?";
    }

    @Override
    public String createQuestionText() {
        ArrayFormatter<Integer> formatter = new ArrayFormatter<>("public static int[] primes", dataSet);

        final StringBuilder builder = new StringBuilder("""
                A Sophie Germain prime number is a prime number p where 2 * p + 1 is
                also prime. How many of the following are Sophie Germain numbers?
                """)
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String code = String.format(CodeUtils.CODE_FRAMEWORK, "SophieGermain", formatter.format(2));
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        numGermainPrimes = 0;
        final int numPrimes = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Copy the list of primes
        for(int elt : PrimeData.PRIMES) {
            primeList.add(elt);
        }

        //Randomly pick a number of primes from the list, deleting from the base list to avoid duplication
        //Check if each one is a Sophie Germain prime
        for(int i = 0; i < numPrimes; i++) {
            int loc = ThreadLocalRandom.current().nextInt(primeList.size());
            int val = primeList.get(loc);
            dataSet.add(val);
            if (isPrime(val * 2 + 1)) {
                numGermainPrimes ++;
            }
            primeList.remove(loc);
        }
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(numGermainPrimes));
    }

    @Override
    public Answer createIncorrectAnswer() {
        //Note zero is a plausible answer to this question.
        return Answer.makeIncorrectAnswer(Integer.toString(ThreadLocalRandom.current().nextInt(MAX_ANS)));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        int count = 0;
        for (int elt: dataSet) {
            boolean sg = true;
            for (int i = 2; i < elt; i++) {
                if ((2 * elt + 1) % i == 0) {
                    sg = false;
                    break;
                }
            }
            if (sg) {
                count++;
            }
        }
        return count == Integer.parseInt(answer.getQuestionAnswer());
    }

    private static boolean isPrime(int n){
        int factors = 0;

        //Check for factors skipping 1 and n
        for(int i = 2; i < n; i++){
            if(n % i == 0)
                factors++;
        }

       return factors == 0;
    }
}
