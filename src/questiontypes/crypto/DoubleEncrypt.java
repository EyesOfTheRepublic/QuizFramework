package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.QuizUtils;

/**
 * What is the result of encrypting a string with a particular 'key' then again with another key?
 */
public class DoubleEncrypt extends Question {

    private String sourceString;
    private int key;
    private int key2;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "Encrypting a string using transposition";
    }

    @Override
    public String createQuestionText() {
        return "What is the result of encrypting the string with a transposition cypher " + sourceString + " using an array"
                + " with " + key + " columns, and then encrypting it again withe an array with " + key2 + "columns?";
    }

    @Override
    public void createCalcData() {
        //Create a random key
        key = QuizUtils.genRandomInt(CypherUtils.MIN_KEY, CypherUtils.MAX_KEY);
        key2 = QuizUtils.genRandomInt(CypherUtils.MIN_KEY, CypherUtils.MAX_KEY);
        //Create random plain text whose length is multiple of the key
        do {
            sourceString = QuizUtils.genRandomString(CypherUtils.MIN_STR_LENG, CypherUtils.MAX_STR_LEN,
                    CypherUtils.LOW_CHAR, CypherUtils.HIGH_CHAR);
        } while (sourceString.length() % key != 0 && sourceString.length() % key2 != 0);

        //Encode it twice
        String tempText = CypherUtils.encode(sourceString, key);
        answerText = CypherUtils.encode(tempText, key2);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(answerText);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.permuteString(answerText,
                0.5, 0.4, 3));
    }

    //We don't use the utilities we have written to more accurately check what a student might write
    @Override
    public boolean checkAnswer(Answer answer) {

        String tempVal =  encode(sourceString, key);
        String result = encode(tempVal, key2);
        return result.equals(answer.getAnswer());
    }

    private String encode(String sourceString, int key) {
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

        return result;
    }

}
