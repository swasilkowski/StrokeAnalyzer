package com.example.asus.strokeanalyzer.View.Form;

/**
 * Klasa reprezentująca pytanie, na które odpowiedź udzielana jest w postaci wprowadzenia przez użytkownika
 * tekstu.
 *
 * @author Marta Marciszewicz
 */

public class DescriptiveQ implements Question {

    private int id;
    private String text;
    private String answer;

    /**
     * Konstruktor ustawiający odpowiednie pola klasy.
     *
     * @param id Id pytania
     * @param text treść pytania
     */
    public DescriptiveQ(int id ,String text)
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
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika
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
     * @return (String) wartość odpowiedzi na pytanie
     */
    public String getAnswer() { return answer;}

    /**
     * Metoda pobierająca Id pytania
     *
     * @return (int) Id pytania
     */
    public int getID() {return id;}

    /**
     * Metoda zwracająca typ pytania z interfejsu {@link Question}
     *
     * @return (int) typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.DESCRIPTIVE;
    }
}
