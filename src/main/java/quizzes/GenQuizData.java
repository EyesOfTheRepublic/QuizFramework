package quizzes;

/**
 * Anything used by all quizzes - currently some standard text
 */

public class GenQuizData {

    /*
    Prevent inadvertant initialisation
     */
    private GenQuizData() {}

    public static final int NUM_ANSWERS = 6;
    public static final String RESOURCES = "";

    public static final String HEADER = """
            <ul> \
            <li>Answer all the questions.</li> \
            <li>You can use the Canvas pages for CS-110 and online resources..</li> \
            <li>HOWEVER IF YOU COMMUNICATE WITH ANYONE OR ANYTHING IN ANYWAY YOU ARE COMMITTING ACADEMIC MISCONDUCT. \
            The penalties for misconduct in exams/tests are MUCH more serious than for coursework and it is \
            perfectly possible to be WITHDRAWN FROM THE UNIVERSITY FOR A FIRST OFFENCE.</li> \
            <li>This applies to e.g. online forums/help sites or AI- asking questions there would be COMMISSIONING - the most serious offence.</li> \
            <li>You have 1 hour to complete the quiz - plus any extra time you may be entitled to.</li> \
            <li>By submitting you state that you fully understand and are complying with the University's \
            <a href="https://myuni.swansea.ac.uk/academic-life/academic-misconduct/" target="_blank" rel="noopener">Academic Misconduct Policy</a> \
            (opens in a new window/tab)</li> \
            </ul>""";
}