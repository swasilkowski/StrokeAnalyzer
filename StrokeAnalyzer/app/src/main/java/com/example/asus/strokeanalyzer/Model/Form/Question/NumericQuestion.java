package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 *  Klasa stanowiąca rozszerzenie klasy {@link Question} reprezentująca pytanie, na które odpowiedź jest liczbą.
 *
 *  @author Marta Marciszewicz
 */

public class NumericQuestion extends Question {

    final public RangeClassifier Range;
    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
     */
    public NumericQuestion(int id, String text, RangeClassifier _range)
    {
        super(id,text);
        this.Range = _range;
    }
}
