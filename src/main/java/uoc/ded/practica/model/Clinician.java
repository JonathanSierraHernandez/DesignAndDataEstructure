package main.java.uoc.ded.practica.model;

import uoc.ei.tads.LlistaEncadenada;

public class Clinician {
	
	private String name;
	private String Surname;
	private String knowledgeArea;
	private String idClinician;
	private LlistaEncadenada<Sample> samples;
	
	public Clinician(String name, String surname, String knowledgeArea, String idClinician) {
		super();
		this.name = name;
		Surname = surname;
		this.knowledgeArea = knowledgeArea;
		this.idClinician = idClinician;
		this.samples = new LlistaEncadenada<Sample>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public String getKnowledgeArea() {
		return knowledgeArea;
	}

	public void setKnowledgeArea(String knowledgeArea) {
		this.knowledgeArea = knowledgeArea;
	}

	public String getIdClinician() {
		return idClinician;
	}

	public void setIdClinician(String idClinician) {
		this.idClinician = idClinician;
	}
	
    public LlistaEncadenada<Sample> getSamples() {
        return this.samples;
    }

    public void setSamples(LlistaEncadenada<Sample> samples) {
        this.samples = samples;
    }
	
    public void addSample(Sample sample) {
        this.samples.afegirAlFinal(sample);
    }
    
    public int numSamples() {
        return this.samples.nombreElems();
    }
	
    public boolean is(String idClinician) {
        return this.getIdClinician().equals(idClinician);
    }
    
}
