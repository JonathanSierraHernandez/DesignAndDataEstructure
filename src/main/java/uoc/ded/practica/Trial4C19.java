package main.java.uoc.ded.practica;

import main.java.uoc.ded.practica.exceptions.*;
import main.java.uoc.ded.practica.model.*;
import uoc.ei.tads.Iterador;

import java.util.Date;

/**
 * Definició del TAD de gestió de la plataforma d'assajos clínics
 */
public interface Trial4C19 {


    /**
     * dimensió del contenidor d'usuaris
     */
    public static final int U = 560;

    /**
     * dimensió del contenidor d'assajos
     */
    public static final int T = 60;

    /**
     * dimensió del contenidor de grups de preguntas
     */
    public static final int G = 20;

    /**
     * dimensió del contenidor de laboratoris
     */
    public static final int L = 15;
    int MAX_SAMPLES_LAB = 500;


    enum Type {
        TEXT_PLAIN,
        LIKERT
    }

    enum Priority {
        LOWER,
        MEDIUM,
        HIGH
    }

    enum Level {
        D,
        C,
        B,
        A
    }

    /**
    * Status per les mostres
    */
    enum Status {
        PENDING,
        SENDED,
        COMPLETED
    }

    /**
     * Mètode que afegeix un nou usuari al sistema
     *
     * @param idUser  identificador de l'usuari
     * @param name    nom de l'usuari
     * @param surname cognom de l'usuari
     * @param birthday data d'aniversari
     * @param level nivell de gravetat
     * @pre cert
     * @post si el codi d’usuari és nou, els usuaris seran els mateixos
     * més un nou usuari amb les dades indicades. Sinó les dades
     * de l’usuari s’hauran actualitzat amb les noves.
     */
    public void addUser(String idUser, String name, String surname, Date birthday, Level level);


    /**
     * Mètode que afegeix un nou assaig clínic al sitema
     * @param idTrial identificador de l'assaig clínic
     * @param description descripció de l'assaig clínic
     *
     * @pre cert
     * @post Si el codi de l'assaig clínic no existeix els assajos clínics
     * seran els mateixos més un nou amb les dades indicades. Sinó caldrà informar de l'error
     */
    public void addTrial (int idTrial, String description) throws TrialAlreadyExistsException;

    /**
     * Mètode que afegeix un nou grup de preguntes al sistema
     * @param idQuestionGroup identificador del grup de preguntes
     * @param priority prioritat del grup de preguntes
     *
     * @pre cert
     * @post si el codi de grup de preguntes és nou, els grups de preguntes
     * seran els mateixos més un nou amb les dades indicades. Sinó les dades
     * del grup de preguntes s’hauran actualitzat amb les noves.
     */
    public void addQuestionGroup(String idQuestionGroup, Priority priority);

    /**
     *
     * @param idQuestion identificador de la pregunta
     * @param wording texto de la pregunta
     * @param type tipo de respuestas (texto libre o Likert)
     * @param choices opcions disponibles per la pregunta
     * @param idGroup identificador del grupo de preguntas
     *
     *  @pre cert
     *  @post Si el codi de pregunta és nou, les preguntes seran les mateixes més una nova.
     *  Sinó  les dades de la pregunta s'hauran actualitzat amb les noves. Si el grup de preguntes
     *  no existeix caldrà infomar de l'error
     *
     */
    public void addQuestion(String idQuestion, String wording, Type type, String[] choices, String idGroup)
            throws QuestionGroupNotFoundException;

    /**
     * Mètode que proporciona les preguntes d'un group de preguntes
     * @param idGroup grup de preguntes
     * @return retorna un iterador de preguntes
     *
     * @pre cert.
     * @post retorna un iterador per recòrrer les preguntes d'un grup de preguntes
     */
    public Iterador<Question> getQuestions(String idGroup) throws QuestionGroupNotFoundException;


