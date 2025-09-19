package quizzes;

import questiontypes.simplequestions.SquareQuestionExample;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class DemoQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Example", "Example for In-Class Test");

        McqQuestion square = new SquareQuestionExample();
        square.createQuestion(6);
        quiz.addQuestion(square);
        System.out.println(quiz);
        try {
            PrintStream stream = new PrintStream("DemoQuiz.txt");
            quiz.generateText2Qti(stream);
            stream.close();
        } catch (FileNotFoundException fne) {
            System.out.println("Can't open file");
        }
    }
}
