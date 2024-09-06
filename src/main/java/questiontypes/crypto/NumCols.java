package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * How many columns did the array have that encrypted a particular string. Code for autograder:
 <pre>
 public class Test {
    public static String plainText = "dkdeaeddfmafdhdhffjgihckgbdicickdjdammhjahhaaigbeigjkgfedidkllighglaag";
    public static String cypherText = "ddchdkhiaidfcadefkikajdglegjbldideidhaigfcmghmkmjgaghklfbjgaddafahiheg";

    public static void main(String[] args) {
        TransposeNumCols.plainText = plainText;
        TransposeNumCols.cypherText = cypherText;

        String enc = "";
        int key = 1;
        //NOTE these are the *current* values of MIN_KEY and MAX_KEY!
        for(int i = 9; i <= 15; i++) {
            if (cypherText.equals(encode(plainText, i))) {
                System.exit(i == TransposeNumCols.answer() ? 0 : 1);
            }
        }
        System.exit(1);
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
public class NumCols extends McqQuestion {

    private String plainText;
    private int key;
    private String cypherText;

    @Override
    public String createQuestionTitle() {
        return "How many columns were used";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("How many columns were used to encrypt the string  \n``"
                + plainText + "``  \nto the following string? ``"
                + cypherText + "``").append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String codeTemplate = """
               public static String plainText = "%s";
               public static String cypherText = "%s";
               """;
        final StringBuilder code = CodeUtils.questionCode("TransposeNumCols",
                new StringBuilder(CodeUtils.indentTextBlock(String.format(codeTemplate, plainText, cypherText))),
                "int");
        return builder.append(CodeUtils.toCodeBlock(code)).toString();
    }

    @Override
    public void createCalcData() {
        //Create a random key
        key = QuizUtils.genRandomInt(CypherUtils.MIN_KEY, CypherUtils.MAX_KEY);
        //Create random plain text whose length is multiple of the key
        do {
            plainText = QuizUtils.genRandomString(CypherUtils.MIN_STR_LENG, CypherUtils.MAX_STR_LEN,
                    CypherUtils.LOW_CHAR, CypherUtils.HIGH_CHAR);
        } while (plainText.length() % key != 0);

        //Encode it
        cypherText = CypherUtils.encode(plainText, key);
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
    public boolean checkAnswer(final Answer answer) {
        int answerKey = Integer.parseInt(answer.getQuestionAnswer());
        char[][] encryptArray = new char[plainText.length() / answerKey][answerKey];
        StringBuilder result = new StringBuilder();

        int charLoc = 0;
        for (int i = 0; i < plainText.length() / answerKey; i++) {
            for(int j = 0; j < answerKey; j++) {
                encryptArray[i][j]= plainText.charAt(charLoc);
                charLoc++;
            }
        }

        for (int i = 0; i < answerKey; i++) {
            for (int j = 0; j < plainText.length() / answerKey; j++) {
                result.append(encryptArray[j][i]);
            }
        }

        return result.toString().equals(cypherText);
    }

}
