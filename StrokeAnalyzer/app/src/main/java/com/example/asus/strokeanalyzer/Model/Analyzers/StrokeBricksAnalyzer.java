package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.Exceptions.WrongQuestionsSetException;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.ExpectedAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.Model.Patient;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Klasa wyznaczająca zbiór regionów mózgu dotkniętych udarem przy wykorzystaniu modelu Stroke Bricks
 * Zawiera słownik opisów regionów oraz słownik łączący ID pytania z listą regionów. Klasa StrokeBricksAnalyzer
 * posiada metodę pozwalającą na wyznaczenie listy regionów mózgu objętych udarem oraz metodę generującą opis
 * rozległości udaru.
 *
 * @author Stanisław Wasilkowski
 */

public final class StrokeBricksAnalyzer {

    //key - question id, value - list of regions affected if the answer for a question was correc
    //regionsAffection - dependency between question and regions affected by stroke
    private static Dictionary<Integer, List<Region>> regionsAffectionR;
    private static Dictionary<Integer, List<Region>> regionsAffectionL;
    //key - region, value - description of this part of a brain that is characeterising it
    //regionsDescription - descriptions of regions
    private static Dictionary<Region,String> regionsDescription;
    //key - question id, value - object containing correct answer for a question
    //correctAnswers - contains answers that indicates regions connected with a particular question should be marked
    private static Dictionary<Integer, ExpectedAnswer> correctAnswers;


    /**
     * Bezparametrowy konstruktor klasy
     * Oznaczony jako prywatny, by uniemożliwić jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private StrokeBricksAnalyzer() {
    }

    /**
     * Metoda dokonująca wyznaczenia regionów objętych udarem niedokrwiennym mózgu na podstawie
     * modelu Stroke Bricks dla pacjenta p.
     * Funkcja pobiera odpowiedzi udzielone przez użytkownika w najświeższym badaniu NIHSS i jeżeli
     * wartość punktowa odpowiedzi jest większa niż 0, regiony przypisane do pytania na które udzielona
     * została odpowiedź dołączane są do listy uszkodzonych w wyniku udaru regionów.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return
     * {@literal List<Region>} lista regionów potencjalnego występowania udaru wyznaczonych na podstawie modelu Stroke Bricks
     */
    public static List<Region> AnalyzeRegionsAffection(Patient p) {
        if (correctAnswers == null || regionsDescription == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.StrokeBricks))
            return null;

        Set<Region> affectedRegions = new HashSet<>();

        NihssExamination nihssExamination = p.getLatestNihssExamination();

        for (Answer answer: nihssExamination.Answers) {
            if (!(answer instanceof NumericAnswer)) {
                //throw new WrongQuestionsSetException();
            }
            NumericAnswer numericAnswer = (NumericAnswer)answer;
            int answerId = numericAnswer.GetQuestionID();
            double answerValue = numericAnswer.Value;

            if (answerValue > 0) {

                affectedRegions.addAll(getRegions(answerId, p));
            }
        }

