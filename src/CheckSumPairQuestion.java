public class CheckSumPairQuestion extends CheckSumQuestionCore {
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
    public void createDataSeeds() {
        /* We don't really need to store these in QuizData objects because they don't appear in the question text,
        but this is consistent with other questions */
        String dataString = QuizUtils.genRandomString(65, 20, 'a', 'z');
        addQuizDataItem("checkedString",
                new Seed<>(dataString));
        addQuizDataItem("checkSum",
                new Seed<>(simpleCheckSum(dataString)));
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(getQuizDataItem("checkedString").get()
                        + " " + getQuizDataItem("checkSum"),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(getQuizDataItem("checkedString").valueOf(),
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2) + " "
                + QuizUtils.similarLong((long)getQuizDataItem("checkSum").get()),
                "some incorrect feedback");
    }
}
