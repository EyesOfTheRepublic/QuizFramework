import com.google.common.reflect.ClassPath;
import junit.framework.TestCase;
import org.junit.Test;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Question;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Test essential properties of all MCQ question types. Assumes all classes in the {@link questiontypes} package are
 * MCQ or Numeric questions <b>except</b> those in packages called <emph>util</emph>. Uses Google's Guava to get and filter
 * a set of classes that meet those criteria and which have the superclass {@link McqQuestion}. It then uses
 * 'vanilla' Java reflection to instantiate them and JUnit to test them
 */
public final class TestQuestionTypes extends TestCase {

    private static final int NUM_ANS = 5; //We arbitrarily set the number of answers to 5

    /**
     * Get All classes that extend {@link McqQuestion} in a named package (excluding those in sub
     * packages called 'utils'.
     * @param packageName the package name containing questions
     * @return A set of all classes that meet the criteria
     * @throws IOException if code file data not accessible
     */
    public Set<Class> getMcqQuestions(final String packageName, final Class clsVal) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(cls -> cls.getPackageName()
                        .contains(packageName))
                .filter(cls -> !cls.getPackageName()
                        .contains("utils"))
                .map(cls -> cls.load())
                .filter(cls -> cls.getSuperclass().equals(clsVal)).collect(Collectors.toSet());
    }

    /**
     * Iterate through the set of classes that run a range of tests on each.
     */
    @Test
    public void testMcqQuestions() {
        Set<Class> questions;
        try {
            questions = getMcqQuestions("questiontypes", McqQuestion.class);
            questions.addAll(getMcqQuestions("questiontypes", NumericQuestion.class));
        } catch(IOException io) {
            System.out.println(io.toString());
            return;
        }
        for (Class cls : questions) {
            try {
                Object obj = cls.getConstructor().newInstance();
                if (obj instanceof Question) {
                    handleTest((Question) obj);
                } else {
                    System.out.println("Unexpected class " + obj.getClass());
                }
            } catch (Exception e) {
                System.out.println("***" + cls.getName());
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }

    }

    /**
     * Run tests on a class
     * @param q class that extends either  {@link McqQuestion} or {@link NumericQuestion}
     */
    private void handleTest(final Question q) {
        //Handy for checking all classes are represented
        //System.out.println(q.getClass().toString());
        final Question question;
        if(q instanceof McqQuestion) {
            question = (McqQuestion)q;
            ((McqQuestion) question).createQuestion(NUM_ANS); //creates a set of answers of arbitrary length
        } else if (q instanceof NumericQuestion) {
            question = (NumericQuestion)q;
            ((NumericQuestion) question).createQuestion();
        } else {
            System.out.println("Class not correct: " + q.getClass().getName());
            return;
        }
        /*Uses {@link Question#checkAnswerSet} to check consistency of the answers: correct
        answers should correspond with those where the (optional) {checkAnswer) returns true, and false otherwise
        Note that checkAnswer is optional and this is only meaningful if it is implemented.
         */
        assertTrue(question.checkAnswerSet());
        //The list of "faulty" (erroneously correct/incorrect) answers is empty
        assertTrue(question.getFaultList().isEmpty());
        //The list of answers is NOT empty
        assertFalse(question.getAnswerList().isEmpty());
        //The list of answers is of length NUM_ANS - only applies to MCQ
        if (question instanceof McqQuestion) {
            assertTrue(question.getAnswerList().size() == NUM_ANS);
        }
        //There is only ONE correct answer
        assertEquals(question.getAnswerList().stream().filter(ans -> ans.isCorrect()).count(), 1);
        //There is a non-null title
        assertNotNull(question.getQuestionTitle());
        //There is non-null question text
        assertNotNull(question.getQuestionText());
        //The number of points available is positive
        assertTrue(question.getQuestionPoints() > 0);
    }
}


