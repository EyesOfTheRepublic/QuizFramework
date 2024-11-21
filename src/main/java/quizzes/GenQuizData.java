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

    public static String TITLE = "CS-110 Programming Class Test %d";
    public static String PRACTICE_TITLE = "CS-110 Programming Class Test: Practice Version";
    public static String PRE_AMBLE = """
            <h2>Question Types</h2> \
            <p>This test contains two questions about <em>numbers</em>, one question about <em>rewriting</em>, \
            one question about <em>encryption/decryption</em>, \
            and one question about <em>time</em>. \
            <p>You can find (and should already have read) background information on Numbers, Term Rewriting, Encryption/Decryption \
            and Time in the In-Class Test Information module on Canvas.</p> \
            <em>Remember to cut-and-paste long strings - do not try to type them in.</em>\
            <h3>Numbers</h3> \
            <p><em>Depending on the specific questions in your tests,</em> the following information may be useful (note that not \
            all tests will include all of these):</p> \
            <ul> \
            <li>A Prime Number is a number with no factors other than 1 and itself.</li> \
            <li>A Sophie Germain Prime Number is a Prime Number p where 2 * p + 1 is also prime</li> \
            <li>A Pythagorean Triple is a set of three numbers <kbd>{a, b, c} such that a*a + b*b == c*c</kbd></li> \
            <li>A Factor <kbd>x</kbd> of a number <kbd>y</kbd> is a number such that <kbd>y/x</kbd> is an integer</li> \
            <li>A Fibonacci Number is a number <kbd>Fib(n)</kbd> such that <kbd>Fib(0) = Fib(1) = 1</kbd>, and \
            <kbd>Fib(n) = Fib(n-1) + Fib(n-2)</kbd> for <kbd>n>1</kbd></li> \
            </ul> \
            <h3>Term Rewriting</h3> \
            <p>You will need to apply  the following set of rules <em>in the order given below</em> to successfully solve \
            the term rewriting question.</p> \
            <h4>Term Rewriting Rule Set</h4> \
            <ul><li>bYb -> Y</li><li>c -> Y</li><li>XXbYaX -> X</li><li>XXba -> X</li><li>Xa -> X</li><li>XY -> X</li><li>bb -> X</li></ul> \
            <h3>Time</h3> \
            <p>The following code (also in the preliminary information on Canvas) may be useful in answering the Time question.</p> \
            <p>The following imports are probably useful - you may not use all of them but it will not be a problem if you include \
            them all:</p>\
            <pre>\
            import java.time.format.DateTimeFormatter;\
            </pre><pre>\
            import java.time.Instant;\
             </pre><pre>\
            import java.time.LocalDateTime;\
             </pre><pre>\
            import java.time.ZoneId;\
            </pre>\
            <p>To create a <kbd>DateTimeFormatter</kbd> to parse a date/time in the format used in the question:</p> \
            <pre>\
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");\
            </pre>\
            <p>To convert a date/time string into the corresponding number of milliseconds using the formatter above:</p> \
            <pre>\
            long milliSeconds = LocalDateTime.parse(date, df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(); \
            </pre> \
            <p>To turn milliseconds represented by a <kbd>long</kbd> called <kbd>millis</kbd> back into a String:</p> \
            <pre>\
            LocalDateTime dmils = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()); \
             </pre><pre>\
            String dateString = dmils.format(df);\
            </pre>\
            <p>Note that it is <em>essential</em> that you use the <kbd>long</kbd> data type and NOT <kbd>int</kbd>. \
            <h3>Encryption/Decryption</h3>\
            You will most easily be able to solve the Transposition Cypher problems using two-dimensional arrays.
            This code may be useful for debugging.</p> \
            <pre>\
            /*<br/>\
             * Prints an array of characters row-by-row in a readable way. <br/>\
             * You may need to add the keyword static after public <br/>\
             */ <br/>\
            public static void formatArray(char[][] charArray) { <br/>\
                for (char[] row : charArray) { <br/>\
                    for (char item : row) { <br/>\
                        System.out.print(" " + item); <br/>\
                    } <br/>\
                    System.out.println(); <br/>\
                } <br/>\
            } <br/>\
            </pre> \
            """;

    public static String PRACTICE_PRE_AMBLE = """
            <h2>Practice Test<h2>\
            <p>This is a practice test, containing five questions - two on <em>locations</em> and three on \
            <em>checksums</em>. You can find the preliminary information on these question types in the modules \
            section on Canvas. Two of these questions require you to enter an answer and the other three are multiple \
            choice. The actual test will also have five questions - two will also need you to enter an answer and the \
            other three will be multiple choice. In the actual test, two questions will be about <em>numbers</em>, one about \
            <em>time</em>, one about <em>term rewriting</em> and one about <em>cyphers</em>. Preliminary information \
            about these question types will be released a day or so before the actual test.
            """;
}