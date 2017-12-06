package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import com.example.asus.strokeanalyzer.Entities.NihssExamination;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Dao
public interface NihssDao {
    @Insert
    public long insert(NihssExamination nihssExamination);

    @Query("SELECT * FROM nihss_form WHERE patient_id = :patientId")
    public List<NihssExamination> SelectByPatientId(int patientId);
}
