package com.example.asus.strokeanalyzer.Model.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;
import com.example.asus.strokeanalyzer.Model.Form.Question.BulletedQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.NumericQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.Question;
import com.example.asus.strokeanalyzer.Model.Form.Question.TrueFalseQuestion;
import com.example.asus.strokeanalyzer.Model.Patient;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Klasa zarządzająca formularzami dostępnymi w aplikacji. Przechowuje listę wszystkich pytań aplikacji oraz
 * definiuje powiązania formularzy z pytaniami używanymi przy wyznaczaniu wyniku i wyświetlaniu danego formularza.
 * Dodatkowo klasa umożliwia też sprawdzenie, czy zostały przez użytkownika udzielone odpowiedzi na
 * wszystkie pytania wymagane dla analizy danego formularza.
 *
 * @author Marta Marciszewicz & Stanisław Wasilkowski
 */

public final class FormsStructure {
    //key - particular form, value - list of question identifiers
    //QuestionsUsedForForm - contains list of questions used in a particular dictionary for analysis
    /**
     * Mapa wiążąca formualrz z listą pytań wykorzystywaną w nim podczas analizy wyniku.
     * Klucz: formularz.
     * Wartość: lista id pytań wykorzystywanych w formularzu w trakcie analizy.
     */
    final public static Map<Form, List<Integer>> QuestionsUsedForForm = new Hashtable<>();
    //key - particular form, value - list of question identifiers
    //QuestionsPrintedInForm - contains list of questions printed while showing particular form
    /**
     * Mapa wiążąca formularz z listą pytań wyświetlanych w momencie uzupełniania danego formularza.
     * Klucz: formularz.
     * Wartość: lista id pytań wyświetlanych w danym formularzu.
     */
    final public static Map<Form, List<Integer>> QuestionsPrintedInForm = new Hashtable<>();
    //key - question id, value - question object
    //Questions - container for all questions used in an application
    /**
     * Mapa przechowujący wszystkie pytania wykorzystywane w aplikacji.
     * Klucz: id pytania.
     * Wartość: obiekt klasy {@link Question} zawierający pytanie.
     */
    final public static Map<Integer, Question> Questions = new Hashtable<>();


