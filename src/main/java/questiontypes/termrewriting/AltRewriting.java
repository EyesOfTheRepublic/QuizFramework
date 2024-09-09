package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * What is the result of alternately running one rule set then the other until no further changes occur?
 * The code below is suitable for use in autograder
 <pre>

public class Test {
    public static final String[][] REWRITE_MAP
            = {{"bYb", "Y"},
            {"c", "Y"},
            {"XXbYaX", "X"},
            {"XXba", "X"},
            {"Xa", "X"},
            {"XY", "X"},
            {"bb", "X"}};

    public static final String[][] ALT_REWRITE_MAP
        = {{"aYb", "Y"},
        {"cY", "Y"},
        {"XabYa", "X"},
        {"XXX", "X"},
        {"Xa", "X"}};

    public static String[] possResults = {
                "YbaaaaYYYYbYYaaYaabYaXaYYXYYYbbaYYXaabYXbabaYYYYabaYa", "YbaaaaYYYaXYYaaYYaYYababYXbYYbYaYYYYabYXbabaaXYYabaYa",
                "YbaaaaYYYYXYYaaYYaYaabaYYXbYYbYaYYYaabYXbabaYXbYabaYa", "YbaaaaYYYaXYYbYYYaYaabaYYXbYYbYaYYYaabYXaabaYXbYabaYa",
                "YbaaaaYYYYXYYaaYYaYaabaYYbbYYbYaYYYabbYXaaaXYXbYabaYa", "YbaaaYYYYYXYYaaYYaYaabaYaXbYYbYaYYYaaaYbXabbYXbYabaYa"
    };
    public static String sourceString = "cbaaaaccccbbcacccaaccaacbaabaccbbbccbcacccaabcbbbabacbbbcabaca";

    public static void main(String[] args) {
        AlternatingRewriting.sourceString = sourceString;
        AlternatingRewriting.possResults = possResults;

        boolean done = false;
        boolean isSet1 = true;
        String workStr = sourceString;
        while(!done) {
            String temp = workStr;
            if (isSet1) {
                temp = runOneStep(workStr, REWRITE_MAP);
            } else {
                temp = runOneStep(workStr, ALT_REWRITE_MAP);
            }
            if (temp.equals(workStr)) {
                done = true;
            }
            isSet1 = !isSet1;
            workStr = temp;
        }

        System.exit(workStr.equals(AlternatingRewriting.answer()) ? 0 : 1);
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
public class AltRewriting extends McqQuestion {

    private String sourceString;
    private String answerString;

    @Override
    public String createQuestionTitle() {
        return "Alternating term rewriting rules";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Alternate Rewriting.** Which of the following is the result of alternately running term rewriting rule sets 1 and 2 on the string  \n``"
                + sourceString + "``?  \nThat is, you run rule set 1 once, then you run rule set 2 once, then you run"
                + " rule set 1 again - and you alternate until there are no more changes.")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possResults", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };
        final StringBuilder code = CodeUtils.questionCode("AlternatingRewriting",
                formatter.format()
                        .append(CodeUtils.indentTextBlock(String.format("public static String sourceString = \"%s\";", sourceString))),
                "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public void createCalcData() {
        sourceString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);

        String tempString = sourceString;
        boolean done = false;
        boolean isSet1 = true;
        //Run the rules until no more changes happen, switching between rule sets
        do {
            String rewrittenString;
            if (isSet1) {
                rewrittenString = RewritingUtils.runOneStep(tempString, CoreRewritingData.REWRITE_MAP);
            } else {
                rewrittenString = RewritingUtils.runOneStep(tempString, CoreRewritingData.ALT_REWRITE_MAP);
            }
            isSet1 = !isSet1;
            if (rewrittenString.equals(tempString)) {
                done = true;
            } else {
                tempString = rewrittenString;
            }
        } while (!done);

        answerString = tempString;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(answerString);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.permuteString(answerString,0.5, 0.4, 5));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        String finalValue = answer.getQuestionAnswer();
        String workingString = sourceString;

        boolean done = false;
        boolean isSet1 = true;
        while (!done) {
            String startVal = workingString;
            if (isSet1) {
                workingString = RewritingUtils.replaceAll(workingString, CoreRewritingData.REWRITE_MAP);
            } else {
                workingString = RewritingUtils.replaceAll(workingString, CoreRewritingData.ALT_REWRITE_MAP);
            }
            isSet1 = !isSet1;
            if (workingString.equals(startVal)) {
                done = true;
            }
        }
        return workingString.equals(finalValue);
    }
}
