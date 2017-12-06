package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
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

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class HatAnalyzer {
    //key - prognosis sum of points, value - description for a particular sum
    //prognosisDescription - contains descriptions for particular results of a scale analysis
    private static Dictionary<Integer, String> prognosisDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    private HatAnalyzer() {}

    public static int AnalyzePrognosis(Patient p)
    {
        int pointsSum=0;
        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.Hat);

        //check if user's answer was correct and count points for given answers
        for(int i=0;i<questionIDs.size();i++) {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

            if (userAnswer != null && expectedAnswer != null) {
                if (userAnswer instanceof TextAnswer && expectedAnswer instanceof ExpectedTextAnswer) {
                    if (((TextAnswer) userAnswer).Value.equals(((ExpectedTextAnswer) expectedAnswer).CorrectValue)) {
                        pointsSum += 1;
                    }
                } else if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
                    pointsSum += ((ExpectedNumericAnswer) expectedAnswer).CalculatePoints(((NumericAnswer) userAnswer).Value);
                } else if (userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer) {
                    if (((TrueFalseAnswer) userAnswer).Value == ((ExpectedTrueFalseAnswer) expectedAnswer).CorrectValue) {
                        pointsSum += 1;
                    }
                } else {
                    //throw new WrongQuestionsSetException();
                }

            }
        }
        return pointsSum;
    }
    public static String GetPrognosisDescription(int points)
    {
        return prognosisDescription.get(points);
    }
}
