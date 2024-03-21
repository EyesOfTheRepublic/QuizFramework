package quizframework;

import quizframework.utils.CodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Abstract class intended to form the basis of new question types. Some methods are abstract (and need to implementations);
 * others have default implementations that you can override if you choose.
 * Questions need a title, description, data seeds (data used to generate the question, which can appear in the description),
 * a way to compute the correct answer and a way to compute incorrect answers. Optionally you can also override the number of
 * points available (this defaults to 1) and the general, correct and incorrect feedback (these default to null, which should
 * ideally be changed though Canvas does not display all of it anyway).
 */

public abstract class Question {

    private String questionTitle;
    private String questionText;

    private String generalFeedback;
    private String correctAnswerFeedback;
    private String incorrectAnswerFeedback;

    private int points;
    private final List<Answer> answerList = new ArrayList<>();
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
        addQuestionTitle(createQuestionTitle());
        addQuestionText(createQuestionText());
        //Prevent questions having zero/negative points
        addQuestionPoints(Math.max(createQuestionPoints(), 1));
        addGeneralFeedback(createGeneralFeedback());
        addCorrectFeedback(createCorrectFeedback());
        addIncorrectAnswerFeedback(createIncorrectFeedback());
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

        for (Answer answer : getAnswerList()) {
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
     * Only meaningfully called the appropriate method to create the question (in the relevant question type class)
     * generating different question types.
     *
     * @return the String representation of the
     */
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder(getQuestionTitle());
        builder.append("\n");
        builder.append(getQuestionText());
        builder.append("\n");
        builder.append("Points: ").append(getQuestionPoints()).append("\n");
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

            final List<Answer> list = getAnswerList();
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
     * Generate the question test in a format suitable for text2qti
     * @see <a href="https://github.com/gpoore/text2qti">text2qti</a> Note the format of the answers is question
     * type specific and generated by the appropriate implementation of the abstract method
     * {@link #generateQtiAnswerSet(StringBuilder)}
     *
     * @param qNum the number that should appear in the questions
     * @return the (markDown) format string
     */
    public final String toText2Qti(final int qNum) {
        final StringBuilder builder = new StringBuilder("Title: " + getQuestionTitle() + "\n");
        builder.append("Points: ").append(getQuestionPoints()).append("\n");
        builder.append(CodeUtils.outputTextBlock(qNum + ". ", getQuestionText()));
        getGeneralFeedback().ifPresent(fb -> builder.append(CodeUtils.outputTextBlock("... ", fb)));
        getCorrectAnswerFeedback().ifPresent(fb -> builder.append(CodeUtils.outputTextBlock("+ ", fb)));
        getIncorrectAnswerFeedback().ifPresent(fb -> builder.append(CodeUtils.outputTextBlock("- ", fb)));

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
        final List<Answer> list = new ArrayList<>(getAnswerList());
        java.util.Collections.shuffle(list);
        return list;
    }

    //Setters, getters...
    /**
     * Set the question title
     * @param questionTitle the question title
     */
    public void addQuestionTitle(final String questionTitle) {
        this.questionTitle = questionTitle;
    }

    /**
     * Set descriptive text for the question
     * @param questionText the question description
     */
    public void addQuestionText(final String questionText) {
        this.questionText = questionText;
    }

    /**
     * Set the points available for this question
     * @param points the questions' points/marks
     */
    public void addQuestionPoints(final int points) {
        this.points = points;
    }

    /**
     * Add general feedback for this question. This does not relate to correct/incorrect answers but is meant to
     * be a general explanation of the question (perhaps with some guidance on how to answer it)
     * @param feedback the general feedback text
     */
    public void addGeneralFeedback(final String feedback) {
        this.generalFeedback = feedback;
    }

    /**
     * Add general correct feedback - feedback to be displayed if the user answers correctly. Note this is additional
     * to any answer-specific feedback (see {link @Answer}
     * @param feedback correct answer feedback
     */
    public void addCorrectFeedback(final String feedback) {
        this.correctAnswerFeedback = feedback;
    }

    /**
     * Add general incorrect feedback - feedback to be displayed if the user answers incorrectly. Note this is additional
     * to any answer-specific feedback (see {link @Answer}
     * @param feedback incorrect answer feedback
     */
    public void addIncorrectAnswerFeedback(final String feedback) {
        this.incorrectAnswerFeedback = feedback;
    }

    /**
     * Add a new answer if and only if that answer is not already present. An answer is present if and only if the answer
     * text matches an existing answer (feedback or correctness of the answer is not considered)
     * @param answer the {@link Answer} object to be added
     * @return true if the {@link Answer} was added, false if was already present (and not added)
     */
    public boolean addAnswer(final Answer answer) {
        if (getAnswerList().contains(answer)) {
            return false;
        } else {
            getAnswerList().add(answer);
            return true;
        }
    }

    /**
     * Return the question title
     * @return the question title
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    /**
     * Return the question description text
     * @return the question description text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Return the points/marks available for the question
     * @return the question marks/points
     */
    public int getQuestionPoints() {
        return points;
    }

    /**
     * Return the list of answers
     * @return a list of {@link Answer} objects representing the correct and incorrect question answers
     */
    public List<Answer> getAnswerList() {
        return answerList;
    }

    /**
     * Return the general feedback (used if the answer is correct <strong>or</strong> incorrect
     * @return Optional containing the general feedback if present or empty otherwise
     */
    public Optional<String> getGeneralFeedback() {
        return generalFeedback == null ? Optional.empty() : Optional.of(generalFeedback);
    }

    /**
     * Return the general feedback for a correctly-answered question
     * @return Optional containing the correct answer feedback if present or empty otherwise
     */
    public Optional<String> getCorrectAnswerFeedback() {
        return correctAnswerFeedback == null ? Optional.empty() : Optional.of(correctAnswerFeedback);
    }

    /**
     * Return the general feedback for an incorrectly-answered question
     * @return Optional containing the incorrect answer feedback if present or empty otherwise
     */
    public Optional<String> getIncorrectAnswerFeedback() {
        return incorrectAnswerFeedback == null ? Optional.empty() : Optional.of(incorrectAnswerFeedback);
    }
}
