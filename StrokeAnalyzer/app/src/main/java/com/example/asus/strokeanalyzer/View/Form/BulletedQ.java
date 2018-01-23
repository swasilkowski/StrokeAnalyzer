package com.example.asus.strokeanalyzer.View.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa reprezentująca pytanie zamknięte, na które odpowiedź należy do konkretnego zbioru wartości.
 *
 * @author Marta Marciszewicz
 */

public class BulletedQ implements Question{

    private int id;
    private String text;
    private int answerId;
    private List<BulletedAnswer> answers;

    /**
     * Konstruktor ustawiający odpowiednie pola klasy.
     *
     * @param id Id pytania
     * @param text treśc pytania
     * @param possibleValues mapa zawierająca Id odpowiedzi oraz jej treść
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
    public int getID(){return id;}

    /**
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika
     *
     * @param answer Id odpowiedzi wybranej przez użytkownika
     */
    public void setAnswer(int answer)
    {
        answerId = answer;
    }

    /**
     * Metoda zwracająca odpowiedź na pytanie.
     *
     * @return (int) Id odpowiedzi na pytanie
     */
    public int getAnswer(){return answerId;}

    /**
     * Metoda zwracająca listę wszystkich możliwych odpowiedzi na pytanie
     *
     * @return
     * {@code List<BulletedAnswer>} lista możliwych odpowiedzi
     */
    public List<BulletedAnswer> getAnswers()
    {
        return answers;
    }

    /**
     * Metoda zwracająca typ pytania z interfejsu {@link Question}
     *
     * @return (int) typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.BULLETED;
    }
}
