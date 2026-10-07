package questiontypes.demo;

import quizframework.Answer;
import quizframework.McqQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Purely used for a demo video to show issue in c&p long strings
 */
public class AddNumberExample extends McqQuestion {

    private int a;
    private int b;

    @Override
    public String createQuestionTitle() {
        return "Add numbers";
    }

    @Override
    public String createQuestionText() {
        final StringBuilder builder = new StringBuilder("**Add Numbers.** What is the sum of the variables a and b in the code below?")
                .append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final StringBuilder code = CodeUtils.questionCode("AddNumbers",
                new StringBuilder(CodeUtils.indentTextBlock(String.format("public static int val1 = %d;", a)))
                        .append(CodeUtils.indentTextBlock(String.format("public static int val2 = %d;", b))),
                "int");
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        a = QuizUtils.genRandomInt(20, 100);
        b = QuizUtils.genRandomInt(20, 100);
    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(a+b));
    }

    @Override
    public Answer createIncorrectAnswer() {
        final int wrongAnswer = QuizUtils.genRandomInt(20, 200);
        return Answer.makeIncorrectAnswer(Integer.toString(wrongAnswer));
    }
}
