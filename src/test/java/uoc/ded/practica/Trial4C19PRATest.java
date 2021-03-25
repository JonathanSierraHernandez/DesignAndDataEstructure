package test.java.uoc.ded.practica;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.java.uoc.ded.practica.Trial4C19;
import main.java.uoc.ded.practica.exceptions.*;
import main.java.uoc.ded.practica.model.*;
import test.java.uoc.ded.practica.util.DateUtils;

import java.util.Date;

public class Trial4C19PRATest {

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
     * *feature*: (sobre la que fem @test): addClinician del TAD TrialC19
     * *given*: Hi ha 10 especialistes en el sistema
     * *scenario*:
     * - S'afegeix un nou especialista en el sistema
     * - S'afegeix un segon especialista en el sistema
     * - Es modifiquen les dades del segon especialista inserit
     */
    @Test
    public void testAddClinician() {

        // GIVEN:
        Assert.assertEquals(10, this.trial4C19.numClinician());
        //

        this.trial4C19.addClinician("idClinician100", "Maria", "Garcia", "biology, cells");
        Assert.assertEquals("Maria", this.trial4C19.getClinician("idClinician100").getName());
        Assert.assertEquals(11, this.trial4C19.numClinician());

        this.trial4C19.addClinician("idClinician9999", "XXXXX", "YYYYY", "KK,KK");
        Assert.assertEquals("XXXXX", this.trial4C19.getClinician("idClinician9999").getName());
        Assert.assertEquals(12, this.trial4C19.numClinician());

        this.trial4C19.addClinician("idClinician9999", "David", "Roncon", "K1,K2");
        Assert.assertEquals("David", this.trial4C19.getClinician("idClinician9999").getName());
        Assert.assertEquals("Roncon", this.trial4C19.getClinician("idClinician9999").getSurname());
        Assert.assertEquals("K1,K2", this.trial4C19.getClinician("idClinician9999").getKnowledgeArea());
        Assert.assertEquals(12, this.trial4C19.numClinician());

    }


    
    /**
     * *feature*: (sobre la que fem @test): addLaboratory del TAD TrialC19
     * *given*: Hi ha 5 Laboratoris en el sistema
     * *scenario*:
     * - S'afegeix un nou laboratori en el sistema
     * - S'afegeix un segon laboratori en el sistema
     * - Es modifiquen les dades del segon laboratori inserit
     */
    
    @Test
    public void testAddLaboratory() {

        // GIVEN:
        Assert.assertEquals(5, this.trial4C19.numLaboratories());
        //

        this.trial4C19.addLaboratory("LAB6", "LAB CLINIC 6");
        Assert.assertEquals("LAB CLINIC 6", this.trial4C19.getLaboratory("LAB6").getName());
        Assert.assertEquals(6, this.trial4C19.numLaboratories());

        this.trial4C19.addLaboratory("LAB7", "LAB XXXXXX");
        Assert.assertEquals("LAB XXXXXX", this.trial4C19.getLaboratory("LAB7").getName());
        Assert.assertEquals(7, this.trial4C19.numLaboratories());

        this.trial4C19.addLaboratory("LAB7", "LAB CLINIC 7");
        Assert.assertEquals("LAB CLINIC 7", this.trial4C19.getLaboratory("LAB7").getName());
        Assert.assertEquals(7, this.trial4C19.numLaboratories());
    }
    
    /**
     * @throws ClinicianNotFoundException 
     * @throws NOClinicianException 
     * @throws SampleNotFoundException 
     * @throws NOSAmplesException 
     * @throws LaboratoryNotFoundException 
     *

     */
    
