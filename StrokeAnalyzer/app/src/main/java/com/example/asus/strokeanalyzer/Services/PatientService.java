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
 * Klasa definująca operacje na bazie danych związane z tablą zawierająca dane pacjenta oraz tablą zawierającą odpowiedzi
 * pacjenta na pytania
 *
 * @author Stanisław Wasilkowski
 */

public final class PatientService {
    private StrokeAnalyzerDatabase db;

    /**
     * Konstruktor umożliwiający pozyskanie obiektu bazy danych, na którym wykonywane będą operacje
     * Funkcja pobiera instancję klasy DatabaseAccess, z której następnie pobiera obiekt bazy danych
     *
     * @param context kontekst aplikacji wymagany do zbudowania obiektu bazy danych
     */
    public PatientService(Context context) {
        db = DatabaseAccess.getInstance(context).database;
    }

    /**
     * Metoda pobierająca listę pacjentów z bazy danych
     * Funkcja pobiera z bazy danych obiekty klasy Entities.Patient i mapuje je przy użyciu funkcji
     * EntityToModel na obiekty klasy Model.Patient. Tak zmapowane obiekty są dodawane do wynikowej
     * listy wszystkich pacjentów
     *
     * @return
     * {@code List<Patient>} lista wszystkich pacjentów zapisanych w bazie danych
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
     * Metoda pobierająca dane pacjenta z bazy danych na podstawie jego ID
     * Metoda pobiera z bazy danych obiekt Entities.PAtient a następnie mapuje go przy użyciu funkcji
     * EntityToModel na obiekt klasy Model.Patient
     *
     * @param id Id pacjenta, którego dane chcemy pozyskać
     * @return (Patient) obiekt zawierający dane pacjenta
     */
    public Patient GetPatientById(int id) {
        //patient.PatientAnswers = entityDataToModelData(db.otherDataDao().SelectByPatientId(id));
        return EntityToModel(db.patientDao().selectById(id));
    }

    /**
     * Metoda umożliwiając dodanie pacjenta do bazy danych
     * Funkcja dodaje obiekt pacjenta do tabeli przechowującej dane pacjenta oraz dodaje wszystkie
     * odpowiedzi pacjenta znajdujące się w obiekcie patient do tabeli przechowującej odpowiedzi na pytania
     *
     * @param patient obiekt klasy Patient, który chcemy umieścić w bazie danych
     * @return (long) id pacjenta wygenerowane przy dodawaniu obiektu do bazy danych
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
     * Metoda aktualizująca dane pacjenta w bazie danych
     * Funkcja aktualizuje profil pacjenta w tabeli przechowującej dane pacjenta, a następnie
     * dodaje bądź aktualizuje odpowiedzi na pytania z tabeli je przechowującej
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
     * Metoda usuwająca pacjenta z bazy danych
     * Funkcja tworzy obiekt pacjenta na podstawie podanego Id, a następnie usuwa wszytkie jego odpowiedzi na pytania,
     * wszystkie badania w skali NIHSS oraz dane pacjenta z odpowiednich tabel
     *
     * @param patientID Id pacjenta, którego dane mają zostać usunięte z bazy danych
     */
    public void DeletePatient(Integer patientID) {
        Patient patient = GetPatientById(patientID);
        db.otherDataDao().deleteByPatientId(patientID);
        db.nihssDao().deleteByPatientId(patientID);
        db.patientDao().delete(ModelToEntity(patient));
    }

    /**
     * Metoda sprawdzająca, czy pacjent o danym numerze identyfikacyjnym istnieje już w bazie danych
     * Funkcja poiera liczbępacjentów o podanym numerze i jeżeli jest ona większa od 0 zwraca wynik informujący
     * o istnieniu pacjenta o podanym numerze.
     *
     * @param patientNumber numer identyfikacyjny pacjenta
     * @return (boolean) true - jeżeli pacjent o takim numerze już istnieje w bazie; false - jeżeli pacjent o
     *          takim numerze nie istnieje w bazie
     */
    public boolean isPatientNumberTaken(long patientNumber) {
        return  (db.patientDao().checkIfPatientExistsByNumber(patientNumber) > 0);
    }

    /**
     * Metoda mapująca odpowiedzi na pytania formularzy aplikacji dotyczące pacjenta na obiekty
     * typu OtherData przechowywane w tabeli z odpowiedziami bazy danych
     * Funkcja przechodzi po wszystkich odpowiedziach udzielonych przez użytkownika i w zależności
     * od typu odpowiedzi wprowadza odpowiednie dane do odpowiedniego pola obiektu typu OtherData
     *
     * @param model obiekt klasy Patient, które odpowiedzi mają zostać przekonwertowane
     * @return
     * {@code List<OtherData>} lista obiektów typu OtherData otrzymanych z odpowiedi udzielonych przez
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
     * Metoda mapująca profil pacjenta pacjenta typu Model.Patient na obiekty klasy Entites.Patient
     * przechowywane w tabeli bazy danych zawierającej dane pacjentów
     *
     * @param model obiekt klasy Model.Patient, który ma zostać zmapowany na obiekt bazodanowy
     * @return (Entities.Patient) obiekt bazodanowy reprezentujący pacjenta
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
     * Metoda mapująca profil pacjenta pacjenta typu Entites.Patient przechowywane w tabeli bazy danych
     * zawierającej dane pacjentów na obiekty klasy Model.Patient
     * Funkcja przepisuje odpowiednie dane do nowego typu obiektu oraz pobiera z bazy danych wszystkie
     * odpowiedzi udzielone przez pacjenta a następnie po ich przekonwertowaniu dodaje je do listy
     * odpowiedzi w obiekcie Model.Patient
     *
     *
     * @param entity obiekt klasy Entities.Patient, który ma zostać zmapowany na obiekt klasy modelu aplikacji
     * @return (Model.Patient) obiekt z modelu aplikacji reprezentujący pacjenta
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
