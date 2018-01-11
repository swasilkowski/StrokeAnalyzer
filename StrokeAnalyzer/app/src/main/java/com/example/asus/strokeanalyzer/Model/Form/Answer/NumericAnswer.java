package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa reprezentująca odpowiedź będącą liczbą.
 * Zawiera wartość udzielonej odpowiedzi typu double
 *
 * @author Marta Marciszewicz
 */

public class NumericAnswer extends Answer {

    public double Value;

    /**
     * Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public NumericAnswer(int questionId)
    {
        super(questionId);
    }
}
