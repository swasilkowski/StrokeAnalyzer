package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class TreatmentAnalyzer {

    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    private TreatmentAnalyzer() {}

    public static TreatmentResult MakeTreatmentDecision(Patient p) { return new TreatmentResult();}
}
