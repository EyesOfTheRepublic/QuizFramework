package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * What is the result of decrypting a string with a particular 'key' - array size? Note this seems to be more
 * difficult than encrypting. Code suitable for use in autograder:
 <pre>
 public class Test {
    public static String cypherText = "lgkbmdlkbkfaifcdkgkjmbgeljdlcbambaddaidcheiabeclbjijebgmlbjd";
    public static int columns = 12;

    public static void main(String[] args) {
        TransposeDecrypt.cypherText = cypherText;
        TransposeDecrypt.columns = columns;

        char[][] encryptArray = new char[columns][cypherText.length() / columns];
        String result = "";

        int charLoc = 0;
        for (int i = 0; i < columns ; i++) {
            for(int j = 0; j < cypherText.length() / columns; j++) {
                encryptArray[i][j]= cypherText.charAt(charLoc);
                charLoc++;
            }
        }

        for (int i = 0; i < cypherText.length() / columns; i++) {
            for (int j = 0; j < columns; j++) {
                result += encryptArray[j][i];
            }
        }

        System.exit(result.equals(TransposeDecrypt.answer()) ? 0 : 1);
    }
}
 </pre>
 */
public class Decryption extends McqQuestion {

    private String sourceString;
    private int key;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "Decrypting a string using transposition";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder( "**Decryption.** What is the result of decrypting the string  \n``" + sourceString + "``  \nwith a transposition cypher that has" +
                " been encrypted using an array with " + key + " columns?").append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String codeTemplate = """
               public static String cypherText = "%s";
               public static int columns = %d;
               """;
        final StringBuilder code = CodeUtils.questionCode("TransposeDecrypt",
                new StringBuilder(CodeUtils.indentTextBlock(String.format(codeTemplate, sourceString, key))), "String");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
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
        answerText = CypherUtils.decode(sourceString, key);
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
        char[][] encryptArray = new char[key][sourceString.length() / key];
        StringBuilder result = new StringBuilder();

        int charLoc = 0;
        for (int i = 0; i < key ; i++) {
            for(int j = 0; j < sourceString.length() / key; j++) {
                encryptArray[i][j]= sourceString.charAt(charLoc);
                charLoc++;
            }
        }

        for (int i = 0; i < sourceString.length() / key; i++) {
            for (int j = 0; j < key; j++) {
                result.append(encryptArray[j][i]);
            }
        }

        return result.toString().equals(answer.getQuestionAnswer());
    }

}
