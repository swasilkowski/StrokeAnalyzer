package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa bazowa dla klas reprezentujących różnego typu oczekiwane odpowiedzi.
 * Pozwala na sprawdzenie, czy udzielona przez użytkownika odpowiedź jest zgodna z oczekiwaną
 * odpowiedzią na dane pytanie dla danej skali. Zawiera id pytania, którego dotyczy odpowiedź
 *
 * @author Marta Marciszewicz
 */

public class ExpectedAnswer {

    final private int questionId;

    /**
     * Konstruktor ustawiający id pytania.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    ExpectedAnswer(int questionId)
    {
        this.questionId = questionId;
    }

    /**
     * Metoda zwracająca id pytania, którego dotyczy odpowiedź.
     *
     * @return id pytania, którego dotyczy odpowiedź
     */
    public int GetQuestionID()
    {
        return questionId;
    }
}
