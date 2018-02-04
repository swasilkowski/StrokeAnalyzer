package com.example.asus.strokeanalyzer.Model.Form.Question;

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
}
