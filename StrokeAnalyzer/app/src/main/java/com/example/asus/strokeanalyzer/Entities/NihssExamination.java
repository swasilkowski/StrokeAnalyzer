package com.example.asus.strokeanalyzer.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Entity(foreignKeys = @ForeignKey(
            entity = Patient.class,
            parentColumns = "id",
            childColumns = "patient_id"),
        tableName = "nihss_form")
public class NihssExamination {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "patient_id")
    public int PatientId;

    @ColumnInfo(name = "added_on")
    public int addedOn;

    public int Question1;
    public int Question2;
    public int Question3;
    public int Question4;
    public int Question5;
    public int Question6;
    public int Question7;
    public int Question8;
    public int Question9;
    public int Question10;
    public int Question11;

}
