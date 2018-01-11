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
import com.example.asus.strokeanalyzer.Model.results.DragonResult;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasa dokonująca analizy rezultatów leczenie tromolitycznego w skali Dragon
 * Zawiera słownik poprawnych odpowiedzi dla pytań o danym ID. Klasa DragonAnalyzer posiada metodę
 * pozwalającą na wyznaczenie wyniku skali Dragon oraz metody rzutujące wyznaczoną liczbę punktów na prawdopodobieństwo
 * powodzenia i niepowodzenia leczenia.
 *
 * @author Stanisław Wasilkowski
 */

public final class DragonAnalyzer {

    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains proper answers in this scale for particular question
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;

    /**
     * Bezparametrowy konstruktor klasy
     * Oznaczony jako prywatny, by uniemożliwić jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private DragonAnalyzer() {}

    /**
     * Metoda dokonująca wyliczenia wyniku w skali Dragon dla pacjenta p.
     * Funkcja pobiera sumę punktów najświeższego badania w skali NIHSS i w zależności od jej wartości
     * dodaje odpowiednią liczba punktów w skali Dragon. Następnie dla każdego pytania tego formularza
     * wyznacza odpowiednią liczbę punktów w zależności od odpowiedzi udzielonej przez użytkownika.
     * Za odpowiedż typu prawda/fałsz przyznawany jest 1 punkt w przypadku zgodności odpowiedzi.
     * Dla odpowiedzi numerycznej liczba punktów dopierana jest na podstaie zakresu, do którego należy liczba.
     * Następnie w zależności od liczby uzyskanych punktów określane jest prawdopodobieństwo dobrego
     * rezultatu leczenia oraz prawdopodobieństwo niepowodzenia leczenia trombolitycznego.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return (DragonResult) wynik przeprowadzanej analizy; zawiera liczbę punktów skali Dragon,
     *          procent powodzenia oraz procent niepowodzenia leczenia trombolitycznego
     */
    public static DragonResult AnalyzePrognosis(Patient p)
    {
        if (correctAnswers == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.Dragon))
            return null;

        DragonResult result = new DragonResult();
        int pointsSum=0;

        int nihss = p.getNihssOnAdmission();
        if (nihss >= 5 && nihss <= 9) {
            pointsSum += 1;
        }
        if (nihss >= 10 && nihss <= 15) {
            pointsSum += 2;
        }
        if (nihss > 15) {
            pointsSum += 3;
        }

        //getting list of questions for analysis
        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.Dragon);

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
        result.GoodOutcomePrognosis = getGoodOutcomePrognosis(pointsSum);
        result.MiserableOutcomePrognosis = getMiserableOutcomePrognosis(pointsSum);

        return result;
    }

    /**
     * Metoda wyznaczająca procentowe prawdopodobieństwo powodzenia leczenia trombolitycznego
     * @param score liczba punktów skali Dragon
     * @return (int) procent określający szanse powodzenie leczenia trombolitycznego
     */
    private static int getGoodOutcomePrognosis(int score) {
        switch (score) {
            case 0:
                return 96;
            case 1:
                return 96;
            case 2:
                return 93;
            case 3:
                return 78;
            case 4:
                return 63;
            case 5:
                return 50;
            case 6:
                return 22;
            case 7:
                return 10;
            default:
                return 0;
        }
    }

    /**
     * Metoda wyznaczająca procentowe prawdopodobieństwo niepowodzenia leczenia trombolitycznego
     * @param score liczba punktów skali Dragon
     * @return (int) procent określający niepowodzenie leczenia trombolitycznego
     */
    private static int getMiserableOutcomePrognosis(int score){
        switch (score) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 10;
            case 5:
                return 23;
            case 6:
                return 40;
            case 7:
                return 50;
            case 8:
                return 89;
            case 9:
                return 97;
            case 10:
                return 100;
            default:
                return 100;
        }
    }

    /**
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza
     */
    private static void Initialize() {
        correctAnswers = new Hashtable<>();

        ExpectedNumericAnswer answer200 = new ExpectedNumericAnswer(200);
        answer200.Ranges.add(new RangeClassifier(65,79,1));
        answer200.Ranges.add(new RangeClassifier(80,120,2));
        correctAnswers.put(200, answer200);

        correctAnswers.put(204, new ExpectedTrueFalseAnswer(204, true, 1));

        ExpectedNumericAnswer answer205 = new ExpectedNumericAnswer(205);
        answer205.Ranges.add(new RangeClassifier(1.5, Double.MAX_VALUE, 1));
        correctAnswers.put(205, answer205);

        ExpectedNumericAnswer answer206 = new ExpectedNumericAnswer(206);
        answer206.Ranges.add(new RangeClassifier(144, Double.MAX_VALUE, 1));
        correctAnswers.put(206, answer206);

        correctAnswers.put(209, new ExpectedTrueFalseAnswer(209, true, 1));

        ExpectedNumericAnswer answer210 = new ExpectedNumericAnswer(210);
        answer206.Ranges.add(new RangeClassifier(1, 2));
        correctAnswers.put(210, answer210);
    }
}
