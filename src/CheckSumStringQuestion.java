public class CheckSumStringQuestion extends CheckSumQuestionCore {

    private String correctAnswer;
    @Override
    public String createQuestionText() {
        CalcData<Long> item = getQuizDataItem("checkSum");
        long seedVal = item.get();

        return "Which of the following strings generates the simple checksum " + seedVal + " ?";
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
        this.correctAnswer = QuizUtils.genRandomString(65, 20, 'a', 'z');
        CalcData<Long> val = new CalcData<>(simpleCheckSum(this.correctAnswer));
        addQuizDataItem("checkSum", val);
    }

    @Override
    public Answer createCorrectAnswer() {

        return Answer.makeCorrectAnswerWithFeedback(this.correctAnswer,
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(QuizUtils.permuteString(this.correctAnswer,
                        0.5, QuizUtils.MIN_PERMUTATION_RNG, 2), "some incorrect feedback");
    }
}
