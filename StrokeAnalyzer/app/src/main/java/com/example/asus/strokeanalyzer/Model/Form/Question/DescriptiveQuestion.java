package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 *  Klasa reprezentująca pytanie, na które odpowiedż jest tekstem.
 *
 *  @author Marta Marciszewicz
 */

public class DescriptiveQuestion extends Question {

    /**
     * Konstruktor ustawiający ID pytania oraz jego treść
     * @param id ID pytania
     * @param text treść pytania
     */
    public DescriptiveQuestion(int id, String text)
    {
        super(id,text);
    }
}
