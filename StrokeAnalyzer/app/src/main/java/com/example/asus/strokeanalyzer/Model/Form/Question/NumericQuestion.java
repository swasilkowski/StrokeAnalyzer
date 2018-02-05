package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 *  Klasa stanowiąca rozszerzenie klasy {@link Question} reprezentująca pytanie, na które odpowiedź jest liczbą.
 *
 *  @author Marta Marciszewicz
 */

public class NumericQuestion extends Question {

    /**
     * Klasyfikator zakresu dopuszczalnych wartości odpowiedzi na pytanie.
     */
    final public RangeClassifier Range;

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz zakres dopuszczalnych wartości odpowiedzi.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _range zakres dopuszczalnych wartości odpowiedzi
     */
    public NumericQuestion(int id, String text, RangeClassifier _range)
    {
        super(id,text);
        this.Range = _range;
    }

    /**
     * Konstruktor ustawiający id pytania, jego treść i wagę oraz zakres dopuszczalnych wartości odpowiedzi.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _strength waga pytania
     * @param _range zakres dopuszczalnych wartości odpowiedzi
     */
    public NumericQuestion(int id, String text, QuestionStrength _strength, RangeClassifier _range)
    {
        super(id,text,_strength);
        this.Range = _range;
    }

}
