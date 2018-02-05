package com.example.asus.strokeanalyzer.Model.Form.Question;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

/**
 * Klasa bazowa dla klas reprezentujących różne rodzaje pytań wykorzystywanych w formularzach aplikacji.
 * Zawiera id pytania oraz jest treść
 *
 * @author Marta Marciszewicz
 */

public class Question {

    /**
     * Id pytania.
     */
    final private int ID;
    /**
     * Treść pytania.
     */
    final private String Text;

    /**
     * Waga pytania. Informuje w jakim kolorze wyświetlona powinna być treść pytania.
     */
    private QuestionStrength Strength;

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
        Strength = QuestionStrength.NEUTRAL;
    }

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz jego wagę.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _strength waga pytania
     */
    Question(int id, String text, QuestionStrength _strength)
    {
        ID=id;
        Text = text;
        Strength = _strength;
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

    /**
     * Metoda zwracająca wagę pytania.
     *
     * @return waga pytania
     */
    public QuestionStrength GetStrength(){
        return Strength;
    }
}
