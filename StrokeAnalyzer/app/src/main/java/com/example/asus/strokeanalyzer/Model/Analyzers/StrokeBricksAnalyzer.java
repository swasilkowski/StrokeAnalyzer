package com.example.asus.strokeanalyzer.Model.Analyzers;

import android.graphics.Bitmap;

import com.example.asus.strokeanalyzer.Model.CTPictures;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedNumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedTextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedTrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Created by Asus on 20.11.2017.
 */

public final class StrokeBricksAnalyzer {

    //key - question id, value - list of regions affected if the answer for a question was correc
    //regionsAffection - dependency between question and regions affected by stroke
    private static Dictionary<Integer, List<Region>> regionsAffection;
    //key - region, value - description of this part of a brain that is characeterising it
    //regionsDescription - descriptions of regions
    private static Dictionary<Region,String> regionsDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains answers that indicates regions connected with a particular question should be marked
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;


    private StrokeBricksAnalyzer() {
    }

    public static List<Region> AnalyzeRegionsAffection(Patient p) {
        if (correctAnswers == null || regionsDescription == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.StrokeBricks))
            return null;

        Set<Region> affectedRegions = new HashSet<>();

        NihssExamination nihssExamination = p.getLatestNihssExamination();

        for (Answer answer:
             nihssExamination.Answers) {
            if (!(answer instanceof NumericAnswer)) {
                //throw new WrongQuestionsSetException();
            }
            NumericAnswer numericAnswer = (NumericAnswer)answer;
            int answerId = numericAnswer.GetQuestionID();
            double answerValue = numericAnswer.Value;

            if (answerValue > 0) {
                switch (answerId) {
                    case 102: //1b
                        affectedRegions.add(Region.M1_L);
                        affectedRegions.add(Region.M4_L);
                        break;
                    case 103: //1c
                        affectedRegions.add(Region.M3_L);
                        affectedRegions.add(Region.M6_L);
                        break;
                    case 104: //2
                        affectedRegions.add(Region.M1_L);
                        affectedRegions.add(Region.M4_L);
                        affectedRegions.add(Region.M1_R);
                        affectedRegions.add(Region.M4_R);
                        break;
                    case 105: //3
                        affectedRegions.add(Region.P_L);
                        affectedRegions.add(Region.M2_L);
                        affectedRegions.add(Region.M3_L);
                        affectedRegions.add(Region.P_R);
                        affectedRegions.add(Region.M2_R);
                        affectedRegions.add(Region.M3_R);
                        break;
                    case 106: //4
                        affectedRegions.add(Region.M5_L);
                        affectedRegions.add(Region.CR_L);
                        affectedRegions.add(Region.BGIC_L);
                        affectedRegions.add(Region.M5_R);
                        affectedRegions.add(Region.CR_R);
                        affectedRegions.add(Region.BGIC_R);
                        break;
                    case 107: //5a
                        affectedRegions.add(Region.M5_R);
                        affectedRegions.add(Region.CR_R);
                        affectedRegions.add(Region.BGIC_R);
                        break;
                    case 108: //5b
                        affectedRegions.add(Region.M5_L);
                        affectedRegions.add(Region.CR_L);
                        affectedRegions.add(Region.BGIC_L);
                        break;
                    case 109: //6a
                        affectedRegions.add(Region.A2_R);
                        affectedRegions.add(Region.CR_R);
                        affectedRegions.add(Region.BGIC_R);
                        break;
                    case 110: //6b
                        affectedRegions.add(Region.A2_L);
                        affectedRegions.add(Region.CR_L);
                        affectedRegions.add(Region.BGIC_L);
                        break;
                    case 112: //8
                        affectedRegions.add(Region.M5_L);
                        affectedRegions.add(Region.CR_L);
                        affectedRegions.add(Region.BGIC_L);
                        affectedRegions.add(Region.T_L);
                        affectedRegions.add(Region.A2_L);
                        affectedRegions.add(Region.M5_R);
                        affectedRegions.add(Region.CR_R);
                        affectedRegions.add(Region.BGIC_R);
                        affectedRegions.add(Region.T_R);
                        affectedRegions.add(Region.A2_R);
                        break;
                    case 113: //9
                        affectedRegions.add(Region.M1_L);
                        affectedRegions.add(Region.M4_L);
                        affectedRegions.add(Region.M3_L);
                        affectedRegions.add(Region.M6_L);
                        break;
                    case 114: //10
                        affectedRegions.add(Region.A1_L);
                        affectedRegions.add(Region.M1_L);
                        affectedRegions.add(Region.M4_L);
                        affectedRegions.add(Region.BGIC_L);
                        affectedRegions.add(Region.A1_R);
                        affectedRegions.add(Region.M1_R);
                        affectedRegions.add(Region.M4_R);
                        affectedRegions.add(Region.BGIC_R);
                        break;
                    case 115: //11
                        affectedRegions.add(Region.P_L);
                        affectedRegions.add(Region.M2_L);
                        affectedRegions.add(Region.M3_L);
                        affectedRegions.add(Region.P_R);
                        affectedRegions.add(Region.M2_R);
                        affectedRegions.add(Region.M3_R);
                        affectedRegions.add(Region.A3_R);
                        affectedRegions.add(Region.M6_R);
                        break;
                }
            }
        }

        return new ArrayList<>(affectedRegions);
    }

    public static String CreateStrokeRangeDescription(List<Region> regions)
    {
        String description = "Obszar objęty udarem zawiera: \n";

        for(Region r :regions)
        {
            description+= regionsDescription.get(r);
            description+="\n";
        }

        return description;
    }

    public static Bitmap[] GetStrokeImage(List<Region> regions)
    {
        return CTPictures.GenerateOutputImage(regions);
    }

    private static void Initialize() {
        correctAnswers = new Hashtable<>();
        regionsDescription = new Hashtable<>();
        regionsDescription.put(Region.A1_L,"A1_L");
        regionsDescription.put(Region.A1_R, "A1_R");
        regionsDescription.put(Region.A2_L, "A2_L");
        regionsDescription.put(Region.A2_R,"A2_R");
        regionsDescription.put(Region.A3_L, "A3_L");
        regionsDescription.put(Region.A3_R, "A3_R");
        regionsDescription.put(Region.BGIC_L, "BGIC_L");
        regionsDescription.put(Region.BGIC_R,"BGIC_R");
        regionsDescription.put(Region.CR_L, "CR_L");
        regionsDescription.put(Region.CR_R, "CR_R");
        regionsDescription.put(Region.M1_L,"M1_L");
        regionsDescription.put(Region.M1_R, "M1_R");
        regionsDescription.put(Region.M2_L, "M2_L");
        regionsDescription.put(Region.M2_R, "M2_R");
        regionsDescription.put(Region.M3_L,"M3_L");
        regionsDescription.put(Region.M3_R, "M3_R");
        regionsDescription.put(Region.M4_L, "M4_L");
        regionsDescription.put(Region.M4_R,"M4_R");
        regionsDescription.put(Region.M5_L, "M5_L");
        regionsDescription.put(Region.M5_R, "M5_R");
        regionsDescription.put(Region.M6_L, "M6_L");
        regionsDescription.put(Region.M6_R,"M6_R");
        regionsDescription.put(Region.P_L, "P_L");
        regionsDescription.put(Region.P_R, "P_R");
        regionsDescription.put(Region.T_L,"T_L");
        regionsDescription.put(Region.T_R, "T_R");
    }
}
