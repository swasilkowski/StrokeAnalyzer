package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Klasa definiująca tabelę w bazie danych zawierającą odpowiedzi na pytania formularzy aplikacji.
 *
 * @author Stanisław Wasilkowski
 */

@Entity(foreignKeys = @ForeignKey(
            entity = Patient.class,
            parentColumns = "id",
            childColumns = "patient_id"),
        indices = @Index("patient_id"),
        tableName = "other_data")
public class OtherData {
    /**
     * Unikatowe id wpisu do tabeli danych pacjenta, stanowi klucz główny.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * Id pacjenta, którego dotyczy dana informacja.
     */
    @ColumnInfo(name = "patient_id")
    public int patientId;

    /**
     * Id typu udzielonej przez użytkownika odpowiedzi na pytanie. 0 - odpowiedź jest wartością numeryczną; 1 - odpowiedź
     * jest wartością tekstową; 2 - odpowiedź jest wartością typu prawda/fałsz
     */
    @ColumnInfo(name = "data_type")
    public int dataType;

    /**
     * Id pytania, którego dotyczy odpowiedź.
     */
    @ColumnInfo(name = "answer_id")
    public int answerId;

    /**
     * Wartość numeryczna odpowiedzi ustawiana w przypadku, gdy {@code dataType} przyjmuje wartość 0.
     */
    @ColumnInfo(name = "numeric_data")
    public double numericData;

    /**
     * Wartość tekstowa odpowiedzi ustawiana w przypadku, gdy {@code dataType} przyjmuje wartość 1.
     */
    @ColumnInfo(name = "text_data")
    public String textData;

    /**
     * Wartość typu prawda/fałsz odpowiedzi ustawiana w przypadku, gdy {@code dataType} przyjmuje wartość 2.
     */
    @ColumnInfo(name = "boolean_data")
    public Boolean booleanData;
}
