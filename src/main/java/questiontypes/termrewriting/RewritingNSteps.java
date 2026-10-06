package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * What is the result of running the main set of rewriting rules once? (We could make this more generic and able to
 * accept multiple sets of rules). The following code is suitable for use in autograder:
 * <pre>
import java.awt.*;
import java.util.*;

public class Test {
    public static final String[][] REWRITE_MAP
            = {{"bYb", "Y"},
            {"c", "Y"},
            {"XXbYaX", "X"},
            {"XXba", "X"},
            {"Xa", "X"},
            {"XY", "X"},
            {"bb", "X"}};

    public static String[] possResults = {
        "XYabYaaaXXXXXbYaabaXbaY", "XYaYYaaaXXXXbbbaabaXXaY", "XYabYbaaXXXXXbYaabaaXaY",
        "XYbbYaaaXXXabaYaabXXXaY", "XYabaaXaXXXXabYaabYXbaY", "XYaXXbaaXXXYbbbaXYaaaaY"
    };
    public static int numTimes = 7;

    public static String sourceString = "bbbbcacbaabcbaacaccabcaaabbcbbabbacbbabbcabbbaccabcaababbccbac";

    public static void main(String[] args) {
        RewriteNTimes.possResults = possResults;
        RewriteNTimes.numTimes = numTimes;

        String workingStr = sourceString;
        for(int i = 0; i < numTimes; i++) {
            workingStr = runOneStep(workingStr, REWRITE_MAP);
        }
        System.exit(workingStr.equals(RewriteNTimes.answer()) ? 0 : 1);
    }

    public static String runOneStep(String input, final String[][] rules) {
        for (int i = 0; i < rules.length; i++) {
            input = input.replaceAll(rules[i][0], rules[i][1]);
        }
        return input;
    }
}
 * </pre>
 */
public class RewritingNSteps extends McqQuestion {

    private int numSteps;
    private String sourceString = "";
    private String answerString;

    @Override
    public String createQuestionTitle() {
        return "Running the rewriting rules N times";
    }

    @Override
    public String createQuestionText() {
        //final StringBuilder builder = new StringBuilder("**Rewrite N Times.** What is the result of running ALL of term rewriting rule set 1 for "
        final StringBuilder builder = new StringBuilder("**Rewrite N Times.** What is the result of running ALL of term rewriting rules "
                + numSteps + " times on the string  \n``"
                + sourceString + "``?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possResults", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };
        final StringBuilder code = CodeUtils.questionCode("RewriteNTimes",
                formatter.format().append(CodeUtils.indentTextBlock(String.format("public static int numTimes = %d;", numSteps)))
                        .append(CodeUtils.indentTextBlock(String.format("public static String sourceString = \"%s\";", sourceString))),
        "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    /*Generate the number of steps the rewriting should run for; the source string; and the correct answer string */
    @Override
    public void createCalcData() {
        //Generate the number of steps and the source string
        numSteps = QuizUtils.genRandomInt(CoreRewritingData.STEP_MIN, CoreRewritingData.STEP_MAX);
        sourceString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);

        //Run the rules for the number of steps to generate the correct answer
        String tempString = sourceString;
        for(int i = 0; i < numSteps; i++) {
            tempString = RewritingUtils.runOneStep(tempString, CoreRewritingData.REWRITE_MAP);
        }
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
        String answerVal = sourceString;
        for(int i = 0; i < numSteps; i++) {
            answerVal = RewritingUtils.runOneStep(answerVal,CoreRewritingData.REWRITE_MAP);
        }
        return answerVal.equals(answer.getQuestionAnswer());
    }
}
