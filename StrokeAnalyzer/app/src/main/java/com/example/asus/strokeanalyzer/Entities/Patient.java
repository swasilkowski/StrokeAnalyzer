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
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String Name;

    @ColumnInfo(name = "surname")
    public String Surname;

    @ColumnInfo(name = "patient_number")
    public long PatientNumber;
}
