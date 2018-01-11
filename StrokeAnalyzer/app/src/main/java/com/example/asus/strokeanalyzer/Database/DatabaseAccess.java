package com.example.asus.strokeanalyzer.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;

/**
 * Klasa umożliwiająca dostęp do bazy danych
 * Zawiera obiekt bazy danych oraz metody pozwalające na jej zainicjowanie
 *
 * @author Stanisław Wasilkowski
 */

public final class DatabaseAccess {
    public StrokeAnalyzerDatabase database;
    private static DatabaseAccess instance;

    /**
     * Prywatny konstruktor wywoływany przez metodę getInstance inicjalizujący obiekt bazy danych
     *
     * @param context kontekst aplikacji wymagany do zbudowania obiektu bazy danych
     */
    private DatabaseAccess(Context context) {
        database = Room.databaseBuilder(context, StrokeAnalyzerDatabase.class,
                "stroke_analyzer_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    /**
     * Metoda tworząca i zwracająca nową instancję klasy dostępu do bazy danych. Powoduje
     * zainicjowanie bazy danych
     *
     * @param context kontekst aplikacji wymagany do zbudowania obiektu bazy danych
     * @return (DatabaseAccess) instancja klasy zapewniająca dostęp do bazy danych
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Metoda zwracająca istniejącą instację klasy dostępu do bazy danych.
     *
     * @return (DatabaseAccess) instancja klasy zapewniająca dostęp do bazy danych
     * @throws SQLiteCantOpenDatabaseException nie istnieje instancja klasy oraz baza danych nie
     *                                          została utworzona
     */
    public static DatabaseAccess getInstance() throws SQLiteCantOpenDatabaseException {
        if (instance == null) {
            throw new SQLiteCantOpenDatabaseException();
        }
        return instance;
    }
}
