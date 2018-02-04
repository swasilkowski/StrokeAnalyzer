package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link Answer} reprezentująca odpowiedź będącą tekstem.
 * Zawiera wartość udzielonej odpowiedzi typu String.
 *
 * @author Marta Marciszewicz
 */

public class TextAnswer extends Answer {

    /**
     * Wartość odpowiedzi.
     */
    public String Value;

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public TextAnswer(int questionId)
    {
        super(questionId);
    }
}
