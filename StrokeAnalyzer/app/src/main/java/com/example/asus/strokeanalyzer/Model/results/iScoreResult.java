package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy skali iScore.
 * Zawiera pola przechowujące liczbę punktów w skali iScore dla prognozy 30-dniowej oraz 1-rocznej,
 * jak również pola przechowujące prawdopodobieństwo zgonu pacjenta w przeciągu 30 dni oraz 1 roku
 * od wystąpienia udaru.
 *
 * @author Stanisław Wasilkowski
 */

public final class iScoreResult {
    /**
     * Procentowe przewidywanie dotyczące zgonu pacjenta w przeciągu 30 dni od wystąpienia udaru.
     */
    public double PrognosisFor30Days;
    /**
     * Procentowe przewidywanie dotyczące zgonu pacjenta w przeciągu 1 roku od wystąpienia udaru.
     */
    public double PrognosisFor1Year;
    /**
     * Liczba punktów w skali iSoore uzyskana dla prognozy 30-dniowej.
     */
    public int ScoreFor30Days;
    /**
     * Liczba punktów w skali iScore uzyskana dla prognozy 1-rocznej.
     */
    public int ScoreFor1Year;
    /**
     * Opis otrzymanego wyniku dla prognozy 30-dniowej.
     */
    public String PrognosisFor30DaysDescription;
    /**
     * Opis otrzymanego wyniku dla prognozy 1-rocznej.
     */
    public String PrognosisFor1YearDescription;
}
