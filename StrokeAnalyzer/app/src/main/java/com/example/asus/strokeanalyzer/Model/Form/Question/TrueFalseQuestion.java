package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 * Klasa reprezentująca pytanie, na które odpowiedż jest typu prawda/fałsz.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseQuestion extends Question {

    /**
     * Konstruktor ustawiający ID pytania oraz jego treść
     * @param id ID pytania
     * @param text treść pytania
     */
    public TrueFalseQuestion(int id, String text)
    {
        super(id, text);
    }
}
