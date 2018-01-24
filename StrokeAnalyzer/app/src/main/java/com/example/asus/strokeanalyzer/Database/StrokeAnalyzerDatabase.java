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
 * Abstrakcyjna klasa określająca definicję bazy danych aplikacji
 * Jest rozszerzeniem klasy RoomDatabase. Zawiera metody zwracające interfejsy DAO
 * (Data Access Object) bazy danych.
 *
 * @author Stanisław Wasilkowski
 */

@Database(entities = {Patient.class, NihssExamination.class, OtherData.class}, version = 6, exportSchema = false)
public abstract class StrokeAnalyzerDatabase extends RoomDatabase {

    public abstract PatientDao patientDao();

    public abstract NihssDao nihssDao();

    public abstract OtherDataDao otherDataDao();

}
