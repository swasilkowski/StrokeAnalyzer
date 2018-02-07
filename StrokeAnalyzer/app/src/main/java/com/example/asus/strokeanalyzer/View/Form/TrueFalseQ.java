package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie, na które odpowiedź jest typu prawda/fałsz.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseQ implements Question
{
    /**
     * Id pytania.
     */
    final private int id;
    /**
     * Treść pytania.
     */
    final private String text;
    /**
     * Wartość odpowiedzi udzielonej przez użytkownika.
     */
    private boolean answer;
    /**
     * Waga pytania wykorzystywana do kolorowania tekstu treści pytania.
     */
    private QuestionStrength strength;

    /**
     * Konstruktor ustawiający id pytania oraz jego treść i wagę.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param strength waga pytania
     */
    public TrueFalseQ(int id ,String text, QuestionStrength strength)
    {
        this.id = id;
        this.text = text;
        this.strength = strength;
    }

    /**
     * Metoda pozwalająca na pobranie treści pytania.
     *
     * @return treść pytania
     */
    public String getText() { return text; }

    /**
     * Metoda pobierająca id pytania.
     *
     * @return id pytania
     */
    public int getId()
    {
        return id;
    }

    /**
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika.
     *
     * @param answer wartość odpowiedzi wprowadzonej przez użytkownika
     */
    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }

    /**
     * Metoda zwracająca ustawioną odpowiedź na pytanie.
     *
     * @return wartość ustawionej odpowiedzi na pytanie
     */
    public boolean getAnswer() {return answer;}

    /**
     * Metoda zwracająca typ pytania jako wartość z interfejsu {@link Question}.
     *
     * @return typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.TRUEFALSE;
    }

    /**
     * Metoda zwracająca wagę pytania.
     *
     * @return waga pytania
     */
    @Override
    public QuestionStrength getStrength() {
        return strength;
    }
}
