package questiontypes.verybasic;
import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.Random;
/**
 Simple example question asking what is the next biggest even number after some number X
 Here is code suitable for autograder:
 <pre>
 public class Test {

 public static void main(String[] args) {
 NextBiggestEven.number = 205;

 int givenAns = NextBiggestEven.answer();
 System.exit(givenAns == (NextBiggestEven.number % 2 == 0 ? NextBiggestEven.number + 2 : NextBiggestEven.number + 1) ? 0 : 1);
 }

 }
 </pre>
 */
public class NextBiggestEven extends McqQuestion {

    private final Random rnd = new Random();

    private int number;

    @Override
    public String createQuestionTitle() {
        return "Next Biggest Even Number";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Next Biggest Even Number.** What is the next even number that is bigger than " + number + " ?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("NextBiggestEven",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int number = %d;", number))),
                "int");
        return builder.append(code).toString();
    }

    @Override
    public String createGeneralFeedback() {
        return "Some generic feedback";
    }

    @Override
    public String createCorrectFeedback() {
        return "Some feedback for the correct answer";
    }

    @Override
    public String createIncorrectFeedback() {
        return "Some general feedback for incorrect answers";
    }

    public void createCalcData() {
        number = rnd.nextInt(15) + 5;
    }

    @Override
    public int createQuestionPoints() {
        return 6;
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswerWithFeedback(Integer.toString(number % 2 == 0 ? number + 2 : number + 1),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Integer.toString(rnd.nextInt(20)),
                "some incorrect feedback");
    }
}
