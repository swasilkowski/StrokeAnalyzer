package com.example.asus.strokeanalyzer.View.Form;

/**
 * Interfejs do rozróżniania rodzaju pytania w RecyclerView.
 *
 * @author Marta Marciszewicz
 */

public interface Question {
    int BULLETED = 1;
    int DESCRIPTIVE = 2;
    int TRUEFALSE = 3;
    int NUMERIC = 4;

    int getListItemType();
}
