import java.util.Objects;

/**
 * Class to represent a question's answer - which may or may not be correct. The actual answer is always a string - as
 * that is what it will be represented as, ultimately, in the code (there are more 'elegant' solutions to this, but they
 * do mean more complex code - and possibly more cryptic errors). Answers can also either be correct or incorrect, and
 * optionally include feedback.
 * Note that not having feedback currently means the feedback field is just null, which
 * should be fixed.
 */
public class Answer {
    private final String answer;
    private final boolean isCorrect;
    private final String feedback;

    /**
     * Return the answer
     *
     * @return the question's answer
     */
    public String getAnswer() {
        return answer;
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
     * Factory method to create a correct answer without feedback
     *
     * @param answer the (correct) answer)
     * @return the constructed Answer object (with feedback == null)
     */
    public static Answer makeCorrectAnswer(final String answer) {
        return new Answer(answer, null, true);
    }

    /**
     * Factory method to create an incorrect answer without feedback
     *
     * @param answer the (incorrect) answer
     * @return the constructed Answer object (with feedback == null)
     */
    public static Answer makeIncorrectAnswer(final String answer) {
        return new Answer(answer, null, false);
    }

    /**
     * Factory method to create a correct answer with feedback
     *
     * @param answer   the (correct) answer)
     * @param feedback the answer-specific feedback
     * @return the constructed Answer object
     */
    public static Answer makeCorrectAnswerWithFeedback(final String answer,
                                                       final String feedback) {
        return new Answer(answer, feedback, true);
    }

    /**
     * Factory method to create an incorrect answer with feedback
     *
     * @param answer   the (incorrect) answer)
     * @param feedback the answer-specific feedback
     * @return the constructed Answer object
     */
    public static Answer makeIncorrectAnswerWithFeedback(final String answer,
                                                         final String feedback) {
        return new Answer(answer, feedback, false);
    }

    /*
    Constructor for Answer - private - you should always use the factory methods above
     */
    private Answer(final String answer, final String feedback,
                   final boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.feedback = feedback;
    }

    /**
     * Method to return the <strong>answer text only</strong> as a string
     *
     * @return the answer text
     */
    @Override
    public String toString() {
        return answer;
    }

    /**
     * Implement the equals method to allow us to consider and object to be equal to an Answer object if and only if
     * it is an instance of Answer and the answer text is
     * the same
     *
     * @param obj the object to check for equality
     * @return true if the objects are equal (identical or have the same answer text)
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Answer)) {
            return false;
        }
        Answer ans = (Answer) obj;
        if (obj == this) {
            return true;
        }
        return (ans.getAnswer().equals(this.answer));
    }

    /**
     * Compute the hashcode in a way compatible with equals - compute it only using the answer text
     *
     * @return hashcode computed from the answer text
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.answer);
    }
}
