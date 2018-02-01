package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy skali DRAGON.
 * Zawiera pole przechowujące liczbę punktów w skali DRAGON oraz pola przechowujące prawdopodobieństwo
 * powodzenia i niepowodzenia leczenia trombolitycznego.
 *
 * @author Stanisław Wasilkowski
 */

public final class DragonResult {
    public int Score;
    public int GoodOutcomePrognosis;
    public int MiserableOutcomePrognosis;
}
