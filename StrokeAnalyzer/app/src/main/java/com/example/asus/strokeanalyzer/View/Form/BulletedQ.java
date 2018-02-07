package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

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

    /**
     * Id pytania.
     */
    final private int id;
    /**
     * Treść pytania.
     */
    final private String text;
    /**
     * Id odpowiedzi wybranej przez użytkownika.
     */
    private int answerId;
    /**
     * Lista wszystkich możliwych odpowiedzi na pytanie.
     */
    final private List<BulletedAnswer> answers;
    /**
     * Waga pytania wykorzystywana do kolorowania tekstu treści pytania.
     */
    private final QuestionStrength strength;


    /**
     * Konstruktor ustawiający id pytania, jego treść, wagę oraz możliwe odpowiedi na pytanie.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param strength waga pytania
     * @param possibleValues mapa zawierająca możliwe odpowiedzi na dane pytanie powiązane z pewną daną liczbową
     */
    public BulletedQ(int id ,String text, QuestionStrength strength, Map<Integer, String> possibleValues)
    {
        this.id = id;
        this.text = text;
        answers = new ArrayList<>();
        answerId = -1;
        this.strength = strength;
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
