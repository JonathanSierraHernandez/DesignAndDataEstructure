package main.java.uoc.ded.practica;

import main.java.uoc.ded.practica.exceptions.*;
import main.java.uoc.ded.practica.model.*;
import main.java.uoc.ded.practica.util.DiccionariOrderedVector;
import main.java.uoc.ded.practica.util.OrderedVector;
import uoc.ei.tads.*;

import java.util.Date;

public class Trial4C19Impl implements Trial4C19 {

    private DiccionariOrderedVector<String, User> users;
    private Trial[] trials;
    private OrderedVector<QuestionGroup> groups;
    private int numTrials;
    private Trial mostActiveTrial;
    //Especialistes: taula de dispersió
    private Diccionari<String, Clinician> clinicians;
    private Clinician mostActiveClinician;
    //Laboratoris: vector
    private Laboratory[] laboratories;
    private int numLaboratories;
    //Mostres cliniques: AVL i cua
    private Diccionari<String, Sample> samples;
    private CuaAmbPrioritat<Sample> samplesPending;

    public Trial4C19Impl() {
        this.users = new DiccionariOrderedVector<String, User>(U, User.CMP);
        this.trials = new Trial[T];
        this.groups = new OrderedVector<QuestionGroup>(G, QuestionGroup.CMP);
        this.mostActiveTrial = null;
        this.clinicians = new TaulaDispersio<String, Clinician>();
        this.mostActiveClinician = null;
        this.laboratories = new Laboratory[Trial4C19.L];
        this.numLaboratories = 0;
        this.samples = new DiccionariAVLImpl<String, Sample>();
        this.samplesPending = new CuaAmbPrioritat<Sample>(Sample.CMP);
    }

	@Override
	public void addUser(String idUser, String name, String surname, Date birthDay, Level level) {
		// TODO Auto-generated method stub
        User u = this.getUser(idUser);
        if (u != null) {
            u.setName(name);
            u.setSurname(surname);
            u.setBirthDay(birthDay);
            u.setLevel(level);
        } else {
            u = new User(idUser, name, surname, birthDay, level);
            this.users.afegir(idUser, u);
        }
		
	}
    
    public void addTrial(int idTrial, String description) throws TrialAlreadyExistsException {
        if (this.trials[idTrial]!=null) throw new TrialAlreadyExistsException();
        else {
            this.trials[idTrial] = new Trial(idTrial, description);
            this.numTrials++;
        }


    }

    private QuestionGroup getQuestionGroup(String idQuestionGroup) {
        boolean found = false;

        Iterador<QuestionGroup> it = this.groups.elements();
        QuestionGroup qg = null;

        while (it.hiHaSeguent() && !found) {
            qg = it.seguent();
            found = qg.is(idQuestionGroup);
        }
        return (found? qg: null);
    }

    public void addQuestionGroup(String idQuestionGroup, Priority priority) {
        QuestionGroup group = this.getQuestionGroup(idQuestionGroup);
        if (group==null) {
            group = new QuestionGroup(idQuestionGroup, priority);
            this.groups.update(group);
        }
        else group.setPriority(priority);

    }

    public void addQuestion(String idQuestion, String wording, Type type, String[] choices, String idGroup)
            throws QuestionGroupNotFoundException {

        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();

        qg.addQuestion(idQuestion, wording, type, choices);

    }

    public Iterador<Question> getQuestions(String idGroup) throws QuestionGroupNotFoundException {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();
        return qg.questions();
    }

    public void assignQuestionGroup2Trial(String idGroup, int idTrial) throws QuestionGroupNotFoundException, TrialNotFoundException {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        if (qg == null) throw new QuestionGroupNotFoundException();

        Trial t = this.trials[idTrial];
        if (t==null) throw new TrialNotFoundException();

        t.addQuestionGroup(qg);
    }

    public void assignUser2Trial(int idTrial, String idUser) throws UserIsAlreadyInTrialException {
        Trial trial = this.trials[idTrial];
        User user = this.users.consultar(idUser);
        if (!user.isActive()) {
            trial.addUser(user);
            user.setActive();
            user.setTrial(trial);
        }
        else throw new UserIsAlreadyInTrialException();
    }

