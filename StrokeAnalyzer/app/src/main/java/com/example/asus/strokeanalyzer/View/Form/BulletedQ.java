package com.example.asus.strokeanalyzer.View.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie zamknięte
 * posiadające zbiór możliwych odpowiedzi.
 *
 * @author Marta Marciszewicz
 */

public class BulletedQ implements Question{

    final private int id;
    final private String text;
    private int answerId;
    final private List<BulletedAnswer> answers;

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz możliwe odpowiedi na pytanie.
     *
     * @param id id pytania
     * @param text treśc pytania
     * @param possibleValues mapa zawierająca możliwe odpowiedzi na dane pytanie powiązane z pewną daną liczbową
     */
    public BulletedQ(int id ,String text, Map<Integer, String> possibleValues)
    {
        this.id = id;
        this.text = text;
        answers = new ArrayList<>();
        answerId = -1;
        for(Integer key: possibleValues.keySet())
        {
            answers.add(new BulletedAnswer(key,possibleValues.get(key)));
        }
    }

    /**
     * Metoda pobierająca treść pytania.
     *
     * @return treść pytania
     */
    public String getText() { return text; }

    /**
     * Metoda pobierająca id pytania.
     *
     * @return id pytania
     */
    public int getID(){return id;}

    /**
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika.
     *
     * @param answer dana liczbowa powiązana z wybraną przez użytkownika odpowiedzią
     */
    public void setAnswer(int answer)
    {
        answerId = answer;
    }

    /**
     * Metoda zwracająca daną liczbową powiązaną z ustawioną odpowiedzią na pytanie.
     *
     * @return dana liczbowa odpowiadająca ustawionej odpowiedzi na pytanie
     */
    public int getAnswer(){return answerId;}

    /**
     * Metoda zwracająca listę wszystkich możliwych odpowiedzi na pytanie.
     *
     * @return lista możliwych odpowiedzi
     */
    public List<BulletedAnswer> getAnswers()
    {
        return answers;
    }

    /**
     * Metoda zwracająca typ pytania jako wartość z interfejsu {@link Question}.
     *
     * @return typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.BULLETED;
    }
}
