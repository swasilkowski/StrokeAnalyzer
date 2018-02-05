package com.example.asus.strokeanalyzer.View.Form;

import com.example.asus.strokeanalyzer.Model.EnumValues.QuestionStrength;

/**
 * Interfejs do rozróżniania rodzaju pytania w RecyclerView.
 *
 * @author Marta Marciszewicz
 */

interface Question {
    int BULLETED = 1;
    int DESCRIPTIVE = 2;
    int TRUEFALSE = 3;
    int NUMERIC = 4;

    /**
     * Metoda zwracająca rodzaj pytania.
     *
     * @return rodzaj pytania
     */
    int getListItemType();

    /**
     * Metoda zwracająca wagę pytania.
     *
     * @return waga pytania
     */
    QuestionStrength getStrength();
}
