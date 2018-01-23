package com.example.asus.strokeanalyzer.Model.Analyzers;

import android.util.Log;

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
import com.example.asus.strokeanalyzer.Model.results.iScoreResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasa dokonująca analizy prawdopodobieństwa zgonu pacjenta w skali iScore
 * Zawiera słowniki poprawnych odpowiedzi dla pytań o danym ID z podziałem na analizę dotyczącą okresu
 * 1 roku o raz 30 dni od momentu wystąpienia udaru niedokrwiennego mózgu. Klasa iScoreAnalyzer posiada
 * metodę pozwalającą na wyznaczenie wyniku skali iScore oraz metody pomocnicze.
 *
 * @author Stanisław Wasilkowski
 */

public final class iScoreAnalyzer {

    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question in 30 days prediction
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor30Days;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains propwer answers in this scale for particular question in 1 year prediction
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor1Year;

    /**
     * Metoda dokonująca wyliczenia wyniku w skali iScore dla pacjenta p.
     * Zlicza ona osobno sumy punktów dla analizy dotyczącej 1 roku oraz 30 dni, a następnie przelicza to
     * na wartości procentowe prawdopodobieństwa zgonu pacjenta w przeciągu 1 roku oraz 30 dni od wystąpienia
     * uadru niedokriennego mózgu
     *
     * @param patient obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return (iScoreResult) wynik przeprowadzanej analizy; zawiera liczbę punktów skali iScore dla przewidywań
     *          dotyczących 1 roku i 30 dni od wystąpienia udaru mózgu, procent określający prawdopodobieństwo
     *          zgonu pacjenta w przeciągu 1 roku oraz procent określający prawdopodobieństwo
     *          zgonu pacjenta w przeciągu 30 dni od zajścia udaru mózgu.
     */
    public static iScoreResult AnalyzePrognosis(Patient patient) {

        if(!FormsStructure.patientReady(patient,Form.iScore) || patient.getLatestNihssExamination() == null)
            return null;

        if(correctAnswersFor30Days == null || correctAnswersFor1Year == null)
        {
            Initialize();
        }

        iScoreResult result = new iScoreResult();
        result.ScoreFor30Days = getPointsFor30Days(patient);
        result.ScoreFor1Year = getPointsFor1Year(patient);
        result.PrognosisFor30Days = getPredictionFor30Days(result.ScoreFor30Days);
        result.PrognosisFor1Year = getPredictionFor1Year(result.ScoreFor1Year);
        return result;
    }

    /**
     * Metoda wyznaczająca sumę punktów w skali iScore w zależności od podanego zbioru poprawnych odpowiedzi
     * dla prawdopodobieństwa zgonu pacjenta w przeciągu 30 dni od wystąpienia udaru mózgu
     * Metoda zlicza punkty na podstawie odpowiedzi udzielonych przez użytkowniak oraz dodaje punkty
     * przyznane w zależności od liczby punktów najświeższego badania pacjenta w skali NIHSS
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @return (int) suma punktów w skali iScore dla przewidywań dotyczących 30 dni od wystąpienia udaru
     */
    private static int getPointsFor30Days(Patient p)
    {
        int iScorePoints=0;
        try{
            iScorePoints = countPoints(p, correctAnswersFor30Days);
        }
        catch (WrongQuestionsSetException exception)
        {
            Log.e("iScoreAnalyzer", "WrongQuestionsSetException:" + exception);
        }


        int nihssScore = p.getNihss();
        if (nihssScore > 22) {
            iScorePoints += 105;
        }
        if (nihssScore <= 22 && nihssScore >= 14) {
            iScorePoints += 65;
        }
        if (nihssScore < 14 && nihssScore >= 9) {
            iScorePoints += 40;
        }
        return iScorePoints;
    }

    /**
     * Metoda wyznaczająca sumę punktów w skali iScore w zależności od podanego zbioru poprawnych odpowiedzi
     * dla prawdopodobieństwa zgonu pacjenta w przeciągu 1 roku od wystąpienia udaru mózgu
     * Metoda zlicza punkty na podstawie odpowiedzi udzielonych przez użytkowniak oraz dodaje punkty
     * przyznane w zależności od liczby punktów najświeższego badania pacjenta w skali NIHSS
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @return (int) suma punktów w skali iScore dla przewidywań dotyczących 1 roku od wystąpienia udaru
     */
    private static int getPointsFor1Year(Patient p)
    {
        int iScorePoints=0;
        try{
            iScorePoints = countPoints(p, correctAnswersFor1Year);
        }
        catch (WrongQuestionsSetException exception)
        {
            Log.e("iScoreAnalyzer", "WrongQuestionsSetException:" + exception);
        }


        int nihssScore = p.getNihss();
        if (nihssScore > 22) {
            iScorePoints += 70;
    }
        if (nihssScore <= 22 && nihssScore >= 14) {
            iScorePoints += 40;
        }
        if (nihssScore < 14 && nihssScore >= 9) {
            iScorePoints += 25;
        }
        return iScorePoints;
    }

