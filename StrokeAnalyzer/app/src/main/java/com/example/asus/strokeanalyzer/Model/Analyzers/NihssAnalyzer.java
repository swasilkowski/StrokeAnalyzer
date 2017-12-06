package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class NihssAnalyzer {

    private NihssAnalyzer() {}

    public static int CountNihssSum(Patient p)
    {
        int pointsSum=0;
        //choosing questions needed for sum calculation
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.NIHSS);

        //checking users answer and adding it to sum
        for(int i=0;i<questionIDs.size();i++)
        {
            Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
            if(userAnswer!=null && userAnswer instanceof NumericAnswer)
            {
                //??czy -1 to domyslan wartosc czy moze null czy co jak nie odpowiedziano jeszcze na pytanie???
                pointsSum+= ((NumericAnswer) userAnswer).Value;
            }
            else
            {
                //throw new WrongQuestionsSetException();
            }
        }

        //write patients nihss points sum
        p.NihssSum = pointsSum;

        return pointsSum;
    }
}
