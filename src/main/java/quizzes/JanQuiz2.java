package quizzes;

import questiontypes.basic.ProductOddRange;
import questiontypes.basic.SumThreeFiveQuestion;
import questiontypes.crypto.Decryption;
import questiontypes.numbers.*;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */
public class JanQuiz2 {

    private static final int QUIZ_NUM = 2;

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.TITLE, QUIZ_NUM),
                GenQuizData.PRE_AMBLE);

        McqQuestion productOddRange = new ProductOddRange();
        productOddRange.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(productOddRange);

        McqQuestion sumThreeFive = new SumThreeFiveQuestion();
        sumThreeFive.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sumThreeFive);

        McqQuestion sophieGermain = new SophieGermain();
        sophieGermain.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sophieGermain);

        McqQuestion pythTriplets = new PythTriplets();
        pythTriplets.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(pythTriplets);

        McqQuestion decryption = new Decryption();
        decryption.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(decryption);

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
