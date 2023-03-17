import java.io.PrintStream;
import java.util.ArrayList;

public class Quiz {

    private ArrayList<Question> questionList = new ArrayList<>();
    private String quizTitle;

    private String quizDesc;

    public Quiz(final String title, final String desc) {
        this.quizTitle = title;
        this.quizDesc = desc;
    }

    public boolean addQuestion(final Question question) {
        questionList.add(question); //Note adding duplicates is allowed!
        return true; //until we think of appropriate data validation...
    }

    public void generateText2Qti(final PrintStream stream) {
        stream.println("Title: " + quizTitle);
        stream.println("Quiz description: " + quizDesc + "\n");
        int qNum = 1;
        for(Question question: questionList) {
            stream.println(question.toText2Qti(qNum));
            qNum++;
        }
        stream.close(); //Should we do this here???
    }

}
