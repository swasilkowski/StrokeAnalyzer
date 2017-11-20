package com.example.asus.strokeanalyzer.Model.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Question.Question;
import com.example.asus.strokeanalyzer.Model.Patient;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class FormsStructure {
    //key - particular form, value - list of question identifiers
    //QuestionsUsedForForm - contains list of questions used in a particular dictionary for analysis
    public static Dictionary<Form, List<Integer>> QuestionsUsedForForm;
    //key - particular form, value - list of question identifiers
    //QuestionsPrintedInForm - contains list of questions printed while showing particular form
    public static Dictionary<Form, List<Integer>> QuestionsPrintedInForm;
    //key - question id, value - question object
    //Questions - container for all questions used in an application
    public static Dictionary<Integer, Question> Questions;


    private FormsStructure(){}

    public static void PrintForm(Form form, Patient p){}
}

