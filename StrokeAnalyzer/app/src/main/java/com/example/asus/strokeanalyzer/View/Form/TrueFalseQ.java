package com.example.asus.strokeanalyzer.View.Form;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie, na które odpowiedź jest typu prawda/fałsz.
 *
 * @author Marta Marciszewicz
 */

public class TrueFalseQ implements Question
{
    final private int id;
    final private String text;
    private boolean answer;

    /**
     * Konstruktor ustawiający id pytania oraz jego treść.
     *
     * @param id id pytania
     * @param text treść pytania
     */
    public TrueFalseQ(int id ,String text)
    {
        this.id = id;
        this.text = text;
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
}
