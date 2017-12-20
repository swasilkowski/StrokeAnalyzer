package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedNumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedTrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class TreatmentAnalyzer {

    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    public static TreatmentResult MakeTreatmentDecision(Patient p)
    {
        if (correctAnswers == null) {
            Initialize();
        }
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
                if(userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer)
                {
                    if(!((ExpectedNumericAnswer)expectedAnswer).CheckCorrectness((((NumericAnswer)userAnswer).Value)))
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

    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer200 = new ExpectedNumericAnswer(200);
        answer200.Ranges.add(new RangeClassifier(18, 100));
        correctAnswers.put(200, answer200);

        correctAnswers.put(203, new ExpectedTrueFalseAnswer(203, false));

        ExpectedNumericAnswer answer205 = new ExpectedNumericAnswer(205);
        answer205.Ranges.add(new RangeClassifier(0, 6));
        correctAnswers.put(205, answer205);

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(40, 400));
        correctAnswers.put(206, answer206);

        correctAnswers.put(207, new ExpectedTrueFalseAnswer(207, false));

        correctAnswers.put(208, new ExpectedTrueFalseAnswer(208, false));

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer206.Ranges.add(new RangeClassifier(0, 0));
        correctAnswers.put(210, answer210);

        correctAnswers.put(212, new ExpectedTrueFalseAnswer(212, false));

        correctAnswers.put(301, new ExpectedTrueFalseAnswer(301, false));

        correctAnswers.put(302, new ExpectedTrueFalseAnswer(302, false));

        correctAnswers.put(303, new ExpectedTrueFalseAnswer(303, false));

        correctAnswers.put(304, new ExpectedTrueFalseAnswer(304, false));

        correctAnswers.put(305, new ExpectedTrueFalseAnswer(305, false));

        correctAnswers.put(306, new ExpectedTrueFalseAnswer(306, false));

        correctAnswers.put(307, new ExpectedTrueFalseAnswer(307, false));

        correctAnswers.put(308, new ExpectedTrueFalseAnswer(308, false));

        correctAnswers.put(309, new ExpectedTrueFalseAnswer(309, false));

        correctAnswers.put(310, new ExpectedTrueFalseAnswer(310, false));

        correctAnswers.put(311, new ExpectedTrueFalseAnswer(311, false));

        correctAnswers.put(312, new ExpectedTrueFalseAnswer(312, false));

        correctAnswers.put(313, new ExpectedTrueFalseAnswer(313, false));

        correctAnswers.put(314, new ExpectedTrueFalseAnswer(314, false));

        correctAnswers.put(315, new ExpectedTrueFalseAnswer(315, false));

        correctAnswers.put(316, new ExpectedTrueFalseAnswer(316, false));

        correctAnswers.put(317, new ExpectedTrueFalseAnswer(317, false));

        correctAnswers.put(318, new ExpectedTrueFalseAnswer(318, false));

        correctAnswers.put(319, new ExpectedTrueFalseAnswer(319, false));

        correctAnswers.put(320, new ExpectedTrueFalseAnswer(320, false));
    }
}
