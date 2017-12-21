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
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.results.HatResult;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class HatAnalyzer {
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    private HatAnalyzer() {
    }

    public static HatResult AnalyzePrognosis(Patient p)
    {
        if (correctAnswers == null) {
            Initialize();
        }
        HatResult result = new HatResult();
        int pointsSum=0;
        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.Hat);

        int nihss = p.getNihssOnAdmission();
        if (nihss >= 15 && nihss <= 20) {
            pointsSum += 1;
        }
        if (nihss > 20) {
            pointsSum += 2;
        }

        //check if user's answer was correct and count points for given answers
        for(int i=0;i<questionIDs.size();i++) {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

            if (userAnswer != null && expectedAnswer != null) {
                if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
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

        result.Score = pointsSum;
        result.RiskOfFatalICH = getRiskOfFatalICH(pointsSum);
        result.RiskOfSymptomaticICH = getRiskOfSymptomaticICH(pointsSum);

        return result;
    }

    private static int getRiskOfSymptomaticICH(int score) {
        switch (score) {
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 44;
            case 5:
                return 44;
            default:
                return 100;
        }
    }

    private static int getRiskOfFatalICH(int score) {
        switch (score) {
            case 0:
                return 0;
            case 1:
                return 3;
            case 2:
                return 7;
            case 3:
                return 6;
            case 4:
                return 33;
            case 5:
                return 33;
            default:
                return 100;
        }
    }

    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(200, 0, 1));

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer210.Ranges.add(new RangeClassifier(1, 1, 1));
        answer210.Ranges.add(new RangeClassifier(2, 2, 2));
    }
}
