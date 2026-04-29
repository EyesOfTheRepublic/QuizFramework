# QuizFramework
Generate multiple choice questions based on problems that students have to write code for to answer.
Used as the basis or in-class tests at Swansea University (teaching Java to first year students).
##text2qti
The main use of this is to generate QTI files that can be imported into Canvas - currently using
[text2qti](https://github.com/gpoore/text2qti), which is fundamentally Markdown. Currently only MCQs and Numeric questions are implemented
(and Numeric ones are not that well tested) but other question types are possible.
## Overview
- Quizzes are based on *Quiz* objects to which *questions* are added
- Questions are classes that extend the abstract *Question* class.
- Questions contain multiple *Answer* objects - answers can either be correct or incorrect and can optionally include feedback
- The *Question* class contains a number of abstract methods that you *must* implement, as well as a number of default methods that you *may* implement.
- Although it can be used for 'general' questions, more usually the questions created require a code 'template' to be filled in to enable automatic checking with autograder.

### Required
- *String createQuestionTitle()* returns the title of your question (Canvas ignores this, but it seems to be necessary)
- *String createQuestionText()* contains the text of the question.
- *void createCalcData()* creates data on which the question is based. This is usually (partly) random and this method runs *before* *createQuestionText* so the data can potentially appear in the question text.
- *Answer createCorrectAnswer()* creates an *Answer* object containing the correct answer.
- *Answer createIncorrectAnswer()* *for MCQ questions only* creates an *Answer* object containing an incorrect answer (required if the question type displays incorrect answers) - these should be in some way randomly generated because typically there will be multiple incorrect answers (there is no need to check they will be unique - this is dealt with by the code)
- 
### Optional

- *int createQuestionPoint()* defaults to 1 (and cannot be negative).
- *String createGeneralFeedback()* defaults to null - shown in all cases (though Canvas does not seem to provide access to all feedback types)
- *String createCorrectFeedback()* defaults to null - contains general feedback for the correct answer
- *String createIncorrectFeedback()* defaults to null - contains general feedback for incorrect answers
- *boolean checkAnswer(Answer answer)* defaults to correct. Optional (defaults) to 'correct' but important: enables an independent check of the correctness of the correct (and incorrectness of incorrect answers) - ideally written in a way that matches the code students are likely to write.

## Example
Below is a trivial and minimal MCQ example that shows how this works - it generates a question asking what is the multiple of two (randomly generated) numbers:

```
public class MultQuestionExample extends McqQuestion {

    private final Random rnd = new Random();

    private long val1;
    private long val2;

    @Override
    public String createQuestionTitle() {
        return "Multiplying Numbers";
    }

    @Override
    public String createQuestionText() {
        return "What is " + val1 + " * " + val2 + " ?";
    }

    @Override
    public void createCalcData() {
        val1 = rnd.nextInt(15);
        val2 = rnd.nextInt(15);
    }

    public Answer createCorrectAnswer() {
        return Answer.makeCorrectAnswer(Long.toString(val1 * val2));
    }

    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswer(Long.toString(rnd.nextInt(30)));
    }
}
```
Answers are created with the factory methods ``Answer.makeCorrectAnswer`` and ``Answer createIncorrectAnswer`` (and there are also versions that include feedback). To create a quiz with this question:

```
public class DemoQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("Demo", "Demonstrating multiplication...");

        Question multi = new MultQuestionExample();
        multi.createQuestion(6);
        quiz.addQuestion(multi);
        try {
            PrintStream stream = new PrintStream("DemoQuiz.txt");
            quiz.generateText2Qti(stream);
            stream.close();
        } catch (FileNotFoundException fne) {
            System.out.println("Can't open file");
        }
    }
}
```
This creates a *Quiz* (with a title) and then an *McqQuestion* using the code above; it then generates a specific question with one correct and five incorrect answers (all guaranteed unique and *not*
equal to the correct answer). This is then added to the quiz (obviously multiple questions can be added) and written in a text2qti complaint format to "DemoQuiz.txt".
There is also a *toString()* method that writes the quiz in a slightly more readable format. It also flags up any questions where there seem to be correctness issues based on the (optional)
``checkAnswer`` method. This isn't implemented in the example above but would look something like:
```
@Override
public boolean checkAnswer(final Answer ans) {
    return val1 * val2 == Integer.parseInt(ans.getQuestionAnswer());
}
```

It is also possible to implement *Numeric* questions by overriding *NumericQuestion* though you do not need to (and cannot)
override *createIncorrectAnswer*, and the *createQuestion* method has no parameters.

You can find examples of questions in the *questiontypes* package; and quizzes in the *quizzes* package. Currently,
all question types are generated by *DemoQuestions.java* in the *test* package.
## Supporting Classes and Methods
A range of utilities exist to help generate questions, answers and (critically) plausible-looking *incorrect* answers. These include methods to:
- generate parameterized random integers, longs and doubles.
- generate a long with the same number of (decimal) digits as another long.
- generate random strings parameterized by length and characters they include.
- permute strings parameterized by the number of permutations and the part of the string changed (it is usually better to restrict changes to the middle of long strings).
- format a list of data as a Java array (if the built-in toString() method for the underlying data type isn't appropriate another one can be provided).
- format a block of text as a Markdown code block.

## Roadmap
- *Direct upload via Canvas API.* Currently the tool is reliant on text2qti to generate QTI files, and this could (a) be discontinued and (b) has limited question types (of which only the ones already implemented are in practice suitable for an automatic testing system. More significantly, it adds extra steps and complexity to the generation process - the tool needs to be run; QTI files need to be generated; QTI files need to be uploaded and configured. Extending the tool to directly use the Canvas API would remove these obstacles. (QTI upload will be retained however as it makes the tool useable on VLEs other than Canvas.)
- *More Question Types.* Direct access to the Canvas API would remove the restriction on question types imposed by text2qti - there is a wider range of available and suitable question types in Canvas other than numerical and MCQ.
- *Refactor to SOLID.* Currently the code isn't SOLID-compliant in that it does not entirely conform to the Liskov Substitution Principle. Refactoring to address this would be good practice.

