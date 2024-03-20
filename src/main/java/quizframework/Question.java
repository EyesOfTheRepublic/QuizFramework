package quizframework;

import quizframework.utils.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class intended to form the basis of new question types. Some methods are abstract (and need to implementations);
 * others have default implementations that you can override if you choose.
 * Questions need a title, description, data seeds (data used to generate the question, which can appear in the description),
 * a way to compute the correct answer and a way to compute incorrect answers. Optionally you can also override the number of
 * points available (this defaults to 1) and the general, correct and incorrect feedback (these default to null, which should
 * ideally be changed though Canvas does not display all of it anyway).
 */

public abstract class Question {

    protected final QuestionData questData = new QuestionData();
    protected final List<Answer> faultList = new ArrayList<>();
    protected QuestionType questionType;

    protected enum QuestionType {
        MCQ,
        NUMERIC
    }

    /* Abstract Methods - MUST be implemented */

    /*
     * Abstract method to return a question title. A typical implementation will just return a constant string
     *
     * @return the question title
     */
    protected abstract String createQuestionTitle();

    /*
     * Abstract method to return the question description. A typical implementation will return a string with embedded
     * data used to compute the correct answer for this specific question.
     *
     * @return the question description text (commonly with embedded data fields)
     */
    protected abstract String createQuestionText();

    /*
     * Generate any data that will be used by the question. For example, if you want to generate a question "What is the
     * square of X?", where X is generated (and potentially different) for each use of the question, then you need to
     * generate (probably) a random integer.
     * This method is guaranteed to be called <strong>before</strong> any others when generating a question, so the
     * data will be available when computing the correct and incorrect answers, and can also appear in te description.
     */
    protected abstract void createCalcData();

    /*
     * Create and return a correct answer to the question. Typically,
     * this will include the implementation that is your solution to the problem (either directly or indirectly).
     *  However, you may wish to separately implement the solution you think students are likely to choose in
     * {@link Question#checkAnswer(Answer)}
     *
     * @return the {@link Answer} object that is the correct answer to the question
     */
    protected abstract Answer createCorrectAnswer();

    /*
     * Create and return an incorrect answer. <strong>This must be implemented if a question needs to display incorrect answers</strong> - not
     * all question types need to. This will typically be called multiple times, and incorrect answers need
     * to be unique. </strong>However, that is handled elsewhere and <strong>there is no requirement to ensure that here</strong>
     *
     * @return an {@link Answer} object that is the incorrect answer to the question.
     */
    protected abstract Answer createIncorrectAnswer() ;

    /* Overridable Methods - CAN be implemented and some MUST be for some question types */

    /**
     * Return the number of points for the question. By default, this returns 1 (but can be optionally overridden)
     *
     * @return the number of points (defaults to 1)
     */
    public int createQuestionPoints() {
        return 1;
    }

    /**
     * Return the general feedback for the question - defaults to null (which should be fixed) but can be overridden
     *
     * @return the general feedback to the question
     */
    protected String createGeneralFeedback() {
        return null;
    }

    /**
     * Return the general correct answer feedback for the question - defaults to null (which should be fixed) but can be overridden
     *
     * @return the general correct answer feedback to the question
     */
    public String createCorrectFeedback() {
        return null;
    }

    /**
     * Return the general incorrect answer feedback for the question - defaults to null (which should be fixed) but can be overridden
     *
     * @return the general incorrect answer feedback to the question
     */
    public String createIncorrectFeedback() {
        return null;
    }

    /**
     * Check that the answer is correctly either correct or wrong - defaults to returning true if the answer is marked correct
     * and false otherwise. Can be overridden to compute the correctness of the tested answer. The rationale is that an implementation
     * of this method should contain an example of how to solve the problem as a test and sample solution. Note this would not
     * normally be called directly but by the {@link #checkAnswerSet() checkAnswerSet} method.
     *
     * @param answer The answer being tested for correctness
     * @return true if the answer is correct, false otherwise.
     */
    public boolean checkAnswer(final Answer answer) {
        return answer.isCorrect();
    }

    /* Operational Methods - CANNOT be overridden */

    /*
    Do all things common to all question types
     */
    protected final void buildQuestionBasics() {
        createCalcData();//This needs to be first to ensure the data is available to compute question text and answers
        questData.addQuestionTitle(createQuestionTitle());
        questData.addQuestionText(createQuestionText());
        //Prevent questions having zero/negative points
        questData.addQuestionPoints(Math.max(createQuestionPoints(), 1));
        questData.addGeneralFeedback(createGeneralFeedback());
        questData.addCorrectFeedback(createCorrectFeedback());
        questData.addIncorrectAnswerFeedback(createIncorrectFeedback());
    }

