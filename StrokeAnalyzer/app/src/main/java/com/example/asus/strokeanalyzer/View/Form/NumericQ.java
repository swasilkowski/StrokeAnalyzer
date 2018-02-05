package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;
import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;
import com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer.RangeClassifier;

/**
 * Klasa implementująca interfejs {@link Question}, która reprezentuje pytanie, na które odpowiedź
 * udzielana jest w postaci wprowadzenia przez użytkownika danej liczbowej.
 *
 * @author Marta Marciszewicz
 */

public class NumericQ implements Question {
    //id pytania
    /**
     * Id pytania.
     */
    final private int id;
    //tresc pytania
    /**
     * Treść pytania.
     */
    final private String text;
    //wartosc odpowiedzi
    /**
     * Wartość odpowiedzi udzielonej przez użytkownika.
     */
    private double answer;
    /**
     * Wartość mówiąca o tym, czy użytkownik udzielił już odpowiedzi na pytanie.
     */
    private boolean answerSet;
    /**
     * Zakres dopuszczalnej wielkości odpowiedzi wprowadzanej przez użytkownika.
     */
    final private RangeClassifier range;
    /**
     * Waga pytania wykorzystywana do kolorowania tekstu treści pytania.
     */
    private QuestionStrength strength;

    /**
     * Konstruktor ustawiający id pytania, jego treść oraz klasyfikator, określający przedział dopuszczalnych
     * wartości dla wprowadzanej odpowiedzi.
     *
     * @param id id pytania
     * @param text treść pytania
     * @param _range przedział liczbowy, do którego powinna należeć odpowiedź udzielana przez
     *               użytkownika
     */
    public NumericQ(int id ,String text, QuestionStrength strength, RangeClassifier _range)
    {
        this.id = id;
        this.text = text;
        this.answerSet = false;
        this.range = _range;
        this.strength = strength;
    }

    /**
     * Metoda pozwalająca na pobranie treści pytania.
     *
     * @return treść pytania
     */
    public String getText() { return text; }

    /**
     * Metoda pozwalająca na ustawienie odpowiedzi wprowadzonej przez użytkownika.
     *
     * @param ans wartość odpowiedzi wprowadzonej przez użytkwonika
     * @return true - jeżeli odpowiedź jest dopuszczalna i została ustawiona;
     *          false - jeżeli odpowiedź nie może zostać ustawiona, ponieważ nie należy do przedziału
     *          dopuszczalnych wartości
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
     * Metoda czyszcząca odpowiedź ustawioną przez użytkownika.
     */
    public void clearAnswer()
    {
        answer =0;
        answerSet = false;
    }

    /**
     * Metoda zwracająca ustawioną odpowiedź na pytanie.
     *
     * @return wartość ustawionej odpowiedzi na pytanie
     */
    public double getAnswer() { return answer;}

    /**
     * Metoda pobierająca id pytania.
     *
     * @return id pytania
     */
    public int getID() {return id;}

    /**
     * Metoda sprawdzająca, czy odpowiedź na pytanie jest ustawiona.
     *
     * @return true - odpowiedź jest ustawiona; false - odpowiedź nie była jeszcze ustawiana
     */
    public boolean getAnswerSet() {return answerSet;}

    /**
     * Metoda zwracająca typ pytania jako wartość z interfejsu {@link Question}.
     *
     * @return typ pytania
     */
    @Override
    public int getListItemType() {
        return Question.NUMERIC;
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
