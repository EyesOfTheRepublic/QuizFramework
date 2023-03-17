import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class intended to form the basis of new question types
 */

public abstract class Question {

    private QuestionData question = new QuestionData();
    private HashMap<String, Seed> seedList = new HashMap<>();

    public abstract String createQuestionTitle();
    public abstract String createQuestionText();

    public abstract void createDataSeeds();

    public abstract Answer createCorrectAnswer();

    public abstract Answer createIncorrectAnswer();

    public int createQuestionPoints() {
        return 1;
    }

    public String createGeneralFeedback() {
        return null;
    }

    public String createCorrectFeedback() {
        return null;
    }

    public String createIncorrectFeedback() {
        return null;
    }

    public final boolean createMcqAnswerSet(final int numAnswers) {
        createDataSeeds();
        question.addQuestionTitle(createQuestionTitle());
        question.addQuestionText(createQuestionText());
        //Prevent questions having zero/negative points
        question.addQuestionPoints(createQuestionPoints() < 1 ? 1 : createQuestionPoints());
        question.addGeneralFeedback(createGeneralFeedback());
        question.addCorrectFeedback(createCorrectFeedback());
        question.addIncorrectAnswerFeedback(createIncorrectFeedback());
        if (!question.addAnswer(createCorrectAnswer())) {
            return false;
        }

        int incorrectCount = 0;
        while (incorrectCount < numAnswers) {
            if (question.addAnswer(createIncorrectAnswer())) {
                incorrectCount++;
            }
        }
        return true;
    }

    public final void addQuizDataItem(final String key, final Seed val) {
        seedList.putIfAbsent(key, val);
    }

    public final Seed getQuizDataItem(final String key) {
        return seedList.getOrDefault(key, null);
    }

    @Override
    public final String toString() {
        ArrayList<Answer> list = randomize();
        StringBuilder builder = new StringBuilder(question.getQuestionTitle());
        builder.append("\n");
        builder.append(question.getQuestionText());
        builder.append("\n");
        builder.append("Points: " + question.getQuestionPoints() + "\n");
        for (Answer ans: list) {
            builder.append(ans.getAnswer());
            if (ans.isCorrect()) {
                builder.append(" *");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public final String toText2Qti(final int qNum) {
        StringBuilder builder = new StringBuilder("Title: " + question.getQuestionTitle() + "\n");
        builder.append("Points: " + question.getQuestionPoints() + "\n");
        builder.append(qNum + ". " + question.getQuestionText() + "\n");
        if (question.getGeneralFeedback() != null) {
            builder.append("... " + question.getGeneralFeedback() + "\n");
        }
        if (question.getCorrectAnswerFeedback() != null) {
            builder.append("+ " + question.getCorrectAnswerFeedback() + "\n");
        }
        if (question.getIncorrectAnswerFeedback() != null) {
            builder.append("- " + question.getIncorrectAnswerFeedback() + "\n");
        }
        ArrayList<Answer> list = randomize();
        char qItem = 'a';
        for (Answer ans: list) {
            if (ans.isCorrect()) {
                builder.append("*");
            }
            builder.append(qItem + ") " + ans.getAnswer());
            builder.append("\n");
            if (ans.getFeedback() != null) {
                builder.append("... " + ans.getFeedback() + "\n");
            }
            qItem++;
        }
        return builder.toString();
    }

    private ArrayList<Answer> randomize() {
        ArrayList<Answer> list = new ArrayList<>(question.getAnswerList());
        java.util.Collections.shuffle(list);
        return list;
    }
}