    /**
     * Check the correctness of all the recorded answers by calling {@link #checkAnswer(Answer answer) checkAnswer} for
     * each one. For debugging returns a list of answers that do not match the expected result.
     * By default, checkAnswer just uses the correct/incorrect stored in each {@link Answer Answer} and so
     * will always return an empty list. Override {@link #checkAnswer(Answer answer) checkAnswer} with a sample/reference
     * implementation of the answer to the question to check its correctness.
     *
     * @return true if the answerset is correct and false otherwise
     */
    public final boolean checkAnswerSet() {

        for (Answer answer : questData.getAnswerList()) {
            //If checkAnswer does not agree with the recorded correctness of the answer
            if (answer.isCorrect() && !checkAnswer(answer)
                    || !answer.isCorrect() && checkAnswer(answer)) {
                faultList.add(answer);
            }
        }
        return faultList.isEmpty();
    }

    /**
     * Get the list of non-matching answers based on running {@link #checkAnswerSet() checkAnswerSet} - correct answer
     * marked wrong on incorrect answers marked correct.
     *
     * @return the list of faulty answers.
     */
    protected final List<Answer> getFaultList() {
        return faultList;
    }

    /**
     * Used to represent the question as a readable string - <emph>provided the question is fault free</emph>.
     * The correct answer is always returend <emph>first</emph> - unlike {@link #toText2Qti(int) toText2Qti} where
     * the orders are random (this is because this method is mainly used for question checking).
     * If there are faults in the question, only those answers which are not correct (do not pass the fault testing)
     * are output, with the correct answer first.
     * Only meaningfully called
     * after you have called {@link #createQuestion(int answers) createMcqAnswerSet} (or possible future methods)
     * generating different question types.
     *
     * @return the String representation of the
     */
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder(questData.getQuestionTitle());
        builder.append("\n");
        builder.append(questData.getQuestionText());
        builder.append("\n");
        builder.append("Points: ").append(questData.getQuestionPoints()).append("\n");
        builder.append("Type: ").append(questionType).append("\n");

        if (!faultList.isEmpty()) {
            builder.append("QUESTION DOES NOT PASS FAULT CHECKING\n");
            builder.append("The following answers do not match the expected value:\n");
            for (Answer ans : faultList) {
                builder.append(ans.getQuestionAnswer())
                        .append(" should be: ")
                        .append(ans.isCorrect() ? "correct\n" : "incorrect\n");
            }
        } else {

            final List<Answer> list = questData.getAnswerList();
            for (Answer ans : list) {
                builder.append(ans.getQuestionAnswer());
                if (ans.isCorrect()) {
                    builder.append(" *");
                }
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Generate the question test in a format suitable for
     *
     * @param qNum the number that should appear in the questions
     * @return the (markDown) format string suitable for text2qti
     * @see <a href="https://github.com/gpoore/text2qti">text2qti</a>.
     * Answer order is randomized. The format is a restricted form of MarkDown.
     */
    public final String toText2Qti(final int qNum) {
        final StringBuilder builder = new StringBuilder("Title: " + questData.getQuestionTitle() + "\n");
        builder.append("Points: ").append(questData.getQuestionPoints()).append("\n");
        builder.append(CodeUtils.outputTextBlock(qNum + ". ", questData.getQuestionText()));
        if (questData.getGeneralFeedback() != null) {
            builder.append(CodeUtils.outputTextBlock("... ", questData.getGeneralFeedback()));
        }
        if (questData.getCorrectAnswerFeedback() != null) {
            builder.append(CodeUtils.outputTextBlock("+ ", questData.getCorrectAnswerFeedback()));
        }
        if (questData.getIncorrectAnswerFeedback() != null) {
            builder.append(CodeUtils.outputTextBlock("- ", questData.getIncorrectAnswerFeedback()));
        }

        generateQtiAnswerSet(builder);
        return builder.toString();
    }

    /*
    Generate the answers to the question in the relevant (question type-specific) QTI format
    Classes that implement question types should implement this method but *not* classes that create questions
     */
    protected abstract void generateQtiAnswerSet(StringBuilder builder);


    /*Shuffle an arraylist - used to randomize the order of answers in the list of possible answers (by default, in
    MCQ example, the correct answer will always be added first and will always be at the front, so this shuffles the order) */
    protected final List<Answer> randomize() {
        final List<Answer> list = new ArrayList<>(questData.getAnswerList());
        java.util.Collections.shuffle(list);
        return list;
    }
}
