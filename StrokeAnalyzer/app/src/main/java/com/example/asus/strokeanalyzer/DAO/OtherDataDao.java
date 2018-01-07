package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.asus.strokeanalyzer.Entities.OtherData;

import java.util.List;

/**
 * Created by S. Wasilkowski on 2017-12-12.
 */

@Dao
public interface OtherDataDao {
    @Insert
    long insert(OtherData data);

    @Query("SELECT * FROM other_data WHERE patient_id = :patientId")
    List<OtherData> SelectByPatientId(int patientId);

    @Update
    void update(OtherData data);

    @Query("SELECT COUNT(*) FROM other_data WHERE patient_id = :patientId AND answer_id = :answerId")
    int checkIfExists(int patientId, int answerId);

    @Query("DELETE FROM other_data WHERE patient_id = :patientId")
    void deleteByPatientId(int patientId);
}
