package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
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
 * Klasa dokonująca analizy możliwości zastosowania leczenia trombolitycznego u pacjenta.
 *
 * @author Marta Marciszewicz & Stanisław Wasilkowski
 */

public final class TreatmentAnalyzer {

    //key - question ID; value - expected answer for question with given ID
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private TreatmentAnalyzer() {
    }

    /**
     * Metoda dokonująca analizy możliwości dopuszczenia pacjenta do leczenia trombolitycznego na podstawie
     * odpowiedzi udzielonych przez użytkownika w formularzach apliakcji.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return wynik przeprowadzonej analizy; zawiera zarówno decyzję dotyczącą
     *          zastosowania leczenia trombolitycznego, jak również listę odpowiedzi, które wpłynęły
     *          na ostateczną decyję w przypadku gdy jest ona negatywna; wynik może
     *          przyjąć wartość null jeżeli nie wszystkie wymagane odpowiedzi zostały udzielone przez
     *          użytkownika
     */
    public static TreatmentResult MakeTreatmentDecision(Patient p) throws WrongQuestionsSetException {
        if (correctAnswers == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.ThrombolyticTreatment))
            return null;

        TreatmentResult result = new TreatmentResult();

        int nihss = p.getNihss();
        if (nihss > 25) {
            result.Decision = false;
            result.badAnswers.add(new NumericAnswer(500,nihss));
        }

        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.ThrombolyticTreatment);

        try
        {
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
                        throw new WrongQuestionsSetException();
                    }

                }
            }
        }
        catch (NoAnswerException e)
        {
            return null;
        }


        return result;
    }

    /**
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza.
     */
    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer200 = new ExpectedNumericAnswer(200);
        answer200.Ranges.add(new RangeClassifier(19, 150));
        correctAnswers.put(200, answer200);

        ExpectedNumericAnswer answer205 = new ExpectedNumericAnswer(205);
        answer205.Ranges.add(new RangeClassifier(0, 270));
        correctAnswers.put(205, answer205);

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(50, 400));
        correctAnswers.put(206, answer206);

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer210.Ranges.add(new RangeClassifier(0, 1));
        correctAnswers.put(210, answer210);

        ExpectedNumericAnswer answer321 = new ExpectedNumericAnswer(321);
        answer321.Ranges.add(new RangeClassifier(100000, Double.MAX_VALUE));
        correctAnswers.put(321, answer321);

        correctAnswers.put(203, new ExpectedTrueFalseAnswer(203, false));
        correctAnswers.put(207, new ExpectedTrueFalseAnswer(207, false));
        correctAnswers.put(208, new ExpectedTrueFalseAnswer(208, false));
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
        correctAnswers.put(322, new ExpectedTrueFalseAnswer(322, true));
        correctAnswers.put(323, new ExpectedTrueFalseAnswer(323, true));
        correctAnswers.put(324, new ExpectedTrueFalseAnswer(324, true));
    }
}
