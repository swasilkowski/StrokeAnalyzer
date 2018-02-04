package com.example.asus.strokeanalyzer.Model;

import android.util.Log;
import com.example.asus.strokeanalyzer.Model.Analyzers.DragonAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.HatAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.StrokeBricksAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.TreatmentAnalyzer;
import com.example.asus.strokeanalyzer.Model.Analyzers.iScoreAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.results.DragonResult;
import com.example.asus.strokeanalyzer.Model.results.HatResult;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;
import com.example.asus.strokeanalyzer.Model.results.iScoreResult;
import com.example.asus.strokeanalyzer.Services.NihssService;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Klasa przechowująca informację o pacjencie.
 *
 * @author Marta Marciszewicz & Stanisław Wasilkowski
 */

public class Patient {

    /**
     * Unikatowe id pacjenta, wykorzystywane jako klucz główny w bazie danych aplikacji.
     */
    public int Id;
    /**
     * Imię pacjenta.
     */
    public String Name;
    /**
     * Nazwisko pacjenta.
     */
    public String Surname;
    /**
     * Indywidualny numer pacjenta wprowadzany przez użytkownika.
     */
    public long PatientNumber;
    /**
     * Mapa przechowująca odpowiedzi udzielone przez użytkownika.
     * Klucz: id pytania, którego dotyczy odpowiedź.
     * Wartość: Obiekt klasy {@link Answer} przechowujący odpowiedź wprowadzoną przez użytkownika.
     */
    public Map<Integer, Answer> PatientAnswers;

    /**
     * Metoda wyliczająca sumę punktów w skali NIHSS dla najświeższego badania pacjenta.
     *
     * @return suma punktów w skali NIHSS dla najświeższego badania
     */
    public int getNihss()
    {
        NihssExamination examination = getLatestNihssExamination();
        if(examination == null)
            return -1;
        return NihssAnalyzer.CountNihssSum(examination);
    }

    /**
     * Metoda wyliczająca sumę punktów w skalii NIHSS dla najstarszego (pierwszego) badania pacjenta.
     *
     * @return suma punktów w skali NIHSS dla najstarszego badania
     */
    public int getNihssOnAdmission() {
        NihssExamination examination = NihssService.getEarliestNihssExaminationForPatient(Id);
        if(examination == null)
            return -1;
        return NihssAnalyzer.CountNihssSum(examination);
    }

    /**
     * Metoda wyzaczająca regiony możliwego wystąpienia udaru mózgu przy wykorzystaniu modelu Stroke Bricks.
     *
     * @return lista regionów możliwego wystąpienia udaru mózgu
     */
    public List<Region> getStrokeBricksAffectedRegions() {
        return StrokeBricksAnalyzer.AnalyzeRegionsAffection(this);
    }

    /**
     * Metoda wyznaczająca decyzję dotyczącą zastosowania leczenia trombolitycznego oraz, w przypadku decyzji negatywnej,
     * listująca pytania, które wpłynęły na ostateczną decyzję.
     *
     * @return obiekt przechowujący wyniki analizy możliwości zastosowania
     *          leczenia trombolitycznego
     */
    public TreatmentResult getTreatmentDecision() {
        TreatmentResult result = null;

        try
        {
            result= TreatmentAnalyzer.MakeTreatmentDecision(this);
        }
        catch (WrongQuestionsSetException exception)
        {
            Log.e("TreatmentAnalyzer", "WrongQuestionsSetException:" + exception);
        }
        return result;

    }

    /**
     * Metoda analizująca ryzyko wystąpienia u pacjenta krwotoku śródmózgowego po zastosowaniu
     * leczenia trombolitycznego w skali HAT. Określa liczbę zdobytych przez pacjenta punktów w tej
     * skali oraz prawdopodobieństwo wystąpienia śmiertelengo i objawowego krwotoku śródczaszkowego.
     *
     * @return obiekt przechowujący wyniki analizy prawdopodobieństwa wystąpienia
     *          krwotoku śródczaskowego u pacjenta
     */
    public HatResult getHatPrognosis() {
        try
        {
            return HatAnalyzer.AnalyzePrognosis(this);
        }
        catch (WrongQuestionsSetException exception)
        {
            Log.e("HatAnalyzer", "WrongQuestionsSetException:" + exception);
        }
        return null;
    }

    /**
     * Metoda analizująca prawdopodobieństwo zgonu lub niepełnosprawności pacjenta w przeciągu 30 dni
     * oraz 1 roku od wystąpienia udaru w skali iScore. Określa liczbę zdobytych przez pacjenta punktów w tej
     * skali dla progonozy zarówno 30-dniowej jak i 1-rocznej oraz prawdopodobieństwo
     * zgonu pacjenta w przeciągu 30 dni oraz 1 roku od wystąpienia udaru.
     *
     * @return obiekt przechowujący wyniki analizy prawdopodobieństwa zgonu pacjenta
     */
    public iScoreResult getIscorePrognosis() {
        return iScoreAnalyzer.AnalyzePrognosis(this);
    }

    /**
     * Metoda analizująca rezultat zastosowania u pacjenta leczenia trombolitycznego
     * za pomocą rekombinowanego tkankowego aktywatora plazminogenu (rt-PA) w skali Dragon.
     * Określa liczbę zdobytych przez pacjenta punktów w tej skali oraz prawdopodobieństwo powodzenia
     * i niepowodzenia terapii trombolitycznej.
     *
     * @return obiekt przechowujący wyniki analizy prawdopodobieństwa powodzenia
     *          leczenia trombolitycznego u pacjenta
     */
    public DragonResult getDragonPrognosis() {
        try
        {
            return DragonAnalyzer.AnalyzePrognosis(this);
        }
        catch (WrongQuestionsSetException exception)
        {
            Log.e("DragonAnalyzer", "WrongQuestionsSetException:" + exception);
        }
        return  null;
    }

    /**
     * Konstruktor bezparametrowy klasy.
     */
    public Patient() {
        PatientAnswers = new Hashtable<>();
    }

    /**
     * Nadpisana metoda z klasy Object pozwalająca na przyrównanie elementów klasy Patient.
     * Elementy są sobie równe, jeżeli mają takie same Id.
     *
     * @param p obiekty klasy Patient, z którym porównujemy bierzący obiekt
     * @return true - jeżeli obiekty są sobie równe; false - jeżeli obiekty mają różne id
     */
    @Override
    public boolean equals(Object p) {
        return p instanceof Patient && ((Patient) p).Id == Id;
    }

    /**
     * Metoda zwracająca wszystkie badania pacjenta dla skali NIHSS.
     *
     * @return lista obiektów przechowujących odpowiedzi pacjenta na pytania
     * formularza skali NIHSS
     */
    public List<NihssExamination> getNihssHistory() {
        return NihssService.getNihssExaminationsForPatient(Id);
    }

    /**
     * Metoda pobierająca najświeższe badanie w skali NIHSS.
     *
     * @return obiekt przechowujący najstarsze badanie pacjenta w skali NIHSS
     */
    public NihssExamination getLatestNihssExamination() {
        return NihssService.getLatestNihssExaminationForPatient(Id);
    }

    /**
     * Metoda umożliwiająca dodanie pojedynczego badania w skali NIHSS do profilu pacjenta przechowywanego w bazie danych
     * (zapisanie badania w bazie danych).
     *
     * @param nihssExamination obiekt przechowujący pojedyncze badanie w skalii NIHSS, które ma zostać
     *                         zapisane w bazie danych
     */
    public void addNihssExamination(NihssExamination nihssExamination) {
        NihssService.addNihssExaminationForPatient(nihssExamination, Id);
    }
}
