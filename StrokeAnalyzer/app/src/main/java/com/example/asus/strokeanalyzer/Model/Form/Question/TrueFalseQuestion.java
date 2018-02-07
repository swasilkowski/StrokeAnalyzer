package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

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

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz wagę.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _strength waga pytania
     */
    public TrueFalseQuestion(int id, String text, QuestionStrength _strength)
    {
        super(id, text, _strength);
    }

}
