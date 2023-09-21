package quizzes;

/**
 * Anything used by all quizzes - currently some standard text
 */
public class GenQuizData {
    public static final int NUM_ANSWERS = 6;
    public static String RESOURCES = """
            <h3>Information Sources and Tools</h3> \
            <p>The following sources of information/tools ONLY will be available to you in the real test.</p> \
            <h4>Course Pages</h4> \
            <p><a class="inline_disabled" href="/courses/44525" target="_blank" rel="noopener noreferrer">Open the CS-110 Canvas pages in a new tab/window.</a></p> \
            <h4>IDEs</h4> \
            <p>You can choose to use any of the following online IDEs for the In-Class Test (open in new tab/window)</p> \
            <ul style="list-style-type: disc;"> \
                <li><a class="inline_disabled" href="https://www.online-ide.com" target="_blank" rel="noopener">online-ide.com</a></li> \
                <li><a class="inline_disabled" href="https://www.ideone.com" target="_blank" rel="noopener">ideone.com</a></li> \
                <li><a class="inline_disabled" href="https://www.jdoodle.com/online-java-compiler/" target="_blank" rel="noopener">jdoodle.com</a></li> \
                <li><a class="inline_disabled" href="https://www.onlinegdb.com" target="_blank" rel="noopener">onlinegdb.com</a></li> \
            </ul> \
            <h4>Additional Tutorial/Support Information</h4> \
            <p>You can use any of the following sources of Java information during the In-Class Test (open in new tab/window)</p> \
            <ul style="list-style-type: disc;"> \
                <li><a class="inline_disabled" href="https://www.w3schools.com/java/" target="_blank" rel="noopener">w3schools.com</a></li> \
                <li><a class="inline_disabled" href="https://www.tutorialspoint.com/java/index.htm" target="_blank" rel="noopener">tutorialspoint.com</a></li> \
                <li><a class="inline_disabled" href="https://www.javatpoint.com/java-tutorial" target="_blank" rel="noopener">javatpoint.com</a></li> \
            </ul>""";

    public static String HEADER = """
            <ul> \
            <li>Answer all the questions.</li> \
            <li>You can use the Canvas pages for CS-110 and the online resources linked below.</li> \
            <li>HOWEVER IF YOU COMMUNICATE WITH ANYONE IN ANYWAY YOU ARE COMMITTING ACADEMIC MISCONDUCT. \
            The penalties for misconduct in exams/tests are MUCH more serious than for coursework and it is \
            perfectly possible to be WITHDRAWN FROM THE UNIVERSITY FOR A FIRST OFFENCE.</li> \
            <li>This applies to e.g. online forums/help sites - asking questions there would be COMMISSIONING - the most serious offence.</li> \
            <li>You have 1 hour to complete the quiz - plus any extra time you may be entitled to.</li> \
            <li>By submitting you state that you fully understand and are complying with the University's \
            <a href="https://myuni.swansea.ac.uk/academic-life/academic-misconduct/" target="_blank" rel="noopener">Academic Misconduct Policy</a> \
            (opens in a new window/tab)</li> \
            </ul>""";
}