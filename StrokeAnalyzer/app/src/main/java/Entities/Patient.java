package Entities;


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
    public int ID;

    @ColumnInfo(name = "name")
    public String Name;

    @ColumnInfo(name = "surname")
    public String Surname;

    @ColumnInfo(name = "patient_name")
    public int PatientNumber;

    @Embedded
    @ColumnInfo(name = "other_data")
    public OtherData OtherData;
}
