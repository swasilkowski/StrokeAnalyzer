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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasa dokonująca analizy prawdopodobieństwa zgonu pacjenta w przeciągu 30 dni
 * oraz 1 roku od wystąpienia udaru w skali iScore.
 *
 * @author Stanisław Wasilkowski
 */

public final class iScoreAnalyzer {

    /**
     * Słownik przechowujący oczekiwane odpowiedzi na pytania formularza skali iScore odnośnie prognozy 30-dniowej.
     * Klucz: id pytania formularza.
     * Wartość: Obiekt klasy {@link ExpectedAnswer} przechowujący poprawną bądź możliwą odpowiedź na dane pytanie.
     */
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor30Days;
    /**
     * Słownik przechowujący oczekiwane odpowiedzi na pytania formularza skali iScore odnośnie prognozy 1-rocznej.
     * Klucz: id pytania formularza.
     * Wartość: Obiekt klasy {@link ExpectedAnswer} przechowujący poprawną bądź możliwą odpowiedź na dane pytanie.
     */
    private static Dictionary<Integer, ExpectedAnswer> correctAnswersFor1Year;

    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private iScoreAnalyzer() {
    }

    /**
     * Metoda dokonująca wyliczenia wyniku w skali iScore dla pacjenta p.
     *
     * @param patient obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return wynik przeprowadzanej analizy; zawiera liczbę punktów skali iScore dla przewidywań
     *          dotyczących 1 roku i 30 dni od wystąpienia udaru mózgu, procent określający prawdopodobieństwo
     *          zgonu pacjenta w przeciągu 1 roku, procent określający prawdopodobieństwo
     *          zgonu pacjenta w przeciągu 30 dni od zajścia udaru mózgu oraz rekomendacje dotyczącą
     *          zastosowania leczenia trombolitycznego wobec pacjenta; wynik może
     *          przyjąć wartość null jeżeli nie wszystkie wymagane odpowiedzi zostały udzielone przez
     *          użytkownika
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
        result.PrognosisFor30DaysDescription = getDescriptionFor30Days(result.ScoreFor30Days, result.PrognosisFor30Days);
        result.PrognosisFor1YearDescription = getDescriptionFor1Year(result.ScoreFor1Year, result.PrognosisFor1Year);
        result.ThrombolyticRecommendation = getThrombolyticRecommendation(result.ScoreFor30Days);
        return result;
    }

    /**
     * Metoda wyznaczająca sumę punktów w skali iScore dla prawdopodobieństwa zgonu pacjenta
     * w przeciągu 30 dni od wystąpienia udaru mózgu.
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @return suma punktów w skali iScore dla przewidywań dotyczących 30 dni od wystąpienia udaru
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
     * Metoda wyznaczająca sumę punktów w skali iScore dla prawdopodobieństwa zgonu pacjenta
     * w przeciągu 1 roku od wystąpienia udaru mózgu.
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @return suma punktów w skali iScore dla przewidywań dotyczących 1 roku od wystąpienia udaru
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
     * od wystąpienia udaru. Wartość wyznaczana jest na podstawie zakresu do którego przynależy suma punktów points.
     *
     * @param points suma punktów w skali iScore dla 30-dniowej prognozy
     * @return wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 30 dni
     *          od wystąpienia udaru
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
     * Metoda zwracająca opis wyniku skali iScore dla przewidywań 30-dniowych.
     *
     * @param points suma punktów w skali iScore dla 30-dniowej prognozy
     * @param procent wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 30 dni
     *          od wystąpienia udaru
     * @return opis wyniku zawierający przedział punktowy oraz wartość procentową
     */
    private static String getDescriptionFor30Days(int points, double procent) {
        String text;
        if (points < 59) {
            text= "0-58: ";
        }
        else if (points >= 59 && points <= 70) {
            text ="59-70: ";
        }
        else if (points >= 71 && points <= 80) {
            text= "71-80: ";
        }
        else if (points >= 81 && points <= 90) {
            text= "81-90: ";
        }
        else if (points >= 91 && points <= 100) {
            text= "91-100: ";
        }
        else if (points >= 101 && points <= 110) {
            text= "101-110: ";
        }
        else if (points >= 111 && points <= 120) {
            text= "111-120: ";
        }
        else if (points >= 121 && points <= 130) {
            text= "121-130: ";
        }
        else if (points >= 131 && points <= 140) {
            text= "131-140: ";
        }
        else if (points >= 141 && points <= 150) {
            text= "141-150: ";
        }
        else if (points >= 151 && points <= 160) {
            text= "151-160: ";
        }
        else if (points >= 161 && points <= 170) {
            text= "161-170: ";
        }
        else if (points >= 171 && points <= 180) {
            text= "171-180: ";
        }
        else if (points >= 181 && points <= 190) {
            text= "181-190: ";
        }
        else if (points >= 191 && points <= 200) {
            text= "191-200: ";
        }
        else if (points >= 201 && points <= 210) {
            text= "201-210: ";
        }
        else if (points >= 211 && points <= 220) {
            text= "211-220: ";
        }
        else if (points >= 221 && points <= 230) {
            text= "221-230: ";
        }
        else if (points >= 231 && points <= 240) {
            text= "231-240: ";
        }
        else if (points >= 241 && points <= 250) {
            text= "241-250: ";
        }
        else if (points >= 251 && points <= 260) {
            text= "251-260: ";
        }
        else if (points >= 261 && points <= 270) {
            text= "261-270: ";
        }
        else if (points >= 271 && points <= 280) {
            text= "271-280: ";
        }
        else if (points >= 281 && points <= 285) {
            text= "281-285: ";
        }
        else
            text=">285: ";

        return text+procent+"%";
    }


