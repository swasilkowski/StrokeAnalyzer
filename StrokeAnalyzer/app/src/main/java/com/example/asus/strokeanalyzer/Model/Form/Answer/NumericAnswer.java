package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link Answer} reprezentująca odpowiedź będącą liczbą.
 * Zawiera wartość udzielonej odpowiedzi typu double.
 *
 * @author Marta Marciszewicz
 */

public class NumericAnswer extends Answer {

    /**
     * Liczbowa wartość odpowiedzi.
     */
    public double Value;

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public NumericAnswer(int questionId)
    {
        super(questionId);
    }

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź oraz wartość udzielonej odpowiedzi.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     * @param value wartość odpowiedzi
     */
    public NumericAnswer(int questionId, double value) {
        super(questionId);
        Value = value;
    }
}