    /**
     * Mètode que permet afegir un grup de preguntes a un assaig clínic
     * @param idGroup identificador del grup de preguntes
     * @param idTrial identificador de l'assaig clínic
     *
     * @pre cert
     * @post s'actualitza l'estructura de preguntes de l'assaig clínic. Si el grup o assaig
     * clínic no existeix, caldrà informar de l'error
     */
    public void assignQuestionGroup2Trial(String idGroup, int idTrial)
            throws QuestionGroupNotFoundException, TrialNotFoundException;

    /**
     * Mètode que permet assignar un usuari a un assaig clínic
     * @param idTrial identificador de l'assaig clínic
     * @param idUser identificador de l'usuari
     *
     * @pre l'usuari i l'assaig clínic existeixen
     * @post s'actualitza l'estructura d'usuaris d'un assaig clínic. Si l'usuari ja
     * pertany a un assaaig clínic caldrà informar de l'error
     */
    public void assignUser2Trial(int idTrial, String idUser)
            throws UserIsAlreadyInTrialException;

    /**
     * Mètode que proporciona la següent pregunta a respondre
     * @param idUser identificador de l'usuari
     * @return retorna la següent pregunta a respondre
     *
     * @pre cierto
     * @post retorna la següent pregunta a respondre. En cas que l'usuari no existeixi
     * caldrà mostrar l'error. En cas que no hi hagi preguntes retorna un null
     */
    public Question getCurrentQuestion(String idUser) throws UserNotFoundException;

    /**
     * Mètode que permet respondre a preguntes d'un assaig clínic
     * @param idUser identificador de l'usuari
     * @param date data en la que es produeix la resposta
     * @param answer la resposta
     *
     * @pre cert
     * @post el nombre de respostes d'un usuari serà el mateix més la nova resposta. Si
     * l'usuari no existeix caldrà mostrar l'error.
     */
    public void addAnswer(String idUser, Date date, String answer)
        throws UserNotFoundException, NoQuestionsException;


    /**
     * Mètode que proporciona les respostes d'un usuari ordenades per data
     * @param idUser identificador de l'usuari
     * @return retorna un iterador de les respostes d'un usuari
     *
     * @pre cert
     * @post retora un iterador ordenat de respostes. En cas que l'usuari no existei o
     * no hi hagi preguntes caldrà informar de l'error
     */
    public Iterador<Answer> getAnswers(String idUser)
            throws UserNotFoundException, NoAnswersException;


    /**
     * Mètode que proporciona l'usuari que ha realitzat més respostes d'un assaig clínic
     * @param idTrial identificador de l'assaig
     * @return retorna l'usuari més actiu dins un assaig clínic
     *
     * @pre l'assaig clínic existeix
     * @post retorna l'usuari més actiu d'un assaig
     */
    public User mostActiveUser(int idTrial);

    /**
     * Mètode que proporciona l'assaig clínic amb més participació
     *
     * @pre cert
     * @post retorna l'assaig que més respostes ha generat
     *
     * @return retorna l'assaig clínic amb més participació
     */
    public Trial mostActiveTrial();

    /**
     * Mètode que proporciona el nombre d'usuaris
     * @return retorna el total d'usuaris
     */
    public int numUsers();


    /**
     * Mètode que proporciona el nombre d'assajos clínics
     * @return retorna el total d'assajos clínics
     */
    public int numTrials();


    /**
     * Mètode que proporciona el nombre de grups de preguntes
     * @return retorna el total de nombre de preguntes
     */
    public int numQuestionGroups();

    /**
     * Mètode que proporciona el nombre de preguntes d'un grup de preguntes
     * @param idGroup identificador del grup
     * @return retorna el nombre de preguntes d'un grup. En cas que el grup no existeixi retorna 0
     */
    public int numQuestion4Group(String idGroup);

    /**
     * Mètode que proporciona el nombre de grups de preguntes d'un assaig
     * @param idTrial identificador de l'assaig clínic
     * @return retorna el nombre de grups o 0 i no existeix l'assaig clínic
     */
    public int numQuestionGroups4Trial(int idTrial);


    /**
     * Mètode que proporciona el nombre d'usuaris d'un assaig clínic
     * @param idTrial identificador de l'assaig clínic
     * @return retorna el nombre d'usuaris d'un assaig clínic o 0 si no existeix l'assaig
     */
    public int numUsers4Trial(int idTrial);

