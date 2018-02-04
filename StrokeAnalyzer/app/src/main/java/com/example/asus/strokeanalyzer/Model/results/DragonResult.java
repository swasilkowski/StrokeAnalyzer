package com.example.asus.strokeanalyzer.Model.results;

/**
 * Klasa przechowująca wynik analizy skali DRAGON.
 * Zawiera pole przechowujące liczbę punktów w skali DRAGON oraz pola przechowujące prawdopodobieństwo
 * powodzenia i niepowodzenia leczenia trombolitycznego.
 *
 * @author Stanisław Wasilkowski
 */

public final class DragonResult {
    /**
     * Liczba punktów uzyskana w skali DRAGON.
     */
    public int Score;
    /**
     * Procentowe prawdopodobieństwo pozytywnego rezultatu zastosowania leczenia trombolitycznego.
     */
    public int GoodOutcomePrognosis;
    /**
     * Procentowe prawdopodobieństwo niepomyślnego rezultatu zastosowania leczenia trombolitycznego.
     */
    public int MiserableOutcomePrognosis;
}
