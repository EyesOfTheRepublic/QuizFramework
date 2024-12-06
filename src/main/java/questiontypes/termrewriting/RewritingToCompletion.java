package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * When run until no more changes happen, how many term rewriting steps are needed?
 <pre>
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

    public static String sourceString = "baccccababcccbabbbbcaaaabbcbbacbcbabccaaaaccccaaaccacabcbbbccccabaccaccb";


    public static void main(String[] args) {
        ReduceToCompletion.sourceString = sourceString;

        boolean done = false;
        int count = 0;
        while(!done) {
            String temp = runOneStep(sourceString, REWRITE_MAP);
            if (temp.equals(sourceString)) {
                done = true;
            } else {
                count++;
                sourceString = temp;
            }
        }
        System.exit(count == (ReduceToCompletion.answer()) ? 0 : 1);
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
public class RewritingToCompletion extends McqQuestion {

    private String sourceString;
    private int numSteps;

    @Override
    public String createQuestionTitle() {
        return "Term rewriting until there are no more changes";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder =
                new StringBuilder("**Rewrite to Completion.** How many times does term rewriting rule set 1 need to be run on the string  \n``"
                        + sourceString + "``  \nbefore no more changes happen? ONLY count the number of times that applying"
                        + " the rules results in a change.")
                        .append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final StringBuilder code = CodeUtils.questionCode("ReduceToCompletion",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static String sourceString = \"%s\";", sourceString))),
                "int");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public void createCalcData() {

        //Create the source string
        sourceString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);
        String tempString = sourceString;
        int steps = 0;
        boolean done = false;
        //Run the rules until no more changes happen
        do {
            String rewrittenString = RewritingUtils.runOneStep(tempString, CoreRewritingData.REWRITE_MAP);
            if (rewrittenString.equals(tempString)) {
                done = true;
            } else {
                steps++;
                tempString = rewrittenString;
            }
        } while (!done);
        numSteps = steps;
    }

    @Override
    public Answer createCorrectAnswer() {
        return  Answer.makeCorrectAnswer(Integer.toString(numSteps));
    }

    @Override
    public Answer createIncorrectAnswer() {
        int min = numSteps < CoreRewritingData.STEP_MIN ? CoreRewritingData.STEP_MIN : numSteps;
        int max = numSteps + CoreRewritingData.STEP_MAX;
        return Answer.makeIncorrectAnswer(Integer.toString(QuizUtils.genRandomInt(CoreRewritingData.STEP_MIN, max)));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        String startingString = sourceString;
        boolean done = false;
        int count = 0;
        while (!done) {
            String tempString = startingString;
            startingString = RewritingUtils.runOneStep(startingString, CoreRewritingData.REWRITE_MAP);
            done = startingString.equals(tempString);
            if (!done) {
                count++;
            }
        }
        return count == Integer.parseInt(answer.getQuestionAnswer());
    }
}
