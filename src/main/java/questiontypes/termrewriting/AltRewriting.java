package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.QuizUtils;

/**
 * What is the result of alternately running one rule set then the other until no further changes occur?
 */
public class AltRewriting extends McqQuestion {

    private String questionString;
    private String answerString;

    @Override
    public String createQuestionTitle() {
        return "Alternating term rewriting rules";
    }

    @Override
    public String createQuestionText() {
        return "Which of the following is the result of alternately running term rewriting rule sets 1 and 2 on the string  \n``"
                + questionString + "``?  \nThat is, you run rule set 1 once, then you run rule set 2 once, then you run"
                + " rule set 1 again - and you alternate until there are no more changes.";
    }

    @Override
    public void createCalcData() {
        questionString = QuizUtils.genRandomString(CoreRewritingData.MIN_LEN, CoreRewritingData.MAX_LEN,
                CoreRewritingData.LOW_RNG, CoreRewritingData.HIGH_RNG);

        String tempString = questionString;
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
        String workingString = questionString;

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
