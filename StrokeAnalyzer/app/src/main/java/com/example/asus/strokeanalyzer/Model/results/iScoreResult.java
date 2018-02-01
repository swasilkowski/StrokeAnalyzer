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
    public double PrognosisFor30Days;
    public double PrognosisFor1Year;
    public int ScoreFor30Days;
    public int ScoreFor1Year;
    public String PrognosisFor30DaysDescription;
    public String PrognosisFor1YearDescription;
}
