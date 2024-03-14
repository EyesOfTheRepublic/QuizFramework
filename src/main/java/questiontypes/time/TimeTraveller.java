package questiontypes.time;

import questiontypes.time.timeutils.TimeUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;

/**
 * How many milliseconds does it take to travel though a sequence of dates
 */
public class TimeTraveller extends Question {

    private long numMillis;
    private ArrayList<String> dateSeq = new ArrayList<>();

    private TimeUtils timeUtils = new TimeUtils();

    @Override
    public String createQuestionTitle() {
        return "Time Traveller";
    }

    @Override
    public String createQuestionText() {
        StringBuilder questionText = new StringBuilder("""
                Suppose you are at time traveller and travel through the sequence of dates in the following list,
                how many milliseconds would you have travelled through? Note that going backwards in time does not mean you 'subtract'
                milliseconds - the time you travel through (forwards or backwards) always adds on to the time you have travelled through up to that point.
                For example, if you travelled from 1st Jan 2021 to 1st Jan 2022, and then back to 1st Jan 2021 you would have travelled through two years
                of time (note though that the question is asking for an answer in milliseconds).""");
        ArrayFormatter<String> formatter = new ArrayFormatter<>("String dateList[]", dateSeq) {
            @Override
            public String outputItem(String item) {
                return "\"" + item + "\"";
            }
        };

        return questionText.append(CodeUtils.toCodeBlock(formatter.format())).toString();
    }

    @Override
    public void createCalcData() {
        final int numDates = QuizUtils.genRandomInt(TimeUtils.MIN_DATE_SEQ, TimeUtils.MAX_DATE_SEQ);
        numMillis = 0;
        String date = timeUtils.genRandomDate();
        for (int i = 0; i < numDates; i++) {
            dateSeq.add(date);
            date = timeUtils.genRandomDate();
            numMillis += Math.abs(timeUtils.getMillis(date) - timeUtils.getMillis(dateSeq.get(i)));
        }
        numMillis += Math.abs(timeUtils.getMillis(date) - timeUtils.getMillis(dateSeq.get(dateSeq.size() - 1)));
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Long.toString(numMillis));
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(Long.toString(
                QuizUtils.genRandomLong(numMillis / TimeUtils.TIME_TRAVEL_MIN_FACTOR,
                        Long.MAX_VALUE / TimeUtils.TIME_TRAVEL_MAX_FACTOR)));
    }
}
