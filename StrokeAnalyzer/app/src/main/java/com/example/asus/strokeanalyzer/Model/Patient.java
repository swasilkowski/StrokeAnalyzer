package com.example.asus.strokeanalyzer.Model;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public class Patient {

    private int id;
    public String Name;
    public String Surname;
    public int PatientNumber;
    public String Description;

    private Dictionary<Integer, Answer> patientAnswers;

    public int NihssSum;
    public List<NihssExamination> NihssHistory;

    private Dictionary<Region, Boolean> affectedRegionsSB;

    public boolean TreatmentDecision;

    private int prognosisHat;
    private int prognosisiScore;
    private  int prognsisDragon;
}
