package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.QuizUtils;

/**
 * When run until no more changes happen, how many term rewriting steps are needed?
 */
public class RewritingToCompletion extends Question {

    private String sourceString;
    private int numSteps;

    @Override
    public String createQuestionTitle() {
        return "Term rewriting until there are no more changes";
    }

    @Override
    public String createQuestionText() {
        return "How many times does term rewriting rule set 1 need to be run on the string  \n``"
                + sourceString + "``  \nbefore no more changes happen?";
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
            steps++;
            if (rewrittenString.equals(tempString)) {
                done = true;
            } else {
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
            count++;
        }
        return count == Integer.parseInt(answer.getQuestionAnswer());
    }
}
