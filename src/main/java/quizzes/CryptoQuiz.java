package quizzes;

import questiontypes.crypto.Decryption;
import questiontypes.crypto.DoubleEncrypt;
import questiontypes.crypto.Encryption;
import questiontypes.crypto.NumCols;
import questiontypes.numbers.Primes;
import questiontypes.numbers.SophieGermain;
import quizframework.McqQuestion;
import quizframework.Quiz;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Generate quizzes based on the transposition cypher questions and two of the numbers questions
 */

public class CryptoQuiz {

    private static final String QUIZ_DESC = """
            <h3>Transposition Cyphers</h3> \
            <p>The first four questions in this quiz are based on Transposition Cyphers. \
            You can find (and should already have read) background information on Transposition Cyphers in the In-Class Test Information module \
            n Canvas. You will most easily be able to solve the Transposition Cypher problems using two-dimensional arrays.</p> \
            <p>Remember: cut-and-paste long strings from the questions; do not try to type them in.</p> \
            <pre>\
            /*<br/>\
             * Prints an array of characters row-by-row in a readable way. <br/>\
             * You may need to add the keyword static after public <br/>\
             */ <br/>\
            public void formatArray(char[][] charArray) { <br/>\
                for (char[] row : charArray) { <br/>\
                    for (char item : row) { <br/>\
                        System.out.print(" " + item); <br/>\
                    } <br/>\
                    System.out.println(); <br/>\
                } <br/>\
            } <br/>\
            </pre> \
            <h3>Prime Numbers</h3> \
            <p>The next two questions relate to prime numbers.</p>""";
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Transposition Cyphers and Numbers 3",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Transposition questions
        McqQuestion encrypt = new Encryption();
        encrypt.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(encrypt);

        McqQuestion decrypt = new Decryption();
        decrypt.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(decrypt);

        McqQuestion numCols = new NumCols();
        numCols.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(numCols);

        McqQuestion doubleEncrypt = new DoubleEncrypt();
        doubleEncrypt.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(doubleEncrypt);

        //Prime number questions
        McqQuestion prime = new Primes();
        prime.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(prime);

        McqQuestion sophieG = new SophieGermain();
        sophieG.createQuestion(GenQuizData.NUM_ANSWERS);
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
