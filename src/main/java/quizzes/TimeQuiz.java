package quizzes;

import questiontypes.numbers.Primes;
import questiontypes.numbers.SophieGermain;
import questiontypes.time.ClosestDateTime;
import questiontypes.time.DiffMills;
import questiontypes.time.PairDiffMills;
import questiontypes.time.TimeTraveller;
import quizframework.Question;
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
            <h3>Prime Numbers</h3> \
            <p>The next two questions relate to prime numbers.</p>""";
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Time and Numbers 2",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Time questions
        Question closestDateTime = new ClosestDateTime();
        closestDateTime.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(closestDateTime);

        Question diffMills = new DiffMills();
        diffMills.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(diffMills);

        Question pairDiffMills = new PairDiffMills();
        pairDiffMills.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(pairDiffMills);

        Question timeTraveller = new TimeTraveller();
        timeTraveller.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        //Prime number questions
        Question prime = new Primes();
        prime.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(prime);

        Question sophieG = new SophieGermain();
        sophieG.createMcqQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sophieG);

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
