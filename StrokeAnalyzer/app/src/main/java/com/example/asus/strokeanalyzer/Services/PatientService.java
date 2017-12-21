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
import java.util.Map;

/**
 * Created by S. Wasilkowski on 2017-12-12.
 */

public final class PatientService {
    private StrokeAnalyzerDatabase db;

    public PatientService(Context context) {
        db = DatabaseAccess.getInstance(context).database;
    }

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

    public Patient GetPatientById(int id) {
        Patient patient = EntityToModel(db.patientDao().selectById(id));
        //patient.PatientAnswers = entityDataToModelData(db.otherDataDao().SelectByPatientId(id));
        return patient;
    }

    public long AddPatient(Patient patient) {
        long id = db.patientDao().insert(ModelToEntity(patient));
        patient.Id = (int)id;

        for (OtherData data:
             modelDataToEntityData(patient)) {
            db.otherDataDao().insert(data);
        }
        return id;
    }

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

    public void DeletePatient(Integer patientID) {
        Patient patient = GetPatientById(patientID);
        db.patientDao().delete(ModelToEntity(patient));
        //TODO: deleting all patients data
    }

    private Map<Integer, Answer> entityDataToModelData(List<OtherData> otherData) {
        Map<Integer, Answer> patientAnswers = new Hashtable<>();

        for (OtherData data:
             otherData) {
            switch (data.dataType) {
                case 0:
                    NumericAnswer answer = new NumericAnswer(data.answerId);
                    answer.Value = data.numericData;
                    patientAnswers.put(data.answerId, answer);
                case 1:
                    TextAnswer answer1 = new TextAnswer(data.answerId);
                    answer1.Value = data.textData;
                    patientAnswers.put(data.answerId, answer1);
                case 2:
                    TrueFalseAnswer answer2 = new TrueFalseAnswer(data.answerId);
                    answer2.Value = data.booleanData;
                    patientAnswers.put(data.answerId, answer2);
            }
        }

        return patientAnswers;
    }

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

    private com.example.asus.strokeanalyzer.Entities.Patient ModelToEntity(Patient model) {
        com.example.asus.strokeanalyzer.Entities.Patient entity;
        entity = new com.example.asus.strokeanalyzer.Entities.Patient();
        entity.id = model.Id;
        entity.Name = model.Name;
        entity.Surname = model.Surname;
        entity.PatientNumber = model.PatientNumber;

        return entity;
    }

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
