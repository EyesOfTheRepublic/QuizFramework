package questiontypes.location;

import questiontypes.location.locationutils.LocationUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.QuizUtils;

/**
 * How far is it between two points (lat, long) on a globe?
 */
public class DistanceTwoPoints extends Question {

    private LocationUtils.Point point1;
    private LocationUtils.Point point2;

    private double distance;

    @Override
    public String createQuestionTitle() {
        return "Distance between two points";
    }

    @Override
    public String createQuestionText() {
        return "What is the distance in Km between coordinates ``" + point1
                + "`` and ``" + point2 + "``?";
    }

    @Override
    public void createCalcData() {
        //Generate random lat long in the ranges -90 to +90
        point1 = LocationUtils.randomPoint();
        point2 = LocationUtils.randomPoint();

        distance = LocationUtils.getDistance(point1, point2);
    }

    @Override
    public Answer createCorrectAnswer(){
        return Answer.makeCorrectAnswer(Double.toString(distance));
    }

    @Override
    public Answer createIncorrectAnswer() {
        //The range of wrong answers cannot be outside 0 to circumference of the Earth, and must be within 1000Km of the real answer
        final double minVal = (distance - LocationUtils.MAX_DIST_VARIATION < 0) ? 0 : distance - LocationUtils.MAX_DIST_VARIATION;
        final double maxVal = (distance + LocationUtils.MAX_DIST_VARIATION > LocationUtils.EARTH_CIRC) ?
                LocationUtils.EARTH_CIRC : distance + LocationUtils.MAX_DIST_VARIATION;
        return Answer.makeIncorrectAnswer(Double.toString(QuizUtils.genRandomDouble(minVal, maxVal, 0)));
    }

    @Override
    public boolean checkAnswer(Answer answer){
        double lat1 = point1.lat();
        double long1 = point1.lon();
        double lat2 = point2.lat();
        double long2 = point2.lon();
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double answerDistance = LocationUtils.EARTH_RAD * c;

        return answerDistance == Double.parseDouble(answer.getQuestionAnswer());
    }
}
