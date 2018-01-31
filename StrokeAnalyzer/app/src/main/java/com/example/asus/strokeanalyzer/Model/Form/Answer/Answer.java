package com.example.asus.strokeanalyzer.Model.Form.Answer;

/**
 * Klasa bazowa dla odpowiedzi udzielanych przez użytkownika.
 * Zawiera Id pytania, którego dotyczy odpowiedź.
 *
 * @author Marta Marciszewicz
 */

public class Answer {

    final private int questionId;

    /**
     * Konstruktor ustawiający Id pytania
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public Answer(int questionId)
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
