package com.example.asus.strokeanalyzer.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.asus.strokeanalyzer.DAO.NihssDao;
import com.example.asus.strokeanalyzer.DAO.OtherDataDao;
import com.example.asus.strokeanalyzer.DAO.PatientDao;
import com.example.asus.strokeanalyzer.Entities.NihssExamination;
import com.example.asus.strokeanalyzer.Entities.OtherData;
import com.example.asus.strokeanalyzer.Entities.Patient;

/**
 * Created by S. Wasilkowski on 2017-12-06.
 */

@Database(entities = {Patient.class, NihssExamination.class, OtherData.class}, version = 1)
public abstract class StrokeAnalyzerDatabase extends RoomDatabase {

    public abstract PatientDao patientDao();

    public abstract NihssDao nihssDao();

    public abstract OtherDataDao otherDataDao();

}
