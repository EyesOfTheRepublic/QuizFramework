package questiontypes.directanswer.time;

import questiontypes.directanswer.time.timeutils.TimeUtils;
import quizframework.Answer;
import quizframework.Question;

/**
 * Which pair of dates are separated by a specific number of milliiseconds?
 */
public class PairDiffMills extends Question {
    private String baseDate;
    private String endDate;

    private long millsDiff;
    TimeUtils timeUtils = new TimeUtils();

    @Override
    public String createQuestionTitle() {
        return "Pairs of dates differing by specific number of milliseconds";
    }

    @Override
    public String createQuestionText() {
        return "Which of the pairs of dates below is separated by ``" + millsDiff + "L`` milliseconds?";
    }

    @Override
    public void createCalcData() {
        baseDate = timeUtils.genRandomDate();
        endDate = timeUtils.genRandomDate();
        millsDiff = Math.abs(timeUtils.getMillis(baseDate) - timeUtils.getMillis(endDate));
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(baseDate + " and " + endDate);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(timeUtils.genRandomDate() + " and " + timeUtils.genRandomDate());
    }
}
