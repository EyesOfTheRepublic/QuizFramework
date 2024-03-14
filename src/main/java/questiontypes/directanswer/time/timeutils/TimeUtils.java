package questiontypes.directanswer.time.timeutils;
/*
Basic operations etc. used in the time based questions
 */

import quizframework.utils.QuizUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private final DateTimeFormatter df
            = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static final int TIME_STEP = 1000000;
    //These two values are empirically determined to make wrong answers look plausible
    public static final int TIME_TRAVEL_MIN_FACTOR = 100000;
    public static final int TIME_TRAVEL_MAX_FACTOR = 10000;
    public static final long MILLIS_IN_HOUR = 1000L * 60 * 60;

    public static final int MIN_DATE_SEQ = 50;
    public static final int MAX_DATE_SEQ = 70;

    /*
    Parse a string in the format used by the date formatter and return the number of milliseconds
     */
    public long getMillis(String date) {
        return LocalDateTime.parse(date, df)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

    /*
    Turn a long representing the time in milliseconds into a formatted string
     */
    public String millisToDate(long millis) {
        LocalDateTime dmils
                = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return dmils.format(df);
    }

    /*
    Generate a random data in way that the date formatter used can parse
     */
    public String genRandomDate() {
        //"1970/01/01 01:10:45"
        int year = QuizUtils.genRandomInt(1970, 2970);
        int month = QuizUtils.genRandomInt(1, 12);
        boolean isLeapYr = (year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0);
        int day =
        switch (month) {
            case 2 -> QuizUtils.genRandomInt(1, isLeapYr ? 29 : 28);
            case 4, 6, 9, 11 -> QuizUtils.genRandomInt(1, 30);
            default -> QuizUtils.genRandomInt(1, 31);
        };

        int hour = QuizUtils.genRandomInt(0, 24);
        int min = QuizUtils.genRandomInt(0, 60);
        int sec = QuizUtils.genRandomInt(0, 60);
        return String.format("%04d/%02d/%02d %02d:%02d:%02d",
                year, month, day, hour, min, sec);
    }

}
