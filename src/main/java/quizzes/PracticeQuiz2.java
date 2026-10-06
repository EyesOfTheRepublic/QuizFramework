package quizzes;

import questiontypes.basic.SumMultiplesFour;
import questiontypes.checksum.CheckSumString;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.time.DiffMills;
import questiontypes.verybasic.NextBiggestEven;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**PracticeQuizPracticeQuiz
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class PracticeQuiz2 {

    public static void main(String[] args) {
        Quiz quiz = new Quiz(String.format(GenQuizData.PRACTICE_TITLE,2),
                GenQuizData.PRACTICE_PRE_AMBLE);

        McqQuestion nextBiggestEven = new NextBiggestEven();
        nextBiggestEven.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(nextBiggestEven);

        McqQuestion sumFour = new SumMultiplesFour();
        sumFour.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sumFour);

        McqQuestion rewritingNSteps = new RewritingNSteps();
        rewritingNSteps.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewritingNSteps);

        McqQuestion checksumString = new CheckSumString();
        checksumString.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(checksumString);

        McqQuestion diffMills = new DiffMills();
        diffMills.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(diffMills);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "CS-128-PracticeQuiz2";
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
