package quizframework;

import java.util.Objects;

/**
 * Class to represent a question's answer - which may or may not be correct. The actual answer is always a string - as
 * that is what it will be represented as, ultimately, in the code (there are more 'elegant' solutions to this, but they
 * do mean more complex code - and possibly more cryptic errors). Answers can also either be correct or incorrect, and
 * optionally include feedback.
 * Note that not having feedback currently means the feedback field is just null, which
 * should be fixed.
 */
public final class Answer {
    private final String questionAnswer;
    private final boolean isCorrect;
    private final String feedback;
    private double errorRange; //Used ONLY in numeric questions - if non-zero adds a range to the accepted answer

    /**
     * Return the answer
     *
     * @return the question's answer
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * Return the (optional) feedback
     *
     * @return the feedback if present; null if not
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Return the answer's correctness
     *
     * @return true if this is the/a correct answer; false otherwise
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * Get the allowed error range - this only makes sense for numeric questions.
     * @return the error range
     */
    public double getErrorRange() {
        return errorRange;
    }

    /**
     * Set the required error range - this only makes sense for numeric questions
     * @param errorRange the permitted error range
     */
    public void setErrorRange(double errorRange) {
        if (errorRange > 0.0) {
            this.errorRange = errorRange;
        }
    }

    /**
     * Factory method to create a correct answer without feedback
     *
     * @param answer the (correct) answer
     * @return the constructed quizframework.Answer object (with feedback == null)
     */
    public static Answer makeCorrectAnswer(final String answer) {
        return new Answer(answer, null, true);
    }

    /**
     * Factory method to create an incorrect answer without feedback
     *
     * @param answer the (incorrect) answer
     * @return the constructed quizframework.Answer object (with feedback == null)
     */
    public static Answer makeIncorrectAnswer(final String answer) {
        return new Answer(answer, null, false);
    }

    /**
     * Factory method to create a correct answer with feedback
     *
     * @param answer   the (correct) answer
     * @param feedback the answer-specific feedback
     * @return the constructed quizframework.Answer object
     */
    public static Answer makeCorrectAnswerWithFeedback(final String answer,
                                                       final String feedback) {
        return new Answer(answer, feedback, true);
    }

    /**
     * Factory method to create an incorrect answer with feedback
     *
     * @param answer   the (incorrect) answer
     * @param feedback the answer-specific feedback
     * @return the constructed quizframework.Answer object
     */
    public static Answer makeIncorrectAnswerWithFeedback(final String answer,
                                                         final String feedback) {
        return new Answer(answer, feedback, false);
    }

    /*
    Constructor for quizframework.Answer - private - you should always use the factory methods above
     */
    private Answer(final String answer, final String feedback,
                   final boolean isCorrect) {
        this.questionAnswer = answer;
        this.isCorrect = isCorrect;
        this.feedback = feedback;
        this.errorRange = 0.0;
    }

    /**
     * Used to generate a version of an answer that is a string and needs to appear in quotes
     * @param ans the answer that needs to be quoted
     * @return the same answer but with the text in quotes
     */
    public Answer makeQuotedStringAnswer() {
        return new Answer("\"" + questionAnswer + "\"", feedback, isCorrect);
    }

    /**
     * Method to return the <strong>answer text only</strong> as a string
     *
     * @return the answer text
     */
    @Override
    public String toString() {
        return questionAnswer;
    }

    /**
     * Implement the equals method to allow us to consider and object to be equal to a quizframework.Answer object if and only if
     * it is an instance of quizframework.Answer and the answer text is
     * the same
     *
     * @param obj the object to check for equality
     * @return true if the objects are equal (identical or have the same answer text)
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Answer ans)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return (ans.getQuestionAnswer().equals(this.questionAnswer));
    }

    /**
     * Compute the hashcode in a way compatible with equals - compute it only using the answer text
     *
     * @return hashcode computed from the answer text
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.questionAnswer);
    }
}
