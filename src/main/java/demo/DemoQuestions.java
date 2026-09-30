package demo;

import questiontypes.basic.EvenSumRange;
import questiontypes.basic.SumThreeFiveQuestion;
import questiontypes.checksum.BitwiseChecksum;
import questiontypes.checksum.CheckSumString;
import questiontypes.checksum.CheckSumValue;
import questiontypes.crypto.Decryption;
import questiontypes.crypto.DoubleEncrypt;
import questiontypes.crypto.Encryption;
import questiontypes.crypto.NumCols;
import questiontypes.location.DistanceTwoPoints;
import questiontypes.location.TotalDistance;
import questiontypes.numbers.AddPairs;
import questiontypes.numbers.Collatz;
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
import questiontypes.time.TimeTraveller;
import quizframework.McqQuestion;
import quizframework.NumericQuestion;
import quizframework.Quiz;

public class DemoQuestions {

    public static final int NUM_ANSWERS = 6; //Total number of answers, correct and incorrect

    /*
     * Example of a quiz created using the framework - contains all possible questions at this point (including
     * some basic 'demo' ones)
     * There are still issues and things that could be better
     */
    public static void main(String[] args) {

        //quizframework.Quiz is class that represents a whole quiz - we initially create one with a title and description
        Quiz quiz = new Quiz("All Autograder Question", "All the questions that work with autograder.");

        /* Quizzes consist of zero or more Questions - quizframework.Question is an abstract class that must be implemented to
        actually create a question of a specific type. Here we create a question that is specifically about
        squares - see the implementation of questiontypes.example.SquareQuestionExample and quizframework.Question for more information
         */
       /* McqQuestion squareExample = new SquareQuestionExample();
        *//*Once created we build an actual question - multiple choice (MCQ) with 6 possible answers (1 will be correct)
        There can be as many answers as we want - ideally (but not yet) there would be more question types.
         *//*
        squareExample.createQuestion(NUM_ANSWERS);
        //Then we add it to the quiz
        quiz.addQuestion(squareExample);

        *//*The remaining questions are created in a similar way below - note that they all include their own
        implementations of the abstract quizframework.Question class *//*

        McqQuestion multExample = new MultQuestionExample();
        multExample.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(multExample);

        //Real Questions - these are questions that could be realistically used in a quiz.

        *//*Checksum questions - note checkAnswer to (semi-independently)check the correctness of the answers
        not yet implemented!

        NOR HAVE THEY BEEN AS CAREFULLY CHECKED AS THOSE USED IN LIVE QUIZZES!
         */
        McqQuestion sumRange = new EvenSumRange();
        sumRange.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(sumRange);

        McqQuestion collatz = new Collatz();
        collatz.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(collatz);

        McqQuestion sumThreeFive = new SumThreeFiveQuestion();
        sumThreeFive.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(sumThreeFive);

        NumericQuestion checkValueQuestion = new CheckSumValue();
        checkValueQuestion.createQuestion();
        quiz.addQuestion(checkValueQuestion);

        McqQuestion checkStringQuestion = new CheckSumString();
        checkStringQuestion.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(checkStringQuestion);

        McqQuestion bitwise = new BitwiseChecksum();
        bitwise.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(bitwise);

        /*Time questions - note checkAnswer to (semi-independently)check the correctness of the answers
        not yet implemented!

        NOR HAVE THEY BEEN AS CAREFULLY CHECKED AS THOSE USED IN LIVE QUIZZES!
         */
        McqQuestion closestTime = new ClosestDateTime();
        closestTime.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(closestTime);

        McqQuestion timeTraveller = new TimeTraveller();
        timeTraveller.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(timeTraveller);

        McqQuestion diffMills = new DiffMills();
        diffMills.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(diffMills);
        /*
        THE FOLLOWING QUESTIONS HAVE BEEN MORE CAREFULLY CHECKED.
        They also include implementations of checkAnswer to (semi-independently) confirm (in)correctness of a questions'
        answers when it is generated
         */

        //"Term rewriting" questions
        McqQuestion nStepRewrite = new RewritingNSteps();
        nStepRewrite.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(nStepRewrite);

        McqQuestion completeRewrite = new RewritingToCompletion();
        completeRewrite.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(completeRewrite);

        McqQuestion whichIsX = new ReducesToX();
        whichIsX.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(whichIsX);

        McqQuestion altSets = new AltRewriting();
        altSets.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(altSets);

        //Transposition cypher questions
        McqQuestion encode = new Encryption();
        encode.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(encode);

        McqQuestion numCols = new NumCols();
        numCols.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(numCols);

        McqQuestion decode = new Decryption();
        decode.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(decode);

        McqQuestion doubleEncode = new DoubleEncrypt();
        doubleEncode.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(doubleEncode);

        //Geographical distance questions
        McqQuestion singleDistance = new DistanceTwoPoints();
        singleDistance.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(singleDistance);

        NumericQuestion totalDistance = new TotalDistance();
        totalDistance.createQuestion();
        quiz.addQuestion(totalDistance);

        NumericQuestion testingTriples = new PythTriplets();
        testingTriples.createQuestion();
        quiz.addQuestion(testingTriples);

        McqQuestion testingSgCode = new SophieGermain();
        testingSgCode.createQuestion(NUM_ANSWERS);
        quiz.addQuestion(testingSgCode);

        NumericQuestion testingAddPairs = new AddPairs();
        testingAddPairs.createQuestion();
        quiz.addQuestion(testingAddPairs);

        Factors testingFactorCode = new Factors();
        testingFactorCode.createQuestion();
        quiz.addQuestion(testingFactorCode);

        Fibonacci testingFibonacciCode = new Fibonacci();
        testingFibonacciCode.createQuestion();
        quiz.addQuestion(testingFibonacciCode);

        Primes testingPrimesCode = new Primes();
        testingPrimesCode.createQuestion();
        quiz.addQuestion(testingPrimesCode);

        /*
        The generateText2Qti method outputs a quiz in *markdown* format to the specified PrintStream - in this case,
        System.out (the screen). This format is suitable for use with a Python tool text2Qti:
        https://github.com/gpoore/text2qti
        That can generate QTI format - which Canvas can import. Ideally, (but not yet) there would be other output formats.
         */
        quiz.generateText2Qti(System.out);
        //System.out.println(quiz);
    }
}
