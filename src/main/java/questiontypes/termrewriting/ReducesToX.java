package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

/**
 * Which of a set of strings reduces to X using the first set of rewrite rules?
 */
public class ReducesToX extends Question {

    private String answerString = "";

    @Override
    public String createQuestionTitle() {
        return "Which one reduces to X?";
    }

    @Override
    public String createQuestionText() {
        return "Which of the following strings reduces to <kbd>X</kbd> when the term rewriting rule set 1 is run until no more changes occur?";
    }

    @Override
    public void createCalcData() {
        String tempString;
        String inString;
        //Run strings to completion until we get one that reduces to X - potentially unbounded of course.
        do {
            inString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                    CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);
            tempString = RewritingUtils.runToCompletion(inString, CoreRewritingData.rewriteMap);
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
            tempString = RewritingUtils.runToCompletion(inString, CoreRewritingData.rewriteMap);
        } while (tempString.equals("X"));
        return Answer.makeIncorrectAnswer(inString);
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        String answerString = answer.getAnswer();
        boolean done = false;

        while (!done) {
            String startVal = answerString;
            answerString = RewritingUtils.replaceAll(startVal, CoreRewritingData.rewriteMap);
            if (answerString.equals(startVal)) {
                done = true;
            }
        }

        return answerString.equals("X");
    }
}
