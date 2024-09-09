package quizzes;

import questiontypes.checksum.BitwiseChecksum;
import questiontypes.checksum.CheckSumString;
import questiontypes.checksum.CheckSumValue;
import questiontypes.crypto.Encryption;
import questiontypes.location.DistanceTwoPoints;
import questiontypes.location.TotalDistance;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.Primes;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.time.TimeTraveller;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * One of the 'real' quizzes - two numbers, one rewriting and one time question
 */

public class PracticeQuiz {

    public static void main(String[] args) {
        Quiz quiz = new Quiz(GenQuizData.PRACTICE_TITLE,
                GenQuizData.HEADER + GenQuizData.PRACTICE_PRE_AMBLE);

        McqQuestion distanceTwoPoints = new DistanceTwoPoints();
        distanceTwoPoints.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(distanceTwoPoints);

        NumericQuestion totalDistance = new TotalDistance();
        totalDistance.createQuestion();
        quiz.addQuestion(totalDistance);

        NumericQuestion checkSumValue = new CheckSumValue();
        checkSumValue.createQuestion();
        quiz.addQuestion(checkSumValue);

        McqQuestion checkSumString = new CheckSumString();
        checkSumString.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(checkSumString);

        McqQuestion bitwiseCheckSum = new BitwiseChecksum();
        bitwiseCheckSum.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(bitwiseCheckSum);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "PracticeQuiz";
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
