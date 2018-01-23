package com.example.asus.strokeanalyzer.View.Form;

/**
 * Klasa reprezentująca pojedynczą dopuszczalną odpowiedź na pytanie zamknięte.
 *
 * @author Marta Marciszewicz
 */

public class BulletedAnswer {

    private Integer id;
    private String text;

    /**
     * Konstruktor ustawiający odpowiednie pola klasy.
     *
     * @param id Id odpowiedzi (często Id powiedzi równoważne jest liczbie punktów przyznawanych za dane pytanie)
     * @param text treść pytania
     */
    public BulletedAnswer(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    /**
     * Metoda pobierająca treść odpowiedzi
     *
     * @return (String) treść odpowiedzi
     */
    public String getText() { return text; }

    /**
     * Metoda pobierająca Id odpowiedzi
     *
     * @return (Integer) Id odpowiedzi
     */
    public Integer getId()
    {
        return id;
    }
}
