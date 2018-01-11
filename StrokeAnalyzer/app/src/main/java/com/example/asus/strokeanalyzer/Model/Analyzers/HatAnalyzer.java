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
import com.example.asus.strokeanalyzer.Model.results.HatResult;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasa dokonująca analizy prawdopodobieństwa wystąpienia krwotoku śródczaszkowego w skali Hat
 * Zawiera słownik poprawnych odpowiedzi dla pytań o danym ID. Klasa HatAnalyzer posiada metodę
 * pozwalającą na wyznaczenie wyniku skali Hat oraz metody rzutujące wyznaczoną liczbę punktów na
 * prawdopodobieństwo wystąpienia krwotoku śródczaszkowego oraz śmiertelnego krwotoku śródczaszkowego.
 *
 * @author Stanisław Wasilkowski
 */

public final class HatAnalyzer {
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    /**
     * Bezparametrowy konstruktor klasy
     * Oznaczony jako prywatny, by uniemożliwić jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private HatAnalyzer() {
    }

    /**
     * Metoda dokonująca wyliczenia wyniku w skali Hat dla pacjenta p.
     * Funkcja pobiera sumę punktów najświeższego badania w skali NIHSS i w zależności od jej wartości
     * dodaje odpowiednią liczbę punktów. Następnie przechodzi po pytaniach dotyczących tego formularza
     * i dla każdego wyznacza odpowiednią liczbę punktów w zależności od odpowiedzi udzielonej przez użytkownika.
     * Następnie w zależności od liczby uzyskanych punktów zwracane są procentowe prawdopodobieństwa
     * wystąpienia krwotoku.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return (HatResult) wynik przeprowadzanej analizy; zawiera liczbę punktów skali Hat,
     *          procent określający prawdopodobieństwo wystąpienia krwotoku śródczaszkowego oraz
     *          procent określający prawdopodobieństwo śmiertelnego krwotoku śródczaszkowego.
     */
    public static HatResult AnalyzePrognosis(Patient p)
    {
        if (correctAnswers == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.Hat))
            return null;

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

    /**
     * Metoda wyznaczająca procentowe prawdopodobieństwo wystąpienia krwotoku śródczaszkowego
     * po zastosowaniu leczenia trombolitycznego
     * @param score liczba punktów skali Hat
     * @return (int) procent określający prawdopodobieństwo wystąpienia krwotoku śródczaszkowego
     */
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

    /**
     * Metoda wyznaczająca procentowe prawdopodobieństwo wystąpienia śmiertelnego krwotoku śródczaszkowego
     * po zastosowaniu leczenia trombolitycznego
     * @param score liczba punktów skali Hat
     * @return (int) procent określający prawdopodobieństwo wystąpienia śmiertelnego krwotoku śródczaszkowego
     */
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

    /**
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza
     */
    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(200, 0, 1));

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer210.Ranges.add(new RangeClassifier(1, 1, 1));
        answer210.Ranges.add(new RangeClassifier(2, 2, 2));
    }
}
