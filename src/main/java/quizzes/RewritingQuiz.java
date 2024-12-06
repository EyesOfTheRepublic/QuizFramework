package quizzes;

import questiontypes.termrewriting.AltRewriting;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.termrewriting.RewritingToCompletion;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;
import quizframework.utils.QuizUtils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Generate quizzes based on the term rewriting cypher questions and two of the numbers questions
 */

public class RewritingQuiz {

    private static final String QUIZ_DESC = """
          <h3>Term Rewriting</h3><p>The first four questions in this test are based on based on Term Rewriting. \
          You can find (and should already have read) background information on Term Rewriting in the In-Class Test Information module \
          on Canvas.</p>\
          <p>You will need to apply ONE OR BOTH of the following set sof rules <emph>in the order given below</emph> to successfully solve these problems.</p>\
          <h4>Term Rewriting Rule Set 1</h4>\
          <ul><li>bYb -> Y</li><li>c -> Y</li><li>XXbYaX -> X</li><li>XXba -> X</li><li>Xa -> X</li><li>XY -> X</li><li>bb -> X</li></ul>\
           <h4>Term Rewriting Rule Set 2</h4>\
          <ul><li>aYb -> Y</li><li>cY -> Y</li><li>XabYa -> X</li><li>XXX -> X</li><li>Xa -> X</li></ul>\
          <p>Remember: cut-and-paste long strings from the questions; do not try to type them in.</p> \
          """;

    public static void main(String[] args) {
        Quiz quiz = new Quiz("Rewriting and Numbers 3",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Rewriting questions
        McqQuestion rewriteN = new RewritingNSteps();
        rewriteN.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewriteN);

        McqQuestion toCompletion = new RewritingToCompletion();
        toCompletion.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(toCompletion);

        McqQuestion reducesToX = new ReducesToX();
        reducesToX.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(reducesToX);

        McqQuestion altRewrite = new AltRewriting();
        altRewrite.createQuestion(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(altRewrite);

        if (quiz.hasFaults()) {
            System.out.println("Quiz has Errors:");
            System.out.println(quiz);
        } else {
            final String fileName = "Rewriting3";
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
