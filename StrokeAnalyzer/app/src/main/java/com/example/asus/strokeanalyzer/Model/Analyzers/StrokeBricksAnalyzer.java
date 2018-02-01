package com.example.asus.strokeanalyzer.Model.Analyzers;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
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
 * oraz skali NIHSS.
 *
 * @author Marta Marciszewicz & Stanisław Wasilkowski
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
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private StrokeBricksAnalyzer() {
    }

    /**
     * Metoda dokonująca wyznaczenia regionów objętych udarem niedokrwiennym mózgu na podstawie
     * modelu Stroke Bricks dla pacjenta p. Funkcja pobiera odpowiedzi udzielone przez użytkownika
     * w najświeższym badaniu NIHSS i jeżeli wartość punktowa odpowiedzi jest większa niż 0,
     * regiony przypisane do pytania, na które udzielona została odpowiedź dołączane
     * są do listy uszkodzonych w wyniku udaru regionów.
     *
     * @param p obiekt klasy Patient, dla którego dokonywana jest analiza
     * @return lista regionów potencjalnego występowania udaru wyznaczonych na podstawie modelu Stroke Bricks;
     *          wynik może przyjąć wartość null jeżeli nie wszystkie wymagane odpowiedzi zostały udzielone przez
     *          użytkownika
     */
    public static List<Region> AnalyzeRegionsAffection(Patient p) {
        if (correctAnswers == null || regionsDescription == null) {
            Initialize();
        }

        if(!FormsStructure.patientReady(p,Form.StrokeBricks))
            return null;

        Set<Region> affectedRegions = new HashSet<>();

        NihssExamination nihssExamination = p.getLatestNihssExamination();
        if(nihssExamination==null)
            return null;

        for (Answer answer: nihssExamination.Answers) {

            NumericAnswer numericAnswer = answer instanceof NumericAnswer? ((NumericAnswer)answer) : null;

            if(numericAnswer != null && numericAnswer.Value>0)
            {
                int answerId = numericAnswer.GetQuestionID();
                affectedRegions.addAll(getRegions(answerId, p));
            }
        }

        return new ArrayList<>(affectedRegions);
    }

    /**
     * Metoda pobierająca listę regionów powiązaną z konkretnym pytaniem formularza skali NIHSS.
     *
     * @param questionID id pytania, dla którego pobierana jest lista regionów
     * @param patient obiekt klasy Patient, który analizowany jest w celu zwrócenia listy regionów
     * @return lista regionów potencjalnego występowania udaru powiązana z pytaniem
     */
    private static List<Region> getRegions(int questionID, Patient patient)
    {
        //only left hemisphere
        if(questionID==108 || questionID==110)
            return regionsAffectionL.get(questionID);

        //only right hemisphere
        if(questionID==107 || questionID==109)
            return regionsAffectionR.get(questionID);

        //depending on dominant hemisphere
        if(questionID==102 || questionID==103 || questionID==113)
        {
            ////right hemisphere dominant
            if(((NumericAnswer)patient.PatientAnswers.get(116)).Value>0)
                return regionsAffectionR.get(questionID);
            else return  regionsAffectionL.get(questionID);

        }

        //depending on symptoms side
        if(questionID==105 || questionID==106 || questionID==112 || questionID==114 )
        {
            //right side symptoms
            if(((NumericAnswer)patient.PatientAnswers.get(117)).Value>0)
                return regionsAffectionL.get(questionID);
            else return  regionsAffectionR.get(questionID);
        }

        //special analysis for question 2
        if(questionID==104)
        {
            //right side symptoms
            if(((NumericAnswer)patient.PatientAnswers.get(117)).Value>0)
                return regionsAffectionR.get(questionID);
            else return  regionsAffectionL.get(questionID);

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
            }

            List<Region> tmpList;
            ////right hemisphere dominant
            if(((NumericAnswer)patient.PatientAnswers.get(116)).Value>0)
                tmpList= regionsAffectionL.get(questionID);
            else tmpList=  regionsAffectionR.get(questionID);

            if(((NumericAnswer)patient.PatientAnswers.get(115)).Value==2)
            {
                tmpList.addAll(getRegions(105, patient));
            }
            return tmpList;

        }

        return new ArrayList<>();
    }

    /**
     * Metoda generująca opsi rozległości udaru.
     *
     * @param regions lista regionów
     * @return opis rozległości udaru (opis zawiera listę regionów możliwego występowania udaru
     *          niedokrwiennego mózgu)
     */
    public static String CreateStrokeRangeDescription(List<Region> regions)
    {
        String description = "Obszar objęty udarem zawiera: \n";

        StringBuilder sb = new StringBuilder(description);
        for(Region r :regions)
        {
            sb.append(regionsDescription.get(r)).append("\n");
        }

        return sb.toString();
    }

    /**
     * Metoda inicjalizująca słownik zawierający opisy (nazwy) poszczególnych regionów oraz słownik
     * wiążący pytania skali NIHSS z regionami modelu Stroke Bricks.
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
        regionsAffectionL.put(102,new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        regionsAffectionR.put(102,new ArrayList<>(tmpRegionList));
        //1c
        tmpRegionList.clear();
        tmpRegionList.add(Region.M3_L);
        tmpRegionList.add(Region.M6_L);
        regionsAffectionL.put(103, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M3_R);
        tmpRegionList.add(Region.M6_R);
        regionsAffectionR.put(103, new ArrayList<>(tmpRegionList));
        //2
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        regionsAffectionL.put(104, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        regionsAffectionR.put(104, new ArrayList<>(tmpRegionList));
        //3
        tmpRegionList.clear();
        tmpRegionList.add(Region.P_L);
        tmpRegionList.add(Region.M2_L);
        tmpRegionList.add(Region.M3_L);
        regionsAffectionL.put(105, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.P_R);
        tmpRegionList.add(Region.M2_R);
        tmpRegionList.add(Region.M3_R);
        regionsAffectionR.put(105, new ArrayList<>(tmpRegionList));
        //4
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(106, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(106, new ArrayList<>(tmpRegionList));
        //5a
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(107, new ArrayList<>(tmpRegionList));
        //5b
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(108, new ArrayList<>(tmpRegionList));
        //6a
        tmpRegionList.clear();
        tmpRegionList.add(Region.A2_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(109, new ArrayList<>(tmpRegionList));
        //6b
        tmpRegionList.clear();
        tmpRegionList.add(Region.A2_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(110, new ArrayList<>(tmpRegionList));
        //8
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_L);
        tmpRegionList.add(Region.CR_L);
        tmpRegionList.add(Region.BGIC_L);
        tmpRegionList.add(Region.T_L);
        tmpRegionList.add(Region.A2_L);
        regionsAffectionL.put(112, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M5_R);
        tmpRegionList.add(Region.CR_R);
        tmpRegionList.add(Region.BGIC_R);
        tmpRegionList.add(Region.T_R);
        tmpRegionList.add(Region.A2_R);
        regionsAffectionR.put(112, new ArrayList<>(tmpRegionList));
        //9
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        tmpRegionList.add(Region.M3_L);
        tmpRegionList.add(Region.M6_L);
        regionsAffectionL.put(113, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        tmpRegionList.add(Region.M3_R);
        tmpRegionList.add(Region.M6_R);
        regionsAffectionR.put(113, new ArrayList<>(tmpRegionList));
        //10
        tmpRegionList.clear();
        tmpRegionList.add(Region.A1_L);
        tmpRegionList.add(Region.M1_L);
        tmpRegionList.add(Region.M4_L);
        tmpRegionList.add(Region.BGIC_L);
        regionsAffectionL.put(114, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.A1_R);
        tmpRegionList.add(Region.M1_R);
        tmpRegionList.add(Region.M4_R);
        tmpRegionList.add(Region.BGIC_R);
        regionsAffectionR.put(114, new ArrayList<>(tmpRegionList));
        //11
        tmpRegionList.clear();
        tmpRegionList.add(Region.A3_R);
        tmpRegionList.add(Region.M6_R);
        regionsAffectionR.put(115, new ArrayList<>(tmpRegionList));
        tmpRegionList.clear();
        tmpRegionList.add(Region.A3_L);
        tmpRegionList.add(Region.M6_L);
        regionsAffectionL.put(115, new ArrayList<>(tmpRegionList));


    }
}
