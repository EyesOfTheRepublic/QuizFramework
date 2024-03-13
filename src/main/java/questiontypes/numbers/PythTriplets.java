package questiontypes.numbers;

import questiontypes.numbers.utils.CoreData;
import questiontypes.numbers.utils.PythTripletsData;
import quizframework.Answer;
import quizframework.Question;
import quizframework.utils.QuizUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * How many of a list of triplets are Pythagorean: a * a + b * b == c * c
 *
 */
public class PythTriplets extends Question {

    private ArrayList<Integer[]> triples = new ArrayList<>();

    private ArrayList<Integer[]> pythList = new ArrayList<>();

    private record PythTriplet(int a, int b, int c) {}

    int numCorrect;

    int numWrong;

    @Override
    public String createQuestionTitle() {
        return "How many Pythagorean Triples?";
    }

    @Override
    public String createQuestionText() {
        String qText =  """
                How many of the groups of three numbers in the list are Pythagorean Triples?
                That is, for each ``{a, b, c}``, ``a*a + b*b == c*c``.
                
                ```
                int[][] possTriples = {""";
        for (int j = 0; j < pythList.size() - 1; j++) {
            qText += QuizUtils.formattedItem(j, "{" + pythList.get(j)[0] + ", " + pythList.get(j)[1] + ", " + pythList.get(j)[2] + "}, ");
        }
        qText += "{" + pythList.get(pythList.size() - 1)[0] + ", "
                + pythList.get(pythList.size() - 1)[1] + ", "
                + pythList.get(pythList.size() - 1)[2] + "}};\n```\n";

        return qText;
    }

    @Override
    public void createCalcData() {
        //Generate number of correct and incorrect triplets
        numCorrect = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        numWrong = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);

        //Copy of list of triples to choose correct values from
        for(Integer[] elt: PythTripletsData.TRIPLETS) {
            triples.add(elt);
        }

        //Move random legal triples to the question list
        for (int i = 0; i < numCorrect; i++) {
            int index = ThreadLocalRandom.current().nextInt(triples.size());

            pythList.add(triples.get(index));
            triples.remove(index);
        }

        //Generate incorrect triples
        ArrayList<Integer[]> fakeTriples = new ArrayList<>();
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
    public Answer createIncorrectAnswer() {
        int candidate = QuizUtils.genRandomInt(CoreData.MIN_NUM, CoreData.MAX_NUM);
        //Just in case...
        return Answer.makeIncorrectAnswer(Integer.toString(candidate > pythList.size() ?
                pythList.size() : candidate));
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        int pythCount = 0;
        for(Integer[] elt : pythList) {
            if (elt[0] * elt[0] + elt[1] * elt[1] == elt[2] * elt[2]) {
                pythCount++;
            }
        }

        return pythCount == Integer.parseInt(answer.getAnswer());
    }

    /*Generate wong answers - triplets that are not pythagorean - that are not already present in the question list */
    private Integer[] genWrong(ArrayList<Integer[]> wrongList) {
        int a;
        int b;
        int c;
        Integer[] notTriple = new Integer[3];
        final int tripLength  = PythTripletsData.TRIPLETS.length - 1;

        do {
            //Make sure they are in the range of numbers used for correct answers - origin/bound doesn't work here...
            a = PythTripletsData.TRIPLETS[0][0] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][0]);
            b = PythTripletsData.TRIPLETS[0][1] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][1]);
            c = PythTripletsData.TRIPLETS[0][2] + ThreadLocalRandom.current().nextInt(PythTripletsData.TRIPLETS[tripLength][2]);
            notTriple[0] = a;
            notTriple[1] = b;
            notTriple[2] = c;
            Arrays.sort(notTriple);
        } while (a * a + b * b == c * c && wrongList.contains(notTriple));
        return notTriple;
    }

}
