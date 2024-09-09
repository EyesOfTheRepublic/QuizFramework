package questiontypes.checksum;

import questiontypes.checksum.utils.CheckSumQuestionUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * Generate a bitwise checksum question - based on the algorithm implemented in {#link CheckSumQuestionUtils}
 * The following code is suitable for use in autograder:
 <pre>
 import java.util.Arrays;
 import java.util.Optional;

 public class Test {
    public static String[] possStrings = {
                        "jbxrjtnckphsurwvrttdawiotstsvawxsooykviqmuollnkqpibsgszgrdwiyxrnapethbupbp",
                        "iyclqqdkyafrbbngmscxhoiplakzrhuxlicljimqsjresblmhuooeiuejfzrjfmeatawzvfrvi",
                        "whcfsbzbqwetzxzdkyajtowzwphqzpntexfcotndibaqvheekzhknqnzgwtyqauezlinmltboee",
                        "dhqtpyvuibadzqxirmasqverscfgklwwwzaquxtexmtofowgtnjdwodmwmvlbcqhwjb",
                        "czufmvibvcelkooojryvcvcshstxgmhsozhlbohbohiboreehncuxfurnkydzpxtqhtsmnugzppfxvggixk",
                        "vqewhmmfxqykkfwjgxnjsvegdzdrfydatorumdmjzjvtbuhpbramoridctbnomeleirjyywwtxxzaigelwp"
                };
    public static byte checkSum = -34;

    public static void main(String[] args) {
        BitWiseCheckSum.possStrings = possStrings;
        BitWiseCheckSum.checkSum = checkSum;

        Optional<String> ans = Arrays.stream(BitWiseCheckSum.possStrings)
        .filter(x -> bitwiseCheckSum(x) == BitWiseCheckSum.checkSum).findFirst();
        if(!ans.isPresent()) {
            System.exit(1);
        }
        System.exit(BitWiseCheckSum.answer().equals(ans.get()) ? 0 : 1);
    }

    public static byte bitwiseCheckSum(final String str) {
        byte[] input = str.getBytes();
        byte checksum = 0;
        for (byte cur_byte : input) {
            checksum = (byte) (((checksum & 255) >>> 1) + ((checksum & 1) << 7));
            checksum = (byte) ((checksum + cur_byte) & 255);
        }
        return checksum;
    }
}
 </pre>
 */

public class BitwiseChecksum extends McqQuestion {

    private byte checkSum;
    private String correctString;

    @Override
    public String createQuestionTitle() {
        return "Bitwise checksum question";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Bitwise Checksum.** Which of the following strings generates the bitwise checksum ``"
                + checkSum + "``?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possStrings", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };

        final StringBuilder code = CodeUtils.questionCode("BitWiseCheckSum",
                formatter.format().append(CodeUtils.indentTextBlock(String.format("public static byte checkSum = %d;", checkSum))),
                "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public void createCalcData() {
        correctString = QuizUtils.genRandomString(CheckSumQuestionUtils.MIN_LEN,
                CheckSumQuestionUtils.MAX_LEN,CheckSumQuestionUtils.LOW_RNG, CheckSumQuestionUtils.HIGH_RNG);
        checkSum = CheckSumQuestionUtils.bitwiseCheckSum(correctString);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(correctString);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.genRandomString(CheckSumQuestionUtils.MIN_LEN,
                CheckSumQuestionUtils.MAX_LEN,CheckSumQuestionUtils.LOW_RNG, CheckSumQuestionUtils.HIGH_RNG));
    }
}
