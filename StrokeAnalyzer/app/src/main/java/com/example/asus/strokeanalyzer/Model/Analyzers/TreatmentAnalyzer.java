package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
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

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class TreatmentAnalyzer {

    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    private TreatmentAnalyzer() {}

    public static TreatmentResult MakeTreatmentDecision(Patient p)
    {
        TreatmentResult result = new TreatmentResult();
        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.ThrombolyticTreatment);

        //check if user's answer was correct
        for(int i=0;i<questionIDs.size();i++)
        {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

            if(userAnswer!=null && expectedAnswer!=null)
            {
                if(userAnswer instanceof TextAnswer && expectedAnswer instanceof ExpectedTextAnswer)
                {
                    if(!((TextAnswer) userAnswer).Value.equals(((ExpectedTextAnswer) expectedAnswer).CorrectValue))
                    {
                        result.Decision = false;
                        result.badAnswers.add(userAnswer);
                    }
                }
                else if(userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer)
                {
                    if(!((ExpectedNumericAnswer) expectedAnswer).CheckCorrectness(((NumericAnswer) userAnswer).Value))
                    {
                            result.Decision = false;
                            result.badAnswers.add(userAnswer);
                    }
                }
                else if(userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer)
                {
                    if(((TrueFalseAnswer) userAnswer).Value != ((ExpectedTrueFalseAnswer) expectedAnswer).CorrectValue)
                    {
                        result.Decision = false;
                        result.badAnswers.add(userAnswer);
                    }
                }
                else
                {
                    //throw new WrongQuestionsSetException();
                }

            }
        }

        p.TreatmentDecision = result.Decision;

        return result;
    }
}
