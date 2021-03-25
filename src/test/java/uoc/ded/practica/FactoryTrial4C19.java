package test.java.uoc.ded.practica;


import test.java.uoc.ded.practica.util.DateUtils;
import main.java.uoc.ded.practica.Trial4C19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.java.uoc.ded.practica.Trial4C19Impl;

public class FactoryTrial4C19 {


    public static Trial4C19 getTrial4C19() throws Exception {
        Trial4C19 trial4C19;
        trial4C19 = new Trial4C19Impl();

        ////
        //// USERS
        ////
        trial4C19.addUser("idUser1", "Maria", "Simo", DateUtils.createDate("07-01-1934 00:00:00"), Trial4C19.Level.C);
        trial4C19.addUser("idUser2", "Àlex", "Lluna", DateUtils.createDate("08-10-1985 00:00:00"), Trial4C19.Level.D);
        trial4C19.addUser("idUser3", "Pepet", "Ferra", DateUtils.createDate("30-03-1931 00:00:00"), Trial4C19.Level.D);
        trial4C19.addUser("idUser4", "Joana", "Quilez", DateUtils.createDate("07-01-1924 00:00:00"), Trial4C19.Level.D);
        trial4C19.addUser("idUser5", "Armand", "Morata", DateUtils.createDate("07-01-1942 00:00:00"), Trial4C19.Level.D);
        trial4C19.addUser("idUser6", "Jesus", "Sallent", DateUtils.createDate("07-01-1932 00:00:00"), Trial4C19.Level.D);
        trial4C19.addUser("idUser7", "Anna", "Casals", DateUtils.createDate("09-07-1988 00:00:00"), Trial4C19.Level.A);
        trial4C19.addUser("idUser8", "Mariajo", "Padró", DateUtils.createDate("02-06-1992 00:00:00"), Trial4C19.Level.B);
        trial4C19.addUser("idUser9", "Agustí", "Padró", DateUtils.createDate("15-01-2005 00:00:00"), Trial4C19.Level.B);
        trial4C19.addUser("idUser10", "Pepet", "Marieta", DateUtils.createDate("23-04-2008 00:00:00"), Trial4C19.Level.A);


        ////
        //// Trial
        ////
        trial4C19.addTrial(1, "Description 1");
        trial4C19.addTrial(2, "Description 2");
        trial4C19.addTrial(15, "Description 15");
        trial4C19.addTrial(8, "Description 8");
        trial4C19.addTrial(20, "Description 20");
        trial4C19.addTrial(18, "Description 18");


        ////
        //// QuestionGroups
        ////
        trial4C19.addQuestionGroup("habits",Trial4C19.Priority.MEDIUM );
        trial4C19.addQuestionGroup("wellness", Trial4C19.Priority.LOWER);
        trial4C19.addQuestionGroup("symptoms",Trial4C19.Priority.HIGH );

        ////
        //// Questions
        ////
        trial4C19.addQuestion("idQuestion1a", "Can't sleep because of coughing?", Trial4C19.Type.TEXT_PLAIN, null, "symptoms");
        trial4C19.addQuestion("idQuestion1b", "Do you have pain in the chest or upper abdomen ?", Trial4C19.Type.TEXT_PLAIN, null,"symptoms");
        trial4C19.addQuestion("idQuestion1c", "do you have a headache?", Trial4C19.Type.TEXT_PLAIN, null, "symptoms");

        trial4C19.addQuestion("idQuestion2a", "How long you wash your hands?", Trial4C19.Type.TEXT_PLAIN, null, "habits");
        String[] choices = {"cloth masks", "Surgical masks", "N95 masks"};
        trial4C19.addQuestion("idQuestion2b", "What kind of mask are you using ?", Trial4C19.Type.LIKERT, choices, "habits");
        trial4C19.addQuestion("idQuestion2c", "Good hydration is crucial for optimal health. How do you hydrate in one day?", Trial4C19.Type.TEXT_PLAIN, null, "habits");

        trial4C19.addQuestion("idQuestion3a", "do you have pain?",Trial4C19.Type.TEXT_PLAIN, null, "wellness");
        trial4C19.addQuestion("idQuestion3b", "do you feel itchy?",Trial4C19.Type.TEXT_PLAIN, null, "wellness");
        trial4C19.addQuestion("idQuestion3c", "do you have a skin rash?",Trial4C19.Type.TEXT_PLAIN, null, "wellness");

        ////
        //// Assign QuestionGroups
        ////
        trial4C19.assignQuestionGroup2Trial("habits", 1);
        trial4C19.assignQuestionGroup2Trial("wellness", 1);
        trial4C19.assignQuestionGroup2Trial("symptoms", 1);

        trial4C19.assignQuestionGroup2Trial("habits", 2);

        ////
        //// Assign Users
        ////
        trial4C19.assignUser2Trial(1, "idUser1");
        trial4C19.assignUser2Trial(1, "idUser2");
        trial4C19.assignUser2Trial(1, "idUser3");
        trial4C19.assignUser2Trial(2, "idUser4");

        trial4C19.assignUser2Trial(1, "idUser5");
        trial4C19.assignUser2Trial(1, "idUser6");
        trial4C19.assignUser2Trial(1, "idUser7");
        trial4C19.assignUser2Trial(2, "idUser8");
        trial4C19.assignUser2Trial(2, "idUser9");

        ////
        //// Clinician
        ////
        trial4C19.addClinician("idClinician1", "Mario", "Gas", "K1, K2, K3");
        trial4C19.addClinician("idClinician2", "Luke", "Tevi", "K6, K7");
        trial4C19.addClinician("idClinician3", "Jordi", "Aguilar", "K2");
        trial4C19.addClinician("idClinician4", "Héctor", "Vidal", "K1, K5");
        trial4C19.addClinician("idClinician5", "Josep", "Yúfera", "K9, K2, K1");
        trial4C19.addClinician("idClinician6", "Mayte", "Garcia", "K5, K8");
        trial4C19.addClinician("idClinician7", "Krunal", "Pere", "K9, K6");
        trial4C19.addClinician("idClinician8", "Moha", "López", "K3, K6, K9");
        trial4C19.addClinician("idClinician9", "Alberto", "Sánchez", "K2, K4, K5");
        trial4C19.addClinician("idClinician10", "Celia", "Garcia", "K6, K4, K2");

        ////
        //// Laboratories
        ////
        trial4C19.addLaboratory("LAB1","Lab Clínic 1");
        trial4C19.addLaboratory("LAB2","Lab Clínic 2");
        trial4C19.addLaboratory("LAB3","Lab Clínic 3");
        trial4C19.addLaboratory("LAB4","Lab Clínic 4");
        trial4C19.addLaboratory("LAB5","Lab Clínic 5");

        return trial4C19;
    }



}