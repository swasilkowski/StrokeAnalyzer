package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 *  Klasa reprezentująca pytanie, na które odpowiedż jest liczbą.
 *
 *  @author Marta Marciszewicz
 */

public class NumericQuestion extends Question {

    public RangeClassifier Range;
    /**
     * onstruktor ustawiający ID pytania oraz jego treść
     * @param id ID pytania
     * @param text treść pytania
     */
    public NumericQuestion(int id, String text, RangeClassifier _range)
    {
        super(id,text);
        this.Range = _range;
    }
}
