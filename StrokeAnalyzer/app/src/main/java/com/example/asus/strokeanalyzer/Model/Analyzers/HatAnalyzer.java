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
import com.example.asus.strokeanalyzer.Model.results.HatResult;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasa dokonująca analizy prawdopodobieństwa wystąpienia krwotoku śródczaszkowego po zastosowaniu
 * leczenia trombolitycznego w skali HAT.
 *
 * @author Stanisław Wasilkowski
 */

public final class HatAnalyzer {
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question
    /**
     * Słownik przechowujący oczekiwane odpowiedzi na pytania formularza skali HAT.
     * Klucz: id pytania formularza.
     * Wartość: Obiekt klasy {@link ExpectedAnswer} przechowujący poprawną bądź możliwą odpowiedź na dane pytanie.
     */
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private HatAnalyzer() {
    }

    /**
     * Metoda dokonująca wyliczenia wyniku w skali HAT dla pacjenta p.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return wynik przeprowadzanej analizy; zawiera liczbę punktów skali Hat,
     *          procent określający prawdopodobieństwo wystąpienia krwotoku śródczaszkowego oraz
     *          procent określający prawdopodobieństwo śmiertelnego krwotoku śródczaszkowego; wynik może
     *          przyjąć wartość null jeżeli nie wszystkie wymagane odpowiedzi zostały udzielone przez
     *          użytkownika
     */
    public static HatResult AnalyzePrognosis(Patient p) throws WrongQuestionsSetException {
        if (correctAnswers == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.Hat))
            return null;

        HatResult result = new HatResult();
        int pointsSum=0;
        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.Hat);

        int nihss = p.getNihss();
        if (nihss >= 15 && nihss < 20) {
            pointsSum += 1;
        }
        if (nihss >= 20) {
            pointsSum += 2;
        }

        try
        {
//check if user's answer was correct and count points for given answers
            for(int i=0;i<questionIDs.size();i++) {
                Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
                ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

                if (userAnswer != null && expectedAnswer != null) {
                    if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
                        pointsSum += ((ExpectedNumericAnswer) expectedAnswer).CalculatePoints(((NumericAnswer) userAnswer).Value);
                    } else if (userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer) {
                        //if this is question about diabetes and points for glucose where already given then ignore this
                        if(questionIDs.get(i)==203 && ((ExpectedNumericAnswer) correctAnswers.get(206)).CalculatePoints(((NumericAnswer) p.PatientAnswers.get(206)).Value)>0)
                            continue;
                        if (((TrueFalseAnswer) userAnswer).Value == ((ExpectedTrueFalseAnswer) expectedAnswer).CorrectValue) {
                            pointsSum += ((ExpectedTrueFalseAnswer) expectedAnswer).Score;
                        }
                    } else {
                        throw new WrongQuestionsSetException();
                    }

                }
            }
        }
        catch (NoAnswerException e)
        {
            return null;
        }



        result.Score = pointsSum;
        result.RiskOfFatalICH = getRiskOfFatalICH(pointsSum);
        result.RiskOfSymptomaticICH = getRiskOfSymptomaticICH(pointsSum);

        return result;
    }

    /**
     * Metoda wyznaczająca procentowe prawdopodobieństwo wystąpienia krwotoku śródczaszkowego
     * po zastosowaniu leczenia trombolitycznego.
     *
     * @param score liczba punktów skali HAT
     * @return procent określający prawdopodobieństwo wystąpienia krwotoku śródczaszkowego
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
     * po zastosowaniu leczenia trombolitycznego.
     *
     * @param score liczba punktów skali HAT
     * @return procent określający prawdopodobieństwo wystąpienia śmiertelnego krwotoku śródczaszkowego
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
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza.
     */
    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(201,Double.MAX_VALUE, 1));

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer210.Ranges.add(new RangeClassifier(1, 1, 1));
        answer210.Ranges.add(new RangeClassifier(2, 2, 2));

        ExpectedTrueFalseAnswer answer203 = new ExpectedTrueFalseAnswer(203,true,1);

        correctAnswers.put(206, answer206);
        correctAnswers.put(210, answer210);
        correctAnswers.put(203, answer203);
    }
}
