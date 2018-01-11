package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa reprezentująca oczekiwaną odpowiedź typu prawda/fałsz.
 * Zawiera wartość poprawnej odpowiedzi typu boolean. Może zawierać takżę liczbę punktów przyznawanych
 * dla poprawnej odpowiedzi
 *
 * @author Marta Marciszewicz
 */

public class ExpectedTrueFalseAnswer extends ExpectedAnswer {

    public boolean CorrectValue;
    public int Score;

    /**
     *  Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public ExpectedTrueFalseAnswer(int questionId)
    {
        super(questionId);
    }

    /**
     * Konstruktor umożliwiający ustawienie poprawnej odpowiedzi. Ustawia także ID pytania,
     * którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     * @param correctValue oczekiwana wartość odpowiedzi
     */
    public ExpectedTrueFalseAnswer(int questionId, boolean correctValue)
    {
        super(questionId);
        CorrectValue = correctValue;
    }

    /**
     * Konstruktor umożliwiający ustawienie poprawnej odpowiedzi oraz liczby punktów przyznawanych
     * za daną odpowiedź. Ustawia także ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     * @param correctValue oczekiwana wartość odpowiedzi
     * @param score liczba przyznanych punktów dla odpowiedzi
     */
    public ExpectedTrueFalseAnswer(int questionId, boolean correctValue, int score)
    {
        super(questionId);
        CorrectValue = correctValue;
        Score = score;
    }
}
