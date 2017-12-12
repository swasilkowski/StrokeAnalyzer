package com.example.asus.strokeanalyzer.Services;

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

public class PatientService {
    private StrokeAnalyzerDatabase db = DatabaseAccess.getInstance().database;

    public List<Patient> GetPatientsList(){
        List<Patient> patientsList = new LinkedList<>();
        List<com.example.asus.strokeanalyzer.Entities.Patient> entities;
        entities = db.patientDao().selectAll();

        for (com.example.asus.strokeanalyzer.Entities.Patient patient:
             entities) {
            patientsList.add(EntityToModel(patient));
        }
        return  patientsList;
    }

    public Patient GetPatientById(int id) {
        Patient patient = EntityToModel(db.patientDao().selectById(id));
        return patient;
    }

    public long AddPatient(Patient patient) {
        long id = db.patientDao().insert(ModelToEntity(patient));
        patient.Id = (int)id;

        for (OtherData data:
             modelDatatoEntityData(patient)) {
            db.otherDataDao().insert(data);
        }
        return id;
    }

    public void UpdatePatient(Patient patient){
        db.patientDao().update(ModelToEntity(patient));

        for (OtherData data:
                modelDatatoEntityData(patient)) {
            db.otherDataDao().update(data);
        }
    }

    private List<OtherData> modelDatatoEntityData(Patient model){
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
