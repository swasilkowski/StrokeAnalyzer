package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 *  Klasa stanowiąca rozszerzenie klasy {@link Question} reprezentująca pytanie, na które odpowiedź jest tekstem.
 *
 *  @author Marta Marciszewicz
 */

public class DescriptiveQuestion extends Question {

    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
     */
    public DescriptiveQuestion(int id, String text)
    {
        super(id,text);
    }
}