        return new ArrayList<>(affectedRegions);
    }

    private static List<Region> getRegions(int questionID, Patient patient)
    {
        //only left hemisphere
        if(questionID==102 || questionID==103 || questionID==108 || questionID==110)
            return regionsAffectionL.get(questionID);

        //only right hemisphere
        if(questionID==107 || questionID==109 || questionID==105 || questionID==112 || questionID==114)
            return regionsAffectionR.get(questionID);

        //combination of both right and left hemispheres
        if(questionID==104 || questionID==106)
        {
            List<Region> tmpList = new ArrayList<Region>(regionsAffectionR.get(questionID));
            tmpList.addAll(regionsAffectionL.get(questionID));
            return tmpList;
        }

        //special analysis for question 9
        if(questionID==113)
        {
            if(((NumericAnswer)patient.PatientAnswers.get(213)).Value>0)
                return regionsAffectionR.get(questionID);
            else return regionsAffectionL.get(questionID);

        }
        //special analyzis for question 11
        if(questionID==115)
        {
            if(((NumericAnswer)patient.PatientAnswers.get(115)).Value==1)
            {
                if(((NumericAnswer)patient.PatientAnswers.get(105)).Value>0)
                {
                    return new ArrayList<>();
                }
                else
                    return regionsAffectionR.get(questionID);
            }
            else if(((NumericAnswer)patient.PatientAnswers.get(115)).Value==2)
            {
                List<Region> tmpList = new ArrayList<Region>(regionsAffectionR.get(questionID));
                if(((NumericAnswer)patient.PatientAnswers.get(213)).Value>0)
                    tmpList.addAll(regionsAffectionL.get(105));
                else tmpList.addAll(regionsAffectionR.get(105));
                return tmpList;
            }

        }

        return new ArrayList<>();
    }

    /**
     * Metoda generująca opsi rozległości udaru.
     * Funkcja pobiera opis regionu dla każdego z regionów dostarczonych w parametrze regions i następnie
     * dodaje go do wynikowego tekstu opisującego rozległość udaru
     *
     * @param regions lista regionów
     * @return (String) opis rozległości udaru (opis zawiera listę regionów możliwego występowania udaru
     *          niedokriwennego mózgu)
     */
    public static String CreateStrokeRangeDescription(List<Region> regions)
    {
        String description = "Obszar objęty udarem zawiera: \n";

        for(Region r :regions)
        {
            description+= regionsDescription.get(r);
            description+="\n";
        }

        return description;
    }

    /**
     * Metoda inicjalizująca słownik zawierający opisy poszczególnych regionów (nazwy)
     */
    private static void Initialize() {
        //correctAnswers = new Hashtable<>();
        regionsDescription = new Hashtable<>();
        regionsDescription.put(Region.A1_L,"A1_L");
        regionsDescription.put(Region.A1_R, "A1_R");
        regionsDescription.put(Region.A2_L, "A2_L");
        regionsDescription.put(Region.A2_R,"A2_R");
        regionsDescription.put(Region.A3_L, "A3_L");
        regionsDescription.put(Region.A3_R, "A3_R");
        regionsDescription.put(Region.BGIC_L, "BGIC_L");
        regionsDescription.put(Region.BGIC_R,"BGIC_R");
        regionsDescription.put(Region.CR_L, "CR_L");
        regionsDescription.put(Region.CR_R, "CR_R");
        regionsDescription.put(Region.M1_L,"M1_L");
        regionsDescription.put(Region.M1_R, "M1_R");
        regionsDescription.put(Region.M2_L, "M2_L");
        regionsDescription.put(Region.M2_R, "M2_R");
        regionsDescription.put(Region.M3_L,"M3_L");
        regionsDescription.put(Region.M3_R, "M3_R");
        regionsDescription.put(Region.M4_L, "M4_L");
        regionsDescription.put(Region.M4_R,"M4_R");
        regionsDescription.put(Region.M5_L, "M5_L");
        regionsDescription.put(Region.M5_R, "M5_R");
        regionsDescription.put(Region.M6_L, "M6_L");
        regionsDescription.put(Region.M6_R,"M6_R");
        regionsDescription.put(Region.P_L, "P_L");
        regionsDescription.put(Region.P_R, "P_R");
        regionsDescription.put(Region.T_L,"T_L");
        regionsDescription.put(Region.T_R, "T_R");


        regionsAffectionR = new Hashtable<>();
        regionsAffectionL = new Hashtable<>();
        List<Region> tmpRegionList = new ArrayList<>();
        //1b
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        regionsAffectionL.put(102,new ArrayList<Region>(tmpRegionList));
        //1c
        tmpRegionList.clear();
        tmpRegionList.add(Region.M3_L);
        tmpRegionList.add(Region.M6_L);
        regionsAffectionL.put(103, new ArrayList<Region>(tmpRegionList));
        //2
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        regionsAffectionL.put(104, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        regionsAffectionR.put(104, new ArrayList<Region>(tmpRegionList));
        //3
        tmpRegionList.clear();
        tmpRegionList.add(Region.P_L);
        tmpRegionList.add(Region.M2_L);
        tmpRegionList.add(Region.M3_L);
        regionsAffectionL.put(105, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.P_R);
        tmpRegionList.add(Region.M2_R);
        tmpRegionList.add(Region.M3_R);
        regionsAffectionR.put(105, new ArrayList<Region>(tmpRegionList));
        //4
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(106, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(106, new ArrayList<Region>(tmpRegionList));
        //5a
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(107, new ArrayList<Region>(tmpRegionList));
        //5b
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(108, new ArrayList<Region>(tmpRegionList));
        //6a
        tmpRegionList.clear();
        tmpRegionList.add(Region.A2_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(109, new ArrayList<Region>(tmpRegionList));
        //6b
        tmpRegionList.clear();
        tmpRegionList.add(Region.A2_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(110, new ArrayList<Region>(tmpRegionList));
        //8
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        tmpRegionList.add(Region.T_L);
        tmpRegionList.add(Region.A2_L);
        regionsAffectionL.put(112, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        tmpRegionList.add(Region.T_R);
        tmpRegionList.add(Region.A2_R);
        regionsAffectionR.put(112, new ArrayList<Region>(tmpRegionList));
        //9
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        tmpRegionList.add(Region.M3_L);
        tmpRegionList.add(Region.M6_L);
        regionsAffectionL.put(113, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        tmpRegionList.add(Region.M3_R);
        tmpRegionList.add(Region.M6_R);
        regionsAffectionR.put(113, new ArrayList<Region>(tmpRegionList));
        //10
        tmpRegionList.clear();
        tmpRegionList.add(Region.A1_L);
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(114, new ArrayList<Region>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.A1_R);
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(114, new ArrayList<Region>(tmpRegionList));
        //11
        tmpRegionList.clear();
        tmpRegionList.add(Region.A3_R);
        tmpRegionList.add(Region.M6_R);
        regionsAffectionR.put(115, new ArrayList<Region>(tmpRegionList));


    }
}
