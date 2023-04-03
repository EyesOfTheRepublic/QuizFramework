public class CheckSumPairQuestion extends CheckSumQuestionCore {

    private String checkedString;
    private long checkSum;
    @Override
    public String createQuestionText() {

        return "Which of the following pairs represents a string and it's simple checksum?";
    }

    @Override
    public String createGeneralFeedback() {
        return "Some generic feedback";
    }

    @Override
    public String createCorrectFeedback() {
        return "Some feedback for the correct answer";
    }

    @Override
    public String createIncorrectFeedback() {
        return "Some general feedback for incorrect answers";
    }

    @Override
    public void createCalcData() {
        /* We don't really need to store these in QuizData objects because they don't appear in the question text,
        but this is consistent with other questions */
        String dataString = QuizUtils.genRandomString(65, 20, 'a', 'z');
        checkedString = dataString;
        checkSum = simpleCheckSum(dataString);
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(checkedString
                        + " " + checkSum,
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(checkedString,
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2) + " "
                + checkSum, "some incorrect feedback");
    }
}
