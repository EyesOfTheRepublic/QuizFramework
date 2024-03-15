package quizframework;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a quiz - A quizframework.Quiz object is created with a title and a description (there is currently no other
 * constructor so these must be supplied). Once created, question objects can be added, and the quiz can be output in
 * a format compatible with <a href="https://github.com/gpoore/text2qti">text2qti</a>(which will generate a QTI file).
 */
public final class Quiz {

    private final List<Question> questionList = new ArrayList<>();

    //Questions that fail fault checking - answers do not match expectations
    private final List<Question> faultyQuestions = new ArrayList<>();
    private final String quizTitle;

    private final String quizDesc;

    /**
     * Create a quiz object with a title and a description
     * @param title the quiz title
     * @param desc the quiz description (in HTML format)
     */
    public Quiz(final String title, final String desc) {
        this.quizTitle = title;
        this.quizDesc = desc;
    }

    /**
     * Add a question to the quiz - questions must be added in order (there is currently no way to add questions
     * at other points in the quiz)
     * @param question the {@link Question} to be added
     * @return true if all the answers pass the correctness test and false otherwise
     */
    public boolean addQuestion(final Question question) {
        questionList.add(question); //Note adding duplicates is allowed!
        if(!question.checkAnswerSet()) {
            faultyQuestions.add(question);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check to see if the quiz has questions that do not pass the fault checking process
     *
     * @return true if there are faults, false otherwise
     */
    public boolean hasFaults() {
        return !faultyQuestions.isEmpty();
    }

    /**
     * Generate output in a form suitable for <a href="https://github.com/gpoore/text2qti">text2qti</a> to turn into
     * a QTI file (which can be imported into, say, Canvas).
     * @param stream the output stream to write the text2Qti format text to.
     */
    public void generateText2Qti(final PrintStream stream) {
        if (hasFaults()) {
            stream.println("***QUESTIONS HAVE FAULTS");
            stream.println("***Re-run using toString() to get more information about what and why");
        }
        stream.println("Quiz title: " + quizTitle);
        stream.println("Quiz description: " + quizDesc + "\n");
        int qNum = 1;
        for(Question question: questionList) {
            if (!question.checkAnswerSet()) {
                stream.println("***FAULTY QUESTION");
            }
            stream.println(question.toText2Qti(qNum));
            qNum++;
        }
    }

    /**
     * Format the quiz as a readable string
     *
     * @return the quiz as a readable string
     */

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Title: %s%n", quizTitle));
        builder.append(String.format("Quiz description: %s%n", quizDesc));
        for (Question question: questionList) {
            builder.append("\n").append(question);
        }
        return builder.toString();
    }

}
