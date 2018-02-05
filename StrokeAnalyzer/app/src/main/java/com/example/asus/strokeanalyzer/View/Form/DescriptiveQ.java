package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie, na które odpowiedź
 * udzielana jest w postaci wprowadzenia przez użytkownika tekstu.
 *
 * @author Marta Marciszewicz
 */

public class DescriptiveQ implements Question {

    final private int id;
    final private String text;
    private String answer;
    private QuestionStrength strength;

    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
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
