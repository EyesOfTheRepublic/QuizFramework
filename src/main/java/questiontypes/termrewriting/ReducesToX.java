package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * Which of a set of strings reduces to X using the first set of rewrite rules? Code suitable for use in autograder:
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

    public static String[] possAnswers = {
                "accaccacbacbcbcacbcaacbbabcabcabaabcacbaccccbcaababacabcbabbcbabaccaccb",
                "aabccabbcbbcbbcabbaacbcabababcaaaccaaabaccacbabcbbccacaaababcabbcbcb",
                "bbacabbbabbaccaabaccccbbcacaacacabbccacaaaaacbaabacaaabbcabaabcbaca",
                "aabcacaccccacccccbcbaccbcacaaabaccbcaccaccaaaacabccaabcacacaccccbcaaacab",
                "aacacbcacccbcbbbcaaacccaccbbcbbcacababbaaccbacaaabbacaabcaaacbaaacc",
                "abcccbaacaabaabcaabaaabbbbbaaaaaaccabaababbbbbacacbcbaccbbbbcbaccbcabaaabcc"
    };

    public static void main(String[] args) {
        ReduceToX.possAnswers = possAnswers;

        for(String posAns : possAnswers) {
            String workStr = posAns;
            boolean done = false;
            while(!done) {
                String temp = runOneStep(workStr, REWRITE_MAP);
                if (temp.equals(workStr)) {
                    done = true;
                }
                workStr = temp;
            }
            if(workStr.equals("X")) {
                System.exit(posAns.equals(ReduceToX.answer()) ? 0 : 1);
            }
        }
        System.exit(1);
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
public class ReducesToX extends McqQuestion {

    private String answerString = "";

    @Override
    public String createQuestionTitle() {
        return "Which one reduces to X?";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder =
                new StringBuilder("**Rewrite to X.** Which of the following strings reduces to ``X`` when the term rewriting rule set 1 is run until no more changes occur?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final ArrayFormatter<Answer> formatter = new ArrayFormatter<>("public static String[] possAnswers", this.answerList)
        {
            @Override
            public String outputItem(Answer item) {
                return super.outputItem(item.makeQuotedStringAnswer());
            }
        };
        final StringBuilder code = CodeUtils.questionCode("ReduceToX", formatter.format(), "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public void createCalcData() {
        String tempString;
        String inString;
        //Run strings to completion until we get one that reduces to X - potentially unbounded of course.
        do {
            inString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                    CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);
            tempString = RewritingUtils.runToCompletion(inString, CoreRewritingData.REWRITE_MAP);
        } while (!tempString.equals("X"));
        answerString = inString;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(answerString);
    }

    @Override
    public Answer createIncorrectAnswer() {
        String tempString;
        String inString;
        //Run strings to completion until we get one that *does not* reduce to X - potentially unbounded of course.
        do {
            inString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                    CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);
            tempString = RewritingUtils.runToCompletion(inString, CoreRewritingData.REWRITE_MAP);
        } while (tempString.equals("X"));
        return Answer.makeIncorrectAnswer(inString);
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        String answerStrCheck = answer.getQuestionAnswer();
        boolean done = false;

        while (!done) {
            String startVal = answerStrCheck;
            answerStrCheck = RewritingUtils.replaceAll(startVal, CoreRewritingData.REWRITE_MAP);
            if (answerStrCheck.equals(startVal)) {
                done = true;
            }
        }

        return answerStrCheck.equals("X");
    }
}
