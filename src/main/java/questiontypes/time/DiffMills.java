package questiontypes.time;

import questiontypes.time.timeutils.TimeUtils;
import quizframework.Answer;
import quizframework.Question;

/**
 * Dates which differ by a specific number of milliseconds
 */
public class DiffMills extends Question {

    private String baseDate;
    private String answerDate;

    private long millsDiff;
    TimeUtils timeUtils = new TimeUtils();

    @Override
    public String createQuestionTitle(){
        return "Which date is specific number of milliseconds from another?";
    }

    @Override
    public String createQuestionText() {
        return "Which of the dates below is <kbd>" + millsDiff + "L</kbd> milliseconds from the "
                + "date " + baseDate + "?";
    }

    @Override
    public void createCalcData() {
        baseDate = timeUtils.genRandomDate();
        answerDate = timeUtils.genRandomDate();
        millsDiff = Math.abs(timeUtils.getMillis(baseDate) - timeUtils.getMillis(answerDate));
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(answerDate);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(timeUtils.genRandomDate());
    }
}
