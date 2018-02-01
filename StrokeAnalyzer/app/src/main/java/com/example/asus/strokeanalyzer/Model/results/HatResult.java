package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy skali HAT.
 * Zawiera pole przechowujące liczbę punktów w skali HAT oraz pola przechowujące prawdopodobieństwo
 * wystąpienia krwotoku śródczaszkowego oraz śmiertelnego krwotoku śródczaszkowego.
 *
 * @author Stanisław Wasilkowski
 */

public final class HatResult {
    public int Score;
    public int RiskOfSymptomaticICH;
    public int RiskOfFatalICH;
}
