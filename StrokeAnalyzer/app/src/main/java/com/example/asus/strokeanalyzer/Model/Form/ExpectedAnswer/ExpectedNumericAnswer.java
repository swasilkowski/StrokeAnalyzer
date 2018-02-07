package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa stanowiąca rozszerzenie klasy {@link ExpectedAnswer} reprezentująca oczekiwaną odpowiedź będącą liczbą.
 * Zawiera wartość prawidłowej odpowiedzi typu Double oraz listę klasyfikatorów przechowującą zakresy,
 * w których może znajdować się udzielona odpowiedź.
 *
 * @author Stanisław Wasilkowski
 */

public class ExpectedNumericAnswer extends ExpectedAnswer {

    /**
     * Lista klasyfikatorów możliwych zakresów odpowiedzi.
     */
    final public List<RangeClassifier> Ranges;

    /**
     * Konstruktor ustawiający id pytania, którego dotyczy odpowiedź.
     *
     * @param questionId id pytania, którego dotyczy odpowiedź
     */
    public ExpectedNumericAnswer(int questionId)
    {
        super(questionId);
        Ranges = new LinkedList<>();
    }

    /**
     * Metoda wyliczająca przyznane punkty w zależności od wartości odpowiedzi podanej jako parametr.
     *
     * Uwaga: Jeżeli w obiekcie ustawiona jest pojedyncza poprawna wartość to metoda przyznaje 1 punkt za zgodność
     * podanych wartości. W przeciwnym przypadku sprawdzana jest przynależność odpowiedzi do któregokolwiek
     * z możliwych zakresów. Jeżeli odpowiedź należy do danego zakresu zwracana jest
     * liczba punktów jego klasyfikatora.
     *
     * @param value wartość odpowiedzi udzielonej na pytanie
     * @return liczba punktów uzyskanych dla podanej odpowiedzi
     * @throws NoAnswerException podana wartość to wartość domyślna, może to oznaczać, że na to pytanie
     *                           nie została jeszcze udzielona odpowiedź
     */
    public int CalculatePoints(double value) throws NoAnswerException {
        if (Ranges.size() > 0)
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
     * Metoda sprawdzająca poprawność wartości odpowiedzi podanej jako parametr.
     *
     * Uwaga: Jeżeli w obiekcie ustawiona jest pojedyncza poprawna wartość to metoda zwraca wynik zgodności
     * podanych wartości. W przeciwnym przypadku sprawdzana jest przynależność odpowiedzi do któregokolwiek
     * z możliwych zakresów.
     *
     * @param value wartość odpowiedzi udzielonej na pytanie
     * @return true - jeżeli wartość odpowiedzi jest zgodna z oczekiwaną odpowiedzią bądź należy do jednego z
     *          możliwych zakresów; false - jeżeli odpowiedź nie jest zgodna z oczekiwaną odpowiedzią,
     *          ani nie przynależy do żadnego z zakresów
     */
    public boolean CheckCorrectness(double value) throws NoAnswerException {
        if (Ranges.size() > 0)
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