    public Question getCurrentQuestion(String idUser) throws UserNotFoundException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();
        Question q = user.currentQuestion();
        return q;
    }

    public void addAnswer(String idUser, Date date, String answer) throws UserNotFoundException, NoQuestionsException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();
        Question q = user.nextQuestion();
        if (q == null) throw new NoQuestionsException();
        user.addAnswer(q, answer);
        updateMostActiveTrial(user.getTrial());
    }

    private void updateMostActiveTrial(Trial trial) {
        if (this.mostActiveTrial==null) this.mostActiveTrial = trial;
        else if (this.mostActiveTrial.numAnswers()<trial.numAnswers()) this.mostActiveTrial = trial;
    }

    public Iterador<Answer> getAnswers(String idUser) throws UserNotFoundException, NoAnswersException {
        User user = this.users.consultar(idUser);
        if (user==null) throw new UserNotFoundException();

        Iterador<Answer> it = user.answers();
        if (!it.hiHaSeguent())  throw new NoAnswersException();

        return it;
    }

    public User mostActiveUser(int idTrial) {
        Trial trial = this.trials[idTrial];

        return trial.mostActiveUser();
    }

    public Trial mostActiveTrial() {
        return this.mostActiveTrial;
    }


    public int numUsers() {
        return this.users.nombreElems();
    }

    public int numTrials() {
        return this.numTrials;
    }

    public int numQuestionGroups() {
        return this.groups.nombreElems();
    }

    public int numQuestion4Group(String idGroup) {
        QuestionGroup qg = this.getQuestionGroup(idGroup);
        return (qg!=null?qg.numQuestions(): 0 );
    }

    public int numQuestionGroups4Trial(int idTrial) {
        Trial trial = this.trials[idTrial];

        return (trial!=null?trial.numQuestionGroups():0);
    }

    public int numUsers4Trial(int idTrial) {
        Trial trial = this.trials[idTrial];
        return (trial!=null?trial.numUsers():0);
    }

    public User getUser(String idUser) {
        return this.users.consultar(idUser);
    }

    public Iterador<QuestionGroup> getQuestionGroups() {
        return this.groups.elements();
    }

	@Override
	public void addClinician(String idClinician, String name, String surname, String knowledgeArea) {
		Clinician c  = this.getClinician(idClinician);
		if (c == null) {
			c = new Clinician(name, surname, knowledgeArea, idClinician);
			this.clinicians.afegir(idClinician, c);
		} else {
			c.setIdClinician(idClinician);
			c.setName(name);
			c.setSurname(surname);
			c.setKnowledgeArea(knowledgeArea);
		}
	}


	@Override
	public Clinician getClinician(String idClinician) {    
		Iterador<Clinician> it = this.clinicians.elements();
		Clinician clinician = null;
		boolean found = false;
		
		while (it.hiHaSeguent() && !found) {
		    clinician = it.seguent();
		    found = clinician.is(idClinician);
		}
		
		return (found ? clinician : null);
	}


	@Override
	public void addLaboratory(String idLaboratory, String name) {
		Laboratory lab = getLaboratory(idLaboratory);
		if(lab != null) {
			lab.setName(name);
		}else {
			this.laboratories[numLaboratories] = new Laboratory(idLaboratory, name);
			this.numLaboratories++;		
		}		
	}

	@Override
	public Laboratory getLaboratory(String idLaboratory) {
		int i;
		for (i = 0; i < this.numLaboratories; ++i) {
			Laboratory lab = this.laboratories[i];
			if (lab.getIdLaboratory() == idLaboratory) {
				return lab;
			}
		}
		return null;
	}


	@Override
	public void newSample(String idSample, String idUser, String idClinician, Date date)
			throws ClinicianNotFoundException, UserNotFoundException, TrialNotFoundException {
		
		//Recupera el user
		User u = this.getUser(idUser);
		if (u == null)throw new UserNotFoundException();
		
		//Crea el sample
		Sample s = new Sample(idSample, idUser, idClinician, date);
		s.setUser(u);
		
		//L'afegeix al AVL de samples
        this.samples.afegir(idSample, s);
        
        //L'afegeix a la cua de samples pendents d'enviar
        this.samplesPending.encuar(s);
        
        //Afegeix el samle a la llista encadenada de user
        u.addSample(s);
        
        //Afegeix el sample a la llista encadenada de clinican
        Clinician c = this.getClinician(idClinician);
        if (c == null) throw new ClinicianNotFoundException();
        c.addSample(s);
        
        //Afegeix el samle a la llista encadenada de trial
        Trial t = u.getTrial();
        if (t == null) throw new TrialNotFoundException();
        t.addSample(s);
	}


	@Override
	public Sample sendSample(Date date) throws NOSAmplesException {
		//Se obtiene el primer sample que se ha de enviar
		if (this.samplesPending.nombreElems() == 0) throw new NOSAmplesException();
		Sample s = this.samplesPending.desencuar();
			
		//Se añade el sample al laboratorio
		int i;
		for (i = 0; i < this.numLaboratories; ++i) {
			if (i < this.numLaboratories - 1) {
				Laboratory lab1 = this.laboratories[i];
				Laboratory lab2 = this.laboratories[i+1];
				//Se comparan dos laboratorios consecutivos
				//Si el primero tiene más elementos que el segundo, el sample se añade en el segundo
				if(lab1.getSamples().nombreElems() > lab2.getSamples().nombreElems()){
					lab2.addSample(s);
					break;
				}
			//En el caso que todos los laboratorios tengan el mismo numero de samples
			//Se añade el sample al primer laboratorio del vector
			} else {
				Laboratory lab3 = this.laboratories[0];
				lab3.addSample(s);
			}
		}
		
		//Se cambia el estado del sample a enviado
		s.setStatus(Status.SENDED);
		
		//Se añade la fecha del sample a enviado
		s.setDateSended(date);
		
		return s;
	}


	@Override
	public Sample processSample(String idLaboratory, Date date, String report)
			throws LaboratoryNotFoundException, NOSAmplesException {
		
		Laboratory lab = this.getLaboratory(idLaboratory);
		Sample s = null;
		
		if (lab == null) {
			throw new LaboratoryNotFoundException();
		}
		else if (lab.getSamples().nombreElems() <= 0) {
			throw new NOSAmplesException();
		}
		else {
			//Desencuar sample
			s = lab.getSamples().desencuar();
			//Modificar status
			s.setStatus(Status.COMPLETED);
			//Afegir la data de processat
			s.setDateCompleted(date);
			//Afegir el report
			s.setReport(report);
			
		}
		return s;
	}


	@Override
	public Iterador<Sample> samplesByTrial(int idTrial) throws TrialNotFoundException {
		Trial t = this.trials[idTrial];
		if (t == null) throw new TrialNotFoundException();
		return t.getSamples().elements();
	}


	@Override
	public Iterador<Sample> samplesByUser(String idUser) throws UserNotFoundException {
		User u = this.getUser(idUser);
		if (u == null) throw new UserNotFoundException();
		return u.getSamples().elements();
	}


	@Override
	public Iterador<Sample> samplesByClinician(String idClinician) throws ClinicianNotFoundException {
		Clinician c = this.getClinician(idClinician);
		if (c == null) throw new ClinicianNotFoundException();
		return c.getSamples().elements();
	}


	@Override
	public Sample getSample(String idSample) throws SampleNotFoundException {
		Iterador<Sample> it = this.samples.elements();
        Sample s = null;

        //Recorregut per tots els clinicians
        while (it.hiHaSeguent()) {
            s = it.seguent();
            if (s.getIdSample() == idSample) {
            	break;
            }
            s = null;
        }
        
        if (s==null) throw new SampleNotFoundException();
        else {
            return s;
        }
	}
	
	@Override
	public Clinician mostActiveClinician() throws NOClinicianException {		
		
		Iterador<Clinician> it = this.clinicians.elements();
        Clinician c = null;
        int mostActiveNumber = 0;

        //Recorregut per tots els clinicians
        while (it.hiHaSeguent()) {
            c = it.seguent();
            
            //Si el numero de samples d'el usuari que s'esta tractant es major al de l'usuari més actiu
            //L'usuari que s'esta tractant passa a ser l'usuari més actiu
            if (mostActiveNumber < c.numSamples()) {
            	this.mostActiveClinician = c;
            }
             
        }
		
        if (this.mostActiveClinician == null) throw new NOClinicianException();
		return this.mostActiveClinician;
	}


	@Override
	public int numClinician() {
		return this.clinicians.nombreElems();
	}


	@Override
	public int numSamples() {
		return this.samples.nombreElems();
	}


	@Override
	public int numSamplesByClinician(String idClinician) {
		//Es busca el clinician
		Iterador<Clinician> it = this.clinicians.elements();
		Clinician c = null;
		
        while (it.hiHaSeguent()) {
            c = it.seguent();
            if (c.getIdClinician() == idClinician) {
            	break;
            }
        }
		
		return c.numSamples();	
	}


	@Override
	public int numSamplesByUser(String idUser) {
		//Es busca el user
		Iterador<User> it = this.users.elements();
		User u = null;
		
        while (it.hiHaSeguent()) {
            u = it.seguent();
            if (u.getId() == idUser) {
            	break;
            }
        }
		
		return u.numSamples();	
	}


	@Override
	public int numSamplesByTrial(int idTrial) {
		Trial t = this.trials[idTrial];
		return t.numSamples();
	}


	@Override
	public int numPendingSamplesByLaboratory(String idLaboratory) {
		Laboratory lab = this.getLaboratory(idLaboratory);
		return lab.getSamples().nombreElems();
	}


	@Override
	public int numLaboratories() {
		return this.numLaboratories;
	}


}
