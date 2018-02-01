package com.example.asus.strokeanalyzer.View.Form;

/**
 * Klasa reprezentująca pojedynczą dopuszczalną odpowiedź na pytanie zamknięte.
 *
 * @author Marta Marciszewicz
 */

class BulletedAnswer {

    final private Integer id;
    final private String text;

    /**
     * Konstruktor ustawiający daną liczbową przypisaną do odpowiedzi oraz treść odpowiedzi.
     *
     * @param id dana liczbowa powiązana z odpowiedzią (może to być przykładowo jej id, bądź liczba punktów za odpowiedź)
     * @param text treść pytania
     */
    public BulletedAnswer(int id ,String text)
    {
        this.id = id;
        this.text = text;
    }

    /**
     * Metoda pobierająca treść odpowiedzi.
     *
     * @return treść odpowiedzi
     */
    public String getText() { return text; }

    /**
     * Metoda pobierająca daną liczbową powiązaną z odpowiedzią.
     *
     * @return dana liczbowa powiązana z odpowiedzią
     */
    public Integer getId()
    {
        return id;
    }
}
