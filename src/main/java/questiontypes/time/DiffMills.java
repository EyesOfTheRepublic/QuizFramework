package questiontypes.time;

import questiontypes.time.utils.TimeUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * Dates which differ by a specific number of milliseconds
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
