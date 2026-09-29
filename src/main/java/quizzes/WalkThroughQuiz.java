package quizzes;

import questiontypes.examples.MultQuestionExample;
import questiontypes.examples.OddNumberExample;
import questiontypes.examples.SquareQuestionExample;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**PracticeQuizPracticeQuiz
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class WalkThroughQuiz {

    public static void main(String[] args) {
        Quiz quiz = new Quiz("Simple Example Test",
                "This is a test just to illustrate how the programming class test will work");

        McqQuestion mult = new MultQuestionExample();
        mult.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(mult);
        McqQuestion square = new SquareQuestionExample();
        square.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(square);
        McqQuestion oddNumbers = new OddNumberExample();
        oddNumbers.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(oddNumbers);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "WalkThroughQuiz";
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