    /**
     * Mètode que proporciona un usuari identificat
     *
     * @param idUser identificador de l'usuari
     * @return retorna l'usuari identificcat per idUser o null en cas que no existeixi
     */
    public User getUser(String idUser);

    /**
     * Mètode que proporciona els grups de preguntes existents en el sistema
     * @return retorna un iterador amb els grups de preguntes
     */
    public Iterador<QuestionGroup> getQuestionGroups();


    //
    // PRA
    //

    /**
     * Mètode que afegeix un nou especialista en el sistema
     * @pre cert.
     * @post si el codi d'especialista és nou, els usuaris seran els mateixos més
     * un nou especialista amb les dades indicades. Sinó les dades de l'especialista
     * s'hauran actualitzat amb els nous.
     *
     * @param idClinician
     * @param name
     * @param surname
     * @param knowledgeArea
     */
    public void addClinician (String idClinician, String name, String surname, String knowledgeArea);


    /**
     * Mètode que proporciona informació sobre un especialista
     * @param idClinician identificador de l'especialista
     * @return retorna l'especialista
     */
    public Clinician getClinician(String idClinician);


    /**
     * Mètode que afegeix un nou laboratori en el sistema
     * @pre cert.
     * @post si el codi de laboratori és nou, els laboratoris seran els
     * mateixos més un nou laboratori amb les dades indicades. Sinó les dades del laboratori s'hauran actualitzat amb els nous.
     *
     * @param idLaboratory identificador del laboratori
     * @param name nom del laboratori
     */
    public void addLaboratory(String idLaboratory, String name);

    /**
     * Mètode que proporciona informació sobre un laboratori
     * @param idLaboratory identificador del laboratori
     * @return retorna el laboratori
     */
    public Laboratory getLaboratory(String idLaboratory);


    /**
     * Mètode que genera una nova mostra en el sistema
     *
     * @pre cert.
     * @post El nombre de mostres pendents d'enviar-se serà el mateix més una mostra més. El nombre de
     * mostres global serà el mateix més una mostra més. El nombre de mostres
     * associades a un assaig clínic serà el mateix més una mostra més. El nombre
     * de mostres associades a un usuari serà el mateix més una mostra més. El nombre
     * de mostres associades a un especialista serà el mateix més una mostra més. En cas
     * que no existeixi l'usuari, l'especialista o assaig clínic associat a l'usuari s'haurà d'informar.
     *
     * @param idSample identidicador de la mostra
     * @param idUser identificador de l'usuari
     * @param idClinician identificador de l'especialista
     * @param date data en la que es realitza l'extracció de la mostra
     * @throws ClinicianNotFoundException Es llença aquesta excepció en el cas que l'especialista no existeix
     * @throws UserNotFoundException Es llença aquesta excepció en el cas que l'usuari no existeixi
     * @throws TrialNotFoundException Es llença aquesta excepció en el cas que l'usuari no estigui vincula a cap assaig
     */
    public void newSample(String idSample, String idUser, String idClinician, Date date)
            throws ClinicianNotFoundException, UserNotFoundException, TrialNotFoundException;

    /**
     * Mètode que realitza l'enviament de la mostra al laboratori pertinent
     * @pre cert.
     * @post El nombre de mostres pendents a processar-se pel laboratori que espera la
     * següent mostra serà el mateix més una mostra més. El següent laboratori que
     * espera rebre una mostra serà el consecutiu, i en cas que sigui l'últim
     * laboratori el següent laboratori serà el primer. En cas que no existeixi
     * cap mostra pendent d'enviés, s'informarà del cas.
     *
     * @param date data en la que es produeix l'enviament
     * @return retorna la mostra que s'ha enviat
     * @throws NOSAmplesException es llença aquesta excepció en cas que no hi hagi mostres a enviar
     */
    public Sample sendSample(Date date) throws NOSAmplesException;

