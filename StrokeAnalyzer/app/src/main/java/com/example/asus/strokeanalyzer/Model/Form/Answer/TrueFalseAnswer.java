package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link Answer} reprezentująca odpowiedź typu prawda/fałsz.
 * Zawiera wartość udzielonej odpowiedzi typu boolean.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseAnswer extends Answer {

    public boolean Value;

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public TrueFalseAnswer(int questionId)
    {
        super(questionId);
    }


    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź, oraz wartość udzielonej odpowiedzi.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     * @param value wartość odpowiedzi
     */
    @SuppressWarnings("SameParameterValue")
    public TrueFalseAnswer(int questionId, boolean value) {
        super(questionId);
        Value = value;
    }
}
