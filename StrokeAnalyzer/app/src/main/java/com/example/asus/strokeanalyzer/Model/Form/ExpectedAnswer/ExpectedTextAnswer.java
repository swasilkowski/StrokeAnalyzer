package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa reprezentująca spodziewaną odpowiedź będącą napisem.
 * Zawiera wartość poprawnej odpowiedzi typu String
 *
 * @author Marta Marciszewicz
 */

class ExpectedTextAnswer extends  ExpectedAnswer {

    public String CorrectValue;

    /**
     * Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public ExpectedTextAnswer(int questionId)
    {
        super(questionId);
    }
}
