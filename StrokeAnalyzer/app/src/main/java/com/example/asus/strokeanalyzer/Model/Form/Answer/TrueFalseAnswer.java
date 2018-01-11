package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa reprezentująca odpowiedź typu prawda/fałsz.
 * Zawiera wartość udzielonej odpowiedzi typu boolean
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseAnswer extends Answer {

    public boolean Value;

    /**
     * Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public TrueFalseAnswer(int questionId)
    {
        super(questionId);
    }

    public TrueFalseAnswer(int questionId, boolean value) {
        super(questionId);
        Value = value;
    }
}
