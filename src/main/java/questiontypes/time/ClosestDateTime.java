package questiontypes.time;

import questiontypes.time.utils.TimeUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.time.LocalDate;

/**
Work out the time that is closest to a specific number of hours from midnight on 1st Jan 1970. Code for autograder:
 <pre>
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Test {
    public static String[]  possDates = {
                "2293/06/29 23:05:10", "2575/06/05 02:54:27", "2825/04/24 13:32:29", "2462/01/28 14:44:23",
                "2927/09/29 15:28:47", "2710/01/06 20:40:13"
    };
    public static long hours = 6486836L;

    public static void main(String[] args) {
        TimeSince.hours = hours;
        TimeSince.possDates = possDates;

        long millis = hours * 60 * 60 * 1000;
        long diff = millis;
        String closest = possDates[0];
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        for(String date : possDates) {
            long possMillis = LocalDateTime.parse(date, df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (Math.abs(possMillis - millis) < diff) {
                diff = Math.abs(possMillis - millis);
                closest = date;
            }
        }

        System.exit(closest.equals(TimeSince.answer()) ? 0 : 1);
    }

    public static String runOneStep(String input, final String[][] rules) {
        for (int i = 0; i < rules.length; i++) {
            input = input.replaceAll(rules[i][0], rules[i][1]);
        }
        return input;
    }
}
 </pre>
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
        final StringBuilder builder = new StringBuilder("**Time Since.** Which of the times in the following list is CLOSEST to "
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
