package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedNumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class NihssAnalyzer {

    public static int CountNihssSum(NihssExamination examination)
    {
        int pointsSum=0;

        for (Answer answer:
             examination.Answers) {
            NumericAnswer numericAnswer = answer instanceof NumericAnswer? ((NumericAnswer)answer) : null;
            if(numericAnswer != null && numericAnswer.Value>=0)
                pointsSum += numericAnswer.Value;
        }

        return pointsSum;
    }
}
