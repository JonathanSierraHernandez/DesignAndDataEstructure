package main.java.uoc.ded.practica.model;

import uoc.ei.tads.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;

import main.java.uoc.ded.practica.Trial4C19.Level;

public class User implements Comparable<User>{
    public static final Comparator<String> CMP = new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    } ;

    private String id;
    private String name;
    private String surname;
    private Date birthDay;
    private Level level;
    private boolean active;
    private LlistaEncadenada<Answer> answers;
    private LlistaEncadenada<Sample> samples;
    private Trial trial;
    private Cua<Question> questions;

    public User(String idUser, String name, String surname, Date birthday, Level level) {
        this.setId(idUser);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthDay(birthday);
        this.setLevel(level);
        this.active = false;
        this.answers = new LlistaEncadenada<Answer>();
        this.samples = new LlistaEncadenada<Sample>();
        this.trial = null;
        this.questions = new CuaVectorImpl<Question>();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }
   
    public Date getBirthDay() {
        return this.birthDay;
    }
    
    public Level getLevel() {
        return this.level;
    }

    public int compareTo(User o) {
        return this.getId().compareTo(o.getId());
    }

    public void setActive() {
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void addAnswer(Question q, String answer) {
        Answer theAnswer = new Answer(q, answer);
        this.answers.afegirAlFinal(theAnswer);
        this.trial.incNumAnswers();
        this.trial.updateMostActiveUser(this);
    }
    
    public void addSample(Sample sample) {
        this.samples.afegirAlFinal(sample);
    }
    
    public void setSamples(LlistaEncadenada<Sample> samples) {
        this.samples = samples;
    }
    
    public LlistaEncadenada<Sample> getSamples() {
        return this.samples;
    }
    
    public int numSamples() {
        return this.samples.nombreElems();
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
        Iterador<QuestionGroup> itQG =  trial.questionGroups();
        Iterador<Question> itQ = null;
        while (itQG.hiHaSeguent()) {
            itQ = itQG.seguent().questions();
            while (itQ.hiHaSeguent()) {
                this.questions.encuar(itQ.seguent());
            }
        }
    }

    public Iterador<Answer> answers() {
        return this.answers.elements();
    }

    public Question nextQuestion() {
        Question q = this.questions.desencuar();
        return q;
    }

    public Question currentQuestion() {
        return this.questions.primer();
    }

    public Trial getTrial() {
        return this.trial;
    }

    public int numAnswers()  {
        return this.answers.nombreElems();
    }
  
	public int years(Date now) {
	 LocalDate nD = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	 LocalDate d = getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	 return Period.between(d, nD).getYears();
	}
}
