package com.example.asus.strokeanalyzer.View.Form;

/**
 * Klasa reprezentująca pytanie, na które odpowiedź może być 'tak' lub 'nie'.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseQ implements Question
{
    private int id;
    private String text;
    private boolean answer;

    /**
     * Konstruktor ustawiający odpowiednie pola klasy.
     *
     * @param id Id pytania
     * @param text treść pytania
     */
    public TrueFalseQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    /**
     * Metoda pozwalająca na pobranie treści pytania
     *
     * @return (String) treść pytania
     */
    public String getText() { return text; }

    /**
     * Metoda pobierająca Id pytania
     *
     * @return (int) Id pytania
     */
    public int getId()
    {
        return id;
    }

    /**
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika
     *
     * @param answer wartość odpowiedzi wprowadzonej przez użytkwonika
     */
    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }

    /**
     * Metoda zwracająca odpowiedź na pytanie.
     *
     * @return (boolean) wartość odpowiedzi na pytanie
     */
    public boolean getAnswer() {return answer;}

    /**
     * Metoda zwracająca typ pytania z interfejsu {@link Question}
     *
     * @return (int) typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.TRUEFALSE;
    }
}
