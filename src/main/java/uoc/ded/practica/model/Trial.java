package main.java.uoc.ded.practica.model;

import main.java.uoc.ded.practica.Trial4C19;
import main.java.uoc.ded.practica.util.OrderedVector;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

public class Trial {
    private int idTrial;
    private String description;
    private LlistaEncadenada<User> users;
    private LlistaEncadenada<Sample> samples;
    private OrderedVector<QuestionGroup> groups;
    private User mostActiveUser;
    private int numAnswers;


    public Trial(int idTrial, String description) {
        this.idTrial = idTrial;
        this.description = description;
        this.users = new LlistaEncadenada<User>();
        this.samples = new LlistaEncadenada<Sample>();
        this.groups = new OrderedVector<QuestionGroup>(Trial4C19.G, QuestionGroup.CMP);
        this.mostActiveUser = null;
    }

    public void addQuestionGroup(QuestionGroup qg) {
        this.groups.update(qg);
    }

    public int numQuestionGroups() {
        return this.groups.nombreElems();
    }

    public void addUser(User user) {
        this.users.afegirAlFinal(user);
    }
    
    public void addSample(Sample sample) {
        this.samples.afegirAlFinal(sample);
    }

    public LlistaEncadenada<Sample> getSamples() {
        return this.samples;
    }
    
    public int numSamples() {
        return this.samples.nombreElems();
    }
    
    public int numUsers() {
        return this.users.nombreElems();
    }

    public User mostActiveUser() {
        return this.mostActiveUser;
    }

    public Iterador<QuestionGroup> questionGroups() {
        return this.groups.elements();
    }

    public void incNumAnswers() {
        this.numAnswers++;
    }

    public int numAnswers() {
        return this.numAnswers;
    }

    public void updateMostActiveUser(User user) {
        if (this.mostActiveUser == null) this.mostActiveUser = user;
        else if (this.mostActiveUser.numAnswers()<user.numAnswers()) this.mostActiveUser = user;
    }
}
