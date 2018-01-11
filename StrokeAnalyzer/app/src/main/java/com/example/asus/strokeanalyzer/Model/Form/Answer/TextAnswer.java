package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa reprezentująca odpowiedź będącą tekstem.
 * Zawiera wartość udzielonej odpowiedzi typu String
 *
 * @author Marta Marciszewicz
 */

public class TextAnswer extends Answer {

    public String Value;

    /**
     * Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public TextAnswer(int questionId)
    {
        super(questionId);
    }
}
