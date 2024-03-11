package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

/**
 * How many columns did the array have that encrypted a particular string
 */
public class NumCols extends Question {

    private String sourceString;
    private int key;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "How many columns were used";
    }

    @Override
    public String createQuestionText() {
        return "How many columns were used to encrypt the string ``" + sourceString + "`` to the following string? ``"
                + answerText + "``";
    }

    @Override
    public void createCalcData() {
        //Create a random key
        key = QuizUtils.genRandomInt(CypherUtils.MIN_KEY, CypherUtils.MAX_KEY);
        //Create random plain text whose length is multiple of the key
        do {
            sourceString = QuizUtils.genRandomString(CypherUtils.MIN_STR_LENG, CypherUtils.MAX_STR_LEN,
                    CypherUtils.LOW_CHAR, CypherUtils.HIGH_CHAR);
        } while (sourceString.length() % key != 0);

        //Encode it
        answerText = CypherUtils.encode(sourceString, key);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(key));
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(Integer.toString(QuizUtils.genRandomInt(CypherUtils.MIN_KEY, CypherUtils.MAX_KEY)));
    }

    //We don't use the utilities we have written to more accurately check what a student might write
    @Override
    public boolean checkAnswer(Answer answer) {
        int key = Integer.parseInt(answer.getAnswer());
        char[][] encryptArray = new char[sourceString.length() / key][key];
        String result = "";

        int charLoc = 0;
        for (int i = 0; i < sourceString.length() / key; i++) {
            for(int j = 0; j < key; j++) {
                encryptArray[i][j]= sourceString.charAt(charLoc);
                charLoc++;
            }
        }

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < sourceString.length() / key; j++) {
                result += encryptArray[j][i];
            }
        }

        return result.equals(answerText);
    }

}
