package quizzes;

import questiontypes.numbers.AddPairs;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.Primes;
import questiontypes.numbers.SophieGermain;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.termrewriting.RewritingToCompletion;
import questiontypes.time.DiffMills;
import questiontypes.time.TimeTraveller;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */
public class Quiz2 {

    private static int QUIZ_NUM = 2;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM),
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        McqQuestion sophieGermain = new SophieGermain();
        sophieGermain.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sophieGermain);
        NumericQuestion addPairs = new AddPairs();
        addPairs.createQuestion();
        quiz.addQuestion(addPairs);

        NumericQuestion rewriteToCompletion = new RewritingToCompletion();
        rewriteToCompletion.createQuestion();
        quiz.addQuestion(rewriteToCompletion);

        McqQuestion diffMills = new DiffMills();
        diffMills.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(diffMills);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Quiz " + QUIZ_NUM;
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
