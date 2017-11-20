package com.example.asus.strokeanalyzer.Model.Analyzers;

import android.media.Image;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class StrokeBricksAnalyzer {

    //key - question id, value - list of regions affected if the answer for a question was correc
    //regionsAffection - dependency between question and regions affected by stroke
    private Dictionary<Integer, List<Region>> regionsAffection;
    //key - region, value - description of this part of a brain that is characeterising it
    //regionsDescription - descriptions of regions
    private Dictionary<Region,String> regionsDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;


    private StrokeBricksAnalyzer() {}

    public static List<Region> AnalyzeRegionsAffection(Patient p)
    {
        List<Region> affectedRegions = new ArrayList<>();
        return affectedRegions;
    }
    public static String CreateStrokeRangeDescription(List<Region> regions) { return "";}
    public static Image[] GetStrokeImage(List<Region> regions) { return new Image[4];}
}
