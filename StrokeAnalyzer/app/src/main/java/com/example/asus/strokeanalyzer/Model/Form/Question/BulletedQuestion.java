package com.example.asus.strokeanalyzer.Model.Form.Question;

import java.util.Map;

/**
 * Klasa reprezentująca pytanie, które zawiera zbiór gotowych odpowiedzi
 * Przechowuje ono mapę łączącą pewną wartość liczbową z treścią odpowiedzi. Wartość ta może stanowić
 * liczbę punktów przyznawanych odpowiedzi bądź jej Id w zależności od pytania.
 *
 * @author Marta Marciszewicz
 */

public class BulletedQuestion extends Question {

    //key - value of a particular answer, value - description for this answer to be printed
    //possibleValues - field containing all of the values user may pick
    private Map<Integer, String> possibleValues;

    /**
     * Konstruktor ustawiający ID pytania, jego treść oraz możliwe odpowiedi na pytanie
     * @param id ID pytania
     * @param text treść pytania
     * @param answers mapa zawierająca możliwe odpowiedzi na dane pytanie
     */
    public BulletedQuestion(int id, String text, Map<Integer, String> answers)
    {
        super(id, text);
        possibleValues = answers;
    }

    /**
     * Metoda wracająca treść danej odpowiedzi
     * @param ind liczba charakteryzująca daną odpowiedż
     * @return (String) tekst odpowiedzi
     */
    public String GetValueDescription(int ind)
    {
        return possibleValues.get(ind);
    }

    /**
     * Metoda zwracająca możliwe odpowiedzi na dane pytnaie
     * @return
     * {@code Map<Integer, String>} mapa możliwych do wyboru odpowiedzi na pytanie
     */
    public Map<Integer, String> GetPosiibleValues()
    {
        return possibleValues;
    }

}
