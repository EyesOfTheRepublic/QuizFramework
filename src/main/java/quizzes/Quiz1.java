package quizzes;

import questiontypes.crypto.Encryption;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.McqPrimes;
import questiontypes.basic.SumSquareQuestion;
import questiontypes.termrewriting.RewritingNSteps;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class Quiz1 {

    private static final int QUIZ_NUM = 1;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM) + ": Engineering C109, 12:00",
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        McqQuestion checkSumOfSquares = new SumSquareQuestion();
        checkSumOfSquares.createQuestion(5);
        quiz.addQuestion(checkSumOfSquares);

        McqQuestion primes = new McqPrimes();
        primes.createQuestion(5);
        quiz.addQuestion(primes);

        NumericQuestion fibonacci = new Fibonacci();
        fibonacci.createQuestion();
        quiz.addQuestion(fibonacci);

        McqQuestion rewritingNSteps = new RewritingNSteps();
        rewritingNSteps.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewritingNSteps);

        McqQuestion encryption = new Encryption();
        encryption.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(encryption);
/*
        McqQuestion timeTraveller = new TimeTraveller();
        timeTraveller.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);
*/
        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Quiz-C109-" + QUIZ_NUM;
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
