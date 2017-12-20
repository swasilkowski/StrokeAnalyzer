package com.example.asus.strokeanalyzer.Services;

import com.example.asus.strokeanalyzer.Database.DatabaseAccess;
import com.example.asus.strokeanalyzer.Database.StrokeAnalyzerDatabase;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by S. Wasilkowski on 2017-12-12.
 */

public class NihssService {
    private static StrokeAnalyzerDatabase db = DatabaseAccess.getInstance().database;

    public static List<NihssExamination> getNihssExaminationsForPatient(int patientId){
        List<NihssExamination> examinations = new ArrayList<>();

        List<com.example.asus.strokeanalyzer.Entities.NihssExamination> entities;
        entities = db.nihssDao().SelectByPatientId(patientId);

        for (com.example.asus.strokeanalyzer.Entities.NihssExamination entity:
             entities) {
            examinations.add(EntityToModel(entity));
        }

        return examinations;
    }

    public static NihssExamination getLatestNihssExaminationForPatient(int patientId) {
        com.example.asus.strokeanalyzer.Entities.NihssExamination entity;
        entity = db.nihssDao().SelectLatestByPatientId(patientId);
        return EntityToModel(entity);
    }

    public static long addNihssExaminationForPatient(NihssExamination examination, int patientId){
        return db.nihssDao().insert(ModelToEntity(examination, patientId));
    }

    private static com.example.asus.strokeanalyzer.Entities.NihssExamination
        ModelToEntity(NihssExamination model, int patientId) throws IndexOutOfBoundsException {

        com.example.asus.strokeanalyzer.Entities.NihssExamination entity;
        entity = new com.example.asus.strokeanalyzer.Entities.NihssExamination();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.Date);
        entity.addedOn = calendar.getTimeInMillis();

        entity.PatientId = patientId;

        for (Answer answer:
             model.Answers) {
            NumericAnswer numericAnswer = answer instanceof NumericAnswer ? ((NumericAnswer) answer) : null;
            switch (numericAnswer.GetQuestionID()){
                case 101:
                    entity.Question1 = numericAnswer.Value;
                    break;
                case 102:
                    entity.Question2 = numericAnswer.Value;
                    break;
                case 103:
                    entity.Question3 = numericAnswer.Value;
                    break;
                case 104:
                    entity.Question4 = numericAnswer.Value;
                    break;
                case 105:
                    entity.Question5 = numericAnswer.Value;
                    break;
                case 106:
                    entity.Question6 = numericAnswer.Value;
                    break;
                case 107:
                    entity.Question7 = numericAnswer.Value;
                    break;
                case 108:
                    entity.Question8 = numericAnswer.Value;
                    break;
                case 109:
                    entity.Question9 = numericAnswer.Value;
                    break;
                case 110:
                    entity.Question10 = numericAnswer.Value;
                    break;
                case 111:
                    entity.Question11 = numericAnswer.Value;
                    break;
                case 112:
                    entity.Question12 = numericAnswer.Value;
                    break;
                case 113:
                    entity.Question13 = numericAnswer.Value;
                    break;
                case 114:
                    entity.Question14 = numericAnswer.Value;
                    break;
                case 115:
                    entity.Question15 = numericAnswer.Value;
                    break;
                default:
                    throw new IndexOutOfBoundsException();
            }
        }

        return entity;
    }

    private static NihssExamination EntityToModel(com.example.asus.strokeanalyzer.Entities.NihssExamination entity){
        NihssExamination model = new NihssExamination();

        model.Date = new Date(entity.addedOn);
        model.Answers = new ArrayList<>();

        NumericAnswer answer;
        answer = new NumericAnswer(0);
        answer.Value = entity.Question1;
        model.Answers.add(answer);
        answer = new NumericAnswer(1);
        answer.Value = entity.Question2;
        model.Answers.add(answer);
        answer = new NumericAnswer(2);
        answer.Value = entity.Question3;
        model.Answers.add(answer);
        answer = new NumericAnswer(3);
        answer.Value = entity.Question4;
        model.Answers.add(answer);
        answer = new NumericAnswer(4);
        answer.Value = entity.Question5;
        model.Answers.add(answer);
        answer = new NumericAnswer(5);
        answer.Value = entity.Question6;
        model.Answers.add(answer);
        answer = new NumericAnswer(6);
        answer.Value = entity.Question7;
        model.Answers.add(answer);
        answer = new NumericAnswer(7);
        answer.Value = entity.Question8;
        model.Answers.add(answer);
        answer = new NumericAnswer(8);
        answer.Value = entity.Question9;
        model.Answers.add(answer);
        answer = new NumericAnswer(9);
        answer.Value = entity.Question10;
        model.Answers.add(answer);
        answer = new NumericAnswer(10);
        answer.Value = entity.Question11;
        model.Answers.add(answer);

        return model;
    }
}
