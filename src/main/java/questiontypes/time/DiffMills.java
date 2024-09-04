package questiontypes.time;

import questiontypes.time.utils.TimeUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * Dates which differ by a specific number of milliseconds. Here is code suitable for autograder:
 <pre>
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test {
    public static String[] possDates = {
                "2050/10/21 07:33:41", "2871/10/16 05:54:52", "1971/07/12 05:17:52", "2607/01/01 01:07:50",
                "2398/08/08 03:50:32", "2559/07/17 01:34:34"
    };
    public static long millsDifferent = 13693785983000L;

    public static String baseDate = "2437/11/07 07:28:29";

    public static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
        MillisFrom.baseDate = baseDate;
        MillisFrom.possDates = possDates;
        MillisFrom.millsDifferent = millsDifferent;


        long baseDateMillis = Math.abs(LocalDateTime.parse(baseDate,df).atZone(ZoneId.systemDefault())
            .toInstant().toEpochMilli());

        String closest = "not found";
        for(String str: possDates) {
            if (isDate(str, millsDifferent, baseDateMillis)) {
                closest = str;
                break;
            }
        }
        System.exit(closest.equals(MillisFrom.answer()) ? 0 : 1);
    }

    private static boolean isDate(String pd, long millisDate, long offset) {
        long currentDateMillis = LocalDateTime.parse(pd,df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                return currentDateMillis + millsDifferent == offset
        ||  currentDateMillis - millsDifferent == offset;
    }
}
 </pre>
 */
public class DiffMills extends McqQuestion {

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
        final StringBuilder builder = new StringBuilder("Which of the dates below is ``" + millsDiff + "`` milliseconds from (either "
                + " before OR after) the date " + baseDate + "?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possDates", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };
        final StringBuilder code = CodeUtils.questionCode("MillisFrom",
                formatter.format()
                        .append(CodeUtils.indentTextBlock(String.format("public static long millsDifferent = %dL;", millsDiff)))
                        .append(CodeUtils.indentTextBlock(String.format("public static String baseDate = \"%s\";", baseDate))),
                "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
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
