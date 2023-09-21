package test;

import questiontypes.checksum.BitwiseChecksum;
import questiontypes.checksum.CheckSumPairQuestion;
import questiontypes.checksum.CheckSumStringQuestion;
import questiontypes.checksum.CheckSumValueQuestion;
import questiontypes.crypto.Decryption;
import questiontypes.crypto.DoubleEncrypt;
import questiontypes.crypto.Encryption;
import questiontypes.crypto.NumCols;
import questiontypes.simpleexamples.MultQuestionExample;
import questiontypes.simpleexamples.SquareQuestionExample;
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
import questiontypes.time.ClosestDateTime;
import questiontypes.time.DiffMills;
import questiontypes.time.PairDiffMills;
import questiontypes.time.TimeTraveller;
import quizframework.Question;
import quizframework.Quiz;

public class DemoQuestions {

    public static final int NUM_ANSWERS = 6; //Total number of answers, correct and incorrect

    /*
     * Example of a quiz created using the framework - contains all possible questions at this point (including
     * some basic 'demo' ones.
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
        squareExample.createMcqAnswerSet(NUM_ANSWERS);
        //Then we add it to the quiz
        quiz.addQuestion(squareExample);

        /*The remaining questions are created in a similar way below - note that they all include their own
        implementations of the abstract quizframework.Question class */

        Question multExample = new MultQuestionExample();
        multExample.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(multExample);

        //Real Questions - these are questions that could be realistically used in a quiz.

        /*Checksum questions - note checkAnswer to (semi-independently)check the correctness of the answers
        not yet implemented!

        NOR HAVE THEY BEEN AS CAREFULLY CHECKED AS THOSE USED IN LIVE QUIZZES!
         */
        Question checkValueQuestion = new CheckSumValueQuestion();
        checkValueQuestion.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(checkValueQuestion);

        Question checkStringQuestion = new CheckSumStringQuestion();
        checkStringQuestion.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(checkStringQuestion);

        Question checkPairQuestion = new CheckSumPairQuestion();
        checkPairQuestion.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(checkPairQuestion);

        Question bitwise = new BitwiseChecksum();
        bitwise.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(bitwise);

        /*Time questions - note checkAnswer to (semi-independently)check the correctness of the answers
        not yet implemented!

        NOR HAVE THEY BEEN AS CAREFULLY CHECKED AS THOSE USED IN LIVE QUIZZES!
         */
        Question closestTime = new ClosestDateTime();
        closestTime.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(closestTime);

        Question timeTraveller = new TimeTraveller();
        timeTraveller.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        Question diffMills = new DiffMills();
        diffMills.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(diffMills);

        Question pairDiffMills = new PairDiffMills();
        pairDiffMills.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(pairDiffMills);

        /*
        THE FOLLOWING QUESTIONS HAVE BEEN MORE CAREFULLY CHECKED.
        They also include implementations of checkAnswer to (semi-independently) confirm (in)correctness of a questions'
        answers when it is generated
         */
        //Number problem questions
        Question factorsQuestion = new Factors();
        factorsQuestion.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(factorsQuestion);

        Question pythTriplets = new PythTriplets();
        pythTriplets.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(pythTriplets);

        Question fibonacci = new Fibonacci();
        fibonacci.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(fibonacci);

        Question pairSum = new AddPairs();
        pairSum.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(pairSum);

        Question primes = new Primes();
        primes.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(primes);

        Question sophieG = new SophieGermain();
        sophieG.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(sophieG);

        //"Term rewriting" questions
        Question nStepRewrite = new RewritingNSteps();
        nStepRewrite.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(nStepRewrite);

        Question completeRewrite = new RewritingToCompletion();
        completeRewrite.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(completeRewrite);

        Question whichIsX = new ReducesToX();
        whichIsX.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(whichIsX);

        Question altSets = new AltRewriting();
        altSets.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(altSets);

        //Transposition cypher questions
        Question encode = new Encryption();
        encode.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(encode);

        Question numCols = new NumCols();
        numCols.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(numCols);

        Question decode = new Decryption();
        decode.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(decode);

        Question doubleEncode = new DoubleEncrypt();
        doubleEncode.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(doubleEncode);

        //Geographical distance questions
        Question singleDistance = new DistanceTwoPoints();
        singleDistance.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(singleDistance);

        Question whichDistance = new WhichDistance();
        whichDistance.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(whichDistance);

        Question totalDistance = new TotalDistance();
        totalDistance.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(totalDistance);

        Question minSec = new MinSecDistance();
        minSec.createMcqAnswerSet(NUM_ANSWERS);
        quiz.addQuestion(minSec);

        /*
        The generateText2Qti method outputs a quiz in *markdown* format to the specified PrintStream - in this case,
        System.out (the screen). This format is suitable for use with a Python tool text2Qti:
        https://github.com/gpoore/text2qti
        That can generate QTI format - which Canvas can import. Ideally, (but not yet) there would be other output formats.
         */
        //quiz.generateText2Qti(System.out);
        System.out.println(quiz);
    }
}
