package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

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

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz wagę.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _strength waga pytania
     */
    public DescriptiveQuestion(int id, String text, QuestionStrength _strength)
    {
        super(id,text,_strength);
    }
}
