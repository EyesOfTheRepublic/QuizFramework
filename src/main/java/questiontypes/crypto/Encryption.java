package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.QuizUtils;

/**
 * What is the result of encrypting a string with a particular 'key' - array size?
 */
public class Encryption extends Question {

    private String sourceString;
    private int key;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "Encrypting a string using transposition";
    }

    @Override
    public String createQuestionText() {
        return "What is the result of encrypting the string  \n``" + sourceString + "``  \nwith a transposition cypher using an array"
                + " with " + key + " columns?";
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
        return Answer.makeCorrectAnswer(answerText);
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(QuizUtils.permuteString(answerText,
                0.5, 0.4, 3));
    }

    //We don't use the utilities we have written to more accurately check what a student might write
    @Override
    public boolean checkAnswer(final Answer answer) {
        char[][] encryptArray = new char[sourceString.length() / key][key];
        StringBuilder result = new StringBuilder();

        int charLoc = 0;
        for (int i = 0; i < sourceString.length() / key; i++) {
            for(int j = 0; j < key; j++) {
                encryptArray[i][j]= sourceString.charAt(charLoc);
                charLoc++;
            }
        }

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < sourceString.length() / key; j++) {
                result.append(encryptArray[j][i]);
            }
        }

        return result.toString().equals(answer.getQuestionAnswer());
    }

}
