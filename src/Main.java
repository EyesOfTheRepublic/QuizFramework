public class Main {
    /*
     * Example of a quiz created using the framework - contains five sample questions.
     * There are still issues and things that could be better
     */
    public static void main(String[] args) {

        //Quiz is class that represents a whole quiz - we initially create one with a title and description
        Quiz quiz = new Quiz("A sample quiz", "An example to show how this works");

        /* Quizzes consist of zero or more Questions - Question is an abstract class that must be implemented to
        actually create a question of a specific type. Here we create a question that is specifically about
        squares - see the implementation of SquareQuestionExample and Question for more information
         */
        Question squareExample = new SquareQuestionExample();
        /*Once created we build an actual question - multiple choice (MCQ) with 5 possible answers (1 will be correct)
        There can be as many answers as we want - ideally (but not yet) there would be more question types.
         */
        squareExample.createMcqAnswerSet(5);
        //Then we add it to the quiz
        quiz.addQuestion(squareExample);

        /*The remaining questions are created in a similar way below - note that they all include their own
        implementations of the abstract Question class */
        Question multExample = new MultQuestionExample();
        multExample.createMcqAnswerSet(5);
        quiz.addQuestion(multExample);

        Question checkValueQuestion = new CheckSumValueQuestion();
        checkValueQuestion.createMcqAnswerSet(5);
        quiz.addQuestion(checkValueQuestion);


        Question checkStringQuestion = new CheckSumStringQuestion();
        checkStringQuestion.createMcqAnswerSet(5);
        quiz.addQuestion(checkStringQuestion);

        Question checkPairQuestion = new CheckSumPairQuestion();
        checkPairQuestion.createMcqAnswerSet(5);
        quiz.addQuestion(checkPairQuestion);

        /*
        The generateText2Qti method outputs a quiz in *markdown* format to the specified PrintStream - in this case,
        System.out (the screen). This format is suitable for use with a Python tool text2Qti:
        https://github.com/gpoore/text2qti
        That can generate QTI format - which Canvas can import. Ideally, (but not yet) there would be other output formats.
         */
        quiz.generateText2Qti(System.out);
        System.out.close(); //makes more sense if this is a file
    }
}
