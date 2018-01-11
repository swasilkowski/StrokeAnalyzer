package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 *  Klasa reprezentująca pytanie, na które odpowiedż jest liczbą.
 *
 *  @author Marta Marciszewicz
 */

public class NumericQuestion extends Question {

    /**
     * onstruktor ustawiający ID pytania oraz jego treść
     * @param id ID pytania
     * @param text treść pytania
     */
    public NumericQuestion(int id, String text)
    {
        super(id,text);
    }
}