    /**
     * Metoda wyznaczająca procentową wartość prawdopodobieństwa zgodnu pacjenta w przeciągu 30 dni
     * od wystąpienia udaru. Wartość wyznaczana jest na podstawie zakresu do którego przynależy suma punktó points
     *
     * @param points suma punktów w skali iScore
     * @return (double) wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 30 dni
     *          od wystąpienia udaru.
     */
    private static double getPredictionFor30Days(int points) {
        if (points < 59) {
            return 0;
        }
        if (points >= 59 && points <= 70) {
            return 0.43;
        }
        if (points >= 71 && points <= 80) {
            return 0.73;
        }
        if (points >= 81 && points <= 90) {
            return 0.89;
        }
        if (points >= 91 && points <= 100) {
            return 1.33;
        }
        if (points >= 101 && points <= 110) {
            return 1.87;
        }
        if (points >= 111 && points <= 120) {
            return 2.58;
        }
        if (points >= 121 && points <= 130) {
            return 3.73;
        }
        if (points >= 131 && points <= 140) {
            return 5.03;
        }
        if (points >= 141 && points <= 150) {
            return 7.39;
        }
        if (points >= 151 && points <= 160) {
            return 9.78;
        }
        if (points >= 161 && points <= 170) {
            return 13.1;
        }
        if (points >= 171 && points <= 180) {
            return 18.0;
        }
        if (points >= 181 && points <= 190) {
            return 24.2;
        }
        if (points >= 191 && points <= 200) {
            return 33.0;
        }
        if (points >= 201 && points <= 210) {
            return 39.2;
        }
        if (points >= 211 && points <= 220) {
            return 49.2;
        }
        if (points >= 221 && points <= 230) {
            return 57.6;
        }
        if (points >= 231 && points <= 240) {
            return 65.7;
        }
        if (points >= 241 && points <= 250) {
            return 80.0;
        }
        if (points >= 251 && points <= 260) {
            return 84.2;
        }
        if (points >= 261 && points <= 270) {
            return 90.0;
        }
        if (points >= 271 && points <= 280) {
            return 90.2;
        }
        if (points >= 281 && points <= 285) {
            return 90.5;
        }
        return 100;
    }

    /**
     * Metoda wyznaczająca procentową wartość prawdopodobieństwa zgodnu pacjenta w przeciągu 1 roku
     * od wystąpienia udaru. Wartość wyznaczana jest na podstawie zakresu do którego przynależy suma punktó points
     *
     * @param points suma punktów w skali iScore
     * @return (double) wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 1 roku
     *          od wystąpienia udaru.
     */
    private static double getPredictionFor1Year(int points) {
        if (points < 59) {
            return 0;
        }
        if (points >= 59 && points <= 70) {
            return 1.78;
        }
        if (points >= 71 && points <= 80) {
            return 2.93;
        }
        if (points >= 81 && points <= 90) {
            return 4.36;
        }
        if (points >= 91 && points <= 100) {
            return 6.56;
        }
        if (points >= 101 && points <= 110) {
            return 9.76;
        }
        if (points >= 111 && points <= 120) {
            return 14.4;
        }
        if (points >= 121 && points <= 130) {
            return 20.4;
        }
        if (points >= 131 && points <= 140) {
            return 29.0;
        }
        if (points >= 141 && points <= 150) {
            return 38.2;
        }
        if (points >= 151 && points <= 160) {
            return 48.9;
        }
        if (points >= 161 && points <= 170) {
            return 59.3;
        }
        if (points >= 171 && points <= 180) {
            return 74.8;
        }
        if (points >= 181 && points <= 190) {
            return 75.7;
        }
        if (points >= 191 && points <= 200) {
            return 87.3;
        }
        if (points >= 201 && points <= 210) {
            return 93.8;
        }
        if (points >= 211 && points <= 220) {
            return 95.3;
        }
        if (points >= 221 && points <= 230) {
            return 97.3;
        }
        if (points >= 231 && points <= 240) {
            return 97.7;
        }
        return 100;
    }

