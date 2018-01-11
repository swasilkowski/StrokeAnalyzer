package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 * Klasa bazowa dla pytań wykorzystywanych w formualrzach aplikacji
 * Zawiera ID pytania oraz jest treść
 *
 * @author Marta Marciszewicz
 */

public class Question {

    private int ID;
    private String Text;

    /**
     * Konstruktor ustawiający pola klasy
     * @param id ID pytania
     * @param text treść pytania
     */
    public Question(int id, String text)
    {
        ID = id;
        Text = text;
    }

    /**
     * Metoda zwracajaca ID pytania
     * @return (int) ID pytania
     */
    public int GetID()
    {
        return ID;
    }

    /**
     * Metoda zwracająca treść pytania
     * @return (String) treść pytania
     */
    public String GetText()
    {
        return Text;
    }
}
