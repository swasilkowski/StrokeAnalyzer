package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 * Klasa reprezentująca pytanie, na które odpowiedź udzielana jest w postaci wprowadzenia przez użytkownika
 * liczby.
 *
 * @author Marta Marciszewicz
 */

public class NumericQ implements Question {
    //id pytania
    final private int id;
    //tresc pytania
    final private String text;
    //wartosc odpowiedzi
    private double answer;
    private boolean answerSet;
    final private RangeClassifier range;

    /**
     * Konstruktor ustawiający odpowiednie pola klasy.
     *
     * @param id Id pytania
     * @param text treść pytania
     * @param _range przedział liczbowy, do którego powinna należeć odpowiedź udzielana przez
     *               użytkownika
     */
    public NumericQ(int id ,String text, RangeClassifier _range)
    {
        this.id = id;
        this.text = text;
        this.answerSet = false;
        this.range = _range;
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
     * @param ans wartość odpowiedzi wprowadzonej przez użytkwonika
     * @return (boolean) true - jeżeli odpowiedź jest dopuszczalna i została ustawiona,
     *          false - jeżeli odpowiedź nie może zostać ustawiona
     */
    public boolean setAnswer(double ans)
    {
        try
        {
            if(range.withinARange(ans))
            {
                answer = ans;
                answerSet = true;
                return true;
            }
            else
                return false;
        }
        catch (NoAnswerException e){
            return false;
        }
    }

    /**
     * Metoda czyszcząca odpowiedź ustawioną przez użytkownika
     */
    public void clearAnswer()
    {
        answer =0;
        answerSet = false;
    }

    /**
     * Metoda zwracająca odpowiedź na pytanie.
     *
     * @return (double) wartość odpowiedzi na pytanie
     */
    public double getAnswer() { return answer;}

    /**
     * Metoda pobierająca Id pytania
     *
     * @return (int) Id pytania
     */
    public int getID() {return id;}

    /**
     * Metoda sprawdzająca, czy odpowiedź została jużustawiona
     *
     * @return (boolean) true - odpowiedź jest ustawiona; false - odpowiedź nie była jeszce ustawiana
     */
    public boolean getAnswerSet() {return answerSet;}

    /**
     * Metoda zwracająca typ pytania z interfejsu {@link Question}
     *
     * @return (int) typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.NUMERIC;
    }
}
