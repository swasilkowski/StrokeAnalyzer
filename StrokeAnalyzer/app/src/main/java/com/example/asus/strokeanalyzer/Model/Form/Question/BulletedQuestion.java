package com.example.asus.strokeanalyzer.Model.Form.Question;

import java.util.Map;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link Question} reprezentująca pytanie zamknięte,
 * które posiada zbiór możliwych odpowiedzi.
 *
 * @author Marta Marciszewicz
 */

public class BulletedQuestion extends Question {

    //key - value of a particular answer, value - description for this answer to be printed
    //possibleValues - field containing all of the values user may pick
    final private Map<Integer, String> possibleValues;

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz możliwe odpowiedi na pytanie.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param answers mapa zawierająca możliwe odpowiedzi na dane pytanie powiązane z pewną daną liczbową
     */
    public BulletedQuestion(int id, String text, Map<Integer, String> answers)
    {
        super(id, text);
        possibleValues = answers;
    }

    /**
     * Metoda zwracająca treść wybranej odpowiedzi.
     *
     * @param ind liczba charakteryzująca daną odpowiedż
     * @return tekst odpowiedzi
     */
    public String GetValueDescription(int ind)
    {
        return possibleValues.get(ind);
    }

    /**
     * Metoda zwracająca wszystkie możliwe odpowiedzi na dane pytanie.
     *
     * @return mapa możliwych do wyboru odpowiedzi na pytanie powiązanych z pewną daną liczbową
     */
    public Map<Integer, String> GetPosiibleValues()
    {
        return possibleValues;
    }

}
