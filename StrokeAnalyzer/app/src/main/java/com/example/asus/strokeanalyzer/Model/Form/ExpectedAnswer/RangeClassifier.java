package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

import com.example.asus.strokeanalyzer.Model.Exceptions.NoAnswerException;

/**
 * Klasa pomocnicza umożliwiająca sprawdzenie przynależności danej liczby do przedziału liczbowego.
 * Oprócz wartości granicznych zakresu sprawdzanego przez klasyfikator, klasa może zawierać także liczbę
 * punktów, które przydzielane są w momencie, gdy podana do klasyfikatora liczba przynależy do zakresu.
 *
 * @author Stanisław Wasilkowski
 */

public class RangeClassifier
{
    final private double MinValue;
    final private double MaxValue;
    public int Points;


    /**
     * Metoda sprawdzająca, czy podana wartość jest większa bądź równa wartości minimalnej oraz mniejsza
     * bądź równa wartości maksymalnej.
     *
     * @param value wartość dla której funkcja sprawdza przynależność do zakresu klasyfikatora
     * @return true - jeżeli wartość należy do przedziału [MinValue;MaxValue]; false - w przeciwnym przypadku
     * @throws NoAnswerException podana wartość to wartość domyślna, może to oznaczać, że na to pytanie
     *                           nie została jeszcze udzielona odpowiedź
     */
    public boolean withinARange(double value) throws NoAnswerException {
        if(value==(-1))
            throw  new NoAnswerException();
        return !(value > MaxValue || value < MinValue);
    }

    /**
     * Konstruktor klasyfikatora pozwalający na ustalenie wartości minimalnej, maksymalnej oraz
     * liczby punktów dla zakresu.
     *
     * @param minValue dolna granica zakresu sprawdzanego przez klasyfikator
     * @param maxValue górna granica zakresu sprawdzanego przez klasyfikator
     * @param points liczba punktów przydzielanych w przypadku, gdy podana liczba przynależy do zakresu klasyfikatora
     */
    public RangeClassifier(double minValue, double maxValue, int points) {
        MinValue = minValue;
        MaxValue = maxValue;
        Points = points;
    }

    /**
     * Konstruktor klasyfikatora pozwalający na ustalenie wartości minimalnej oraz maksymalnej zakresu.
     *
     * @param minValue dolna granica zakresu sprawdzanego przez klasyfikator
     * @param maxValue górna granica zakresu sprawdzanego przez klasyfikator
     */
    public RangeClassifier(double minValue, double maxValue) {
        MinValue = minValue;
        MaxValue = maxValue;
    }
}