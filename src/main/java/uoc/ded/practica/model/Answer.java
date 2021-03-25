package main.java.uoc.ded.practica.model;

public class Answer {
    private Question question;
    private String answer;

    public Answer(Question q, String answer) {
        this.question = q;
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }

    @Override
    public String toString() {
        return question.toString()+" "+answer;
    }
}
