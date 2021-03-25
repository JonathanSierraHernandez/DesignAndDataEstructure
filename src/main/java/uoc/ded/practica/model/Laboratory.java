package main.java.uoc.ded.practica.model;

import uoc.ei.tads.CuaAmbPrioritat;

public class Laboratory {

	private String idLaboratory;
	private String name;
	private CuaAmbPrioritat<Sample> samples;
	
	public Laboratory(String idLaboratory, String name) {
		super();
		this.idLaboratory = idLaboratory;
		this.name = name;
		this.samples = new CuaAmbPrioritat<Sample>(Sample.CMP);
	}

	public String getIdLaboratory() {
		return idLaboratory;
	}

	public void setIdLaboratory(String idLaboratory) {
		this.idLaboratory = idLaboratory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CuaAmbPrioritat<Sample> getSamples(){
		return this.samples;
	}
	
	public void setSamples(CuaAmbPrioritat<Sample> samples) {
		this.samples = samples;
	}
	
	public void addSample(Sample sample) {
		this.samples.encuar(sample);
	}
	

}
