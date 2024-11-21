package quizzes;

import questiontypes.crypto.NumCols;
import questiontypes.numbers.AddPairs;
import questiontypes.numbers.Factors;
import questiontypes.numbers.PythTriplets;
import questiontypes.numbers.SophieGermain;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.termrewriting.RewritingToCompletion;
import questiontypes.time.ClosestDateTime;
import questiontypes.time.DiffMills;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class Quiz3 {

    private static int QUIZ_NUM = 3
            ;
    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM),
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        NumericQuestion factors = new Factors();
        factors.createQuestion();
        quiz.addQuestion(factors);
        NumericQuestion pythTriplets = new PythTriplets();
        pythTriplets.createQuestion();
        quiz.addQuestion(pythTriplets);

        McqQuestion rewritingNSteps = new RewritingNSteps();
        rewritingNSteps.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewritingNSteps);

        McqQuestion numCols = new NumCols();
        numCols.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(numCols);

        McqQuestion closestDateTime = new ClosestDateTime();
        closestDateTime.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(closestDateTime);

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
