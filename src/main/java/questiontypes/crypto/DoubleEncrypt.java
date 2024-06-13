package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * What is the result of encrypting a string with a particular 'key' then again with the same key?
 * Code for autograder:
 <pre>
 public class Test {
    public static String plainText = "daidemhcjjldadmlfhlmefkjlaaamffihlcgglchilelccicildkklggfgbkimemckmbkagejadllbcjllbc";
    public static int columns = 12;

    public static void main(String[] args) {
        DoubleEncrypt.plainText = plainText;
        DoubleEncrypt.columns = columns;

        String doubleEnc = encode(encode(plainText, columns), columns);
        System.exit(doubleEnc.equals(DoubleEncrypt.answer()) ? 0 : 1);
    }

    public static String encode(final String str, final int cols) {
        char[][] codeArray = new char[str.length()/cols][cols];
        int count = 0;
        for(int i = 0; i < str.length()/cols; i++) {
            for(int j = 0; j < cols; j++) {
                codeArray[i][j] = plainText.charAt(count);
                count++;
            }
        }
        String retVal = "";
        for(int k = 0; k < cols; k++) {
            for(int l = 0; l < str.length()/cols; l++) {
                retVal += codeArray[l][k];
            }
        }
    return retVal;
    }

}
 </pre>
 */
public class DoubleEncrypt extends McqQuestion {

    private String sourceString;
    private int key;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "Encrypting a string using transposition";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("What is the result of encrypting the string  \n``" + sourceString + "``  \nwith a transposition cypher using an array"
                + " with " + key + " columns, and then encrypting it AGAIN with an array WITH THE SAME NUMBER ("
                + key + ") OF COLUMNS?").append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String codeTemplate = """
               public static String plainText = "%s";
               public static int columns = %d;
               """;
        final StringBuilder code = CodeUtils.questionCode("DoubleEncrypt",
                new StringBuilder(CodeUtils.indentTextBlock(String.format(codeTemplate, sourceString,key))), "String");
        return builder.append(code).toString();
    }

    @Override
    public void createCalcData() {
        /*We need to produce a pair consisting of a string and an integer (key) such that:
        1. The length of the string is a multiple of the key;
        2. The length of the string is not equal to key * key - because the double encrypted text would be the same
        as the original plaintext in that case (does this matter?)
         */

        do {
            key = QuizUtils.genRandomInt(CypherUtils.MIN_KEY,CypherUtils.MAX_KEY);
            sourceString = QuizUtils.genRandomString(CypherUtils.MIN_STR_LENG, CypherUtils.MAX_STR_LEN,
                    CypherUtils.LOW_CHAR, CypherUtils.HIGH_CHAR);
        } while (sourceString.length() % key != 0 || sourceString.length() == key * key);

        //Encode it twice
        String tempText = CypherUtils.encode(sourceString, key);
        answerText = CypherUtils.encode(tempText, key);
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

        String tempVal =  encode(sourceString, key);
        String result = encode(tempVal, key);
        return result.equals(answer.getQuestionAnswer());
    }

    private String encode(String sourceString, int key) {
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

        return result.toString();
    }

}
