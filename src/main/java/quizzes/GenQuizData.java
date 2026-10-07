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

    public static final String PRE_AMBLE = """
            <p><b style='color:red'>Open whatever software tools - e.g. IntelliJ - you want to use BEFORE you start the test!</b></p> \
            <p style='color:red'>Otherwise you will lose test time while the tool(s) open! It could easily take 5mins or more to \
            open software if nobody has run it before on the machine you are using.</p> \
            <ul> \
            <li>Answer all the questions.</li> \
            <li>You can use the Canvas pages for CS-128 and online resources THAT ALREADY EXIST.</li> \
            <li>HOWEVER IF YOU COMMUNICATE WITH ANYONE OR ANYTHING IN ANYWAY YOU ARE COMMITTING ACADEMIC MISCONDUCT. \
            The penalties for misconduct in exams/tests are MUCH more serious than for coursework and it is \
            perfectly possible to be WITHDRAWN FROM THE UNIVERSITY FOR A FIRST OFFENCE.</li> \
            <li>This applies to e.g. asking questions on online forums/help sites. You can though LOOK UP any EXISTING content on \
            online forums/help sites - just don't ask anything new.</li> \
            <li>YOU CANNOT USE AI IN ANY WAY AT ALL - other than the built-in autocomplete in e.g. IntelliJ.</li> \
            <li><b>You have 1 hour 20 minutes to complete the quiz</b> - plus any extra time you may be entitled to.</li> \
            <li>By submitting you state that you fully understand and are complying with the University's \
            <a href="https://hwb.swansea.ac.uk/academic-life/academic-misconduct/" target="_blank" rel="noopener">Academic Misconduct Policy</a> \
            (opens in a new window/tab)</li> \
            </ul>\
            <h2>Information for Invigilators</h2>\
            <p>Dictionaries are permitted.</p>\
            <p>Calculators are NOT permitted.</p>\
            <p>This is an OPEN BOOK exam - students are permitted to access the internet to access existing information, but they are NOT permitted to \
            communicate with anyone (verbally or electronically) and they are NOT permitted to use AI.</p>\
            <p>The content of this exam paper has been thoroughly checked, however if you think you have spotted an error, please raise your \
            hand and report it to an invigilator, to log the query. The question will not be corrected in the exam venue.</p>\
            <h2>Question Types</h2>\
            <p>This test contains two simple questions, two questions about <em>properties of numbers</em>, and \
            one question about <em>encryption/decryption</em>. \
            <p>You can find (and should already have read) background information on Numbers and Encryption/Decryption \
            in the In-Class Test Information module on Canvas.</p> \
            <em>Remember to cut-and-paste long strings - do not try to type them in.</em>\
            <h3>Numbers</h3> \
            <p><em>Depending on the specific questions in your tests,</em> the following information may be useful (note that not \
            all tests will include all of these):</p> \
            <ul> \
            <li>A Prime Number is a number with no factors other than 1 and itself.</li> \
            <li>A Pythagorean Triple is a set of three numbers <kbd>{a, b, c} such that a*a + b*b == c*c</kbd></li> \
            <li>A Factor <kbd>x</kbd> of a number <kbd>y</kbd> is a number such that <kbd>y/x</kbd> is an integer - that is, \
            in Java: <kbd>x % y == 0</kbd></li> \
            <li>A Fibonacci Number is a number <kbd>Fib(n)</kbd> such that <kbd>Fib(0) = Fib(1) = 1</kbd>, and \
            <kbd>Fib(n) = Fib(n-1) + Fib(n-2)</kbd> for <kbd>n>1</kbd></li> \
            </ul> \
            <h3>Encryption/Decryption</h3>\
            You will most easily be able to solve the Transposition Cypher problems using two-dimensional arrays. \
            This code may be useful for debugging.</p> \
            <pre>\
            /*<br/>\
             * Prints an array of characters row-by-row in a readable way. <br/>\
             * You may need to remove the keyword static after public, depending <br/>\
             * on how you write your code.<br/>\
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

    public static String TITLE = "CS-128 Programming Exam %d";
    public static String PRACTICE_TITLE = "CS-110 Programming Exam: Practice Version %d";
    public static String PRACTICE_PRE_AMBLE = """
            <h2>Practice Exam<h2> \
            <p>This is a practice exam, containing five questions - two simple questions, one about <em>rewriting</em>, \
            one about <em>checksums</em>, and one about <em>time</em>. You can find the preliminary information about the \
            rewriting, checksum and time questions in the modules \
            section on Canvas, and you can find some more information that will be useful below. \
            The actual test will also have five questions - the first question will be more difficult than the first \
            one in this test, but the final three will be a bit easier. Preliminary information \
            about the question types in the actual exam will be released a day or so before the exam date. \
            <h2> Actual Exam Preamble Text</h2> \
            <p> The text below will appear at the start of the actual exam. </p>\
            <p><b style='color:red'>Open whatever software tools - e.g. IntelliJ - you want to use BEFORE you start the test!</b></p> \
            <p style='color:red'>Otherwise you will lose test time while the tool(s) open! It could easily take 5mins or more to \
            open software if nobody has run it before on the machine you are using.</p> \
            <ul> \
            <li>Answer all the questions.</li> \
            <li>You can use the Canvas pages for CS-128 and online resources THAT ALREADY EXIST.</li> \
            <li>HOWEVER IF YOU COMMUNICATE WITH ANYONE OR ANYTHING IN ANYWAY YOU ARE COMMITTING ACADEMIC MISCONDUCT. \
            The penalties for misconduct in exams/tests are MUCH more serious than for coursework and it is \
            perfectly possible to be WITHDRAWN FROM THE UNIVERSITY FOR A FIRST OFFENCE.</li> \
            <li>This applies to e.g. asking questions on online forums/help sites. You can though LOOK UP any EXISTING content on \
            online forums/help sites - just don't ask anything new.</li> \
            <li>YOU CANNOT USE AI IN ANY WAY AT ALL - other than the built-in autocomplete in e.g. IntelliJ.</li> \
            <li><b>You have 1 hour 20 minutes to complete the quiz</b> - plus any extra time you may be entitled to.</li> \
            <li>By submitting you state that you fully understand and are complying with the University's \
            <a href="https://hwb.swansea.ac.uk/academic-life/academic-misconduct/" target="_blank" rel="noopener">Academic Misconduct Policy</a> \
            (opens in a new window/tab)</li> \
            <h2 style='color:red'>VITAL POINT</h2>\
            <p style='color:red'>The questions are multiple choice so you can see if your code generates the correct answer before you submit it to autograder. So \
            by all means answer the question on Canvas but the WHOLE POINT of this is to test that you can write the code. So you HAVE to submit \
            working code to autograder to get the marks: just manually working out the solution and answering it on Canvas will NOT get you the marks \
            for the question.</p>\
            <h2> Question Types</h2> \
            <p>You can find (and should already have read) background information on Term Rewriting, Checksums \
            and Time in the In-Class Test Information module on Canvas.</p> \
            <em>Remember to cut-and-paste long strings - do not try to type them in.</em>\
            <h3>Term Rewriting</h3> \
            <p>You will need to apply  the following set of rules <em>in the order given below</em> to successfully solve \
            the term rewriting question.</p> \
            <p><em>When a term rewriting question says "apply the rules" it means apply ALL OF THEM one after the other in the order \
            given in the list below.</em></p>.\
            <h4>Term Rewriting Rule Set</h4> \
            <ul><li>bYb -> Y</li><li>c -> Y</li><li>XXbYaX -> X</li><li>XXba -> X</li><li>Xa -> X</li><li>XY -> X</li><li>bb -> X</li></ul> \
            <h2>Checksums</h2>\
            <p>Some of the questions in this test are based on Checksums. You can find (and should already have read) background \
            information on Checksums in the In-Class Test Information module on Canvas.</p>\
            <p>NOTE there are some fragments of code in the algorithms below, but you are responsible for implementing them in Java.</p>\
            <h3>Simple CheckSum Algorithm</h3>\
            <p>The simple checksum algorithm is as follows:</p>\
            <ul>\
                <li>We assume you are computing the checksum for a String called str.</li>\
                <li>Declare a variable k of type long and initialise it to 7</li>\
                <li>For each character in the string str:</li>\
                <ul>\
                    <li>set k to k multiplied by 23</li>\
                    <li>Add the ith character in str to k (use str.charAt(i) to get the ith character)</li>\
                    <li>Set k to k multiplied by 13</li>\
                    <li>Set k to the remainder of dividing k by 1000000009</li>\
                </ul>\
                <li>At the end of this loop the checksum is stored in k</li>\
            </ul>\
            <h3>Bitwise Checksum Algorithm</h3>\
            <p>The bitwise checksum algorithm is as follows:</p>\
            <ul>\
                <li>We assume you are computing the checksum for a string called str</li>\
                <li>Declare a variable c of type byte and initialise it to 0</li>\
                <li>Turn the string str into a byte array (str.getBytes() will return str as an array of bytes);</li>\
                <li>For each byte - curByte - in the array input:\
                    <ul>\
                        <li>Set c as follows: c = (byte) (((c &amp; 255) &gt;&gt;&gt; 1) + ((c &amp; 1) &lt;&lt; 7));</li>\
                        <li>Now set c as follows: c = (byte) ((c + curByte) &amp; 255);</li>\
                    </ul>\
                </li>\
                <li>At the end of this loop c now contains the one byte checksum</li>\
            </ul>\
            <h3>Time</h3> \
            <p>The following imports are probably useful - you may not need all of them but it will not be a problem if you include \
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
            <p>The following code (also in the preliminary information on Canvas) may be useful in answering the Time question.</p> \
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
            """;
}