package com.example.asus.strokeanalyzer.Model.Analyzers;

import android.graphics.Bitmap;
import android.media.Image;

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
import com.example.asus.strokeanalyzer.Model.Patient;

import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

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


    private StrokeBricksAnalyzer() {}

    public static List<Region> AnalyzeRegionsAffection(Patient p)
    {
        List<Region> affectedRegions = new ArrayList<>();

        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.StrokeBricks);

        //check if user's answer was the one expected for marking the region
        for(int i=0;i<questionIDs.size();i++)
        {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

            if (userAnswer != null && expectedAnswer != null)
            {
                if (userAnswer instanceof TextAnswer && expectedAnswer instanceof ExpectedTextAnswer) {
                    if (((TextAnswer) userAnswer).Value.equals(((ExpectedTextAnswer) expectedAnswer).CorrectValue)) {
                        for (Region r : regionsAffection.get(i)) {
                            if (!affectedRegions.contains(r))
                                affectedRegions.add(r);
                        }
                    }
                } else if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
                    if(((ExpectedNumericAnswer) expectedAnswer).CheckCorrecteness(((NumericAnswer) userAnswer).Value)) {
                            for (Region r : regionsAffection.get(i)) {
                                if (!affectedRegions.contains(r))
                                    affectedRegions.add(r);
                            }
                        }

                } else if (userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer) {
                    if (((TrueFalseAnswer) userAnswer).Value == ((ExpectedTrueFalseAnswer) expectedAnswer).CorrectValue) {
                        for (Region r : regionsAffection.get(i)) {
                            if (!affectedRegions.contains(r))
                                affectedRegions.add(r);
                        }
                    }
                } else {
                    //throw new WrongQuestionsSetException();
                }

            }
        }

        for(Region r: Collections.list(p.AffectedRegionsSB.keys()))
        {
            p.AffectedRegionsSB.put(r,false);
        }
        for(Region r: affectedRegions)
        {
            p.AffectedRegionsSB.put(r, true);
        }

        return affectedRegions;
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
}
