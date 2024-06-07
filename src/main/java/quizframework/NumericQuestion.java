package quizframework;

import quizframework.utils.CodeUtils;

/**
 * Abstract class that is used to create questions with a numerical answer - override this class and provide (minimally)
 * implementations of:
 * <ul>
 *     <li>{@link #createQuestionText()}</li>
 *     <li>{@link #createQuestionText()}</li>
 *     <li>{@link #createCalcData()}</li>
 *     <li>{@link #createCorrectAnswer()}</li>
 * </ul>
 * Optionally you can also override the methods that define feedback and the number of points available, and the method
 * to independently compute and check the answer {@link Question#checkAnswer(Answer)} - see {@link Question}
 */
public abstract class NumericQuestion extends Question {

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
    public final Answer createIncorrectAnswer() {
        return null;
    }

    /**
     * Create a numeric question with the specified correct answer.
     *
     * @return true for success and false for failure (should not happen)
     */

    public final boolean createQuestion() {
        questionType = QuestionType.NUMERIC;
        buildQuestionBasics();
        //Must be generated LAST to allow answers to appear in it - this is clunky...
        addQuestionText(createQuestionText());
        //Add the correct answer first to ensure an incorrect one randomly-matching it is not already present
        return addAnswer(createCorrectAnswer());
    }

    /*
    Generate an answer in QTI format for a numeric question
     */
    protected final void generateQtiAnswerSet(final StringBuilder builder) {
        builder.append(CodeUtils.outputTextBlock("=", getAnswerList().get(0).getQuestionAnswer()));
    }
}
