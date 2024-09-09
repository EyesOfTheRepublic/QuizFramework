package questiontypes.crypto;

import questiontypes.crypto.utils.CypherUtils;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

/**
 * What is the result of encrypting a string with a particular 'key' - array size? Code to use in Autograder:
 <pre>
import java.util.List;
import java.util.Arrays;
import java.util.stream.*;
import java.awt.*;
import java.util.*;

public class Test {
    public static String plainText = "fcmbeiehghmfmkefjkbefcfbkbbgfddbkdckeliggdalmfmkmgjmbmaalgdllhefemciljjfmjfljlbmafejiiic";
    public static int columns = 11;

    public static void main(String[] args) {
        TransposeEncrypt.plainText = plainText;
        TransposeEncrypt.columns = columns;

        char[][] codeArray =
            new char[TransposeEncrypt.plainText.length()/TransposeEncrypt.columns][TransposeEncrypt.columns];
        int count = 0;
        for(int i = 0; i < TransposeEncrypt.plainText.length()/TransposeEncrypt.columns; i++) {
            for(int j = 0; j < TransposeEncrypt.columns; j++) {
                codeArray[i][j] = TransposeEncrypt.plainText.charAt(count);
                count++;
            }
        }
        String retVal = "";
        for(int k = 0; k < TransposeEncrypt.columns; k++) {
            for(int l = 0; l < TransposeEncrypt.plainText.length()/TransposeEncrypt.columns; l++) {
                retVal += codeArray[l][k];
            }
        }
        System.exit(retVal.equals(TransposeEncrypt.answer()) ? 0 : 1);
    }
}

 </pre>
 */
public class Encryption extends McqQuestion {

    private String sourceString;
    private int key;
    private String answerText;

    @Override
    public String createQuestionTitle() {
        return "Encrypting a string using transposition";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Encryption.** What is the result of encrypting the string  \n``" + sourceString + "``  \nwith a transposition cypher using an array"
                + " with " + key + " columns?").append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String codeTemplate = """
               public static String plainText = "%s";
               public static int columns = %d;
               """;
        final StringBuilder code = CodeUtils.questionCode("TransposeEncrypt",
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
