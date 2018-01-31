package com.example.asus.strokeanalyzer.Services;

import android.content.Context;
import com.example.asus.strokeanalyzer.Database.DatabaseAccess;
import com.example.asus.strokeanalyzer.Database.StrokeAnalyzerDatabase;
import com.example.asus.strokeanalyzer.Entities.OtherData;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Patient;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa udostępniająca operacje na bazie danych związane z tabelą zawierającą dane pacjenta oraz tabelą zawierającą odpowiedzi
 * pacjenta na pytania formularzy aplikacji.
 *
 * @author Stanisław Wasilkowski
 */

public final class PatientService {
    final private StrokeAnalyzerDatabase db;

    /**
     * Konstruktor umożliwiający pozyskanie obiektu bazy danych, na którym wykonywane będą operacje.
     *
     * @param context kontekst aplikacji wymagany do zbudowania obiektu bazy danych
     */
    public PatientService(Context context) {
        db = DatabaseAccess.getInstance(context).database;
    }

    /**
     * Metoda pobierająca listę pacjentów z bazy danych.
     *
     * @return lista wszystkich pacjentów zapisanych w bazie danych
     */
    public List<Patient> GetPatientsList(){
        List<Patient> patientsList = new LinkedList<>();
        List<com.example.asus.strokeanalyzer.Entities.Patient> entities;
        entities = db.patientDao().selectAll();

        for (com.example.asus.strokeanalyzer.Entities.Patient patient:
             entities) {
            Patient model = EntityToModel(patient);
            patientsList.add(model);
        }
        return  patientsList;
    }

    /**
     * Metoda pobierająca dane pacjenta z bazy danych na podstawie jego id.
     *
     * @param id id pacjenta, którego dane chcemy pozyskać
     * @return obiekt zawierający dane pacjenta
     */
    public Patient GetPatientById(int id) {
        return EntityToModel(db.patientDao().selectById(id));
    }

    /**
     * Metoda umożliwiając dodanie obiektu pacjenta do bazy danych. Funkcja dodaje obiekt pacjenta
     * do tabeli przechowującej dane pacjenta oraz dodaje wszystkie odpowiedzi pacjenta znajdujące się
     * w obiekcie patient do tabeli przechowującej odpowiedzi na pytania.
     *
     * @param patient obiekt klasy Patient, który chcemy umieścić w bazie danych
     * @return id pacjenta wygenerowane przy dodawaniu obiektu do bazy danych
     */
    public long AddPatient(Patient patient) {
        long id = db.patientDao().insert(ModelToEntity(patient));
        patient.Id = (int)id;

        for (OtherData data:
             modelDataToEntityData(patient)) {
            db.otherDataDao().insert(data);
        }
        return id;
    }

    /**
     * Metoda aktualizująca dane pacjenta w bazie danych.
     *
     * @param patient obiekt klasy Patient, którego dane mają zostać zaktualizowane
     */
    public void UpdatePatient(Patient patient){
        db.patientDao().update(ModelToEntity(patient));

        for (OtherData data:
                modelDataToEntityData(patient)) {
            if (db.otherDataDao().checkIfExists(patient.Id, data.id) == 0) {
                db.otherDataDao().insert(data);
            } else {
                db.otherDataDao().update(data);
            }
        }
    }

    /**
     * Metoda usuwająca pacjenta z bazy danych.
     *
     * @param patientID id pacjenta, którego dane mają zostać usunięte z bazy danych
     */
    public void DeletePatient(Integer patientID) {
        Patient patient = GetPatientById(patientID);
        db.otherDataDao().deleteByPatientId(patientID);
        db.nihssDao().deleteByPatientId(patientID);
        db.patientDao().delete(ModelToEntity(patient));
    }

    /**
     * Metoda sprawdzająca, czy pacjent o danym numerze identyfikacyjnym istnieje już w bazie danych.
     *
     * @param patientNumber numer identyfikacyjny pacjenta
     * @return true - jeżeli pacjent o takim numerze już istnieje w bazie; false - jeżeli pacjent o
     *          takim numerze nie istnieje w bazie
     */
    public boolean isPatientNumberTaken(long patientNumber) {
        return  (db.patientDao().checkIfPatientExistsByNumber(patientNumber) > 0);
    }

