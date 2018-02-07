package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie, na które odpowiedź
 * udzielana jest w postaci wprowadzenia przez użytkownika tekstu.
 *
 * @author Marta Marciszewicz
 */

public class DescriptiveQ implements Question {

    /**
     * Id pytania.
     */
    final private int id;
    /**
     * Treść pytania
     */
    final private String text;
    /**
     * Wartość odpowiedzi na pytanie udzielonej przez użytkownika.
     */
    private String answer;
    /**
     * Waga pytania wykorzystywana do kolorowania tekstu treści pytania.
     */
    private QuestionStrength strength;

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz wagę.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param strength waga pytania
     */
    public DescriptiveQ(int id ,String text, QuestionStrength strength)
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
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika.
     *
     * @param text wartość odpowiedzi wprowadzonej przez użytkwonika
     */
    public void setAnswer(String text)
    {
        answer = text;
    }

    /**
     * Metoda zwracająca odpowiedź na pytanie.
     *
     * @return wartość wprowadzonej odpowiedzi na pytanie
     */
    public String getAnswer() { return answer;}

    /**
     * Metoda pobierająca id pytania.
     *
     * @return id pytania
     */
    public int getID() {return id;}

    /**
     * Metoda zwracająca typ pytania jako wartość z interfejsu {@link Question}.
     *
     * @return typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.DESCRIPTIVE;
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
