package questiontypes.location;

import questiontypes.location.locationutils.LocationUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

/**
 * Distance between two points using minutes/seconds representation
 */
public class MinSecDistance extends Question {

    private String point1MinSec;
    private String point2MinSec;

    private double distance;

    @Override
    public String createQuestionTitle() {
        return "How far apart are these points (degrees, minutes, seconds)?";
    }

    @Override
    public String createQuestionText() {
        return "What is the distance in Km between " + point1MinSec + " and " + point2MinSec + "?";
    }

    @Override
    public void createCalcData() {
        final LocationUtils.Point point1 = LocationUtils.randomPoint();
        final LocationUtils.Point point2 = LocationUtils.randomPoint();

        //Safer to convert to the expected format and then parse and calculate distance to avoid rounding errors
        point1MinSec = LocationUtils.toMinSec(point1);
        point2MinSec = LocationUtils.toMinSec(point2);

        final String[] point1Parsed = point1MinSec.split(",");
        final double lat1 = LocationUtils.convertHourToDecimal(point1Parsed[0]);
        final double lon1 = LocationUtils.convertHourToDecimal(point1Parsed[1]);

        final String[] point2Parsed = point2MinSec.split(",");
        final double lat2 = LocationUtils.convertHourToDecimal(point2Parsed[0]);
        final double lon2 = LocationUtils.convertHourToDecimal(point2Parsed[1]);

        distance = LocationUtils.getDistance(new LocationUtils.Point(lat1, lon1),
                new LocationUtils.Point(lat2, lon2));
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Double.toString(distance));
    }

    @Override
    public Answer createIncorrectAnswer() {
        //The range of wrong answers cannot be outside 0 to circumference of the Earth, and must be within 1000Km of the real answer
        final double minVal = (distance - LocationUtils.MAX_DIST_VARIATION < 0) ? 0 : distance - LocationUtils.MAX_DIST_VARIATION;
        final double maxVal = (distance + LocationUtils.MAX_DIST_VARIATION > LocationUtils.EARTH_CIRC) ?
                LocationUtils.EARTH_CIRC : distance + LocationUtils.MAX_DIST_VARIATION;
        if (minVal > maxVal) {
            System.out.println(minVal + " " + maxVal + " " + distance);
        }
        return Answer.makeIncorrectAnswer(Double.toString(QuizUtils.getRandomDouble(minVal, maxVal, 0)));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        final double expectedAns = Double.parseDouble(answer.getAnswer());

        final String[] point1Parsed = point1MinSec.split(",");
        final double lat1 = LocationUtils.convertHourToDecimal(point1Parsed[0]);
        final double lon1 = LocationUtils.convertHourToDecimal(point1Parsed[1]);

        final String[] point2Parsed = point2MinSec.split(",");
        final double lat2 = LocationUtils.convertHourToDecimal(point2Parsed[0]);
        final double lon2 = LocationUtils.convertHourToDecimal(point2Parsed[1]);

        return expectedAns == LocationUtils.getDistance(new LocationUtils.Point(lat1, lon1),
                new LocationUtils.Point(lat2, lon2));
    }
}
