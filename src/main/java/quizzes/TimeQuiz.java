package quizzes;

import questiontypes.time.ClosestDateTime;
import questiontypes.time.DiffMills;
import questiontypes.time.PairDiffMillsNoCode;
import questiontypes.time.TimeTraveller;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Generate quizzes based on the time questions and two of the numbers questions
 */

public class TimeQuiz {
     private static final String QUIZ_DESC = """
            <h3>Time</h3> \
            <p>The first four questions in this quiz are based on Time. \
            You can find (and should already have read) background information on Time in the In-Class Test Information module \
            in Canvas. One of the problem solving question blocks in this paper is based on Time. \
            You will need to use the DateFormatter and LocalDateTime classes, along with the code examples, described in the \
            background information.</p> \
            <p>Remember: cut-and-paste long strings from the questions; do not try to type them in.</p> \
            """;
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Time and Numbers 2",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Time questions
        McqQuestion closestDateTime = new ClosestDateTime();
        closestDateTime.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(closestDateTime);

        McqQuestion diffMills = new DiffMills();
        diffMills.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(diffMills);

        McqQuestion pairDiffMills = new PairDiffMillsNoCode();
        pairDiffMills.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(pairDiffMills);

        McqQuestion timeTraveller = new TimeTraveller();
        timeTraveller.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Time2";
            try {
                PrintStream stream = new PrintStream(fileName + ".txt");
                quiz.generateText2Qti(stream);
                stream.close();
            }catch (FileNotFoundException fne) {
                System.out.println("Cannot open " + fileName);
            }
        }
    }
}
