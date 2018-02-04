package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy skali HAT.
 * Zawiera pole przechowujące liczbę punktów w skali HAT oraz pola przechowujące prawdopodobieństwo
 * wystąpienia krwotoku śródczaszkowego oraz śmiertelnego krwotoku śródczaszkowego.
 *
 * @author Stanisław Wasilkowski
 */

public final class HatResult {
    /**
     * Liczba punktów uzyskana w skali HAT.
     */
    public int Score;
    /**
     * Procentowe prawdopodobieństwo wystąpienia krwotoku śródmózgowego u pacjenta.
     */
    public int RiskOfSymptomaticICH;
    /**
     * Procentowe prawdopodobieństwo wystąpienia śmiertelnego krwotoku śródmózgowego u pacjenta.
     */
    public int RiskOfFatalICH;
}
