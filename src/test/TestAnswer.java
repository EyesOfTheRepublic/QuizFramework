import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import quizframework.Answer;

public class TestAnswer extends TestCase {
    protected String correctAnswer;
    protected String incorrectAnswer;
    protected String feedback;

    @Before
    public void setup() {
        correctAnswer = "A correct answer string";
        incorrectAnswer = "An incorrect answer string";
        feedback = "Some feedback";
    }

    @Test
    public void testAnswer() {
        final Answer correct = Answer.makeCorrectAnswer(correctAnswer);
        final Answer incorrect = Answer.makeIncorrectAnswer(incorrectAnswer);
        final Answer correctFeedback = Answer.makeCorrectAnswerWithFeedback(correctAnswer, feedback);
        final Answer incorrectFeedback = Answer.makeIncorrectAnswerWithFeedback(incorrectAnswer, feedback);

        assertTrue(correct.isCorrect());
        assertFalse(incorrect.isCorrect());
        assertTrue(correctFeedback.isCorrect());
        assertFalse(incorrectFeedback.isCorrect());

        assertNull(correct.getFeedback());
        assertNull(incorrect.getFeedback());
        assertEquals(correctFeedback.getFeedback(), feedback);
        assertEquals(incorrectFeedback.getFeedback(), feedback);

        assertEquals(correct.getQuestionAnswer(), correctAnswer);
        assertEquals(incorrect.getQuestionAnswer(), incorrectAnswer);
        assertEquals(correctFeedback.getQuestionAnswer(), correctAnswer);
        assertEquals(incorrectFeedback.getQuestionAnswer(), incorrectAnswer);
    }
}
