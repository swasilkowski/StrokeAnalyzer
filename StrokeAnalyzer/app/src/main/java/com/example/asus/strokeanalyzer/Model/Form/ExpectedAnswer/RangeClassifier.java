package com.example.asus.strokeanalyzer.Model.Form.ExpectedAnswer;

/**
 * Klasa pomocnicza umożliwiająca sprawdzenie przynależności danej liczby do zakresu
 * Oprócz wartości granicznych zakresu sprawdzanego przez klasyfikator, klasa może zawierać także liczbę
 * punktów, które przydzielane są w momencie, gdy podana do klasyfikatora liczba przynależy do zakresu
 *
 * @author Stanisław Wasilkowski
 */

public class RangeClassifier
{
    private double MinValue;
    private double MaxValue;
    public int Points;

    /**
     * Sprawdzenie, czy podana wartość jest większa bądź równa wartości minimalnej oraz mniejsza
     * bądź równa wartości maksymalnej.
     * @param value wartość dla której funkcja sprawdza przynależność do zakresu klasyfikatora
     * @return true - jeżeli wartość należy do przedziału [MinValue;MAxValue]; false - w przeciwnym przypadku
     */
    public boolean withinARange(double value)
    {
        if(value>MaxValue || value<MinValue)
            return false;
        return true;
    }

    /**
     * Konstruktor klasyfikatora pozwalający na ustalenie liczby punktów. Ustawia pola obiektu
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
     * Konstruktor klasyfikatora. Ustawia pola obiektu
     * @param minValue dolna granica zakresu sprawdzanego przez klasyfikator
     * @param maxValue górna granica zakresu sprawdzanego przez klasyfikator
     */
    public RangeClassifier(double minValue, double maxValue) {
        MinValue = minValue;
        MaxValue = maxValue;
    }
}