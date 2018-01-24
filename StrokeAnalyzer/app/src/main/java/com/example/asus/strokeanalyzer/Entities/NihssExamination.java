package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Klasa definiująca tabelę bazy danych zawierającą badania pacjenta w skali NIHSS
 *
 * @author Stanisław Wasilkowski
 */

@Entity(foreignKeys = @ForeignKey(
            entity = Patient.class,
            parentColumns = "id",
            childColumns = "patient_id"),
        indices = @Index("patient_id"),
        tableName = "nihss_form")
public class NihssExamination {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "patient_id")
    public int PatientId;

    @ColumnInfo(name = "added_on")
    public long addedOn;

    @ColumnInfo(name = "dominant_hemisphere")
    public double DominantHemisphere;

    @ColumnInfo(name = "symptoms_side")
    public double SymptomsSide;

    public double Question1;
    public double Question2;
    public double Question3;
    public double Question4;
    public double Question5;
    public double Question6;
    public double Question7;
    public double Question8;
    public double Question9;
    public double Question10;
    public double Question11;
    public double Question12;
    public double Question13;
    public double Question14;
    public double Question15;
}
