package main.java.uoc.ded.practica.model;

import java.util.Comparator;
import java.util.Date;
import main.java.uoc.ded.practica.Trial4C19.Status;
import test.java.uoc.ded.practica.util.DateUtils;

public class Sample {
	
	private String idSample;
	private String idUser;
	private String idClinician;
	private Date dateCreation;
	private Date dateSended;
	private Date dateCompleted;
	private User user;
	private Status status;
	private String report;
    
    public static final Comparator<Sample> CMP = new Comparator<Sample>() {

		public int compare(Sample s1, Sample s2) {
			Date n = new Date();
			if(s1.getUser().getLevel().compareTo(s2.getUser().getLevel())==0) {
				if (s1.getUser().years(n) > s2.getUser().years(n)) {
					return -1;
    			}
    			else {
    				return 1;
    			}
    		} else {
    			return s1.getUser().getLevel().compareTo(s2.getUser().getLevel());
    		}
        
        }
      };
    
	
	public Sample(String idSample, String idUser, String idClinician, Date dateCreation) {
		super();
		this.idSample = idSample;
		this.idUser = idUser;
		this.idClinician = idClinician;
		this.dateCreation = dateCreation;
		this.status = Status.PENDING;
	}

	public void setIdSample(String idSample) {
		this.idSample = idSample;
	}
	
	public String getIdSample() {
		return this.idSample;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public String getIdUser() {
		return this.idUser;
	}
	
	public void setIdClinician(String idClinician) {
		this.idClinician = idClinician;
	}
	
	public String getIdClinician() {
		return this.idClinician;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public Date getDateCreation() {
		return this.dateCreation;
	}
	
	public void setDateSended(Date dateSended) {
		this.dateSended = dateSended;
	}
	
	public Date getDateSended() {
		return this.dateSended;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	
	public Date getDateCompleted() {
		return this.dateCompleted;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setReport(String report) {
		this.report = report;
	}
	
	public String getReport() {
		return this.report;
	}

}
