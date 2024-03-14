package quizzes;

import questiontypes.numbers.AddPairs;
import questiontypes.numbers.PythTriplets;
import questiontypes.termrewriting.AltRewriting;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.termrewriting.RewritingToCompletion;
import quizframework.Question;
import quizframework.Quiz;

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
          <h4>Pairs of Numbers and Pythagorean Triples</h4> \
          <p>The remaining two questions ask you to find the only number in a list which does not add to another number in the list \
          to make a specified value; and to work out how many groups of three numbers in a list are Pythagorean.</p>""";

    public static void main(String[] args) {
        Quiz quiz = new Quiz("Rewriting and Numbers 3",
                GenQuizData.HEADER + QUIZ_DESC + GenQuizData.RESOURCES);

        //Rewriting questions
        Question rewriteN = new RewritingNSteps();
        rewriteN.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(rewriteN);

        Question toCompletion = new RewritingToCompletion();
        toCompletion.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(toCompletion);

        Question reducesToX = new ReducesToX();
        reducesToX.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(reducesToX);

        Question altRewrite = new AltRewriting();
        altRewrite.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(altRewrite);

        //Pair Addition and Pythagorean Triples Questions
        Question pairs = new AddPairs();
        pairs.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(pairs);

        Question pythag = new PythTriplets();
        pythag.createMcqAnswerSet(GenQuizData.NUM_ANSWERS);
        quiz.addQuestion(pythag);

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