    /**
     * Mètode que actualitza la informació de la mostra generant un informe
     *
     * @pre cert.
     * @post El nombre de mostres pendents a processar-se pel laboratori serà
     * el mateix menys una mostra que ja ha estat processada. En cas que no
     * existeixin mostres pendents de processar, s'informarà del cas.
     *
     * @param idLaboratory identificador del laboratori
     * @param date data en la que es produeix l'informe
     * @param report informe associat a la mostra
     * @return retorna la mostra
     * @throws LaboratoryNotFoundException es llença aquesta excepció en cas que no hi hagi laboratori
     * @throws NOSAmplesException es llença aquesta excepció en cas que no hi hagi mostres pendents de processar
     */
    public Sample processSample(String idLaboratory, Date date, String report)
            throws LaboratoryNotFoundException, NOSAmplesException;


    /**
     * Mètode que proporciona un iterador de mostres d'un assaig clínic
     *
     * @pre cert.
     * @post retorna un iterador per recórrer les mostres d'un assaig clínic
     * @param idTrial
     * @return retorna l'iterador de mostres
     * @throws TrialNotFoundException Es llença l'excepció en cas que no existeixi l'assaig clínic
     */
    public Iterador<Sample> samplesByTrial(int idTrial) throws TrialNotFoundException;

    /**
     * Mètode que proporciona un iterador de mostres d'un usuari
     *
     * @pre cert.
     * @post retorna un iterador per recórrer les mostres d'un usuari
     *
     * @param idUser identificador de l'usuari
     * @return retorna l'iterador de mostres
     * @throws UserNotFoundException es llença l'excepció en cas que no existeixi l'usuari
     */
    public Iterador<Sample> samplesByUser(String idUser) throws UserNotFoundException;

    /**
     * Mètode que proporciona les mostres d'un especialista
     *
     * @pre cert.
     * @post retorna un iterador per recórrer les mostres associades a un especialista
     *
     * @param idClinician identificador de l'especialista
     * @return retorna l'iterador de mostres
     * @throws ClinicianNotFoundException es llença l'excepció en cas que l'especialista no existeixi
     */
    public Iterador<Sample> samplesByClinician(String idClinician) throws ClinicianNotFoundException;

    /**
     * Mètode que proporciona informació sobre la mostra
     *
     * @pre cert.
     * @post retorna la mostra identificada. En cas que no existeixi la mostra, s'haurà d'informar
     *
     * @param idSample identificador de la mostra
     * @return retorna la mostra
     * @throws SampleNotFoundException es llença l'excepció en cas que no existeixi la mostra
     */
    public Sample getSample(String idSample) throws SampleNotFoundException;

    /**
     * Mètode que proporciona l'especialista més actiu
     *
     * pre cert.
     * @post retorna l'especialista que més mostres ha extret
     *
     * @return retorna l'especialista més actiu
     * @throws NOClinicianException Es llença l'excepció en cas que no hi hagi cap especialista
     */
    public Clinician mostActiveClinician() throws NOClinicianException;



    /**
     * Mètode que proporciona el nombre d'especialistes
     * @return retorna el nombre d'especialistes
     */
    public int numClinician();

    /**
     * Mètode que proporciona el nombre de mostres
     * @return retorna el nombre de mostres
     */
    public int numSamples();

    /**
     * Mètode que proporciona el nombre de mostres d'un especialista
     * @return retorna el nombre de mostres d'un especialista
     */
    public int numSamplesByClinician(String idClinician);

    /**
     * Mètode que proporciona el nombre de mostres d'un usuari
     * @return retorna el nombre de mostres d'un usuari
     */
    public int numSamplesByUser(String idUser);

    /**
     * Mètode que proporciona el nombre de mostres d'un assaig
     * @return retorna el nombre de mostres d'un assaig
     */
    public int numSamplesByTrial(int idTrial);

    /**
     * Mètode que proporciona el nombre de mostres pendents de ser processades per un laboratori
     * @return retorna el nombre de mostres pendents de ser processades per un laboratori
     */
    public int numPendingSamplesByLaboratory(String idLaboratory);

    /**
     * Mètode que proporciona el nombre de laboratoris
     * @return retorna el nombre de laboratoris
     */
    public int numLaboratories();


}


