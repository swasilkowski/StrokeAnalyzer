package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link Question} reprezentująca pytanie, na które odpowiedź jest typu prawda/fałsz.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseQuestion extends Question {

    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
     */
    public TrueFalseQuestion(int id, String text)
    {
        super(id, text);
    }
}
