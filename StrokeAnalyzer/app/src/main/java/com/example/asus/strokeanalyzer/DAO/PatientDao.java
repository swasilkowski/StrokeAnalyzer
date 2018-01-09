package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.example.asus.strokeanalyzer.Entities.Patient;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Dao
public interface PatientDao {
    @Insert
    long insert(Patient patient);

    @Update
    void update(Patient patient);

    @Query("SELECT * FROM patient")
    List<Patient> selectAll();

    @Query("SELECT * FROM patient WHERE id = :id")
    Patient selectById(int id);

    @Query("SELECT COUNT(*) FROM patient WHERE patient_number = :patientNumber")
    int checkIfPatientExistsByNumber(long patientNumber);

    @Delete
    void delete(Patient patient);
}
