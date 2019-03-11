package fr.intech.tpapp;


public class Question {
    private String question;
    private String[] answers;
    private int rightAnswer;

    public String[] getAnswers() { return answers; }

    public void setAnswers(String[] answers) { this.answers = answers; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

}
