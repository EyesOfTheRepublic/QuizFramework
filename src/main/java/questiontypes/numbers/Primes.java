package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PrimeData;
import quizframework.Answer;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This question has a large list of numbers some prime and some not. The question is to identify how many are prime.
 * This version suitable for use with autograder - use this code:
 <pre>
import java.util.stream.IntStream;

public class Test {
    public static final int[] primes = {
            4543, 4699, 4393, 2531, 9151, 10973, 10351, 6917, 7829, 8669, 1471, 9431,
            10885, 7757, 8629, 8317, 7783, 4673, 11323, 6019, 4241, 3319, 8039, 5449,
            5527, 10375, 9589, 9343, 8521, 3359, 4073, 2347, 9235, 5335, 2447, 4477,
            7807, 5897, 8947, 11011, 8281, 9805, 5471, 8233, 6847, 8581, 4843, 7079,
            11173, 10717, 2161, 1511, 7817, 5029, 8077, 10081, 6355, 11117, 9037, 8797
    };

    public static void main(String[] args) {
        Primes.primes = primes;

        long count = IntStream.of(Primes.primes).filter(Test::isPrime).count();
        System.exit(count == Primes.answer() ? 0 : 1);
    }

    public static boolean isPrime(final int val) {
        for(int i = 2; i < Math.ceil(Math.sqrt(val)) + 1; i++) { //to be safe...
            if (val % i == 0) {
                return false;
            }
        }
        return true;
    }
}
 </pre>
 */
public class Primes extends NumericQuestion {

    private int numPrimes;

    private final ArrayList<Integer> dataSet = new ArrayList<>(); //The set that appears in the question
    private final ArrayList<Integer> primeList = new ArrayList<>(); //A copy of the list of primes in PrimeData

    private final ArrayList<Integer> nonPrimeList = new ArrayList<>(); //A copy of the list of non primes in PrimeData

    @Override
    public String createQuestionTitle() {
        return "How Many Numbers Are Prime?";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("How many of the following numbers are prime?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Integer> formatter = new ArrayFormatter<>("public static final int[] primes", dataSet);
        final StringBuilder code = CodeUtils.questionCode("Primes", formatter.format(2), "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {

        //Generate the number of primes (the correct answer) and non primes to add to the list of data
        numPrimes = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        final int numNonPrimes = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //copy the prime and non prime data to generate the data list
        for(int elt : PrimeData.PRIMES) {
            primeList.add(elt);
        }
        for(int elt : PrimeData.NOT_PRIME) {
            nonPrimeList.add(elt);
        }

        //Randomly select primes and non primes to add to the data sets - remove them from the base sets
        //once added to avoid duplicates
        for (int i = 0; i < numPrimes; i++) {
            int index = ThreadLocalRandom.current().nextInt(primeList.size());
            dataSet.add(primeList.get(index));
            primeList.remove(index);
        }
        for (int i = 0; i < numNonPrimes; i++) {
            int index = ThreadLocalRandom.current().nextInt(nonPrimeList.size());
            dataSet.add(nonPrimeList.get(index));
            nonPrimeList.remove(index);
        }

        Collections.shuffle(dataSet);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(numPrimes));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        int primeCount = 0;
        for (int potentialPrime : dataSet) {
            boolean prime = true;
            for (int j = 2; j < potentialPrime - 1; j++) {
                if (potentialPrime % j == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                primeCount++;
            }
        }

        return primeCount == Integer.parseInt(answer.getQuestionAnswer());
    }
}
