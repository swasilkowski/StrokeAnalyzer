package com.example.asus.strokeanalyzer.Entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Klasa definiująca tabelę bazy danych zawierającą dane pacjentów.
 *
 * @author Stanisław Wasilkowski
 */

@Entity(tableName = "patient")
public class Patient {
    /**
     * Unikatowe id pacjenta stanowiące klucz główny w tabeli pacjentów.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * Imię pacjenta.
     */
    @ColumnInfo(name = "name")
    public String Name;

    /**
     * Nazwisko pacjenta.
     */
    @ColumnInfo(name = "surname")
    public String Surname;

    /**
     * Indywidualny numer pacjenta.
     */
    @ColumnInfo(name = "patient_number")
    public long PatientNumber;
}
