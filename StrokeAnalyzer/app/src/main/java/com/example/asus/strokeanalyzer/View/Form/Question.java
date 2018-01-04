package com.example.asus.strokeanalyzer.View.Form;

/**
 * Created by Asus on 04.12.2017.
 */

public interface Question {
    int BULLETED = 1;
    int DESCRIPTIVE = 2;
    int TRUEFALSE = 3;
    int NUMERIC = 4;

    int getListItemType();
}
