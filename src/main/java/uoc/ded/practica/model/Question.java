package main.java.uoc.ded.practica.model;

import main.java.uoc.ded.practica.Trial4C19;

public class Question {
    private String idQuestion;
    private String wording;
    private Trial4C19.Type type;
    private String[] choices;

    public Question(String idQuestion, String wording, Trial4C19.Type type, String[] choices) {
        this.idQuestion = idQuestion;
        this.wording = wording;
        this.type = type;
        this.choices = choices;
    }

    public String getIdQuestion() {
        return this.idQuestion;
    }

    @Override
    public String toString() {
        return this.idQuestion;
    }
}
