package main.java.uoc.ded.practica.model;

import main.java.uoc.ded.practica.Trial4C19;

import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;
import uoc.ei.tads.Posicio;

import java.util.Comparator;

public class QuestionGroup implements Comparable<QuestionGroup> {
    public static final Comparator<QuestionGroup> CMP = new Comparator<QuestionGroup>() {

        public int compare(QuestionGroup o1, QuestionGroup o2) {
            return o1.priority.compareTo(o2.priority);
        }
    };

    private String idGroup;
    private LlistaEncadenada<Question> questions;
    private Trial4C19.Priority priority;

    public QuestionGroup(String idGroup, Trial4C19.Priority priority) {
        this.idGroup = idGroup;
        this.priority = priority;
        this.questions = new LlistaEncadenada<Question>();
    }

    public String getIdGroup() {
        return this.idGroup;
    }

    public void addQuestion(String idQuestion, String wording, Trial4C19.Type type, String[] choices) {
        Posicio<Question> p = this.questions.afegirAlFinal(new Question(idQuestion, wording, type, choices));

        //this.questions.
    }

    public boolean is(String idQuestionGroup) {
        return this.idGroup.equals(idQuestionGroup);
    }

    public void setPriority(Trial4C19.Priority priority) {
        this.priority = priority;
    }

    public Iterador<Question> questions() {
        return this.questions.elements();
    }

    public Trial4C19.Priority getPriority() {
        return this.priority;
    }

    public int compareTo(QuestionGroup o) {
        return this.priority.compareTo(o.priority);
    }

    public int numQuestions() {
        return this.questions.nombreElems();
    }

    public Question firstQuestion() {
        //return this.questions.
        return null;
    }
}
