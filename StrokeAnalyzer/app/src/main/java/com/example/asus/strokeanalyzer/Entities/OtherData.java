package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Entity(foreignKeys = @ForeignKey(
        entity = Patient.class,
        parentColumns = "id",
        childColumns = "patient_id"),
        tableName = "other_data")
public class OtherData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "patient_id")
    public int patientId;

    @ColumnInfo(name = "data_type")
    public int dataType;

    @ColumnInfo(name = "answer_id")
    public int answerId;

    @ColumnInfo(name = "numeric_data")
    public double numericData;

    @ColumnInfo(name = "text_data")
    public String textData;

    @ColumnInfo(name = "boolean_data")
    public Boolean booleanData;
}
