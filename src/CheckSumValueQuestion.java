public class CheckSumValueQuestion extends CheckSumQuestionCore { ;

    @Override
    public String createQuestionText() {
        Seed<String> item = getQuizDataItem("checkString");
        String seedVal = item.get();

        return "What is the result of running the simple checksum algorithm on the string " + seedVal + " ?";
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
        Seed<String> val = new Seed<>(QuizUtils.genRandomString(65, 20, 'a', 'z'));
        addQuizDataItem("checkString", val);
    }

    @Override
    public Answer createCorrectAnswer() {
        Seed<String> item = getQuizDataItem("checkString");
        String seedVal = item.get();

        return Answer.makeCorrectAnswerWithFeedback(Long.toString(simpleCheckSum(seedVal)),
                "some correct feedback");
    }

    @Override
    public Answer createIncorrectAnswer() {
        return Answer.makeIncorrectAnswerWithFeedback(Long.toString(rnd.nextLong()),
                "some incorrect feedback");
    }
}
