package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa bazowa dla klas reprezentujących różnego typu odpowiedzi udzielane przez użytkownika.
 * Zawiera id pytania, którego dotyczy odpowiedź.
 *
 * @author Marta Marciszewicz
 */

public class Answer {

    /**
     * Id pytania, którego dotyczy odpowiedź.
     */
    final private int questionId;

    /**
     * Konstruktor ustawiający id pytania.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public Answer(int questionId)
    {
        this.questionId = questionId;
    }

    /**
     * Metoda zwracająca id pytania, którego dotyczy odpowiedź.
     *
     * @return id pytania, którgo dotyczy odpowiedź
     */
    public int GetQuestionID()
    {
        return questionId;
    }
}
