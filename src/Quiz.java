import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Class representing a quiz - A Quiz object is created with a title and a description (there is currently no other
 * constructor so these must be supplied). Once created, question objects can be added, and the quiz can be output in
 * a format compatible with <a href="https://github.com/gpoore/text2qti">text2qti</a>(which will generate a QTI file).
 */
public class Quiz {

    private ArrayList<Question> questionList = new ArrayList<>();
    private String quizTitle;

    private String quizDesc;

    /**
     * Create a quiz object with a title and a description
     * @param title the quiz title
     * @param desc the quiz description
     */
    public Quiz(final String title, final String desc) {
        this.quizTitle = title;
        this.quizDesc = desc;
    }

    /**
     * Add a question to the quiz - questions must be added in order (there is currently no way to add questions
     * at other points in the quiz
     * @param question the {@link Question} to be added
     * @return currently returns true
     */
    public boolean addQuestion(final Question question) {
        questionList.add(question); //Note adding duplicates is allowed!
        return true; //until we think of appropriate data validation...
    }

    /**
     * Generate output in a form suitable for <a href="https://github.com/gpoore/text2qti">text2qti</a> to turn into
     * a QTI file (which can be imported into, say, Canvas).
     * @param stream the output stream to write the text2Qti format text to.
     */
    public void generateText2Qti(final PrintStream stream) {
        stream.println("Title: " + quizTitle);
        stream.println("Quiz description: " + quizDesc + "\n");
        int qNum = 1;
        for(Question question: questionList) {
            stream.println(question.toText2Qti(qNum));
            qNum++;
        }
    }

}
