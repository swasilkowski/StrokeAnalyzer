package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;

/**
 * Klasa dokonująca analizy stanu pacjenta w trakcie udaru przy pomocy skali NIHSS.
 *
 * @author Stanisław Wasilkowski
 */

public final class NihssAnalyzer {

    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private NihssAnalyzer() {
    }

    /**
     * Metoda wyliczająca sumę punktów dla badania examination w skali NIHSS.
     *
     * @param examination badanie, dla którego wyliczana jest suma punktów
     * @return suma punktów w skali NIHSS
     */
    public static int CountNihssSum(NihssExamination examination)
    {
        int pointsSum=0;

        for (Answer answer:
             examination.Answers) {
            if (answer.GetQuestionID() > 115) {
                continue;
            }
            NumericAnswer numericAnswer = answer instanceof NumericAnswer? ((NumericAnswer)answer) : null;
            if(numericAnswer != null && numericAnswer.Value>=0)
                pointsSum += numericAnswer.Value;
        }

        return pointsSum;
    }
}
