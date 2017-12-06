package DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Entities.Patient;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Dao
public interface PatientDao {
    @Insert
    public int insert(Patient patient);

    @Update
    public void update(Patient patient);

    @Query("SELECT * FROM patient")
    public List<Patient> selectAll();

    @Query("SELECT * FROM patient WHERE id = :id")
    public Patient selectById(int id);
}
