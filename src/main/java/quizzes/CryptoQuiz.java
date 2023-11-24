package quizzes;

import questiontypes.crypto.Decryption;
import questiontypes.crypto.DoubleEncrypt;
import questiontypes.crypto.Encryption;
import questiontypes.crypto.NumCols;
import questiontypes.numbers.Primes;
import questiontypes.numbers.SophieGermain;
import quizframework.Question;
import quizframework.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Generate quizzes based on the transposition cypher questions and two of the numbers questions
 */

public class CryptoQuiz {

    private static final String quizDesc = """
            <h3>Transposition Cyphers</h3> \
            <p>The first four questions in this quiz are based on Transposition Cyphers. \
            You can find (and should already have read) background information on Transposition Cyphers in the In-Class Test Information module \
            n Canvas. You will most easily be able to solve the Transposition Cypher problems using two-dimensional arrays.</p> \
            <p>Remember: cut-and-paste long strings from the questions; do not try to type them in.</p> \
            <pre> \
            /* \
             * Prints an array of characters row-by-row in a readable way. \
             * You may need to add the keyword static after public \
             */ \
            public void formatArray(char[][] charArray) { \
            	for (char[] row : charArray) { \
            		for (char item : row) { \
            			System.out.print(" " + item); \
            		} \
            		System.out.println(); \
            	} \
            } \
            </pre> \
            <h3>Prime Numbers</h3> \
            <p>The next two questions relate to prime numbers.</p>""";
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Transposition Cyphers and Numbers 3",
                GenQuizData.HEADER + quizDesc + GenQuizData.RESOURCES);

        //Transposition questions
        Question encrypt = new Encryption();
        encrypt.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(encrypt);

        Question decrypt = new Decryption();
        decrypt.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(decrypt);

        Question numCols = new NumCols();
        numCols.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(numCols);

        Question doubleEncrypt = new DoubleEncrypt();
        doubleEncrypt.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(doubleEncrypt);

        //Prime number questions
        Question prime = new Primes();
        prime.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(prime);

        Question sophieG = new SophieGermain();
        sophieG.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(sophieG);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Transposition3";
            try {
                PrintStream stream = new PrintStream(fileName + ".txt");
                quiz.generateText2Qti(stream);
                stream.close();
            }catch (FileNotFoundException fne) {
                System.out.println("Cannot open " + fileName);
            }
        }
    }
}
