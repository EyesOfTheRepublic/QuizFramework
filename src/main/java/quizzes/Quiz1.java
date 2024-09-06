package quizzes;

import questiontypes.crypto.Encryption;
import questiontypes.location.DistanceTwoPoints;
import questiontypes.location.MinSecDistanceNoCode;
import questiontypes.location.TotalDistance;
import questiontypes.location.WhichDistanceNoCode;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.Primes;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.time.TimeTraveller;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class Quiz1 {

    private static int QUIZ_NUM = 1;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(GenQuizData.TITLE,
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        NumericQuestion primes = new Primes();
        primes.createQuestion();
        quiz.addQuestion(primes);
        NumericQuestion fibonacci = new Fibonacci();
        fibonacci.createQuestion();
        quiz.addQuestion(fibonacci);

        McqQuestion reduceToX = new ReducesToX();
        reduceToX.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(reduceToX);

        McqQuestion encryption = new Encryption();
        encryption.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(encryption);

        McqQuestion timeTraveller = new TimeTraveller();
        timeTraveller.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Quiz" + QUIZ_NUM;
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
