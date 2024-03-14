package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PrimeData;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This question has a large list of numbers some prime and some not. The question is to identify how many are prime.
 */
public class Primes extends Question {

    private int numPrimes;

    private ArrayList<Integer> dataSet = new ArrayList<>(); //The set that appears in the question
    private ArrayList<Integer> primeList = new ArrayList<>(); //A copy of the list of primes in PrimeData

    private ArrayList<Integer> nonPrimeList = new ArrayList<>(); //A copy of the list of non primes in PrimeData

    @Override
    public String createQuestionTitle() {
        return "How Many Numbers Are Prime?";
    }

    @Override
    public String createQuestionText() {
        StringBuilder questionText = new StringBuilder("How many of the following numbers are prime?");
        ArrayFormatter<Integer> formatter = new ArrayFormatter<>("int[] primes", dataSet);
        return questionText.append(CodeUtils.toCodeBlock(formatter.format())).toString();
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
    public Answer createIncorrectAnswer() {
        int candidate = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        //dataSet.size() won't be > MAX_NUM for current values but just in case...
        return Answer.makeIncorrectAnswer(Integer.toString(Math.min(candidate, dataSet.size())));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        int primeCount = 0;
        for(int i = 0; i < dataSet.size(); i++) {
            boolean prime = true;
            for (int j = 2; j < dataSet.get(i) - 1; j++) {
                if (dataSet.get(i) % j == 0) {
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