    @Test
    public void testAddSamples() throws DEDException, ClinicianNotFoundException, NOClinicianException, SampleNotFoundException, NOSAmplesException, LaboratoryNotFoundException {
        Assert.assertEquals(10, this.trial4C19.numUsers());
        Assert.assertEquals(5, this.trial4C19.numLaboratories());

        this.trial4C19.newSample("sample1", "idUser7", "idClinician1", createDate("15-12-2020 15:00:00"));
        this.trial4C19.newSample("sample2", "idUser8", "idClinician1", createDate("15-12-2020 15:10:00"));
        this.trial4C19.newSample("sample3", "idUser9", "idClinician2", createDate("15-12-2020 15:20:00"));
        this.trial4C19.newSample("sample4", "idUser1", "idClinician3", createDate("15-12-2020 15:50:00"));
        this.trial4C19.newSample("sample5", "idUser2", "idClinician3", createDate("15-12-2020 16:00:00"));
        this.trial4C19.newSample("sample6", "idUser3", "idClinician3", createDate("15-12-2020 16:10:00"));
        this.trial4C19.newSample("sample7", "idUser4", "idClinician3", createDate("15-12-2020 16:20:00"));
        this.trial4C19.newSample("sample8", "idUser1", "idClinician1", createDate("16-12-2020 10:00:00"));

        Clinician clinician = this.trial4C19.mostActiveClinician();
        Assert.assertEquals("idClinician3", clinician.getIdClinician());

        Assert.assertEquals(8, this.trial4C19.numSamples());
        Assert.assertEquals(3, this.trial4C19.numSamplesByClinician("idClinician1"));
        Assert.assertEquals(1, this.trial4C19.numSamplesByClinician("idClinician2"));
        Assert.assertEquals(4, this.trial4C19.numSamplesByClinician("idClinician3"));
        Assert.assertEquals(2, this.trial4C19.numSamplesByUser("idUser1"));

        Assert.assertEquals(5, this.trial4C19.numSamplesByTrial(1));
        Assert.assertEquals(3, this.trial4C19.numSamplesByTrial(2));


        Sample sample1 = this.trial4C19.getSample("sample1");
        Assert.assertEquals(Trial4C19.Status.PENDING, sample1.getStatus());
        Sample sample2 = this.trial4C19.getSample("sample2");
        Assert.assertEquals(Trial4C19.Status.PENDING, sample2.getStatus());

        // sample 7
        Sample s1st = this.trial4C19.sendSample(createDate("15-12-2020 20:00:00"));

        // sample 6
        Sample s2nd = this.trial4C19.sendSample(createDate("15-12-2020 20:01:00"));

        // sample 5
        Sample s3rd = this.trial4C19.sendSample(createDate("15-12-2020 20:02:00"));

        // sample 4
        Sample s4th = this.trial4C19.sendSample(createDate("15-12-2020 20:03:00"));

        // sample 8
        Sample s5th = this.trial4C19.sendSample(createDate("15-12-2020 20:04:00"));

        // sample 2
        Sample s6th = this.trial4C19.sendSample(createDate("15-12-2020 20:05:00"));

        // sample 3
        Sample s7th = this.trial4C19.sendSample(createDate("15-12-2020 20:06:00"));

        // sample 1
        Sample s8th = this.trial4C19.sendSample(createDate("15-12-2020 20:07:00"));

        Date now = createDate("16-12-2020 11:30:00");

        Assert.assertEquals("sample7", s1st.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s1st.getUser().getLevel());
        Assert.assertEquals(96 , s1st.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s1st.getStatus());

        Assert.assertEquals("sample6", s2nd.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s2nd.getUser().getLevel());
        Assert.assertEquals(89 , s2nd.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s2nd.getStatus());

        Assert.assertEquals("sample5", s3rd.getIdSample());
        Assert.assertEquals(Trial4C19.Level.D,  s3rd.getUser().getLevel());
        Assert.assertEquals(35 , s3rd.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s3rd.getStatus());

        Assert.assertEquals("sample4", s4th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.C,  s4th.getUser().getLevel());
        Assert.assertEquals(86 , s4th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s4th.getStatus());

        Assert.assertEquals("sample8", s5th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.C,  s5th.getUser().getLevel());
        Assert.assertEquals(86 , s5th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s5th.getStatus());

        Assert.assertEquals("sample2", s6th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.B,  s6th.getUser().getLevel());
        Assert.assertEquals(28 , s6th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s6th.getStatus());

        Assert.assertEquals("sample3", s7th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.B,  s7th.getUser().getLevel());
        Assert.assertEquals(15 , s7th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s7th.getStatus());

        Assert.assertEquals("sample1", s8th.getIdSample());
        Assert.assertEquals(Trial4C19.Level.A,  s8th.getUser().getLevel());
        Assert.assertEquals(32 , s8th.getUser().years(now));
        Assert.assertEquals(Trial4C19.Status.SENDED, s8th.getStatus());

        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB1"));
        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB2"));
        Assert.assertEquals(2, this.trial4C19.numPendingSamplesByLaboratory("LAB3"));
        Assert.assertEquals(1, this.trial4C19.numPendingSamplesByLaboratory("LAB4"));
        Assert.assertEquals(1, this.trial4C19.numPendingSamplesByLaboratory("LAB5"));

        Sample sample7Lab1 = this.trial4C19.processSample("LAB1", now, "Report: Sample7 (LAB1)");
        Sample sample2Lab1 = this.trial4C19.processSample("LAB1", now, "Report: Sample2 (LAB1)");
        Assert.assertEquals("sample7", sample7Lab1.getIdSample());
        Assert.assertEquals("sample2", sample2Lab1.getIdSample());


        Sample sample6Lab2 = this.trial4C19.processSample("LAB2", now, "Report: Sample6 (LAB2)");
        Sample sample3Lab2 = this.trial4C19.processSample("LAB2", now, "Report: Sample3 (LAB2)");
        Assert.assertEquals("sample6", sample6Lab2.getIdSample());
        Assert.assertEquals("sample3", sample3Lab2.getIdSample());

        Sample sample5Lab3 = this.trial4C19.processSample("LAB3", now, "Report: Sample5 (LAB3)");
        Sample sample1Lab3 = this.trial4C19.processSample("LAB3", now, "Report: Sample1 (LAB3)");
        Assert.assertEquals("sample5", sample5Lab3.getIdSample());
        Assert.assertEquals("sample1", sample1Lab3.getIdSample());

        Sample sample4Lab4 = this.trial4C19.processSample("LAB4", now, "Report: Sample4 (LAB4)");
        Assert.assertEquals("sample4", sample4Lab4.getIdSample());

        Sample sample7Lab5 = this.trial4C19.processSample("LAB5", now, "Report: Sample7 (LAB5)");
        Assert.assertEquals("sample8", sample7Lab5.getIdSample());


        Assert.assertEquals(Trial4C19.Status.COMPLETED, s1st.getStatus());
        Assert.assertEquals("Report: Sample7 (LAB1)", s1st.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s2nd.getStatus());
        Assert.assertEquals("Report: Sample6 (LAB2)", s2nd.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s3rd.getStatus());
        Assert.assertEquals("Report: Sample5 (LAB3)", s3rd.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s4th.getStatus());
        Assert.assertEquals("Report: Sample4 (LAB4)", s4th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s5th.getStatus());
        Assert.assertEquals("Report: Sample7 (LAB5)", s5th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s6th.getStatus());
        Assert.assertEquals("Report: Sample2 (LAB1)", s6th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s7th.getStatus());
        Assert.assertEquals("Report: Sample3 (LAB2)", s7th.getReport());

        Assert.assertEquals(Trial4C19.Status.COMPLETED, s8th.getStatus());
        Assert.assertEquals("Report: Sample1 (LAB3)", s8th.getReport());

        Assert.assertEquals(createDate("15-12-2020 15:00:00"), sample1.getDateCreation() );
        Assert.assertEquals(createDate("15-12-2020 20:07:00"), sample1.getDateSended() );
        Assert.assertEquals(createDate("16-12-2020 11:30:00"), sample1.getDateCompleted() );


    }
    
    
    @Test(expected = UserNotFoundException.class)
    public void testAddSamplesAndUserNotFound() throws DEDException, ClinicianNotFoundException {
        this.trial4C19.newSample("sample7", "idUserXXX", "idClinician3", createDate("15-12-2020 16:20:00"));
    }
    
