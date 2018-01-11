package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa bazowa dla oczekiwanych odpowiedzi. Pozwala na sprawdzenie, czy udzielona przez użytkownika odpowiedź
 * jest zgodna z oczekiwaną odpowiedzią na dane pytanie dla danej skali.
 * Zawiera Id pytania, którego dotyczy odpowiedź
 *
 * @author Marta Marciszewicz
 */

public class ExpectedAnswer {

    private int questionId;

    /**
     * Konstruktor ustawiający Id pytania
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public ExpectedAnswer(int questionId)
    {
        this.questionId = questionId;
    }

    /**
     * Metoda zwracająca ID pytania, którego dotyczy odpowiedź
     * @return (int) ID pytania, którgo dotyczy odpowiedź
     */
    public int GetQuestionID()
    {
        return questionId;
    }
}
