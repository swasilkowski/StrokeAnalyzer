package com.example.asus.strokeanalyzer.Model.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
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

        ArrayList<Integer> questionIDs = new ArrayList<>();
        questionIDs.add(1);
        questionIDs.add(2);

        QuestionsUsedForForm.put(Form.DemographicAndClinic, (ArrayList)questionIDs.clone());


        questionIDs.clear();
        QuestionsUsedForForm.put(Form.Dragon,(ArrayList)questionIDs.clone());
        QuestionsUsedForForm.put(Form.Hat,(ArrayList)questionIDs.clone());

        questionIDs.clear();
        questionIDs.add(4);
        QuestionsUsedForForm.put(Form.iScore,(ArrayList)questionIDs.clone());
        QuestionsUsedForForm.put(Form.NIHSS,(ArrayList)questionIDs.clone());
        QuestionsUsedForForm.put(Form.StrokeBricks,(ArrayList)questionIDs.clone());
        questionIDs.clear();
        questionIDs.add(3);
        QuestionsUsedForForm.put(Form.ThrombolyticTreatment,(ArrayList)questionIDs.clone());

        questionIDs.clear();
        questionIDs.add(1);
        questionIDs.add(2);

        QuestionsPrintedInForm.put(Form.DemographicAndClinic,(ArrayList)questionIDs.clone());
        QuestionsPrintedInForm.put(Form.Dragon,(ArrayList)questionIDs.clone());
        QuestionsPrintedInForm.put(Form.Hat,(ArrayList)questionIDs.clone());

        questionIDs.clear();
        questionIDs.add(4);
        QuestionsPrintedInForm.put(Form.iScore,(ArrayList)questionIDs.clone());
        QuestionsPrintedInForm.put(Form.NIHSS,(ArrayList)questionIDs.clone());
        QuestionsPrintedInForm.put(Form.StrokeBricks,(ArrayList)questionIDs.clone());

        questionIDs.clear();
        questionIDs.add(3);
        QuestionsPrintedInForm.put(Form.ThrombolyticTreatment,questionIDs);



    }


    public static void PrintForm(Form form, Patient p){}
}

