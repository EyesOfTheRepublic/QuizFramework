import java.util.ArrayList;

/**
 * Contains all data relating to a question - note that it is never necessary to directly deal with this: all user
 * code should be added in new classes that extend {@Question}. Contains: question title; question description, general question feedback,
 * incorrect and correct question feedback (distinct from feedback that is specific to an {@Answer}, the points score for the question,
* and a list of {@Answer} objects representing correct and incorrect answers. It also contains the {@Seed} data - any data (numbers, strings etc.)
 * used by the question to compute the correct answer
 */
public class QuestionData {
    private String questionTitle;
    private String questionText;

    private String generalFeedback;
    private String correctAnswerFeedback;
    private String incorrectAnswerFeedback;

    private int points;
    private final ArrayList<Answer> answerList; //may be more complex than required

    /**
     * Create a new empty Question object
     */
    public QuestionData() {
        answerList = new ArrayList<>();
    }

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
     * to any answer-specific feedback (see {@Answer}
     * @param feedback correct answer feedback
     */
    public void addCorrectFeedback(final String feedback) {
        this.correctAnswerFeedback = feedback;
    }

    /**
     * Add general incorrect feedback - feedback to be displayed if the user answers incorrectly. Note this is additional
     * to any answer-specific feedback (see {@Answer}
     * @param feedback incorrect answer feedback
     */
    public void addIncorrectAnswerFeedback(final String feedback) {
        this.incorrectAnswerFeedback = feedback;
    }

    /**
     * Add a new answer if and only if that answer is not already present. An answer is present if and only if the answer
     * text matches an existing answer (feedback or correctness of the answer is not considered)
     * @param answer the {@Answer} object to be added
     * @return true if the {@Answer} was added, false if was already present (and not added)
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
     * @return a list of {@Answer} objects representing the correct and incorrect question answers
     */
    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    /**
     * Return the general feedback (used if the answer is correct <strong>or</strong> incorrect
     * @return the general feedback text
     */
    public String getGeneralFeedback() {
        return generalFeedback;
    }

    /**
     * Return the general feedback for a correctly-answered question
     * @return the correct answer feedback
     */
    public String getCorrectAnswerFeedback() {
        return correctAnswerFeedback;
    }

    /**
     * Return the general feedback for an incorrectly-answered question
     * @return the incorrect answer feedback
     */
    public String getIncorrectAnswerFeedback() {
        return incorrectAnswerFeedback;
    }
}
