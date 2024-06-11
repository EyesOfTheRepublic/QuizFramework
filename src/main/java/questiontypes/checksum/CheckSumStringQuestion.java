package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 *Generate a simple checksum question - create a random string and a corresponding checksum. Then create a set of
 *(incorrect) strings - the question asks which string the checksum belongs to. Uses algorithms defined in
 * {@link CheckSumQuestionUtils}. The following code is suitable to use in autograder:
 <pre>
 public class Test {
    public static void main(String[] args) {
        String chosenVal = CheckSumString.answer();
        String correctVal = getStr(CheckSumString.checkSum, CheckSumString.possStrings);
        if (chosenVal == null) {
            System.exit(1);
        }
        System.exit(chosenVal.equals(correctVal) ? 0 : 1);
    }

    public static String getStr(final long check, final String[] possStrings) {
        for(String str: possStrings) {
            if(check == simpleCheckSum(str)) {
                return str;
            }
        }
        return null;
    }

    public static long simpleCheckSum(String str) {
        long k = 7;//7
        for (int i = 0; i < str.length(); i++) {
            k *= 23;//23
            k += str.charAt(i);
            k *= 13;//13
            k %= 1000000009;
        }
        return k;
    }
}
 </pre>
 */
public class CheckSumStringQuestion extends McqQuestion {

    private String correctAnswer;

    @Override
    public String createQuestionTitle(){
        return "Which string matches checksum?";
    }
    @Override
    public String createQuestionText() {
        final StringBuilder builder =
                new StringBuilder( "Which of the strings in the array in the code below generates the simple checksum "
                + CheckSumQuestionUtils.simpleCheckSum(correctAnswer) + " ?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possStrings",this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };

        final StringBuilder  code = CodeUtils.questionCode("CheckSumString",
                formatter.format().append(CodeUtils.indentTextBlock(String.format("public static long checkSum = %dL;",
                        CheckSumQuestionUtils.simpleCheckSum(correctAnswer)))), "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public String createGeneralFeedback() {
        return "Some generic feedback";
    }

    @Override
    public String createCorrectFeedback() {
        return "Some feedback for the correct answer";
    }

    @Override
    public String createIncorrectFeedback() {
        return "Some general feedback for incorrect answers";
    }

    @Override
    public void createCalcData() {
        correctAnswer = QuizUtils.genRandomString(65, 20, 'a', 'z');
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(this.correctAnswer,
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(this.correctAnswer,
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2), "some incorrect feedback");
    }
}
