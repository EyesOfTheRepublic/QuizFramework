package quizzes;

import questiontypes.simpleexamples.OddNumberExample;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class DemoQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Demo", "Demonstrating long strings...");

        McqQuestion odd = new OddNumberExample();
        odd.createQuestion(6);
        quiz.addQuestion(odd);
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
