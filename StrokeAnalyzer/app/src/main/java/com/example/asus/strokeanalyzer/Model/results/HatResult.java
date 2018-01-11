package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy dotyczącej skali Hat.
 * Zawiera pole przechowujące liczbę punktów w skali Hat oraz pola przechowujące prawdopodobieństwo
 * wystąpienia krwotoku śródczaszkowego oraz śmiertelnego krwotoku śródczaszkowego.
 *
 * @author Stanisław Wasilkowski
 */

public final class HatResult {
    public int Score;
    public int RiskOfSymptomaticICH;
    public int RiskOfFatalICH;
}
