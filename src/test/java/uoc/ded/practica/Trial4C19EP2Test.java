package test.java.uoc.ded.practica;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import main.java.uoc.ded.practica.exceptions.*;
import main.java.uoc.ded.practica.model.Answer;
import main.java.uoc.ded.practica.model.Question;
import main.java.uoc.ded.practica.model.QuestionGroup;
import test.java.uoc.ded.practica.util.DateUtils;
import main.java.uoc.ded.practica.Trial4C19;
import uoc.ei.tads.Iterador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trial4C19EP2Test {

    private Trial4C19 trial4C19;

    @Before
    public void setUp() throws Exception {
        this.trial4C19 = FactoryTrial4C19.getTrial4C19();
    }

    @After
    public void tearDown() {
        this.trial4C19 = null;
    }


    /**
     * *feature*: (sobre la que fem @test): addUser del TAD TrialC19
     * *given*: Hi ha 10 usuaris en el sistema
     * *scenario*:
     * - S'afegeix un nou usuari en el sistema
     * - S'afegeix un segon usuari en el sistema
     * - Es modifiquen les dades del segon usuari inserir
     */
    @Test
    public void testAddUser() {

        // GIVEN:
        Assert.assertEquals(10, this.trial4C19.numUsers());
        //

        this.trial4C19.addUser("idUser1000", "Robert", "Lopez", createDate("02-01-1942 00:00:00"), Trial4C19.Level.C);
        Assert.assertEquals("Robert", this.trial4C19.getUser("idUser1000").getName());
        Assert.assertEquals(11, this.trial4C19.numUsers());

        this.trial4C19.addUser("idUser9999", "XXXXX", "YYYYY", createDate("12-11-1962 00:00:00"), Trial4C19.Level.D);
        Assert.assertEquals("XXXXX", this.trial4C19.getUser("idUser9999").getName());
        Assert.assertEquals(12, this.trial4C19.numUsers());

        this.trial4C19.addUser("idUser9999", "Lluis", "Casals", createDate("22-07-1938 00:00:00"), Trial4C19.Level.C);
        Assert.assertEquals("Lluis", this.trial4C19.getUser("idUser9999").getName());
        Assert.assertEquals("Casals", this.trial4C19.getUser("idUser9999").getSurname());
        Assert.assertEquals(12, this.trial4C19.numUsers());
    }


    /**
     * *feature*: (sobre la que fem @test): addTrial del TAD Trial4C19
     * *given*: Hi ha 6 Assajos en el sistema
     * *scenario*:
     * - S'afegeix un nou assaig en el sistema
     * - S'afegeix un segon assaig en el sistema
     */
    @Test
    public void testAddTrial()
            throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        //

        this.trial4C19.addTrial(22, "Description 22");
        this.trial4C19.addTrial(6, "Description 6");
        Assert.assertEquals(8, this.trial4C19.numTrials());
    }

    /**
     * *feature*: (sobre la que fem @test): addTrial del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema
     * *scenario*:
     * - S'afegeix un nou assaig en el sistema
     * - S'afegeix un segon assaig en el sistema que ja existeix
     */
    @Test(expected = TrialAlreadyExistsException.class)
    public void testAddTrialAlreadyExists()
            throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        //

        this.trial4C19.addTrial(22, "Description 22222");
        Assert.assertEquals(7, this.trial4C19.numTrials());
        this.trial4C19.addTrial(22, "Description 22222");

    }


    /**
     * *feature*: (sobre la que fem @test): AddQuestionGroup del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema i tres grups de preguntes
     * <p>
     * *scenario*:
     * - S'afegeix un nou grup de preguntes
     */
    @Test
    public void testAddQuestionGroup() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        //
        trial4C19.addQuestionGroup("hygiene", Trial4C19.Priority.LOWER);


        Assert.assertEquals(4, this.trial4C19.numQuestionGroups());

        Iterador<QuestionGroup> it = this.trial4C19.getQuestionGroups();
        QuestionGroup qg1 = it.seguent();
        QuestionGroup qg2 = it.seguent();
        QuestionGroup qg3 = it.seguent();
        QuestionGroup qg4 = it.seguent();

        Assert.assertEquals("symptoms", qg1.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.HIGH, qg1.getPriority());

        Assert.assertEquals("habits", qg2.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.MEDIUM, qg2.getPriority());

        Assert.assertEquals("wellness", qg3.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.LOWER, qg3.getPriority());

        Assert.assertEquals("hygiene", qg4.getIdGroup());
        Assert.assertEquals(Trial4C19.Priority.LOWER, qg4.getPriority());
    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'afegeix una nova pregunta
     */
    @Test
    public void testAddQuestion() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        trial4C19.addQuestionGroup("hygiene", Trial4C19.Priority.LOWER);
        Assert.assertEquals(0, this.trial4C19.numQuestion4Group("hygiene"));

        Assert.assertEquals(4, this.trial4C19.numQuestionGroups());


        trial4C19.addQuestion("idQuestion100", "theWording", Trial4C19.Type.TEXT_PLAIN, null, "hygiene");
        Assert.assertEquals(1, this.trial4C19.numQuestion4Group("hygiene"));

    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'afegeix un nou grup de preguntes sobre un grup de preguntes inexistent
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testAddQuestionQuestionGroupNotFound() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        trial4C19.addQuestion("idQuestion100", "theWording", Trial4C19.Type.TEXT_PLAIN, null, "XXXXXXXXXX");
    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - es consulten les preguntes d'un grup de preguntes
     */
    @Test
    public void testGetQuestions() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        Iterador<Question> it = trial4C19.getQuestions("symptoms");

        Question q1 = it.seguent();
        Assert.assertEquals("idQuestion1a", q1.getIdQuestion());

        Question q2 = it.seguent();
        Assert.assertEquals("idQuestion1b", q2.getIdQuestion());

        Question q3 = it.seguent();
        Assert.assertEquals("idQuestion1c", q3.getIdQuestion());

    }

    /**
     * *feature*: (sobre la que fem @test): AddQuestion del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - es consulten les preguntes d'un grup de preguntes INEXISTENT
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testGetQuestionsAndQuestionGroupNotFound() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        //
        Iterador<Question> it = trial4C19.getQuestions("XXXXXX");
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * *given*: Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     * <p>
     * *scenario*:
     * - S'assigna un segon grup de preguntes a un assaig clínic (2)
     */
    @Test
    public void testAssignQuestionGroup2Trial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));
        //

        trial4C19.assignQuestionGroup2Trial("symptoms", 2);

        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(2, trial4C19.numQuestionGroups4Trial(2));
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * given*: Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     * <p>
     * *scenario*:
     * - S'assigna un  grup de preguntes INEXISTENT a un assaig clínic
     */
    @Test(expected = QuestionGroupNotFoundException.class)
    public void testAssignQuestionGroup2TriaAndQuestionGroupNotFoundl() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));

        //
        trial4C19.assignQuestionGroup2Trial("XXXXX", 1);
    }

    /**
     * *feature*: (sobre la que fem @test): assignQuestionGroup2Trial del TAD Trial4C19
     * *given*: Hi ha 6 assajos en el sistema,  tres grups de preguntes i tres preguntes per grup
     * <p>
     * *scenario*:
     * - S'assigna un  grup de preguntes a un assaig clínic INEXISTENT
     */
    @Test(expected = TrialNotFoundException.class)
    public void testAssignQuestionGroup2TriaAndTrialNotFoundl() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(3, trial4C19.numQuestionGroups4Trial(1));
        Assert.assertEquals(1, trial4C19.numQuestionGroups4Trial(2));

        //
        trial4C19.assignQuestionGroup2Trial("habits", 50);
    }

    /**
     * *feature*: (sobre la que fem @test): assignUser2Trial del TAD Trial4C19
     * given Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     *  <p>
     * *scenario*:
     * - S'assigna un usuari a un assaig clínic
     * - S'assigna un segon usuari a un assaig clínic
     * - S'assigna un tercer usuari a un assaig clínic
     * - S'assigna un primer usuari a un nou assaig clínic
     */
    @Test
    public void testAssignUser2Trial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //
        trial4C19.assignUser2Trial(1, "idUser10");


        Assert.assertEquals(7, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
    }

    /**
     * *feature*: (sobre la que fem @test): assignUser2Trial del TAD Trial4C19
     * given Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     *  <p>
     * *scenario*:
     * - S'assigna un usuari a un assaig clínic
     */
    @Test(expected = UserIsAlreadyInTrialException.class)
    public void testAssignUser2TrialAndUserAlreadyInTrial() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //
        trial4C19.assignUser2Trial(2, "idUser1");
    }

    /**
     * *feature*: (sobre la que fem @test): getCurrentQuestion del TAD Trial4C19
     * given
     *  Hi ha:
     *  - 6 assajos en el sistema
     *  - tres grups de preguntes
     *  - tres preguntes per grup
     *  - 3 grups de preguntes a l'assaig 1
     *  - 1 grups de preguntes a l'assaig 2
     *  - 3 usuaris assignats a l'assaig 1
     *  - 1 usuari assignat a l'assaig 2
     * <p>
     * *scenario*:
     *  - Es consulta la primera pregunta
     *  - Es consulta la següent pregunta
     *  - Es consulta la tercera pregunta

     */
    @Test
    public void testAnswerQuestions() throws DEDException {
        // GIVEN:
        Assert.assertEquals(6, this.trial4C19.numTrials());
        Assert.assertEquals(3, this.trial4C19.numQuestionGroups());
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("symptoms"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("habits"));
        Assert.assertEquals(3, this.trial4C19.numQuestion4Group("wellness"));
        Assert.assertEquals(6, trial4C19.numUsers4Trial(1));
        Assert.assertEquals(3, trial4C19.numUsers4Trial(2));
        //

        Question q1 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1a", q1.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:00:00"), "NO");

        Question q2 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1b", q2.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:15:00"), "YES");

        Question q3 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion1c", q3.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:20:00"), "YES");

        Question q4 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2a", q4.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 17:250:00"), "5 times a day");

        Question q5 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2b", q5.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 18:00:00"), "N95 masks");

        Question q6 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion2c", q6.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 19:00:00"), "3 times a day");

        Question q7 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3a", q7.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 20:00:00"), "Yes");

        Question q8 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3b", q8.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 21:00:00"), "Yes");

        Question q9 = trial4C19.getCurrentQuestion("idUser1");
        Assert.assertEquals("idQuestion3c", q9.getIdQuestion());
        trial4C19.addAnswer("idUser1", createDate("19-10-2020 22:00:00"), "Yes");

        Iterador<Answer> it = trial4C19.getAnswers("idUser1");

        Answer a1 = it.seguent();
        Assert.assertEquals("NO",a1.getAnswer() );

        Answer a2 = it.seguent();
        Assert.assertEquals("YES",a2.getAnswer() );

        Answer a3 = it.seguent();
        Assert.assertEquals("YES",a3.getAnswer() );

    }

    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }

}