package com.example.asus.strokeanalyzer.Entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by S. Wasilkowski on 2017-12-04.
 */

@Entity(tableName = "patient")
public class Patient {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String Name;

    @ColumnInfo(name = "surname")
    public String Surname;

    @ColumnInfo(name = "patient_name")
    public int PatientNumber;
}
