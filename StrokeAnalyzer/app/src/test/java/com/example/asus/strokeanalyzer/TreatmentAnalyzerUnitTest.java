package com.example.asus.strokeanalyzer;

import android.util.Log;

import com.example.asus.strokeanalyzer.Model.Analyzers.TreatmentAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Form.Question.NumericQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.Question;
import com.example.asus.strokeanalyzer.Model.Patient;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by S. Wasilkowski on 1/10/2018.
 */

public class TreatmentAnalyzerUnitTest {
    @Test
    public void MakeTreatmentDecision_isTrue() {
        FormsStructure.InitializeQuestionsList();
        Patient patient = new Patient();
        for (Integer questionId:
                FormsStructure.QuestionsUsedForForm.get(Form.ThrombolyticTreatment)) {
            Question question = FormsStructure.Questions.get(questionId);
            if (question instanceof NumericQuestion) {
                patient.PatientAnswers.put(questionId, new NumericAnswer(questionId, 60));
            } else {
                patient.PatientAnswers.put(questionId, new TrueFalseAnswer(questionId, false));
            }
        }
        try
        {
            assertFalse(TreatmentAnalyzer.MakeTreatmentDecision(patient).Decision);
        }
        catch(WrongQuestionsSetException exp)
        {
            Log.e("TEST", "Exception has been thrown while testing method MakeTreatmentDecision");
        }

    }
}
