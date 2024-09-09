package questiontypes.location;

import questiontypes.location.utils.LocationUtils;
import quizframework.Answer;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;

/**
 * Total distance between a sequence of points. Code for autograder:
 <pre>
import java.awt.*;
import java.util.*;

public class Test {
    record Point(double lat, double lon){};

    public static double[][] coordArray = {
        {-30.319848577289598, 76.6191736509924}, {-54.451048774171504, -77.38507407459403},
        {48.37912023938199, -29.91915190297184}, {37.0118146543936, -31.81397614791058},
        {-12.62458853244641, 41.02191176916082}, {29.013933440534615, 87.88604044563311},
        {-89.3415821972897, 58.11382963779954}, {-57.92319332452288, -80.11415946336002},
        {50.25133370793782, -58.09256798324694}, {58.372114664179435, -12.50813840806741},
        {82.42033849077137, -47.0367127775651}, {-63.80960076789579, -47.769674799973814},
        {74.20602844982335, -22.328028592786865}, {-35.844929901879425, -54.05393558963534},
        {-46.01170714128204, -34.636792048111964}, {-25.9840735092574, -5.344981014251687},
        {50.14359249276029, 66.02770621539003}, {-2.7532973809016994, 6.696882114362694},
        {-19.160300339375368, -34.17859726201821}, {-4.652313611379924, 51.5526387470112},
        {6.757670684771384, -3.3667133960006623}, {-20.085809071583597, -1.6864380371333993}
    };

    public static void main(String[] args) {
        TotalDistance.coordArray = coordArray;

        double dist = 0.0;
        double dist2 = 0.0;
        for(int i = 0; i < coordArray.length - 1; i++) {
            dist += distance(new Point(coordArray[i][0], coordArray[i][1]), new Point(coordArray[i+1][0], coordArray[i+1][1]));
        }
        final long minDist = Math.round(dist) - 2;
        final long maxDist = Math.round(dist) + 2;
        System.exit(TotalDistance.answer() >= minDist && TotalDistance.answer() <= maxDist ? 0 : 1);
    }

    public static double distance(final Point point1, Point point2) {
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
        return 6371 * c;
    }
}
 </pre>
 */
public class TotalDistance extends NumericQuestion {

    private double distance;
    private ArrayList<LocationUtils.Point> points = new ArrayList<>();

    @Override
    public String createQuestionTitle() {
        return "Total Distance between Points";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("""
                **Total Distance.** What is the TOTAL distance in km if you travel between all the
                coordinates in the following Java array? Your answer needs to be an integer so round it to the nearest km
                (think carefully about when you should do that in your code), and
                your answer needs to be within (+-) 2km of the actual answer. """).append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<LocationUtils.Point> formatter =
                new ArrayFormatter<>("public static double[][] coordArray", points) {
            @Override
            public String outputItem(LocationUtils.Point point) {
                return "{" + point + "}";
            }
        };
        final StringBuilder code = CodeUtils.questionCode("TotalDistance", formatter.format(2), "long");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        distance = 0;
        int numSteps = QuizUtils.genRandomInt(LocationUtils.MIN_STEPS, LocationUtils.MAX_STEPS);
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
        final int roundedDistance = (int) Math.round(distance);
        final Answer ans = Answer.makeCorrectAnswer(Integer.toString(roundedDistance));
        ans.setErrorRange(2.0);
        return ans;
    }

    /*We use getDistance to simplify this because the correctness of the published algorithm is established in
    DistanceTwoPoints.java and this avoids repeating too much code
    */

    @Override
    public boolean checkAnswer(final Answer answer) {
        final int expectedAns = Integer.parseInt(answer.getQuestionAnswer());
        final double range = answer.getErrorRange();
        final double maxAns = expectedAns + range;
        final double minAns = expectedAns - range;
        double answerDistance = 0;
        for(int i = 1; i < points.size(); i++) {
            answerDistance += LocationUtils.getDistance(points.get(i-1), points.get(i));
        }
        int roundedDistance = (int) Math.round(answerDistance);
        return roundedDistance <= maxAns && roundedDistance >= minAns;
    }
}