    /**
     * Domyślny konstruktor bezparametrowy klasy oznaczony jako prywatny, by uniemożliwić
     * jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private FormsStructure(){
    }

    /**
     * Metoda sprawdzająca, czy obiekt klasy Patient zawiera odpowiedzi na wszytkie pytania wykorzystywane w formularzu
     * przekazanym jako parametr.
     *
     * @param p obiekt klasy Patient, dla którego sprawdzamy możliwość analizy we wskazanej skali
     * @param form rodzaj skali, której analizę chcemy przeprowadzić dla obiektu p
     * @return true - jeżeli obiekt p zawiera wszystkie odpowiedzi na pytania formularza form;
     *          false - jeżeli obiekt p nie posiada odpowiedzi na wszystkie pytania formularza form
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean patientReady(Patient p, Form form)
    {
        List<Integer> questions = FormsStructure.QuestionsUsedForForm.get(form);
        for(Integer id:questions)
        {
            if(!p.PatientAnswers.containsKey(id))
                return false;
        }
        return true;
    }

    /**
     * Metoda inicjalizująca słowniki klasy FormsStructure.
     */
    public static void InitializeQuestionsList()
    {
        //uzupelnienei slownikow
        LinkedList<Integer> questionsUsedForNihhs = new LinkedList<>();
        LinkedList<Integer> questionsUsedForHat = new LinkedList<>();
        LinkedList<Integer> questionsUsedForIscore = new LinkedList<>();
        LinkedList<Integer> questionsUsedForThrombolysis = new LinkedList<>();
        LinkedList<Integer> questionsUsedForDragon = new LinkedList<>();
        LinkedList<Integer> questionsUsedForStrokeBricks = new LinkedList<>();

        //NIHSS zrodlo: http://www.medistudent.pl/img/neurologia/skale/skala_nihss.pdf
        int id = 101;
        Map<Integer, String> answers101 = new Hashtable<>();
        answers101.put(0, "Czuwa. Żywa reakcja na bodźce.");
        answers101.put(1, "Reakcja na słabe bodźce. Spełanianie poleceń.");
        answers101.put(2, "Reakcja na silny bodziec bólowy.");
        answers101.put(3, "Brak reakcji lub reakcja odruchowa.");
        BulletedQuestion question101 = new BulletedQuestion(id, "1a. Przytomność", answers101);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 102;
        Map<Integer, String> answers102 = new Hashtable<>();
        answers102.put(0, "Prawidłowa odpowiedź na obydwa pytania.");
        answers102.put(1, "Prawidłowa odpowiedź na jedno z pytań.");
        answers102.put(2, "Brak prawidłowej odpowiedzi na żadne pytanie.");
        BulletedQuestion question102 = new BulletedQuestion(id, "1b. Orientacja: Aktualny miesiąc i wiek. Afazja - 2 pkt. Intubacja, Dyzartria, Uraz, Bariera językowa - 1 pkt", answers102);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 103;
        Map<Integer, String> answers103 = new Hashtable<>();
        answers103.put(0, "Prawidłowe wykonanie obu poleceń.");
        answers103.put(1, "Prawidłowe wykonanie jednego polecenia.");
        answers103.put(2, "Chory nie spełnił żadnego polecenia.");
        BulletedQuestion question103 = new BulletedQuestion(id, "1c. Polecenia: Otwarcie i zamknięcie oczu. Zaciśnięcie i rozluźnienie władnej ręki.", answers103);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 104;
        Map<Integer, String> answers104 = new Hashtable<>();
        answers104.put(0, "Ruchy prawidłowe.");
        answers104.put(1, "Częściowe porażenie (zbaczanie).");
        answers104.put(2, "Porażenie pełne lub przymusowe spojrzenie.");
        BulletedQuestion question104 = new BulletedQuestion(id, "2. Gałki oczne: Poziome ruchy gałek ocznych. Dowolne i odruchowe ruchy.", answers104);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 105;
        Map<Integer, String> answers105 = new Hashtable<>();
        answers105.put(0, "Bez ubytków w polu widzenia.");
        answers105.put(1, "Niedowidzenie +/- kwadrantowe oka.");
        answers105.put(2, "Niedowidzenie połowiczne oka.");
        answers105.put(3, "Ślepota organiczna lub korowa.");
        BulletedQuestion question105 = new BulletedQuestion(id, "3. Pole widzenia", answers105);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 106;
        Map<Integer, String> answers106 = new Hashtable<>();
        answers106.put(0, "Twarz prawidłowa, symetryczna.");
        answers106.put(1, "Spłycenie fałdu. Asymetria przy uśmiechu.");
        answers106.put(2, "Całkowity (lub prawie) paraliż dolnego piętra twarzy.");
        answers106.put(3, "Całkowity paraliż dolnej i górnej części twarzy.");
        BulletedQuestion question106 = new BulletedQuestion(id, "4. Nerw VII", answers106);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 107;
        Map<Integer, String> answers107 = new Hashtable<>();
        answers107.put(0, "LKG nie opada w ciągu 10 s.");
        answers107.put(1, "LKG opada przed 10 s., ale nie całym impetem");
        answers107.put(2, "LKG opada przed 10 s., ale oporuje grawitacji.");
        answers107.put(3, "LKG opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers107.put(4, "LKG – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question107 = new BulletedQuestion(id, "5a. LKG", answers107);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 108;
        Map<Integer, String> answers108 = new Hashtable<>();
        answers108.put(0, "PKG nie opada w ciągu 10 s.");
        answers108.put(1, "PKG opada przed 10 s., ale nie całym impetem");
        answers108.put(2, "PKG opada przed 10 s., ale oporuje grawitacji.");
        answers108.put(3, "PKG opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers108.put(4, "PKG – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question108 = new BulletedQuestion(id, "5b. PKG", answers108);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 109;
        Map<Integer, String> answers109 = new Hashtable<>();
        answers109.put(0, "LKD nie opada w ciągu 5 s.");
        answers109.put(1, "LKD opada przed 5 s., ale nie całym impetem");
        answers109.put(2, "LKD opada przed 5 s., ale oporuje grawitacji.");
        answers109.put(3, "LKD opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers109.put(4, "LKD – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question109 = new BulletedQuestion(id, "6a. LKD", answers109);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 110;
        Map<Integer, String> answers110 = new Hashtable<>();
        answers110.put(0, "PKD nie opada w ciągu 5 s.");
        answers110.put(1, "PKD opada przed 5 s., ale nie całym impetem");
        answers110.put(2, "PKD opada przed 5 s., ale oporuje grawitacji.");
        answers110.put(3, "PKD opada, nie oporuje grawitacji. Minimalne ruchy czynne.");
        answers110.put(4, "PKD – porażenie (całkowity bark ruchów czynnych).");
        BulletedQuestion question110 = new BulletedQuestion(id, "6b. LKD", answers110);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 111;
        Map<Integer, String> answers111 = new Hashtable<>();
        answers111.put(0, "Ataksja nieobecna.");
        answers111.put(1, "Ataksja obecna w minimum jednej kończynie L lub P.");
        answers111.put(2, "Ataksja obecna w obu kk. górnych lub dolnych (ew. skos).");
        BulletedQuestion question111 = new BulletedQuestion(id, "7. Ataksja", answers111);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 112;
        Map<Integer, String> answers112 = new Hashtable<>();
        answers112.put(0, "Czucie prawidłowe.");
        answers112.put(1, "Łagodna do umiarkowanej utrata czucia.");
        answers112.put(2, "Poważna, całkowita lub obustronna utrata czucia.");
        BulletedQuestion question112 = new BulletedQuestion(id, "8. Czucie", answers112);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 113;
        Map<Integer, String> answers113 = new Hashtable<>();
        answers113.put(0, "Brak afazji.");
        answers113.put(1, "Łagodna do umiarkowanej afazja.");
        answers113.put(2, "Poważna afazja.");
        answers113.put(3, "Mutyzm. Afazja globalna.");
        BulletedQuestion question113 = new BulletedQuestion(id, "9. Afazja", answers113);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 114;
        Map<Integer, String> answers114 = new Hashtable<>();
        answers114.put(0, "Mowa prawidłowa.");
        answers114.put(1, "Niewyraźne wymawianie przynajmniej niektórych słów.");
        answers114.put(2, "Mowa bardzo niewyraźna. Brak mowy.");
        BulletedQuestion question114 = new BulletedQuestion(id, "10. Dyzartria", answers114);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        id = 115;
        Map<Integer, String> answers115 = new Hashtable<>();
        answers115.put(0, "Bez nieprawidłowości.");
        answers115.put(1, "Zaniedbywanie lub ekstyncja bodźców w jednej modalności.");
        answers115.put(2, "Połowiczna nieuwaga lub ekstyncja bodźców w kilku modalnościach.");
        BulletedQuestion question115 = new BulletedQuestion(id, "11. Zaniedbywanie", answers115);
        questionsUsedForNihhs.add(id);
        questionsUsedForStrokeBricks.add(id);

        //dodatkowe pomocnicze pytania
        id=116;
        Map<Integer, String> answers116 = new Hashtable<>();
        answers116.put(0, "Lewa");
        answers116.put(1, "Prawa");
        BulletedQuestion question116 = new BulletedQuestion(id, "* Dominująca półkula mózgu", answers116);
        questionsUsedForStrokeBricks.add(id);

        id=117;
        Map<Integer, String> answers117 = new Hashtable<>();
        answers117.put(0, "Prawa");
        answers117.put(1, "Lewa");
        BulletedQuestion question117 = new BulletedQuestion(id, "* Strona ciała, po której występują zaznaczone objawy", answers117);
        questionsUsedForStrokeBricks.add(id);

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
        Questions.put(116, question116);
        Questions.put(117, question117);


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
        questionsForNihss.add(116);
        questionsForNihss.add(117);
        QuestionsUsedForForm.put(Form.NIHSS,questionsUsedForNihhs);
        QuestionsPrintedInForm.put(Form.NIHSS, questionsForNihss);

        //DEMOGRAFICZNE I KLINICZNE
        id = 200;
        NumericQuestion question200 = new NumericQuestion(id, "Wiek", new RangeClassifier(0,150));
        questionsUsedForDragon.add(id);
        questionsUsedForIscore.add(id);
        questionsUsedForThrombolysis.add(id);

        id = 201;
        Map<Integer, String> answers201 = new Hashtable<>();
        answers201.put(0, "Kobieta");
        answers201.put(1, "Mężczyzna");
        BulletedQuestion question201 = new BulletedQuestion(id, "Płeć", answers201);
        questionsUsedForIscore.add(id);

        id = 202;
        TrueFalseQuestion question202 = new TrueFalseQuestion(id, "Czy pacjent pali papierosy?");
        questionsUsedForIscore.add(id);

        id = 203;
        TrueFalseQuestion question203 = new TrueFalseQuestion(id, "Czy pacjent choruje na cukrzycę?");
        questionsUsedForHat.add(id);

        id = 204;
        TrueFalseQuestion question204 = new TrueFalseQuestion(id, "Poziom niepełnosprawności przed przyjęciem (mRS > 1)");
        questionsUsedForDragon.add(id);
        questionsUsedForIscore.add(id);

        id = 205;
        NumericQuestion question205 = new NumericQuestion(id, "Czas od wystąpienia objawów (minuty)", new RangeClassifier(0,6000));
        questionsUsedForDragon.add(id);
        questionsUsedForThrombolysis.add(id);

        id = 206;
        NumericQuestion question206 = new NumericQuestion(id, "Poziom glukozy we krwi (mg/dL)", new RangeClassifier(0,1000));
        questionsUsedForDragon.add(id);
        questionsUsedForIscore.add(id);
        questionsUsedForHat.add(id);
        questionsUsedForThrombolysis.add(id);

        id = 207;
        TrueFalseQuestion question207 = new TrueFalseQuestion(id, "aPTT powyżej górnej granicy normy?");
        questionsUsedForThrombolysis.add(id);

        id = 208;
        TrueFalseQuestion question208 = new TrueFalseQuestion(id, "Doustne leczenie przeciwzakrzepowe powodujące wzrost INR > 1,7");
        questionsUsedForThrombolysis.add(id);

        id = 209;
        TrueFalseQuestion question209 = new TrueFalseQuestion(id, "Hiperdensyjna MCA");
        questionsUsedForDragon.add(id);

        id = 210;
        Map<Integer, String> answers210 = new Hashtable<>();
        answers210.put(0, "Nie");
        answers210.put(1, "Tak, ale zajmuje <1/3 terytorium MCA");
        answers210.put(2, "Tak, zajmuje >=1/3 terytorium MCA");
        BulletedQuestion question210 = new BulletedQuestion(id, "Widoczny świeży udar na obrazie CT mózgu", answers210);
        questionsUsedForDragon.add(id);
        questionsUsedForHat.add(id);
        questionsUsedForThrombolysis.add(id);

        id = 211;
        Map<Integer, String> answers211 = new Hashtable<>();
        answers211.put(0, "Lakunarny");
        answers211.put(1, "Nielakunarny");
        answers211.put(2, "Nieznanej etiologii");
        BulletedQuestion question211 = new BulletedQuestion(id, "Podtyp udaru", answers211);
        questionsUsedForIscore.add(id);

        id = 212;
        TrueFalseQuestion question212 = new TrueFalseQuestion(id, "Krwotok śródmózgowy w TK/MR");
        questionsUsedForThrombolysis.add(id);

        Questions.put(200,question200);
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
        questionsDemographic.add(200);
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
        QuestionsPrintedInForm.put(Form.DemographicAndClinic,questionsDemographic);

        //LECZENIE
        id = 301;
        TrueFalseQuestion question301 = new TrueFalseQuestion(id, "Choroba nowotworowa", QuestionStrength.STRONG);
        questionsUsedForIscore.add(id);
        questionsUsedForThrombolysis.add(id);

        id = 302;
        TrueFalseQuestion question302 = new TrueFalseQuestion(id, "Leczenie heparyną w ciągu 48h poprzedzających wystąpienie udaru mózgu", QuestionStrength.STRONG);
        questionsUsedForThrombolysis.add(id);

        id = 303;
        TrueFalseQuestion question303 = new TrueFalseQuestion(id, "Wcześniej przebyty udar u chorego ze współistniejącą cukrzycą", QuestionStrength.MIDSTRONG);
        questionsUsedForThrombolysis.add(id);

        id = 304;
        TrueFalseQuestion question304 = new TrueFalseQuestion(id, "Wcześniej przebyty udar w ciągu ostatnich 3 miesięcy", QuestionStrength.MIDSTRONG);
        questionsUsedForThrombolysis.add(id);

        id = 305;
        TrueFalseQuestion question305 = new TrueFalseQuestion(id, " skaza krwotoczna");
        questionsUsedForThrombolysis.add(id);

        id = 306;
        TrueFalseQuestion question306 = new TrueFalseQuestion(id, "Czynne bądź niedawno przebyte krwawienie zagrażające życiu");
        questionsUsedForThrombolysis.add(id);

        id = 307;
        TrueFalseQuestion question307 = new TrueFalseQuestion(id, "Przebyte świeże krwawienie wewnątrzczaszkowe lub jego podejrzenie");
        questionsUsedForThrombolysis.add(id);

        id = 308;
        TrueFalseQuestion question308 = new TrueFalseQuestion(id, "Podejrzenie krwotoku podpajęczynówkowego (SAH) oraz stan po przebytyn SAH");
        questionsUsedForThrombolysis.add(id);

        id = 309;
        TrueFalseQuestion question309 = new TrueFalseQuestion(id, "Przebyte lub czynne uszkodzenie OUN (np. choroba nowotworowa, tętniak, przebyte zabiegi chirurgiczne z otwarciem czaszki lub kręgosłupa");
        questionsUsedForThrombolysis.add(id);

        id = 310;
        TrueFalseQuestion question310 = new TrueFalseQuestion(id, "Retinopatia krwotoczna");
        questionsUsedForThrombolysis.add(id);

        id = 311;
        TrueFalseQuestion question311 = new TrueFalseQuestion(id, "Przebyty ostatnio (<10 dni) urazowy zewnętrzny masaż serdca, pród, nakłucia naczunia niedostępnego dla ucisku");
        questionsUsedForThrombolysis.add(id);

        id = 312;
        TrueFalseQuestion question312 = new TrueFalseQuestion(id, "Bakteryjne zapalenie wsierdzia, zapalenie osierdzia");
        questionsUsedForThrombolysis.add(id);

        id = 313;
        TrueFalseQuestion question313 = new TrueFalseQuestion(id, "Ostre zapalenie trzustki");
        questionsUsedForThrombolysis.add(id);

        id = 314;
        TrueFalseQuestion question314 = new TrueFalseQuestion(id, "Udokumentowana choroba wrzodowa przewodu pokarmowego w ciągu 3 miesięcy, żylaki przełyku, tętniak, malformacja tętniczo-żylna");
        questionsUsedForThrombolysis.add(id);

        id = 315;
        TrueFalseQuestion question315 = new TrueFalseQuestion(id, "Ciężka choroba wątroby z niewydolnością, marskością lub nadciśnieniem wrotnym");
        questionsUsedForThrombolysis.add(id);

        id = 316;
        TrueFalseQuestion question316 = new TrueFalseQuestion(id, "Duży zabieg chirurgiczny lub rozległy uraz w ciągu ostatnich 3 miesięcy");
        questionsUsedForThrombolysis.add(id);

        id = 317;
        TrueFalseQuestion question317 = new TrueFalseQuestion(id, "Niewielki lub szybko ustępujący deficyt neurologiczny");
        questionsUsedForThrombolysis.add(id);

        id = 318;
        TrueFalseQuestion question318 = new TrueFalseQuestion(id, "udar mózgu rozpoczynający się napadami drgawkowymi");
        questionsUsedForThrombolysis.add(id);

        id = 319;
        TrueFalseQuestion question319 = new TrueFalseQuestion(id, "Kliniczne objawy krwotoku podpajęczynówkowego (SAH) nawet bez stwierdzenia charakterystycznych zmian w TK");
        questionsUsedForThrombolysis.add(id);

        id = 320;
        TrueFalseQuestion question320 = new TrueFalseQuestion(id, "Ciśnienie skurczowe (SBP)> 185 mmHg lub rozkurczowe (DBP) >110 mmHg nie ulegające obniżeniu po podaniu labetalolu, urapidylu lub innych leków iv");
        questionsUsedForThrombolysis.add(id);

        id=321;
        NumericQuestion question321 = new NumericQuestion(id, "Liczba płytek krwi", new RangeClassifier(0,Double.MAX_VALUE));
        questionsUsedForThrombolysis.add(id);

        id = 322;
        TrueFalseQuestion question322 = new TrueFalseQuestion(id, "Wykluczenie uogólnionego niedokrwienia mózgu (np. omdlenie), napadem drgawkowym i migreną oraz hipoglikemią");
        questionsUsedForThrombolysis.add(id);

        id=323;
        TrueFalseQuestion question323 = new TrueFalseQuestion(id, "Czy czas trwania objawów bez znaczącej poprawy wynosi ≥ 30 minut?", QuestionStrength.WEAK);
        questionsUsedForThrombolysis.add(id);

        id=324;
        TrueFalseQuestion question324 = new TrueFalseQuestion(id, "Ostry udaru niedokrwiennego mózgu z istotnym deficytem neurologicznym.");
        questionsUsedForThrombolysis.add(id);

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
        Questions.put(321, question321);
        Questions.put(322, question322);
        Questions.put(323,question323);
        Questions.put(324, question324);

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
        questionsTreatment.add(321);
        questionsTreatment.add(322);
        questionsTreatment.add(323);
        questionsTreatment.add(324);
        QuestionsPrintedInForm.put(Form.ThrombolyticTreatment,questionsTreatment);

        //iScore
        id = 401;
        TrueFalseQuestion question401 = new TrueFalseQuestion(id, "Migotanie przedsionków (AF)");
        questionsUsedForIscore.add(id);

        id = 402;
        TrueFalseQuestion question402 = new TrueFalseQuestion(id, "Zastoinowa niewydolność serca (CHF)");
        questionsUsedForIscore.add(id);

        id = 403;
        TrueFalseQuestion question403 = new TrueFalseQuestion(id, "Zawał serca w przeszłości (MI)");
        questionsUsedForIscore.add(id);

        id = 404;
        TrueFalseQuestion question404 = new TrueFalseQuestion(id, "Niewydolność nerek");
        questionsUsedForIscore.add(id);

        Questions.put(401, question401);
        Questions.put(402, question402);
        Questions.put(403, question403);
        Questions.put(404, question404);

        ArrayList<Integer> questionsIscore = new ArrayList<>();
        questionsIscore.add(401);
        questionsIscore.add(402);
        questionsIscore.add(403);
        questionsIscore.add(404);
        QuestionsPrintedInForm.put(Form.iScore,questionsIscore);

        QuestionsUsedForForm.put(Form.iScore, questionsUsedForIscore);
        QuestionsUsedForForm.put(Form.ThrombolyticTreatment, questionsUsedForThrombolysis);
        QuestionsUsedForForm.put(Form.Dragon, questionsUsedForDragon);
        QuestionsUsedForForm.put(Form.Hat, questionsUsedForHat);
        QuestionsUsedForForm.put(Form.StrokeBricks, questionsUsedForStrokeBricks);

        NumericQuestion nihssQuestion = new NumericQuestion(500, "Liczba punktów w skali NIHSS", new RangeClassifier(0,50));
        Questions.put(500,nihssQuestion);

    }
}

