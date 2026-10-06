package quizzes;

import questiontypes.basic.Collatz;
import questiontypes.basic.EvenSumRange;
import questiontypes.crypto.Encryption;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.McqFactors;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class JanQuiz1 {

    private static final int QUIZ_NUM = 1;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM),
                GenQuizData.PRE_AMBLE);

        McqQuestion collatz = new Collatz();
        collatz.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(collatz);

        McqQuestion evenSumRange = new EvenSumRange();
        evenSumRange.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(evenSumRange);

        McqQuestion factors = new McqFactors();
        factors.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(factors);

        McqQuestion fibonacci = new Fibonacci();
        fibonacci.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(fibonacci);

        McqQuestion encryption = new Encryption();
        encryption.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(encryption);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Jan-Quiz-" + QUIZ_NUM;
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