    @Test(expected = TrialNotFoundException.class)
    public void testAddSamplesAndTrialNotFound() throws DEDException, ClinicianNotFoundException {
        this.trial4C19.newSample("sample7", "idUser10", "idClinician3", createDate("15-12-2020 16:20:00"));
    }

    
    @Test(expected = ClinicianNotFoundException.class)
    public void testAddSamplesAndClinicianNotFound() throws DEDException, ClinicianNotFoundException {
        this.trial4C19.newSample("sample1", "idUser1", "idClinicianXXX", createDate("15-12-2020 16:20:00"));
    }
    
    @Test(expected = NOSAmplesException.class)
    public void testSendSampleAndNoSamples() throws DEDException, NOSAmplesException {
        this.trial4C19.sendSample(createDate("15-12-2020 16:20:00"));
    }
    
    @Test(expected = LaboratoryNotFoundException.class)
    public void testProcessSampleAndNotLaboratory() throws DEDException, LaboratoryNotFoundException, NOSAmplesException {
    	this.trial4C19.processSample("Test", createDate("15-12-2020 16:20:00"), "Report Test");
    }
    
    @Test(expected = NOSAmplesException.class)
    public void testProcessSampleAndNotSample() throws DEDException, LaboratoryNotFoundException, NOSAmplesException {
    	this.trial4C19.addLaboratory("LabTest", "LabNameTest");
    	this.trial4C19.processSample("LabTest", createDate("15-12-2020 16:20:00"), "Report Test");
    }
    
    @Test(expected = TrialNotFoundException.class)
    public void testSamplesByTrialAndTrialNotFound() throws DEDException, TrialNotFoundException {
    	this.trial4C19.samplesByTrial(33);
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testSamplesByUserAndUserNotFound() throws DEDException, UserNotFoundException {
    	this.trial4C19.samplesByUser("TestUser");
    }
    
    @Test(expected = ClinicianNotFoundException.class)
    public void testSamplesByClinicianAndClinicianNotFound() throws DEDException, ClinicianNotFoundException {
    	this.trial4C19.samplesByClinician("TestClinician");
    }
    
    @Test(expected = SampleNotFoundException.class)
    public void testGetSampleAndSampleNotFound() throws DEDException, SampleNotFoundException {
    	this.trial4C19.getSample("TestSample");
    }
    
    @Test(expected = NOClinicianException.class)
    public void testMostActiveClinicianAndClinicianException() throws DEDException, NOClinicianException {
    	this.trial4C19.mostActiveClinician();
    }
    
    

    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }

}