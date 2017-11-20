package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;

import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public class TreatmentResult {

    //final decision whether treatment should be conducted
    public boolean Decision;
    //list of answers that were different than expected answers, and influenced final decision
    public List<Answer> badAnswers;
}