    /**
     * Metoda wyznaczająca sumę punktów w skali iScore w zależności od podanego zbioru poprawnych odpowiedzi,
     * która nie uwzględnia punktów przydzielanych przez skalę NIHSS
     * Metoda porównuje oczekiwane odpowiedzi z odpowiedziami udzielonymi przez użytkownika i na tej podstawie
     * dodaje punkty w skali iScore.
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @param correctAnswers słownik zawierający poprawne odpowiedzi, z którymi porównywane są odpowiedzi
     *                       udzielone przez użytkownika
     * @return (int) suma punktów w skali iScore dla podanego pacjenta i zbioru poprawnych odpowiedzi
     */
    private static int countPoints(Patient p, Dictionary<Integer, ExpectedAnswer> correctAnswers) throws WrongQuestionsSetException {
        //adding age to the points sum
        int pointsSum = (int)((NumericAnswer)(p.PatientAnswers.get(200))).Value;
        List<Integer> questionIDs = new ArrayList<>(FormsStructure.QuestionsUsedForForm.get(Form.iScore));
        questionIDs.remove(questionIDs.indexOf(200));

        try
        {
            //points from other questions
            for(int i=0;i<questionIDs.size();i++) {
                Answer userAnswer = p.PatientAnswers.get(questionIDs.get(i));
                ExpectedAnswer expectedAnswer = correctAnswers.get(questionIDs.get(i));

                if (userAnswer != null && expectedAnswer != null) {
                    if (userAnswer instanceof NumericAnswer && expectedAnswer instanceof ExpectedNumericAnswer) {
                        pointsSum += ((ExpectedNumericAnswer) expectedAnswer).CalculatePoints(((NumericAnswer) userAnswer).Value);
                    } else if (userAnswer instanceof TrueFalseAnswer && expectedAnswer instanceof ExpectedTrueFalseAnswer) {
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
            return 0;
        }


        return pointsSum;
    }

    /**
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza
     */
    private static void Initialize() {
        correctAnswersFor30Days = new Hashtable<>();
        correctAnswersFor1Year = new Hashtable<>();

        ExpectedNumericAnswer answer201a = new ExpectedNumericAnswer(201);
        answer201a.Ranges.add(new RangeClassifier(1,1, 10));
        correctAnswersFor30Days.put(201, answer201a);

        ExpectedNumericAnswer answer201b = new ExpectedNumericAnswer(201);
        answer201b.Ranges.add(new RangeClassifier(1,1, 5));
        correctAnswersFor1Year.put(201, answer201b);

        correctAnswersFor1Year.put(202,  new ExpectedTrueFalseAnswer(202, true, 5));
        correctAnswersFor30Days.put(202, new ExpectedTrueFalseAnswer(202,true,0));

        correctAnswersFor30Days.put(204, new ExpectedTrueFalseAnswer(204, true, 15));
        correctAnswersFor1Year.put(204, new ExpectedTrueFalseAnswer(204, true, 20));

        ExpectedNumericAnswer answer206a = new ExpectedNumericAnswer(206);
        answer206a.Ranges.add(new RangeClassifier(135, Double.MAX_VALUE, 15));
        correctAnswersFor30Days.put(206, answer206a);

        ExpectedNumericAnswer answer206b = new ExpectedNumericAnswer(206);
        answer206b.Ranges.add(new RangeClassifier(135, Double.MAX_VALUE, 10));
        correctAnswersFor1Year.put(206, answer206b);

        ExpectedNumericAnswer answer211a = new ExpectedNumericAnswer(211);
        answer211a.Ranges.add(new RangeClassifier(1,1,30));
        answer211a.Ranges.add(new RangeClassifier(2,2,35));
        correctAnswersFor30Days.put(211, answer211a);

        ExpectedNumericAnswer answer211b = new ExpectedNumericAnswer(211);
        answer211b.Ranges.add(new RangeClassifier(1,1,15));
        answer211b.Ranges.add(new RangeClassifier(2,2,20));
        correctAnswersFor1Year.put(211, answer211b);

        correctAnswersFor30Days.put(301, new ExpectedTrueFalseAnswer(301, true, 10));
        correctAnswersFor1Year.put(301, new ExpectedTrueFalseAnswer(301, true, 15));

        correctAnswersFor30Days.put(401, new ExpectedTrueFalseAnswer(401, true, 10));
        correctAnswersFor1Year.put(401, new ExpectedTrueFalseAnswer(401, true, 5));

        correctAnswersFor30Days.put(402, new ExpectedTrueFalseAnswer(402, true, 10));
        correctAnswersFor1Year.put(402, new ExpectedTrueFalseAnswer(402, true, 10));

        correctAnswersFor30Days.put(403, new ExpectedTrueFalseAnswer(403, true, 0));
        correctAnswersFor1Year.put(403, new ExpectedTrueFalseAnswer(403, true, 5));

        correctAnswersFor30Days.put(404, new ExpectedTrueFalseAnswer(404, true, 35));
        correctAnswersFor1Year.put(404, new ExpectedTrueFalseAnswer(404, true, 40));

    }
}
