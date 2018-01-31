package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca oczekiwaną odpowiedź będącą liczbą.
 * Zawiera wartość prawidłowej odpowiedzi typu Double oraz listę klasyfikatorów przechowującą zakresu,
 * w których może znajdować się udzielona odpowiedź.
 * <p>
 * Klasa zawiera metodę sprawdzającą, czy podana wartość odpowiedzi jest zgodna z poprawną odpowiedzią
 * bądź należy do któregoś z zakresów, oraz metodę wyznaczającą liczbę przyznanych punktów dla wartości
 * odpowiedzi użytkownika
 *
 * @author Stanisław Wasilkowski
 */

public class ExpectedNumericAnswer extends ExpectedAnswer {

    //null value indicates that particular parameter should not be taken into consideration
    //if correctValue is considered we give 1 point
    private Double CorrectValue;
    public List<RangeClassifier> Ranges;

    /**
     * Konstruktor ustawiający ID pytania, którego dotyczy odpowiedź oraz inicjujący listę przedziałów wartości
     * @param questionId ID pytania, którego dotyczy odpowiedź
     */
    public ExpectedNumericAnswer(int questionId)
    {
        super(questionId);
        Ranges = new LinkedList<>();
    }

    /**
     * Klasa wyliczająca przyznane punkty w zależności od wartości odpowiedzi wprowadząnej jako parametr
     * Jeżeli w obiekcie ustawiona jest wartość pola CorrectValue to metoda przyznaje 1 punkt za zgodność
     * podanych wartości. W przeciwnym przypadku sprawdzana jest przynależność odpowiedzi do któregokolwiek
     * z zakresów znajdujących się na liście Ranges. Jeżeli odpowiedź należy do danego zakresu zwracana jest
     * liczba punktów jego klasyfikatora.
     * @param value wartość odpowiedzi udzielonej na pytanie, któego dotyczy obiekt tej klasy
     * @return (int) liczba punktów uzyskanych dla podanej odpowiedzi
     */
    public int CalculatePoints(double value) throws NoAnswerException {
        if(CorrectValue!= null && CorrectValue.equals(value))
            return 1;
        else if(Ranges.size() > 0)
        {
            for(RangeClassifier r:Ranges)
            {
                    if(r.withinARange(value))
                        return r.Points;


            }
        }
        return 0;
    }

    /**
     * Klasa sprawdzająca poprawność wartości odpowiedzi wprowadząnej jako parametr
     * Jeżeli w obiekcie ustawiona jest wartość pola CorrectValue to metoda zwraca true za zgodność
     * podanych wartości. W przeciwnym przypadku sprawdzana jest przynależność odpowiedzi do któregokolwiek
     * z zakresów znajdujących się na liście Ranges. Jeżeli odpowiedź należy do jednego z zakresów zwracana jest
     * wartość true. Jeżeli odpowiedź nie jest zgodna z odpowiedzią CorrectValue ani nie przynależy
     * do żadnego z zakresów to zwracana jest wartość false.
     * @param value wartość odpowiedzi udzielonej na pytanie, któego dotyczy obiekt tej klasy
     * @return (boolean) true - jeżeli wartość odpowiedzi jest zgodna z oczekiwaną odpowiedzią; false
     *          - w przeciwnym przypadku
     */
    public boolean CheckCorrectness(double value) throws NoAnswerException {
        if(CorrectValue!= null && CorrectValue.equals(value))
            return true;
        else if(Ranges.size() > 0)
        {
            for(RangeClassifier r:Ranges)
            {
                    if(r.withinARange(value))
                        return true;
            }
        }
        return false;
    }


}
