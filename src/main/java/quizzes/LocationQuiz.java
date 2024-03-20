package quizzes;

import questiontypes.location.DistanceTwoPoints;
import questiontypes.location.MinSecDistance;
import questiontypes.location.TotalDistance;
import questiontypes.location.WhichDistance;
import questiontypes.numbers.Factors;
import questiontypes.numbers.Fibonacci;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Generate quizzes based on the location questions and two of the numbers questions
 */

public class LocationQuiz {

    private static final String QUIZ_DESC = """
              <h3>Coordinate Sytems</h3><p>The first four questions in this quiz are based on Coordinate Systems. \
              You can find (and should already have read) background information on Coordinate Systems in the  In-Class Test Information module \
              on Canvas.</p><p>NOTE there are some fragments of code in the algorithms below, but you are responsible for implementing them in Java.</p>\
              <h4>Algorithm to Measure Distance</h4><p>The algorithm to measure the distance between one point and another, when represented as \
              as latitude and longitude is as follows:</p>\
              <ul><li>We assume the two points are <kbd>lat1</kbd>, <kbd>lon1</kbd> and <kbd>lat2</kbd>, <kbd>lon2</kbd></li>\
              <li>All variables except <kbd>R</kbd> below should be double</li>\
              <li>Set a variable <kbd>R</kbd> to be the radius of the Earth in km - 6371</li>\
              <li>Set a variable <kbd>latDistance</kbd> to be <kbd>lat2 - lat1</kbd> in Radians - \
              the Java method <kbd>Math.toRadians</kbd> will turn the result of <kbd>lat2 - lat1</kbd> to Radians</li>\
              <li>Set a variable <kbd>lonDistance</kbd> to be <kbd>lon2 - lon1</kbd> in Radians (using the same Java method to convert to Radians</li>\
              <li>Set a variable <kbd>a</kbd> to <kbd>Math.sin(latDistance / 2) * Math.sin(latDistance / 2)\
              + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))\
              * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)</kbd></li>\
              <li>Set a variable <kbd>c</kbd> to <kbd>2 * Math.asin(Math.sqrt(a))</kbd></li>\
              <li>The distance between <kbd>lat1</kbd>, <kbd>lon1</kbd> and <kbd>lat2</kbd>, <kbd>lon2</kbd> is <kbd>R * c</kbd></li>\
              </ul>\
              <p>The algorithm for converting from coordinates in degrees, minutes, seconds to decimal is in the information on Canvas in\
              the January Assessment Information module.</p>\
              <p>Remember: cut-and-paste long strings from the questions; do not try to type them in.</p> \
              <h4>Fibonacci Numbers and Factors</h4> \
              <p>The remaining two questions ask you to find which numbers in a list have a specific number as a factor, \
              and which numbers in a list are Fibonacci numbers.</p>""";

    public static void main(String[] args) {
        Quiz quiz = new Quiz("Location and Numbers 3",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Location questions
        McqQuestion distTwoPoints = new DistanceTwoPoints();
        distTwoPoints.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(distTwoPoints);

        McqQuestion whichDistance = new WhichDistance();
        whichDistance.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(whichDistance);

        McqQuestion totalDist = new TotalDistance();
        totalDist.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(totalDist);

        McqQuestion minSecDist = new MinSecDistance();
        minSecDist.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(minSecDist);

        //Factor and Fibonacci questions
        McqQuestion factor = new Factors();
        factor.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(factor);

        McqQuestion fibNum = new Fibonacci();
        fibNum.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(fibNum);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Location3";
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
