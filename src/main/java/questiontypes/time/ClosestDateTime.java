package questiontypes.time;

import questiontypes.time.utils.TimeUtils;
import quizframework.Answer;
import quizframework.McqQuestion;

/**
Work out the time that is closest to a specific number of hours from midnight on 1st Jan 1970
 */
public class ClosestDateTime extends McqQuestion {

    private long hours;
    private String dateTime;

    private TimeUtils timeUtils = new TimeUtils();

    @Override
    public String createQuestionTitle() {
        return "Closest to specific hour";
    }

    @Override
    public String createQuestionText() {
        return "Which of the times in the following list are closest to hour "
                + hours + " since 1970/01/01 01:00:00?";
    }

    @Override
    public void createCalcData() {
        dateTime = timeUtils.genRandomDate();
        final long millis = timeUtils.getMillis(dateTime);
        hours = millis / TimeUtils.MILLIS_IN_HOUR;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(dateTime);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(timeUtils.genRandomDate());
    }
}
