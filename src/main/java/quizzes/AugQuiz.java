package quizzes;

import questiontypes.basic.SumNumbers;
import questiontypes.basic.SumSquareQuestion;
import questiontypes.crypto.NumCols;
import questiontypes.numbers.AddPairs;
import questiontypes.numbers.McqPrimes;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**PracticeQuizPracticeQuiz
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class AugQuiz {

    public static void main(String[] args) {
        Quiz quiz = new Quiz("CS-128 Supplementary Programming Exam",
                GenQuizData.PRE_AMBLE);

        McqQuestion sumSquares = new SumSquareQuestion();
        sumSquares.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sumSquares);

        McqQuestion sumNumbers = new SumNumbers();
        sumNumbers.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sumNumbers);

        McqQuestion primes = new McqPrimes();
        primes.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(primes);

        McqQuestion addPairs = new AddPairs();
        addPairs.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(addPairs);

        McqQuestion numCols = new NumCols();
        numCols.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(numCols);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "AugQuiz";
            try {
                PrintStream stream = new PrintStream(fileName + ".txt");
                quiz.generateText2Qti(stream);
                stream.close();
            } catch (FileNotFoundException fne) {
                System.out.println("Cannot open " + fileName);
            }
        }
    }
}
