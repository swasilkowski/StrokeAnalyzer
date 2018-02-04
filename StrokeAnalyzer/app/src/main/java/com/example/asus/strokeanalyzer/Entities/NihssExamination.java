package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Klasa definiująca tabelę bazy danych zawierającą badania pacjenta w skali NIHSS.
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
    /**
     * Unikatowe id badania w skali NIHSS stanowiące klucz główny w tabeli badań skali.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * Id pacjenta, którego dotyczy badanie.
     */
    @ColumnInfo(name = "patient_id")
    public int PatientId;

    /**
     * Data wykonania badania.
     */
    @ColumnInfo(name = "added_on")
    public long addedOn;

    /**
     * Informacja wskazująca, która półkula mózgu u pacjenta jest dominująca.
     */
    @ColumnInfo(name = "dominant_hemisphere")
    public double DominantHemisphere;

    /**
     * Informacja dotycząca strony ciała, po której występują objawy udaru.
     */
    @ColumnInfo(name = "symptoms_side")
    public double SymptomsSide;

    /**
     * Pytanie 1a skali NIHSS.
     */
    public double Question1;
    /**
     * Pytanie 1b skali NIHSS.
     */
    public double Question2;
    /**
     * Pytanie 1c skali NIHSS.
     */
    public double Question3;
    /**
     * Pytanie 2 skali NIHSS.
     */
    public double Question4;
    /**
     * Pytanie 3 skali NIHSS.
     */
    public double Question5;
    /**
     * Pytanie 4 skali NIHSS.
     */
    public double Question6;
    /**
     * Pytanie 5a skali NIHSS.
     */
    public double Question7;
    /**
     * Pytanie 5b skali NIHSS.
     */
    public double Question8;
    /**
     * Pytanie 6a skali NIHSS.
     */
    public double Question9;
    /**
     * Pytanie 6b skali NIHSS.
     */
    public double Question10;
    /**
     * Pytanie 7 skali NIHSS.
     */
    public double Question11;
    /**
     * Pytanie 8 skali NIHSS.
     */
    public double Question12;
    /**
     * Pytanie 9 skali NIHSS.
     */
    public double Question13;
    /**
     * Pytanie 10 skali NIHSS.
     */
    public double Question14;
    /**
     * Pytanie 11 skali NIHSS.
     */
    public double Question15;
}
