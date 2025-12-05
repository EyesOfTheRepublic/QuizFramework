package quizzes;

import questiontypes.crypto.Encryption;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.McqPrimes;
import questiontypes.numbers.SophieGermain;
import questiontypes.simplequestions.AddSubQuestion;
import questiontypes.simplequestions.SumSquareQuestion;
import questiontypes.termrewriting.RewritingNSteps;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * An extra quick for exchange students who had to leave early
 */

public class QuizExch {

    private static int QUIZ_NUM = 5;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM) + ": Location TBD, 5th Jan 2026, 14:00",
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        McqQuestion checkSumOfSquares = new SumSquareQuestion();
        checkSumOfSquares.createQuestion(5);
        quiz.addQuestion(checkSumOfSquares);

        McqQuestion addSubQuiz = new AddSubQuestion();
        addSubQuiz.createQuestion(5);
        quiz.addQuestion(addSubQuiz);

        McqQuestion primes = new SophieGermain();
        primes.createQuestion(5);
        quiz.addQuestion(primes);

        NumericQuestion fibonacci = new Fibonacci();
        fibonacci.createQuestion();
        quiz.addQuestion(fibonacci);

        McqQuestion rewritingNSteps = new RewritingNSteps();
        rewritingNSteps.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewritingNSteps);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Quiz-Exch-" + QUIZ_NUM;
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
