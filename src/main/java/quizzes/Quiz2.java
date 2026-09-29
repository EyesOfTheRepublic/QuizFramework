package quizzes;

import questiontypes.crypto.Decryption;
import questiontypes.numbers.*;
import questiontypes.basic.SumNumbers;
import questiontypes.termrewriting.RewritingToCompletion;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */
public class Quiz2 {

    private static final int QUIZ_NUM = 2;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM) + ": Engineering C109, 14:30",
                GenQuizData.HEADER + GenQuizData.PRE_AMBLE);

        McqQuestion addSubQuestion = new SumNumbers();
        addSubQuestion.createQuestion(5);
        quiz.addQuestion(addSubQuestion);

        McqQuestion factors = new McqFactors();
        factors.createQuestion(5);
        quiz.addQuestion(factors);

        NumericQuestion pythTriplets = new PythTriplets();
        pythTriplets.createQuestion();
        quiz.addQuestion(pythTriplets);

        McqQuestion rewriteToCompletion = new RewritingToCompletion();
        rewriteToCompletion.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewriteToCompletion);

        McqQuestion decryption = new Decryption();
        decryption.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(decryption);
/*
        McqQuestion diffMills = new DiffMills();
        diffMills.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(diffMills);
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
