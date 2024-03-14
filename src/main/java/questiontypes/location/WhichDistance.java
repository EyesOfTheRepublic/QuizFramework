package questiontypes.location;

import questiontypes.location.locationutils.LocationUtils;
import quizframework.Answer;
import quizframework.Question;

/**
 * Which two points are a specific distance apart?
 */
public class WhichDistance extends Question {

    private double distance;
    private LocationUtils.Point point1;
    private LocationUtils.Point point2;

    @Override
    public String createQuestionTitle() {
        return "Which Points are a Specific Distance Apart";
    }

    @Override
    public String createQuestionText() {
        return "Which pair of points in the following list are " + distance + "Km apart?";
    }

    @Override
    public void createCalcData() {
        point1 = LocationUtils.randomPoint();
        point2 = LocationUtils.randomPoint();
        distance = LocationUtils.getDistance(point1, point2);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(point1 + " and " + point2);
    }

    @Override
    public Answer createIncorrectAnswer() {
        LocationUtils.Point wrongPont1 = LocationUtils.randomPoint();
        LocationUtils.Point wrongPoint2 = LocationUtils.randomPoint();
        return Answer.makeIncorrectAnswer(wrongPont1 + " and " + wrongPoint2);
    }
}
