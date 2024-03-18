package questiontypes.location;

import questiontypes.location.locationutils.LocationUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;

/**
 * Total distance between a sequence of points.
 */
public class TotalDistance extends Question {

    private double distance;
    private int numSteps;
    private ArrayList<LocationUtils.Point> points = new ArrayList<>();

    @Override
    public String createQuestionTitle() {
        return "Total Distance between Points";
    }

    @Override
    public String createQuestionText() {
        StringBuilder res = new StringBuilder("""
                What is the TOTAL distance in km if you travel between all the
                coordinates in the following Java array?:""");
        ArrayFormatter<LocationUtils.Point> formatter = new ArrayFormatter<>("double[][] coordArray", points) {
            @Override
            public String outputItem(LocationUtils.Point point) {
                return "{" + point + "}";
            }
        };
        return res.append(CodeUtils.toCodeBlock(formatter.format())).toString();
    }

    @Override
    public void createCalcData() {
        distance = 0;
        numSteps = QuizUtils.genRandomInt(LocationUtils.MIN_STEPS, LocationUtils.MAX_STEPS);
        LocationUtils.Point currPoint = LocationUtils.randomPoint();
        for(int i = 0; i < numSteps; i++) {
            points.add(currPoint);
            currPoint = LocationUtils.randomPoint();
            distance += LocationUtils.getDistance(points.get(points.size() - 1), currPoint);
        }
        points.add(currPoint);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Double.toString(distance));
    }

    @Override
    public Answer createIncorrectAnswer() {
        /*The range of wrong answers cannot be outside 0 to circumference of the Earth multiplied by half
        the number of steps (empirically determined as looking 'OK')
         */
        return Answer.makeIncorrectAnswer(Double.toString(QuizUtils.genRandomDouble(0,
                LocationUtils.EARTH_CIRC * numSteps /2 , 0)));
    }

    /*We use getDistance to simplify this because the correctness of the published algorithm is established in
    DistanceTwoPoints.java and this avoids repeating too much code
    */

    @Override
    public boolean checkAnswer(final Answer answer) {
        final double expectedAns = Double.parseDouble(answer.getQuestionAnswer());
        double answerDistance = 0;
        for(int i = 1; i < points.size(); i++) {
            answerDistance += LocationUtils.getDistance(points.get(i-1), points.get(i));
        }
        return answerDistance == expectedAns;
    }
}
