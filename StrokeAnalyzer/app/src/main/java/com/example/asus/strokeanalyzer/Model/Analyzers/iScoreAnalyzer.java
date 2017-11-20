package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.Dictionary;

/**
 * Created by Asus on 20.11.2017.
 */

public final class iScoreAnalyzer {

    //key - prognosis sum of points, value - description for a particular sum
    //prognosisDescription - contains descriptions for particular results of a scale analysis
    private static Dictionary<Integer, String> prognosisDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    private iScoreAnalyzer() {}

    public static int AnalyzePrognosis(Patient p) { return -1;}
    public static String GetPrognosisDescription(int points) { return "";}
}