    /**
     * Metoda wyznaczająca procentową wartość prawdopodobieństwa zgodnu pacjenta w przeciągu 1 roku
     * od wystąpienia udaru. Wartość wyznaczana jest na podstawie zakresu do którego przynależy suma punktów points.
     *
     * @param points suma punktów w skali iScore dla 1-rocznej prognozy
     * @return wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 1 roku
     *          od wystąpienia udaru
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
     * Metoda zwracająca opis wyniku skali iScore dla przewidywań 1-rocznych.
     *
     * @param points suma punktów w skali iScore dla 1-rocznej prognozy
     * @param procent wartość procentowa prawdopodobieństwa zgodnu pacjenta w przeciągu 1 roku
     *          od wystąpienia udaru
     * @return opis wyniku zawierający przedział punktowy oraz wartość procentową
     */
    private static String getDescriptionFor1Year(int points, double procent) {
        String text;
        if (points < 59) {
            text= "0-58: ";
        }
        else if (points >= 59 && points <= 70) {
            text= "59-70: ";
        }
        else if (points >= 71 && points <= 80) {
            text= "71-80: ";
        }
        else if (points >= 81 && points <= 90) {
            text= "81-90: ";
        }
        else if (points >= 91 && points <= 100) {
            text= "91-100: ";
        }
        else if (points >= 101 && points <= 110) {
            text= "101-110: ";
        }
        else if (points >= 111 && points <= 120) {
            text= "111-120: ";
        }
        else if (points >= 121 && points <= 130) {
            text= "121-130: ";
        }
        else if (points >= 131 && points <= 140) {
            text= "131-140: ";
        }
        else if (points >= 141 && points <= 150) {
            text= "141-150: ";
        }
        else if (points >= 151 && points <= 160) {
            text= "151-160: ";
        }
        else if (points >= 161 && points <= 170) {
            text= "161-170: ";
        }
        else if (points >= 171 && points <= 180) {
            text= "171-180: ";
        }
        else if (points >= 181 && points <= 190) {
            text= "181-190: ";
        }
        else if (points >= 191 && points <= 200) {
            text= "191-200: ";
        }
        else if (points >= 201 && points <= 210) {
            text= "201-210: ";
        }
        else if (points >= 211 && points <= 220) {
            text= "211-220: ";
        }
        else if (points >= 221 && points <= 230) {
            text= "221-230: ";
        }
        else if (points >= 231 && points <= 240) {
            text= "231-240: ";
        }
        else
            text= ">240: ";
        return text+procent+"%";
    }

    /**
     * Metoda wyznaczająca wskazania dla leczenia trombolitycznego na podstawie punktacji skali iScore.
     *
     * @param points suma punktów w skali iScore dla przewidywań 30-dniowych
     * @return tekst zawierający zalecenia dotyczące leczenia
     */
    private static String getThrombolyticRecommendation(int points)
    {
        if(points>=0 && points<=130)
            return "Zalecane";
        else if(points>130 && points<=200)
            return "Zachowaj ostrożność przy leczeniu";
        else if (points>200)
            return "NIE zalecane";
        else
            return "Brak wyniku";
    }


    /**
     * Metoda pomocniczna wyznaczająca sumę punktów w skali iScore dla podanego zbioru poprawnych odpowiedzi
     * (bez uwzględnienia punktów przydzielanych przez skalę NIHSS).
     *
     * @param p obiekt klasy Patient, dla którego dokonywane jest zliczenie sumy punktów
     * @param correctAnswers słownik zawierający poprawne odpowiedzi, z którymi porównywane są odpowiedzi
     *                       udzielone przez użytkownika
     * @return suma punktów w skali iScore dla podanego pacjenta i zbioru poprawnych odpowiedzi
     * @throws WrongQuestionsSetException zestaw odpowiedzi udzielonych przez użytkownika jest niezgodny
     *                                      z zestawem pytań dla tej skali (być może użytkownik nie udzielił
     *                                      odpowiedzi na jedno z wymaganych pytań)
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
     * Metoda inicjalizująca słownik zawierający oczekiwane odpowiedzi dla formularza.
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
