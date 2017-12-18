package com.example.asus.strokeanalyzer.Model.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Question.BulletedQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.DescriptiveQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.Question;
import com.example.asus.strokeanalyzer.Model.Form.Question.TrueFalseQuestion;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 20.11.2017.
 */

public final class FormsStructure {
    //key - particular form, value - list of question identifiers
    //QuestionsUsedForForm - contains list of questions used in a particular dictionary for analysis
    public static Map<Form, List<Integer>> QuestionsUsedForForm = new Hashtable<>();
    //key - particular form, value - list of question identifiers
    //QuestionsPrintedInForm - contains list of questions printed while showing particular form
    public static Map<Form, List<Integer>> QuestionsPrintedInForm = new Hashtable<>();
    //key - question id, value - question object
    //Questions - container for all questions used in an application
    public static Map<Integer, Question> Questions = new Hashtable<>();


    private FormsStructure(){


    }

    public static void InitializeQuestionsList()
    {
        //uzupelnienei slownikow
        DescriptiveQuestion q1 = new DescriptiveQuestion(1, "Wiek pacjenta:");
        TrueFalseQuestion q2 = new TrueFalseQuestion(2, "Czy pacjent pali papierosy?");
        TrueFalseQuestion q3 = new TrueFalseQuestion(3, "Czy pacjent przebył udar w ciągu ostatnich 3 miesięcy?");
        TrueFalseQuestion q4 = new TrueFalseQuestion(4, "Czy pacjent cierpi na niewydolność nerek?");

        Questions.put(1,q1);
        Questions.put(2,q2);
        Questions.put(3, q3);
        Questions.put(4, q4);

        //NIHSS zrodlo: http://www.medistudent.pl/img/neurologia/skale/skala_nihss.pdf
        Map<Integer, String> answers101 = new Hashtable<>();
        answers101.put(0, "Czuwa. Żywa reakcja na bodźce.");
        answers101.put(1, "Reakcja na słabe bodźce. Spełanianie poleceń.");
        answers101.put(2, "Reakcja na silny bodziec bólowy.");
        answers101.put(3, "Brak reakcji lub reakcja odruchowa.");
        BulletedQuestion question101 = new BulletedQuestion(101, "1a. Przytomność", answers101);

        Map<Integer, String> answers102 = new Hashtable<>();
        answers102.put(0, "Prawidłowa odpowiedź na obydwa pytania.");
        answers102.put(1, "Prawidłowa odpowiedź na jedno z pytań.");
        answers102.put(2, "Brak prawidłowej odpowiedzi na żadne pytanie.");
        BulletedQuestion question102 = new BulletedQuestion(102, "1b. Orientacja: Aktualny miesiąc i wiek. Afazja - 2 pkt. Intubacja, Dyzartria, Uraz, Bariera językowa - 1 pkt", answers102);

        Map<Integer, String> answers103 = new Hashtable<>();
        answers103.put(0, "Prawidłowe wykonanie obu poleceń.");
        answers103.put(1, "Prawidłowe wykonanie jednego polecenia.");
        answers103.put(2, "Chory nie spełnił żadnego polecenia.");
        BulletedQuestion question103 = new BulletedQuestion(103, "1c. Polecenia: Otwarcie i zamknięcie oczu. Zaciśnięcie i rozluźnienie władnej ręki.", answers103);

        Map<Integer, String> answers104 = new Hashtable<>();
        answers104.put(0, "Ruchy prawidłowe.");
        answers104.put(1, "Częściowe porażenie (zbaczanie).");
        answers104.put(2, "Porażenie pełne lub przymusowe spojrzenie.");
        BulletedQuestion question104 = new BulletedQuestion(104, "2. Gałki oczne: Poziome ruchy gałek ocznych. Dowolne i odruchowe ruchy.", answers104);

        Map<Integer, String> answers105 = new Hashtable<>();
        answers105.put(0, "Bez ubytków w polu widzenia.");
        answers105.put(1, "Niedowidzenie +/- kwadrantowe oka.");
        answers105.put(2, "Niedowidzenie połowiczne oka.");
        answers105.put(3, "Ślepota organiczna lub korowa.");
        BulletedQuestion question105 = new BulletedQuestion(105, "3. Pole widzenia", answers105);

        Map<Integer, String> answers106 = new Hashtable<>();
        answers106.put(0, "Twarz prawidłowa, symetryczna.");
        answers106.put(1, "Spłycenie fałdu. Asymetria przy uśmiechu.");
        answers106.put(2, "Całkowity (lub prawie) paraliż dolnego piętra twarzy.");
        answers106.put(3, "Całkowity paraliż dolnej i górnej części twarzy.");
        BulletedQuestion question106 = new BulletedQuestion(106, "4. Nerw VII", answers106);

        Map<Integer, String> answers107 = new Hashtable<>();
        answers107.put(0, "LKG nie opada w ciągu 10 s.");
        answers107.put(1, "LKG opada przed 10 s., ale nie całym impetem");
        answers107.put(2, "LKG opada przed 10 s., ale oporuje grawitacji.");
        answers107.put(3, "LKG opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers107.put(4, "LKG – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question107 = new BulletedQuestion(107, "5a. LKG", answers107);

        Map<Integer, String> answers108 = new Hashtable<>();
        answers108.put(0, "PKG nie opada w ciągu 10 s.");
        answers108.put(1, "PKG opada przed 10 s., ale nie całym impetem");
        answers108.put(2, "PKG opada przed 10 s., ale oporuje grawitacji.");
        answers108.put(3, "PKG opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers108.put(4, "PKG – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question108 = new BulletedQuestion(108, "5b. PKG", answers108);

        Map<Integer, String> answers109 = new Hashtable<>();
        answers109.put(0, "LKD nie opada w ciągu 5 s.");
        answers109.put(1, "LKD opada przed 5 s., ale nie całym impetem");
        answers109.put(2, "LKD opada przed 5 s., ale oporuje grawitacji.");
        answers109.put(3, "LKD opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers109.put(4, "LKD – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question109 = new BulletedQuestion(109, "6a. LKD", answers109);

        Map<Integer, String> answers110 = new Hashtable<>();
        answers110.put(0, "PKD nie opada w ciągu 5 s.");
        answers110.put(1, "PKD opada przed 5 s., ale nie całym impetem");
        answers110.put(2, "PKD opada przed 5 s., ale oporuje grawitacji.");
        answers110.put(3, "PKD opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers110.put(4, "PKD – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question110 = new BulletedQuestion(110, "6b. LKD", answers110);

        Map<Integer, String> answers111 = new Hashtable<>();
        answers111.put(0, "Ataksja nieobecna.");
        answers111.put(1, "Ataksja obecna w minimum jednej kończynie L lub P.");
        answers111.put(2, "Ataksja obecna w obu kk. górnych lub dolnych (ew. skos).");
        BulletedQuestion question111 = new BulletedQuestion(111, "7. Ataksja", answers111);

        Map<Integer, String> answers112 = new Hashtable<>();
        answers112.put(0, "Czucie prawidłowe.");
        answers112.put(1, "Łagodna do umiarkowanej utrata czucia.");
        answers112.put(2, "Poważna, całkowita lub obustronna utrata czucia.");
        BulletedQuestion question112 = new BulletedQuestion(112, "8. Czucie", answers112);

        Map<Integer, String> answers113 = new Hashtable<>();
        answers113.put(0, "Brak afazji.");
        answers113.put(1, "Łagodna do umiarkowanej afazja.");
        answers113.put(2, "Poważna afazja.");
        answers113.put(3, "Mutyzm. Afazja globalna.");
        BulletedQuestion question113 = new BulletedQuestion(113, "9. Afazja", answers113);

        Map<Integer, String> answers114 = new Hashtable<>();
        answers114.put(0, "Mowa prawidłowa.");
        answers114.put(1, "Niewyraźne wymawianie przynajmniej niektórych słów.");
        answers114.put(2, "Mowa bardzo niewyraźna. Brak mowy.");
        BulletedQuestion question114 = new BulletedQuestion(114, "10. Dyzartria", answers114);

        Map<Integer, String> answers115 = new Hashtable<>();
        answers115.put(0, "Bez nieprawidłowości.");
        answers115.put(1, "Zaniedbywanie lub ekstyncja bodźców w jednej modalności.");
        answers115.put(2, "Połowiczna nieuwaga lub ekstyncja bodźców w kilku modalnościach.");
        BulletedQuestion question115 = new BulletedQuestion(115, "11. Zaniedbywanie", answers115);

        Questions.put(101, question101);
        Questions.put(102, question102);
        Questions.put(103, question103);
        Questions.put(104, question104);
        Questions.put(105, question105);
        Questions.put(106, question106);
        Questions.put(107, question107);
        Questions.put(108, question108);
        Questions.put(109, question109);
        Questions.put(110, question110);
        Questions.put(111, question111);
        Questions.put(112, question112);
        Questions.put(113, question113);
        Questions.put(114, question114);
        Questions.put(115, question115);

        ArrayList<Integer> questionsForNihss = new ArrayList<>();
        questionsForNihss.add(101);
        questionsForNihss.add(102);
        questionsForNihss.add(103);
        questionsForNihss.add(104);
        questionsForNihss.add(105);
        questionsForNihss.add(106);
        questionsForNihss.add(107);
        questionsForNihss.add(108);
        questionsForNihss.add(109);
        questionsForNihss.add(110);
        questionsForNihss.add(111);
        questionsForNihss.add(112);
        questionsForNihss.add(113);
        questionsForNihss.add(114);
        questionsForNihss.add(115);
        QuestionsUsedForForm.put(Form.NIHSS,questionsForNihss);
        QuestionsPrintedInForm.put(Form.NIHSS, questionsForNihss);

        //DEMOGRAFICZNE I KLINICZNE
        Map<Integer, String> answers201 = new Hashtable<>();
        answers201.put(0, "Kobieta");
        answers201.put(1, "Mężczyzna");
        BulletedQuestion question201 = new BulletedQuestion(201, "Płeć", answers201);

        TrueFalseQuestion question202 = new TrueFalseQuestion(202, "Czy pacjent pali papierosy?");

        TrueFalseQuestion question203 = new TrueFalseQuestion(203, "Czy pacjent choruje na cukrzycę?");

        TrueFalseQuestion question204 = new TrueFalseQuestion(204, "Niepełnosprawność mRS?");

        DescriptiveQuestion question205 = new DescriptiveQuestion(205, "Czas");

        DescriptiveQuestion question206 = new DescriptiveQuestion(206, "Poziom glukozy we krwi");

        TrueFalseQuestion question207 = new TrueFalseQuestion(207, "aPTT powyżej górnej granicy normy?");

        TrueFalseQuestion question208 = new TrueFalseQuestion(208, "Doustne leczenie przeciwzakrzepowe powodujące wzrost INR > 1,7");

        TrueFalseQuestion question209 = new TrueFalseQuestion(209, "Hiperdensyjna MCA");

        TrueFalseQuestion question210 = new TrueFalseQuestion(210, "Świeży udar widoczny w CT");

        Map<Integer, String> answers211 = new Hashtable<>();
        answers211.put(0, "Lakularny");
        answers211.put(1, "Innego typu");
        BulletedQuestion question211 = new BulletedQuestion(211, "Podtyp udaru", answers211);

        TrueFalseQuestion question212 = new TrueFalseQuestion(212, "Krwotok śródmózgowy w TK/MR");

        Questions.put(201, question201);
        Questions.put(202, question202);
        Questions.put(203, question203);
        Questions.put(204, question204);
        Questions.put(205, question205);
        Questions.put(206, question206);
        Questions.put(207, question207);
        Questions.put(208, question208);
        Questions.put(209, question209);
        Questions.put(210, question210);
        Questions.put(211, question211);
        Questions.put(212, question212);

        ArrayList<Integer> questionsDemographic = new ArrayList<>();
        questionsDemographic.add(201);
        questionsDemographic.add(202);
        questionsDemographic.add(203);
        questionsDemographic.add(204);
        questionsDemographic.add(205);
        questionsDemographic.add(206);
        questionsDemographic.add(207);
        questionsDemographic.add(208);
        questionsDemographic.add(209);
        questionsDemographic.add(210);
        questionsDemographic.add(211);
        questionsDemographic.add(212);
        QuestionsUsedForForm.put(Form.DemographicAndClinic,questionsDemographic);

        //LECZENIE
        TrueFalseQuestion question301 = new TrueFalseQuestion(301, "Choroba nowotworowa");
        TrueFalseQuestion question302 = new TrueFalseQuestion(302, "Leczenie heparyną w ciągu 48h poprzedzających wystąpienie udaru mózgu");
        TrueFalseQuestion question303 = new TrueFalseQuestion(303, "Wcześniej przebyty udar u chorego ze współistniejącą cukrzycą");
        TrueFalseQuestion question304 = new TrueFalseQuestion(304, "Wcześniej przebyty udar w ciągu ostatnich 3 miesięcy");
        TrueFalseQuestion question305 = new TrueFalseQuestion(305, " skaza krwotoczna");
        TrueFalseQuestion question306 = new TrueFalseQuestion(306, "Czynne bądź niedawno przebyte krwawienie zagrażające życiu");
        TrueFalseQuestion question307 = new TrueFalseQuestion(307, "Przebyte świeże krwawienie wewnątrzczaszkowe lub jego podejrzenie");
        TrueFalseQuestion question308 = new TrueFalseQuestion(308, "Podejrzenie krwotoku podpajęczynówkowego (SAH) oraz stan po przebytyn SAH");
        TrueFalseQuestion question309 = new TrueFalseQuestion(309, "Przebyte lub czynne uszkodzenie OUN (np. choroba nowotworowa, tętniak, przebyte zabiegi chirurgiczne z otwarciem czaszki lub kręgosłupa");
        TrueFalseQuestion question310 = new TrueFalseQuestion(310, "Retinopatia krwotoczna");
        TrueFalseQuestion question311 = new TrueFalseQuestion(311, "Przebyty ostatnio (<10 dni) urazowy zewnętrzny masaż serdca, pród, nakłucia naczunia niedostępnego dla ucisku");
        TrueFalseQuestion question312 = new TrueFalseQuestion(312, "Bakteryjne zapalenie wsierdzia, zapalenie osierdzia");
        TrueFalseQuestion question313 = new TrueFalseQuestion(313, "Ostre zapalenie trzustki");
        TrueFalseQuestion question314 = new TrueFalseQuestion(314, "Udokumentowana choroba wrzodowa przewodu pokarmowego w ciągu 3 miesięcy, żylaki przełyku, tętniak, malformacja tętniczo-żylna");
        TrueFalseQuestion question315 = new TrueFalseQuestion(315, "Ciężka choroba wątroby z niewydolnością, marskością lub nadciśnieniem wrotnym");
        TrueFalseQuestion question316 = new TrueFalseQuestion(316, "Duży zabieg chirurgiczny lub rozległy uraz w ciągu ostatnich 3 miesięcy");
        TrueFalseQuestion question317 = new TrueFalseQuestion(317, "Niewielki lub szybko ustępujący deficyt neurologiczny");
        TrueFalseQuestion question318 = new TrueFalseQuestion(318, "udar mózgu rozpoczynający się napadami drgawkowymi");
        TrueFalseQuestion question319 = new TrueFalseQuestion(319, "Kliniczne objawy krwotoku podpajęczynówkowego (SAH) nawet bez stwierdzenia charakterystycznych zmian w TK");
        TrueFalseQuestion question320 = new TrueFalseQuestion(320, "Ciśnienie skurczowe (SBP)> 185 mmHg lub rozkurczowe (DBP) >110 mmHg nie ulegające obniżeniu po podaniu labetalolu, urapidylu lub innych leków iv");

        Questions.put(301, question301);
        Questions.put(302, question302);
        Questions.put(303, question303);
        Questions.put(304, question304);
        Questions.put(305, question305);
        Questions.put(306, question306);
        Questions.put(307, question307);
        Questions.put(308, question308);
        Questions.put(309, question309);
        Questions.put(310, question310);
        Questions.put(311, question311);
        Questions.put(312, question312);
        Questions.put(313, question313);
        Questions.put(314, question314);
        Questions.put(315, question315);
        Questions.put(316, question316);
        Questions.put(317, question317);
        Questions.put(318, question318);
        Questions.put(319, question319);
        Questions.put(320, question320);

        ArrayList<Integer> questionsTreatment = new ArrayList<>();
        questionsTreatment.add(301);
        questionsTreatment.add(302);
        questionsTreatment.add(303);
        questionsTreatment.add(304);
        questionsTreatment.add(305);
        questionsTreatment.add(306);
        questionsTreatment.add(307);
        questionsTreatment.add(308);
        questionsTreatment.add(309);
        questionsTreatment.add(310);
        questionsTreatment.add(311);
        questionsTreatment.add(312);
        questionsTreatment.add(313);
        questionsTreatment.add(314);
        questionsTreatment.add(315);
        questionsTreatment.add(316);
        questionsTreatment.add(317);
        questionsTreatment.add(318);
        questionsTreatment.add(319);
        questionsTreatment.add(320);
        QuestionsUsedForForm.put(Form.ThrombolyticTreatment,questionsTreatment);

        //iScore
        TrueFalseQuestion question401 = new TrueFalseQuestion(401, "Migotanie przedsionków (AF)");
        TrueFalseQuestion question402 = new TrueFalseQuestion(402, "Zastoinowa niewydolność serca (CHF)");
        TrueFalseQuestion question403 = new TrueFalseQuestion(403, "Zawał serca w przeszłości (MI)");
        TrueFalseQuestion question404 = new TrueFalseQuestion(404, "Niewydolność nerek");
        //TrueFalseQuestion question405 = new TrueFalseQuestion(405, "");


//        ArrayList<Integer> questionIDs = new ArrayList<>();
//        questionIDs.add(1);
//        questionIDs.add(2);
//
//        QuestionsUsedForForm.put(Form.DemographicAndClinic, (ArrayList)questionIDs.clone());
//
//
//        questionIDs.clear();
//        QuestionsUsedForForm.put(Form.Dragon,(ArrayList)questionIDs.clone());
//        QuestionsUsedForForm.put(Form.Hat,(ArrayList)questionIDs.clone());
//
//        questionIDs.clear();
//        questionIDs.add(4);
//        QuestionsUsedForForm.put(Form.iScore,(ArrayList)questionIDs.clone());
//        QuestionsUsedForForm.put(Form.StrokeBricks,(ArrayList)questionIDs.clone());
//        questionIDs.clear();
//        questionIDs.add(3);
//        QuestionsUsedForForm.put(Form.ThrombolyticTreatment,(ArrayList)questionIDs.clone());
//
//        questionIDs.clear();
//        questionIDs.add(1);
//        questionIDs.add(2);
//
//        QuestionsPrintedInForm.put(Form.DemographicAndClinic,(ArrayList)questionIDs.clone());
//        QuestionsPrintedInForm.put(Form.Dragon,(ArrayList)questionIDs.clone());
//        QuestionsPrintedInForm.put(Form.Hat,(ArrayList)questionIDs.clone());
//
//        questionIDs.clear();
//        questionIDs.add(4);
//        QuestionsPrintedInForm.put(Form.iScore,(ArrayList)questionIDs.clone());
//        QuestionsPrintedInForm.put(Form.NIHSS,(ArrayList)questionIDs.clone());
//        QuestionsPrintedInForm.put(Form.StrokeBricks,(ArrayList)questionIDs.clone());
//
//        questionIDs.clear();
//        questionIDs.add(3);
//        QuestionsPrintedInForm.put(Form.ThrombolyticTreatment,questionIDs);



    }


    public static void PrintForm(Form form, Patient p){}
}