    /**
     * Metoda mapująca odpowiedzi użytkownika pozyskane z formularzy aplikacji na obiekty
     * typu OtherData przechowywane w bazie danych.
     *
     * @param model obiekt klasy Patient, którego odpowiedzi mają zostać przekonwertowane
     * @return lista obiektów typu OtherData otrzymanych z odpowiedi udzielonych przez
     * użytkownika
     */
    private List<OtherData> modelDataToEntityData(Patient model){
        List<OtherData> dataList = new LinkedList<>();

        for (Answer answer:
                model.PatientAnswers.values()) {
            OtherData data = new OtherData();
            data.answerId = answer.GetQuestionID();
            data.patientId = model.Id;

            if(answer instanceof NumericAnswer) {
                data.dataType = 0;
                data.numericData = ((NumericAnswer)answer).Value;
            }
            else {
                if (answer instanceof TextAnswer) {
                    data.dataType = 1;
                    data.textData = ((TextAnswer)answer).Value;
                }
                else {
                    data.dataType = 2;
                    data.booleanData = ((TrueFalseAnswer)answer).Value;
                }
            }

            dataList.add(data);
        }

        return dataList;
    }

    /**
     * Metoda mapująca profil pacjenta typu Model.Patient na obiekt klasy Entites.Patient
     * przechowywany w tabeli bazy danych zawierającej dane pacjentów.
     *
     * @param model obiekt klasy Model.Patient, który ma zostać zmapowany na obiekt bazodanowy
     * @return obiekt bazodanowy reprezentujący pacjenta
     */
    private com.example.asus.strokeanalyzer.Entities.Patient ModelToEntity(Patient model) {
        com.example.asus.strokeanalyzer.Entities.Patient entity;
        entity = new com.example.asus.strokeanalyzer.Entities.Patient();
        entity.id = model.Id;
        entity.Name = model.Name;
        entity.Surname = model.Surname;
        entity.PatientNumber = model.PatientNumber;

        return entity;
    }

    /**
     * Metoda mapująca profil pacjenta typu Entites.Patient przechowywany w tabeli bazy danych
     * zawierającej dane pacjentów na obiekty klasy Model.Patient.
     *
     * @param entity obiekt klasy Entities.Patient, który ma zostać zmapowany na obiekt klasy modelu aplikacji
     * @return obiekt z modelu aplikacji reprezentujący pacjenta
     */
    private Patient EntityToModel(com.example.asus.strokeanalyzer.Entities.Patient entity) {
        Patient patient = new Patient();
        patient.Id = entity.id;
        patient.Name = entity.Name;
        patient.Surname = entity.Surname;
        patient.PatientNumber = entity.PatientNumber;

        patient.PatientAnswers = new Hashtable<>();
        List<OtherData> otherDataList = db.otherDataDao().SelectByPatientId(patient.Id);
        for (OtherData data:
             otherDataList) {
            switch (data.dataType){
                case 0:
                    NumericAnswer numericAnswer;
                    numericAnswer = new NumericAnswer(data.answerId);
                    numericAnswer.Value = data.numericData;
                    patient.PatientAnswers.put(numericAnswer.GetQuestionID(), numericAnswer);
                    break;

                case 1:
                    TextAnswer textAnswer;
                    textAnswer = new TextAnswer(data.answerId);
                    textAnswer.Value = data.textData;
                    patient.PatientAnswers.put(textAnswer.GetQuestionID(), textAnswer);
                    break;

                case 2:
                    TrueFalseAnswer trueFalseAnswer;
                    trueFalseAnswer = new TrueFalseAnswer(data.answerId);
                    trueFalseAnswer.Value = data.booleanData;
                    patient.PatientAnswers.put(trueFalseAnswer.GetQuestionID(), trueFalseAnswer);
                    break;
            }
        }

        return patient;
    }
}
