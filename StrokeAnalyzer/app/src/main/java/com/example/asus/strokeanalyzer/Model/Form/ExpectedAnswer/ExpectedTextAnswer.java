package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link ExpectedAnswer} reprezentująca spodziewaną odpowiedź będącą napisem.
 * Zawiera wartość poprawnej odpowiedzi typu String.
 *
 * @author Marta Marciszewicz
 */

class ExpectedTextAnswer extends  ExpectedAnswer {

    public String CorrectValue;

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public ExpectedTextAnswer(int questionId)
    {
        super(questionId);
    }
}
