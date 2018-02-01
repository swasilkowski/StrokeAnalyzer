package com.example.asus.strokeanalyzer.Model.Form.Question;

/**
 * Klasa bazowa dla klas reprezentujących różne rodzaje pytań wykorzystywanych w formularzach aplikacji.
 * Zawiera id pytania oraz jest treść
 *
 * @author Marta Marciszewicz
 */

public class Question {

    final private int ID;
    final private String Text;

    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
     */
    Question(int id, String text)
    {
        ID = id;
        Text = text;
    }

    /**
     * Metoda zwracająca id pytania.
     *
     * @return id pytania
     */
    public int GetID()
    {
        return ID;
    }

    /**
     * Metoda zwracająca treść pytania.
     *
     * @return treść pytania
     */
    public String GetText()
    {
        return Text;
    }
}
