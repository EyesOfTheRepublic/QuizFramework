package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PythTripletsData;
import quizframework.Answer;
import quizframework.NumericQuestion;
import quizframework.utils.ArrayFormatter;
import quizframework.utils.CodeUtils;
import quizframework.utils.QuizUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * How many of a list of triplets are Pythagorean: a * a + b * b == c * c
 * This version requires a numeric answer and a specific code format suitable
 * for autograder
 * Here is the code for autograder:
 <pre>
import java.util.Arrays;

public class Test {
    public static int[][] possTriples = {
            {10632, 10719, 19233}, {10608, 206, 10610}, {11253, 2996, 11645}, {10307, 10343, 10948},
            {2163, 10267, 10487}, {10791, 1040, 10841}, {9216, 8320, 12416}, {10349, 5460, 11701},
            {10115, 3468, 10693}, {9936, 1600, 10064}, {10875, 4028, 11597}, {10379, 13615, 19653},
            {8960, 11232, 14368}, {4137, 19184, 19625}, {9199, 10003, 32216}, {9945, 9592, 13817},
            {10057, 2424, 10345}, {4732, 16224, 16900}, {9675, 5508, 11133}, {2572, 10170, 14030},
            {10013, 14090, 20081}, {7776, 11970, 14274}, {2604, 17200, 17396}, {6120, 13802, 15098},
            {10285, 19445, 23337}, {5780, 13872, 15028}, {10585, 7848, 13177}, {8200, 12198, 14698},
            {8362, 10498, 14416}, {5425, 14832, 15793}, {10018, 13608, 19946}, {10394, 22185, 27150},
            {10480, 15601, 32990}, {10173, 14726, 29173}, {10364, 21157, 24579}, {10265, 13640, 20782},
            {8517, 12644, 15245}, {8105, 10167, 30967}, {2472, 21146, 21290}, {10496, 19175, 21404},
            {10240, 4992, 11392}, {10562, 12024, 21009}, {817, 10003, 12735}, {3770, 10518, 28934},
            {5049, 17120, 17849}, {11712, 2834, 12050}, {5429, 10042, 11759}, {10275, 10307, 25403},
            {10061, 14133, 23184}, {1845, 10339, 10657}, {3846, 10299, 21614}, {10194, 13652, 16489},
            {4268, 18576, 19060}, {8281, 13080, 15481}, {3757, 10643, 29027}, {6125, 14700, 15925},
            {9387, 9116, 13085}, {10442, 16847, 18488}, {293, 10186, 17759}, {3814, 10023, 25436},
            {11413, 1284, 11485}, {10388, 816, 10420}, {6636, 11600, 13364}, {5163, 10067, 10092},
            {7942, 10523, 29562}, {4106, 10625, 27194}, {5607, 10312, 31403}, {10250, 12743, 18179},
            {4575, 16432, 17057}
    };

    public static void main(String[] args) {
        Pythagoras.possTriples = possTriples;

        long count = Arrays.asList(Pythagoras.possTriples)
        .stream()
        .filter(x -> x[0] * x[0] + x[1] * x[1] == x[2] * x[2]).count();

        System.exit(count == Pythagoras.answer() ? 0 : 1);
    }
}
 </pre>
 */
public class PythTriplets extends NumericQuestion {

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
        final ArrayFormatter<Triplet> formatter = new ArrayFormatter<>("public static int[][] possTriples", pythList);

        final StringBuilder builder = new StringBuilder("""
                **Pythagoras.** How many of the groups of three numbers in the array in the code below are Pythagorean Triples?
                That is, for each ``{a, b, c}``,  ``a*a + b*b == c*c``.
                """).append(QuizUtils.CODE_QUESTION_BOILERPLATE);

        final StringBuilder code = CodeUtils.questionCode("Pythagoras", formatter.format(2), "int");
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
