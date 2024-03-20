package quizframework;

import quizframework.utils.CodeUtils;

import java.util.List;

/**
 * Abstract class that is used to create MCQ questions - override this class and provide (minimally)
 * implementations of:
 * <ul>
 *     <li>{@link #createQuestionText()}</li>
 *     <li>{@link #createQuestionText()}</li>
 *     <li>{@link #createCalcData()}</li>
 *     <li>{@link #createCorrectAnswer()}</li>
 *     <li>{@link #createIncorrectAnswer()}</li>
 * </ul>
 * Optionally you can also override the methods that define feedback and the number of points available, and the method
 * to independently compute and check the answer {@link Question#checkAnswer(Answer)} - see {@link Question}
 */
public abstract class McqQuestion extends Question {

    /* Abstract Methods - MUST be implemented */

    /**
     * Abstract method to return a question title. A typical implementation will just return a constant string
     * Overrides abstract method in {@link Question}
     *
     * @return the question title
     */
    @Override
    public abstract String createQuestionTitle();

    /**
     * Abstract method to return the question description. A typical implementation will return a string with embedded
     * data used to compute the correct answer for this specific question.
     * Overrides abstract method in {@link Question}
     *
     * @return the question description text (commonly with embedded data fields)
     */
    @Override
    public abstract String createQuestionText();

    /**
     * Generate any data that will be used by the question. For example, if you want to generate a question "What is the
     * square of X?", where X is generated (and potentially different) for each use of the question, then you need to
     * generate (probably) a random integer.
     * This method is guaranteed to be called <strong>before</strong> any others when generating a question, so the
     * data will be available when computing the correct and incorrect answers, and can also appear in te description.
     * Overrides abstract method in {@link Question}
     */
    @Override
    public abstract void createCalcData();

    /**
     * Create and return a correct answer to the question. Typically,
     * this will include the implementation that is your solution to the problem (either directly or indirectly). However,
     * you may wish to separately implement the solution you think students are likely to choose in {@link Question#checkAnswer(Answer)}
     * Overrides abstract method in {@link Question}
     *
     * @return the {@link Answer} object that is the correct answer to the question
     */
    @Override
    public abstract Answer createCorrectAnswer();

    /**
     * Must be implemented but not used here as NumericQuestion questions do not have incorrect answers that appear in the questions.
     * So overridden to return null and made final.
     * See {@link Question}
     *
     * @return an {@link Answer} object that is the incorrect answer to the question.
     */
    @Override
    public abstract Answer createIncorrectAnswer();


    /**
     * Create a multiple choice (MCQ) question with the specified number of answers (including both correct and incorrect
     * ones). It will check to see if the correct answer is already present, defined to mean that the correct answer is already
     * present - since the correct answer is added first, and the code ensures incorrect ones are unique and do not match
     * the correct one, this should not happen. However, although this question type only has one possible correct answer, future
     * question types may have multiple correct answers.
     *
     * @param numAnswers the number of answers (correct and incorrect) required.
     * @return true for success and false for failure (should not happen)
     */

    public final boolean createQuestion(final int numAnswers) {
        questionType = QuestionType.MCQ;
        buildQuestionBasics();
        //Add the correct answer first to ensure an incorrect one randomly-matching it is not already present
        if (!questData.addAnswer(createCorrectAnswer())) {
            return false;
        }

        //Add the required number of incorrect answers, ensuring they are unique
        final int incorrectAnswers = Math.max(1, numAnswers - 1);
        int incorrectCount = 0;
        while (incorrectCount < incorrectAnswers) {
            if (questData.addAnswer(createIncorrectAnswer())) {
                incorrectCount++;
            }
        }
        return true;
    }

    /*
   Generate a set of answers in text2qti format for an MCQ question
   */
    protected void generateQtiAnswerSet(final StringBuilder builder) {
        final List<Answer> list = randomize();
        char qItem = 'a';
        for (Answer ans : list) {
            final String qLabel = (ans.isCorrect() ? "*" : "") + qItem + ")";
            builder.append(CodeUtils.outputTextBlock(qLabel, ans.getQuestionAnswer()));
            if (ans.getFeedback() != null) {
                builder.append(CodeUtils.outputTextBlock("... ", ans.getFeedback()));
            }
            qItem++;
        }
    }
}
