package quizzes;

import questiontypes.basic.HalfToZero;
import questiontypes.checksum.BitwiseChecksum;
import questiontypes.termrewriting.RewritingToCompletion;
import questiontypes.time.TimeTraveller;
import questiontypes.verybasic.SquareQuestionExample;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**PracticeQuizPracticeQuiz
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class PracticeQuiz3 {

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.PRACTICE_TITLE,3),
                GenQuizData.PRACTICE_PRE_AMBLE);

        McqQuestion squareQuestion = new SquareQuestionExample();
        squareQuestion.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(squareQuestion);

        McqQuestion halfToZero = new HalfToZero();
        halfToZero.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(halfToZero);

        McqQuestion rewritingToCompletion = new RewritingToCompletion();
        rewritingToCompletion.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewritingToCompletion);

        McqQuestion bitwiseChecksum = new BitwiseChecksum();
        bitwiseChecksum.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(bitwiseChecksum);

        McqQuestion timeTraveller = new TimeTraveller();
        timeTraveller.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "CS-128-PracticeQuiz3";
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
