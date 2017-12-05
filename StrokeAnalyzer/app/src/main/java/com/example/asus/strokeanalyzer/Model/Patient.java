package com.example.asus.strokeanalyzer.Model;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public class Patient {

    public int Id;
    public String Name;
    public String Surname;
    public int PatientNumber;
    //public String Description;

    public Dictionary<Integer, Answer> PatientAnswers;

    public int NihssSum;
    public List<NihssExamination> NihssHistory;

    public Dictionary<Region, Boolean> AffectedRegionsSB;

    public boolean TreatmentDecision;

    public int PrognosisHat;
    public int PrognosisiScore;
    public int PrognsisDragon;

    public void WriteToDatabse(){}
}
