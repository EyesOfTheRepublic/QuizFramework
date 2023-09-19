package Test;

import questiontypes.checksum.CheckSumPairQuestion;
import questiontypes.checksum.CheckSumStringQuestion;
import questiontypes.checksum.CheckSumValueQuestion;
import questiontypes.crypto.Decryption;
import questiontypes.crypto.DoubleEncrypt;
import questiontypes.crypto.Encryption;
import questiontypes.crypto.NumCols;
import questiontypes.example.MultQuestionExample;
import questiontypes.example.SquareQuestionExample;
import questiontypes.location.DistanceTwoPoints;
import questiontypes.location.MinSecDistance;
import questiontypes.location.TotalDistance;
import questiontypes.location.WhichDistance;
import questiontypes.numbers.AddPairs;
import questiontypes.numbers.Factors;
import questiontypes.numbers.Fibonacci;
import questiontypes.numbers.Primes;
import questiontypes.numbers.PythTriplets;
import questiontypes.numbers.SophieGermain;
import questiontypes.termrewriting.AltRewriting;
import questiontypes.termrewriting.ReducesToX;
import questiontypes.termrewriting.RewritingNSteps;
import questiontypes.termrewriting.RewritingToCompletion;
import quizframework.Question;
import quizframework.Quiz;

public class Main {
    /*
     * Example of a quiz created using the framework - contains five sample questions.
     * There are still issues and things that could be better
     */
    public static void main(String[] args) {

        //quizframework.Quiz is class that represents a whole quiz - we initially create one with a title and description
        Quiz quiz = new Quiz("A sample quiz", "An example to show how this works");

        /* Quizzes consist of zero or more Questions - quizframework.Question is an abstract class that must be implemented to
        actually create a question of a specific type. Here we create a question that is specifically about
        squares - see the implementation of questiontypes.example.SquareQuestionExample and quizframework.Question for more information
         */
        Question squareExample = new SquareQuestionExample();
        /*Once created we build an actual question - multiple choice (MCQ) with 6 possible answers (1 will be correct)
        There can be as many answers as we want - ideally (but not yet) there would be more question types.
         */
        squareExample.createMcqAnswerSet(6);
        //Then we add it to the quiz
        quiz.addQuestion(squareExample);

        /*The remaining questions are created in a similar way below - note that they all include their own
        implementations of the abstract quizframework.Question class */
  /*      Question multExample = new MultQuestionExample();
        multExample.createMcqAnswerSet(6);
        quiz.addQuestion(multExample);

        Question checkValueQuestion = new CheckSumValueQuestion();
        checkValueQuestion.createMcqAnswerSet(6);
        quiz.addQuestion(checkValueQuestion);


        Question checkStringQuestion = new CheckSumStringQuestion();
        checkStringQuestion.createMcqAnswerSet(6);
        quiz.addQuestion(checkStringQuestion);

        Question checkPairQuestion = new CheckSumPairQuestion();
        checkPairQuestion.createMcqAnswerSet(6);
        quiz.addQuestion(checkPairQuestion);
*/
        //Real Questions...

        Question factorsQuestion = new Factors();
        factorsQuestion.createMcqAnswerSet(6);
        quiz.addQuestion(factorsQuestion);

        Question pythTriplets = new PythTriplets();
        pythTriplets.createMcqAnswerSet(6);
        quiz.addQuestion(pythTriplets);

        Question fibonacci = new Fibonacci();
        fibonacci.createMcqAnswerSet(6);
        quiz.addQuestion(fibonacci);

        Question pairSum = new AddPairs();
        pairSum.createMcqAnswerSet(6);
        quiz.addQuestion(pairSum);

        Question primes = new Primes();
        primes.createMcqAnswerSet(6);
        quiz.addQuestion(primes);

        Question sophieG = new SophieGermain();
        sophieG.createMcqAnswerSet(6);
        quiz.addQuestion(sophieG);

        Question nStepRewrite = new RewritingNSteps();
        nStepRewrite.createMcqAnswerSet(6);
        quiz.addQuestion(nStepRewrite);

        Question completeRewrite = new RewritingToCompletion();
        completeRewrite.createMcqAnswerSet(6);
        quiz.addQuestion(completeRewrite);

        Question whichIsX = new ReducesToX();
        whichIsX.createMcqAnswerSet(6);
        quiz.addQuestion(whichIsX);

        Question altSets = new AltRewriting();
        altSets.createMcqAnswerSet(6);
        quiz.addQuestion(altSets);

        Question encode = new Encryption();
        encode.createMcqAnswerSet(6);
        quiz.addQuestion(encode);

        Question numCols = new NumCols();
        numCols.createMcqAnswerSet(6);
        quiz.addQuestion(numCols);

        Question decode = new Decryption();
        decode.createMcqAnswerSet(6);
        quiz.addQuestion(decode);

        Question doubleEncode = new DoubleEncrypt();
        doubleEncode.createMcqAnswerSet(6);
        quiz.addQuestion(doubleEncode);

        Question singleDistance = new DistanceTwoPoints();
        singleDistance.createMcqAnswerSet(6);
        quiz.addQuestion(singleDistance);

        Question whichDistance = new WhichDistance();
        whichDistance.createMcqAnswerSet(6);
        quiz.addQuestion(whichDistance);

        Question totalDistance = new TotalDistance();
        totalDistance.createMcqAnswerSet(6);
        quiz.addQuestion(totalDistance);

        Question minSec = new MinSecDistance();
        minSec.createMcqAnswerSet(6);
        quiz.addQuestion(minSec);

        /*
        The generateText2Qti method outputs a quiz in *markdown* format to the specified PrintStream - in this case,
        System.out (the screen). This format is suitable for use with a Python tool text2Qti:
        https://github.com/gpoore/text2qti
        That can generate QTI format - which Canvas can import. Ideally, (but not yet) there would be other output formats.
         */
        //quiz.generateText2Qti(System.out);
        System.out.println(quiz);
        System.out.close(); //makes more sense if this is a file
    }
}
