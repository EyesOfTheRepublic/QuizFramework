package questiontypes.time;

import questiontypes.time.utils.TimeUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.time.LocalDate;

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
        final StringBuilder builder = new StringBuilder("Which of the times in the following list is CLOSEST to "
                + hours + " hours after 1970/01/01 00:00:00? UTC? "
                + "(I.e. midnight in London on 1st January 1970.)")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possDates", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };
        final StringBuilder code = CodeUtils.questionCode("TimeSince",
                formatter.format()
                        .append(CodeUtils.indentTextBlock(String.format("public static long hours = %dL;", hours))),
                "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
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
