package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PythTripletsData;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * How many of a list of triplets are Pythagorean: a * a + b * b == c * c
 */
public class PythTripletsCode extends Question {

    private final ArrayList<Triplet> triples = new ArrayList<>();

    private final ArrayList<Triplet> pythList = new ArrayList<>();

    private record Triplet(int a, int b, int c) {
        @Override
        public String toString() {
            return "{" + a + ", " + b + ", " + c + "}";
        }
    }

    int numCorrect;

    int numWrong;

    @Override
    public String createQuestionTitle() {
        return "How many Pythagorean Triples?";
    }

    @Override
    public String createQuestionText() {
        ArrayFormatter<Triplet> formatter = new ArrayFormatter<>("public static int[][] possTriples", pythList);

        final StringBuilder builder = new StringBuilder("""
                How many of the groups of three numbers in the list are Pythagorean Triples?
                That is, for each ``{a, b, c}``,  ``a*a + b*b == c*c``.
                """).append(QuizUtils.CODE_QUESTION_BOILERPLATE);
        final String codeFramework = """
                public class Pythagoras {
                %s
                    
                     public static void main(String[] args) {
                         System.out.println(answer());
                     }
                    
                     public static int answer() {
                         //Write your code here - it should *return* the answer
                     }
                }
                """;

        final String code = String.format(codeFramework, formatter.format(2));
        return builder.append(CodeUtils.toCodeBlock(new StringBuilder(code))).toString();
    }

    @Override
    public void createCalcData() {
        //Generate number of correct and incorrect triplets
        numCorrect = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        numWrong = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Copy of list of triples to choose correct values from
        for (Integer[] elt : PythTripletsData.TRIPLETS) {
            triples.add(new Triplet(elt[0], elt[1], elt[2]));
        }

        //Move random legal triples to the question list
        for (int i = 0; i < numCorrect; i++) {
            int index = ThreadLocalRandom.current().nextInt(triples.size());

            pythList.add(triples.get(index));
            triples.remove(index);
        }

        //Generate incorrect triples
        ArrayList<Triplet> fakeTriples = new ArrayList<>();
        for (int j = 0; j < numWrong; j++) {
            fakeTriples.add(genWrong(fakeTriples));
        }

        pythList.addAll(fakeTriples);
        Collections.shuffle(pythList);

    }

    @Override
    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Integer.toString(numCorrect));
    }

    @Override
    public boolean checkAnswer(final Answer answer) {
        int pythCount = 0;
        for (Triplet elt : pythList) {
            if (elt.a() * elt.a() + elt.b() * elt.b() == elt.c() * elt.c()) {
                pythCount++;
            }
        }

        return pythCount == Integer.parseInt(answer.getQuestionAnswer());
    }

    /*Generate wong answers - triplets that are not pythagorean - that are not already present in the question list */
    private Triplet genWrong(final ArrayList<Triplet> wrongList) {
        int a;
        int b;
        int c;
        final int tripLength = PythTripletsData.TRIPLETS.length - 1;

        Triplet candidateIncorrectTriplet;
        do {
            /*Make sure they are in the range of numbers used for correct answers - origin/bound doesn't work here
            Also the apparently-clumsy use of an array as well is to simplify sorting the results - not being sorted
            would make them potentially stand out
             */
            a = PythTripletsData.TRIPLETS[0][0] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][0]);
            b = PythTripletsData.TRIPLETS[0][1] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][1]);
            c = PythTripletsData.TRIPLETS[0][2] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][2]);
            final Integer[] notTriple = new Integer[3];
            notTriple[0] = a;
            notTriple[1] = b;
            notTriple[2] = c;
            Arrays.sort(notTriple);
            candidateIncorrectTriplet = new Triplet(notTriple[0], notTriple[1], notTriple[2]);
        } while (a * a + b * b == c * c && wrongList.contains(candidateIncorrectTriplet));
        return candidateIncorrectTriplet;
    }

}
