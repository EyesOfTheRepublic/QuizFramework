package questiontypes.termrewriting;

import questiontypes.termrewriting.utils.CoreRewritingData;
import questiontypes.termrewriting.utils.RewritingUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.QuizUtils;

/**
 * What is the result of running the main set of rewriting rules once? (We could make this more generic and able to
 * accept multiple sets of rules)
 */
public class RewritingNSteps extends Question {

    private int numSteps;
    private String sourceString = "";
    private String answerString;

    @Override
    public String createQuestionTitle() {
        return "Running the rewriting rules N times";
    }

    @Override
    public String createQuestionText() {
        return "What is the result of running term rewriting rule set 1 for " + numSteps + " times on the string  \n``"
                + sourceString + "``?";
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
    public boolean checkAnswer(Answer answer) {
        String answerVal = sourceString;
        for(int i = 0; i < numSteps; i++) {
            answerVal = RewritingUtils.runOneStep(answerVal,CoreRewritingData.REWRITE_MAP);
        }
        return answerVal.equals(answer.getQuestionAnswer());
    }
}
